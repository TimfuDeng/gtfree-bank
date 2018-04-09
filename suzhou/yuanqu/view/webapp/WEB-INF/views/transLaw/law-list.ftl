<#list transLaws.items as transLaw>
<ul>
    <li>
        <#if transLaw.title?length lt 20>
            <a href="/fltinr/index.jhtml?lawId=${transLaw.lawId!}" title="" target="_blank">${transLaw.title}</a>
            <span style="float:right;color:#AAAAAA">${transLaw.deployTime?string("yyyy-MM-dd")}</span>
        <#else>
            <a href="/fltinr/index.jhtml?lawId=${transLaw.lawId!}" title="" target="_blank">${transLaw.title[0..19]}...</a>
            <span style="float:right;color:#AAAAAA">${transLaw.deployTime?string("yyyy-MM-dd")}</span>
        </#if>
    </li>
</ul>
</#list>