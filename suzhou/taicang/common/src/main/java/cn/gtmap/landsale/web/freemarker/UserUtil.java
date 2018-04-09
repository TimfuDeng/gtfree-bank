package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.model.TransUserApplyInfo;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransUserApplyInfoService;
import cn.gtmap.landsale.service.TransUserService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Jibo on 2015/5/25.
 */
public class UserUtil {

    TransUserService transUserService;

    TransUserApplyInfoService transUserApplyInfoService;

    public void setTransUserService(TransUserService transUserService) {
        this.transUserService = transUserService;
    }

    public void setTransUserApplyInfoService(TransUserApplyInfoService transUserApplyInfoService) {
        this.transUserApplyInfoService = transUserApplyInfoService;
    }

    public String getUserName(String userId){
        TransUser transUser= transUserService.getTransUser(userId);
        return transUser==null? "":transUser.getViewName();
    }

    public boolean isCurrentUser(String userId){
        return StringUtils.isBlank(SecUtil.getLoginUserId())?false: SecUtil.getLoginUserId().equals(userId);
    }

    public TransUser getUser(String userId){
        return transUserService.getTransUser(userId);
    }

    public TransUserApplyInfo getUserInfo(String userId){
        List<TransUserApplyInfo> transUserApplyInfoList= transUserApplyInfoService.getTransUserApplyInfoByUser(userId);
        return transUserApplyInfoList.size()>0?transUserApplyInfoList.get(0):null;
    }
}
