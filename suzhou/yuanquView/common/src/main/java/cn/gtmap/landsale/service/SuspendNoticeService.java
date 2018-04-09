package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.SuspendNotice;

import java.util.Collection;

/**
 * Created by trr on 2015/11/2.
 */
public interface SuspendNoticeService {
    /**
     * 保存中止公告
     * @param suspendNotice
     * @return
     */
    public SuspendNotice save(SuspendNotice suspendNotice);

    /**
     * 删除中止公告
     * @param noticeId
     */
    public void delete(Collection<String> noticeId);

    /**
     * 根据noticeId获取中止公告对象
     * @param noticeId
     * @return
     */
    public SuspendNotice getNotice(String noticeId);

    /**
     * 获取所有的中止公告对象
     * @param request
     * @param title
     * @return
     */
    public Page<SuspendNotice> findAllSuspendNotices(Pageable request,String title);

    /**
     * 根据发布状态查询中止公告
     * @param request
     * @param deployStatus
     * @return
     */
    public Page<SuspendNotice> findAllSuspendNoticesByStatus(Pageable request,int deployStatus);
}
