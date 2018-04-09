<div class="l" id="div_status" style="margin-left:10px">
    <#if resource.resourceVerifyId??>
        <@resource_edit_status_verify resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus resourceVerifyId=resource.resourceVerifyId/>
    <#else>
        <@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
    </#if>
</div>
<div id="btn_action" class="r">
<#if resource.resourceEditStatus==0>
    <#--<#if AuthorizeUtil.isPermitted("/console/resource/edit","地块编辑")>-->
    <a href="javascript:changeSrc('${base}/resource/edit?resourceId=${resource.resourceId!}')" class="btn btn-secondary size-S">编辑</a>
    <#--</#if>-->
    <#--<#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块申请发布")>-->
    <input class="btn size-S btn-primary" type="button" value="申请发布" onclick="PreRelease('${resource.resourceId!}')">
    <#--</#if>-->
<#elseif resource.resourceEditStatus==1 >
    <#--<#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块发布")>-->
    <input class="btn size-S btn-secondary" type="button" value="退回编辑" onclick="BackEdit('${resource.resourceId!}')">
    <input class="btn size-S btn-success" type="button" value="发布" onclick="Release('${resource.resourceId!}')">
    <#--</#if>-->
<#elseif resource.resourceEditStatus==2 >
    <#--<#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块中止与终止")>-->
    <input class="btn size-S btn-danger" type="button" value="中止" onclick="Break('${resource.resourceId!}')">
    <input class="btn size-S btn-danger" type="button" value="终止" onclick="Stop('${resource.resourceId!}')">
    <#--</#if>-->
<#elseif resource.resourceEditStatus==3 >
    <#--<#if AuthorizeUtil.isPermitted("/console/resource/status/change.f","地块退回编辑与重启")>-->
    <#if resource.suspendNoticeId??>
        <a href="javascript:changeSrc('${base}/suspendNotice/detail', {noticeId: '${resource.suspendNoticeId}'})" class="btn size-S btn-primary">查看中止公告</a>
    <#else>
        <a href="javascript:changeSrc('${base}/suspendNotice/addByResource', {resourceId: '${resource.resourceId}'})" class="btn size-S btn-primary">新增中止公告</a>
    </#if>
    <input class="btn size-S btn-secondary" type="button" value="退回编辑" onclick="BackEdit('${resource.resourceId!}')">
    <input class="btn size-S btn-danger" type="button" value="重新启动" onclick="ReStart('${resource.resourceId!}')">
    <#--</#if>-->
<#elseif resource.resourceEditStatus==4 || resource.resourceEditStatus==9>
    <#-- 终止-->
    <#if resource.resourceStatus==30>
    <#if resource.dealNoticeId??>
        <a href="javascript:changeSrc('${base}/dealNotice/detail?noticeId=${resource.dealNoticeId!}')" class="btn btn-primary">查看成交公示</a>
    <#else>
        <a href="javascript:changeSrc('${base}/dealNotice/edit',{resourceCode: '${resource.resourceCode}'})" class="btn btn-primary">新增成交公示</a>
    </#if>
    </#if>
    <a href="${base}/resource-apply/list?resourceId=${resource.resourceId!}" class="btn btn-primary">详情</a>
</#if>
</div>
