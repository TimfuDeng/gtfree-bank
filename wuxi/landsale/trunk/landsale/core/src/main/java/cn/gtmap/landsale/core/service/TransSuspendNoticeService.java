package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransSuspendNotice;

/**
 * 中止公告
 * @author zsj
 * @version v1.0, 2017/11/3x
 */
public interface TransSuspendNoticeService {

    /**
     * 保存中止公告
     * @param transSuspendNotice
     * @return
     */
    public ResponseMessage<TransSuspendNotice> saveSuspendNotice(TransSuspendNotice transSuspendNotice);

    /**
     * 删除中止公告
     * @param noticeIds
     * @return
     */
    public ResponseMessage deleteSuspendNotice(String noticeIds);

    /**
     * 根据noticeId获取中止公告对象
     * @param noticeId
     * @return
     */
    public TransSuspendNotice getNotice(String noticeId);

    /**
     * 获取所有的中止公告对象
     * @param request
     * @param title
     * @param regionCodes
     * @return
     */
    public Page<TransSuspendNotice> findAllSuspendNotices(Pageable request, String title, String regionCodes);

    /**
     * 根据发布状态查询中止公告
     * @param request
     * @param deployStatus
     * @return
     */
    public Page<TransSuspendNotice> findAllSuspendNoticesByStatus(Pageable request, int deployStatus);

}
