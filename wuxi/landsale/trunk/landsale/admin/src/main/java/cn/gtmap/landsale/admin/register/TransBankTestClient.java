package cn.gtmap.landsale.admin.register;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBankPay;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 银行 服务
 * @author zsj
 * @version v1.0, 2017/9/12
 */
@FeignClient(name = "bank-server")
public interface TransBankTestClient {

    /**
     * G00009
     * 链路测试
     * @return
     */
    @RequestMapping(value = "/bankTest/sendTestXml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendTestXml(@RequestParam(value = "bankId") String bankId);

    /**
     * G00001
     * 模拟银行发送保证金到账通知 测试
     * @return
     */
    @RequestMapping(value = "/bankTest/sendBankPayTest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage sendBankPayTest(@RequestBody TransBankPay transBankPay, @RequestParam(value = "bankId") String bankId);


}
