package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.TransDept;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.service.TransDeptService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

/**
 *
 * 组织机构
 * Created by trr on 2015/10/9.
 */
@Controller
@RequestMapping(value = "console/dept")
public class DeptController extends BaseController {

    @Autowired
    TransDeptService transDeptService;

    @RequestMapping(value = "list")
    public String list(@PageDefault(value = 10) Pageable page,String title,Model model){
        Page<TransDept> transDeptPage=null;
        transDeptPage= transDeptService.findTransDept(title,page);
        model.addAttribute("transDeptPage",transDeptPage);
        model.addAttribute("title",title);

        return "dept/dept-list";
    }

    @ModelAttribute("transDept")
    public TransDept getRole(@RequestParam(value = "deptId",required = false)String deptId){
        return StringUtils.isBlank(deptId)? new TransDept():transDeptService.getTransDept(deptId);
    }

    @RequestMapping(value = "save")
    public String save (@ModelAttribute("transDept")TransDept transDept,RedirectAttributes ra,Model model){
        if(StringUtils.isBlank(transDept.getDeptId())){
            transDept.setDeptId(null);
        }
        transDeptService.saveTransDept(transDept);
        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg","保存成功");
        //return "redirect:/console/dept/list";
        return "redirect:/console/dept/edit?deptId="+transDept.getDeptId();

    }

    @RequestMapping(value = "edit")
    public String transNews( String deptId,Model model){
        TransDept transDept=null;
        if(StringUtils.isNotBlank(deptId)){
            transDept=transDeptService.getTransDept(deptId);

        }else{
            transDept=new TransDept();

        }
        model.addAttribute("transDept",transDept);
        return "dept/dept-edit";
    }

    @RequestMapping(value = "view")
    public String viewTransNews( String deptId,Model model){
        TransDept transDept=null;
        transDept=transDeptService.getTransDept(deptId);
        model.addAttribute("transDept",transDept);
        return "dept/dept";
    }


    @RequestMapping(value = "delete.f")
    @ResponseBody
    public String deleteTreansDept(@RequestParam(value = "deptIds",required = false) String deptIds){
        transDeptService.deleteTransDept(Lists.newArrayList(deptIds.split(";")));
        return "true";
    }

}
