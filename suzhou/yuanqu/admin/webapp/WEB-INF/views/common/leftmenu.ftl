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
                <li role="presentation" class="${(_meta.menu=='menu_admin_resourcelist')?string('active','')}"><a href="${base}/console/resource/list"><i class="icon-th-large"></i>&nbsp;&nbsp;出让地块</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/suspendNotice/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_suspendNotice')?string('active','')}"><a href="${base}/console/suspendNotice/list"><i class="icon-tasks"></i>&nbsp;&nbsp;其他公告</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/price/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_priceresourcelist')?string('active','')}"><a href="${base}/console/price/list"><i class="icon-upload-alt"></i>&nbsp;&nbsp;底价管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/qualified/list/land","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_qualifiedlist')?string('active','')}"><a href="${base}/console/qualified/list/land"><i class="icon-th-large"></i>&nbsp;&nbsp;资格审核</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/verify/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_verify')?string('active','')}"><a href="${base}/console/verify/list?status=9&dealStatus=30"><i class="icon-th-large"></i>&nbsp;&nbsp;成交审核</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/dealNotice/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_dealNotice')?string('active','')}"><a href="${base}/console/dealNotice/list"><i class="icon-tasks"></i>&nbsp;&nbsp;成交公示</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/money/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_moneyresourcelist')?string('active','')}"><a href="${base}/console/money/list"><i class="icon-bar-chart"></i>&nbsp;&nbsp;保证金管理</a></li>
</#if>
    <hr class="nav-separator" style="width: 190px">
    <div class="clearfix"></div>
<#if AuthorizeUtil.isPermitted("/console/file/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_filelist')?string('active','')}"><a href="${base}/console/file/list"><i class="icon-th-large"></i>&nbsp;&nbsp;资料管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/bank/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_banklist')?string('active','')}"><a href="${base}/console/bank/list"><i class="icon-money"></i>&nbsp;&nbsp;银行管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/user/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_userlist')?string('active','')}"><a href="${base}/console/user/list?userType=1"><i class="icon-user"></i>&nbsp;&nbsp;人员管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/user/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_register')?string('active','')}"><a href="${base}/console/user/list?userType=0"><i class="icon-user"></i>&nbsp;&nbsp;竞买人注册</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/role/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_rolelist')?string('active','')}"><a href="${base}/console/role/list"><i class="icon-th-large"></i>&nbsp;&nbsp;角色管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/trans/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_translist')?string('active','')}"><a href="${base}/console/trans/list"><i class="icon-th-large"></i>&nbsp;&nbsp;大屏幕管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/count/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_countlist')?string('active','')}"><a href="${base}/console/count/list"><i class="icon-th-large"></i>&nbsp;&nbsp;查询统计</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/dept/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_deptlist')?string('active','')}"><a href="${base}/console/dept/list"><i class="icon-th-large"></i>&nbsp;&nbsp;机构管理</a></li>
</#if>


<#if AuthorizeUtil.isPermitted("/console/log/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_loglist')?string('active','')}"><a href="${base}/console/log/list"><i class="icon-th-large"></i>&nbsp;&nbsp;日志管理</a></li>
</#if>


<#--
<#if AuthorizeUtil.isPermitted("/console/news/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_newslist')?string('active','')}"><a href="${base}/console/news/list"><i class="icon-th-large"></i>&nbsp;&nbsp;新闻管理</a></li>
</#if>
<#if AuthorizeUtil.isPermitted("/console/transLaw/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_transLaw')?string('active','')}"><a href="${base}/console/transLaw/list"><i class="icon-th-large"></i>&nbsp;&nbsp;法律依据管理</a></li>
</#if>
-->

<#--<#if AuthorizeUtil.isPermitted("/console/communion/list","")>
    <li role="presentation" class="${(_meta.menu=='menu_admin_communionlist')?string('active','')}"><a href="${base}/console/communion/list"><i class="icon-th-large"></i>&nbsp;&nbsp;互动交流</a></li>
</#if>-->
<#--
    <hr class="nav-separator" style="width: 190px">
    <div class="clearfix"></div>


    <hr class="nav-separator" style="width: 190px">
    <div class="clearfix"></div>
-->



            </ul>
        </div>
        <div class="col-10">
        ${_body}
        </DIV>
    </div>
</div>
</body>
</html>