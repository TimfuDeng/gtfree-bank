package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.TransResourceLock;
import org.springframework.stereotype.Service;

/**
 * @author liushaoshuai on 2017/7/21.
 */
@Service
public interface TransResourceLockService {

    /**
     * 根据地块id获取
     * @param resourceId
     * @return
     */
    TransResourceLock getResourceLock4Update(String resourceId);

    /**
     * 保存
     * @param transResourceLock
     * @return
     */
    TransResourceLock save(TransResourceLock transResourceLock);
}
