<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link href="${base}/css/css.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<script type="text/javascript" src="${base}/js/ui-choose.js"></script>
<script type="text/javascript" src="${base}/js/tab.js"></script>
<script type="text/javascript" src="${base}/js/index.js"></script>
<#--<script type="text/javascript" src="${base}/js/iframeHight.js"></script>-->

    <style type="text/css"> 
        .name{
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            max-width:120px;
        }
    </style>
</head>

<body>
    <div class="head_lie">
        <div class="head_in">
            <div class="head_city">
            	 <div class="tab" js-tab="1">
                    <div class="tab-title" id="indexRegion"></div>
            	</div>
                
              <div class="user_lie">
                	<img src="${base}/images/users.png" style="margin-top:8px; float:left;"></img>
                  <#if Session["_USER"]?exists>
                      <#assign transUser = Session["_USER"]>
                      <p title="${transUser.viewName!}" class="name">${transUser.viewName!}</p>
                      <p>|</p>
                      <a href="${base}/logout">退出</a>
                  <#else>
                      <a href="javascript:loginAndRedirect();">登录</a>
                  </#if>

                </div>
                
            </div>    
                
            <div class="head_middle">
            	<div class="head_middle_title"></div>
            </div>
            <div class="head_nav">
            	<select class="ui-choose" id="index_tab">
                    <option value="${base}/resource/index">首页</option>
                    <option value="${base}/my/menu">我的交易</option>
                    <option value="${base}/notice/index">通知公告</option>
                    <option value="${base}/material/index">资料下载</option>
                    <option value="${base}/material/help">系统帮助</option>
	            </select>
            </div>
        </div>
     </div>

    <#--<div id="mainDiv"></div>-->
    <input type="hidden" id="regionCode" />
    <input type="hidden" id="redirectUrl" value="${redirectUrl!}" />
    <input type="hidden" id="currentUrl" value="${base}/resource/index" />
    <iframe id="mainFrame" frameborder="0" scrolling="no" class="iframe_liebiao"></iframe>

    <div class="foot_liebiao">
        <p>Copyrignt © 无锡市国土资源局 版权所有 网站等级资料   建议使用IE8.0+ 1280*768以上分辨率浏览</p>
        <p>备案序号：苏ICP备10217070号-3</p>
        <p>站长统计 | 今日IP[133] | 今日PV[527] | 昨日IP[112] | 昨日PV[363] | 当前在线[13] </p>
    </div>

 <script>
     $.ajaxSetup ({
         cache: false //关闭AJAX相应的缓存
     });
    $(function () {
        // 将所有.ui-choose实例化
        $('#index_tab').ui_choose();

        // index_tab select 单选
        var index_tab = $('#index_tab').data('ui-choose');
        index_tab.click = function(value, item) {
            var regionCode = $("#regionCode").val();
            if (isEmpty(regionCode)) {
                $("#mainFrame").attr("src", value);
            } else {
                $("#mainFrame").attr("src", value + "?regionCode=" + regionCode);
            }

        };
        index_tab.change = function(value, item) {
            var regionCode = $("#regionCode").val();
            if (isEmpty(regionCode)) {
                $("#mainFrame").attr("src", value);
            } else {
                $("#mainFrame").attr("src", value + "?regionCode=" + regionCode);
            }
            $("#currentUrl").val(value);
        };
        // 登陆后 跳转原页面
        if (!isEmpty('${redirectUrl!}') && '${redirectUrl!}' != '/') {
            $("#mainFrame").attr("src", '${redirectUrl!}');
        } else {
            $("#mainFrame").attr("src", "${base}/resource/index");
        }
        // tab卡 选中
        $('.ui-choose li').each(function (i, e) {
            $(e).removeClass("selected");
            if ($(e).attr("data-value").indexOf($("#mainFrame").attr("src")) > -1) {
                $(e).addClass("selected");
            }
        });
        // 初始化 行政区
        $("#indexRegion").load("${base}/getRegion", {regionLevel: 2}, function (response, status, xhr) {
            // 多个元素不同变化方式（需要在HTML中加入js-tab）
            $('[js-tab=1]').tab();
        });
        /*$("#mainDiv").load("${base}/resource/index");*/
    });

     // 改变行政区
     function changeRegionCode (regionCode, regionLevel) {
         $("#regionCode").val(regionCode);
         if (isExitsFunction($("#mainFrame")[0].contentWindow.changeParentRegion)) {
             $("#mainFrame")[0].contentWindow.changeParentRegion(regionCode);
         }
         var currentUrl = $("#currentUrl").val();
         if (regionLevel == 1) {
             $("#indexRegion").load("${base}/getRegion", {regionLevel: 2}, function (response, status, xhr) {
                 // 多个元素不同变化方式（需要在HTML中加入js-tab）
                 $('[js-tab=1]').tab({curDisplay: 1});
             });
         } else if (regionLevel == 2) {
             $("#indexRegion").load("${base}/getRegion", {regionCode: regionCode}, function (response, status, xhr) {
                 // 多个元素不同变化方式（需要在HTML中加入js-tab）
                 $('[js-tab=1]').tab({curDisplay: 2});
             });
         }
//         $("#mainFrame").attr("src", currentUrl + "?regionCode=" + regionCode);
     }

     // 报价window对象 至于顶层
     var window_offer_top = null;

     function getWindowOffer() {
         return window_offer_top;
     }

     function setWindowOffer(window_offer) {
         window_offer_top = window_offer;
     }

     // 登录
     function loginAndRedirect() {
        var redirectUrl = $("#mainFrame").attr("src");
         window.location.href = "${base}/login?url=" + redirectUrl;
     }

  </script>
  

</body>
</html>
