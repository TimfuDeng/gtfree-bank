package cn.gtmap.landsale.common.web.freemarker;

import cn.gtmap.landsale.common.security.SecUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jibo1_000 on 2015/5/10.
 */
@Component
public class WebUtil {

    private  boolean caEnabled;

    private String mapUrl;

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public void setCaEnabled(boolean caEnabled) {
        this.caEnabled = caEnabled;
    }

    private static Logger log = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 判断登录
     * @return
     */
    public boolean isLogin(){
        return SecUtil.isLogin();
    }

    public String getLoginUserViewName(){
        return SecUtil.getLoginUserViewName();
    }

    public String getLoginUserId(){
        return SecUtil.getLoginUserId();
    }

    public String getUrl(HttpServletRequest request){
        return ServletUriComponentsBuilder.fromRequest(request).build().encode().toUriString();
    }

    public boolean isAdmin(){
        return SecUtil.isAdmin();
    }

    public boolean isCaEnabled() {
        return caEnabled;
    }
}
