<html>
<head>
    <title>出让地块</title>
    <meta name="menu" content="menu_oneprice_resourcelist"/>
    <style type="text/css">
        .col-name th {
            height: 38px;
            text-align: center;
            background: #f5f5f5;
            border-top: 1px solid #e8e8e8;
            border-bottom: 1px solid #e8e8e8;
            color: #3c3c3c;
            font: 12px/1.5 Tahoma,Helvetica,Arial,'微软雅黑 Regular',sans-serif;
        }
        .sep-row{
            height: 10px;
        }
        .sep-row td{
            border: 0;
        }
        .order-hd td{
            background: #f5f5f5;
            border-bottom-color: #f5f5f5;
            height: 40px;
            line-height: 40px;
            text-align: left;
            border: 1px solid #daf3ff;
        }
        .order-bd td{
            padding: 10px 5px;
            overflow: hidden;
            vertical-align: top;
            border-color: #e8e8e8;
            border: 1px solid #e8e8e8;
        }
        .l span{
            margin-left:15px;
        }
        .active{
            border: 1px solid #e8e8e8;
        }
    </style>
    <script type="text/javascript">
        var _transTargetId,_status;
        $(document).ready(function(){
            $('#btn_modal').click(function(){
                setAndReloadStatus();
            });
        });
        //申请发布
        function PreRelease(transTargetId){
            _transTargetId=transTargetId;_status=1;
            showConfirm("是否确认申请发布！",transTargetId,1);
        }
        //撤回
        function Break(transTargetId){
            _transTargetId=transTargetId;_status=0;
            showConfirm("是否确认撤回！",transTargetId,0);
        }

        function showConfirm(content){
            $('#modal_content').html(content);
            $('#myModal').modal({
                backdrop: false
            });
        }

        function setAndReloadStatus(){
            $('#myModal').modal('hide');
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/oneprice/resource/status/change.f",{"transTargetId":_transTargetId,"status":_status},
                function(data){
                    $('#myModal').modal('hide');
                    if(data.ret!=null&&data.ret==true)
                        $("#div_status_" + _transTargetId).load("${base}/oneprice/resource/status/view.f?transTargetId="+_transTargetId );
                    else
                        alert(data.msg);
                        $("#div_status_" + _transTargetId).load("${base}/oneprice/resource/status/view.f?transTargetId="+_transTargetId );
                },'json'
            );
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/oneprice/resource/list" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">出让地块</span>
</nav>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="title" value="${title!}" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="${status!}">
                <button type="submit" class="btn">查询</button>
            </div>

            <div class="r">

            </div>
        </form>
    </div>
</div>
<table class="table" style="table-layout: fixed;width: 100%;border-collapse: collapse;border-spacing: 2px;border-color: gray;">
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="border-right: 1px solid #e8e8e8;">资源描述</th>
            <th style="width:155px;border-right: 1px solid #e8e8e8;">中止时间</th>
            <th style="width:137px;border-right: 1px solid #e8e8e8;">市场指导价(元)</th>
            <th style="width:137px">操作</th>
        </tr>
    </thead>
    <tbody>
    <#list transResourceList.items as resource >

    <tr class="sep-row"><td colspan="4"></td></tr>
    <tr class="active">
        <td colspan="4" style="padding-left:5px">
            <div class="l" style="margin-top: 4px">
                <#if resource.ggId??>
                    <#assign crgg=ResultUtil.getTransCrgg(resource.ggId)!/>
                    <#if crgg??>
                    ${crgg.ggTitle!}
                    </#if>
                </#if>
                <i class="icon-th-large"></i>
            ${resource.resourceCode!}
            </div>
            <div id="div_status_${resource.resourceId!}" style="margin-top: 2px">
                <#assign oneTarget=ResultUtil.getOneTargetByTransTargetId(resource.resourceId)!>
                <#include "resource-status.ftl">
            </div>
        </td>
    </tr>
    <tr class="order-bd">
        <td>
            <a style="float: left">
                <img style="width:80px;height:80px;border: 1px solid #e9e9e9;"
                     src="${base}/static/image/blank.jpg"  />
            </a>
            <div class="l" style="width: 400px;">
                <span>${resource.resourceLocation!}</span>
                <br><span>保证金：人民币${resource.fixedOffer?string("0.######")}万元<#if resource.fixedOfferUsd??>，美元${resource.fixedOfferUsd?string(",##0.######")}万美元</#if><#if resource.fixedOfferHkd??>，港币${resource.fixedOfferHkd?string(",##0.######")}万港币</#if>&nbsp;&nbsp;
                <br><span>面积：${resource.crArea?string("0.##")}平方米&nbsp;&nbsp;
                ${(resource.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                ${(resource.crArea*0.0001)?string("0.##")}公顷</span>
            </div>

        </td>
        <td class="text-c">
            <#if oneTarget?? && oneTarget.stopDate??>
                ${(oneTarget.stopDate)?string("yyyy-MM-dd HH:mm:ss")}
            </#if>
        </td>

        <td class="text-c">
            <#if oneTarget??>
                ${oneTarget.priceGuid!}
            </#if>
        </td>
        <td >
            <p>
                <a  class="btn size-S btn-success" target="_blank" type="button" href="${base}/oneprice/resource/edit?id=${resource.resourceId!}">设置报价信息</a>
            </p>
            <p>
                <a  class="btn size-S btn-success" target="_blank" type="button" href="${base}/oneprice/resource/info?id=${resource.resourceId!}">报价信息导出</a>
            </p>
            <p>
                <a  class="btn size-S btn-success" target="_blank" type="button" href="${base}/oneprice/resource/success/edit?id=${resource.resourceId!}">设置成交信息</a>
            </p>

        </td>

    </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourceList formId="frmSearch" />
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel"><i class="icon-warning-sign"></i> 确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>