package cn.gtmap.landsale.client.register;

import cn.gtmap.landsale.common.model.TransBankAccount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 银行账户服务
 * @author lq
 * @version v1.0, 2017/11/20
 */
@FeignClient(name = "bank-server")
public interface TransBankAccountClient {

    /**
     * 获取子账号信息
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/bankAccount/getTransBankAccountByApplyId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBankAccount getTransBankAccountByApplyId(@RequestParam(value = "applyId", required = false) String applyId);

    /**
     * 获得银行子账户开户信息
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/bankAccount/getTransBankAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBankAccount getTransBankAccount(@RequestParam(value = "accountId", required = false) String accountId);

    /**
     * 根据applyNo获得银行子账户信息,流水号
     * @param applyNo
     * @return
     */
    @RequestMapping(value = "/bankAccount/getTransBankAccountByApplyNo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBankAccount getTransBankAccountByApplyNo(@RequestParam(value = "applyNo", required = false) String applyNo);

    /**
     * 获得正在使用的银行子帐号信息
     * @param accountCode
     * @return
     */
    @RequestMapping(value = "/bankAccount/getTransBankAccountByAccountCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBankAccount getTransBankAccountByAccountCode(@RequestParam(value = "accountCode", required = false) String accountCode);

    /**
     * 保存相关子账户信息
     * @param transBankAccount
     * @return
     */
    @RequestMapping(value = "/bankAccount/saveTransBankAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBankAccount saveTransBankAccount(@RequestBody TransBankAccount transBankAccount);

    /**
     * 获取下一个竞买号
     * @return
     */
    @RequestMapping(value = "/bankAccount/getNextApplyNo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String getNextApplyNo();

    /**
     * 获得 或 创建 子账号信息
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/bankAccount/createOrGetTransBankAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBankAccount createOrGetTransBankAccount(@RequestParam(value = "applyId", required = false) String applyId);

}
