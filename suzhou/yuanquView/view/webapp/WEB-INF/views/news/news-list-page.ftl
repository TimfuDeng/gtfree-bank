<form class="navbar-form" id="frmSearch">
<#list transNewsList.items as news>
    <ul>
        <li>
            <#if news.newsTitle?length lt 30>
                <a href="/xwgg/index.jhtml?newsId=${news.newsId!}" title="" target="_blank">${news.newsTitle}</a>
                <span style="float:right;color:#AAAAAA">${news.newsReportTime?string("yyyy-MM-dd")}</span>
            <#else>
                <a href="/xwgg/index.jhtml?newsId=${news.newsId!}" title="" target="_blank">${news.newsTitle[0..29]}...</a>
                <span style="float:right;color:#AAAAAA">${news.newsReportTime?string("yyyy-MM-dd")}</span>
            </#if>
        </li>
    </ul>
</#list>
</form>
<div class="pageNav">
<@PageHtml pageobj=transNewsList formId="frmSearch" />
</div>