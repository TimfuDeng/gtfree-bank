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
                    <a href="${base}/oneprice/my/view-resource-list">
                        <i class="icon-th-large"></i>&nbsp;&nbsp;成交结果
                            <#--<span class="label label-success radius" style="width:15px!important;text-align: center;">1</span>-->
                    </a>
                </li>
            </ul>
        </div>
        <div class="col-10">
        ${_body}
        </div>
    </div>
</div>
</body>
</html>