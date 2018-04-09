package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.MaterialCrgg;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.core.service.MaterialCrggService;
import cn.gtmap.landsale.core.service.TransCrggService;
import cn.gtmap.landsale.core.service.TransFileService;
import cn.gtmap.landsale.core.service.TransResourceService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 网上交易出让公告服务
 * @author jiff on 14/12/21.
 */
@Service
public class TransCrggServiceImpl extends HibernateRepo<TransCrgg, String> implements TransCrggService {


    @Autowired
    TransFileService transFileService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    MaterialCrggService materialCrggService;

    /**
     * 根据Id获取出让公告
     * @param ggId 公告Id
     * @return 出让公告对象
     */
    @Override
    @Transactional(readOnly = true)
    public TransCrgg getTransCrgg(String ggId) throws EntityNotFoundException {
        return get(ggId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransCrgg getTransCrggByGyggId(String gyggId) {
        return StringUtils.isNotBlank(gyggId)?get(criteria(Restrictions.eq("gyggId", gyggId))):null;
    }

    /**
     * 保存出让公告对象
     * @param transCrgg 出让公告对象
     * @return  新的出让公告对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransCrgg> saveTransCrgg(TransCrgg transCrgg, String materialIds) {
        transCrgg = saveOrUpdate(transCrgg);
        // 通过公告Id 删除原有关系
        materialCrggService.deleteMaterialCrggList(transCrgg.getGgId());
        // 如果存在新关系 添加
        if(StringUtils.isNotBlank(materialIds)){
            String materialId = null;
            String[] id = materialIds.split(",");
            for(int i = 0;i < id.length;i++){
                materialId = id[i];
                MaterialCrgg materialCrgg = new MaterialCrgg();
                materialCrgg.setCrggId(transCrgg.getGgId());
                materialCrgg.setMaterialId(materialId);
                materialCrggService.saveMaterialCrgg(materialCrgg);
            }
        }
        return new ResponseMessage(true, transCrgg);
    }

    /**
     * 更新 出让公告对象
     * @param transCrgg 出让公告对象
     * @return  新的出让公告对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransCrgg> updataTransCrgg(TransCrgg transCrgg) {
        transCrgg = merge(transCrgg);
        return new ResponseMessage(true, transCrgg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransCrgg(String ggIds) {
        transResourceService.deleteTransResourceByGgId(ggIds);
        transFileService.deleteFilesByKey(Arrays.asList(ggIds.split(",")));
        deleteByIds(ggIds.split(","));
        return new ResponseMessage(true);
    }


    /**
     * 以分页的形式获取出让公告列表,排除中止公告
     * @param title 公告标题
     * @param request 分页请求对象
     * @return 分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> findTransCrgg(String title, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("ggTitle", title, MatchMode.ANYWHERE));
        }
        if(StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        //排除中止公告
        criterionList.add(Restrictions.ne("afficheType",Constants.AFFICHE_TYPE_ZZ));
        //排除终止公告
        criterionList.add(Restrictions.ne("afficheType",Constants.AFFICHE_TYPE_ZZ_2));
        return find(criteria(criterionList).addOrder(Order.desc("ggBeginTime")), request);
    }

    /*
    排除中止公告
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> findTransCrggByStatus(String title, String regionCodes, Pageable request, int crggStatus) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("crggStauts", crggStatus));
        if(StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("ggTitle", title, MatchMode.ANYWHERE));
        }
        if(StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        //排除中止公告
        criterionList.add(Restrictions.ne("afficheType",Constants.AFFICHE_TYPE_ZZ));
        //排除终止公告
        criterionList.add(Restrictions.ne("afficheType",Constants.AFFICHE_TYPE_ZZ_2));
        return find(criteria(criterionList).addOrder(Order.desc("ggBeginTime")), request);
    }

    @Override
    @Transactional
    public TransCrgg findTransCrggFirst() {
        String sql = "select * from (select t.* from TRANS_CRGG  t order by t.gg_begin_time desc) where rownum=1";
        return (TransCrgg)sql(sql).addEntity(TransCrgg.class).uniqueResult();

    }

    /**
     * 根据类型获取出让公告
     * @param ggType 公告类型
     * @return 出让公告对象
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> getTransCrggByGgType(String title, String regionCodes,int ggType,Pageable request) throws EntityNotFoundException {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("ggTitle", title, MatchMode.ANYWHERE));
        }

            criterionList.add(Restrictions.eq("afficheType", ggType));
        if(StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        return find(criteria(criterionList).addOrder(Order.desc("postDate")).addOrder(Order.desc("ggBeginTime")), request);
    }

    /**
     * 获取中止（终止）公告
     * @return 出让公告对象
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> findSuspendNotice(String title, String regionCodes,Pageable request) throws EntityNotFoundException {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("ggTitle", title, MatchMode.ANYWHERE));
        }

        criterionList.add(Restrictions.or(Restrictions.eq("afficheType", Constants.AFFICHE_TYPE_ZZ_2),Restrictions.eq("afficheType", Constants.AFFICHE_TYPE_ZZ)));
        if(StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        return find(criteria(criterionList).addOrder(Order.desc("postDate")).addOrder(Order.desc("ggBeginTime")), request);
    }

    /**
     * 根据公告类型和地块编号查询，用于获取中止、终止公告
     * @param afficheType
     * @param resourceCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransCrgg> findTransCrggByGGTypeAndResourceCode(int afficheType, String resourceCode) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(afficheType > -1) {
            criterionList.add(Restrictions.eq("afficheType", afficheType));
        }
        if(StringUtils.isNotBlank(resourceCode)) {
            criterionList.add(Restrictions.eq("resourceCode", resourceCode));
        }
        return list(criteria(criterionList).addOrder(Order.desc("postDate")));
    }

    /**
     * 条件查询出让公告
     * @param ggNum
     * @param request
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> searchTransCrgg(String ggNum, String afficheType, String startTime,String endIime, String regionCodes, Pageable request) throws EntityNotFoundException {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(ggNum)) {
            criterionList.add(Restrictions.like("ggNum", ggNum, MatchMode.ANYWHERE));
        }
        if(StringUtils.isNotBlank(startTime)) {
            criterionList.add(Restrictions.ge("ggBeginTime",new Date(Long.valueOf(startTime))));
        }
        if(StringUtils.isNotBlank(endIime)) {
            criterionList.add(Restrictions.le("ggEndTime",new Date(Long.valueOf(endIime))));
        }
        if(StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        if(StringUtils.isNotBlank(afficheType)) {
            criterionList.add(Restrictions.eq("afficheType", Integer.valueOf(afficheType)));
        }
        //发布状态
        criterionList.add(Restrictions.eq("crggStauts", 1));
        return find(criteria(criterionList).addOrder(Order.desc("postDate")).addOrder(Order.desc("ggBeginTime")), request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteSuspendCrgg(String ggIds) {
        deleteByIds(ggIds.split(","));
        return new ResponseMessage(true);
    }

}
