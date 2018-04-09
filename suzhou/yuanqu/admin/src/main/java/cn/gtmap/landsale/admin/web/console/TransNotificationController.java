package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.admin.service.NotificationService;
import cn.gtmap.landsale.model.TransNotification;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Date;

/**
 * Created by light on 2015/9/23.
 */
@Controller
@RequestMapping(value = "console/notification")
public class TransNotificationController extends BaseController{

    @Autowired
    private NotificationService notificationService;

    public static final String DEPLOY_STATUS_ON ="1"; //发布中
    public static final String DEPLOY_STATUS_OFF ="0";//撤回

    @RequestMapping(value = "list")
    public String list(@PageDefault(value=10) Pageable page,Model model,String noteTitle){
        Page<TransNotification> transNoteList = notificationService.findNote(noteTitle,page);
        model.addAttribute("transNoteList",transNoteList);
        return "note/trans-note-list";
    }

    @RequestMapping(value = "edit")
    public String edit(String noteId, Model model) throws Exception{
        TransNotification transNote;
        if(StringUtils.isNotBlank(noteId)){
            transNote = notificationService.findNote(noteId);
        }else{
            transNote = new TransNotification();
            transNote.setDeployStatus(DEPLOY_STATUS_OFF);
        }
        model.addAttribute("transNote",transNote);
        return "note/trans-note-edit";
    }

    @RequestMapping(value="save")
    public String save(@ModelAttribute(value="transNote")TransNotification transNote,RedirectAttributes ra,Model model){
        if(StringUtils.isBlank(transNote.getNoteId())){
            transNote.setNoteId(null);
            transNote.setDeployStatus(DEPLOY_STATUS_OFF);
        }
        transNote = notificationService.saveNote(transNote);
        ra.addFlashAttribute("_msg","保存成功！");
        ra.addFlashAttribute("_result",true);
        return "redirect:/console/notification/edit?noteId="+transNote.getNoteId();
    }

    @RequestMapping(value="delete")
    @ResponseBody
    public String delete(@RequestParam(value="noteIds",required = false) String noteIds){
        notificationService.deleteNote(Lists.newArrayList(noteIds.split(";")));
        return "true";
    }

    @RequestMapping(value="deploy")
    @ResponseBody
    public String deploy(String noteId,Model model){
        TransNotification transNote = null;
        try {
            transNote = notificationService.findNote(noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transNote.setDeployStatus(DEPLOY_STATUS_ON);
        transNote.setNoteTime(new Date());
        transNote = notificationService.saveNote(transNote);
        model.addAttribute(transNote);
        return "true";
    }

    @RequestMapping(value="revoke")
    @ResponseBody
    public String revoke(String noteId,Model model){
        TransNotification transNote = null;
        try {
            transNote = notificationService.findNote(noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transNote.setDeployStatus(DEPLOY_STATUS_OFF);
        transNote.setNoteTime(new Date());
        notificationService.saveNote(transNote);
        model.addAttribute(transNote);
        return "true";
    }

    @RequestMapping(value="/status/view.f")
    public String viewStatus(String noteId,Model model){
        TransNotification transNote = null;
        try {
            transNote = notificationService.findNote(noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("note",transNote);
        return "common/note-status";
    }

    @RequestMapping(value="detail")
    public String detail(String noteId,Model model){
        TransNotification transNote = null;
        try {
            transNote = notificationService.findNote(noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("transNote",transNote);
        return "note/trans-note-detail";
    }
}
