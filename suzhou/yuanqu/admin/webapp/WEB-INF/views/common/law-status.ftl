<#if transLaw??>
<div id="statusId_${transLaw.lawId!}">
    <#if transLaw.lawStauts==1>
        <input type="button" onclick="revoke('${transLaw.lawId!}')" class="btn btn-primary size-S" value="撤回"/>
    <#else>
        <a href="${base}/console/transLaw/edit?lawId=${transLaw.lawId!}"  class="btn size-S btn-default" >编辑</a>
        <input class="btn size-S btn-primary" type="button" onclick="deploy('${transLaw.lawId!}')"  value="发布"/>
    </#if>
</div>
</#if>