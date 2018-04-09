package cn.gtmap.landsale.client.register;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBankAccount;
import cn.gtmap.landsale.common.model.TransBankPay;
import cn.gtmap.landsale.common.model.TransResourceApply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 银行接口 服务
 * @author zsj
 * @version v1.0, 2017/9/12
 */
@FeignClient(name = "bank-server")
public interface TransBankInterfaceClient {

    /**
     * G00001
     * 模拟银行发送保证金到账通知 测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankPay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankPay(@RequestBody TransBankPay transBankPay, @RequestParam("bankId") String bankId);

    /**
     * G00012
     * 模拟银行发送保证金退款到账通知 测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankRefund", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankRefund(@RequestBody TransBankAccount transBankAccount);

    /**
     * G00003
     * 向银行发送开户申请 申请保证金子账号测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankApplyAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankApplyAccount(@RequestBody TransResourceApply transResourceApply);

    /**
     * G00004
     * 向银行注销子账号测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankCancelAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankCancelAccount(@RequestBody TransBankAccount transBankAccount);

    /**
     * G00005
     * 向银行查询保证金到账明细 测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankPayDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankPayDetail(@RequestBody TransBankAccount transBankAccount);

    /**
     * G00009
     * 链路测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendTestXml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendTestXml(@RequestParam(value = "bankId") String bankId);

    /**
     * G00010
     * 模拟退款申请 测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/applyBankRefund", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage applyBankRefund(@RequestBody TransBankAccount transBankAccount);

    /**
     * G00011
     * 模拟向银行发送保证金退款明细查询 测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankRefundDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankRefundDetail(@RequestParam(value = "batchNo") String batchNo, @RequestParam(value = "accountCode") String accountCode, @RequestParam(value = "payBankAccount") String payBankAccount, @RequestParam(value = "payNo") String payNo, @RequestParam(value = "amount") String amount);

    /**
     * G00013
     * 模拟向银行发送子账号剩余情况查询 测试
     * @return
     */
    @RequestMapping(value = "/bankInterface/sendBankAccountSurplus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankAccountSurplus(@RequestParam(value = "bankCode") String bankCode);

}
