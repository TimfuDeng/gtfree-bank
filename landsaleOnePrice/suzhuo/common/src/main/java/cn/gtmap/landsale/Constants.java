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
    public static final String USER_ADMIN_ID = "0001";
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
    public enum UserType{
        MANAGER("后台管理者"),
        CLIENT("前台交易用户");
        private String title;

        UserType(String title) {
            this.title = title;
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


    private Constants() {
    }
}
