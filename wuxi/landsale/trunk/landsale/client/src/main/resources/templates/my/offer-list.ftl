<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>我的报价列表</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
    <style type="text/css">
        table p{
            margin-bottom: 0px;
        }
        thead  td{
            text-align: center !important;
            background-color: #f5fafe;
        }
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
<div class="wp">
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span>
    <a href="${base}/my/resource-list" class="maincolor">我的保证金</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">我的报价列表</span>
</nav>

<form class="navbar-form" id="frmSearch" action="${base}/my/offer-list?resourceId=${transResource.resourceId}">
    <input type="hidden" name="resourceId" value="${transResource.resourceId}">
</form>

<table class="table table-border table-bordered table-bg">
    <thead>
    <tr>
        <td width="190">报价时间</td>
        <td width="190">报价金额</td>
        <td width="190">报价类型</td>
    </tr>
    </thead>
    <tbody>
    <#list transResourceOfferPage.items as transResourceOffer>
    <tr>
        <td class="offerText">
        ${transResourceOffer.offerTime!?number_to_datetime}
        </td>
        <td>
            <p>
            ${transResourceOffer.offerPrice}
                <#if transResource.maxOfferChoose.code==1 &&  transResourceOffer.offerType==-1>
                    <label class="offer_unit">万元（总价）</label>
                <#else>
                    <label class="offer_unit"><@unitText offerUnit = transResource.offerUnit/></label>
                </#if>
            </p>
        </td>
        <td>
            <#if transResourceOffer.offerType==1>
                <span class="label label-primary ">限时竞价报价</span>
            <#elseif transResourceOffer.offerType==2>
                <span class="label label-secondary ">最高限价</span>
            <#elseif transResourceOffer.offerType==-1>
                <#if transResource.maxOfferChoose.code==1>
                    <span class="label label-primary ">一次报价</span>
                <#elseif transResource.maxOfferChoose.code==2>
                    <span class="label label-primary ">摇号报价</span>
                </#if>
            <#else>
                <span class="label label-primary ">挂牌期报价</span>
            </#if>
        </td>
    </tr>
    </#list>
    </tbody>
</table>

<@PageHtml pageobj=transResourceOfferPage formId="frmSearch" />
</div>
</body>
</html>