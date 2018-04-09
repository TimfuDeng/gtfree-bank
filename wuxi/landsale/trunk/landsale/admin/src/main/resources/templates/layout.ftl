<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>土地交易系统 - ${_title!}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/html5.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/respond.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/PIE_IE678.js"></script>
    <![endif]-->
    <link href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/thridparty/H-ui.2.0/static/h-ui/style.css" rel="stylesheet" type="text/css" />
    <link href="${base}/thridparty/H-ui.2.0/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
    <link href="${base}/thridparty/H-ui.2.0/lib/bootstrapSwitch/bootstrapSwitch.css" rel="stylesheet" type="text/css" />
    <link href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!--[if IE 7]>
    <link href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
    <![endif]-->
    <link href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->

    <link href="${base}/css/landsale.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/js/laytpl.js"></script>

    ${_head!}
</head>
<body>
<#include "common/head.ftl"/>
<div class="wp">
    <div class="row cl" >
        <div class="col-2" id="left_menu" style="padding-right: 10px"></div>
        <input type="hidden" id="mainUrl" />
        <div id="ajaxResultDiv" class="col-10" style="display:none"></div>
        <div class="col-10" id="mainDiv">
        </div>
    </div>
</div>
<#include "common/foot.ftl"/>
<script id="leftMenu" type="text/html">
    <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
        {{# for(var i = 0, len = d.length; i < len; i++){ }}
            {{# if(d[i].menuType == 1){ }}
            <li class=""><a href="javascript:changeSrc('${base}/{{d[i].menuUrl}}')"><i class="{{d[i].menuIcon}}"></i>{{d[i].menuName}}</a></li>
            {{# } }}
        {{# } }}
        <hr class="nav-separator" style="width: 190px">
        <div class="clearfix"></div>
        {{# for(var i = 0, len = d.length; i < len; i++){ }}
            {{# if(d[i].menuType == 2){ }}
            <li class=""><a href="javascript:changeSrc('${base}/{{d[i].menuUrl}}')"><i class="{{d[i].menuIcon}}"></i>{{d[i].menuName}}</a></li>
            {{# } }}
        {{# } }}
    </ul>
</script>
<script type="text/javascript">
    $(document).ready(function () {
        loadInfo();
        <#--changeSrc("${base}/crgg/index");-->
    })
    $.ajaxSetup ({
        cache: false //关闭AJAX相应的缓存
    })
    // 异步获取 菜单数据
    function loadInfo() {
        var tpl = document.getElementById('leftMenu').innerHTML; //读取模版
        $.ajax({
            type: "post",
            url: "${base}/menu/getLeftMenu",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                laytpl(tpl).render(data, function (render) {
                    document.getElementById("left_menu").innerHTML = render;
                });
                clickMenu();
            }
        })
    }

    // 菜单选中
    function clickMenu() {
        $("#left_menu li").click(function () {
            $("#left_menu li").each(function (i, e) {
                $(e).removeClass("active");
            });
            $(this).addClass("active");
        });
    }

    // 跳转到公告列表
    function jumpToCrgg() {
        $("#mainUrl").val("${base}/crgg/index");
        window.location.href = "${base}/index";
        $("#mainDiv").load("${base}/crgg/index");

        return;
    }
    // 跳转到中止公告列表
    function jumpToSuspend() {
        <#--$("script[src='${base}/thridparty/xheditor/xheditor-1.2.2.min.js']").remove();-->
        <#--$("script[src='${base}/thridparty/xheditor/xheditor_lang/zh-cn.js']").remove();-->
        changeSrc("${base}/suspendNotice/index");
        /*$("#mainUrl").val("${base}/suspendNotice/index");
        $("#mainDiv").load("${base}/suspendNotice/index");*/
        return;
    }

//    // 刷新
//    function reloadSrc(url) {
//        $("#mainDiv").load(url);
//    }
</script>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/layer1.8/layer.min.js"></script>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/laypage/laypage.js"></script>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${base}/thridparty/xdate/xdate.js"></script>
<script type="text/javascript" src="${base}/thridparty/layer/layer.js"></script>
<script type="text/javascript" src="${base}/js/main.js"></script>
<script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
<script type="text/javascript" src="${base}/js/servertime.js"></script>
<script type="text/javascript" src="${base}/js/checkFilter.js"></script>

</body>
</html>