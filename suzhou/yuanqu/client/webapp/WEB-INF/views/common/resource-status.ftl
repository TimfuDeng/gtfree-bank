<@TopOffer resourceObj=resource maxOfferObj=maxOffer!/>
<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#if resource.resourceStatus==1>
    <span class="label label-danger">正在竞价</span>
    <#elseif resource.resourceStatus==10 >
    <span class="label label-danger">正在挂牌</span>
    <#elseif resource.resourceStatus==20 >
    <span class="label label-success">正在公告</span>
    <#elseif resource.resourceStatus==30>
    <!--成交审核过-->
        <#if resource.resourceVerifyId??>
            <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
        <!--成交审核未通过-->
            <#if resouerceVerify.verifyStatus==0>
            <span class="label label-over ">已结束</span>
            <#elseif resouerceVerify.verifyStatus==1>
            <span class="label label-over ">已成交</span>
            </#if>
        <#else>
            <span class="label label-over ">已成交</span>
        </#if>
    <#elseif resource.resourceStatus==31>
    <span class="label label-over ">&nbsp;已流拍&nbsp;</span>
    </#if>
    <#if isTopOffer?? && isTopOffer && (resource.resourceStatus==1 || resource.resourceStatus==10)>
    <br/><span class="label label-danger" style="white-space: normal;">正在竞拍公租房</span>
    </#if>
<#elseif resource.resourceEditStatus==3 >
<span class="label label-over ">&nbsp;已中止&nbsp;</span>
<#elseif resource.resourceEditStatus==4 >
<span class="label label-over ">&nbsp;已终止&nbsp;</span>
</#if>