package cn.gtmap.landsale.common.web.freemarker;

import cn.gtmap.landsale.common.model.OneTarget;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.model.YHResult;
import cn.gtmap.landsale.common.register.OneTargetClient;
import cn.gtmap.landsale.common.register.TransCrggClient;
import cn.gtmap.landsale.common.register.TransUserClient;
import cn.gtmap.landsale.common.register.YHClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lq on 2017/11/17.
 */
@Component
public class ResultUtil {

    @Autowired
    OneTargetClient oneTargetClient;

    @Autowired
    YHClient yhClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransUserClient transUserClient;

    public YHResult getYHResultByResourceId(String resourceId) {
        if (StringUtils.isNotBlank(resourceId)) {
            return yhClient.getYHResultByResourceId(resourceId);
        }
        return null;
    }

    public OneTarget getOneTargetByTransResourceId(String resourceId) {
        if (StringUtils.isNotBlank(resourceId)) {
            return oneTargetClient.getOneTargetByTransResource(resourceId);
        }
        return null;
    }

    public OneTarget getOneTargetById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return oneTargetClient.getOneTarget(id);
        }
        return null;
    }

    public TransCrgg getTransCrgg(String id) {
        if (StringUtils.isNotBlank(id)) {
            return transCrggClient.getTransCrgg(id);
        }
        return null;
    }

    public TransUser getTransUser(String id) {
        if (StringUtils.isNotBlank(id)) {
            return transUserClient.getTransUserById(id);
        }
        return null;
    }

}
