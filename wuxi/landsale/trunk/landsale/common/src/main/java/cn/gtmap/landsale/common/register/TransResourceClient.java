package cn.gtmap.landsale.common.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageImpl;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 地块资源 服務
 *
 * @author cxm
 * @version v1.0, 2017/10/20
 */
@FeignClient(name = "core-server")
public interface TransResourceClient {

    /**
     * 获取地块信息
     * @param title
     * @param displayStatus
     * @param regionCodes
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/resource/findDisplayResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransResource> findDisplayResource(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "displayStatus", required = false) String displayStatus, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable);

    /**
     * 更新地块
     * @param resourceId
     * @param displayStatus
     */
    @RequestMapping(value = "/resource/updateTransResourceDisplayStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateTransResourceDisplayStatus(@RequestParam("resourceId") String resourceId, @RequestParam("displayStatus") int displayStatus);

    /**
     * 查询符合条件的地块
     * @param displayStatus
     * @return
     */
    @RequestMapping(value = "/resource/findTransResourcesByDisplayStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResource> findTransResourcesByDisplayStatus(@RequestParam("displayStatus") int displayStatus);

    /**
     * 获得最新的报价
     * @param resourceId
     * @param request
     * @return
     */
    @RequestMapping(value = "/resource/getResourceOfferPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    PageImpl<TransResourceOffer> getResourceOfferPage(@RequestParam("resourceId") String resourceId, @PageDefault(value = 10) Pageable request);

    /**
     * 获取地块的报价次数
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/resource/getResourceOfferFrequency", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Long getResourceOfferFrequency(@RequestParam("resourceId") String resourceId);

    /**
     * 根据offerId获得报价
     * @param offerId
     * @return
     */
    @RequestMapping(value = "/resource/getTransResourceOffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceOffer getTransResourceOffer(@RequestParam("offerId") String offerId);

    /**
     * 查询列表
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/resource/getTransResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResource getTransResource(@RequestParam("resourceId") String resourceId);
}
