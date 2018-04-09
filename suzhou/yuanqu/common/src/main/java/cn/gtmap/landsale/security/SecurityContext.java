package cn.gtmap.landsale.security;

import cn.gtmap.egovplat.core.attr.Attrs;
import cn.gtmap.egovplat.core.bean.Attrable;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public class SecurityContext {
    private static ThreadLocal<SecurityContext> LOCAL = new InheritableThreadLocal<SecurityContext>() {
        @Override
        protected SecurityContext initialValue() {
            return new SecurityContext();
        }
    };

    private Attrable attr = Attrs.newJSONAttrable();

    public static SecurityContext getContext() {
        return LOCAL.get();
    }

    public static void clearContext() {
        LOCAL.remove();
    }

    public Attrable getAttr() {
        return attr;
    }

    private SecurityContext() {
    }
}
