package cn.gtmap.landsale.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    public static BigDecimal createBigDecimalIfBlank(String str,String defaultStr){
        return StringUtils.isBlank(str)?createBigDecimal(defaultStr):createBigDecimal(str);
    }

    public static double createDoubleIfBlank(String str,String defaultStr){
        return StringUtils.isBlank(str)?createDouble(defaultStr) : createDouble(str);
    }
}
