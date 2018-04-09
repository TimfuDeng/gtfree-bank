package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.NotificationService;
import cn.gtmap.landsale.model.TransNotification;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by u on 2015/9/24.
 */
public class NotificationServiceImpl  extends HibernateRepo<TransNotification,String> implements NotificationService {
    /**
     * 保存通知
     * @param transNote 通知对象
     */
    @Override
    @Transactional
    public TransNotification saveNote(TransNotification transNote){
       return saveOrUpdate(transNote);
    }

    /**
     * 删除通知
     * @param noteIds 通知ids
     */
    @Override
    @Transactional
    public void deleteNote(Collection<String> noteIds){
        deleteByIds(noteIds);
    }

    /**
     * 分页形式查询通知
     * @param where 查询条件
     * @param request 分页请求对象
     * @return 通知列表
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransNotification> findNote(String where , Pageable request){
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(where))
            criterionList.add(Restrictions.like("noteTitle", where, MatchMode.ANYWHERE));
        return find(criteria(criterionList),request);
    }

    /**
     * 查询通知对象
     * @param noteId 通知id
     * @return 通知对象
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public TransNotification findNote(String noteId) throws EntityNotFoundException {
        return get(noteId);
    }
}
