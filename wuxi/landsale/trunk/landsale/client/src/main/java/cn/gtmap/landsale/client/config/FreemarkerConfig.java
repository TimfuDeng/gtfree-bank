package cn.gtmap.landsale.client.config;

import cn.gtmap.landsale.common.web.freemarker.*;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * .FreemarkerConfig
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2016/9/24 16:15
 */
@Configuration
public class FreemarkerConfig {

    static Logger logger = LoggerFactory.getLogger(FreemarkerConfig.class);

    @Autowired
    freemarker.template.Configuration configuration;

    @Autowired
    ResourceUtil resourceUtil;

    @Autowired
    PriceUtil priceUtil;

    @Autowired
    UserUtil userUtil;

    @Autowired
    WebUtil webUtil;

    @Autowired
    ResultUtil resultUtil;

    @Autowired
    RegionUtil regionUtil;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Value("${app.env.base}")
    private String base;

    @Value("${app.env.storage}")
    private String storage;

    @Value("${app.env.core}")
    private String core;

    @PostConstruct
    public void setCfg() {
        try {
            configuration.setSharedVariable("base", base);
            configuration.setSharedVariable("storage", storage);
            configuration.setSharedVariable("core", core);
            configuration.setClassicCompatible(true);
            configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
            configuration.setDateFormat("yyyy-MM-dd");
            List templateNames = new ArrayList<>();
            templateNames.add("common/common.ftl");
            configuration.setAutoIncludes(templateNames);
            configuration.setTimeFormat("HH:mm:ss");
            configuration.setNumberFormat("0.##################");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setURLEscapingCharset("UTF-8");

            webUtil.setCaEnabled(caEnabled);
            configuration.setSharedVariable("WebUtil", webUtil);
            configuration.setSharedVariable("ResultUtil", resultUtil);
            configuration.setSharedVariable("ResourceUtil", resourceUtil);
            configuration.setSharedVariable("PriceUtil", priceUtil);
            configuration.setSharedVariable("UserUtil", userUtil);
            configuration.setSharedVariable("RegionUtil", regionUtil);

        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
    }


}