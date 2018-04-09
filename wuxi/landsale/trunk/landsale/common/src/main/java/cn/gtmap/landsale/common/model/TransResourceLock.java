package cn.gtmap.landsale.common.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 地块报价锁对象
 * @author liushaoshuai on 2017/7/21.
 */
@Entity
@Table(name = "trans_resource_lock")
public class TransResourceLock implements Serializable{

   /* @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String lockId;*/

    @Id
    @Column(length = 32)
    private String resourceId;
    //报价锁地块资源

    /**
     * 当前价格
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private double currentOffer;

    /**
     * 当前状态
     */
    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int currentStatus=0;

    /**
     * 当前状态到期时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentOverTime;

    /**
     * 当前用户Id
     */
    @Column(nullable = false,length = 32)
    private String userId;

  /*  public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public double getCurrentOffer() {
        return currentOffer;
    }

    public void setCurrentOffer(double currentOffer) {
        this.currentOffer = currentOffer;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getCurrentOverTime() {
        return currentOverTime;
    }

    public void setCurrentOverTime(Date currentOverTime) {
        this.currentOverTime = currentOverTime;
    }
}
