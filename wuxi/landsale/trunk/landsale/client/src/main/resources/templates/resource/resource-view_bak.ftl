<link href="${base}/css/xiangxi.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
<#--<link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">-->
<link rel="stylesheet" href="${base}/thridparty/leaflet/leaflet.css" />
<link rel="stylesheet" href="${base}/js/dist/view.css" />
<style type="text/css">
.table-td-title{
background-color:#e5e5e5;
font-weight: bold;
}
</style>
<div class="weizhi">
    <div class="weizhi_in">
        <img src="${base}/images/zuobiao.png">
        <p>当前位置：<a href="${base}/resource/index" class="maincolor">首页</a> > ${resource.resourceCode!} </p>
    </div>
</div>
<div class="main_all">
    <div class="main_up">
        <div class="main_up_left">
            <div class="main_up_left_top">
                <div class="big_data1">
                    <img src="${base}/images/bigdata1.png">
                    <p>挂牌起始价（RMB）</p>
                    <span>¥${resource.beginOffer!}&nbsp;<#if resource.offerUnit == 0>万<#elseif resource.offerUnit == 1>元/平方米<#else>万/亩</#if></span>
                </div>
                <div class="big_data2">
                    <img src="${base}/images/bigdata2.png">
                    <p>竞买保证金（RMB）</p>
                    <span>¥${resource.fixedOffer!}&nbsp;万元<#if resource.fixedOfferUsd?? && resource.fixedOfferUsd &gt; 0>，$${resource.fixedOfferUsd?string(",##0.##")}万美元</#if></span>
                </div>
                <div class="big_data3">
                    <img src="${base}/images/bigdata3.png">
                    <#--<p>距离挂牌竞价截止时间</p>-->
                    <#--<span>${resource.gpEndTime?string("yyyy-MM-dd HH:mm")}</span>-->
                        <p>挂牌竞价截止时间</p>
                        <span class="value" >
                            ${resource.gpEndTime?string("yyyy-MM-dd HH:mm")}
                        </span>
                </div>
            </div>


            <div class="main_up_left_down">
                <table width="966" height="350px" cellspacing="0" cellpadding="0" border="0">
                    <tr>
                        <td class="td_title">规划用途</td>
                        <td class="td_neirong">${resource.landUse!}</td>
                        <td class="td_title">最高限价</td>
                        <td width="320" class="td_neirong">
                        <#if resource.maxOffer?? && resource.maxOffer gt 0>
                        ${resource.maxOffer}万元
                        <#else>
                            无
                        </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_title">加价幅度</td>
                        <td class="td_neirong">¥${resource.addOffer!}&nbsp;<#if resource.offerUnit == 0>万<#elseif resource.offerUnit == 1>元/平方米<#else>万/亩</#if></td>
                        <td class="td_title">出让面积</td>
                        <td class="td_neirong">${resource.crArea!}平方米</td>
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
                        <td class="td_title">规划用途</td>
                        <td class="td_neirong">${resource.landUse!}</td>
                        <td class="td_title">最高限价</td>
                        <td width="320" class="td_neirong">
                        <#if resource.maxOffer?? && resource.maxOffer gt 0>
                        ${resource.maxOffer}万元
                        <#else>
                            无
                        </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_title">土地位置</td>
                        <td colspan="3" class="td_neirong">${resource.resourceLocation!}</td>
                    </tr>
                </table>

            </div>



        </div>
        <div class="main_up_right">
            <div class="main_up_right_title">
                <img src="${base}/images/a.png">
                <p>竞买记录</p>
            </div>
            <div class="main_up_right_in">
                <table width="200" height="380" border="0">
                    <thead>
                    <tr  class="main_jilu">
                        <td>状态</td>
                        <td>报价人</td>
                        <td>报价</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list resourceOffers as resourceOffer>
                    <tr>
                        <td>
                            <#if resourceOffer_index==0>
                                <span class="label label-danger label-max">领先</span>
                            <#else>
                                <span>---</span>
                            </#if>
                        </td>
                        <td >
                            <#if userId?? && resourceOffer.userId==userId>
                                <span class="label label-default">我的报价</span>
                            <#else>
                                <span class="other-price">其他报价</span>
                            </#if>
                        </td>
                        <td>${resourceOffer.offerPrice}
                            <#if resourceOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==0>
                                平米<i class="iconfont" style="font-size:10px">&#xf012b;</i>
                            <#elseif resourceOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==1>
                                万<i class="iconfont" style="font-size:10px">&#xf012b;</i>
                            <#else>
                                <#if resource.offerUnit == 0>万<#elseif resource.offerUnit == 1>元/平方米<#else>万/亩</#if>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
    <div class="main_middle">
        <img src="${base}/images/liucheng.png">
    </div>
    <div class="main_down">
        <div class="tab" js-tab="2">
            <div class="tab-title">
                <a href="javascript:;" class="item item-cur">地块介绍</a>
                <a href="javascript:loadCrgg();" class="item">出让公告</a>
                <a href="javascript:loadOffer();" class="item">竞买记录</a>
                <a href="javascript:loadMap();" class="item">地图</a>
            </div>
            <div class="tab-cont">
                <ul class="tab-cont__wrap">
                    <li class="item">
                        <#include "resource/resource-view-detail.ftl"/>
                    </li>
                    <li class="item" id="crgg">

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
                        <div class="" style="height: 700px">
                            <div id="map" style="width: 100%;height: 100%">
                            </div>
                            <span>*本地图仅提供示意作用，并非精确位置。具体位置以用地红线图和勘测定界图为准</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <#--<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>-->
        <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
        <script type="text/javascript" src="${base}/js/tab.js"></script>
        <script type="text/javascript" src="${base}/js/dist/layout.js"></script>
        <script type="text/javascript" src="${base}/thridparty/leaflet/leaflet.js"></script>
        <script type="text/javascript" src="${base}/js/dist/view.js"></script>
        <script type="text/javascript" src="${base}/js/map.js"></script>
        <script>
            $(function () {
                $('[js-tab=2]').tab();

            });

            function loadCrgg(id) {
                $("#crgg").load("${base}/resource/view/crgg?id=${resource.resourceId!}");
            }

            function loadOffer(id) {
                $("#viewOffer").load("${base}/resource/view/offer/list?resourceId=${resource.resourceId!}");
            }
            function loadMap() {
                gtmapMap.resizeMapAndLocateGeometry('${base}/resource/view/geometry?id=${resource.resourceId!}');
            }
        </script>
    </div>
</div>
