<div id="div_status_offer_${resourceApply.applyId!}" style="margin-top: 2px">
<@userInfo userId=resourceApply.userId />
<#if resourceApply.applyStep==4 >
    <span class="label label-success">已交纳保证金</span>

    <!--成交-->
    <#if  resource.resourceStatus==30>
        <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)!/>
        <#if maxOffer??&&(maxOffer.userId==resourceApply.userId)>
            <!--成交审核过-->
            <#if resource.resourceVerifyId??>
                <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
                <!--成交审核未通过-->
                <#if resouerceVerify.verifyStatus==0 && resourceApply.fixedOfferBack==false>
                    <span class="label label-default">未退还保证金</span>
                <#elseif resouerceVerify.verifyStatus==1>
                    <span class="label label-danger">已转入国库</span>
                </#if>
            <#else>
                <span class="label label-danger">已转入国库</span>
            </#if>
        <#else>
            <#if resourceApply.fixedOfferBack==false>
                <span class="label label-default">未退还保证金</span>
            <#else>
                <span class="label label-success">已退还保证金</span>
            </#if>
        </#if>
    </#if>


    <#--<#if resourceApply.applyStep==4&&resourceApply.fixedOfferBack==true >-->
        <#--<span class="label label-success">已退还保证金</span>-->
    <#--<#else>-->
        <#--<span class="label label-default">未退还保证金</span>-->
    <#--</#if>-->

    <!--流拍-->
    <#if  resource.resourceStatus==31>
        <#if resourceApply.fixedOfferBack==false>
            <span class="label label-default">未退还保证金</span>
        <#else>
            <span class="label label-success">已退还保证金</span>
        </#if>
    </#if>

    <!--中止-->
    <#if  resource.resourceStatus==10>
        <#if resourceApply.fixedOfferBack==false>
            <span class="label label-default">未退还保证金</span>
        <#else>
            <span class="label label-success">已退还保证金</span>
        </#if>
    </#if>


<#else>
    <span class="label label-default">未交纳保证金</span>
</#if>
</div>