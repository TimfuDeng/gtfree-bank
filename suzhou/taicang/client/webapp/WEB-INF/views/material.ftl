<@Head_SiteMash title="国有建设用地网上交易系统-资料下载" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

</head>

<body>
<#include "common/head.ftl"/>
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


<#include "common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>