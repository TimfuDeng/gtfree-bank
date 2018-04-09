package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;

import java.util.Date;
import java.util.List;

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
     * 保存出让公告对象
     * @param transCrgg 出让公告对象
     * @param materialIds 附件id
     * @return  新的出让公告对象
     */
    ResponseMessage<TransCrgg> saveTransCrgg(TransCrgg transCrgg, String materialIds);

    /**
     * 更新 出让公告对象
     * @param transCrgg 出让公告对象
     * @return  新的出让公告对象
     */
    ResponseMessage<TransCrgg> updataTransCrgg(TransCrgg transCrgg);

    /**
     * 删除出让公告
     * @param ggIds 公告对象Id
     * @return
     */
    ResponseMessage deleteTransCrgg(String ggIds);


    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param request 分页请求对象
     * @param regionCodes 行政区代码
     * @return 分页列表
     */
    Page<TransCrgg> findTransCrgg(String title, String regionCodes, Pageable request);

    /**
     * 根据公告类型查找
     * @param title
     * @param regionCodes
     * @param request
     * @param crggStatus
     * @return
     */
    Page<TransCrgg> findTransCrggByStatus(String title, String regionCodes, Pageable request, int crggStatus);


    /**
     * 获取第一条出让公告
     * @return
     */
    TransCrgg findTransCrggFirst();

    /**
     * 根据类型获取出让公告
     * @param ggType 公告Id
     * @param regionCodes 行政区代码
     * @param request
     * @param title 公告标题
     * @return 出让公告对象
     * @throws EntityNotFoundException
     */
    Page<TransCrgg> getTransCrggByGgType(String title, String regionCodes,int ggType,Pageable request) throws EntityNotFoundException;

    /**
     * 获取中止（终止）公告
     * @param request
     * @param title 标题
     * @param regionCodes 行政区代码
     * @return 出让公告对象
     * @throws EntityNotFoundException
     */
    Page<TransCrgg> findSuspendNotice(String title, String regionCodes,Pageable request) throws EntityNotFoundException ;

    /**
     * 根据公告类型和地块编号查询，用于获取中止、终止公告
     * @param afficheType
     * @param resourceCode
     * @return
     * @throws EntityNotFoundException
     */
    List<TransCrgg> findTransCrggByGGTypeAndResourceCode(int afficheType, String resourceCode) throws EntityNotFoundException;

    /**
     * 条件查询出让公告
     * @param ggNum 公告编号
     * @param afficheType 公告类型
     * @param request
     * @param endIime 截止时间
     * @param regionCodes 行政区代码
     * @param startTime 开始时间
     * @return
     * @throws EntityNotFoundException
     */
    public Page<TransCrgg> searchTransCrgg(String ggNum, String afficheType, String startTime, String endIime,String regionCodes, Pageable request) throws EntityNotFoundException;
    /**
     * 删除终止公告
     * @param ggIds 公告Id
     * 不删除关联地块及信息
     * @return
     */
    ResponseMessage deleteSuspendCrgg(String ggIds);

}
