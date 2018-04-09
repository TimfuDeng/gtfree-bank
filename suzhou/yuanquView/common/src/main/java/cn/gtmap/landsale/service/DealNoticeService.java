package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.DealNotice;

import java.util.Collection;

/**
 * Created by trr on 2015/11/3.
 */
public interface DealNoticeService {


    /**
     * 根据noticeId获取成交公告对象
     * @param noticeId
     * @return
     */
    public DealNotice getNotice(String noticeId);

    /**
     * 获取所有的成交公告对象
     * @param request
     * @param title
     * @return
     */
    public Page<DealNotice> findAllDealNotices(Pageable request,String title);

    /**
     * 根据发布状态查询成交公告
     * @param request
     * @param deployStatus
     * @return
     */
    public Page<DealNotice> findAllDealNoticesByStatus(Pageable request,int deployStatus);
}
