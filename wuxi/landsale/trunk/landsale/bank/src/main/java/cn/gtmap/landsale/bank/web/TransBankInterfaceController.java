package cn.gtmap.landsale.bank.web;


import cn.gtmap.landsale.bank.service.TransBankInterfaceService;
import cn.gtmap.landsale.bank.service.TransBankReciverService;
import cn.gtmap.landsale.bank.service.TransBankService;
import cn.gtmap.landsale.bank.util.ClientSocketUtil;
import cn.gtmap.landsale.common.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 银行测试Controller
 * @author zsj
 * @version v1.0, 2017/8/30
 */
@RestController
@RequestMapping("/bankInterface")
@Api(value="onlinestore", description="银行接口模拟测试API")
public class TransBankInterfaceController {

    private static Logger log = LoggerFactory.getLogger(TransBankInterfaceController.class);

    @Autowired
    TransBankInterfaceService transBankInterfaceService;

    @Autowired
    TransBankReciverService transBankReciverService;

    @Autowired
    TransBankService transBankService;

    /**
     * G00001
     * 模拟银行发送保证金到账通知 测试
     * @return
     */
    @RequestMapping(value = "/sendBankPay", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "G00001模拟银行发送保证金到账通知 测试")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "测试数据：[payId:015b2889b4e650c5d5015b2795ae002a,bankId:015ad1a9394c50c5d71d5ad1a8690006]")
    })
    public ResponseMessage sendBankPay(@RequestBody TransBankPay transBankPay, @RequestParam("bankId") String bankId) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank(bankId);
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankReciverService.sendBankPayTest(transBankPay));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00012
     * 模拟银行发送保证金退款到账通知 测试
     * @return
     */
    @RequestMapping(value = "/sendBankRefund", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "G00012模拟银行发送保证金退款到账通知 测试")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "测试数据：[accountId:015b2874d07050c5d5015b2795ae0026,accountCode:32001594140052502581-0016]," +
                    "[accountId:015b284d83e550c5d5015b2795ae001e,accountCode:32001594140052502581-0015]")
    })
    public ResponseMessage sendBankRefund(@RequestBody TransBankAccount transBankAccount) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank("015ad1a9394c50c5d71d5ad1a8690006");
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankReciverService.sendBankRefundTest(transBankAccount));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }



    /**
     * G00003
     * 向银行发送开户申请 申请保证金子账号测试
     * @return
     */
    @RequestMapping(value = "/sendBankApplyAccount", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "G00003向银行发送开户申请 申请保证金子账号测试")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "测试数据：[resourceId:015d2b833d335a0c0c1e5d2b54bd0013,applyId:015d30332a5e5a0c26c15d302e3f000d,bankCode:CBC]," +
                    "[resourceId:015d9740a5525a0cd86d5d962c3f000f,applyId:015d97b2e58a5a0c35305d978395000d,bankCode:ABC]")
    })
    public ResponseMessage sendBankApplyAccount(@RequestBody TransResourceApply transResourceApply) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = null;
        // 测试数据 正式使用时删掉 TODO
        if (StringUtils.isBlank(transResourceApply.getBankId())) {
            transBank = transBankService.getBank("015ad1a9394c50c5d71d5ad1a8690006");
        } else {
            transBank = transBankService.getBank(transResourceApply.getBankId());
        }
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendBankApplyAccount(transResourceApply));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00004
     * 向银行注销子账号测试
     * @return
     */
    @ApiOperation(value = "G00004向银行注销子账号测试")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "测试数据：[accountCode:620000000281,applyNo:00000281]")
    })
    @RequestMapping(value = "/sendBankCancelAccount", method = RequestMethod.POST)
    public ResponseMessage sendBankCancelAccount(@RequestBody TransBankAccount transBankAccount) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank("015ad1a9394c50c5d71d5ad1a8690006");
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendBankCancelAccount(transBankAccount));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00005
     * 向银行查询保证金到账明细 测试
     * @return
     */
    @ApiOperation(value = "G00005向银行查询保证金到账明细 测试")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "测试数据：[accountCode:32001594140052502581-0016,applyNo:000000000205,applyId:015b2874c0bc50c5d5015b2795ae0025]," +
                    "[accountCode:32001594140052502581-0015,applyNo:000000000204,applyId:015b284d713850c5d5015b2795ae001d]")
    })
    @RequestMapping(value = "/sendBankPayDetail", method = RequestMethod.POST)
    public ResponseMessage sendBankPayDetail(@RequestBody TransBankAccount transBankAccount) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank("015ad1a9394c50c5d71d5ad1a8690006");
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendBankPayDetail(transBankAccount));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00009
     * 链路测试
     * @return
     */
    @ApiOperation(value = "G00009链路测试")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "测试数据：015ad1a9394c50c5d71d5ad1a8690006")
    })
    @RequestMapping(value = "/sendTestXml", method = RequestMethod.POST)
    public ResponseMessage sendTestXml(@RequestParam(value = "bankId") String bankId) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank(bankId);
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendTestXml());
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00010
     * 模拟退款申请 测试
     * @return
     */
    @ApiOperation(value = "G00010模拟退款申请 测试")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "测试数据：[accountCode:32001594140052502581-0016,accountId:015b2874d07050c5d5015b2795ae0026]," +
                "[accountCode:32001594140052502581-0015,accountId:015b284d83e550c5d5015b2795ae001e]")
    })
    @RequestMapping(value = "/applyBankRefund", method = RequestMethod.POST)
    public ResponseMessage applyBankRefund(@RequestBody TransBankAccount transBankAccount) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank("015ad1a9394c50c5d71d5ad1a8690006");
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendBankRefund(transBankAccount));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00011
     * 模拟向银行发送保证金退款明细查询 测试
     * @return
     */
    @ApiOperation(value = "G00011模拟向银行发送保证金退款明细查询 测试")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "测试数据(测试数据batchNo随便写)：[batchNo:001,accountCode:32001594140052502581-0016]," +
                    "[batchNo:001,accountCode:32001594140052502581-0015]")
    })
    @RequestMapping(value = "/sendBankRefundDetail", method = RequestMethod.POST)
    public ResponseMessage sendBankRefundDetail(@RequestParam(value = "batchNo") String batchNo, @RequestParam(value = "accountCode")  String accountCode, @RequestParam(value = "payBankAccount")  String payBankAccount, @RequestParam(value = "payNo")  String payNo, @RequestParam(value = "amount")  String amount) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBank("015ad1a9394c50c5d71d5ad1a8690006");
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendBankRefundDetail(batchNo, accountCode, payBankAccount, payNo, amount));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
    }

    /**
     * G00013
     * 模拟向银行发送子账号剩余情况查询 测试
     * @return
     */
    @ApiOperation(value = "G00013模拟向银行发送子账号剩余情况查询 测试")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "测试数据：[bankCode:CCB,]")
    })
    @RequestMapping(value = "/sendBankAccountSurplus", method = RequestMethod.POST)
    public ResponseMessage sendBankAccountSurplus(String bankCode) {
        ResponseMessage responseMessage = new ResponseMessage();
        TransBank transBank = transBankService.getBankByCodeAndRegion(bankCode, null);
        if(transBank !=null && StringUtils.isNotBlank(transBank.getInterfaceIp())){
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil =
                    new ClientSocketUtil(transBank.getInterfaceIp(), Integer.parseInt(transBank.getInterfacePort()));
            clientSocketUtil.send(transBankInterfaceService.sendBankAccountSurplus(transBank));
            try {
                String returnXml = clientSocketUtil.recieve();
                Document doc = DocumentHelper.parseText(returnXml);
                clientSocketUtil.close();
                responseMessage.setMessage(returnXml);
                responseMessage.setCode(getElementValue(doc, "//body/Result"));
                return responseMessage;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return responseMessage;
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
