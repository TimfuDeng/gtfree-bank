<table class="table_list">
    <tr class="table_th">
        <td>编号</td>
        <td>标题</td>
        <td>接受时间</td>
        <td>答复时间</td>
    </tr>
<form class="navbar-form" id="frmSearch">
    <#list letterList.items as communion>
        <tr class="table_tr">
            <td align="center">${communion.publicNo!}</td>
            <td align="center"><a href="/jlnr/index.jhtml?letterId=${communion.communionId!}">${communion.publicTitle!}</a> </td>
            <td align="center">${communion.publicTime?string("yyyy-MM-dd")}</td>
            <td align="center">
                <#if communion.replyTime??>
                    ${communion.replyTime?string("yyyy-MM-dd")}
                </#if>
            </td>
        </tr>
    </#list>
</form>
</table>
<div class="pageNav">
<@PageHtml pageobj=letterList formId="frmSearch" />
</div>