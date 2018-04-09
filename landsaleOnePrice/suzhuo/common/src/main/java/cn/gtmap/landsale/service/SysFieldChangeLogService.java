package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.SysFieldChangeLog;

/**
 * Created by trr on 2016/8/12.
 */
public interface SysFieldChangeLogService {

    /**
     * 得到唯一中止地块，时间
     * @param refId
     * @return
     */
    SysFieldChangeLog getSysFieldChangeLogByRefId(String refId);
}
