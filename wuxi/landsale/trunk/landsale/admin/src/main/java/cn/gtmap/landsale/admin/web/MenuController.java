    package cn.gtmap.landsale.admin.web;

    import cn.gtmap.landsale.admin.service.TransMenuService;
    import cn.gtmap.landsale.common.model.TransMenu;
    import cn.gtmap.landsale.common.model.TransRole;
    import cn.gtmap.landsale.common.security.SecUtil;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import javax.servlet.http.HttpServletRequest;
    import java.util.List;

/**
 * 菜单首页
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    TransMenuService transMenuService;

    @RequestMapping("/getLeftMenu")
    public List<TransMenu> getLeftMenu(HttpServletRequest request, String roleId) {
        List<TransMenu> transMenuList;
        if (SecUtil.isAdmin()) {
            transMenuList = transMenuService.getTransMenuList();
        } else {
            TransRole transRole = SecUtil.getLoginRoleToSession(request);
            transMenuList = transMenuService.getTransMenuListByRole(transRole.getRoleId());
        }
        return transMenuList;
    }

}

