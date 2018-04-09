<@Head_SiteMash title="国有建设用地网上交易系统-资料下载" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

</head>

<body class="bg">
<#include "common/head.ftl"/>
<form class="navbar-form" id="frmSearch">
    <input type="hidden" name="regionCode" value="${regionCode!}"/>
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
</form>
<@PageHtml pageobj=transFilePage formId="frmSearch" />
<#include "common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>
