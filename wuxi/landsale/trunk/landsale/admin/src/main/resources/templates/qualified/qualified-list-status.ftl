<div id="div_status_${resourceApply.applyId!}" style="margin-top: 2px">
<#if transResource.beforeBzjAudit == 1>
    <#if resourceApply.applyStep==2>
        <a   href="javascript:changeSrc('${base}/qualified/verify?applyId=${resourceApply.applyId!}&resourceId=${resourceId}')" class="btn size-S btn-primary">审核</a>
        <a   href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${resourceApply.applyId!}')" class="btn size-S btn-primary">历史记录</a>
    <#elseif resourceApply.applyStep==1>
    <#else>
        <a   href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${resourceApply.applyId!}')" class="btn size-S btn-primary">历史记录</a>
    </#if>
</#if>
    <a  href="javascript:deleteApply('${resourceApply.applyId!}')" class="btn size-S btn-primary">删除</a>
</div>
