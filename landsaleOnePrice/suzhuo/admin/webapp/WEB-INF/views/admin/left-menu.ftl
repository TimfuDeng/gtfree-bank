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
                <#if AuthorizeUtil.isPermitted("/oneprice/resource/list","")>
                    <li role="presentation" class="${(_meta.menu=='menu_admin_resourcelist')?string('active','')}"><a href="${base}/oneprice/resource/list"><i class="icon-th-large"></i>&nbsp;&nbsp;  出让地块</a></li>
                </#if>
                <hr class="nav-separator" style="width: 190px">
                 <div class="clearfix"></div>
                <#if AuthorizeUtil.isPermitted("/oneprice/between/list","")>
                    <li role="presentation" class="${(_meta.menu=='menu_admin_betweenlist')?string('active','')}"><a href="${base}/oneprice/between/list"><i class="icon-linkedin-sign"></i>&nbsp;&nbsp;参数设置</a></li>
                </#if>
                <#if AuthorizeUtil.isPermitted("/oneprice/user/list","")>
                    <li role="presentation" class="${(_meta.menu=='menu_admin_userlist')?string('active','')}"><a href="${base}/oneprice/user/list"><i class="icon-user"></i>&nbsp;&nbsp;人员管理</a></li>
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