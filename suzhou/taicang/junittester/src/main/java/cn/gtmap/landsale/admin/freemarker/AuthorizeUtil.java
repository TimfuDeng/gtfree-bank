package cn.gtmap.landsale.admin.freemarker;

import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.AuthorizationService;
import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/15
 */
public class AuthorizeUtil {
    public boolean isPermitted(String path,String resourceName){
        return SecUtil.isAdmin()?true:SecUtil.isPermitted(path,resourceName, Sets.newHashSet("view"));
    }
}
