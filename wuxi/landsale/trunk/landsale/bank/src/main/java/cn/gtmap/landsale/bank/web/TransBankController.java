package cn.gtmap.landsale.bank.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.bank.service.TransBankPayService;
import cn.gtmap.landsale.bank.service.TransBankService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBank;
import cn.gtmap.landsale.common.model.TransBankPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 银行 Controller
 * @author zsj
 * @version v1.0, 2017/8/30
 */
@RestController
@RequestMapping("/bank")
public class TransBankController {

    private static Logger log = LoggerFactory.getLogger(TransBankController.class);

    @Autowired
    TransBankService transBankService;

    @Autowired
    TransBankPayService transBankPayService;

    @RequestMapping("/findBankPage")
    public Page<TransBank> findBankPage(@RequestParam(value = "bankName", required = false) String bankName, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable) {
        return transBankService.findBankPage(bankName, regionCodes, pageable);
    }

    @RequestMapping("/getBankListByRegion")
    public List<TransBank> getBankListByRegion(@RequestParam(value = "regionCode") String regionCode) {
        return transBankService.getBankListByRegion(regionCode);
    }

    @RequestMapping("/getBankById")
    public TransBank getBankById(@RequestParam(value = "bankId") String bankId) {
        return transBankService.getBank(bankId);
    }

    @RequestMapping("/saveTransBank")
    public ResponseMessage<TransBank> saveTransBank(@RequestBody TransBank transBank) {
        return transBankService.saveTransBank(transBank);
    }

    @RequestMapping("/deleteBank")
    public ResponseMessage deleteBank(@RequestParam(value = "bankId") String bankId) {
        return transBankService.deleteBank(bankId);
    }

    @RequestMapping("/getTransPaysByAccointIdIsNULL")
    public  List<TransBankPay> getTransPaysByAccointIdIsNULL(@RequestParam(value = "accountId") String accountId) {
        return transBankPayService.getTransPaysByAccointIdIsNULL(accountId);
    }

    @RequestMapping("/findByCode")
    public TransBank findByCode(@RequestParam(value = "code") String code) {
        return transBankService.findByCode(code);
    }

}
