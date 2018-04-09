package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceSon;

import java.util.List;

/**
 * 地块多用途服务
 * @author zsj
 * @version v1.0, 2017/10/31
 */
public interface TransResourceSonService {

    /**
     * 查询列表
     * @param resourceId 地块Id
     * @return
     */
    public List<TransResourceSon> getTransResourceSonList(String resourceId);

    /**
     * 根据地块多用途Id 获取TransResourceSon
     * @param resourceSonId
     * @return
     */
    public TransResourceSon getTransResourceSon(String resourceSonId);

    /**
     * 保存地块多用途
     * @param transResourceSon
     * @return
     */
    public ResponseMessage<TransResourceSon> saveTransResourceSon(TransResourceSon transResourceSon);

    /**
     * 修改地块多用途
     * @param transResourceSon
     * @return
     */
    public ResponseMessage<TransResourceSon> updateTransResourceSon(TransResourceSon transResourceSon);

    /**
     * 根据地块多用途Id 删除
     * @param resourceSonId
     * @return
     */
    public ResponseMessage deleteTransResourceSon(String resourceSonId);

    /**
     * 根据地块Id 删除
     * @param resourceId
     * @return
     */
    public ResponseMessage deleteTransResourceSonByResourceId(String resourceId);

    /**
     * 根据宗地号和地块id查询
     * @param zdCode
     * @param resourceId
     * @return
     */
    public List<TransResourceSon> getTransResourceSons(String zdCode, String resourceId);

}
