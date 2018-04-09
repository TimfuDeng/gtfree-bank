package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.service.MaterialCenterService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import sun.security.util.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 网上交易出让公告实体对象
 * Created by jiff on 14/12/24.
 */
@Entity
@Table(name = "trans_crgg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransCrgg implements Serializable {
    private static final long serialVersionUID = -9137292468621763148L;
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String ggId;
    @Column(nullable = false,length = 255)
    private String ggTitle;  //公告标题

    @Column(length = 50)
    private String regionCode;  //所属行政区


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date ggBeginTime;  //公告开始时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date ggEndTime;  //公告结束时间

    @Column(nullable = false)
    private int ggType;  //公告类型(接口字段未用到)

    @Column (nullable = true,columnDefinition ="CLOB")
    private String ggContent;  //公告内容

    @Column (nullable = false)
    private String ggNum;  //公告编号

    @Column
    private String gyggId;//土地市场网土地供应GUID
    //==========================================苏州土地交易新增字段
    @Column(length = 20)
    @Field("出让方式：01招标，02挂牌，03拍卖")
    private String remiseType;
    //private Constants.RemiseType remiseType= Constants.RemiseType.LISTING;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field("发布时间")
    private Date postDate;

    @Column(length = 20)
    @Field("发布人")
    private String passMan;


    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field("公告类别：0工业用地公告，1经营性用地公告，2其它公告，3协议出让类（划拨）公告")
    private int afficheType;

    @Column(length = 4)
    @Field("资源类别：01国有建设用地使用权，02采矿权，03探矿权")
    private String resourceLb;
   // private Constants.resourceLb resourceLb=Constants.resourceLb.SYQ;

    @Column(length = 100)
    @Field("出让单位")
    private String remiseUnit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field("报名开始时间")
    private Date signStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field("报名结束时间")
    private Date signEndTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field("投标开始时间")
    private Date bidStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field("投标结束时间")
    private Date bidEndTime;


    @Column
    @Field("中标原则01-价高者得，02-综合评价高者得")
    private String winStandard;
    //private Constants.WinStandard winStandard= Constants.WinStandard.JGZD;


    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field("缴纳保证金截止时间")
    private Date paymentEndTime;


    @Column(length = 80)
    @Field("联系人")
    private String linkMan;

    @Column(length = 500)
    @Field("联系地址")
    private String linkAddress;

    @Column(length = 60)
    @Field("联系电话")
    private String linkPhone;

    @Column(length = 100)
    @Field("几幅地块")
    private String parcelMsg;


    @Column(length = 50)
    @Field("公告类型：21-挂牌，22-拍卖，23-招标，100-公开公告，101-公告调整，102-其它公告")
    private String ggLx;


    @Column(length = 20)
    @Field("其它公告类型：1-一级开发项目招标公告，2-闲置土地处置公告，3-征收土地公告，4-其它,401-中止公告，402-终止公告，403-恢复公告")
    private String qtGglx;
   // private Constants.QtGglx qtGglx=Constants.QtGglx.FREELAND;

    @Column(length = 1500)
    @Field("竞买资格-申请人其它条件")
    private String bidTerm;

    @Column(length = 1000)
    @Field("备注")
    private String memo;

    @Column(length = 1000)
    @Field("其它需要公告的事项")
    private String otherTerm;

    @Column(length = 1000)
    @Field("其它需要说明的地宗情况")
    private String otherMsg;

    @Column(length = 100)
    @Field("批准机关")
    private String pzJg;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field(value = "0-未发布 1-发布")
    private int crggStauts;

    @Transient
    private List<TransResource> resourceList;

    @Transient
    private List<TransFile> attachmentList;

    public int getCrggStauts() {
        return crggStauts;
    }

    public void setCrggStauts(int crggStauts) {
        this.crggStauts = crggStauts;
    }

    public String getRemiseType() {
        return remiseType;
    }

    public void setRemiseType(String remiseType) {
        this.remiseType = remiseType;
    }

    public String getResourceLb() {
        return resourceLb;
    }

    public void setResourceLb(String resourceLb) {
        this.resourceLb = resourceLb;
    }

    public String getWinStandard() {
        return winStandard;
    }

    public void setWinStandard(String winStandard) {
        this.winStandard = winStandard;
    }

    public String getGgLx() {
        return ggLx;
    }

    public void setGgLx(String ggLx) {
        this.ggLx = ggLx;
    }

    public String getQtGglx() {
        return qtGglx;
    }

    public void setQtGglx(String qtGglx) {
        this.qtGglx = qtGglx;
    }





    public String getPzJg() {
        return pzJg;
    }

    public void setPzJg(String pzJg) {
        this.pzJg = pzJg;
    }

    public String getOtherMsg() {
        return otherMsg;
    }

    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg;
    }

    public String getOtherTerm() {
        return otherTerm;
    }

    public void setOtherTerm(String otherTerm) {
        this.otherTerm = otherTerm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBidTerm() {
        return bidTerm;
    }

    public void setBidTerm(String bidTerm) {
        this.bidTerm = bidTerm;
    }


    public String getParcelMsg() {
        return parcelMsg;
    }

    public void setParcelMsg(String parcelMsg) {
        this.parcelMsg = parcelMsg;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public Date getPaymentEndTime() {
        return paymentEndTime;
    }

    public void setPaymentEndTime(Date paymentEndTime) {
        this.paymentEndTime = paymentEndTime;
    }



    public Date getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(Date bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public Date getBidStartTime() {
        return bidStartTime;
    }

    public void setBidStartTime(Date bidStartTime) {
        this.bidStartTime = bidStartTime;
    }

    public Date getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Date signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Date getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(Date signStartTime) {
        this.signStartTime = signStartTime;
    }

    public String getRemiseUnit() {
        return remiseUnit;
    }

    public void setRemiseUnit(String remiseUnit) {
        this.remiseUnit = remiseUnit;
    }



    public int getAfficheType() {
        return afficheType;
    }

    public void setAfficheType(int afficheType) {
        this.afficheType = afficheType;
    }

    public String getPassMan() {
        return passMan;
    }

    public void setPassMan(String passMan) {
        this.passMan = passMan;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }



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

    public List<TransResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<TransResource> resourceList) {
        this.resourceList = resourceList;
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

    public List<TransFile> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<TransFile> attachmentList) {
        this.attachmentList = attachmentList;
    }

}
