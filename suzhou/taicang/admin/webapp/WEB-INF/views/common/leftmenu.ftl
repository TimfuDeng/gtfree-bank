<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>${_title!}</title>
${_head!}
</head>
<body>
<div class="wp">
    <div class="row cl" >
        <div class="col-2" style="padding-right: 10px">
            <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
<#if AuthorizeUtil.isPermitted("/console/crgg/list","")>
                <li role="presentation"  class="${(_meta.menu=='menu_admin_crgglist')?string('active','')}"><a href="${base}/console/crgg/list"><i class="icon-tasks"></i>&nbsp;&nbsp;出让公告</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/resource/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_resourcelist')?string('active','')}"><a href="${base}/console/resource/list"><i class="icon-th-large"></i>&nbsp;&nbsp;  出让地块</a></li>
                <li role="presentation" class="${(_meta.menu=='menu_oneprice_resourcelist')?string('active','')}"><a href="${base}/oneprice/resource/list"><i class="icon-th-large"></i>&nbsp;&nbsp;  地块设置[一次报价]</a></li>
                <li role="presentation" class="${(_meta.menu=='menu_oneprice_betweenlist')?string('active','')}"><a href="${base}/oneprice/between/list"><i class="icon-linkedin-sign"></i>&nbsp;&nbsp;  参数设置[一次报价]</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/money/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_moneyresourcelist')?string('active','')}"><a href="${base}/console/money/list"><i class="icon-bar-chart"></i>&nbsp;&nbsp;财务管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/price/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_priceresourcelist')?string('active','')}"><a href="${base}/console/price/list"><i class="icon-upload-alt"></i>&nbsp;&nbsp;底价管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/bail/resource/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_bailresourcelist')?string('active','')}"><a href="${base}/console/bail/resource/list"><i class=" icon-trophy"></i>&nbsp;&nbsp;保证金查询</a></li>
</#if>
                <hr class="nav-separator" style="width: 190px">
                <div class="clearfix"></div>
<#if AuthorizeUtil.isPermitted("/console/user/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_userlist')?string('active','')}"><a href="${base}/console/user/list?userType=0"><i class="icon-user"></i>&nbsp;&nbsp;人员管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/bank/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_banklist')?string('active','')}"><a href="${base}/console/bank/list"><i class="icon-money"></i>&nbsp;&nbsp;银行管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/trans/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_translist')?string('active','')}"><a href="${base}/console/trans/list"><i class="icon-th-large"></i>&nbsp;&nbsp;大屏幕管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/ca-user/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_causerlist')?string('active','')}"><a href="${base}/console/ca-user/list"><i class="icon-th-large"></i>&nbsp;&nbsp;CA注册管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/log/list","")>
                <li role="presentation" class="${(_meta.menu=='menu_admin_loglist')?string('active','')}"><a href="${base}/console/log/list"><i class="icon-th-large"></i>&nbsp;&nbsp;日志管理</a></li>
</#if>
            </ul>
        </div>
        <div class="col-10">
        ${_body}
        </div>
    </div>
</div>
</body>
</html>