package cn.gtmap.landsale.bank.web;

import cn.gtmap.landsale.bank.service.TransBankAccountService;
import cn.gtmap.landsale.common.model.TransBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankAccount")
public class TransBankAccountController {

    @Autowired
    TransBankAccountService transBankAccountService;

    /**
     * 根据applyId获得银行子账户信息
     * @param applyId
     * @return
     */
    @RequestMapping("/getTransBankAccountByApplyId")
    public TransBankAccount getTransBankAccountByApplyId(@RequestParam(value = "applyId") String applyId) {
        return transBankAccountService.getTransBankAccountByApplyId(applyId);
    }

    /**
     * 获得银行子账户开户信息
     * @param accountId
     * @return
     */
    @RequestMapping("/getTransBankAccount")
    public TransBankAccount getTransBankAccount(@RequestParam(value = "accountId") String accountId) {
        return transBankAccountService.getTransBankAccount(accountId);
    }

    /**
     * 根据applyNo获得银行子账户信息,流水号
     * @param applyNo
     * @return
     */
    @RequestMapping("/getTransBankAccountByApplyNo")
    public TransBankAccount getTransBankAccountByApplyNo(@RequestParam(value = "applyNo") String applyNo) {
        return transBankAccountService.getTransBankAccountByApplyNo(applyNo);
    }

    /**
     * 获得正在使用的银行子帐号信息
     * @param accountCode
     * @return
     */
    @RequestMapping("/getTransBankAccountByAccountCode")
    public TransBankAccount getTransBankAccountByAccountCode(@RequestParam(value = "accountCode") String accountCode) {
        return transBankAccountService.getTransBankAccountByAccountCode(accountCode);
    }

    /**
     * 保存相关子账户信息
     * @param transBankAccount
     * @return
     */
    @RequestMapping("/saveTransBankAccount")
    public TransBankAccount saveTransBankAccount(@RequestBody TransBankAccount transBankAccount) {
        return transBankAccountService.saveTransBankAccount(transBankAccount);
    }

    /**
     * 获取下一个竞买号
     * @return
     */
    @RequestMapping("/getNextApplyNo")
    public String getNextApplyNo() {
        return transBankAccountService.getNextApplyNo();
    }

    /**
     * 获得 或 创建 子账号信息
     * @param applyId
     * @return
     */
    @RequestMapping("/createOrGetTransBankAccount")
    public TransBankAccount createOrGetTransBankAccount(@RequestParam(value = "applyId") String applyId) {
        return transBankAccountService.createOrGetTransBankAccount(applyId);
    }

}
