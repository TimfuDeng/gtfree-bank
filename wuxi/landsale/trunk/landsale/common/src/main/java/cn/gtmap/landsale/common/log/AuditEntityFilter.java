package cn.gtmap.landsale.common.log;

import cn.gtmap.egovplat.core.entity.EntityFilter;
import cn.gtmap.landsale.common.Constants;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/12
 */
public class AuditEntityFilter<E> implements EntityFilter<E> {
    List<Class> auditClasses = Lists.newArrayList();

    public void setAuditClasses(List<Class> auditClasses) {
        this.auditClasses = auditClasses;
    }

    @Override
    public boolean accept(Class<?> clazz) {
        return auditClasses.contains(clazz)?true:false;
    }

    @Override
    public <S extends E> S prepare(S entity) {
        return entity;
    }

    @Override
    public List<E> prepare(List<E> entities) {
        return entities;
    }

    @Override
    public E beforeSave(E entity) {
        return entity;
    }

    @Override
    @AuditServiceLog(category = Constants.LogCategory.DATA_SAVE,producer = Constants.LogProducer.ADMIN,
            description = "保存数据")
    public E afterSave(E entity) {
        return entity;
    }

    @Override
    public E beforeDelete(E entity) {
        return entity;
    }

    @Override
    @AuditServiceLog(category = Constants.LogCategory.DATA_SAVE,producer = Constants.LogProducer.ADMIN,
            description = "删除数据")
    public E afterDelete(E entity) {
        return entity;
    }
}
