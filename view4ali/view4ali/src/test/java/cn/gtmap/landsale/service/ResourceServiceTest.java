package cn.gtmap.landsale.service;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

/**
 * Created by JIBO on 2016/9/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/app-context.xml")
@TransactionConfiguration(defaultRollback = false)
public class ResourceServiceTest {

    @Autowired
    TransResourceService transResourceService;

    @Test
    @Transactional
    public void testAddResource() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TransResource transResource=new TransResource();
        transResource.setResourceId("1144");
        transResource.setGgId("110");
        transResource.setResourceCode("苏地2016-WG-44号");
        transResource.setResourceLocation("姑苏区人民路东、东二路南");
        transResource.setRegionCode("320500");
        transResource.setResourceType(Constants.ResourceTypeLand);
        transResource.setBmBeginTime(sdf.parse("2016-09-12 09:00:00"));
        transResource.setBmEndTime(sdf.parse("2016-09-21 14:00:00"));
        transResource.setGpBeginTime(sdf.parse("2016-09-12 09:00:00"));
        transResource.setGpEndTime(sdf.parse("2016-09-22 10:00:00"));
        transResource.setBzjBeginTime(sdf.parse("2016-09-12 09:00:00"));
        transResource.setBzjEndTime(sdf.parse("2016-09-21 14:00:00"));
        transResource.setXsBeginTime(sdf.parse("2016-09-21 14:00:00"));
        transResource.setCrArea(18129.80);
        transResource.setBeginOffer(32597.0);
        transResource.setFixedOffer(9780.0);
        transResource.setAddOffer(700.0);
        transResource.setLandUse("05");
        transResourceService.insertOrUpdate(transResource);
    }

}
