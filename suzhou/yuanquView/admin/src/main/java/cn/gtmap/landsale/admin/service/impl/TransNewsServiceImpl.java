package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.service.TransFileService;
import cn.gtmap.landsale.service.TransNewsService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by www on 2015/9/23.
 */
public class TransNewsServiceImpl extends HibernateRepo<TransNews,String> implements TransNewsService {


    @Autowired
    TransFileService transFileService;

    /**
     * 分页列表查询
     * @param title
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransNews> findTransNews(String title, Pageable request) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("newsTitle", title, MatchMode.ANYWHERE));
        }
        criterionList.add(Restrictions.eq("dataFlag", Constants.DataFlagUnDel));
        Page<TransNews> list=find(criteria(criterionList).addOrder(Order.desc("newsReportTime")),request);
        return list;
    }

    /**
     * 查询已发布的新闻
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransNews> findTransNewsDeployed(Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("newsStauts",Constants.DEPLOYED));
        Page<TransNews> list = find(criteria(criterionList).addOrder(Order.desc("newsReportTime")),request);
        return list;
    }

    /**
     * 保存
     * @param transNews
     * @return 返回新闻实体
     */
    @Override
    @Transactional
    public TransNews saveTransNews(TransNews transNews) {
        transNews.setNewsAddTime(new Date());
        transNews.setNewsUpdateTime(new Date());
        return saveOrUpdate(transNews);

    }

    /**
     * 根据newsId获取实体
     * @param newsId 新闻实体id
     * @return 新闻实体
     */
    @Override
    @Transactional(readOnly = true)
    public TransNews getTransNews(String newsId) {
        return get(newsId);
    }

    /**
     * 硬删除实体
     * @param newsIds 多个实体id
     */
    @Override
    @Transactional
    public void deleteTransNews(Collection<String> newsIds) {
        deleteByIds(newsIds);
        transFileService.deleteFilesByKey(newsIds);

    }

}
