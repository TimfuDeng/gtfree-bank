package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceInfo;

/**
 * 地块扩展信息Service
 * @author zsj
 * @version v1.0, 2017/10/26
 */
public interface TransResourceInfoService {

    /**
     * 通过扩展信息Id 获取 TransResourceInfo
     * @param infoId
     * @return TransResourceInfo
     */
    TransResourceInfo getTransResourceInfo(String infoId);

    /**
     * 通过地块Id 获取 TransResourceInfo
     * @param resourceId
     * @return TransResourceInfo
     */
    TransResourceInfo getTransResourceInfoByResourceId(String resourceId);

    /**
     * 保存 TransResourceInfo
     * @param transResourceInfo
     * @return
     */
    ResponseMessage<TransResourceInfo> saveTransResourceInfo(TransResourceInfo transResourceInfo);

    /**
     * 保存 TransResourceInfo
     * @param transResourceInfo
     * @return
     */
    ResponseMessage<TransResourceInfo> updateTransResourceInfo(TransResourceInfo transResourceInfo);

    /**
     * 通过地块编号 删除所属的地块扩展信息
     * @param resourceId
     * @return
     */
    ResponseMessage deleteTransResourceInfo(String resourceId);

}
