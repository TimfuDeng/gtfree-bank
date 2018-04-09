package cn.gtmap.landsale.common.web.freemarker;

import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.model.TransUserUnion;
import cn.gtmap.landsale.common.register.TransUserClient;
import cn.gtmap.landsale.common.register.TransUserUnionClient;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FreeMarker 用户工具类
 *
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@Component
public class UserUtil {

    @Autowired
    TransUserClient userClient;

    @Autowired
    TransUserUnionClient userUnionClient;

//    TransUserApplyInfoService transUserApplyInfoService;

    public String getUserName(String userId) {
        if (StringUtils.isBlank(userId)) {
            return "";
        }
        TransUser transUser = userClient.getTransUserById(userId);
        return transUser == null ? "" : transUser.getViewName();
    }

    public boolean isCurrentUser(String userId) {
        return StringUtils.isBlank(SecUtil.getLoginUserId()) ? false : SecUtil.getLoginUserId().equals(userId);
    }

    public TransUser getUser(String userId) {
        return userClient.getTransUserById(userId);
    }

//    public TransUserApplyInfo getUserInfo(String userId){
//        List<TransUserApplyInfo> transUserApplyInfoList= transUserApplyInfoService.getTransUserApplyInfoByUser(userId);
//        return transUserApplyInfoList.size()>0?transUserApplyInfoList.get(0):null;
//    }

    public List<TransUserUnion> getUserUnionByApplyId(String applyId) {
        return userUnionClient.findTransUserUnion(applyId);
    }

}
