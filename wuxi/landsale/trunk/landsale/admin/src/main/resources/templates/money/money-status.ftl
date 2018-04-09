<div id="div_status_money_${resourceApply.applyId!}" style="margin-top: 2px">
<#if  resource.resourceStatus==30>
    <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)!/>
    <#if maxOffer??&&(maxOffer.userId==resourceApply.userId)>
        <!--成交审核过-->
        <#if resource.resourceVerifyId??>
            <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
            <!--成交审核未通过-->
            <#if resouerceVerify.verifyStatus==0>
                <#if resourceApply.fixedOfferBack==false>
                    <input class="btn btn-primary size-S" type="button" value="退还保证金" onclick="backOffer('${resourceApply.applyId!}')">
                <#else>
                    <span class="label label-danger">已退</span>
                </#if>
            <#else>
                <span class="label label-danger">已转</span>
            </#if>
        <#else>
            <span class="label label-danger">已转</span>
        </#if>
    <#else>
        <#if  resourceApply.applyStep==4&&resourceApply.fixedOfferBack==false>
            <input class="btn btn-primary size-S" type="button" value="退还保证金" onclick="backOffer('${resourceApply.applyId!}')">
        <#elseif resourceApply.applyStep==4&&resourceApply.fixedOfferBack==true>
            <span class="label label-danger">已退</span>
        </#if>
    </#if>


</#if>

<#if resource.resourceStatus==31 || resource.resourceStatus==10>
    <#if resourceApply.applyStep==4>
        <#if resourceApply.fixedOfferBack==false>
            <input class="btn btn-primary size-S" type="button" value="退还保证金" onclick="backOffer('${resourceApply.applyId!}')">
        <#else>
            <span class="label label-danger">已退</span>
        </#if>
    </#if>
</#if>


</div>


