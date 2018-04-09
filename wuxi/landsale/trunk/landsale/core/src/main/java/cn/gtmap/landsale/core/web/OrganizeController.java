package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganize;
import cn.gtmap.landsale.core.service.TransOrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* 组织服务
* @author zsj
* @version v1.0, 2017/9/12
*/
@RestController
@RequestMapping("/organize")
public class OrganizeController {

    @Autowired
    TransOrganizeService transOrganizeService;

    /**
     * 获取组织 分页服务
     * @param organizeName 组织名称
     * @param regionCodes 所属行政区
     * @param request 分页请求
     * @return
     */
    @RequestMapping("/findTransOrganizePage")
    public Page<TransOrganize> findTransOrganizePage(@RequestParam(value = "organizeName", required = false) String organizeName, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody PageRequest pageable, HttpServletRequest request) {
        return transOrganizeService.findTransOrganizePage(organizeName, regionCodes, pageable);
    }

    /**
     * 获取组织 列表服务
     * @param organizeName 组织名称
     * @param regionCode 组织Code
     * @return
     */
    @RequestMapping("/findTransOrganizeList")
    public List<TransOrganize> findTransOrganizeList(@RequestParam(value = "organizeName", required = false) String organizeName, @RequestParam(value = "regionCode", required = false) String regionCode) {
        return transOrganizeService.findTransOrganizeList(organizeName, regionCode);
    }

    /**
     * 通过角色 获取组织 列表服务
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping("/findTransOrganizeByRole")
    List<TransOrganize> findTransOrganizeByRole(@RequestParam(value = "roleId") String roleId) {
        return transOrganizeService.findTransOrganizeByRole(roleId);
    }

    /**
     * 根据组织Id 获取组织对象
     * @param organizeId 组织Id
     * @return
     */
    @RequestMapping("/getTransOrganizeById")
    public TransOrganize getTransOrganizeById(@RequestParam("organizeId") String organizeId) {
        return transOrganizeService.getTransOrganizeById(organizeId);
    }

    /**
     * 保存组织
     * @param transOrganize 组织对象
     * @param regionCodes 行政区Codes
     * @return
     */
    @RequestMapping("/saveTransOrganize")
    public ResponseMessage<TransOrganize> saveTransOrganize(@RequestBody TransOrganize transOrganize, @RequestParam("regionCodes") String regionCodes) {
        return transOrganizeService.saveTransOrganize(transOrganize, regionCodes);
    }

    /**
     * 删除组织
     * @param transOrganize 组织
     */
    @RequestMapping("/deleteTransOrganize")
    public ResponseMessage deleteTransOrganize(@RequestBody TransOrganize transOrganize) {
        return transOrganizeService.deleteTransOrganize(transOrganize);
    }

    /**
     * 修改组织
     * @param transOrganize 组织对象
     * @param regionCodes 行政区Codes
     * @return
     */
    @RequestMapping("/updateTransOrganize")
    public ResponseMessage<TransOrganize> updateTransOrganize(@RequestBody TransOrganize transOrganize, @RequestParam("regionCodes") String regionCodes) {
        return transOrganizeService.updateTransOrganize(transOrganize, regionCodes);
    }


}

