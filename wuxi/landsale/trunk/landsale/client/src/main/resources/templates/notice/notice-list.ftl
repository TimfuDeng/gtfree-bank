<table width="200" border="0" cellpadding="1" cellspacing="0">
    <tr>
        <td id="tr_title">公告名称</td>
        <td id="tr_title">公告编号</td>
        <td id="tr_title">公告开始时间</td>
    </tr>
<#list transCrggList.items as crgg>
    <tr>
        <td>
            <a href="#" onclick="loadCrgg('${crgg.ggId}')">
                <#if (crgg.ggTitle?length gt 30)>
                ${crgg.ggTitle?substring(0,30)}.
                <#else>
                ${crgg.ggTitle!}
                </#if>
            </a>
        </td>
        <td id="td_pd">
            <a href="#" onclick="loadCrgg('${crgg.ggId}')">
                <#if (crgg.ggNum?length gt 20)>
                ${crgg.ggNum?substring(0,20)}
                <#else>
                ${crgg.ggNum!}
                </#if>
            </a>
        </td>
        <td>${crgg.ggBeginTime!?string("yyyy-MM-dd")}</td>
    </tr>
</#list>
</table>

<script>
    //分页参数
    var currentPage = isEmpty($("#currentPage").val()) ? 1 : $("#currentPage").val();

    currentPage = ${transCrggList.pageCount!}
    ==
    0 ? 1 : currentPage;
    var totalPages = ${transCrggList.pageCount!}
    ==
    0 ? 1 : '${transCrggList.pageCount!}';
    $("#totalPages").val(totalPages);
    $("#currentPage").val(currentPage);
    loadpage();
</script>