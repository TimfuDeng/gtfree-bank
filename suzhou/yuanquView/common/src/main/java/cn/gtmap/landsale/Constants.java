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
    public final static String OfferErrorChange="地块状态发生变化，系统不接受报价！";
    public final static String OfferErrorNoPay="您未交纳保证金，系统不接受报价！";
    public final static String OfferErrorNoTop="未进入限地价竞保障房报价！";
    public final static String OfferErrorTop="已达最高限价，不接受报价！";
    //公告类型
    public final static int GgTypeZb=21;  //招标
    public final static int GgTypePm=22; //拍卖;
    public final static int GgTypeGp=23; //挂牌;

    //货币类型
    public final static String MoneyCNY="CNY";
    public final static String MoneyUSD="USD";
    public final static String MoneyHKD="HKD"; //港币

    //竞买步骤
    public final static int StepBaoMing=1;//报名-选择币种
    public final static int StepQualified=2;//资格审核-选择银行
    public final static int StepBaoZhengJin=3;//审核成功，缴纳保证金
    public final static int StepOver=4;//缴纳完成，进行拍卖
    public final static int StepQualifiedFaile=5;//审核失败，并查看原因

   /* public final static int StepBaoMing=1;//报名
    public final static int StepBaoZhengJin=2;//缴纳保证金
    public final static int StepOver=3;*/
    //竞买类型
    public final static int ApplyTypeOne=0; //独立竞买(默认)
    public final static int ApplyTypeMulti=1; //联合竞买

    //记录状态
    public final static int DataFlagUnDel=0;//0-未删除
    public final static int DataFlagDel=1;//1-删除
    //新闻
    public final static int DEPLOYED=1;//新闻已发布
    public final static int UNDEPLOY=0;//新闻未发布

    //互动交流
    public final static int COMM_UNVERFY=0;//互动交流未通过
    //组织机构类型
   // 1-国土局 2-土地储备中心 3-金融机构 4-监管部门 5-其它机构
    public final static String GTJ="国土局";
    public final static String TDCBZX="土地储备中心";
    public final static String JRJG="金融机构";
    public final static String JGBM="监管部门";
    public final static String QTJG="其它机构";

    //文件管理
    public final static String FileKey="ziliao";//ziliao-文件管理的资料

    //空图片默认路径
    public static final String BLANK_IMAGE_PATH="static/image/blank.jpg";

    public static final String IMAGE_PATH="static/image/";

    //图片访问根路径
    public static final String IMAGE_BASE_PATH="file/view.f?fileId=";

    //客户端缩略图默认分辨率
    public static final String CLIENT_THUMBNAIL_RESOLUTION = "402_320";
    //客户端缩略图默认缓存设置
    public static final String CLIENT_THUMBNAIL_CACHE_CONTROL = "max-age=315360000, must-revalidate";

    //公告类别：0工业用地公告，1经营性用地公告，2其它公告，3协议出让类（划拨）公告
    public final static int GgGyYd=0;
    public final static int GgJyYd=1;
    public final static int GgQtYd=2;
    public final static int GgXyYd=3;

    //DES加密与解密
    public final static String DES = "DES";
    public final static String key = "wang!@#$";


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

    public enum RemiseType{
        BIDDING("招标"),
        AUCTION("拍卖"),
        LISTING("挂牌");
        private String title;

        RemiseType(String title) {
            this.title = title;
        }
        @Override
        public String toString(){
            return title;
        }
    }
    public enum WinStandard{
        //最大限度地满足招标文件中规定的各项
        JGZD("价高者得","01"),
        ZDMZ("综合评价高者得","02");
        private String title;
        private String code;

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }

        WinStandard(String title, String code) {
            this.title = title;
            this.code = code;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum GgLx implements Titleable,Dictable{
        GGGP("挂牌","23"),
        GGPM("拍卖","22"),
        GGZB("招标","21"),
        GGGK("公开公告","100"),
        GGTZ("公告调整","101"),
        GGQT("其它公告","102");
        private String title;
        private  String code;

        GgLx(String title, String code) {
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
            return EnumUtils.getDictMap(GgLx.class);
        }
    }

    public enum QtGglx implements Titleable,Dictable{

        LEVELONE("一级开发项目招标公告","1"),
        FREELAND("闲置土地处置公告","2"),
        LEVYLAND("征收土地公告","3"),
        OTHER("其它","4"),
        BREAK("中止公告","401"),
        END("终止公告","402"),
        REGAIN("恢复公告","403");
        private String title;
        private String code;

        QtGglx(String title, String code) {
            this.title = title;
            this.code = code;
        }


        @Override
        public String toString() {
            return title;
        }

        @Override
        public Map<String, String> getItems() {
            return EnumUtils.getDictMap(QtGglx.class);
        }

        @Override
        public String getTitle() {
            return title;
        }

        public String getCode() {
            return code;
        }
    }


    public enum LandUse implements Titleable, Dictable {
        SFYD("商服用地","05"),
        GKCCYD("工矿仓储用地","06"),
        GYYD("工业用地","061"),
        CKYD("采矿用地","062"),
        CCYD("仓储用地","063"),

        GY("工业","064"),
        CC("仓储","065"),
        KJ("科教","066"),
        GYBZCF("工业(标准厂房)","067"),
        GYGBZCF("工业(高标准厂房)","068"),
        GYYF("工业(研发)","069"),
        GYWHCY("工业(文化创意)","070"),
        GYZBJJ("工业(总部经济)","071"),
        KJYF("科教(研发)","072"),
        KJWHCY("科教(文化创意)","073"),
        KJZBJJ("科教(总部经济)","074"),

        ZZYD("住宅用地","07"),
        GGGLYGGFWYD("公共管理与公共服务用地","08"),
        TSYD("特殊用地","09"),
        JTYSYD("交通运输用地","10"),
        SYJSLSSYD("水域及水利设施用地","11"),
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

    public enum resourceLb{
       // 01国有建设用地使用权，02采矿权，03探矿权
        SYQ("国有建设用地使用权","01"),
        CKQ("采矿权","02"),
        TKQ("探矿权","03");
        private String title;
        private String code;

        resourceLb(String title, String code) {
            this.title = title;
            this.code = code;
        }
        public String getCode(){ return code;}

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
