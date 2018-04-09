package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneTarget;
import cn.gtmap.landsale.common.model.ResponseMessage;

/**
 * @author lq on 2017/11/17.
 */
public interface OneTargetService {

    /**
     * 获取 OneTarget
     * @param id
     * @return
     */
    OneTarget getOneTarget(String id);

    /**
     * 通过 resourceId 获取 OneTarget
     * @param transResourceId
     * @return
     */
    OneTarget getOneTargetByTransResource(String transResourceId);

    /**
     * 保存
     * @param oneTarget
     * @return
     */
    ResponseMessage<OneTarget> saveOneTarget(OneTarget oneTarget);

    /**
     * 统一事务,修改地块状态,插入出价记录
     * @param oneTarget
     * @param offerType
     * @param logId
     * @param offerId
     * @return
     */
    ResponseMessage<OneTarget> saveOneTargetAndUpdateResource(OneTarget oneTarget,String offerType,String logId,String offerId);

    /**
     * 分页获取oneTarget列表
     * @param title 标题
     * @param page
     * @return
     */
    Page<OneTarget> findOneTargetPage(String title, Pageable page);

    /**
     * 分页获取oneTarget列表
     * @param title
     * @param page
     * @param isStop
     * @return
     */
    Page<OneTarget> findOneTargetPageByIsStop(String title, Pageable page, Integer isStop);

    /**
     * 根据用户id获取一次报价报名记录
     * @param title
     * @param transUserId
     * @param page
     * @param isStop
     * @return
     */
    Page<OneTarget> findMyOneApplyList(String title, String transUserId, Pageable page, Integer isStop);
}
