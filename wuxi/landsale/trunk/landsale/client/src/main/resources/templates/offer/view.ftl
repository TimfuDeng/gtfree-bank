<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9; IE=8; IE=7; IE=EDGE">
    <meta charset="utf-8">
    <title>国有建设用地网上交易系统 - ${_title!}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css"/>
    <link href="${base}/thridparty/H-ui.2.0/static/h-ui/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/css/tanchu_style.css"/>
    <style type="text/css">
        .time em{
            font-weight: 400;
            font-style: normal;
            color: #666;
            font-size: 15px;
            margin-left: 3px;
        }
        .time var{
            font-size: 25px;
            font-style: normal;
            font-weight: bold;
            margin-left: 3px;
        }
    </style>
</head>
<body>
	<div class="tb_main">
    	<div class="tb_main_up">

        	<div class="tb_main_up_left">
            	<div class="tb_main_up_left_title">
                	<img src="${base}/images/front_03.png">
                    <#--发布状态或结束状态-->
                    <#if resource.resourceEditStatus == 2 || resource.resourceEditStatus == 9>
                        <#if resource.resourceStatus == 20>
                            <p>公告期</p>
                        <#elseif resource.resourceStatus == 10>
                            <#--挂牌期-->
                            <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                                <p>挂牌期</p>
                            <#--挂牌结束 竞价未开始-->
                            <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                                <p>等待限时竞价开始</p>
                            </#if>
                        <#elseif resource.resourceStatus == 1>
                            <p>限时竞价期</p>
                        <#elseif resource.resourceStatus == 11>
                            <#--挂牌期-->
                            <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                                <p>等待挂牌结束</p>
                            <#--挂牌结束 竞价未开始-->
                            <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                                <p>等待限时竞价开始</p>
                            <#--限时竞价期-->
                            <#else>
                                <#--判断最高限价后的 竞价方式 一次报价-->
                                <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
                                    <#--判断一次报价开始时间 一次报价开始时间 < 当前时间-->
                                    <#if oneTarget?exists && oneTarget.stopDate?long - (.now?long) lt 0>
                                        <p>等待一次报价结果</p>
                                    <#elseif oneTarget?exists && oneTarget.stopDate?long - (.now?long) gt 0>
                                        <p>等待一次报价开始</p>
                                    <#else>
                                        <p>等待一次报价发布</p>
                                    </#if>
                                <#else>
                                    <p>等待摇号结果</p>
                                </#if>
                            </#if>
                        <#else>
                            <p>已结束</p>
                        </#if>
                    <#elseif resource.resourceEditStatus == 3>
                        <p>已中止</p>
                    <#elseif resource.resourceEditStatus == 4>
                        <p>已终止</p>
                    <#else>
                        <p>未开始</p>
                    </#if>
                </div>
                 <ul>
                    <li>
                    	<img src="${base}/images/td_icon.png">
                        <p>当前时间：</p>
                        <span id="serverTimeDiv"></span>
                    </li>
                    <li>
                    	<img src="${base}/images/td_icon.png">
                        <#--发布状态或结束状态-->
                        <#if resource.resourceEditStatus == 2 || resource.resourceEditStatus == 9>
                            <#if resource.resourceStatus == 20>
                                <p>距挂牌开始：</p>
                                <span class="time" id="span_${resource.resourceId}" value="${resource.gpBeginTime?long}"></span>
                            <#elseif resource.resourceStatus == 10>
                                <#--挂牌期-->
                                <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                                    <p>距挂牌截止：</p>
                                    <span class="time" id="span_${resource.resourceId}" value="${resource.gpEndTime?long}"></span>
                                <#--挂牌结束 竞价未开始-->
                                <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                                    <p>距限时竞价开始：</p>
                                    <span class="time" id="span_${resource.resourceId}" value="${resource.xsBeginTime?long}"></span>
                                </#if>
                            <#elseif resource.resourceStatus == 1>
                                <p>距限时结束：</p>
                                <span class="time" id="span_${resource.resourceId}" value="${endTime!}"></span>
                            <#elseif resource.resourceStatus == 11>
                                <#--挂牌期-->
                                <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                                    <p>距挂牌截止：</p>
                                    <span class="time" id="span_${resource.resourceId}" value="${resource.gpEndTime?long}"></span>
                                <#--挂牌结束 竞价未开始-->
                                <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                                    <p>距限时竞价开始：</p>
                                    <span class="time" id="span_${resource.resourceId}" value="${resource.xsBeginTime?long}"></span>
                                <#--限时竞价期-->
                                <#else>
                                    <#--判断最高限价后的 竞价方式 一次报价-->
                                    <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
                                        <#--判断一次报价开始时间 一次报价开始时间 > 当前时间-->
                                        <#if oneTarget?exists && oneTarget.stopDate?long - (.now?long) gt 0>
                                            <p>距一次报价开始：</p>
                                            <span class="time" id="span_${resource.resourceId}" value="${oneTarget.stopDate?long}"></span>
                                        <#else>
                                            <p>剩余时间：</p>
                                            <span>--</span>
                                        </#if>
                                    <#else>
                                        <p>剩余时间：</p>
                                        <span>--</span>
                                    </#if>
                                </#if>
                            <#else>
                                <p>剩余时间：</p>
                                <span>--</span>
                            </#if>
                        <#else>
                            <p>剩余时间：</p>
                            <span>--</span>
                        </#if>
                    </li>
                    <li>
                    	<img src="${base}/images/td_icon.png">
                        <#--  地块状态在 竞价 或者 挂牌 或 最高限价  -->
                        <#if resource.resourceStatus==1 || resource.resourceStatus==2 || resource.resourceStatus==10 || resource.resourceStatus==11>
                            <p>当前价：</p>
                            <em class="currency" >¥</em>
                            <span style="font-weight:bold;color: red;font-size: 25px;" id="priceInfo">
                                <#if maxOfferPrice??>
                                    ${maxOfferPrice.offerPrice}
                                <#else>
                                    ${resource.beginOffer}
                                </#if>
                            </span><label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label>
                        <#--  地块状态在 成交  -->
                        <#elseif resource.resourceStatus==30 >
                            <p>成交价：</p>
                            <em class="currency">¥</em>
                            <span style="font-weight:bold;color: red;font-size: 25px;" id="priceInfo">
                                 ${maxOfferPrice.offerPrice}
                            </span><label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label>
                        <#else>
                            <p>起始价：</p>
                            <em class="currency">¥</em>
                            <span style="font-weight:bold;color: red;font-size: 25px;" id="priceInfo">
                            ${maxOfferPrice.beginOffer}
                            </span><label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label>
                        </#if>
                    </li>
                 </ul>
            </div>

            <div class="tb_main_up_right">
            	<div class="tb_main_up_left_title">
                <img src="${base}/images/front_03.png">
                    <p>竞价明细</p>
                </div>
                <ul>
                    <li>
                    	<p>起始价：</p>
                        <span>¥${resource.beginOffer!}&nbsp;<label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label></span>
                    </li>
                    <li>
                    	<p>增价幅度：</p>
                        <span>¥${resource.addOffer!}&nbsp;<label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label></span>
                    </li>
                    <li>
                        <p>出让面积：</p>
                        <span>${resource.crArea!}平方米</span>
                    </li>
                </ul>
                <ul>
                	<li>
                    	<p>挂牌开始时间：</p>
                        <span>${(resource.gpBeginTime?datetime)!}</span>
                    </li>
                    <li>
                    	<p>挂牌截止时间：</p>
                        <span>${(resource.gpEndTime?datetime)!}</span>
                    </li>
                    <li>
                    	<p>竞价开始时间：</p>
                        <span>${(resource.xsBeginTime?datetime)!}</span>
                    </li>
                </ul>
            </div>

        </div>

        <div class="tb_main_middle">
        	<div class="tb_main_middle_title">
            	<img src="${base}/images/front_03.png">
                <p>竞价出价</p>
            </div>
            <div class="tb_main_middle_inner">
                <p>出价</p>
                <#if maxOfferPrice??>
                    <input type="text" readonly="readonly" class="input-text size-L" style="width:236px" id="txtoffer" value="${maxOfferPrice.offerPrice!}">
                <#else>
                    <input type="text" readonly="readonly" class="input-text size-L" style="width:236px" id="txtoffer" value="${resource.beginOffer!}">
                </#if>
                <input name="" type="button" onclick="addOffer()" id="up" />
                <input name="" type="button" onclick="cutOffer()" id="down" />
                <#--发布状态或结束状态-->
                <#if resource.resourceEditStatus == 2 || resource.resourceEditStatus == 9>
                    <#-- 保证金已经到位 地块为挂牌或竞价状态-->
                    <#if resource.resourceStatus==10 || resource.resourceStatus==1>
                        <#-- 判断是否挂牌前一个小时-->
                        <#if (((resource.gpEndTime?long)-(.now?long)) lt 1000*60*60) && (((resource.gpEndTime?long)-(.now?long)) gt 0)>
                            <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                            <span class="label label-warning message_span"> 挂牌截止前1小时停止报价，限时开始时间后进入4分钟限时竞价！</span>
                        <#--竞价期之前挂牌期之后-->
                        <#elseif ((resource.xsBeginTime?long)-(.now?long) lt 0) && ((resource.gpEndTime?long)-(.now?long) gt 0)>
                            <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                            <span class="label label-warning message_span"> 限时开始时间前停止报价，限时开始时间后进入4分钟限时竞价！</span>
                        <#else>
                            <input onclick="beginOffer();" class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 145px" />
                            <div id="showinfo" style="margin:27px 0 0 10px;padding:0px;float: left;width:230px"></div>
                        </#if>
                    <#--地块状态为公告期-->
                    <#elseif resource.resourceStatus==20>
                        <input class="btn btn-default size-L" type="button" value="未开始挂牌" style="width: 145px" />
                    <#--最高限价-->
                    <#elseif resource.resourceStatus==11>
                        <#--判断 限时竞价开始时间 < 当前时间-->
                        <#if (resource.xsBeginTime?long)-(.now?long) lt 0 >
                             <#--判断最高限价后的 竞价方式 一次报价-->
                            <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
                                <#--判断一次报价开始时间 一次报价开始时间 > 当前时间-->
                                <#if oneTarget?exists>
                                    <#if oneTarget.stopDate?long - (.now?long) gt 0>
                                        <span class="label label-warning message_span">等待一次报价开始!</span>
                                    <#else>
                                        <span class="label label-warning message_span">  一次报价开始， <a href="${base}/resource/decideResourceView?resourceId=${resource.resourceId}">点击跳转！</a></span>
                                    </#if>
                                <#else>
                                    <span class="label label-warning message_span">等待一次报价发布!</span>
                                </#if>

                            <#elseif resource.maxOfferChoose?? && resource.maxOfferChoose.code==2>
                                <#if yhAgree?exists>
                                    <#if yhAgree.agreeStatus == 0>
                                        <span class="label label-warning message_span"> 未同意参加摇号,等待公示摇号结果！</span>
                                    <#else>
                                        <span class="label label-warning message_span"> 已同意参加摇号,等待公示摇号结果！</span>
                                    </#if>
                                <#else>
                                    <span style="float: left" class="label label-warning yh_span"> 限时竞价已结束,请确认以最高限价参加摇号！点击"参加摇号"确认参加,点击"不参加摇号"确认不参加,不点击默认为不参加！请确认：</span>
                                    <input onclick="joinYh(1);" class="btn btn-primary size-L" type="button" value="参加摇号" style="width: 145px" />
                                    <input onclick="joinYh(0);" class="btn btn-primary size-L" type="button" value="不参加摇号" style="width: 145px; margin-left: 10px;" />
                                </#if>
                            <#else>
                                <span class="label label-warning message_span"> 等待后续操作,敬请期待！</span>
                            </#if>
                        <#else>
                            <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                            <span class="label label-warning message_span">已达到最高限价,等待限时开始时间后进入下一步！</span>
                        </#if>
                    <#--成交-->
                    <#elseif resource.resourceStatus==30>
                        <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                        <span class="label label-warning message_span">已结束,等待成交审核结果！</span>
                    <#--流拍-->
                    <#elseif resource.resourceStatus==31>
                        <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                        <span class="label label-warning message_span">已流拍！</span>
                    <#--未知状态-->
                    <#else>
                        <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                        <span class="label label-warning message_span">已结束！</span>
                    </#if>
                <#else>
                    <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 145px" />
                </#if>
            </div>
        </div>

        <div class="tb_main_middle_title" style="margin:5px auto 0 auto;">
            <img src="${base}/images/front_03.png">
            <p>竞价记录</p>
        </div>
        <div style="width: 1138px;margin: 0 auto;" id="offerHistory"></div>
    </div>

    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel"><i class="icon-warning-sign"></i>出价确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <p id="modal_content">对话框内容…</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" id="btn_modal" onclick="javascript:postOffer();">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
    </div>

    <div id="myModalLimit" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <h4 id="myModalLabel"><i class="icon-warning-sign"></i>参与摇号确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
        </div>
        <div class="modal-body">
            <p id="modal_content2">您是否确认参加摇号！</p>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" id="btn_modal" onclick="javascript:confirmLimit('${resource.resourceId}');">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
    </div>

    <div id="myModalRefresh" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-body">
            <p id="modal_refresh">
            <div style="text-align: center">
                <p style="font-size:26px;font-weight:700" id="refreshValue">3</p>
            </div>
            </p>
        </div>
    </div>

    <div id="myModalError" class="modal hide fade" tabindex="-1">
        <div class="modal-body">
            <span style="font-size:16px">网络或服务器异常！</span>
            <a href="javascript:window.location.reload();" class="btn btn-primary">刷新页面</a>
            <a href="javascript:windows.close();" class="btn btn-danger">关闭页面</a>
        </div>
    </div>

    <input type="hidden" id="resourceId" value="${resource.resourceId}">
    <input type="hidden" id="crArea" value="${resource.crArea!}">
    <input type="hidden" id="offer0" value="${resource.beginOffer}">
    <input type="hidden" id="resourceStatus" value="${resource.resourceStatus}">
    <input type="hidden" id="resourceEditStatus" value="${resource.resourceEditStatus}">
    <input type="hidden" id="maxOfferPrice" value="<#if maxOfferPrice??>${maxOfferPrice.offerPrice!}</#if>">
    <#if resource.resourceStatus?? && resource.resourceStatus == 11>
        <input type="hidden" id="addOffer" value="${resource.addOffer}">
        <input type="hidden" id="beginOffer" value="${resource.beginOffer}">
        <input type="hidden" id="topOffer" value="0">
        <#if (maxOffer??)&&maxOffer.offerType==2>
            <input type="hidden" id="maxOffer" value="${maxOffer.offerPrice}">
            <input type="hidden" id="maxOfferTime" value="${maxOffer.offerTime?long}">
        <#else>
            <input type="hidden" id="maxOffer" value="0">
            <input type="hidden" id="maxOfferTime" value="${.now?long}">
        </#if>
        <input type="hidden" id="maxOfferType" value="2">
    <#else>
        <input type="hidden" id="addOffer" value="${resource.addOffer}">
        <input type="hidden" id="beginOffer" value="${resource.beginOffer}">
        <input type="hidden" id="topOffer" value="${resource.maxOffer!}">
        <#if maxOffer??>
            <input type="hidden" id="maxOffer" value="${maxOffer.offerPrice}">
            <input type="hidden" id="maxOfferTime" value="${maxOffer.offerTime?long}">
            <input type="hidden" id="maxOfferType" value="${maxOffer.offerType!}">
        <#else>
            <input type="hidden" id="maxOffer" value="0">
            <input type="hidden" id="maxOfferTime" value="${.now?long}">
        </#if>
    </#if>
    <input type="hidden" id="userId" value="${userId!}">

    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
    <script type="text/javascript" src="${base}/thridparty/laytpl/laytpl.js"></script>
    <script type="text/javascript" src="${base}/thridparty/xdate/xdate.js"></script>
    <script type="text/javascript" src="${base}/js/servertime.js"></script>
    <script type="text/javascript" src="${base}/js/index.js"></script>
    <script type="text/javascript" src="${base}/js/view.js"></script>

    <script id="tpl_beginoffer" type="text/html">
        <div style="text-align: center">
            <p style='color: #d91615;font-size: 20px'>小写：{{ d.offer }}<@unitText offerUnit = resource.offerUnit/></p>
            <p style='color: #d91615;font-size: 20px'>大写：{{ d.offerChinese }}<@unitText offerUnit = resource.offerUnit/></p>
        </div>
    </script>

    <script type="text/javascript">
        var _serverTime=${.now?long};
        var _serverTimeUrl="${base}/getServerTime.f";
        $(document).ready(function () {
            // 获取服务器时间
            setServerTime();
            // 历史
            getOfferHistory();
        });
        function getOfferHistory () {
            $("#offerHistory").load("${base}/resourceOffer/getOfferHistory", {resourceId: $("#resourceId").val()});
        }
        function joinYh (agreeStatus) {
            $.ajax({
                type: "post",
                url: "${base}/resourceOffer/joinYh",
                data: {agreeStatus: agreeStatus, resourceId: $("#resourceId").val()},
                success: function (data) {
                    if (data.flag) {
                        window.location.reload();
                    }
                }
            })
        }
    </script>
</body>
</html>
