<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9; IE=8; IE=7; IE=EDGE">
    <meta charset="utf-8">
    <title>国有建设用地网上交易系统 - ${_title!}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/html5.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/respond.min.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/PIE_IE678.js"></script>
    <![endif]-->
    <link href="${base}/static/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/static/h-ui/style.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/lib/bootstrapSwitch/bootstrapSwitch.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!--[if IE 7]>
    <link href="${base}/static/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
    <![endif]-->
    <link href="${base}/static/thridparty/H-ui.2.0/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->

    <link href="${base}/static/css/landsale.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <style>
        .page-error {
            color: #afb5bf;
            padding-top: 60px;
        }
        .page-error .error-title, .page-error .error-title .iconfont {
            font-size: 80px;
        }
        .page-error .error-description {
            font-size: 24px;
        }
        .page-error .error-info {
            font-size: 14px;
        }
        .iconfont{
            float: none;
        }
    </style>

</head>
<body>
<#include "head.ftl"/>
<section class="page-error minWP text-c">
    <p class="error-title"><i class="iconfont va-m">󰅎</i><span class="va-m"> 500</span></p>
    <p class="error-description">不好意思，系统错误<#if exception??>：${exception.message!}</#if>，请联系管理员~</p>
    <p class="error-info">您可以：<a href="${base}" class="c-primary ml-20">去首页 &gt;</a></p>
</section>

<#include "foot.ftl"/>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xdate/xdate.js"></script>
<script type="text/javascript" src="${base}/static/js/servertime.js"></script>
<script type="text/javascript" src="${base}/static/js/ajaxDoResult.js"></script>
</body>
</html>