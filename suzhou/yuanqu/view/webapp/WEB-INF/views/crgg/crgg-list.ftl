<#list transCrggList.items as crgg>
<ul>
    <li>
        <#if crgg.ggTitle?length lt 30>
            <a href="/ggnrnew/index.jhtml?crggId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle}</a>
            <span style="float:right;color:#AAAAAA">${crgg.ggBeginTime?string("yyyy-MM-dd")}</span>
        <#else>
            <a href="/ggnrnew/index.jhtml?crggId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle[0..29]}...</a>
            <span style="float:right;;color:#AAAAAA">${crgg.ggBeginTime?string("yyyy-MM-dd")}</span>
        </#if>
    </li>
</ul>
</#list>