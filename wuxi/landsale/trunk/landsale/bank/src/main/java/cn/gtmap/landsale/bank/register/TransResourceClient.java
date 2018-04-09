package cn.gtmap.landsale.bank.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 地块资源 服務
 * @author cxm
 * @version v1.0, 2017/10/20
 */
@FeignClient(name = "core-server")
public interface TransResourceClient {

    /**
     * 获取可以显示在大屏幕上的地块
     * @param title
     * @param displayStatus
     * @param regionCodes
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/resource/findDisplayResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransResource> findDisplayResource(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "displayStatus", required = false) String displayStatus, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable);

    /**
     * 更新地块在交易大屏幕上的显示状态
     * @param resourceId
     * @param displayStatus
     */
    @RequestMapping(value = "/resource/updateTransResourceDisplayStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateTransResourceDisplayStatus(@RequestParam("resourceId") String resourceId, @RequestParam("displayStatus") int displayStatus);

    /**
     * 根据显示状态查找地块
     * @param displayStatus
     * @return
     */
    @RequestMapping(value = "/resource/findTransResourcesByDisplayStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResource> findTransResourcesByDisplayStatus(@RequestParam("displayStatus") int displayStatus);

    /**
     * 获得出让地块
     * @param resourceId 地块Id
     * @return
     */
    @RequestMapping(value = "/resource/getTransResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResource getTransResource(@RequestParam("resourceId") String resourceId);

    /**
     * 查询资源、分页，Admin用
     * @param title
     * @param status
     * @param pageable
     * @param ggId
     * @param regionCodes
     * @return
     */
    @RequestMapping(value = "/resource/findTransResourcesByEditStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransResource> findTransResourcesByEditStatus(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "status", required = false) int status, @RequestParam(value = "ggId", required = false) String ggId, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable);

    /**
     * 保存出让地块对象
     * @param transResource 出让地块
     * @return ResponseMessage<TransResource>
     */
    @RequestMapping(value = "/resource/saveTransResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResource> saveTransResource(@RequestBody TransResource transResource);

    /**
     * 统计未审核，报名中，已通过
     * @param transResourcePage
     * @return
     */
    @RequestMapping(value = "/resource/countPassOrUnpass", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransResource> countPassOrUnpass(@RequestBody Page<TransResource> transResourcePage);


    /**
     * 获得出让地块
     * @param resourceCode 地块Code
     * @return
     */
    @RequestMapping(value = "/resource/getTransResourceByCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResource getTransResourceByCode(@RequestParam("resourceCode") String resourceCode);

    /**
     * 根据公告Id获取公告地块
     * @param ggId
     * @return
     */
    @RequestMapping(value = "/resource/findTransResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResource> findTransResource(@RequestParam("ggId") String ggId);

    /**
     * 查询资源，分页,client用
     * @param queryParam
     * @param regionCodes
     * @return
     */
    @RequestMapping(value = "/resource/findTransResources", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransResource> findTransResources(@RequestBody ResourceQueryParam queryParam, @RequestParam(value = "regionCodes") String regionCodes);

    /**
     * 更新地块状态
     * @param resource
     * @param status
     * @return
     */
    @RequestMapping(value = "/resource/saveTransResourceStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResource> saveTransResourceStatus(@RequestBody TransResource resource, @RequestParam(value = "status") int status);

}
