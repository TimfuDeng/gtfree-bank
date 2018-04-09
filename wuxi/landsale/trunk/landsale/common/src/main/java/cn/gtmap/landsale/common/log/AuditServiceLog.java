package cn.gtmap.landsale.common.log;


import cn.gtmap.landsale.common.Constants;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/10
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditServiceLog {

    Constants.LogCategory category() default Constants.LogCategory.OTHER;

    Constants.LogProducer producer() default Constants.LogProducer.ADMIN;

    String description() default "";

}
