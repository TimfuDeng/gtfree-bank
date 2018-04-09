<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>地块详情</title>

    <link href="${base}/css/xiangxi.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css"/>
<#--<link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">-->
    <link rel="stylesheet" href="${base}/thridparty/leaflet/leaflet.css" />
    <link rel="stylesheet" href="${base}/js/dist/view.css" />
</head>

<body>
<div class="weizhi">
    <div class="weizhi_in">
        <img src="${base}/images/zuobiao.png">
        <p>当前位置：<a href="${base}/my/menu">我的交易</a> > <a href="${base}/my/resource/index">参与地块</a> > ${resource.resourceCode!} </p>
    </div>
</div>
<div class="main_all">

    <div class="main_up">
        <div class="main_up_left">
            <#--价格 时间-->
            <div class="main_up_left_top">
                <#--发布状态或结束状态-->
                <#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
                    <#--公告期-->
                    <#if resource.resourceStatus == 20>
                        <div class="big_data1">
                            <p>起始价格：</p>
                            <span>${resource.beginOffer!}<@unitText offerUnit = resource.offerUnit/></span>
                        </div>
                        <div class="big_data3">
                            <p>距离挂牌开始：</p>
                            <span class="time" id="span_${resource.resourceId}" value="${resource.gpBeginTime?long}"></span>
                        </div>
                    <#--挂牌期-->
                    <#elseif resource.resourceStatus == 10>
                        <div class="big_data1">
                            <p>当前价格：</p>
                            <span>
                                <#if maxOffer??>
                                    ${maxOffer.offerPrice}
                                 <#else>
                                    ${resource.beginOffer}
                                </#if><@unitText offerUnit = resource.offerUnit/>
                            </span>
                        </div>
                        <#--挂牌期-->
                        <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                            <div class="big_data3">
                                <p>距离挂牌结束：</p>
                                <span class="time" id="span_${resource.resourceId}" value="${resource.gpEndTime?long}"></span>
                            </div>
                        <#--挂牌结束 竞价未开始-->
                        <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                            <div class="big_data3">
                                <p>距离限时开始：</p>
                                <span class="time" id="span_${resource.resourceId}" value="${resource.xsBeginTime?long}"></span>
                            </div>
                        </#if>
                    <#--竞价期-->
                    <#elseif resource.resourceStatus == 1>
                        <div class="big_data1">
                            <p>当前价格：</p>
                            <span>
                                <#if maxOffer??>
                                    ${maxOffer.offerPrice}
                                 <#else>
                                    ${resource.beginOffer}
                                </#if><@unitText offerUnit = resource.offerUnit/>
                            </span>
                        </div>
                        <div class="big_data3">
                            <p>等待限时结束：</p>
                            <span>--</span>
                        </div>
                    <#--最高限价-->
                    <#elseif resource.resourceStatus == 11>
                        <div class="big_data1">
                            <p>当前价格：</p>
                            <span>
                                <#if maxOffer??>
                                    ${maxOffer.offerPrice}
                                 <#else>
                                ${resource.beginOffer}
                                </#if><@unitText offerUnit = resource.offerUnit/>
                            </span>
                        </div>
                        <#--挂牌期-->
                        <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                            <div class="big_data3">
                                <p>距离挂牌结束：</p>
                                <span class="time" id="span_${resource.resourceId}" value="${resource.gpEndTime?long}"></span>
                            </div>
                        <#--挂牌结束 竞价未开始-->
                        <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                            <div class="big_data3">
                                <p>距离限时开始：</p>
                                <span class="time" id="span_${resource.resourceId}" value="${resource.xsBeginTime?long}"></span>
                            </div>
                        <#else>
                            <#--判断最高限价后的成交方式-->
                            <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
                                <div class="big_data3">
                                    <p>等待一次报价结果：</p>
                                    <span>--</span>
                                </div>
                            <#elseif resource.maxOfferChoose?? && resource.maxOfferChoose.code==2>
                                <div class="big_data3">
                                    <p>等待摇号结果：</p>
                                    <span>--</span>
                                </div>
                            </#if>
                        </#if>
                    <#--成交-->
                    <#elseif resource.resourceStatus == 30>
                        <#--<div class="big_data1">
                            <p>当前价格：</p>
                            <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                        </div>-->
                        <#assign resouerceVerify = ResourceUtil.getTransResourceVerify(resource.resourceId)!/>
                        <#--判断地块是否是最高限价-->
                        <#--目前一次报价及摇号成交均使用后审方式,故无论何种成交方式均无差别,此处保留原有判断逻辑-->
                        <#if resource.maxOfferExist == 0>
                            <!--获取成交审核信息-->
                            <#if resouerceVerify != null>
                                <!--未审核-->
                                <#if resouerceVerify.verifyStatus == 0>
                                    <div class="big_data1">
                                        <p>当前价格：</p>
                                        <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                    </div>
                                    <div class="big_data3">
                                        <span>本场竞买已成交,等待审核结果！</span>
                                    </div>
                                <#elseif resouerceVerify.verifyStatus == 1>
                                    <#assign successPrice = PriceUtil.getSuccessOffer(resource.offerId)/>
                                    <div class="big_data1">
                                        <p>成交价：</p>
                                        <span>${successPrice.offerPrice!}<@unitText offerUnit = resource.offerUnit/></span>
                                    </div>
                                    <div class="big_data3">
                                        <p>竞得人：</p>
                                        <span>${UserUtil.getUserName(successOffer.userId!)}</span>
                                    </div>
                                <#elseif resouerceVerify.verifyStatus == 2>
                                    <div class="big_data1">
                                        <p>当前价格：</p>
                                        <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                    </div>
                                    <div class="big_data3">
                                        <span>本场竞买因：${resouerceVerify.verifySuggestion!}原因，成交审核未通过。</span>
                                    </div>
                                </#if>
                            <!--未审核-->
                            <#else>
                                <#--<div class="big_data1">-->
                                    <#--<p>当前价格：</p>-->
                                    <#--<span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>-->
                                <#--</div>-->
                                <div class="big_data3">
                                    <span>本场竞买已成交,等待审核结果！</span>
                                </div>
                            </#if>
                        <#else>
                            <#assign maxOffer=PriceUtil.getTransResourceOffer(resource.offerId)/>
                            <#--有最高限价 但是没达到 正常成交了-->
                            <#if maxOffer.offerType != -1 >
                                <!--获取成交审核信息-->
                                <#if resouerceVerify != null>
                                    <!--未审核-->
                                    <#if resouerceVerify.verifyStatus == 0>
                                        <div class="big_data1">
                                            <p>当前价格：</p>
                                            <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                        </div>
                                        <div class="big_data3">
                                            <span>本场竞买已成交,等待审核结果！</span>
                                        </div>
                                    <#elseif resouerceVerify.verifyStatus == 1>
                                        <#assign successPrice = PriceUtil.getSuccessOffer(resource.offerId)/>
                                        <div class="big_data1">
                                            <p>成交价：</p>
                                            <span>${successPrice.offerPrice!}<@unitText offerUnit = resource.offerUnit/></span>
                                        </div>
                                        <div class="big_data3">
                                            <p>竞得人：</p>
                                            <span>${UserUtil.getUserName(successOffer.userId!)}</span>
                                        </div>
                                    <#elseif resouerceVerify.verifyStatus == 2>
                                        <div class="big_data1">
                                            <p>当前价格：</p>
                                            <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                        </div>
                                        <div class="big_data3">
                                            <span>本场竞买因：${resouerceVerify.verifySuggestion!}原因，成交审核未通过。</span>
                                        </div>
                                    </#if>
                                    <!--未审核-->
                                <#else>
                                    <div class="big_data3">
                                        <div class="big_data1">
                                            <p>当前价格：</p>
                                            <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                        </div>
                                        <span>本场竞买已成交,等待审核结果！</span>
                                    </div>
                                </#if>
                            <#else>
                                <#--有最高限价 达到最高限价  逻辑同正常成交-->
                                <!--获取成交审核信息-->
                                <#if resouerceVerify != null>
                                    <!--未审核-->
                                    <#if resouerceVerify.verifyStatus == 0>
                                        <div class="big_data1">
                                            <p>当前价格：</p>
                                            <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                        </div>
                                        <div class="big_data3">
                                            <span>本场竞买已成交,等待审核结果！</span>
                                        </div>
                                    <#elseif resouerceVerify.verifyStatus == 1>
                                        <#assign successPrice = PriceUtil.getSuccessOffer(resource.offerId)/>
                                            <div class="big_data1">
                                                <p>成交价：</p>
                                                <#--一次报价-->
                                                <#if resource.maxOfferChoose.code == 1>
                                                    <span>${successPrice.offerPrice!}万元（总价）</span>
                                                <#else>
                                                    <span>${successPrice.offerPrice!}<@unitText offerUnit = resource.offerUnit/></span>
                                                </#if>
                                            </div>
                                            <div class="big_data3">
                                                <p>竞得人：</p>
                                                <span>${UserUtil.getUserName(successOffer.userId!)}</span>
                                            </div>
                                    <#elseif resouerceVerify.verifyStatus == 2>
                                        <div class="big_data1">
                                            <p>当前价格：</p>
                                            <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                        </div>
                                        <div class="big_data3">
                                            <span>本场竞买因：${resouerceVerify.verifySuggestion!}原因，成交审核未通过。</span>
                                        </div>
                                    </#if>
                                    <!--未审核-->
                                <#else>
                                    <div class="big_data1">
                                        <p>当前价格：</p>
                                        <span>${maxOffer.offerPrice}<@unitText offerUnit = resource.offerUnit/></span>
                                    </div>
                                    <div class="big_data3">
                                        <span>本场竞买已成交,等待审核结果！</span>
                                    </div>
                                </#if>
                            </#if>
                        </#if>

                    <#--流拍-->
                    <#elseif resource.resourceStatus == 31>
                        <div class="big_data1">
                            <span>本场竞买已流拍！</span>
                        </div>
                    </#if>
                <#--发布状态或结束状态-->
                <#elseif resource.resourceEditStatus == 3>
                    <div class="big_data1">
                        <span>本场竞买已中止！</span>
                    </div>
                <#--发布状态或结束状态-->
                <#elseif resource.resourceEditStatus == 4>
                    <div class="big_data1">
                        <span>本场竞买已终止！</span>
                    </div>
                </#if>

                <#--判断报名状态 -->
                <#if resource.resourceEditStatus == 2 && (resource.resourceStatus == 20 || resource.resourceStatus == 10 || resource.resourceStatus == 1 || resource.resourceStatus == 11)>
                    <#if transResourceApply?? && transResourceApply.applyStep==1>
                        <#-- 缴纳保证金开始时间 > 当前时间-->
                        <#if resource.bzjBeginTime?long gt .now?long>
                            <div class="big_data4">
                                <button type="button" class="display_button">未开始保证金交纳</button>
                            </div>
                        <#-- 缴纳保证金结束时间 < 当前时间-->
                        <#elseif resource.bzjEndTime?long lt .now?long >
                            <div class="big_data4">
                                <button type="button" class="display_button">已结束保证金交纳</button>
                            </div>
                        <#else>
                            <div class="big_data4">
                                <button type="button" onclick="resourceApplyBank('${resource.resourceId}');" >选择竞买方式和银行</button>
                            </div>
                        </#if>
                    <#--已报名未审核-->
                    <#elseif transResourceApply?? && transResourceApply.applyStep==2>
                        <div class="big_data4">
                            <button type="button" class="display_button">资格审核中</button>
                        </div>
                    <#--已报名 审核通过-->
                    <#elseif transResourceApply?? && transResourceApply.applyStep==3>
                        <#if resource.bzjBeginTime?long gt .now?long>
                            <div class="big_data4">
                                <button type="button" class="display_button">未开始保证金交纳</button>
                            </div>
                        <#elseif resource.bzjEndTime?long lt .now?long >
                            <div class="big_data4">
                                <button type="button" class="display_button">已结束保证金交纳</button>
                            </div>
                        <#else>
                            <div class="big_data4">
                                <button type="button" onclick="checkResourceApplyBzj('${resource.resourceId}');" >交纳保证金</button>
                            </div>
                        </#if>
                    <#--已报名 审核被退回-->
                    <#elseif transResourceApply?? && transResourceApply.applyStep==5>
                        <div class="big_data4">
                            <button type="button" onclick="resourceApplyBank('${resource.resourceId}');">审核失败并查看原因</button>
                        </div>
                    <#--已报名 保证金缴纳完成-->
                    <#elseif transResourceApply?? && transResourceApply.applyStep==4>
                        <#--竞价 挂牌期 允许报价-->
                        <#if resource.resourceStatus == 10 || resource.resourceStatus == 1 || resource.resourceStatus == 11>
                            <div class="big_data4">
                                <button type="button" onclick="resourceOffer('${resource.resourceId}', '${resource.resourceCode}');" >报价</button>
                            </div>
                        <#elseif resource.resourceStatus == 20>
                            <div class="big_data4">
                                <button type="button" class="display_button">未开始挂牌</button>
                            </div>
                        </#if>
                    </#if>
                </#if>
            </div>
            <#--地块详细-->
            <div class="main_up_left_down">
                <table width="800" height="350px" cellspacing="0" cellpadding="0" border="0">
                    <tr>
                        <td class="td_title">挂牌开始时间</td>
                        <td class="td_neirong">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="td_title">挂牌结束时间</td>
                        <td class="td_neirong">${resource.gpEndTime?string("yyyy-MM-dd HH:mm")}</td>
                    </tr>
                    <tr>
                        <td class="td_title">报名开始时间</td>
                        <td class="td_neirong">${resource.bmBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="td_title">报名结束时间</td>
                        <td class="td_neirong">${resource.bmEndTime?string("yyyy-MM-dd HH:mm")}</td>
                    </tr>
                    <tr>
                        <td class="td_title">缴纳保证金开始时间</td>
                        <td class="td_neirong">${resource.bzjBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="td_title">缴纳保证金结束时间</td>
                        <td class="td_neirong">${resource.bzjEndTime?string("yyyy-MM-dd HH:mm")}</td>
                    </tr>
                    <tr>
                        <td class="td_title">限时竞价开始时间</td>
                        <td class="td_neirong">${resource.xsBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="td_title">最高限价</td>
                        <#if resource.maxOfferExist == 0>
                            <td class="td_neirong">无</td>
                        <#else>
                            <td class="td_neirong">${resource.maxOffer!}<@unitText offerUnit = resource.offerUnit/></td>
                        </#if>
                    </tr>
                        <tr>
                            <td class="td_title">供地方式</td>
                            <td class="td_neirong">出让</td>
                            <#if resource.maxOfferExist == 1>
                                <td class="td_title">最高限价后竞价方式</td>
                                <td class="td_neirong">${resource.maxOfferChoose!}</td>
                             </#if>
                        </tr>
                    <tr>
                        <td class="td_title">地块编号</td>
                        <td class="td_neirong" style="width:190px;">${resource.resourceCode!}</td>
                        <td class="td_title">行政部门</td>
                        <td width="320" class="td_neirong">${transOrganize.organizeName!}</td>
                    </tr>
                    <tr>
                        <td class="td_title">竞价单位</td>
                        <td class="td_neirong"><@unitText offerUnit = resource.offerUnit/></td>
                        <td class="td_title">起始价</td>
                        <td class="td_neirong">${resource.beginOffer!}<@unitText offerUnit = resource.offerUnit/></td>
                    </tr>
                    <tr>
                        <td class="td_title">增价幅度</td>
                        <td class="td_neirong">${resource.addOffer!}<@unitText offerUnit = resource.offerUnit/></td>
                        <td class="td_title">出让面积</td>
                        <td width="320" class="td_neirong">${resource.crArea!}平方米</td>
                    </tr>
                    <tr>
                        <td class="td_title">土地位置</td>
                        <td colspan="3" class="td_neirong">${resource.resourceLocation!}</td>
                    </tr>

                    <tr>
                        <td class="td_title">保证金</td>
                        <td class="td_neirong">
                        ${resource.fixedOffer!}(万元)
                        </td>
                        <td class="td_title">规划用途</td>
                        <td class="td_neirong">${resource.tdytName!}</td>
                    </tr>
                </table>
            </div>
        </div>
        <#--竞买记录-->
        <div class="main_up_right">
            <div class="main_up_right_title">
                <img src="${base}/images/a.png">
                <p>竞买记录</p>
            </div>
            <div class="main_up_right_in" id="offerHistory">
                <#include "resource/resource-view-offer-history.ftl"/>
            </div>

        </div>
    </div>

    <div class="main_middle">
        <img src="${base}/images/liucheng.png">
    </div>
    <#--地块详情-->
    <div class="main_down">
        <div class="tab" js-tab="2">
            <div class="tab-title">
                <a href="javascript:;" class="item item-cur">地块介绍</a>
                <a href="javascript:;" class="item">出让公告</a>
                <a href="javascript:;" class="item">竞买记录</a>
                <a href="javascript:loadMap();" class="item">地图</a>
                <#--中止状态-->
                <#if resource.resourceEditStatus == 3>
                    <a href="javascript:;" class="item">中止公告</a>
                <#--终止状态-->
                <#elseif resource.resourceEditStatus == 4>
                    <a href="javascript:;" class="item">终止公告</a>
                <#--成交状态-->
                <#elseif resource.resourceStatus == 30>
                    <a href="javascript:;" class="item">成交公告</a>
                </#if>
            </div>
            <div class="tab-cont">
                <ul class="tab-cont__wrap">
                    <li class="item"><#include "resource/resource-view-detail.ftl"/></li>
                    <li class="item" id="crgg">
                        <iframe src="${base}/resource/view/crgg?id=${resource.resourceId!}" width="100%" height="850px" frameborder="no"
                                border="0" marginwidth="0" marginheight="0"></iframe>
                        <table class="table table-border table-bordered">
                            <tr>
                                <td width="100px" style="background-color: #F5F5F5;">
                                    附件材料
                                </td>
                                <td>
                                    <div>
                                    <#list crggAttachments as crggAttachment>
                                        <a title="${crggAttachment.fileName!}" class="btn btn-link attachment" target="_blank" href="${base}/file/get?fileId=${crggAttachment.fileId}">${crggAttachment.fileName!}</a>
                                    </#list>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </li>
                    <li class="item">
                        <div id ="viewOffer">

                        </div>
                        <div style=" width:600px; margin:0 auto;text-align:center;">
                            <ul class="pagination" id="pagination">
                            </ul>
                            <input type="hidden" id="currentPage" runat="server" value="1" />
                            <input type="hidden" id="totalPages" runat="server" value="10"/>
                            <input type="hidden" id="pageSize" runat="server" value="10" />
                            <!--设置最多显示的页码数 可以手动设置 默认为7-->
                            <input type="hidden" id="visiblePages" runat="server" value="7" />
                        </div>
                    </li>
                    <li class="item">
                        <div>
                            <div class="" style="height: 700px">
                                <div id="map" style="width: 100%;height: 100%">
                                </div>
                                <span>*本地图仅提供示意作用，并非精确位置。具体位置以用地红线图和勘测定界图为准</span>
                            </div>
                        </div>
                    </li>
                    <#if resource.resourceEditStatus == 3 || resource.resourceEditStatus == 4 || resource.resourceStatus == 30>
                    <li class="item">
                        <div id ="notice">

                        </div>
                    </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<script type="text/javascript" src="${base}/js/tab.js"></script>
<script type="text/javascript" src="${base}/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/thridparty/leaflet/leaflet.js"></script>
<script type="text/javascript" src="${base}/js/map.js"></script>
<script type="text/javascript" src="${base}/thridparty/xdate/xdate.js"></script>
<script type="text/javascript" src="${base}/js/servertime.js"></script>
<script type="text/javascript" src="${base}/js/index.js"></script>
<script type="text/javascript">
    var _serverTime=${.now?long};
    var _serverTimeUrl="${base}/getServerTime.f";
    var window_offer = window.parent.getWindowOffer();
    $(function () {
        setServerTime();
        gtmapMap.initializeMap();
        $("iframe", window.parent.document).attr("height", document.body.scrollHeight);

//        loadCrgg();
        loadOffer();
        loadNotice();
        $('[js-tab=2]').tab();
    });

   /* function loadCrgg() {
        $("#crgg").load("${base}/resource/view/crgg?id=${resource.resourceId!}");
    }*/

    function loadOffer() {
        $("#viewOffer").load("${base}/resource/view/offer/list?resourceId=${resource.resourceId!}");
    }
    function loadMap() {
        gtmapMap.resizeMapAndLocateGeometry('${base}/resource/view/geometry?id=${resource.resourceId!}');
    }
    function loadNotice() {
        $("#notice").load("${base}/my/resourceNotice?resourceId=${resource.resourceId!}");
    }
    function resourceApplyBank(resourceId) {
        $("#mainFrame", window.parent.document).attr("src", "${base}/resourceApply/apply-bank?resourceId=" + resourceId);
    }

    function resourceOffer(resourceId, resourceCode) {
        // 未打开窗口
        if (window_offer == null) {
            window_offer = window.open('${base}/resourceOffer/index?resourceId=' + resourceId , 'window_offer', 'height=600,width=1200,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
            window_offer.focus();
        } else {
            // 窗口被关闭
            if (window_offer.closed) {
                window_offer = window.open('${base}/resourceOffer/index?resourceId=' + resourceId, 'window_offer', 'height=600,width=1200,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
                window_offer.focus();
            } else {
                window_offer.focus();
                window_offer.selectTab(resourceCode);
            }
        }
        window.parent.setWindowOffer(window_offer);
    }
    function checkResourceApplyBzj (resourceId) {
        $("#mainFrame", window.parent.document).attr("src", "${base}/resourceApply/apply-bzj?resourceId=" + resourceId);
    }
</script>
</body>
</html>
