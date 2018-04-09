package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地图纠偏数据点
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/6
 */
@Entity
@Table(name = "trans_gps_offset")
@IdClass(value=LngLatUnionId.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransGpsOffset implements Serializable{

    @Id
    private Double lng;
    @Id
    private Double lat;
    @Column(columnDefinition ="number(8)")
    @Field("X偏移量")
    private long offx;
    @Column(columnDefinition ="number(8)")
    @Field("Y偏移量")
    private long offy;
    @Column(columnDefinition ="number(8,6)")
    @Field("经度偏移量")
    private Double offsetlng;
    @Column(columnDefinition ="number(8,6)")
    @Field("纬度偏移量")
    private Double offsetlat;


    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public long getOffx() {
        return offx;
    }

    public void setOffx(long offx) {
        this.offx = offx;
    }

    public long getOffy() {
        return offy;
    }

    public void setOffy(long offy) {
        this.offy = offy;
    }

    public Double getOffsetlng() {
        return offsetlng;
    }

    public void setOffsetlng(Double offsetlng) {
        this.offsetlng = offsetlng;
    }

    public Double getOffsetlat() {
        return offsetlat;
    }

    public void setOffsetlat(Double offsetlat) {
        this.offsetlat = offsetlat;
    }
}
