package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.bean.Attrable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.NotifyTimerService;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransUserApplyInfo;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransUserApplyInfoService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保证金相关定时提醒
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/9
 */
public class BailNotifyTimerServiceImpl implements NotifyTimerService {
    private TransResourceService transResourceService;
    private TransUserApplyInfoService transUserApplyInfoService;
    private TransResourceApplyService transResourceApplyService;

    private Map<String,String> notificationTpls;


    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransUserApplyInfoService(TransUserApplyInfoService transUserApplyInfoService) {
        this.transUserApplyInfoService = transUserApplyInfoService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setNotificationTpls(Map<String,String> notificationTpls) {
        this.notificationTpls = notificationTpls;
    }

    /**
     * 定时触发的方法。
     *
     * @return
     */
    @Override
    public Map<String, String> smsNotify() {
        Map notificationsMap = Maps.newHashMap();
        checkBzjEndTime(notificationsMap);
        checkBzjEndTimeExt(notificationsMap);
        checkGpEndTimeExt(notificationsMap);
        return notificationsMap;
    }

    /**
     * 邮件通知方法
     *
     * @return 返回Attrable对象，key分别为address，content，title等
     */
    @Override
    public Attrable emailNotify() {
        return null;
    }

    private void add2NotificationMap(String mobile,String msg,Map notificationsMap){
        if(StringUtils.isBlank(mobile))
            return;
        if(notificationsMap.containsKey(mobile))
            notificationsMap.put(mobile,notificationsMap.get(mobile)+msg);
        else
            notificationsMap.put(mobile,msg);
    }

    /**
     * 检查当天保证金截止到期的地块
     * @param notificationsMap
     */
    private void checkBzjEndTime(Map notificationsMap){
        Date currentTime = Calendar.getInstance().getTime();
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(currentTime);
        startTime.set(Calendar.HOUR_OF_DAY,0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(currentTime);
        endTime.set(Calendar.HOUR_OF_DAY,23);
        endTime.set(Calendar.SECOND,59);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.MILLISECOND,999);
        bzjNotification(notificationsMap,startTime.getTime(),endTime.getTime(),notificationTpls.get("bzjEndTime"));

    }

    /**
     * 检查保证金三天后到期的地块
     * @param notificationsMap
     */
    private void checkBzjEndTimeExt(Map notificationsMap){
        Date currentTime = Calendar.getInstance().getTime();
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(currentTime);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH) + 3);

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(currentTime);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.SECOND,59);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.MILLISECOND,999);
        endTime.set(Calendar.DAY_OF_MONTH,startTime.get(Calendar.DAY_OF_MONTH)+3);
        bzjNotification(notificationsMap,startTime.getTime(),endTime.getTime(),notificationTpls.get("bzjEndTimeExt"));

    }

    private void bzjNotification(Map notificationsMap,Date startTime,Date endTime,String tpl){
        if(StringUtils.isBlank(tpl))
            return;
        List<TransResource> transResourceList = transResourceService.findTransResourceByBzjEndTime(startTime,endTime);
        if(transResourceList!=null&&!transResourceList.isEmpty()){
            for(TransResource transResource:transResourceList){
                List<TransResourceApply>  transResourceApplyList=
                        transResourceApplyService.getTransResourceApplyByResourceId(transResource.getResourceId());
                for(TransResourceApply transResourceApply:transResourceApplyList){
                    if (transResourceApply.getApplyStep()== Constants.StepBaoZhengJin) {   //未交纳保证金的
                        List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoService.getTransUserApplyInfoByUser(transResourceApply.getUserId());
                        if (!transUserApplyInfoList.isEmpty()) {
                            Map params = Maps.newHashMap();
                            params.put("userName", StringUtils.defaultIfBlank(transUserApplyInfoList.get(0).getContactPerson(),""));
                            params.put("resourceCode",StringUtils.defaultIfBlank(transResource.getResourceCode(),""));
                            StrSubstitutor sub = new StrSubstitutor(params, "%{", "}");
                            add2NotificationMap(transUserApplyInfoList.get(0).getContactTelephone(),sub.replace(tpl),notificationsMap);
                        }
                    }
                }
            }
        }
    }

    /**
     * 检查挂牌截止日期为当天的地块
     * @param notificationsMap
     */
    private void checkGpEndTimeExt(Map notificationsMap){
        Date currentTime = Calendar.getInstance().getTime();
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(currentTime);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(currentTime);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.SECOND,59);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.MILLISECOND, 999);
        String tpl = notificationTpls.get("gpEndTime");
        if(StringUtils.isBlank(tpl))
            return;

        List<TransResource> transResourceList = transResourceService.findTransResourceByGpEndTime(startTime.getTime(), endTime.getTime());
        if(transResourceList!=null&&!transResourceList.isEmpty()){
            for(TransResource transResource:transResourceList){
                List<TransResourceApply>  transResourceApplyList=
                        transResourceApplyService.getTransResourceApplyByResourceId(transResource.getResourceId());
                for(TransResourceApply transResourceApply:transResourceApplyList){
                    if (transResourceApply.getApplyStep()== Constants.StepOver) {   //已交纳保证金的
                        List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoService.getTransUserApplyInfoByUser(transResourceApply.getUserId());
                        if (!transUserApplyInfoList.isEmpty()) {
                            Map params = Maps.newHashMap();
                            params.put("userName", StringUtils.defaultIfBlank(transUserApplyInfoList.get(0).getContactPerson(),""));
                            params.put("resourceCode",StringUtils.defaultIfBlank(transResource.getResourceCode(),""));
                            StrSubstitutor sub = new StrSubstitutor(params, "%{", "}");
                            add2NotificationMap(transUserApplyInfoList.get(0).getContactTelephone(),sub.replace(tpl),notificationsMap);
                        }
                    }

                }
            }
        }

    }
}
