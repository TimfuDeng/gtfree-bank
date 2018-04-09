package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransNotice;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/5.
 */
public interface TransNoticeService {

    Page<TransNotice> findTransNoticeList(String title, Pageable request);

    List<TransNotice> findTransNoticeListByNo(String no);



    public TransNotice saveTransNotice(TransNotice transNotice);

    public TransNotice getTransNotice(String id);

    /**
     * 集合之外的公告
     * @param noticeIds
     * @return
     */
    public List<TransNotice> findTransNoticeListNotInNoticeIds(Collection<String> noticeIds);


}
