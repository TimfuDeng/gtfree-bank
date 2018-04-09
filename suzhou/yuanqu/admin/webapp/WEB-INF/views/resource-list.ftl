<html>
<head>
    <title>出让地块</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
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
        var _resourceId,_status;
        $(document).ready(function(){
            $('#btn_modal').click(function(){
                setAndReloadStatus();
            });
        });
        //申请发布
        function PreRelease(resourceId){
            _resourceId=resourceId;_status=1;
            showConfirm("是否确认申请发布！",resourceId,1);
        }
        function BackEdit(resourceId){
            _resourceId=resourceId;_status=0;
            showConfirm("是否确认返回编辑！",resourceId,0);
        }
        function Release(resourceId){
            _resourceId=resourceId;_status=2;
            showConfirm("是否确认正式发布！",resourceId,2);
        }
        function Break(resourceId){
            _resourceId=resourceId;_status=3;
            showConfirm("是否确认中止！",resourceId,3);
        }
        function ReStart(resourceId){
            _resourceId=resourceId;_status=2;
            showConfirm("是否确认重新启动！",resourceId,2);
        }

        function Stop(resourceId){
            _resourceId=resourceId;_status=4;
            showConfirm("是否确认终止！",resourceId,4);
        }
        function showConfirm(content){
            $('#modal_content').html(content);
            $('#myModal').modal({
                backdrop: false
            });
        }
        function setAndReloadStatus(){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/resource/status/change.f",{"resourceId":_resourceId,"status":_status},
                function(data){
                    $('#myModal').modal('hide');
                    if(data.ret!=null&&data.ret==true)
                        $("#div_status_" + _resourceId).load("${base}/console/resource/status/view.f?resourceId="+_resourceId );
                    else{
                        alert(data.msg);
                        $("#div_status_" + _resourceId).load("${base}/console/resource/status/view.f?resourceId="+_resourceId );
                    }
                },'json'
            );
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
    <span class="c_gray en">&gt;</span><span class="c_gray">出让地块</span>
</nav>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="title" value="${title!}" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="${status!}">
                <button type="submit" class="btn" onclick="return checkForm()">查询</button>
            </div>
            <div class="l" style="margin-left:10px">
                <span >编辑状态：</span>
                <a href="${base}/console/resource/list?status=-1">
                <span class="${(status==-1)?string('label label-select','')}">不限</span>
                </a>
                <a href="${base}/console/resource/list?status=0">
                <span class="${(status==0)?string('label label-select','')}">正在编辑</span>
                </a>
                <a href="${base}/console/resource/list?status=1">
                <span class="${(status==1)?string('label label-select','')}">申请发布</span>
                </a>
                <a href="${base}/console/resource/list?status=2">
                <span class="${(status==2)?string('label label-select','')}">正在发布</span>
                </a>
                <a href="${base}/console/resource/list?status=3">
                <span class="${(status==3)?string('label label-select','')}">已中止</span>
                </a>
                <a href="${base}/console/resource/list?status=4">
                <span class="${(status==4)?string('label label-select','')}">已终止</span>
                </a>
                <a href="${base}/console/resource/list?status=9">
                <span class="${(status==9)?string('label label-select','')}">已结束</span>
                </a>
            </div>
            <div class="r">

            </div>
            <#if ggId??>
                <input type="hidden" name="ggId" value="${ggId}"/>
            </#if>
        </form>
    </div>
