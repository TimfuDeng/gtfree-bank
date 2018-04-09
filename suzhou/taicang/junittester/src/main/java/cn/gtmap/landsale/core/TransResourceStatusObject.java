package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jibo on 2015/6/17.
 */
public class TransResourceStatusObject {
    TransResource transResource;
    Constants.ResourceOperateStep OperateStep;
    Date enterTime;

    public TransResourceStatusObject(){

    }

    public TransResourceStatusObject(TransResource transResource,Constants.ResourceOperateStep OperateStep, Date enterTime){
        this.transResource=transResource;
        this.OperateStep=OperateStep;
        this.enterTime=enterTime;
    }
    public TransResource getTransResource() {
        return transResource;
    }

    public void setTransResource(TransResource transResource) {
        this.transResource = transResource;
    }

    public Constants.ResourceOperateStep getOperateStep() {
        return OperateStep;
    }

    public void setOperateStep(Constants.ResourceOperateStep operateStep) {
        OperateStep = operateStep;
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
