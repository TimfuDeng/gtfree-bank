package cn.gtmap.landsale.admin.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransCrgg;

import java.util.Collection;

/**
 * 导入出让公告服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public interface CrggServiceSz {

    /**
     * 分页形式查询出让公告
     * @param where 条件
     * @param request 分页请求对象
     * @return 出让公告列表
     */
    public Page<TransCrgg> findTransCrgg(String where,Pageable request) throws Exception;

    /**
     * 查询出让公告对象
     * @param ggId 出让公告Id
     * @return 出让公告对象
     */
    public TransCrgg findTransCrgg(String ggId) throws Exception;

    /**
     * 根据出让公告Id下载获取并导入
     * @param gyggIds
     */
    public void getTransCrgg(Collection<String> gyggIds) throws Exception;
}
