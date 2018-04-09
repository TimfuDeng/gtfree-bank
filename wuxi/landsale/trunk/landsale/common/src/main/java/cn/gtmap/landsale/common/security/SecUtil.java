package cn.gtmap.landsale.common.security;

import cn.gtmap.egovplat.core.util.RequestUtils;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransOrganize;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.TransRole;
import cn.gtmap.landsale.common.model.TransUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户登录信息 处理
 * @author zsj
 * @version v1.0, 2017/9/19
 */
public class SecUtil {
    /**
     * session信息
     */
    final static String USER_KEY = "_USER";
    final static String USER_ROLE_KEY = "_USER_ROLE";
    final static String USER_REGIONS_KEY = "_USER_REGION";
    final static String USER_ORGANIZE_KEY = "_USER_ORGANIZE";
    final static String USER_BUTTON_KEY = "_USER_BUTTON";

    /**
     * 缓存信息
     */
    final static String USER_ID_KEY = "_USER_ID";
    final static String USER_NAME_KEY = "_USER_VIEW_NAME";
    final static String USER_REGION_KEYS = "_USER_REGION_CODES";

    final static PathMatcher PATH_MATCHER = RequestUtils.PATH_MATCHER;

    /**
     *判断是否登录
     * @return
     */
    public static boolean isLogin(){
        return StringUtils.isNoneBlank(getLoginUserId());
    }

    public static boolean isAdmin() {
        return Constants.USER_ADMIN_ID.equals(getLoginUserId());
    }

    /**
     * 获取登录的userId
     * @return
     */
    public static String getLoginUserId(){
        return SecurityContext.getContext().getAttr().getAttr(USER_ID_KEY);
    }

    public static String getLoginUserViewName(){
        return SecurityContext.getContext().getAttr().getAttr(USER_NAME_KEY);
    }

    public static String getLoginUserRegionCodes(){
        return SecurityContext.getContext().getAttr().getAttr(USER_REGION_KEYS);
    }

    /**
     * 将登录信息放入线程
     * @param userId
     */
    public static void setLoginUserIdToLocal(String userId,String viewName){
        SecurityContext.getContext().getAttr().setAttr(USER_ID_KEY,userId);
        SecurityContext.getContext().getAttr().setAttr(USER_NAME_KEY, viewName);
    }

    public static void setLoginRoleToSession(HttpServletRequest request, TransRole transRole){
        WebUtils.setSessionAttribute(request, USER_ROLE_KEY, transRole);
    }

    public static void setLoginUserToSession(HttpServletRequest request,TransUser transUser){
        WebUtils.setSessionAttribute(request, USER_KEY, transUser);
    }

    public static void setLoginOrganizeToSession(HttpServletRequest request,List<TransOrganize> transOrganizeList){
        WebUtils.setSessionAttribute(request, USER_ORGANIZE_KEY, transOrganizeList);
    }

    public static void setLoginRegionToSession(HttpServletRequest request,List<TransRegion> transRegionList){
        WebUtils.setSessionAttribute(request, USER_REGIONS_KEY, transRegionList);
    }

    public static void setLoginButtonToSession(HttpServletRequest request,List<String> menuIconList){
        WebUtils.setSessionAttribute(request, USER_BUTTON_KEY, menuIconList);
    }


    public static TransRole getLoginRoleToSession(HttpServletRequest request){
        return (TransRole) WebUtils.getSessionAttribute(request, USER_ROLE_KEY);
    }

    public static TransUser getLoginUserToSession(HttpServletRequest request){
        return (TransUser) WebUtils.getSessionAttribute(request, USER_KEY);
    }

    public static List<TransOrganize> getLoginOrganizeToSession(HttpServletRequest request){
        return (List<TransOrganize>) WebUtils.getSessionAttribute(request, USER_ORGANIZE_KEY);
    }

    public static List<TransRegion> getLoginRegionToSession(HttpServletRequest request){
        return (List<TransRegion>) WebUtils.getSessionAttribute(request, USER_REGIONS_KEY);
    }

    public static List<TransRegion> getLoginButtonToSession(HttpServletRequest request){
        return (List<TransRegion>) WebUtils.getSessionAttribute(request, USER_BUTTON_KEY);
    }

    public static boolean login4Session(HttpServletRequest request){
        Object userIdObj=WebUtils.getSessionAttribute(request, USER_KEY);
        if (userIdObj != null) {
            TransUser transUser=(TransUser)userIdObj;
            setLoginUserToLocal(transUser, request);
            return true;
        } else {
            clearContextAttr(false);
            return false;
        }
    }

