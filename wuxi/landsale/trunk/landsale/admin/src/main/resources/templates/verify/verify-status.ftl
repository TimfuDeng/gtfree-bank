<#assign transResourceVerify=ResourceUtil.getTransResourceVerify(transResource.resourceId)!>
<#if transResourceVerify??>
    <#if transResourceVerify.verifyStatus==1>
        <span class="label label-success">审核通过</span>
        <a href="javascript:changeSrc('${base}/verify/verifyHistory?resourceId=${transResource.resourceId!}')" class="btn size-S btn-primary">历史记录</a>
    <#elseif transResourceVerify.verifyStatus==2>
        <span class="label label-danger">未通过审核</span>
        <a href="javascript:changeSrc('${base}/verify/verifyHistory?resourceId=${transResource.resourceId!}')" class="btn size-S btn-primary">历史记录</a>
    <#else>
    <span class="label label-secondary">未审核</span>
    </#if>
<#else>
    <span class="label label-secondary">未审核</span>
</#if>

