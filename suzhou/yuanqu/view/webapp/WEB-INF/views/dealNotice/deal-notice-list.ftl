<#list dealNotices.items as notice>
<ul>
    <li>
        <#if notice.noticeTitle?length lt 20>
            <a href="/cjggnrnew/index.jhtml?noticeId=${notice.noticeId!}" title="" target="_blank">${notice.noticeTitle}</a>
            <span style="float:right;color:#AAAAAA">${notice.deployTime?string("yyyy-MM-dd")}</span>
        <#else>
            <a href="/cjggnrnew/index.jhtml?noticeId=${notice.noticeId!}" title="" target="_blank">${notice.noticeTitle[0..19]}...</a>
            <span style="float:right;color:#AAAAAA">${notice.deployTime?string("yyyy-MM-dd")}</span>
        </#if>
    </li>
</ul>
</#list>