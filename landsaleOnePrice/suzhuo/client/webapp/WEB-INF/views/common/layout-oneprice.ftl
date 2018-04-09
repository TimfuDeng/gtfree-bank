<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9; IE=8; IE=7; IE=EDGE">
    <meta charset="utf-8">
    <title>
        <#if oneTarget??>
            ${oneTarget.transName!}
        <#else >
            一次报价系统 - ${_title!}
         </#if>
    </title>
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
${_head!}
</head>
<body>
<#include "head-oneprice.ftl"/>
${_body}
<#include "foot-oneprice.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>