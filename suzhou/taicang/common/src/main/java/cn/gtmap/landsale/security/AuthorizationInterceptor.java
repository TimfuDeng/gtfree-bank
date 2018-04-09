package cn.gtmap.landsale.security;

import cn.gtmap.egovplat.core.util.RequestUtils;
import cn.gtmap.egovplat.security.ex.NoPermissonException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.service.AuthorizationService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
public class AuthorizationInterceptor implements HandlerInterceptor {
    private String rootPath;
    private Map<String, Collection<String>> privileges = Maps.newLinkedHashMap();

    protected UrlPathHelper urlPathHelper = RequestUtils.URL_PATH_HELPER;
    protected PathMatcher pathMatcher = RequestUtils.PATH_MATCHER;

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setPrivileges(Map<String, String> privileges) {
        for (Map.Entry<String, String> entry : privileges.entrySet()) {
            String value = entry.getValue();
            if (StringUtils.isEmpty(value)) {
                value = Constants.Operation.VIEW.toString();
            }
            this.privileges.put(entry.getKey(), Arrays.asList(StringUtils.split(value, ',')));
        }
    }

    public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);
        for (Map.Entry<String, Collection<String>> entry : privileges.entrySet()) {
            String path = entry.getKey();
            if (pathMatcher.match(path, lookupPath)) {
                if (!SecUtil.isAdmin()&&!SecUtil.isPermitted(lookupPath,null,entry.getValue())) {
                    if(lookupPath.contains("/resource/cjqrs.f"))
                        return true;
                    throw new NoPermissonException(lookupPath);
                } else {
                    break;
                }
            }
        }
        return true;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return internalPreHandle(request, response, handler);
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
