package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/6
 */
public class LngLatUnionId implements Serializable {
    @Column(columnDefinition ="number(8,2)")
    @Field("经度")
    private Double lng;

    @Column(columnDefinition ="number(8,2)")
    @Field("纬度")
    private Double lat;

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


    public LngLatUnionId(Double lng, Double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LngLatUnionId){
            LngLatUnionId pk=(LngLatUnionId)obj;
            if(this.lat.equals(pk.lat)&&this.lng.equals(pk.lng)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
