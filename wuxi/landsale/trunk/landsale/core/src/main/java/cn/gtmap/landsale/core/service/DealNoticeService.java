package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.DealNotice;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 成交公示
 * @author cxm
 * @version v1.0, 2017/11/14
 */
public interface DealNoticeService {

    /**
     * 获取所有的成交公告对象
     * @param request
     * @param title
     * @return
     */
    public Page<DealNotice> findAllDealNotices(Pageable request,String title);

    /**
     * 根据noticeId获取成交公告对象
     * @param noticeId
     * @return
     */
    public DealNotice getNotice(String noticeId);

    /**
     * 保存成交公告
     * @param dealNotice
     * @return
     */
    public ResponseMessage<DealNotice> saveDealNotice(DealNotice dealNotice);


    /**
     * 删除成交公告
     * @param noticeIds
     * @return
     */
    public ResponseMessage<DealNotice> deleteDealNotice(String noticeIds);

    /**
     * 根据地块编号查询成交公告
     * @param resourceCode
     * @return
     */
    public List<DealNotice> findDealNoticeByResourceCode(String resourceCode);

}
