<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>国有建设用地网上交易系统-资料下载</title>
    <link href="${base}/static/js/dist/material.css" rel="stylesheet" type="text/css" />
</head>

<body class="bg">
<div class="wp">
    <div class="row cl" >
        <span class="label label-success radius" style="font-size:15px;margin-top:20px">CA相关：</span>
        <table class="table table-border table-bordered table-striped">
            <thead>
            <tr>
                <th width="20px">序号</th>
                <th width="800px">名称</th>
                <th width="200px">操作</th>
            </tr>
            </thead>
            <tbody>
            <#if materials??>
                <#list materials?keys as key>
                <tr>
                    <td>${key_index+1}</td>
                    <td>${key}</td>
                    <td><a href="${base}/material/get?fileId=${materials[key]}" class="btn btn-success radius"><i class="icon-download"/>下载</a></td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <span class="label label-success radius" style="font-size:15px;margin-top:20px">出让相关：</span>
        <table class="table table-border table-bordered table-striped">
            <thead>
            <tr>
                <th width="20px">序号</th>
                <th width="800px">名称</th>
                <th width="200px">操作</th>
            </tr>
            </thead>
            <tbody>
            <#if materials_cr??>
                <#list materials_cr?keys as key>
                <tr>
                    <td>${key_index+1}</td>
                    <td>${key}</td>
                    <td><a href="${base}/material/get?fileId=${materials_cr[key]}" class="btn btn-success radius"><i class="icon-download"/>下载</a></td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>


</body>
</html>
