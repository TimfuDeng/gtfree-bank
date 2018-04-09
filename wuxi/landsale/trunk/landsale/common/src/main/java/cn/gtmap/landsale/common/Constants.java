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
package cn.gtmap.landsale.common;


import cn.gtmap.egovplat.core.bean.Dictable;
import cn.gtmap.egovplat.core.bean.Titleable;
import cn.gtmap.egovplat.core.util.EnumUtils;

import java.util.Map;

/**
 * .
 * <p>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 12-9-26
 */
public final class Constants {
    public static final String USER_ADMIN_ID = "0";
    //资源状态
    public final static int RESOURCE_EDIT_STATUS_INPUT = 0;   //编辑
    public final static int RESOURCE_EDIT_STATUS_PRE_RELEASE = 1;   //申请发布
    public final static int RESOURCE_EDIT_STATUS_RELEASE = 2;   //发布
    public final static int RESOURCE_EDIT_STATUS_BREAK = 3;   //中止
    public final static int RESOURCE_EDIT_STATUS_STOP = 4;   //终止
    public final static int RESOURCE_EDIT_STATUS_OVER = 9;   //结束
    //交易状态
    public final static int RESOURCE_STATUS_JING_JIA = 1;   //正在竞价
    public final static int RESOURCE_STATUS_MAX_OFFER = 11;   //最高限价
    public final static int RESOURCE_STATUS_GUA_PAI = 10;   //挂牌
    public final static int RESOURCE_STATUS_GONG_GAO = 20;   //公告
    public final static int RESOURCE_STATUS_CHENG_JIAO = 30;   //成交
    public final static int RESOURCE_STATUS_LIU_BIAO = 31;   //流拍

    //资源类别
    public final static int RESOURCE_TYPE_LAND = 0;   //国有建设用地使用权
    public final static int RESOURCE_TYPE_CAI_KUANG = 1;   //采矿权
    public final static int RESOURCE_TYPE_TAN_KUANG = 2;   //探矿权

    //报价类型
    public final static int OFFER_TYPE_GUA_PAI = 0;   //挂牌报价
    public final static int OFFER_JING_JIA = 1;   //限时竞价报价
    public final static int OFFER_XIANJIA = 2;   //达到最高限价后报价

    //报价错误类型
    public final static String OFFER_ERROR_OUT_TIME = "报价超出截止时间！";
    public final static String OFFER_ERROR_MIN = "报价低于或等于最高价！";
    public final static String OFFER_ERROR_LIMIT = "挂牌截止期1个小时，不接受报价！";
    public final static String OFFER_ERROR_CHANGE = "地块状态发送变化，系统不接受报价！！";
    public final static String OFFER_ERROR_NO_PAY = "您未交纳保证金，系统不接受报价！！";
    public final static String OFFER_ERROR_NO_TOP = "未进入限地价竞保障房报价！";
    public final static String OFFER_ERROR_TOP = "已达最高限价，不接受报价！";
    public final static String OFFER_ERROR_LACK_RRESOURCE_ID = "缺少地块Id！";

    //公告类型
    public final static int GG_TYPE_ZB = 21;  //招标
    public final static int GG_TYPE_PM = 22; //拍卖;
    public final static int GG_TYPE_GP = 23; //挂牌;

    //出让公告 中止公告
//    public final static int GgTypeZz = 9; //中止;
    public final static int AFFICHE_TYPE_ZZ = 8; //中止;
    public final static int AFFICHE_TYPE_ZZ_2 = 9; //终止;

    //货币类型
    public final static String MONEY_CNY ="CNY";
    public final static String MONEY_USD ="USD";
    public final static String MONEY_HKD ="HKD"; //港币

    //竞买步骤
    public final static int STEP_BAO_MING = 1;//报名
    public final static int STEP_QUALIFIED =2;//资格审核-选择银行
    public final static int STEP_BAO_ZHENG_JIN = 3;
    public final static int STEP_OVER = 4;
    public final static int STEP_QUALIFIED_FAILE =5;//审核失败，并查看原因

    //竞买类型
    public final static int APPLY_TYPE_ONE = 0; //独立竞买(默认)
    public final static int APPLY_TYPE_MULTI = 1; //联合竞买

    //空图片默认路径
    public static final String BLANK_IMAGE_PATH = "static/image/blank.jpg";

    //图片访问根路径
    public static final String IMAGE_BASE_PATH = "file/view.f?fileId=";

    //客户端缩略图默认分辨率
    public static final String CLIENT_THUMBNAIL_RESOLUTION = "402_320";
    //客户端缩略图默认缓存设置
    public static final String CLIENT_THUMBNAIL_CACHE_CONTROL = "max-age=315360000, must-revalidate";

