<html>
<head>
    <title>竞买人列表</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
    <style type="text/css">
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".offerText").each(function(){
                var timeStr=$(this).html();
                var dateObj= new XDate(timeStr*1);
                $(this).html(dateObj.toString("yyyy年MM月dd日 HH:mm:ss"));
            });
        });
    </script>
</head>
<body>

<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource/list">出让地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人列表</span>
</nav>
<div id="tab_demo" class="HuiTab">
    <div class="tabBar cl" style="border-bottom: 2px solid #999999;"><a href="${base}/console/resource-apply/list?resourceId=${resourceId}"><span>竞买人列表</span></a> <span class="current">竞买人报价</span></div>
</div>
<form class="navbar-form" id="frmSearch">
    <input type="hidden" name="resourceId" value="${resourceId}">
</form>
<a href="${base}/console/resource-apply/offerlist-excel.f?resourceId=${resourceId}" class="btn btn-primary">导出Excel</a>
<table class="table table-border table-bordered table-striped table-bg " >
    <thead>
    <tr class="text-c" >
        <th >竞买人</th>
        <th style="width:155px;">报价时间</th>
        <th style="width:150px">报价</th>
        <th style="width:137px;">报价类型</th>

    </tr>
    </thead>
    <tbody>
    <#list transResourceOfferPage.items as transResourceOffer>

    <tr class="order-bd">
        <td>
        <@userInfo userId=transResourceOffer.userId />
        </td>
        <td class="offerText">
        ${transResourceOffer.offerTime}
        </td>
        <td >
        ${transResourceOffer.offerPrice}
            <#if transResourceOffer.offerType==2>
              平方米
            <#else>
                万
            </#if>
        </td>
        <td style="text-align: center">
            <#if transResourceOffer.offerType==0>
                <span>挂牌报价</span>
            <#elseif transResourceOffer.offerType==2>
                <span>限价报价</span>
            <#else>
                <span>限时报价</span>
            </#if>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourceOfferPage formId="frmSearch" />
</body>
</html>