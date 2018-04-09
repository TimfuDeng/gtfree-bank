package cn.gtmap.landsale.client.register;

import cn.gtmap.landsale.common.model.TransBankPay;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 银行
 * @author lq
 * @version v1.0, 2017/11/16
 */
@FeignClient(name = "bank-server")
public interface TransBankPayClient {

    /**
     * 错转款  accountId is null
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/bankPay/getTransPaysByAccointIdIsNULL", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransBankPay> getTransPaysByAccointIdIsNULL(@RequestParam(value = "accountId") String accountId);

    /**
     * 根据账户信息获取到账列表
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/bankPay/getTransBankPaysByAccountId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransBankPay> getTransBankPaysByAccountId(@RequestParam(value = "accountId") String accountId);

    /**
     * @作者 王建明 6836
     * @创建日期 2015/7/1
     * @创建时间 17:08
     * @描述 —— 退款说明书中的退款金额需要根据子账号和付款用户去查询所有的付款记录
     */
    @RequestMapping(value = "/bankPay/getTransBankPaysByAccountCodeAndAccountId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransBankPay> getTransBankPaysByAccountCodeAndAccountId(@RequestParam(value = "accountCode") String accountCode,@RequestParam(value = "accountId") String accountId);


}
