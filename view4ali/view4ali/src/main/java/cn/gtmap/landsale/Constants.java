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

public final class Constants {
    //结束状态
    public final static int StatusChengJiao=30;   //成交
    public final static int StatusLiuBiao=31;   //流拍
    public final static int StatusBreak=40;   //中止
    public final static int StatusStop=50;   //终止

    //资源类别
    public final static int ResourceTypeLand=0;   //国有建设用地使用权
    public final static int ResourceTypeCaiKuang=1;   //采矿权
    public final static int ResourceTypeTanKuang=2;   //探矿权

    //报价类型
    public final static int OfferTypeGuaPai=0;   //挂牌报价
    public final static int OfferJingJia=1;   //限时竞价报价
    public final static int OfferXianjia=2;   //达到最高限价后报价

    //公告类型
    public final static int GgTypeZb=21;  //招标
    public final static int GgTypePm=22; //拍卖;
    public final static int GgTypeGp=23; //挂牌;


    public final static String LandUseGongYe="06";   //工矿仓储用地
    public final static String LandUseJingYing="05";   //商服用地\07住宅用地等经营性用地



}
