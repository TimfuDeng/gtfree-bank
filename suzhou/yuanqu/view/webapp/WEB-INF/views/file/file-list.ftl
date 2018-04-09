<form class="navbar-form" id="frmSearch">
<#list transFilePage.items as filePage>
<ul>
    <li>
        <#if filePage.storePath?? >
        ${filePage.fileName!}
            <span style="float: right"><a href="/view/file/get?fileId=${filePage.fileId!}" class="btn btn-success radius"><i class="icon-download"/>下载</a></span>
        </#if>
    </li>
</ul>
</#list>
</form>
<div class="pageNav">
<@PageHtml pageobj=transFilePage formId="frmSearch" />
</div>