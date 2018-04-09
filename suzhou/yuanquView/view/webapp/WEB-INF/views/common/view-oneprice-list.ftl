<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>地块一次报价列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/static/dist/H-ui-ie9.js"></script>
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

<div>
    <#if onePriceLogList?? && onePriceLogList?size gt 0 >
        <table class="table table-border table-bordered ">
        <thead>
        <tr><th class="text-c" colspan="3">一次报价记录</th></tr>
        <tr>
            <th class="text-c" >竞买人</th>
            <th class="text-c" >报价(元)</th>
            <th class="text-c" >报价时间</th>
        </tr>
        </thead>
        <tbody>
        <#list onePriceLogList as onePriceLog>
        <tr>
            <td class="text-c"> ${onePriceLog.priceUnit!}</td>
            <td class="text-c">${onePriceLog.price!}</td>
            <td class="text-c"> ${onePriceLog.priceDate?string("yyyy-MM-dd HH:mm:ss")}</td>
        </tr>
        </#list>
        <tr >
            <td colspan="2"  class="text-c" >平均价(元)：</td>
            <td  class="text-c" >${avgPrice!}</td>
        </tr>
        </tbody>
    </table>
    </#if>

</div>

<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>