<form class="navbar-form" id="frmSearch">
<#list transCrggList.items as crgg>
<ul>
    <li>
        <#if crgg.ggTitle?length lt 30>
            <a href="/ggnr/index.jhtml?crggId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle}</a>
            <span style="float:right;color:#AAAAAA">${crgg.ggBeginTime?string("yyyy-MM-dd")}</span>
        <#else>
            <a href="/nrgg/index.jhtml?crggId=${crgg.ggId!}" title="${crgg.ggTitle}" target="_blank" >${crgg.ggTitle[0..29]}...</a>
            <span style="float:right;color:#AAAAAA">${crgg.ggBeginTime?string("yyyy-MM-dd")}</span>
        </#if>
    </li>
</ul>
</#list>
</form>
<div class="pageNav">
<@PageHtml pageobj=transCrggList formId="frmSearch" />
</div>