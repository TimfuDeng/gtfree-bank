package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransInteractCommunion;

import java.util.Collection;

/**
 * Created by www on 2015/9/29.
 */
public interface TransInteractCommunionService {


    /**
     * 列表查询互相交流对象
     * @param title 来信标题
     * @param request
     * @return 互相交流page对象
     */
    public Page<TransInteractCommunion> findTransInteractCommunionPage(String title,Pageable request);

    /**
     * 保存或者更新互相交流对象
     * @param transInteractCommunion 互相交流对象
     * @return 互相交流对象
     */
    public TransInteractCommunion saveTransInteractCommunion(TransInteractCommunion transInteractCommunion);

    /**
     * 删除相互交流对象
     * @param communionIds 多个id集合
     */
    public void deleteTransInteractCommunion(Collection<String> communionIds);

    /**
     * 根据ID返回一个TransInteractCommunion
     * @param communionId  TransInteractCommunion的id
     * @return  互相交流对象
     */
    public TransInteractCommunion getTransInteractCommunionById(String communionId);

    /**
     * 得到编号序列
     * @return 返回序列
     */
    public String getNextPublicNo();

    /**
     * 获取未审核的交流对象
     * @param request
     * @return
     */
    public Page<TransInteractCommunion> findApprovedComm(Pageable request);

}
