package cn.gtmap.landsale.admin.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganize;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 组织服务
 * @author zsj
 * @version v1.0, 2017/9/12
 */
@FeignClient(name = "core-server")
public interface TransOrganizeClient {

    /**
     * 获取组织 分页服务
     * @param organizeName 组织名称
     * @param regionCodes 所属行政区
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/organize/findTransOrganizePage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransOrganize> findTransOrganizePage(@RequestParam(value = "organizeName", required = false) String organizeName, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable);

    /**
     * 获取组织 列表服务
     * @param organizeName 组织名称
     * @param regionCode 组织代码
     * @return
     */
    @RequestMapping(value = "/organize/findTransOrganizeList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransOrganize> findTransOrganizeList(@RequestParam(value = "organizeName", required = false) String organizeName, @RequestParam(value = "regionCode", required = false) String regionCode);

    /**
     * 通过角色 获取组织 列表服务
     * @param roleId 角色编号
     * @return
     */
    @RequestMapping(value = "/organize/findTransOrganizeByRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransOrganize> findTransOrganizeByRole(@RequestParam(value = "roleId") String roleId);

    /**
     * 根据组织Id 获取组织对象
     * @param organizeId 组织Id
     * @return
     */
    @RequestMapping(value = "/organize/getTransOrganizeById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransOrganize getTransOrganizeById(@RequestParam("organizeId") String organizeId);

    /**
     * 保存组织
     * @param transOrganize 组织对象
     * @param regionCodes 行政区Codes
     * @return
     */
    @RequestMapping(value = "/organize/saveTransOrganize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransOrganize> saveTransOrganize(@RequestBody TransOrganize transOrganize, @RequestParam("regionCodes") String regionCodes);

    /**
     * 修改组织
     * @param transOrganize 组织对象
     * @param regionCodes 行政区Codes
     * @return
     */
    @RequestMapping(value = "/organize/updateTransOrganize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransOrganize> updateTransOrganize(@RequestBody TransOrganize transOrganize, @RequestParam("regionCodes") String regionCodes);

    /**
     * 删除组织
     * @param transOrganize 组织
     * @return
     */
    @RequestMapping(value = "/organize/deleteTransOrganize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransOrganize(@RequestBody TransOrganize transOrganize);

}
