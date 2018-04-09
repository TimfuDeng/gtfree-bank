<link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
<div class="wp">
    <div class="row cl">
        <table class="table table-border table-bordered table-striped" style="margin-top:20px">
            <thead>
            <tr>
                <th width="40px">序号</th>
                <th width="800px">名称</th>
                <th width="200px">操作</th>
            </tr>
            </thead>
            <tbody>
            <#if materials??>
                <#list materials.items as material>
                <tr>
                    <td>${material_index+1}</td>
                    <td>${material.fileName}</td>
                    <td>
                        <#if material.storePath??>
                            <a href="${core}/transfile/get?fileId=${material.fileId}" class="btn btn-success radius"><i
                                    class="icon-download"/>下载</a>
                        <#else>
                            <a href="javascript:void(0)" class="btn size-S btn-secondary">无文件</a>
                        </#if>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>

<script>
    //分页参数
    var currentPage = isEmpty($("#currentPage").val()) ? 1 : $("#currentPage").val();

    currentPage = ${materials.pageCount!}
    ==
    0 ? 1 : currentPage;
    var totalPages = ${materials.pageCount!}
    ==
    0 ? 1 : '${materials.pageCount!}';
    $("#totalPages").val(totalPages);
    $("#currentPage").val(currentPage);
    loadpage();
</script>