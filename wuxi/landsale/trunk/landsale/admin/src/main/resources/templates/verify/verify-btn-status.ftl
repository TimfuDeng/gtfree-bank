<#assign transResourceVerify=ResourceUtil.getTransResourceVerify(transResource.resourceId)!>
<#if transResourceVerify.verifyStatus==1>
    <a href="${base}/resource/cjqrs?resourceId=${transResource.resourceId}" class="btn btn-success size-S">成交确认书</a>
<#elseif transResourceVerify.verifyStatus==2>
    <span class="label label-danger" style="margin-top: 15px">未通过审核</span>
</#if>
<div style="margin-top:15px">
    <a href="javascript:changeSrc('${base}/verify/detail', {resourceId: '${transResource.resourceId}'})" class="btn btn-primary">审核</a>
</div>


