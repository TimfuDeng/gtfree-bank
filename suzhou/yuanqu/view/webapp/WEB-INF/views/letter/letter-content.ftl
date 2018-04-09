<table class="table_list">
    <tr class="table_th" align="center">
        <td colspan="4">信件内容</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">编号</td>
        <td class="label" colspan="3">${letter.publicNo!}</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">标题</td>
        <td class="label" colspan="3">${letter.publicTitle!}</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">内容</td>
        <td class="label" colspan="3">${letter.publicContent!}</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">联系人</td>
        <td class="label" colspan="3">${letter.contacter!}</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">联系方式</td>
        <td class="label" colspan="3">${letter.phoneNum!}</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">接受时间</td>
        <td class="label" colspan="3">${letter.publicTime?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">答复时间</td>
        <td class="label" colspan="3">
            <#if letter.replyTime??>
                ${letter.replyTime?string("yyyy-MM-dd HH:mm:ss")}
            </#if>
        </td>
    </tr>
    <tr class="contentRowLight">
        <td class="table_th">答复内容</td>
        <td class="label" colspan="3">${letter.replyContent!}</td>
    </tr>
</table>