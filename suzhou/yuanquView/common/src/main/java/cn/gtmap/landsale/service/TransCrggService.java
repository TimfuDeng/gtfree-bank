package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.CrggInfo;
import cn.gtmap.landsale.model.TransCrgg;


import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 网上交易出让公告服务
 * Created by jiff on 14/12/21.
 */
public interface TransCrggService {


    /**
     * 根据Id获取出让公告
     * @param ggId 公告Id
     * @return 出让公告对象
     */
    TransCrgg getTransCrgg(String ggId);

    /**
     * 根据土地市场网供应公告GUID查找出让公告对象
     * @param gyggId 供应公告GUID
     * @return
     */
    TransCrgg getTransCrggByGyggId(String gyggId);



    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param request 分页请求对象
     * @param regionCodes 行政区代码
     * @return 分页列表
     */
    Page<TransCrgg> findTransCrgg(String title,Collection<String> regionCodes, Pageable request);


    public List<CrggInfo> findCrggInfo(String title, Collection<String> regionCodes, Pageable request, int crggStauts) ;
    /**
     * 以分页的形式获取给定发布状态的出让公告列表
     * @param title 公告标题
     * @param request 分页请求对象
     * @param regionCodes 行政区代码
     * @return 分页列表
     */
    Page<TransCrgg> findTransCrggByStatus(String title,Collection<String> regionCodes, Pageable request,int crggStauts);



    /**
     *得到发布的所有地块Id
     * @return
     */
    Collection<String> findTransCrggIds();

}
