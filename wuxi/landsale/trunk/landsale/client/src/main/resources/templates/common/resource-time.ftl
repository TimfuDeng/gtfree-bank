<#--地块发布或者结束-->
<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#--公告期-->
    <#if resource.resourceStatus==20>
        <p>距挂牌开始</p>
        <span class="span_2" value="${resource.gpBeginTime?long}" id="${resource.resourceId}"></span>
    <#--挂牌期-->
    <#elseif resource.resourceStatus==10>
        <p>距挂牌结束</p>
        <span class="span_2" value="${resource.gpEndTime?long}" id="${resource.resourceId}"></span>
    <#--成交或者流拍-->
    <#elseif resource.resourceStatus==30 || resource.resourceStatus==31>
        <p>结&nbsp;&nbsp;&nbsp;束</p>
        <span class="span_2">
        ${(resource.overTime?datetime)!}
        </span>
    </#if>
<#else>
    <p>距开始</p>
    <span class="span_2">--</span>
</#if>

<#--<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
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
        ${(resource.overTime?datetime)!}
    </span>
    </#if>
<#else>
    <span>距开始</span>
    <span class="value">
         <span>--</span>
     </span>
</#if>-->
