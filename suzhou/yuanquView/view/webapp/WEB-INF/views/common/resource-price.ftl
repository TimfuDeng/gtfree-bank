<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)! />
    <#if  resource.resourceStatus==1 || resource.resourceStatus==10>
    <span>当前价</span>
    <span class="value" style="color: #d91615;">
          <#--  <em class="currency">¥</em>-->

              <em class="price-font-small" >
                  <#if maxOffer??&&maxOffer.offerPrice??>
                     ${maxOffer.offerPrice!}
                <#else>
                  ${resource.beginOffer!}
                  </#if>

              </em>
        万元
         </span>
    <#elseif (maxOffer??&&maxOffer.offerPrice??) && resource.resourceStatus==30 >
    <span>成交价</span>
    <span class="value" style="color: #777;">
          <#--  <em class="currency">¥</em>-->
              <em class="price-font-small">${maxOffer.offerPrice!}</em>
        万元
         </span>
    <#else>
    <span>起始价</span>
    <span class="value" style="color: #5eb95e;">
          <#--  <em class="currency">¥</em>-->
              <em class="price-font-small">${resource.beginOffer!}</em>
        万元
        </span>
    </#if>
<#else>
<span>起始价</span>
<span class="value" style="color: #5eb95e;">
           <#-- <em class="currency">¥</em>-->
               <em class="price-font-small">${resource.beginOffer!}</em>
    万元
        </span>
</#if>