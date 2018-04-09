<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>出资比例列表</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/index')">资格审核</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/resourceApply?resourceId=${resourceId!}')">竞买人审核</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">出资比例列表</span>
</nav>
<table class="table table-border table-bordered table-bg">
    <tr>
        <th style="text-align: center">被联合人</th>
        <th style="text-align: center">法人</th>
        <th style="text-align: center">出资比例(%)</th>
        <th style="text-align: center">地址</th>
        <th style="text-align: center">状态</th>
    </tr>
<#list transUserUnions as transUserUnion>
    <tr style="text-align: center">
        <td style="text-align: center">${transUserUnion.userName!}</td>
        <td style="text-align: center">${transUserUnion.legalPerson!}</td>
        <td style="text-align: center">${transUserUnion.amountScale!}</td>
        <td style="text-align: center">${transUserUnion.userAddress!}</td>
        <td style="text-align: center">
            <#if transUserUnion.agree==true>
                已同意
            <#else>
                未同意
            </#if>
        </td>
    </tr>
</#list>

</table>
<button  class="btn" onclick="javascript:changeSrc('${base}/qualified/resourceApply?resourceId=${resourceId!}');">返回</button>
</body>