package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.datasource.DataSourceHolder;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.service.RegionService;
import cn.gtmap.landsale.service.TransCaUserService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 16:16
 * @版本号 V 1.0
 */
@Controller
@RequestMapping(value = "console/ca-user")
public class CaUserController {
	@Autowired
	TransCaUserService transCaUserService;
	@Autowired
	RegionService regionService;

	@RequestMapping("list")
	public String list(@PageDefault(value = 10) Pageable page, String userName, @RequestParam(value = "userType", required = false, defaultValue = "-1") Integer userType,
	                   String regionCode, Model model) {

		List<String[]> regionsList = regionService.findAllRegions();
		Set<String> regions = Sets.newHashSet();
		if (StringUtils.isNotBlank(regionCode)) {
			regions.add(regionCode);
		}/* else {
			for (String[] strings : regionsList) {
				regions.add(strings[0]);
			}
		}*/
		model.addAttribute("regionAllList", regionsList);
		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
		Page<TransCaUser> transCaUserList = transCaUserService.findTransCaUser(userName, userType, regions, page);
		model.addAttribute("transCaUserList", transCaUserList);
		model.addAttribute("userName", userName);
		model.addAttribute("userType", userType);
		model.addAttribute("regionCode", regionCode);
		return "ca-user-list";
	}

	@RequestMapping("edit")
	public String user(String userId, Model model) {
		TransCaUser transCaUser;
		if (StringUtils.isNotBlank(userId)) {
			DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
			transCaUser = transCaUserService.getTransCaUser(userId);
		} else {
			transCaUser = new TransCaUser();
		}
		model.addAttribute("regionAllList", regionService.findAllRegions());
		model.addAttribute("transCaUser", transCaUser);
		return "ca-user-edit";
	}

	@RequestMapping("save")
	public String save(@ModelAttribute("user") TransCaUser transCaUser, RedirectAttributes ra, Model model) {
		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
		TransCaUser tmpTransUser = transCaUserService.getTransCaUserByKeyInfo(transCaUser.getCaName(), transCaUser.getCaThumbprint());
		if (tmpTransUser == null) {
			tmpTransUser = new TransCaUser();
			tmpTransUser.setCreateAt(Calendar.getInstance().getTime());
		}

		tmpTransUser.setUserName(transCaUser.getUserName());
		tmpTransUser.setMobile(transCaUser.getMobile());
		tmpTransUser.setType(transCaUser.getType());
		tmpTransUser.setStatus(transCaUser.getStatus());
		tmpTransUser.setCaOrganizationCode(transCaUser.getCaOrganizationCode());
		tmpTransUser.setRegionCode(transCaUser.getRegionCode());
		tmpTransUser.setDescription(transCaUser.getDescription());
		tmpTransUser.setCaName(transCaUser.getCaName());
		tmpTransUser.setCaThumbprint(transCaUser.getCaThumbprint());
		tmpTransUser.setCaNotBeforeTime(transCaUser.getCaNotBeforeTime());
		tmpTransUser.setCaNotAfterTime(transCaUser.getCaNotAfterTime());
		tmpTransUser.setCaCertificate(transCaUser.getCaCertificate());

		DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
		tmpTransUser = transCaUserService.saveTransCaUser(tmpTransUser);
		ra.addFlashAttribute("_result", true);
		ra.addFlashAttribute("_msg", "保存成功！");
		return "redirect:/console/ca-user/edit?userId=" + tmpTransUser.getUserId();
	}
}
