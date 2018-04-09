package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.Tree;
import cn.gtmap.landsale.core.service.TransRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 行政区服务
* @author zsj
* @version v1.0, 2017/9/12
*/
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    TransRegionService transRegionService;

    /**
     * 获取行政区分页服务
     * @param regionCode 行政区编码
     * @param regionLevel 行政区级别
     * @param request 分页请求
     * @return
     */
    @RequestMapping("/findTransRegionPageByCodeOrLeave")
    public Page<TransRegion> findTransRegionPageByCodeOrLeave(@RequestParam(value = "regionCode", required = false) String regionCode, @RequestParam(value = "regionLevel", required = false) Integer regionLevel, @RequestBody Pageable request) {
        return transRegionService.findTransRegionByCodeOrLeave(regionCode, regionLevel, request);
    }

    /**
     * 获取行政区列表服务
     * @param regionCode 行政区编码 查询行政区及其子集
     * @param regionLevel 行政区级别
     * @return
     */
    @RequestMapping("/findTransRegionListByCodeOrLeave")
    public List<TransRegion> findTransRegionListByCodeOrLeave(@RequestParam(value = "regionCode", required = false) String regionCode, @RequestParam(value = "regionLevel", required = false) Integer regionLevel) {
        return transRegionService.findTransRegionByCodeOrLeave(regionCode, regionLevel);
    }

    /**
     * 根据所属组织 获取组织所属行政区列表服务
     * @param organizeId 获取组织编号
     * @return
     */
    @RequestMapping("/findTransRegionByOrganize")
    public List<TransRegion> findTransRegionByOrganize(@RequestParam(value = "organizeId", required = false) String organizeId) {
        return transRegionService.findTransRegionByOrganize(organizeId);
    }

    /**
     * 根据所属角色 获取组织所属行政区列表服务
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping("/findTransRegionByRole")
    List<TransRegion> findTransRegionByRole(@RequestParam(value = "roleId") String roleId) {
        return transRegionService.findTransRegionByRole(roleId);
    }

    /**
     * 根据行政区Code获取行政区对象
     * @param regionCode 行政区Code
     * @return
     */
    @RequestMapping("/getTransRegionByRegionCode")
    public TransRegion getTransRegionByRegionCode(@RequestParam("regionCode") String regionCode) {
        return transRegionService.getTransRegionByRegionCode(regionCode);
    }

    /**
     * 根据角色操作行政区权限 获取行政区
     * @param roleId
     * @return
     */
    @RequestMapping("/findTransRegionByRoleRegionOperation")
    public List<TransRegion> findTransRegionByRoleRegionOperation(@RequestParam("roleId") String roleId) {
        return transRegionService.findTransRegionByRoleRegionOperation(roleId);
    }

    /**
     * 保存行政区
     * @param transRegion 行政区对象
     * @return
     */
    @RequestMapping("/saveTransRegion")
    public ResponseMessage saveTransRegion(@RequestBody TransRegion transRegion) {
        return transRegionService.saveTransRegion(transRegion);
    }

    /**
     * 修改行政区
     * @param transRegion 行政区对象
     * @return
     */
    @RequestMapping("/updateTransRegion")
    public ResponseMessage updateTransRegion(@RequestBody TransRegion transRegion) {
        return transRegionService.updateTransRegion(transRegion);
    }

    /**
     * 删除行政区
     * @param transRegion 行政区
     */
    @RequestMapping("/deleteTransRegion")
    public ResponseMessage deleteTransRegion(@RequestBody TransRegion transRegion) {
        return transRegionService.deleteTransRegion(transRegion);
    }

    /**
     * 获取行政区Tree结果
     * @param regionCode 传入查询其子集
     * @return
     */
    @RequestMapping("/getRegionTree")
    public List<Tree> getRegionTree(@RequestParam(value = "regionCode", required = false) String regionCode) {
        return transRegionService.getRegionTree(regionCode);
    }


}

