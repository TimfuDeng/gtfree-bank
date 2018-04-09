<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#if resource.resourceStatus==20>
    <span>距开始</span>
    <span class="value">
         <span class="time" value="${resource.gpBeginTime?long}" id="${resource.resourceId}"></span>
     </span>
    <#elseif resource.resourceStatus==10>
    <span>距结束</span>
    <span class="value" >
         <span class="time" value="${resource.gpEndTime?long}" id="${resource.resourceId}"></span>
     </span>
    <#elseif resource.resourceStatus==30 || resource.resourceStatus==31>
    <span>结&nbsp;&nbsp;&nbsp;束</span>
    <span class="value" >
        ${resource.overTime?string("yyyy-MM-dd HH:mm:ss")}
    </span>
    </#if>
<#else>
    <span>距开始</span>
    <span class="value">
         <span>--</span>
     </span>
</#if>