    //文件管理
    public final static String FILE_KEY = "ziliao";//ziliao-文件管理的资料

    /**
     * 是 否
     */
    public static class Whether {
        public static final Integer NO = 0;
        public static final Integer YES = 1;
    }
    

    /**
     * 最新信息 历史信息
     */
    public static class CURRENT_STATUS {
        public static final Integer CURRENT = 0;
        public static final Integer HISTORY = 1;
    }

    /**
     * 资格审核
     */
    public static class Qualified_Status {
        /**
         * 审核状态 0：未审核
         */
        public static final Integer NONE = 0;

        /**
         * 审核状态 1：通过
         */
        public static final Integer PASS = 1;

        /**
         * 审核状态 被强制退回：2
         */
        public static final Integer NO_PASS = 2;

    }

    /**
     * 报价单位
     */
    public static class OFFER_UNIT {
        // 万元（总价）
        public static final Integer WY = 0;
        // 元/平方米(地面价)
        public static final Integer Y_M2_DMJ = 1;
        // 元/平方米(楼面价)
        public static final Integer Y_M2_LMJ = 2;
        // 万元/亩
        public static final Integer WY_MU = 3;
    }

    /**
     * 一次报价 默认时间
     */
    public static class ONE_PARAM_DEFAULT_TIME {
        // 等待时间
        public static final Integer WAIT_TIME = 4;
        // 问询时间
        public static final Integer QUERY_TIME = 5;
        // 报价时间
        public static final Integer PRICE_TIME = 4;
    }

    /**
     * 一次报价 默认时间
     */
    public static class SUCCESS_OFFER_CHOOSE {
        // 一次报价
        public static final Integer YCBJ = 0;
        // 摇号
        public static final Integer YH = 1;
        // 正常
        public static final Integer NORMAL = 9;
    }

    public static final int YH_POST_STATUS_NO = 0; //摇号未发布
    public static final int YH_POST_STATUS_YES = 1; //摇号已发布

    /**
     * 附件类型
     */
    public enum FileType {
        //缩略图
        THUMBNAIL("缩略图", "THUMBNAIL"),
        //其他
        QT("其他", "QT");
        private String title;
        private String code;

        FileType(String title, String code) {
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

    /**
     * 用途级别
     */
    public static class YT_LEVEL {

        private static Integer LEVEL_1 = 1;
        private static Integer LEVEL_2 = 2;
        private static Integer LEVEL_3 = 3;
        private static Integer LEVEL_4 = 4;
        private static Integer LEVEL_5 = 5;
        private static Integer LEVEL_6 = 6;

    }

    public enum LogCategory {
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

    public enum LogProducer {
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

    public enum UserType {
        MANAGER("后台管理者"),
        CLIENT("前台交易用户");
        private String title;

        UserType(String title) {
            this.title = title;
        }
    }


    public enum RegisterType {
        COMPANY("企业"),
        PERSON("自然人");
        private String title;

        RegisterType(String title) {
            this.title = title;
        }
    }


    public enum UserClass {
        COMPANY("企业"),
        PERSON("自然人");
        private String title;

        UserClass(String title) {
            this.title = title;
        }
    }

    public enum Operation {
        //查看
        VIEW("view"),
        //编辑
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

    public enum BidType {
        ZJ_WANYUAN("万元（总价）", "01"),
        YUAN_M("元/平方米(地面价)", "02"),
        YUAN_JZ_M("元/平方米(楼面价)", "03"),
        WANYUAN_MU("万元/亩", "04");
        private String title;
        private String code;

        BidType(String title, String code) {
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

    public enum BidRule {
        JGZD("价高者得", 1),
        ZHTJYXZD("综合条件优者得", 2);
        private String title;
        private int code;

        BidRule(String title, int code) {
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

    public enum OfferType {
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

        LandUse(String title, String code) {
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

    public enum MaxOfferChoose {
        YCBJ("一次报价", 1),
        YH("摇号", 2);

        private String title;
        private int code;

        MaxOfferChoose(String title, int code) {
            this.title = title;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum ResourceOperateStep {
        GG("即将公告", "01"),
        GP("即将挂牌", "02"),
        GPONEHOUR("挂牌截止前1小时", "03"),
        XS("即将限时", "04"),
        OVER("即将结束", "99");
        private String title;
        private String code;

        ResourceOperateStep(String title, String code) {
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

    public enum CaSignatureAlgo {
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

    public enum CaOriginalDateType {
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
