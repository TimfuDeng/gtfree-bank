package cn.gtmap.landsale.security;

import cn.gtmap.egovplat.core.util.RequestUtils;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.Constants;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jibo1_000 on 2015/5/10.
 */
public class SecUtil {

    final static String USER_ID_KEY ="_USER_ID";
    final static String USER_NAME_KEY ="_USER_VIEW_NAME";
    final static String USER_REGIONS="_USER_REGION_CODE";
    final static String USER_RESOURCES = "_USER_RESOURCE_PRIVILEGE";

    final static PathMatcher pathMatcher = RequestUtils.PATH_MATCHER;
    /**
     *判断是否登录
     * @return
     */
    public static boolean isLogin(){
        return StringUtils.isNoneBlank(getLoginUserId());
    }

    /**
     * 判断登录的userId
     * @return
     */
    public static String getLoginUserId(){
        return SecurityContext.getContext().getAttr().getAttr(USER_ID_KEY);
    }

    public static String getLoginUserViewName(){
        return SecurityContext.getContext().getAttr().getAttr(USER_NAME_KEY);
    }
    /**
     * 将登录信息放入线程
     * @param userId
     */
    public static void setLoginUserIdToLocal(String userId,String viewName){
        SecurityContext.getContext().getAttr().setAttr(USER_ID_KEY,userId);
        SecurityContext.getContext().getAttr().setAttr(USER_NAME_KEY, viewName);
    }

    public static void setLoginUserIdToSession(HttpServletRequest request,TransUser transUser){
        WebUtils.setSessionAttribute(request, USER_ID_KEY, transUser);
    }

    public static boolean login4Session(HttpServletRequest request){
        Object userIdObj=WebUtils.getSessionAttribute(request, USER_ID_KEY);
        if (userIdObj!=null) {
            TransUser transUser=(TransUser)userIdObj;
            setLoginUserToLocal(transUser);
            return true;
        }else {
            clearContextAttr(false);
            return false;
        }
    }

    public static void setLoginUserToLocal(TransUser transUser){
        SecurityContext.getContext().getAttr().setAttr(USER_ID_KEY,transUser.getUserId());
        SecurityContext.getContext().getAttr().setAttr(USER_NAME_KEY,transUser.getViewName());
        String privileges = transUser.getPrivilege();
        if (StringUtils.isNotBlank(privileges)) {
            Map privilegeMap = JSON.parseObject(privileges);
            if(privilegeMap.containsKey("regions"))
                SecurityContext.getContext().getAttr().setAttr(USER_REGIONS,Sets.newHashSet((List) privilegeMap.get("regions")));

            if(privilegeMap.containsKey("resources"))
                SecurityContext.getContext().getAttr().setAttr(USER_RESOURCES,privilegeMap.get("resources"));

        }
    }

    public static void logout4Session(HttpServletRequest request){
        WebUtils.setSessionAttribute(request, USER_ID_KEY, null);
        clearContextAttr(true);
    }

    private static void clearContextAttr(boolean isClearAll){
        SecurityContext.getContext().getAttr().removeAttr(USER_ID_KEY);
        SecurityContext.getContext().getAttr().removeAttr(USER_NAME_KEY);
        SecurityContext.getContext().getAttr().removeAttr(USER_REGIONS);
        SecurityContext.getContext().getAttr().removeAttr(USER_RESOURCES);
        if(isClearAll)
            SecurityContext.clearContext();
    }

    public static boolean isAdmin() {
        return Constants.USER_ADMIN_ID.equals(getLoginUserId());
    }

    public static boolean isPermitted(String path, Collection<String> operations){
        if (CollectionUtils.isEmpty(operations)) {
            return false;
        }
        Set<String> userOps = getPermittedOperations(path);
        for (String op : operations) {
            if (!userOps.contains(op)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPermitted(String path,String resourceName, Collection<String> operations){
        if (CollectionUtils.isEmpty(operations)) {
            return false;
        }
        Set<String> userOps = getPermittedOperations(path,resourceName);
        for (String op : operations) {
            if (!userOps.contains(op)) {
                return false;
            }
        }
        return true;
    }

    public static Set<String> getPermittedOperations(String path,String resourceName) {
        Set<String> operations =Sets.newHashSet();
        String resources = SecurityContext.getContext().getAttr().getAttr(USER_RESOURCES, String.class, null);
        if(StringUtils.isNotBlank(resources)){
            List<Map> resourcePrivileges = JSON.parseObject(resources,List.class);
            for(Map privilege:resourcePrivileges){
                if(StringUtils.isBlank(resourceName)){
                    if(pathMatcher.match(String.valueOf(privilege.get("url")),path))
                        operations.add(String.valueOf(privilege.get("operation")));
                }else{
                    if(pathMatcher.match(String.valueOf(privilege.get("url")),path)&&resourceName.equals(privilege.get("name")))
                        operations.add(String.valueOf(privilege.get("operation")));
                }

            }
        }
        return operations;
    }

    public static Set<String> getPermittedOperations(String path) {
        return getPermittedOperations(path,null);
    }

    public static Set<String> getPermittedRegions(){
        String regions = SecurityContext.getContext().getAttr().getAttr(USER_REGIONS,String.class,null);
        if(StringUtils.isBlank(regions))
            return Sets.newHashSet("-999");
        else{
            Set<String> result = JSON.parseObject(regions,Set.class);
            if(result==null||result.isEmpty())
                return Sets.newHashSet("-999");
            else
                return result;
        }
    }
}
