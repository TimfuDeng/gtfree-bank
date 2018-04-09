package cn.gtmap.landsale.client.web;

import cn.gtmap.landsale.client.App;
import cn.gtmap.landsale.common.model.TransBank;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class BankControllerTest extends TestCase {

    static Logger logger = LoggerFactory.getLogger(BankControllerTest.class);


}