</div>
<table class="table" style="table-layout: fixed;width: 100%;border-collapse: collapse;border-spacing: 2px;border-color: gray;">
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="border-right: 1px solid #e8e8e8;">资源描述</th>
            <th style="width:155px;border-right: 1px solid #e8e8e8;">挂牌时间</th>
            <th style="width:137px;border-right: 1px solid #e8e8e8;">起始价（万元）</th>
            <th style="width:137px;border-right: 1px solid #e8e8e8;">交易状态</th>
        </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>

        <tr class="sep-row"><td colspan="4"></td></tr>
        <#if resource_index==0>
            <#assign  ggIdFlag=resource.ggId>
        </#if>
        <#if (ggIdFlag!=resource.ggId) || (resource_index==0)>
            <#assign  ggIdFlag=resource.ggId>
            <tr class="active">
            <td colspan="4" style="padding-left:5px">
                <div class="l" style="margin-top: 4px">
                    <#if resource.ggId??>
                        <#assign crgg=ResourceUtil.getCrgg(resource.ggId)!/>
                        <#if crgg??>
                        ${crgg.ggNum!}
                        </#if>
                    </#if>
                </div>
            </td>
            </tr>
            <tr class="sep-row"><td colspan="4"></td></tr>
        </#if>


        <tr class="order-bd">
            <td colspan="4" style="padding-left:25px">
                <div class="l" style="margin-top: 4px">
                    <#if resource.ggId??>
                        <#assign crgg=ResourceUtil.getCrgg(resource.ggId)!/>
                        <#if crgg??>
                            <#--${crgg.ggNum!}-->
                        </#if>
                    </#if>
                    <i class="icon-th-large"></i>
                    ${resource.resourceCode!}
                </div>
                <div id="div_status_${resource.resourceId!}" style="margin-top: 2px">
                    <#include "common/resource-status.ftl">
                </div>
            </td>
        </tr>
        <tr class="order-bd">
            <td>
                <a style="float: left;padding-left:25px">
                    <img style="width:80px;height:80px;border: 1px solid #e9e9e9;"
                         src="${base}/${ImageUtil.url(resource.resourceId,'402_320')}"  onerror="this.src='${base}/static/image/blank.jpg'" />
                </a>
                <div class="l" style="width: 390px;">
                    <a href="${base}/console/resource/view?resourceId=${resource.resourceId!}" >
                        <span>${resource.resourceLocation!}</span>
                        <br><span>保证金：人民币${resource.fixedOffer?string("0.######")}万元<#if resource.fixedOfferUsd??>，美元${resource.fixedOfferUsd?string(",##0.######")}万美元</#if><#if resource.fixedOfferHkd??>，港币${resource.fixedOfferHkd?string(",##0.######")}万港币</#if>&nbsp;&nbsp;
                        <br><span>面积：${resource.crArea?string("0.##")}平方米&nbsp;&nbsp;
                        ${(resource.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                        ${(resource.crArea*0.0001)?string("0.##")}公顷</span>
                    </a>
                </div>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td style="text-align: center">
                <p>
                    <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                    ${resource.beginOffer!}</em>
                    <p style="margin-bottom:0px"><em>${(resource.beginOffer*10000/resource.crArea)?string("0")}元/平方米</em></p>
                    <p style="margin-bottom:0px"><em>${(resource.beginOffer/(resource.crArea*0.0015))?string("0.#")}万元/亩</em></p>
                </p>
            </td>
            <td>
                <#if  resource.resourceStatus==30>
                    <#if resource.offerId??>
                        <#assign maxOffer=PriceUtil.getSuccessOffer(resource.offerId!) />
                       <#if maxOffer?? && maxOffer.userId??>
                            <p>竞得人：<@userInfo userId=maxOffer.userId /></p>
                                <#if maxOffer.offerType==2>
                                    <#assign maxOfferPrice=PriceUtil.getMaxPriceOffer(resource.resourceId)/>
                                    <p>竞得价：${maxOfferPrice.offerPrice}万元</p>
                                    公租房<#if resource.publicHouse?? && resource.publicHouse==1 >资金（万元）<#else>面积（平方米）</#if>：${maxOffer.offerPrice}
                                <#else>
                                    <p>竞得价：${maxOffer.offerPrice}万元</p>
                                </#if>
                        </#if>
                    </#if>
                    <#--<a href="${base}/console/resource/cjqrs.f?resourceId=${resource.resourceId}" class="btn btn-success size-S">成交确认书</a>-->

                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
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