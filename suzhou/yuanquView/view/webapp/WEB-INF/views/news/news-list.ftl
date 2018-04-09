<#list transNewsList.items as news>
<ul>
    <li>
        <#if news.newsTitle?length lt 20>
            <a href="/xwgg/index.jhtml?newsId=${news.newsId!}" title="" target="_blank">${news.newsTitle}</a>
            <span style="float:right;color:#AAAAAA">${news.newsReportTime?string("yyyy-MM-dd")}</span>
        <#else>
            <a href="/xwgg/index.jhtml?newsId=${news.newsId!}" title="" target="_blank">${news.newsTitle[0..19]}...</a>
            <span style="float:right;color:#AAAAAA">${news.newsReportTime?string("yyyy-MM-dd")}</span>
        </#if>
    </li>
</ul>
</#list>