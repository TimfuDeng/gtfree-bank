<#--地块发布或者结束-->
<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#--公告期-->
    <#if resource.resourceStatus==20>
        <p>距挂牌开始：</p>
        <span class="time" value="${resource.gpBeginTime?long}" id="${resource.resourceId}"></span>
    <#--挂牌期-->
    <#elseif resource.resourceStatus==10>
    <#--挂牌期-->
        <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
            <p>距挂牌结束：</p>
            <span class="time" value="${resource.gpEndTime?long}" id="${resource.resourceId}"></span>
        <#--挂牌结束 竞价未开始-->
        <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
            <p>距限时开始：</p>
            <span class="time" value="${resource.xsBeginTime?long}" id="${resource.resourceId}"></span>
        </#if>
    <#--限时竞价期-->
    <#elseif resource.resourceStatus==1>
        <p>等待竞价结束：</p>
        <span class="time">--</span>
    <#--最高限价-->
    <#elseif resource.resourceStatus==11>
    <#--挂牌期-->
        <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
            <p>距挂牌结束：</p>
            <span class="time" value="${resource.gpEndTime?long}" id="${resource.resourceId}"></span>
        <#--挂牌结束 竞价未开始-->
        <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
            <p>距限时开始：</p>
            <span class="time" value="${resource.xsBeginTime?long}" id="${resource.resourceId}"></span>
        <#else>
            <#--判断最高限价后的成交方式-->
            <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
            <div class="big_data3">
                <p>等待一次报价结果：</p>
                <span class="span_2">--</span>
            </div>
            <#elseif resource.maxOfferChoose?? && resource.maxOfferChoose.code==2>
            <div class="big_data3">
                <p>等待摇号结果：</p>
                <span class="span_2">--</span>
            </div>
            </#if>
        </#if>
    <#--成交或者流拍-->
    <#elseif resource.resourceStatus==30 || resource.resourceStatus==31>
        <p>结&nbsp;&nbsp;&nbsp;束：</p>
        <span class="span_2">
            ${(resource.overTime!?datetime)!}
        </span>
    </#if>
<#else>
    <p>距开始：</p>
    <span class="span_2">--</span>
</#if>
