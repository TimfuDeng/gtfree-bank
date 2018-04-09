package cn.gtmap.landsale.common.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 出让公告服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@FeignClient(name = "core-server")
public interface TransCrggClient {

    /**
     * 根据Id获取出让公告
     * @param ggId 公告Id
     * @return 出让公告对象
     */
    @RequestMapping(value = "/crgg/getTransCrgg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransCrgg getTransCrgg(@RequestParam(value = "ggId") String ggId);

    /**
     * 根据 导入供应公告Id获取出让公告
     * @param gyggId 导入公告Id
     * @return 出让公告对象
     */
    @RequestMapping(value = "/crgg/getTransCrggByGyggId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransCrgg getTransCrggByGyggId(@RequestParam(value = "gyggId") String gyggId);

    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param regionCodes 行政区编号
     * @param request 分页请求对象
     * @return 分页列表
     */
    @RequestMapping(value = "/crgg/findTransCrgg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransCrgg> findTransCrgg(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable request);

    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param regionCodes 行政区编号
     * @param request 分页请求对象
     * @param crggStatus 公告状态
     * @return 分页列表
     */
    @RequestMapping(value = "/crgg/findTransCrggByStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransCrgg> findTransCrggByStatus(@RequestParam("title") String title, @RequestParam("regionCodes") String regionCodes, @RequestBody Pageable request, @RequestParam("crggStatus") int crggStatus);

    /**
     * 保存出让公告对象
     * @param transCrgg 出让公告对象
     * @param materialIds
     * @return  新的出让公告对象
     */
    @RequestMapping(value = "/crgg/saveTransCrgg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransCrgg> saveTransCrgg(@RequestBody TransCrgg transCrgg, @RequestParam(value = "materialIds", required = false) String materialIds);

    /**
     * 删除出让公告
     * @param ggIds 公告对象Id
     * @return
     */
    @RequestMapping(value = "/crgg/deleteTransCrgg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransCrgg(@RequestParam(value = "ggIds") String ggIds);

}
