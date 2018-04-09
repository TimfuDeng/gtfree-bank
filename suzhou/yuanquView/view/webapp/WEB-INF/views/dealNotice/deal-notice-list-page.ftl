<form class="navbar-form" id="frmSearch">
<#list dealNotices.items as notice>
    <ul>
        <li>
            <#if notice.noticeTitle?length lt 45>
                <a href="/cjggnrnew/index.jhtml?noticeId=${notice.noticeId!}" title="" target="_blank">${notice.noticeTitle}</a>
                <span style="float:right;color:#AAAAAA">${notice.deployTime?string("yyyy-MM-dd")}</span>
            <#else>
                <a href="/cjggnrnew/index.jhtml?noticeId=${notice.noticeId!}" title="" target="_blank">${notice.noticeTitle[0..44]}...</a>
                <span style="float:right;color:#AAAAAA">${notice.deployTime?string("yyyy-MM-dd")}</span>
            </#if>
        </li>
    </ul>
</#list>
</form>
<div class="pageNav">
<@PageHtml pageobj=dealNotices formId="frmSearch" />
</div>