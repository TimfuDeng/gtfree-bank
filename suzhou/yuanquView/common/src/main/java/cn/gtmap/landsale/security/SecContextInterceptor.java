package cn.gtmap.landsale.security;

import cn.gtmap.egovplat.core.util.RequestUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public class SecContextInterceptor implements HandlerInterceptor {
    private String[] excludes;
    private String[] needLogins;
    private String redirectUrl;
    protected UrlPathHelper urlPathHelper = RequestUtils.URL_PATH_HELPER;
    protected PathMatcher pathMatcher = RequestUtils.PATH_MATCHER;

    public void setNeedLogins(String[] needLogins) {
        this.needLogins = needLogins;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (!RequestUtils.matchAny(request, urlPathHelper, pathMatcher, excludes)) {
            boolean login= SecUtil.login4Session(request);
            if (RequestUtils.matchAny(request, urlPathHelper, pathMatcher, needLogins)) {
                 //将登录的session里面的userid放入线程
                if (redirectUrl != null && !login) {
                    response.sendRedirect(redirectUrl + (redirectUrl.contains("?") ? "&" : "?") + "url=" + ServletUriComponentsBuilder.fromRequest(request).build().encode());
                } else {
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        if (RequestUtils.matchAny(request, urlPathHelper, pathMatcher, excludes)) {
            SecurityContext.clearContext();
        }
    }
}
