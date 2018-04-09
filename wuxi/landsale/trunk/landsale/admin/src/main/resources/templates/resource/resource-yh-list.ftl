<html>
<head>
    <title>出让地块</title>
    <meta name="menu" content="menu_admin_yhresourcelist"/>
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
    <script>
        function postResult(resourceId) {
            $.ajax({
                url:"${base}/resource/yh/success/post",//提交地址
                data:{resourceId :resourceId},
                type:"POST",
                async:"false",
                dataType:"json",
                success:function(result){
                    if (result.flag == true){
                        _alertResult('ajaxResultDiv', true, result.message);
                        reloadSrc('frmSearch');
                    }else{
                        _alertResult('ajaxResultDiv', false, result.message);
                    }
                }
            });
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="javascript:changeSrc('${base}/index')" class="maincolor">首页</a>
<#if ggId??>
    <#assign crgg=ResourceUtil.getCrgg(ggId)!/>
    <#if crgg??>
        <span class="c_gray en">&gt;</span><span class="c_gray">${crgg.ggNum!}</span>
    </#if>
</#if>
    <span class="c_gray en">&gt;</span><span class="c_gray">设置摇号地块(成交)</span>
</nav>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="title" value="${title!}" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="${status!}">
                <button type="button" onclick="reloadSrc('frmSearch')" class="btn">查询</button>
            </div>
            <div class="l" style="margin-left:10px">
                <span >编辑状态：</span>
                <a href="javascript:reloadSrc('frmSearch', {status: -1})">
                    <span class="${(status==-1)?string('label label-select','')}">不限</span>
                </a>
              <#--  <a href="${base}/resource/list?status=0">
                    <span class="${(status==0)?string('label label-select','')}">正在编辑</span>
                </a>-->
               <#-- <a href="${base}/resource/list?status=1">
                    <span class="${(status==1)?string('label label-select','')}">申请发布</span>
                </a>-->
                <a href="javascript:reloadSrc('frmSearch', {status: 2})">
                    <span class="${(status==2)?string('label label-select','')}">正在发布</span>
                </a>
                <a href="javascript:reloadSrc('frmSearch', {status: 3})">
                    <span class="${(status==3)?string('label label-select','')}">已中止</span>
                </a>
                <a href="javascript:reloadSrc('frmSearch', {status: 4})">
                    <span class="${(status==4)?string('label label-select','')}">已终止</span>
                </a>
                <a href="javascript:reloadSrc('frmSearch', {status: 9})">
                    <span class="${(status==9)?string('label label-select','')}">已结束</span>
                </a>
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
        <th style="width:137px;border-right: 1px solid #e8e8e8;">最高限价</th>
        <th style="width:137px">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>

    <tr class="sep-row"><td colspan="4"></td></tr>
    <tr class="active">
        <td colspan="4" style="padding-left:5px">
            <div class="l" style="margin-top: 4px">
                <#if resource.ggId??>
                    <#assign crgg=ResourceUtil.getCrgg(resource.ggId!)/>
                    <#if crgg??>
                    ${crgg.ggNum!}
                    </#if>
                </#if>
                <i class="icon-th-large"></i>
            ${resource.resourceCode!}
            </div>
            <#--<#if true = ResourceUtil.isYhResultExist(resource.resourceId)>-->
                <#--<div id="div_status" style="margin-top: 2px">-->
                    <#--<div id="btn_action" class="r">-->
                        <#--<input class="btn size-S btn-primary" type="button" value="发布" onclick="postResult('${resource.resourceId!}')">-->
                    <#--</div>-->
                <#--</div>-->
            <#--</#if>-->
        </td>
    </tr>
    <tr class="order-bd">
        <td>
            <a style="float: left">
                <img style="width:80px;height:80px;border: 1px solid #e9e9e9;"
                     <#--src="${base}/${ImageUtil.url(resource.resourceId,'402_320')}"  onerror="this.src='${base}/static/image/blank.jpg'" />-->
                     src="${base}/image/blank.jpg" />
            </a>
            <div class="l" style="width: 400px;">
                <a href="javascript:changeSrc('${base}/resource/view?resourceId=${resource.resourceId!}')" >
                    <span>${resource.resourceLocation!}</span>
                    <br><span>保证金：人民币${resource.fixedOffer?string("0.##")}万元<#if resource.fixedOfferUsd??>，美元${resource.fixedOfferUsd?string(",##0.##")}美元</#if>&nbsp;&nbsp;
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
                ${resource.maxOffer!}<span><@unitText value=resource.offerUnit! /></span></em>
            <p style="margin-bottom:0px"><em>${values[resource_index].price1}${values[resource_index].unit1}</em></p>
            <p style="margin-bottom:0px"><em>${values[resource_index].price2}${values[resource_index].unit2}</em></p>
            </p>
        </td>
        <td>
            <a class="btn size-S btn-primary" type="button" href="javascript:changeSrc('${base}/resource/yh/resource?resourceId=${resource.resourceId!}')" >设置成交信息</a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
</body>
</html>