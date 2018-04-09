package cn.gtmap.landsale.bank.web;

import cn.gtmap.landsale.bank.service.TransBankPayService;
import cn.gtmap.landsale.common.model.TransBankAccount;
import cn.gtmap.landsale.common.model.TransBankPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bankPay")
public class TransBankPayController {

    @Autowired
    TransBankPayService transBankPayService;

    @RequestMapping("/getTransPaysByAccointIdIsNULL")
    public  List<TransBankPay> getTransPaysByAccointIdIsNULL(@RequestParam(value = "accountId") String accountId) {
        return transBankPayService.getTransPaysByAccointIdIsNULL(accountId);
    }

    @RequestMapping("/getTransBankPaysByAccountId")
    public List<TransBankPay> getTransBankPaysByAccountId(@RequestParam(value = "accountId")String accountId) {
        return transBankPayService.getTransBankPaysByAccountId(accountId);
    }

    /**
     * @作者 王建明 6836
     * @创建日期 2015/7/1
     * @创建时间 17:08
     * @描述 —— 退款说明书中的退款金额需要根据子账号和付款用户去查询所有的付款记录
     */
    @RequestMapping("/getTransBankPaysByAccountCodeAndAccountId")
    public List<TransBankPay> getTransBankPaysByAccountCodeAndAccountId(@RequestParam(value = "accountCode")String accountCode,@RequestParam(value = "accountId")String accountId) {
        return transBankPayService.getTransBankPaysByAccountCodeAndAccountId(accountCode,accountId);
    }

}
