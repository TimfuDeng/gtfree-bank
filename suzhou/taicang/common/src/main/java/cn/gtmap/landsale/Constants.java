/*
 * Project:  hydroplat-parent
 * Module:   hydroplat-common
 * File:     Constants.java
 * Modifier: yangxin
 * Modified: 2014-06-11 22:33
 *
 * Copyright (c) 2014 Mapjs All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent
 * or the registration of a utility model, design or code.
 */
package cn.gtmap.landsale;


import cn.gtmap.egovplat.core.bean.Dictable;
import cn.gtmap.egovplat.core.bean.Titleable;
import cn.gtmap.egovplat.core.util.EnumUtils;

import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 12-9-26
 */
public final class Constants {
    public static final String USER_ADMIN_ID = "0";
    //资源状态
    public final static int ResourceEditStatusInput=0;   //编辑
    public final static int ResourceEditStatusPreRelease=1;   //申请发布
    public final static int ResourceEditStatusRelease=2;   //发布
    public final static int ResourceEditStatusBreak=3;   //中止
    public final static int ResourceEditStatusStop=4;   //终止
    public final static int ResourceEditStatusOver=9;   //结束
    //交易状态
    public final static int ResourceStatusJingJia=1;   //正在竞价
    public final static int ResourceStatusGuaPai=10;   //挂牌
    public final static int ResourceStatusGongGao=20;   //公告
    public final static int ResourceStatusChengJiao=30;   //成交
    public final static int ResourceStatusLiuBiao=31;   //流拍

    //资源类别
    public final static int ResourceTypeLand=0;   //国有建设用地使用权
    public final static int ResourceTypeCaiKuang=1;   //采矿权
    public final static int ResourceTypeTanKuang=2;   //探矿权

    //报价类型
    public final static int OfferTypeGuaPai=0;   //挂牌报价
    public final static int OfferJingJia=1;   //限时竞价报价
    public final static int OfferXianjia=2;   //达到最高限价后报价

    //报价错误类型
    public final static String OfferErrorOutTime="报价超出截止时间！";
    public final static String OfferErrorMin="报价低于或等于最高价！";
    public final static String OfferErrorLimit="挂牌截止期1个小时，不接受报价！";
    public final static String OfferErrorChange="地块状态发送变化，系统不接受报价！！";
    public final static String OfferErrorNoPay="您未交纳保证金，系统不接受报价！！";
    public final static String OfferErrorNoTop="未进入限地价竞保障房报价！";
    public final static String OfferErrorTop="已达最高限价，不接受报价！";
    //公告类型
    public final static int GgTypeZb=21;  //招标
    public final static int GgTypePm=22; //拍卖;
    public final static int GgTypeGp=23; //挂牌;

    //货币类型
    public final static String MoneyCNY="CNY";
    public final static String MoneyUSD="USD";
    //竞买步骤
    public final static int StepBaoMing=1;
    public final static int StepBaoZhengJin=2;
    public final static int StepOver=3;
    //竞买类型
    public final static int ApplyTypeOne=0; //独立竞买(默认)
    public final static int ApplyTypeMulti=1; //联合竞买

    //空图片默认路径
    public static final String BLANK_IMAGE_PATH="static/image/blank.jpg";

    //图片访问根路径
    public static final String IMAGE_BASE_PATH="file/view.f?fileId=";

    //客户端缩略图默认分辨率
    public static final String CLIENT_THUMBNAIL_RESOLUTION = "402_320";
    //客户端缩略图默认缓存设置
    public static final String CLIENT_THUMBNAIL_CACHE_CONTROL = "max-age=315360000, must-revalidate";

    /**
     * 附件类型
     */
    public enum FileType {
        THUMBNAIL("缩略图","THUMBNAIL"),
        QT("其他","QT");
        private String title;
        private String code;
        FileType(String title,String code) {
            this.title = title;
            this.code = code;
        }
        @Override
        public String toString() {
            return title;
        }

        public String getCode() {
            return code;
        }
    }

