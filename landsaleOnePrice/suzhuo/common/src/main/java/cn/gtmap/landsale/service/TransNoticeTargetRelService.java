package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransNoticeTargetRel;

import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public interface TransNoticeTargetRelService {

    public void saveTransNoticeTargetRel(TransNoticeTargetRel transNoticeTargetRel);

    public List<TransNoticeTargetRel> findTransNoticeTargetRelBynoticeId(String noticeId);

    /**
     * 通过标的id寻找公告
     * @param targetId
     * @return
     */
    public TransNoticeTargetRel findTransNoticeTargetRelBytargetId(String targetId);
}
