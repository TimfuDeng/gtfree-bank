<div id="div_status_${resourceApply.applyId!}" style="margin-top: 2px">
<#if resourceApply.applyStep==2>
    <a   href="javascript:changeSrc('${base}/qualified/verify?applyId=${resourceApply.applyId!}&&resourceId=${resourceId}')" class="btn size-S btn-primary">审核</a>
<#--<input class="btn size-S btn-secondary" type="button" value="退回" onclick="BackEdit('${resourceApply.applyId!}')">-->
    <a   href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${resourceApply.applyId!}')" class="btn size-S btn-primary">历史记录</a>
<#elseif resourceApply.applyStep==1&&resourceApply.qualifiedId??>
    <a   href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${resourceApply.applyId!}')" class="btn size-S btn-primary">历史记录</a>
<#elseif resourceApply.applyStep==3||resourceApply.applyStep==4>
    <a   href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${resourceApply.applyId!}')" class="btn size-S btn-primary">历史记录</a>
<#--<input class="btn size-S btn-secondary" type="button" value="退回" onclick="BackEdit('${resourceApply.applyId!}')">-->
<#--<a   href="${base}/console/qualified/backEdit?applyId=${resourceApply.applyId!}&&resourceId=${resourceId}" class="btn size-S btn-secondary">强制退回</a>-->
<#elseif resourceApply.applyStep==5>
    <a   href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${resourceApply.applyId!}')" class="btn size-S btn-primary">历史记录</a>
<#--<input class="btn size-S btn-secondary" type="button" value="退回" onclick="BackEdit('${resourceApply.applyId!}')">-->
</#if>
</div>