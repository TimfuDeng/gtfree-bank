<#list transCrggList as crgg>
<ul>
    <li>
        <#if crgg.ggType=='1'>
            <#if crgg.ggTitle?length lt 200>
                <a href="/ggnrnew/index.jhtml?crggId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle}</a>
                <span style="float:right;color:#AAAAAA">${crgg.ggTime?string("yyyy-MM-dd")}</span>
            <#else>
                <a href="/ggnrnew/index.jhtml?crggId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle[0..200]}...</a>
                <span style="float:right;;color:#AAAAAA">${crgg.ggTime?string("yyyy-MM-dd")}</span>
            </#if>
            <#else >
                <#if crgg.ggTitle?length lt 200>
                    <a href="/zzggnrnew/index.jhtml?noticeId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle}</a>
                    <span style="float:right;color:#AAAAAA">${crgg.ggTime?string("yyyy-MM-dd")}</span>
                <#else>
                    <a href="/zzggnrnew/index.jhtml?noticeId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle[0..200]}...</a>
                    <span style="float:right;;color:#AAAAAA">${crgg.ggTime?string("yyyy-MM-dd")}</span>
                </#if>
        </#if>

    </li>
</ul>
</#list>