    public static void setLoginUserToLocal(TransUser transUser, HttpServletRequest request){
        SecurityContext.getContext().getAttr().setAttr(USER_ID_KEY, transUser.getUserId());
        SecurityContext.getContext().getAttr().setAttr(USER_NAME_KEY, transUser.getViewName());
        List<TransRegion> transRegionList = getLoginRegionToSession(request);
        String regionCodes = "";
        if (transRegionList != null && transRegionList.size() > 0) {
            for (TransRegion transRegion : transRegionList) {
                if ("".equals(regionCodes)) {
                    regionCodes = transRegion.getRegionCode();
                } else {
                    regionCodes += "," + transRegion.getRegionCode();
                }
            }
        }
        SecurityContext.getContext().getAttr().setAttr(USER_REGION_KEYS, regionCodes);
//        String privileges = transUser.getPrivilege();
//        if (StringUtils.isNotBlank(privileges)) {
//            Map privilegeMap = JSON.parseObject(privileges);
//            if(privilegeMap.containsKey("regions"))
//                SecurityContext.getContext().getAttr().setAttr(USER_REGIONS, Sets.newHashSet((List) privilegeMap.get("regions")));
//            if(privilegeMap.containsKey("resources"))
//                SecurityContext.getContext().getAttr().setAttr(USER_RESOURCES,privilegeMap.get("resources"));

//        }
    }

    public static void logout4Session(HttpServletRequest request){
        WebUtils.setSessionAttribute(request, USER_KEY, null);
        WebUtils.setSessionAttribute(request, USER_ROLE_KEY, null);
        WebUtils.setSessionAttribute(request, USER_ORGANIZE_KEY, null);
        WebUtils.setSessionAttribute(request, USER_REGIONS_KEY, null);
        clearContextAttr(true);
    }

    private static void clearContextAttr(boolean isClearAll){
        SecurityContext.getContext().getAttr().removeAttr(USER_ID_KEY);
        SecurityContext.getContext().getAttr().removeAttr(USER_NAME_KEY);
        SecurityContext.getContext().getAttr().removeAttr(USER_REGION_KEYS);
        if(isClearAll) {
            SecurityContext.clearContext();
        }
    }



//    public static boolean isPermitted(String path, Collection<String> operations){
//        if (CollectionUtils.isEmpty(operations)) {
//            return false;
//        }
//        Set<String> userOps = getPermittedOperations(path);
//        for (String op : operations) {
//            if (!userOps.contains(op)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public static boolean isPermitted(String path,String resourceName, Collection<String> operations){
//        if (CollectionUtils.isEmpty(operations)) {
//            return false;
//        }
//        Set<String> userOps = getPermittedOperations(path,resourceName);
//        for (String op : operations) {
//            if (!userOps.contains(op)) {
//                return false;
//            }
//        }
//        return true;
//    }

//    public static Set<String> getPermittedOperations(String path,String resourceName) {
//        Set<String> operations =Sets.newHashSet();
//        String resources = SecurityContext.getContext().getAttr().getAttr(USER_RESOURCES, String.class, null);
//        if(StringUtils.isNotBlank(resources)){
//            List<Map> resourcePrivileges = JSON.parseObject(resources,List.class);
//            for(Map privilege:resourcePrivileges){
//                if(StringUtils.isBlank(resourceName)){
//                    if(PATH_MATCHER.match(String.valueOf(privilege.get("url")),path))
//                        operations.add(String.valueOf(privilege.get("operation")));
//                }else{
//                    if(PATH_MATCHER.match(String.valueOf(privilege.get("url")),path)&&resourceName.equals(privilege.get("name")))
//                        operations.add(String.valueOf(privilege.get("operation")));
//                }
//
//            }
//        }
//        return operations;
//    }

//    public static Set<String> getPermittedOperations(String path) {
//        return getPermittedOperations(path,null);
//    }

//    public static Set<String> getPermittedRegions(){
//        String regions = SecurityContext.getContext().getAttr().getAttr(USER_REGIONS,String.class,null);
//        if(StringUtils.isBlank(regions))
//            return Sets.newHashSet("-999");
//        else{
//            Set<String> result = JSON.parseObject(regions,Set.class);
//            if(result==null||result.isEmpty())
//                return Sets.newHashSet("-999");
//            else
//                return result;
//        }
//    }
}
