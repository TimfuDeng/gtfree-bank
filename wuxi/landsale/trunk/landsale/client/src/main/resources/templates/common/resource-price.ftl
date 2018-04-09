<#--地块发布或者结束-->
<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#--竞价期或挂牌期-->
    <#if resource.resourceStatus==1 || resource.resourceStatus==10>
        <p>当前价</p>
        <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)! />
        <#--<#if maxOffer.offerType?? && maxOffer.offerType!=2>
            <em class="currency">¥</em>
        </#if>-->
        <span style="color: #d91615;">
            <#if maxOffer.offerType??>
            ${maxOffer.offerPrice}
            <#else>
            ${resource.beginOffer}
            </#if>
            <#if maxOffer.offerType?? && maxOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==0 >平方米<#else>万</#if>
        </span>
    <#--已成交-->
    <#elseif resource.resourceStatus==30 >
        <p>成交价</p>
        <span style="color: #777;">
            <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId) />
           <#-- <#if maxOffer.offerType!=2>
                <em class="currency">¥</em>
            </#if>-->
            ${maxOffer.offerPrice}
            <#if maxOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==0 >平方米<#else>万</#if>
         </span>
    <#--公告期-->
    <#elseif resource.resourceStatus==20>
        <p>起始价</p>
        <span style="color: #5eb95e;">
             <#--<em class="currency">¥</em>-->
             ${resource.beginOffer}<label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label>
         </span>
    <#--流拍-->
    <#elseif resource.resourceStatus==31>
        <p>起始价</p>
        <span style="color: #777;">
             ${resource.beginOffer}万
         </span>
    </#if>
<#else>
    <p>起始价</p>
    <span style="color: #777;">
        <#--<em class="currency">¥</em>-->
        ${resource.beginOffer}万
    </span>
</#if>
<#--
<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#if resource.resourceStatus==1 || resource.resourceStatus==10>
    <span>当前价</span>
    <span class="value" style="color: #d91615;">
        <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)! />
        <#if maxOffer.offerType?? && maxOffer.offerType!=2>
            <em class="currency">¥</em>
        </#if>
        <em class="price-font-small">
            <#if maxOffer.offerType??>
            ${maxOffer.offerPrice}
            <#else>
            ${resource.beginOffer}
            </#if>
            <#if maxOffer.offerType?? && maxOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==0 >平方米<#else>万</#if>
        </em>
     </span>
    <#elseif resource.resourceStatus==30 >
    <span>成交价</span>
    <span class="value" style="color: #777;">
        <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId) />
        <#if maxOffer.offerType!=2>
        <em class="currency">¥</em>
        </#if>
         <em class="price-font-small">${maxOffer.offerPrice}
        <#if maxOffer.offerType==2 && resource.publicHouse?? && resource.publicHouse==0 >平方米<#else>万</#if>
         </em>
     </span>
    <#elseif resource.resourceStatus==20>
    <span>起始价</span>
    <span class="value" style="color: #5eb95e;">
         <em class="currency">¥</em>
         <em class="price-font-small">${resource.beginOffer}<@unitText resourceObj=resource/> </em>
     </span>
    <#elseif resource.resourceStatus==31>
    <span>起始价</span>
    <span class="value" style="color: #777;">
         <em class="currency">¥</em>
         <em class="price-font-small">${resource.beginOffer}万</em>
     </span>
    </#if>
<#else>
    <span>起始价</span>
    <span class="value" style="color: #777;">
        <em class="currency">¥</em>
        <em class="price-font-small">${resource.beginOffer}万</em>
    </span>
</#if>-->
