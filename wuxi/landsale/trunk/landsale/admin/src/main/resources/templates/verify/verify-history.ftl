<html>
<head>
    <title>资格审核</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
    <style type="text/css">
    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="javascript:changeSrc('${base}/index')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/verify/list')">审核管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">审核记录</span>
</nav>

<table class="table table-border table-bordered table-striped table-bg " >
    <thead>
    <tr class="text-c" >
        <th >竞买人</th>
        <th style="width:20%;">审核人</th>
        <th style="width:20%;">审核时间</th>
        <th style="width:10%;">是否通过</th>
        <th style="width:20%">操作</th>
    </tr>
    </thead>
    <tbody>
    <#if transResourceVerifyList??>
        <#list transResourceVerifyList as transResourceVerify>
        <tr class="order-bd">
            <td style="text-align: center;">
                <@userInfo userId=transResourceVerify.userId />
            </td>
            <td style="text-align: center">
            ${UserUtil.getUserName(transResourceVerify.auditor!)}
            </td>
            <td style="text-align: center">
                <#if transResourceVerify.verifyTime?exists>
                    ${transResourceVerify.verifyTime!?string("yyyy-MM-dd HH:mm")}
                </#if>
            </td>
            <td style="text-align: center">
                <#if transResourceVerify?? && transResourceVerify.verifyStatus==1 >已通过<#elseif transResourceVerify?? && transResourceVerify.verifyStatus==2 >未通过<#else>未审核</#if>
            </td>
            <td style="text-align: center">
                <a   href="javascript:changeSrc('${base}/verify/verifyHistory/detail', {resourceId: '${resourceId!}', verifyId: '${transResourceVerify.verifyId!}'})" class="btn size-S btn-primary">详情</a>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
</body>
</html>