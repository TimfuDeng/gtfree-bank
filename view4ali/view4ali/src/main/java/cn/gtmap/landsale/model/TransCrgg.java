package cn.gtmap.landsale.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网上交易出让公告实体对象
 * Created by jiff on 14/12/24.
 */

public class TransCrgg implements Serializable {
    private static final long serialVersionUID = -9137292468621763148L;

    private String ggId;
    private String ggTitle;  //公告标题
    private String regionCode;  //所属行政区
    private Date ggBeginTime;  //公告开始时间
    private Date ggEndTime;  //公告结束时间
    private int ggType;  //公告类型
    private String ggContent;  //公告内容
    private String ggNum;  //公告编号
    private String gyggId;//土地市场网土地供应GUID
    private Map attachments;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGgId() {
        return ggId;
    }

    public void setGgId(String ggId) {
        this.ggId = ggId;
    }

    public String getGgTitle() {
        return ggTitle;
    }

    public void setGgTitle(String ggTitle) {
        this.ggTitle = ggTitle;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Date getGgBeginTime() {
        return ggBeginTime;
    }

    public void setGgBeginTime(Date ggBeginTime) {
        this.ggBeginTime = ggBeginTime;
    }

    public Date getGgEndTime() {
        return ggEndTime;
    }

    public void setGgEndTime(Date ggEndTime) {
        this.ggEndTime = ggEndTime;
    }

    public int getGgType() {
        return ggType;
    }

    public void setGgType(int ggType) {
        this.ggType = ggType;
    }

    public String getGgContent() {
        return ggContent;
    }

    public void setGgContent(String ggContent) {
        this.ggContent = ggContent;
    }

    public String getGgNum() {
        return ggNum;
    }

    public void setGgNum(String ggNum) {
        this.ggNum = ggNum;
    }

    public String getGyggId() {
        return gyggId;
    }

    public void setGyggId(String gyggId) {
        this.gyggId = gyggId;
    }

    public Map getAttachments() {
        return attachments;
    }

    public void setAttachments(Map attachments) {
        this.attachments = attachments;
    }
}
