<html>
<head>
    <title>资格审核</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
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
    <span class="c_gray en">&gt;</span><span class="c_gray">资格审核</span>
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
        <th style="width:400px;border-right: 1px solid #e8e8e8;">资源描述</th>
        <th style="border-right: 1px solid #e8e8e8;">状态</th>
        <th style="width:137px;border-right: 1px solid #e8e8e8;">挂牌时间</th>
        <th style="width:137px">交保证金时间</th>
        <th style="width:150px;border-right: 1px solid #e8e8e8;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as transResource>

    <tr class="sep-row"><td colspan="5"></td></tr>
    <tr class="active">
        <td colspan="5" style="padding-left:5px">
            <div class="l" style="margin-top: 4px">
                <#if transResource.ggId??>
                    <#assign crgg=ResourceUtil.getCrgg(transResource.ggId)!/>
                    <#if crgg??>
                    ${crgg.ggNum!}
                    </#if>
                </#if>
                <i class="icon-th-large"></i>
            ${transResource.resourceCode!}
            </div>


        </td>
    </tr>
    <tr class="order-bd">
        <td>
            <a style="float: left">
                <img style="width:80px;height:80px;border: 1px solid #e9e9e9;"
                     src="${base}/${ImageUtil.url(transResource.resourceId,'402_320')}"  onerror="this.src='${base}/static/image/blank.jpg'" />
            </a>
            <div class="l" style="width: 300px;">
                <a href="${base}/console/qualified/view?resourceId=${transResource.resourceId!}" >
                    <span>${transResource.resourceLocation!}</span>
                   <#-- <br><span>保证金：人民币${transResource.fixedOffer?string("0.##")}万元<#if transResource.fixedOfferUsd??>，美元${resourceDto.transResource.fixedOfferUsd?string(",##0.##")}美元</#if>&nbsp;&nbsp;-->
                        <br><span>面积：${transResource.crArea?string("0.##")}平方米&nbsp;&nbsp;
                    ${(transResource.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                    ${(transResource.crArea*0.0001)?string("0.##")}公顷</span>
                </a>
            </div>
        </td>
        <td>
            <#--<div  style="margin-top: 2px">
                <span class="label label-danger" style="margin-top: 0px">未审核</span>
                <span class="label label-danger" style="margin-top: 0px">未通过</span>
                <span class="label label-success">通过</span>
            </div>-->
                报名中：<span class="label label-danger radius">${transResource.unpass!}</span><br/>
                未审核：<span class="label label-warning radius">${transResource.unVerif!}</span><br/>
                已通过：<span class="label label-success radius">${transResource.passed!}</span>

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
            <p>
                <em style="font-family: verdana;font-style: normal;">${transResource.bzjBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
            </p>
            <p>
                <em style="font-family: verdana;font-style: normal;">${transResource.bzjEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
            </p>
        </td>
        <td style="text-align: center">
            <div id="div_status_${transResource.resourceId!}" style="margin-top: 2px">

                <a   href="${base}/console/qualified/list/resourceApply?resourceId=${transResource.resourceId!}" class="btn btn-primary">竞买人审核</a>

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