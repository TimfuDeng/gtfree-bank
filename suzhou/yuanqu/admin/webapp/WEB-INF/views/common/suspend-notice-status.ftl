<div id="statusId_${notice.noticeId!}">
    <#if notice.deployStatus==1>
        <input type="button" onclick="revoke('${notice.noticeId!}')" class="btn btn-primary size-S" value="撤回"/>
    <#else>
        <a href="${base}/console/suspendNotice/edit?noticeId=${notice.noticeId!}"  class="btn size-S btn-default" >编辑</a>
        <input class="btn size-S btn-primary" type="button" onclick="deploy('${notice.noticeId!}')"  value="发布"/>
    </#if>
</div>