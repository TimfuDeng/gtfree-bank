package cn.gtmap.landsale.client.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.Tree;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 行政区服务
 * @author zsj
 * @version v1.0, 2017/9/12
 */
@FeignClient(name = "core-server")
public interface TransRegionClient {

    /**
     * 获取行政区分页服务
     * @param regionCode 行政区编码
     * @param regionLevel 行政区级别
     * @param request 分页请求
     * @return
     */
    @RequestMapping(value = "/region/findTransRegionPageByCodeOrLeave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransRegion> findTransRegionPageByCodeOrLeave(@RequestParam("regionCode") String regionCode, @RequestParam("regionLevel") Integer regionLevel, @RequestBody Pageable request);

    /**
     * 获取行政区列表服务
     * @param regionCode 行政区编码 查询行政区及其子集
     * @param regionLevel 行政区级别
     * @return
     */
    @RequestMapping(value = "/region/findTransRegionListByCodeOrLeave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransRegion> findTransRegionListByCodeOrLeave(@RequestParam(value = "regionCode", required = false) String regionCode, @RequestParam(value = "regionLevel", required = false) Integer regionLevel);

    /**
     * 根据所属组织 获取组织所属行政区列表服务
     * @param organizeId 获取组织编号
     * @return
     */
    @RequestMapping(value = "/region/findTransRegionByOrganize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransRegion> findTransRegionByOrganize(@RequestParam("organizeId") String organizeId);

    /**
     * 根据所属角色 获取组织所属行政区列表服务
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping(value = "/region/findTransRegionByRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransRegion> findTransRegionByRole(@RequestParam(value = "roleId") String roleId);

    /**
     * 根据角色操作行政区权限 获取行政区
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/region/findTransRegionByRoleRegionOperation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransRegion> findTransRegionByRoleRegionOperation(@RequestParam(value = "roleId") String roleId);

    /**
     * 根据行政区Code获取行政区对象
     * @param regionCode 行政区Code
     * @return
     */
    @RequestMapping(value = "/region/getTransRegionByRegionCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransRegion getTransRegionByRegionCode(@RequestParam("regionCode") String regionCode);

    /**
     * 保存行政区
     * @param transRegion 行政区对象
     * @return
     */
    @RequestMapping(value = "/region/saveTransRegion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage saveTransRegion(@RequestBody TransRegion transRegion);

    /**
     * 修改行政区
     * @param transRegion 行政区对象
     * @return
     */
    @RequestMapping(value = "/region/updateTransRegion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage updateTransRegion(@RequestBody TransRegion transRegion);

    /**
     * 删除行政区
     * @param transRegion 行政区
     * @return
     */
    @RequestMapping(value = "/region/deleteTransRegion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransRegion(@RequestBody TransRegion transRegion);

    /**
     * 获取行政区Tree结果
     * @param regionCode 传入查询其子集
     * @return
     */
    @RequestMapping(value = "/region/getRegionTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Tree> getRegionTree(@RequestParam(value = "regionCode", required = false) String regionCode);
}
