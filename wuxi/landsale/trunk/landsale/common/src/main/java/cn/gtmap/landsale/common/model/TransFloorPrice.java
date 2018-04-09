package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 底价权限
 * @author cxm
 * @version v1.0, 2017/11/21
 */
@Entity
@Table(name = "trans_floor_price")
public class TransFloorPrice implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32, nullable = false)
    private String floorPriceId;

    @Column(length = 20, nullable = false)
    @Field("行政区")
    private String regionCode;

    @Column(length = 20, nullable = false)
    @Field("用途编码")
    private String tdytDictCode;

    @Column(length = 20, nullable = false)
    @Field("土地用途名称")
    private String tdytDictName;

    @Column(length = 32, nullable = false)
    @Field("用户id")
    private String userId;

    public String getFloorPriceId() {
        return floorPriceId;
    }

    public void setFloorPriceId(String floorPriceId) {
        this.floorPriceId = floorPriceId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getTdytDictCode() {
        return tdytDictCode;
    }

    public void setTdytDictCode(String tdytDictCode) {
        this.tdytDictCode = tdytDictCode;
    }

    public String getTdytDictName() {
        return tdytDictName;
    }

    public void setTdytDictName(String tdytDictName) {
        this.tdytDictName = tdytDictName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
