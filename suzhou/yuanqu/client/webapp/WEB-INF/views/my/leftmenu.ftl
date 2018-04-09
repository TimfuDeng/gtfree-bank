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
                <li role="presentation" class="${(_meta.menu=='menu_client_resourcelist')?string('active','')}">
                    <a href="${base}/my/resource-list">
                        <i class="icon-th-large"></i>&nbsp;&nbsp;我的交易
                        <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                        <#if applyCount gt 0>
                            <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                        </#if>
                    </a>
                </li>
                <li role="presentation" class="${(_meta.menu=='menu_client_unionlist')?string('active','')}"><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息
                <#assign applyedCount=ResourceUtil.getIsApplyedCountByStauts()>
                <#if applyedCount gt 0>
                    <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyedCount}</span>
                </#if>
                </a></li>
            </ul>
        </div>
        <div class="col-10">
        ${_body}
        </div>
    </div>
</div>
</body>
</html>