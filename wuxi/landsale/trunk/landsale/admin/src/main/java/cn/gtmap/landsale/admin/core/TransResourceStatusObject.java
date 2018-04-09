package cn.gtmap.landsale.admin.core;


import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransResource;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jibo on 2015/6/17.
 */
public class TransResourceStatusObject {
    TransResource transResource;
    Constants.ResourceOperateStep operateStep;
    Date enterTime;

    public TransResourceStatusObject(){

    }

    public TransResourceStatusObject(TransResource transResource, Constants.ResourceOperateStep operateStep, Date enterTime){
        this.transResource=transResource;
        this.operateStep =operateStep;
        this.enterTime=enterTime;
    }
    public TransResource getTransResource() {
        return transResource;
    }

    public void setTransResource(TransResource transResource) {
        this.transResource = transResource;
    }

    public Constants.ResourceOperateStep getOperateStep() {
        return operateStep;
    }

    public void setOperateStep(Constants.ResourceOperateStep operateStep) {
        this.operateStep = operateStep;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public boolean enterStep(){
        Date cDate= Calendar.getInstance().getTime();
        return cDate.after(this.enterTime);
    }
}
