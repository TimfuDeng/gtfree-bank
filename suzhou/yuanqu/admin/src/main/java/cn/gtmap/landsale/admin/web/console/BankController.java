package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.landsale.core.BankAllList;
import cn.gtmap.landsale.core.BankSocketServer;
import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransBankPay;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.RegionService;
import cn.gtmap.landsale.service.TransBankAccountService;
import cn.gtmap.landsale.service.TransBankInterfaceService;
import cn.gtmap.landsale.service.TransBankService;
import cn.gtmap.landsale.util.ClientSocketUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * 银行管理
 * Created by jiff on 14-6-26.
 */
@Controller
@RequestMapping(value = "console/bank")
public class BankController {
    private static Logger log = LoggerFactory.getLogger(BankController.class);
    @Autowired
    TransBankService transBankService;

    @Autowired
    TransBankAccountService transBankAccountService;

    @Autowired
    BankSocketServer bankSocketServer;

    @Autowired
    BankAllList bankAllList;

    @Autowired
    RegionService regionService;

    @Autowired
    TransBankInterfaceService transBankInterfaceService;

    @RequestMapping("list")
    public String list(Model model,String regionCode) throws Exception {
        Set<String> regions= Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions=SecUtil.getPermittedRegions();

        List<TransBank> transBankList = StringUtils.isNotBlank(regionCode)?transBankService.getBankListByRegion(regionCode):transBankService.getBankListByRegions(regions);
        model.addAttribute("transBankList", transBankList);
        model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        model.addAttribute("bankAllList", bankAllList);
        model.addAttribute("regionCode", regionCode);
        return "bank/bank-list";
    }

    @RequestMapping("edit")
    public String bank(String bankId, String bankCode, Model model) {
        TransBank transBank = null;
        if (StringUtils.isNotBlank(bankId)) {
            transBank = transBankService.getBank(bankId);
        } else {
            transBank = new TransBank();
            transBank.setBankCode(bankCode);
            transBank.setMoneyUnit("CNY");
            transBank.setBankName(bankAllList.getBankName(bankCode));
            List<String[]> regionList = regionService.findAllDeptRegions();
            transBank.setRegionCode(regionList.get(0)[0]);
        }
        model.addAttribute("transBank", transBank);
        model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        return "bank/bank-edit";
    }

    @ModelAttribute("bank")
    public TransBank getBank(@RequestParam(value = "id", required = false) String bankId) {
        return StringUtils.isBlank(bankId) ? new TransBank() : transBankService.getBank(bankId);
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("bank") TransBank transBank, RedirectAttributes ra, Model model) {
        if (transBank != null) {
            transBankService.save(transBank);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "保存成功！");
        }
        return "redirect:/console/bank/edit?bankId=" + transBank.getBankId();
    }

    @RequestMapping("del")
    public String del(String bankId, RedirectAttributes ra, Model model) {
        if (StringUtils.isNotBlank(bankId)) {
            transBankService.del(bankId);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "删除成功！");
        }
        return "redirect:/console/bank/list";
    }

    @RequestMapping("test.f")
    public @ResponseBody String testBank(String bankId){
        TransBank transBank = transBankService.getBank(bankId);
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendTestXml());
            try {
                String returnXml = clientSocketUtil.recieve();
                clientSocketUtil.close();
                Document doc = DocumentHelper.parseText(returnXml);
                if("00".equals(getElementValue(doc, "//body/Result"))){
                    return "1";
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return "0";
    }

    //发送到账通知，测试用
    @RequestMapping("sendpay")
    public String sendPay(String bankId, TransBankPay transBankPay, Model model){
        TransBank transBank = transBankService.getBank(bankId);
        transBankPay.setBankCode(transBank.getBankCode());
//        transBankPay.setAccountCode(transBank.getAccountCode());
        transBankPay.setAccountName(transBank.getAccountName());
        transBankPay.setMoneyUnit(transBank.getMoneyUnit());
        transBankPay.setPayTime(Calendar.getInstance().getTime());
        if (StringUtils.isNotBlank(transBankPay.getAccountCode())) {
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil("127.0.0.1", bankSocketServer.getPort());
            clientSocketUtil.send(transBankInterfaceService.sendBankPayTest(transBankPay));
            try {
                String returnXml = clientSocketUtil.recieve();
                clientSocketUtil.close();
                Document doc = DocumentHelper.parseText(returnXml);
                model.addAttribute("retcode", getElementValue(doc, "//body/Result"));
                model.addAttribute("retmsg", getElementValue(doc, "//body/AddWord"));
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }else{
            model.addAttribute("retmsg", "入款帐号必填！");
        }
        model.addAttribute("transBankPay", transBankPay);
        model.addAttribute("transBank", transBank);
        return "bank/bank-sendpay";
    }

    private String getElementValue(Document doc,String path){
        Element element=(Element) doc.selectSingleNode(path);
        if (element!=null){
            return element.getTextTrim();
        }else{
            return null;
        }
    }
}

