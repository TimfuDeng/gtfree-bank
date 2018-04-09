package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransCrgg;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JIBO on 2016/9/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/app-context.xml")
@TransactionConfiguration(defaultRollback = false)
public class TransCrggServiceTest {

    @Autowired
    TransCrggService transCrggService;


    @Test
    @Transactional
    public void testAddCrgg() throws Exception {
        TransCrgg transCrgg = new TransCrgg();
        transCrgg.setGgId("110");
        transCrgg.setGgBeginTime(new Date());
        transCrgg.setGgEndTime(new Date());
        transCrgg.setGgContent("sadfasdfasdfasdf");
        transCrgg.setGgNum("涟水县挂[2016]第14号");
        transCrgg.setGgTitle("涟水县国土资源局\n" +
                "国有土地使用权挂牌出让公告涟水县挂[2016]第14号");
        transCrgg.setRegionCode("320101");
        Map attachements = new LinkedHashMap();
        attachements.put("出让公告", "http://www.landjs.com/web/gg_view.aspx?GYGG_GUID=3C59D33604FE40F6E055000000000001");
        transCrgg.setAttachments(attachements);
        transCrggService.insertOrUpdate(transCrgg);
    }




}
