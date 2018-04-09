<html>
<head>
    <title>竞买人列表</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
    <style type="text/css">
    </style>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource/list">出让地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人列表</span>
</nav>
<div id="tab_demo" class="HuiTab">
    <div class="tabBar cl" style="border-bottom: 2px solid #999999;"><span class="current">竞买人列表</span>
        <a href="${base}/console/resource-apply/offerlist?resourceId=${resourceId}"> <span>竞买人报价</span></a></div>
</div>
<table class="table table-border table-bordered table-striped table-bg " >
    <thead>
    <tr class="text-c" >
        <th style="width:120px;">号牌</th>
        <th >竞买人</th>
        <th style="width:155px;">报名时间</th>
        <th style="width:137px;">竞买方式</th>
        <th style="width:50px">状态</th>
        <th style="width:120px">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list transResourceApplyList as resourceApply>

    <tr class="order-bd">
        <td>
            ${resourceApply.applyNumber!}
        </td>
        <td>
        <@userInfo userId=resourceApply.userId />
        </td>
        <td>
        ${resourceApply.applyDate?string("yyyy-MM-dd HH:mm")}
        </td>
        <td style="text-align: center">
            <#if resourceApply.applyType==0>
                <span>独立竞买</span>
            <#else>
                <span>联合竞买</span>
            </#if>
        </td>
        <td>
	        <#if resourceApply.applyStep==1>
	            <span class="label label-danger">资料未提交</span>
	        <#elseif resourceApply.applyStep==2>
	            <span class="label label-danger">未交保证金</span>
	        <#elseif resourceApply.applyStep==3>
	            <span class="label label-danger">已审核但未交保证金</span>
	        <#elseif resourceApply.applyStep==4>
	        	<span class="label label-danger">已交保证金</span>
	        </#if>
        </td>
        <td style="text-align: center">
            <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/console/resource-apply/attachment?userId=${resourceApply.userId!}&resourceId=${resourceId!}">查看附件材料>>></a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
</body>
</html>