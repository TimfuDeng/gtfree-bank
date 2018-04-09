package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.common.Constants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author M150237 on 2017-11-28.
 */
@Entity
@Table(name = "trans_floor_price_view")
public class TransFloorPriceView implements Serializable {
    @Id
    @Column(length = 32)
    private String userId;

    @Column(length = 30)
    @Field("底价表id")
    private String floorPriceId;

    @Column(length = 200)
    @Field("显示名")
    private String viewName;

    @Column(precision = 1)
    @Field(value = "状态")
    private Status status = Status.ENABLED;

    @Column(length = 20)
    @Field("行政区名称")
    private String regionName;

    @Column(length = 20)
    @Field("行政区code")
    private String regionCode;

    @Column(length = 20)
    @Field("土地用途名称")
    private String tdytDictName;

    @Column(length = 20, nullable = false)
    @Field("用途编码")
    private String tdytDictCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    @Field(value = "创建时间")
    private Date createAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFloorPriceId() {
        return floorPriceId;
    }

    public void setFloorPriceId(String floorPriceId) {
        this.floorPriceId = floorPriceId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getTdytDictName() {
        return tdytDictName;
    }

    public void setTdytDictName(String tdytDictName) {
        this.tdytDictName = tdytDictName;
    }

    public String getTdytDictCode() {
        return tdytDictCode;
    }

    public void setTdytDictCode(String tdytDictCode) {
        this.tdytDictCode = tdytDictCode;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
