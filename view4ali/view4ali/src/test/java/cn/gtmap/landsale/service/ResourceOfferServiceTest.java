package cn.gtmap.landsale.service;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.support.UUIDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by JIBO on 2016/9/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/app-context.xml")
@TransactionConfiguration(defaultRollback = false)
public class ResourceOfferServiceTest {

    @Autowired
    ResourceOfferService resourceOfferService;

    @Test
    @Transactional
    public void testAddResource() throws Exception {
        TransResourceOffer resourceOffer=new TransResourceOffer();
        resourceOffer.setOfferId(UUIDGenerator.generate());
        resourceOffer.setResourceId("1147");
        resourceOffer.setOfferPrice(Math.random());
        resourceOffer.setOfferTime(Calendar.getInstance().getTimeInMillis());
        resourceOfferService.insert(resourceOffer);
    }

}
