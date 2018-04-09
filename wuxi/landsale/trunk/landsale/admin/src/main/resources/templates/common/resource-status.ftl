<div class="l" id="div_status" style="margin-left:10px">
<#if resource.resourceEditStatus==9 >
    <span class="label label-success">结束</span>
    <#if resource.resourceStatus==30 >
        <#if resource.qualificationType?? && resource.qualificationType == 'POST_TRIAL'>
            <#assign maxTrialType=UserApplyUtil.getMaxApplyTrialType(resource.resourceId)! />
            <#if maxTrialType?? && maxTrialType == 'PASSED_TRIAL'>
                <span class="label label-success"> 已成交 </span>
            <#elseif maxTrialType?? && maxTrialType == 'FAILED_TRIAL'>
                <span class="label label-danger"> 竞得人资格未通过审查 </span>
            <#else>
                <span class="label label-success"> 竞得人资格审查中 </span>
            </#if>
        <#else>
            已成交
        </#if>
    <#elseif resource.resourceStatus==31 >
        <span class="label label-danger">已流拍</span>
    </#if>
<#else>
<@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
</#if>
</div>
<div id="btn_action" class="r">
<#if resource.resourceEditStatus==0>
    <#if AuthorizeUtil.isPermitted("/console/resource/edit","地块编辑")>
    <a href="${base}/console/resource/edit?resourceId=${resource.resourceId!}" class="btn btn-secondary size-S">编辑</a>
    </#if>
    <#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块申请发布")>
        <input class="btn size-S btn-primary" type="button" value="申请发布" onclick="PreRelease('${resource.resourceId!}')">
    </#if>
<#elseif resource.resourceEditStatus==1 >
    <#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块发布")>
    <input class="btn size-S btn-secondary" type="button" value="退回编辑" onclick="BackEdit('${resource.resourceId!}')">
    <input class="btn size-S btn-success" type="button" value="发布" onclick="Release('${resource.resourceId!}')">
    </#if>
<#elseif resource.resourceEditStatus==2 >
    <#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块中止与终止")>
        <#if resource.resourceId??>
            <#--<#assign oneTarget = ResourceUtil.getOneTarget(resource.resourceId)/>-->
            <#if ResourceUtil.getOneTarget(resource.resourceId)??>
                <input class="btn size-S btn-danger" type="button" value="开启一次报价" onclick="startOnePrice('${resource.resourceId!}')">
                <input class="btn size-S btn-danger" type="button" value="中止" onclick="Break('${resource.resourceId!}')">
                <input class="btn size-S btn-danger" type="button" value="终止" onclick="Stop('${resource.resourceId!}')">
            <#else>
                <input class="btn size-S btn-danger" type="button" value="中止" onclick="Break('${resource.resourceId!}')">
                <input class="btn size-S btn-danger" type="button" value="终止" onclick="Stop('${resource.resourceId!}')">
            </#if>
        </#if>


    </#if>
<#elseif resource.resourceEditStatus==3 >
    <#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块退回编辑与重启")>
    <input class="btn size-S btn-secondary" type="button" value="退回编辑" onclick="BackEdit('${resource.resourceId!}')">
    <input class="btn size-S btn-danger" type="button" value="重新启动" onclick="ReStart('${resource.resourceId!}')">
    </#if>
<#elseif resource.resourceEditStatus==4 || resource.resourceEditStatus==9>
    <#-- 终止-->
    <a href="${base}/console/resource-apply/list?resourceId=${resource.resourceId!}" class="btn btn-primary">详情</a>
</#if>
</div>