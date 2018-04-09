package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.service.TransBankConfigService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBankConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 菜单首页
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Controller
@RequestMapping("/bank/config")
public class BankConfigController {

    @Autowired
    TransBankConfigService transBankConfigService;

    @RequestMapping("/index")
    public String index(Model model, String bankName, @PageDefault(value = 10) Pageable pageable) {
        Page<TransBankConfig> transBankConfigList = transBankConfigService.getTransBankConfigPage(bankName, pageable);
        model.addAttribute("transBankConfigList", transBankConfigList);
        model.addAttribute("bankName", bankName);
        return "bankConfig/bankConfig-list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "bankConfig/bankConfig-add";
    }

    @RequestMapping("/addBankConfig")
    @ResponseBody
    public ResponseMessage<TransBankConfig> addTransBankConfig(TransBankConfig transBankConfig) {
        return transBankConfigService.addTransBankConfig(transBankConfig);
    }

    @RequestMapping("/edit")
    public String edit(Model model, String configId) {
        TransBankConfig transBankConfig = transBankConfigService.getTransBankConfigById(configId);
        model.addAttribute("transBankConfig", transBankConfig);
        return "bankConfig/bankConfig-add";
    }

    @RequestMapping("/editBankConfig")
    @ResponseBody
    public ResponseMessage<TransBankConfig> editBankConfig(TransBankConfig transBankConfig) {
        return transBankConfigService.updateTransBankConfig(transBankConfig);
    }

    @RequestMapping("/deleteBankConfig")
    @ResponseBody
    public ResponseMessage deleteBankConfig(String configId) {
        return transBankConfigService.deleteTransBankConfig(configId);
    }


}

