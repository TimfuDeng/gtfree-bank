import com.gtmap.txgc.dao.CommonDao;
import com.gtmap.txgc.dao.CommonDao2;
import com.gtmap.txgc.dao.CommonDao2Service;
import com.gtmap.txgc.service.CommonService;
import com.gtmap.txgc.service.industry.IndustryService;
import com.gtmap.txgc.service.middle.MiddleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by liushaoshuai on 2018/1/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
public class commonServiceTest {
    @Resource
    private IndustryService industryService;

    @Resource
    private MiddleService middleService;

    @Autowired
    CommonService commonService;

    @Autowired
    CommonDao commonDao;

    @Autowired
    CommonDao2 commonDao2;

    @Autowired
    CommonDao2Service commonDao2Service;






    /**
     * 中间件业务代码单元测试
     */
    @Test
    public void testMiddleService() {
       /* List<HashMap<String,Object>> crgg = null;
        List<HashMap<String,Object>> resources = null;
        List<HashMap<String,Object>> resourceInfos = null;
        List<HashMap<String,Object>> resourceSons = null;
        crgg = middleService.hashMapListCrgg();
        for (HashMap<String,Object> crggMap:crgg){
            String ggId = crggMap.get("GG_ID").toString();
            resources = middleService.hashMapListResource(ggId);

        }
        for (HashMap<String,Object> resourceMap:resources){
            String resourceId = resourceMap.get("RESOURCE_ID").toString();
            resourceInfos = middleService.hashMapListResourceInfo(resourceId);

        }
        for (HashMap<String,Object> resourceMap:resources){
            String resourceId = resourceMap.get("RESOURCE_ID").toString();
            resourceSons = middleService.hashMapListResourceSon(resourceId);
        }
        System.out.println("记录："+crgg.size()+","+resources.size()+","+resourceInfos.size()+","+resourceSons.size());
*/
        industryService.querySuccessInfo();

    }

    /**
     * 工业业务代码单元测试
     */
    @Test
    public void testIndustryService() {
        //industryService.saveTest();

        //industryService.Middle2Industry();
        //industryService.querySuccessInfo();

        /*List<HashMap<String,Object>> successInfos =  industryService.querySuccessInfo();
        for (HashMap<String,Object> successInfo:successInfos){
            List<HashMap<String,Object>> result = middleService.updateMiddleSuccessInfo(successInfo);
        }*/
        //middleService.updateCrggControlStatus("015c3d66f0fb802962785c3aa92800c8","1");
      /*  try {
            industryService.updateMiddleControlStautsTest3();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //查找成交人信息：成交价、成交单位、成交时间、经办人、经办人联系方式、经办人联系地址（联合竞买只推送主联合人信息）
       /* List<HashMap<String,Object>> successInfos = null;
        successInfos =  industryService.querySuccessInfo();
        for (HashMap<String,Object> successInfo:successInfos){
            //设置成交人信息到中间库，必须有RESOURCE_ID键值对、必须有有要更新的字段
            if (successInfo.size()>1&&null!=successInfo.get("RESOURCE_ID")){
                List<HashMap<String,Object>> result = middleService.updateMiddleSuccessInfo(successInfo);
            }else {
                System.err.println("缺少RESOURCE_ID或者没有要更新的字段！");
            }

        }*/



    }

    @Test
    public void testProxy(){
        try {
           /* List<HashMap<String,Object>> maps = commonService.commonQuery(SqlString.queryTest());
            System.out.println(maps);*/
            //commonDao.commonSqlQuery2();
            //commonDao2.commonSqlQuery2();
            commonDao2Service.commonSqlQuery2();


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           /* try {
                byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy18", new Class[]{CommonDao.class});
                FileOutputStream os = new FileOutputStream("E://$Proxy18.class");
                os.write(bytes);
                os.close();
            }catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }

}
