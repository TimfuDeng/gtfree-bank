<html>
<head>
    <title>审核管理</title>
    <meta name="menu" content="menu_admin_verify"/>
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
        $(document).ready(function(){
            $('#btn_modal').click(function(){
                checkForm();
            });
        });

        function showConfirm(resourceId){
            $("#form_resourceId").attr("value",resourceId);
            $('#myModal').modal({
                backdrop: false
            });
        }

        function setAndReloadStatus(){
            var resourceId = $("#form_resourceId").val();
            var msg = $("#msg").val();
            $.ajaxSetup ({
                cache: false
            });
            $.ajax({
                url:"${base}/console/verify/save",
                data:$("#verify_form").serialize(),
                type:"post",
                dataType:"json",
                success:function(){
                    $('#myModal').modal('hide');
                    $("#verifyStatus_" + resourceId).load("${base}/console/verify/statusBar/view.f?resourceId=" + resourceId);
                    $("#div_status_" + resourceId).load("${base}/console/verify/status/view.f?resourceId=" + resourceId);
                }
            });
        }
        function checkInputNull(name,info){
            if($("input[name='"+name+"']").val()==""){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }

        function checkForm(){
            if (!checkInputNull('auditor','审核人必须填写!')) {
                return false;
            }else if(!checkInputNull('verifyTime','审核时间必须填写!')){
                return false;
            }else if($("#verifySuggestion").val()==""){
                alert("审核内容必须填写！");
                return false;
            }else{
                setAndReloadStatus();
            }
        }
        function checkForm(){
            checkInputFileter();
            return true;
        }

    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
<#if ggId??>
    <#assign crgg=ResourceUtil.getCrgg(ggId)!/>
    <#if crgg??>
        <span class="c_gray en">&gt;</span><span class="c_gray">${crgg.ggNum!}</span>
    </#if>
</#if>
    <span class="c_gray en">&gt;</span><span class="c_gray">审核管理</span>
</nav>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="title" value="${title!}" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="${status!}">
                <button type="submit" class="btn" onclick="return checkForm()">查询</button>
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
        <th style="width:155px;border-right: 1px solid #e8e8e8;">挂牌时间</th>
        <th style="width:137px;border-right: 1px solid #e8e8e8;">交易状态</th>
        <th style="width:137px;border-right: 1px solid #e8e8e8;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as transResource>

    <tr class="sep-row"><td colspan="4"></td></tr>
    <tr class="active">
        <td colspan="4" style="padding-left:5px">
            <div class="l" style="margin-top: 4px">
                <#if transResource.ggId??>
                    <#assign crgg=ResourceUtil.getCrgg(transResource.ggId)!/>
                    <#if crgg??>
                    ${crgg.ggNum!}
                    </#if>
                </#if>
                <i class="icon-th-large"></i>
            ${transResource.resourceCode!}
                <span id="verifyStatus_${transResource.resourceId}">
                    <#include "verify-status.ftl">
                </span>
            </div>
        </td>
    </tr>
    <tr class="order-bd">
        <td>
            <a style="float: left">
                <img style="width:80px;height:80px;border: 1px solid #e9e9e9;"
                     src="${base}/${ImageUtil.url(transResource.resourceId,'402_320')}"  onerror="this.src='${base}/static/image/blank.jpg'" />
            </a>
            <div class="l" style="width: 400px;">
                <a href="${base}/console/verify/view?resourceId=${transResource.resourceId!}" >
                    <span>${transResource.resourceLocation!}</span>
                    <br><span>保证金：人民币${transResource.fixedOffer?string("0.######")}万元<#if transResource.fixedOfferUsd??>，美元${transResource.fixedOfferUsd?string(",##0.######")}美元</#if><#if resource.fixedOfferHkd??>，港币${transResource.fixedOfferHkd?string(",##0.######")}万港币</#if>&nbsp;&nbsp;
                        <br><span>面积：${transResource.crArea?string("0.##")}平方米&nbsp;&nbsp;
                    ${(transResource.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                    ${(transResource.crArea*0.0001)?string("0.##")}公顷</span>
                </a>
            </div>
        </td>
        <td>
            <p>
                <em style="font-family: verdana;font-style: normal;">${transResource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
            </p>
            <p>
                <em style="font-family: verdana;font-style: normal;">${transResource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
            </p>
        </td>
        <td>
            <#if  transResource.resourceStatus==30>
                <#assign maxOffer=PriceUtil.getMaxOffer(transResource.resourceId)/>
                <#if maxOffer??>
                    <p>竞得人：<@userInfo userId=maxOffer.userId /></p>
                    <#if maxOffer.offerType==2>
                        <#assign maxOfferPrice=PriceUtil.getMaxPriceOffer(transResource.resourceId)/>
                        <p>竞得价：${maxOfferPrice.offerPrice}万元</p>
                        公租房<#if transResource.publicHouse?? && transResource.publicHouse==1 >资金（万元）<#else>面积（平方米）</#if>：${maxOffer.offerPrice}
                    <#else>
                        <p>竞得价：${maxOffer.offerPrice}万元</p>
                    </#if>
                    <p>
                        <a class="btn btn-primary size-MINI"  href="/admin/console/verify/attachment?userId=${maxOffer.userId}&resourceId=${transResource.resourceId}">查看竞得人附件材料</a>
                    </p>
                    <p>
                        <a class="btn btn-primary size-MINI"  href="/admin/console/verify/list/resourceApply?resourceId=${transResource.resourceId}">查看报名人附件材料</a>
                    </p>
                </#if>
            </#if>
        </td>
        <td style="text-align: center">
            <div id="div_status_${transResource.resourceId!}" style="margin-top: 2px">
                <#include "common/verify-status.ftl">
            </div>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />

<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>