    public enum LogCategory{
        USER_LOGIN("用户登录"),
        USER_LOGOUT("用户登出"),
        DATA_VIEW("数据浏览"),
        DATA_SAVE("数据保存"),
        DATA_DELETE("数据删除"),
        DATA_RECEIVE("数据接收"),
        CUSTOM_APPLY("用户报名"),
        CUSTOM_OFFER("用户报价"),
        OTHER("其他操作");
        private String title;
        LogCategory(String title) {
            this.title = title;
        }
        @Override
        public String toString() {
            return title;
        }
    }

    public enum LogProducer{
        ADMIN("管理系统"),
        CLIENT("客户端系统");
        private String title;
        LogProducer(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum UserType{
        MANAGER("后台管理者"),
        CLIENT("前台交易用户");
        private String title;

        UserType(String title) {
            this.title = title;
        }
    }

    public enum UserClass{
        COMPANY("企业"),
        PERSON("自然人");
        private String title;

        UserClass(String title) {
            this.title = title;
        }
    }

    /**
     * @作者 王建明
     * @创建日期 2015-10-28
     * @创建时间 9:21
     * @描述 —— 地块审核类型
     */
    public enum QualificationType{
        POST_TRIAL("资格后审"),
        PRE_TRIAL("资格前审");
        private String title;

        QualificationType(String title) {
            this.title = title;
        }
    }

    /**
     * @作者 王建明
     * @创建日期 2015-10-28
     * @创建时间 9:20
     * @描述 —— 地块竞买审核状态
     */
    public enum TrialType{
        NONE_COMMIT_TRIAL("未提交审核"),
        COMMIT_TO_TRIAL("资格提交审核中"),
        PASSED_TRIAL("资格审核通过"),
        FAILED_TRIAL("资格审核失败");
        private String title;

        TrialType(String title) {
            this.title = title;
        }
    }

    public enum Operation{
        VIEW("view"),
        EDIT("edit");
        private String title;

        Operation(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum BidType{
        ZJ_WANYUAN("总价（万元）","01"),
        YUAN_M("元/平方米","02"),
        YUAN_JZ_M("元/建筑平方米","03"),
        WANYUAN_MU("万元/亩","04");
        private String title;
        private String code;
        BidType(String title,String code) {
            this.title = title;
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum BidRule{
        JGZD("价高者得",1),
        ZHTJYXZD("综合条件优者得",2);
        private String title;
        private int code;
        BidRule(String title,int code) {
            this.title = title;
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum OfferType{
        BIDDING("招标"),
        AUCTION("拍卖"),
        LISTING("挂牌"),
        AGREEMENT("协议");
        private String title;

        OfferType(String title) {
            this.title = title;
        }
        @Override
        public String toString() {
            return title;
        }
    }

    public enum LandUse implements Titleable, Dictable {
        SFYD("商服用地","05"),
        GKCCYD("工矿仓储用地","06"),
        GYYD("工业用地","08"),
        CCYD("仓储用地","09"),
        ZZYD("住宅用地","07"),
        SZYD("商住用地","10"),
        QT("其他用地","99");
        private String title;
        private String code;
        LandUse(String title,String code) {
            this.title = title;
            this.code = code;
        }
        @Override
        public String getTitle() {
            return title;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toString() {
            return title;
        }

        @Override
        public Map<String, String> getItems() {
            return EnumUtils.getDictMap(LandUse.class);
        }
    }

    public enum ResourceOperateStep{
        GG("即将公告","01"),
        GP("即将挂牌","02"),
        GPONEHOUR("挂牌截止前1小时","03"),
        XS("即将限时","04"),
        OVER("即将结束","99");
        private String title;
        private String code;
        ResourceOperateStep(String title,String code) {
            this.title = title;
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum CaSignatureAlgo{
        RSA_MD5("RSA-MD5"),
        RSA_SHA1("RSA-SHA1");
        private String title;

        CaSignatureAlgo(String title) {
            this.title = title;
        }
        @Override
        public String toString() {
            return title;
        }
    }

    public enum CaOriginalDateType{
        ORIGINAL("原文"),
        DIGEST("二进制格式摘要"),
        DIGEST_HEX("hex格式摘要");

        private String title;

        CaOriginalDateType(String title) {
            this.title = title;
        }
        @Override
        public String toString() {
            return title;
        }
    }

    private Constants() {
    }
}
