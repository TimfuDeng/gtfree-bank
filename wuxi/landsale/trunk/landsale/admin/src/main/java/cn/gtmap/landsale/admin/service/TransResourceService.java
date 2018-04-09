package cn.gtmap.landsale.admin.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.web.ResourceQueryParam;

import java.util.List;


/**
 * 底价管理
 * @author cxm
 * @version v1.0, 2017/11/06
 */
public interface TransResourceService {
    /**
     * 查询勾选了底价的地块
     * @param queryParam
     * @param regionCodes
     * @param request
     * @return
     */
    Page<TransResource> findTransResourcesByMinOffer(ResourceQueryParam queryParam,String regionCodes, Pageable request);

    /**
     * 获得正在交易的地块，编辑状态为发布的
     * @return
     */
    List<TransResource> getTransResourcesOnRelease();

    /**
     * 更新地块状态
     * @param resource
     * @param status
     * @return
     */
    ResponseMessage<TransResource> saveTransResourceStatus(TransResource resource, int status);

}
