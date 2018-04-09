package cn.gtmap.landsale.core.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBank;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 银行 服务
 * @author zsj
 * @version v1.0, 2017/9/12
 */
@FeignClient(name = "bank-server")
public interface TransBankClient {

    /**
     * 获取银行 分页服务
     * @param regionCodes 所属行政区
     * @param pageable
     * @param bankName
     * @return
     */
    @RequestMapping(value = "/bank/findBankPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransBank> findBankPage(@RequestParam(value = "bankName", required = false) String bankName, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable);

    /**
     * 通过行政区 获得银行的信息
     * @param regionCode
     * @return
     */
    @RequestMapping(value = "/bank/getBankListByRegion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransBank> getBankListByRegion(@RequestParam(value = "regionCode") String regionCode);

    /**
     * 获取银行
     * @param bankId 银行编号
     * @return
     */
    @RequestMapping(value = "/bank/getBankById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBank getBankById(@RequestParam(value = "bankId") String bankId);

    /**
     * 保存 银行信息
     * @param transBank 银行对象
     * @return
     */
    @RequestMapping(value = "/bank/saveTransBank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransBank> saveTransBank(@RequestBody TransBank transBank);

    /**
     * 删除 银行
     * @param bankId 银行编号
     * @return
     */
    @RequestMapping(value = "/bank/deleteBank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteBank(@RequestParam(value = "bankId") String bankId);

    /**
     * 获取银行
     * @param code 银行code
     * @return
     */
    @RequestMapping(value = "/bank/findByCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBank findByCode(@RequestParam(value = "code") String code);
}
