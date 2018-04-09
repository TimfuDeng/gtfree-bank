
<div class="l" id="div_status" style="margin-left:10px">
    <#if resource.resourceEditStatus==3 && oneTarget?? && oneTarget.isStop?? && oneTarget.isStop==1>
        <span class="label label-success">已发布</span>
    <#elseif resource.resourceEditStatus==3 >
        <span class="label label-primary">申请发布</span>
     <#elseif resource.resourceStatus==30>
        <span class="label label-danger">已成交</span>
    </#if>
</div>
<div id="btn_action" class="r">
<#if resource.resourceEditStatus==3 && oneTarget?? && oneTarget.isStop?? && oneTarget.isStop==1>
   <#-- <#if AuthorizeUtil.isPermitted("/oneprice/resource/status/**","已发布")>-->
        <input class="btn size-S btn-danger" type="button" value="撤回" onclick="Break('${resource.resourceId!}')">
    <#--</#if>-->
<#elseif resource.resourceEditStatus==3 >
   <#-- <#if AuthorizeUtil.isPermitted("/oneprice/resource/status/**","正在发布")>-->
        <input class="btn size-S btn-primary" type="button" value="发布" onclick="PreRelease('${resource.resourceId!}')">
    <#--</#if>-->
</#if>
</div>