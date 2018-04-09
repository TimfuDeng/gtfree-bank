<form class="navbar-form" id="frmSearch">
<#list transLaws.items as transLaw>
    <ul>
        <li>
            <#if transLaw.title?length lt 30>
                <a href="/fltlnr/index.jhtml?lawId=${transLaw.lawId!}" title="" target="_blank">${transLaw.title}</a>
                <span style="float:right;color:#AAAAAA">${transLaw.updateTime?string("yyyy-MM-dd")}</span>
            <#else>
                <a href="/fltlnr/index.jhtml?lawId=${transLaw.lawId!}" title="" target="_blank">${transLaw.title[0..29]}...</a>
                <span style="float:right;color:#AAAAAA">${transLaw.updateTime?string("yyyy-MM-dd")}</span>
            </#if>
        </li>
    </ul>
</#list>
</form>
<div class="pageNav">
<@PageHtml pageobj=transLaws formId="frmSearch" />
</div>