<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>地块竞价列表</title>
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
    <!-- 竞买记录  -->
    <table class="table table-border">
        <thead>
        <tr>
            <th>报价时间</th>
            <th>报价人</th>
            <th>报价</th>
        </tr>
        </thead>
        <tbody>
        <#list onePriceLogList as onePriceLog>
        <tr>
            <td class="offerText">${onePriceLog.priceDate?string('yyyy年MM月dd日 HH:mm:ss')}</td>
            <td>
            ${onePriceLog.priceUnit!}
            </td>
            <td>
            ${onePriceLog.price!}万
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="${base}/static/js/dist/viewOfferList.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>