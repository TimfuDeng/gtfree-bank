package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.SysExtend;

import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public interface SysExtendService {

    public void saveSysExtendList(List<SysExtend> sysExtend);

    public void saveSysExtend(SysExtend sysExtend);

    public SysExtend findSysExtend(String refId,String filedNo);
}
