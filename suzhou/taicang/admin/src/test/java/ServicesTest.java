import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.datasource.DataSourceHolder;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.service.ClientService;
import cn.gtmap.landsale.service.TransCaUserService;
import cn.gtmap.landsale.service.TransResourceService;
import com.google.common.collect.Sets;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 11:44
 * @版本号 V 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:app-context.xml") // 加载配置
public class ServicesTest {
	@Autowired
	private TransCaUserService transCaUserService;

	@Autowired
	private TransResourceService transResourceService;

	@Autowired
	private ClientService clientService;

	@Test
	public void testClientService(){
		clientService.applyBankAccount("01567228012c80cdef305648a5970172");
	}

	@Test
	public void testTransResourceService() {
		Set<String> regions = Sets.newHashSet();

		Long countGongGao = transResourceService.countByResourcesStatusAndEditStatus(Constants.ResourceStatusGongGao,Constants.ResourceEditStatusRelease, regions);
		Long countGuaPai = transResourceService.countByResourcesStatusAndEditStatus(Constants.ResourceStatusGuaPai,Constants.ResourceEditStatusRelease, regions);

		System.out.println(countGongGao);
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015-10-26
	 * @创建时间 15:08
	 * @描述 —— 测试ca验证库的使用
	 */
	@Test
	@Ignore
	public void testTransCaUserService() {
//		for (int i = 0; i < 183; i++) {
//			TransCaUser transCaUser = new TransCaUser();
//			transCaUser.setCaCertificate("证书内容" + (i % 2));
//			transCaUser.setCaName("名称" + (i % 3));
//			transCaUser.setCaNotAfterTime(new Date());
//			transCaUser.setCaNotBeforeTime(new Date());
//			transCaUser.setCaOrganizationCode("组织机构代码" + (i % 4));
//			transCaUser.setCaThumbprint("指纹" + (i % 5));
//
//			transCaUser.setCreateAt(new Date());
//			transCaUser.setDescription("描述" + (i % 6));
//			transCaUser.setMobile("移动电话" + (i % 7));
//			transCaUser.setRegionCode("组织机构代码" + (i % 8));
//			transCaUser.setStatus(Status.ENABLED);
//			transCaUser.setType(Constants.UserClass.PERSON);
//			transCaUser.setUserName("联系人" + (i % 9));
//
//			DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
//			transCaUserService.saveTransCaUser(transCaUser);
//		}

//		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
//		TransCaUser transCaUser = transCaUserService.getTransCaUser("0150a322f60c66fac2db50a322f10091");
//		System.out.println(transCaUser.getCaThumbprint());

//		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
//		TransCaUser transCaUser = transCaUserService.getTransCaUserByCAThumbprint("指纹888");
//		System.out.println(transCaUser.getCaOrganizationCode());

//		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
//		TransCaUser transCaUser = transCaUserService.getTransCaUserByUserName("联系人007");
//		System.out.println(transCaUser.getCaOrganizationCode());

//		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
//		Set<String> deleteIds = new HashSet<String>();
//		deleteIds.add("0150a31ead1a0538cfdc50a31eaa0001");
//		transCaUserService.deleteTransCaUser(deleteIds);

		Pageable request = new PageRequest();
		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
		Page<TransCaUser> results = transCaUserService.findTransCaUser(null, null, null, request);

		transResourceService.getTransResource("123123");
	}
}
