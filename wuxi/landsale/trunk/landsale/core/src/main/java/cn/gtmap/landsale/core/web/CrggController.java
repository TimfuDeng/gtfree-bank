package cn.gtmap.landsale.core.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.core.service.TransCrggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 出让公告
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@RestController
@RequestMapping("/crgg")
public class CrggController {

    @Autowired
    TransCrggService transCrggService;

    /**
     * 根据Id获取出让公告
     * @param ggId 公告Id
     * @return 出让公告对象
     */
    @RequestMapping("/getTransCrgg")
    public TransCrgg getTransCrgg(@RequestParam("ggId") String ggId) {
        return transCrggService.getTransCrgg(ggId);
    }

    /**
     * 根据 导入供应公告Id获取出让公告
     * @param gyggId 导入公告Id
     * @return 出让公告对象
     */
    @RequestMapping("/getTransCrggByGyggId")
    public TransCrgg getTransCrggByGyggId(@RequestParam("gyggId") String gyggId) {
        return transCrggService.getTransCrggByGyggId(gyggId);
    }

    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param regionCodes 行政区编号
     * @param request 分页请求对象
     * @return 分页列表
     */
    @RequestMapping("/findTransCrgg")
    public Page<TransCrgg> findTransCrgg(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable request) {
        return transCrggService.findTransCrgg(title, regionCodes, request);
    }

    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param regionCodes 行政区编号
     * @param request 分页请求对象
     * @param crggStatus 公告状态
     * @return 分页列表
     */
    @RequestMapping("/findTransCrggByStatus")
    public Page<TransCrgg> findTransCrggByStatus(@RequestParam("title") String title, @RequestParam("regionCodes") String regionCodes, @RequestBody Pageable request, @RequestParam("crggStatus") int crggStatus) {
        return transCrggService.findTransCrggByStatus(title, regionCodes, request, crggStatus);
    }

    /**
     * 更新 出让公告对象
     * @param transCrgg 出让公告对象
     * @return  新的出让公告对象
     */
    @RequestMapping("/updataTransCrgg")
    public ResponseMessage<TransCrgg> updataTransCrgg(@RequestBody TransCrgg transCrgg) {
        return transCrggService.updataTransCrgg(transCrgg);
    }


    /**
     * 保存出让公告对象
     * @param transCrgg 出让公告对象
     * @return  新的出让公告对象
     */
    @RequestMapping("/saveTransCrgg")
    public ResponseMessage<TransCrgg> saveTransCrgg(@RequestBody TransCrgg transCrgg, @RequestParam(value = "materialIds", required = false) String materialIds) {
        return transCrggService.saveTransCrgg(transCrgg, materialIds);
    }

    /**
     * 删除出让公告对象
     * @param ggIds 公告编号
     * @return
     */
    @RequestMapping("/deleteTransCrgg")
    public ResponseMessage deleteTransCrgg(@RequestParam("ggIds") String ggIds) {
        return transCrggService.deleteTransCrgg(ggIds);
    }

    /**
     * 根据类型获取出让公告
     * @param ggType 公告Id
     * @return 出让公告对象
     */
    @RequestMapping("/findTransCrggByGgType")
    public Page<TransCrgg> findTransCrggByGgType(@RequestParam(value = "title", required = false) String title,@RequestParam(value = "regionCodes", required = false) String regionCodes,@RequestParam(value = "ggType") int ggType,@RequestBody Pageable request) {
        return transCrggService.getTransCrggByGgType(title,regionCodes,ggType,request);
    }

    /**
     * 获取中止（终止）公告
     * @return 出让公告对象
     */
    @RequestMapping("/findSuspendNotice")
    Page<TransCrgg> findSuspendNotice(@RequestParam(value = "title", required = false) String title,@RequestParam(value = "regionCodes", required = false) String regionCodes,Pageable request){
        return transCrggService.findSuspendNotice(title, regionCodes, request);
    }


    /**
     * 删除终止公告
     * @param ggIds 公告Id
     * 不删除关联地块及信息
     */
    @RequestMapping("/deleteSuspendCrgg")
    public ResponseMessage deleteSuspendCrgg(@RequestParam(value = "ggIds") String ggIds) {
        return transCrggService.deleteSuspendCrgg(ggIds);
    }

    /**
     * 前台条件查询出让公告
     * @param ggNum
     * @param afficheType
     * @param request
     * @return
     * @throws EntityNotFoundException
     */
    @RequestMapping("/searchTransCrgg")
    public Page<TransCrgg> searchTransCrgg(@RequestParam(value = "ggNum",required = false) String ggNum,@RequestParam(value = "afficheType",required = false) String afficheType,@RequestParam(value = "startTime",required = false) String startTime,@RequestParam(value = "endIime",required = false) String endIime,@RequestParam(value = "regionCodes",required = false) String regionCodes, @RequestBody Pageable request) throws EntityNotFoundException {
        return transCrggService.searchTransCrgg(ggNum,afficheType,startTime,endIime,regionCodes,request);
    }

}
