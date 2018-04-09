package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransBankClient;
import cn.gtmap.landsale.admin.register.TransBankTestClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.service.TransBankConfigService;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;

/**
 * 银行 Controller
 * @author zsj
 * @version v1.0, 2017/9/28
 */
@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    TransBankClient transBankClient;

    @Autowired
    TransBankTestClient transBankTestClient;

    @Autowired
    TransBankConfigService transBankConfigService;

    @Autowired
    TransRegionClient transRegionClient;

    @RequestMapping("/index")
    public String index(Model model, String bankName, String regionCodes, @PageDefault(value = 10) Pageable pageable) {
        if (!SecUtil.isAdmin()) {
            regionCodes = SecUtil.getLoginUserRegionCodes();
        }
        // 已开通银行列表
        Page<TransBank> transBankList = transBankClient.findBankPage(bankName, regionCodes, pageable);
        // 银行基础配置列表
        List<TransBankConfig> transBankConfigList = transBankConfigService.getTransBankConfigList();
        model.addAttribute("transBankList", transBankList);
        model.addAttribute("transBankConfigList", transBankConfigList);
        model.addAttribute("bankName", bankName);
        return "bank/bank-list";
    }

    @RequestMapping("/add")
    public String add(Model model, String configId) {
        // 查找 基础银行配置
        TransBankConfig transBankConfig = transBankConfigService.getTransBankConfigById(configId);
        model.addAttribute("transBank", transBankConfig);
        return "bank/bank-add";
    }

    @RequestMapping("/addBank")
    @ResponseBody
    public ResponseMessage<TransBank> addBank(TransBank transBank) {
        transBank.setBankId(null);
        return transBankClient.saveTransBank(transBank);
    }

    @RequestMapping("/edit")
    public String edit(Model model, String bankId) {
        // 查找 基础银行配置
        TransBank transBank = transBankClient.getBankById(bankId);
        // 查找行政区
        TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(transBank.getRegionCode());
        model.addAttribute("transRegion", transRegion);
        model.addAttribute("transBank", transBank);
        return "bank/bank-add";
    }

    @RequestMapping("/editBank")
    @ResponseBody
    public ResponseMessage<TransBank> editBank(TransBank transBank) {
        return transBankClient.saveTransBank(transBank);
    }

    @RequestMapping("/deleteBank")
    @ResponseBody
    public ResponseMessage deleteBank(String bankId) {
        return transBankClient.deleteBank(bankId);
    }

    @RequestMapping("/sendText")
    @ResponseBody
    public ResponseMessage sendText(String bankId) {
        return transBankTestClient.sendTestXml(bankId);
    }

    @RequestMapping("/toSendPay")
    public String toSendPay(Model model, String bankId) {
        // 查找 基础银行配置
        TransBank transBank = transBankClient.getBankById(bankId);
        TransBankPay transBankPay = new TransBankPay();
        transBankPay.setBankCode(transBank.getBankCode());
        transBankPay.setAccountName(transBank.getAccountName());
        transBankPay.setMoneyUnit(transBank.getMoneyUnit());
        transBankPay.setPayTime(Calendar.getInstance().getTime());
        model.addAttribute("transBankPay", transBankPay);
        model.addAttribute("transBank", transBank);
        return "bank/bank-sendpay";
    }

    @RequestMapping("/sendPay")
    @ResponseBody
    public ResponseMessage sendPay(TransBankPay transBankPay, String bankId) {
        return transBankTestClient.sendBankPayTest(transBankPay, bankId);
    }

}

