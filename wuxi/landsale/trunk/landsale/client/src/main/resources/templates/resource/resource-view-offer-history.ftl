<table width="360" border="0">
    <tr  class="main_jilu">
        <td>轮次</td>
        <#--<td>状态</td>-->
        <td>报价人</td>
        <td>报价</td>
        <td>报价类型</td>
    </tr>
    <#list resourceOffers.items as resourceOffer>

        <tr>
            <td>${resourceOffers.totalCount - (resourceOffers.size * (resourceOffers.index - 1)) - resourceOffer_index}</td>
            <#--<td>-->
                <#--<#if resourceOffer_index == 0 >-->
                    <#--<div class="main_jilu_fang">领先</div>-->
                <#--<#else>-->
                    <#--<span>---</span>-->
                <#--</#if>-->

            <#--</td>-->
            <td>
                <#if userId?? && resourceOffer.userId==userId>
                    <span class="label label-default">我的报价</span>
                <#else>
                    <span class="other-price">其他报价</span>
                </#if>
            </td>
            <#if resourceOffer_index == 0 >
                <td style="color:red">
                    ${resourceOffer.offerPrice}
                    <#if resource.maxOfferChoose.code == 1 && resourceOffer.offerType == -1>
                        <label class="offer_unit" style="margin-bottom: 0">万元（总价）</label>
                    <#else>
                        <label class="offer_unit" style="margin-bottom: 0"><@unitText offerUnit = resource.offerUnit/></label>
                    </#if>
                </td>
            <#else>
                <td>${resourceOffer.offerPrice}<label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></td>
            </#if>
            <td>
                <#if resourceOffer.offerType==1>
                    <span class="label label-primary ">限时竞价报价</span>
                <#elseif resourceOffer.offerType==2>
                    <span class="label label-secondary ">最高限价</span>
                <#elseif resourceOffer.offerType==-1>
                    <#if resource.maxOfferChoose.code==1>
                        <span class="label label-primary ">一次报价</span>
                    <#elseif resource.maxOfferChoose.code==2>
                        <span class="label label-primary ">摇号报价</span>
                    </#if>
                <#else>
                    <span class="label label-primary ">挂牌期报价</span>
                </#if>
            </td>
        </tr>
    </#list>
</table>