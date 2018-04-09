<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${base}/css/bootstrap.min.css" type="text/css" rel="stylesheet"  />
<link href="${base}/css/liebiao.css" type="text/css" rel="stylesheet" />
<link href="${base}/css/ui-choose.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<script src="${base}/js/jqPaginator.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/ui-choose.js"></script>
<script type="text/javascript" src="${base}/js/tab.js"></script>
<script type="text/javascript" src="${base}/js/myPage.js"></script>
</head>

<body>
	<div class="main_liebiao">
    	<div class="in_selected">

            <form id="frmSearch" runat="server">
            <div class="in_selected1">
                <div class="selected_zhaobogua">
                    <div class="zhaobogua_title">
                        <p>招拍挂状态&nbsp;：</p>
                    </div>
                    <div class="tab" js-tab="1">
                        <div class="tab-title">
                          <a href="javascript:changeResourceStatus(0);" class="item item-cur">不限</a>
                          <a href="javascript:changeResourceStatus(20);" class="item">公告中</a>
                          <a href="javascript:changeResourceStatus(10);" class="item">挂牌中</a>
                          <a href="javascript:changeResourceStatus(1);" class="item">竞价中</a>
                          <a href="javascript:changeResourceStatus(30);" class="item">已结束</a>

                        </div>
                    </div>
                </div>
                <div class="selected_zhaobogua2">
                    <div class="zhaobogua_title">
                        <p>土地用途&nbsp;：</p>
                    </div>
                    <div class="tab" js-tab="2">
                        <div class="tab-title">
                          <a href="javascript:changeUseType();" class="item item-cur">不限</a>
                          <a href="javascript:changeUseType('SFYD');" class="item">商业</a>
                          <a href="javascript:changeUseType('GKCCYD');" class="item">工业</a>
                          <a href="javascript:changeUseType('ZZYD');" class="item">住宅</a>
                          <a href="javascript:changeUseType('QT');" class="item">其他</a>

                        </div>
                    </div>
                </div>
            </div>

            <div class="in_selected2">
                <ul>
                    <input type="text" placeholder="&nbsp;&nbsp;请输入地块编号" id="title" name="title" class="look"></input>
                    <input type="button" onclick="loadData();" class="sousuo"></input>

                    <select class="ui-choose" id="resource_sort">
                        <option value="0">默认</option>
                        <option value="1">价格（升）</option>
                        <option value="2">价格（降）</option>
                        <option value="3">面积（升）</option>
                        <option value="4">面积（降）</option>
                    </select>
                </ul>
            </div>
            <input type="hidden" name="resourceStatus" id="resourceStatus" value="0" />
            <input type="hidden" name="useType" id="useType" />
            <input type="hidden" name="orderBy" id="orderBy" value="0" />
            <input type="hidden" name="regionCode" id="regionCode" value="${regionCode!}" />
            </form>

        </div>
        <#--列表-->
        <div class="in_liebiao" id="listDiv">
            <#--<#include "resource/resource-content.ftl">-->
        </div>

        <div style=" width:600px; margin:0 auto;text-align:center;">
            <ul class="pagination" id="pagination">
            </ul>
            <input type="hidden" id="currentPage" runat="server" value="1" />
            <input type="hidden" id="totalPages" runat="server" value="10"/>
            <input type="hidden" id="pageSize" runat="server" value="8" />
            <!--设置最多显示的页码数 可以手动设置 默认为7-->
            <input type="hidden" id="visiblePages" runat="server" value="7" />
        </div>

    </div>
<script type="text/javascript" src="${base}/thridparty/xdate/xdate.js"></script>
<script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
<script type="text/javascript" src="${base}/js/servertime.js"></script>
<script type="text/javascript" src="${base}/js/index.js"></script>
<script type="text/javascript">
    $.ajaxSetup ({
        cache: false //关闭AJAX相应的缓存
    });
    var _serverTime=${.now?long};
    var _serverTimeUrl="${base}/getServerTime.f";
    var window_offer = window.parent.getWindowOffer();
    $(document).ready(function () {
        //获取服务器时间
        setServerTime();
        $("iframe", window.parent.document).attr("height", document.body.scrollHeight);
        // 多个元素不同变化方式（需要在HTML中加入js-tab）
        $('[js-tab=1]').tab();
        $('[js-tab=2]').tab({
            curDisplay: 1,
            changeMethod: 'horizontal'
        });

        // 将所有.ui-choose实例化
        $('#resource_sort').ui_choose();
        // resource_sort select 单选
        var resource_sort = $('#resource_sort').data('ui-choose');
        resource_sort.click = function(value, item) {
        };
        resource_sort.change = function(value, item) {
            $("#orderBy").val(value);
            loadData();
        };
        // 初始换 获取父级行政区
        var regionCode = $("#regionCode", window.parent.document).val();
        $("#regionCode").val(regionCode);
        loadData();
    });

    function changeParentRegion(regionCode) {
        $("#regionCode").val(regionCode);
        loadData();
    }

    // 查询列表
    function loadData(data) {
        if (isEmpty(data)) {
            data = $("form").serializeArray();
            $("#currentPage").val(1);
        }
        $("#listDiv").load("${base}/my/resource/content", data, function (response, status, xhr) {
            beginChangeTime();
        });
    }

    // 招拍挂状态
    function changeResourceStatus (resourceStatus) {
        $("#resourceStatus").val(resourceStatus);
        var useType = $("#useType").val();
        loadData();
    }
    // 用途
    function changeUseType (useType) {
        $("#useType").val(useType);
        var resourceStatus = $("#resourceStatus").val();
        loadData();
    }

    function resourceOffer(resourceId, resourceCode) {
        // 未打开窗口
        if (window_offer == null) {
            window_offer = window.open('${base}/resourceOffer/index?resourceId=' + resourceId , 'window_offer', 'height=600,width=1200,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
            window_offer.focus();
        } else {
            // 窗口被关闭
            if (window_offer.closed) {
                window_offer = window.open('${base}/resourceOffer/index?resourceId=' + resourceId, 'window_offer', 'height=600,width=1200,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
                window_offer.focus();
            } else {
                window_offer.focus();
                window_offer.selectTab(resourceCode);
            }
        }
        window.parent.setWindowOffer(window_offer);
    }

    function resourceApplyBank(resourceId) {
        $("#mainFrame", window.parent.document).attr("src", "${base}/resourceApply/apply-bank?resourceId=" + resourceId);
    }

    function checkResourceApplyBzj (resourceId) {
        $("#mainFrame", window.parent.document).attr("src", "${base}/resourceApply/apply-bzj?resourceId=" + resourceId);
    }
  </script>

</body>
</html>
