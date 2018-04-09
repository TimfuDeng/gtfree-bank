<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>地块竞价列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/static/js/dist/H-ui-ie9.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${base}/static/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/static/js/dist/layout.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/static/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/static/thridparty/H-ui.2.0/lib/iconfont/iconfont.css"/>
    <!--[if IE 7]>
    <link href="${base}/static/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
    <![endif]-->

    <!--[if IE 6]>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->

    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
</head>
<body>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l">
            <input type="hidden" name="resourceId" value="${resourceId!}">
            <a class="btn btn-primary" type="button" target="_self" href="${base}/resource/offer/list.f?resourceId=${resourceId!}">刷新</a>
        </div>
    </form>
</div>
<div>
    <!-- 竞买记录  -->
    <table class="table table-border">
        <thead>
        <tr>
            <th>报价时间</th>
            <th>号牌</th>
            <th>竞买报价序号</th>
            <th>报价</th>
        </tr>
        </thead>
        <tbody>
        <#list transResourceOffers.items as resourceOffer>
        <tr>
            <td class="offerText">${resourceOffer.offerTime?number_to_datetime?string('yyyy年MM月dd日 HH:mm:ss')}</td>
            <td>
            <#--${resourceOffer.applyNumber!}号-->
                <#if userId?? && resourceOffer.userId==userId>
                    我的报价
                <#else>
                    其他报价
                </#if>
            </td>
            <td>第${resourceOffer.offerCount}轮报价</td>
            <td>${resourceOffer.offerPrice}
                <#if resourceOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==0>
                    平米（公租房面积）
                <#elseif resourceOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==1>
                    万（公租房资金）
                <#else>
                    万
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
<@PageHtml pageobj=transResourceOffers formId="frmSearch" />
</div>
<script type="text/javascript" src="${base}/static/js/dist/viewOfferList.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>

</body>
</html>