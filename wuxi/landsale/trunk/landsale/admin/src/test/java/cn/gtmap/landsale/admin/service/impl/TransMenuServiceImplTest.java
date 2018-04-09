package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.admin.App;
import cn.gtmap.landsale.admin.service.TransMenuService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class TransMenuServiceImplTest extends TestCase {

    @Autowired
    TransMenuService transMenuService;

    @Test
    public void testGetTransMenuListByRole() throws Exception {
        transMenuService.getTransMenuListByRole("1");
    }
}