<#if transResource.resourceVerifyId??>
    <#assign transResourceVerify=ResourceUtil.getTransResourceVerify(transResource.resourceId)!>
    <#if transResourceVerify??>
        <#if transResourceVerify.verifyStatus==1>
        <span class="label label-success">审核通过</span>
        <#elseif transResourceVerify.verifyStatus==0>
        <span class="label label-danger">未通过审核</span>
        </#if>
    </#if>
<#else>
<span class="label label-secondary">未审核</span>
</#if>