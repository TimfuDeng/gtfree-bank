package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceInfo;
import cn.gtmap.landsale.core.service.TransResourceInfoService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 地块扩展信息ServiceImpl
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@Service
public class TransResourceInfoServiceImpl extends HibernateRepo<TransResourceInfo, String> implements TransResourceInfoService {

    /**
     * 通过扩展信息Id 获取 TransResourceInfo
     * @param infoId
     * @return TransResourceInfo
     */
    @Override
    @Transactional(readOnly = true)
    public TransResourceInfo getTransResourceInfo(String infoId) {
        return get(infoId);
    }

    /**
     * 通过地块Id 获取 List<TransResourceInfo>
     * @param resourceId
     * @return List<TransResourceInfo>
     */
    @Override
    @Transactional(readOnly = true)
    public TransResourceInfo getTransResourceInfoByResourceId(String resourceId) {
        return StringUtils.isNotBlank(resourceId) ? get(criteria(Restrictions.eq("resourceId", resourceId))) : null;
    }

    /**
     * 保存 TransResourceInfo
     * @param transResourceInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceInfo> saveTransResourceInfo(TransResourceInfo transResourceInfo) {
        transResourceInfo = save(transResourceInfo);
        return new ResponseMessage(true, transResourceInfo);
    }

    /**
     * 修改 TransResourceInfo
     * @param transResourceInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceInfo> updateTransResourceInfo(TransResourceInfo transResourceInfo) {
        transResourceInfo = merge(transResourceInfo);
        return new ResponseMessage(true, transResourceInfo);
    }

    /**
     * 通过地块编号 删除所属的地块扩展信息
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransResourceInfo(String resourceId) {
        TransResourceInfo transResourceInfo = getTransResourceInfoByResourceId(resourceId);
        if (transResourceInfo != null) {
            delete(transResourceInfo);
        }
        return new ResponseMessage(true);
    }
}
