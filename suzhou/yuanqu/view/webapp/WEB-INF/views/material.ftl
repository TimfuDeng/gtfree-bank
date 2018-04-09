<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>国有建设用地网上出让系统-资料下载</title>
    <link href="${base}/static/js/dist/material.css" rel="stylesheet" type="text/css" />
</head>

<body class="bg">
<div class="wp">
    <div class="row cl" >
        <table  class="table table-hover table-striped" >
            <tr>
                <th>名称</th>
                <th>操作</th>
            </tr>

        <#list transFilePage.items as filePage>
            <tr>
                <#if filePage.storePath?? >
                <td align="center">${filePage.fileName!}</td>
                <td style="text-align: center;width: 150px">
                    <a href="${base}/material/file/get?fileId=${filePage.fileId!}" class="btn btn-success radius"><i class="icon-download"/>下载</a>
                </td>
                </#if>
            </tr>
        </#list>
        </table>
    </div>
</div>
<@PageHtml pageobj=transFilePage formId="frmSearch" />

</body>
</html>
