<tr class="price">
    <#--  地块状态在 竞价 或者 挂牌 或 最高限价  -->
    <#if resource.resourceStatus==1 || resource.resourceStatus==2 || resource.resourceStatus==10 || resource.resourceStatus=11>
        <td width="51">当前价</td>
        <td class="value" style="color: #d91615;">
            <em class="currency" >¥</em>
            <em class="price-font-small" id="priceInfo">
                <#if maxOfferPrice??>
                ${maxOfferPrice.offerPrice}
                <#else>
                ${resource.beginOffer}
                </#if>
            </em>万
            <#if isTopOffer?? && isTopOffer>
                <em style="margin-left: 30px;color: #7a7a7a;font-style: normal">
                公租房<#if resource.publicHouse?? && resource.publicHouse==1 >资金<#else>面积</#if>
                </em>
                <em class="price-font-small" style="margin-left: 10px;" id="priceHouseInfo">
                    <#if maxOffer?? && maxOffer.offerType==2>
                    ${maxOffer.offerPrice}
                    <#else>
                    ${resource.beginHouse}
                    </#if>
                </em>
                <#if resource.publicHouse?? && resource.publicHouse==1 >万<#else>平方米</#if>
            </#if>
        </td>
    <#--  地块状态在 成交  -->
    <#elseif resource.resourceStatus==30 >
        <td width="51">成交价</td>
        <td class="value" style="color: #777;">
             <em class="currency">¥</em>
             <em class="price-font-small" id="priceInfo">${maxOfferPrice.offerPrice}</em>万元
             <#if isTopOffer?? && isTopOffer>
                <#if maxOffer?? && maxOffer.offerType==2>
                    <em style="margin-left: 30px;color: #7a7a7a;font-style: normal">
                        公租房<#if resource.publicHouse?? && resource.publicHouse==1 >资金&nbsp;&nbsp;<em class="currency">¥</em><#else>面积&nbsp;&nbsp;</#if>
                    </em>
                    <em class="price-font-small">${maxOffer.offerPrice}</em><#if resource.publicHouse?? && resource.publicHouse==1 >万元<#else>平方米</#if>
                </#if>
            </#if>
         </td>
    <#--  地块状态在 公告  -->
    <#elseif resource.resourceStatus==20>
        <td width="51">起始价</td>
        <td class="value" style="color: #5eb95e;">
             <em class="currency">¥</em>
             <em class="price-font-small" id="priceInfo">${resource.beginOffer}</em>万
         </td>
    <#else>
        <td width="51">起始价</td>
        <td class="value" style="color: #777;">
            <em class="currency">¥</em>
            <em class="price-font-small" id="priceInfo">${resource.beginOffer}</em>万
        </td>
    </#if>
</tr>

<#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
    <tr>
        <#-- 如果是公告阶段-->
        <#if resource.resourceStatus==20>
            <td>距开始</td>
            <td class="value">
                <span class="time" id="span_${resource.resourceId}" value="${resource.gpBeginTime?long}"></span>
             </td>
        <#-- 如果是挂牌阶段-->
        <#elseif resource.resourceStatus==10>
            <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                <td>距挂牌<br/>截止</td>
                <td class="value" >
                    <span class="time" id="span_${resource.resourceId}" value="${resource.gpEndTime?long}"></span>
                </td>
            <#else>
                <td>距限时<br/>竞价开始</td>
                <td class="value" >
                    <span class="time" id="span_${resource.resourceId}" value="${resource.xsBeginTime?long}"></span>
                </td>
            </#if>
        <#-- 如果是限时竞价阶段-->
        <#elseif resource.resourceStatus==1>
            <td>距结束</td>
            <td class="value" >
                <span class="time" id="span_${resource.resourceId}" value="${endTime!}"></span>
                <div style="float: right;color: #d91615;display: none" id="oneminuteInfo">
                    竞拍即将结束，请竞买人抓紧时间报价！
                </div>
                <div class="progress" style="width:470px;display: none" id="oneminuteProgress">
                    <div class="progress-bar"><span class="sr-only" style="width:100%"></span></div>
                </div>
            </td>
        <#-- 如果是 最高限价-->
        <#elseif resource.resourceStatus==11>
            <#--挂牌期-->
            <#if (resource.gpEndTime?long)-(.now?long) gt 0 >
                <td>距挂牌<br/>截止</td>
                <td class="value" >
                    <span class="time" id="span_${resource.resourceId}" value="${resource.gpEndTime?long}"></span>
                </td>
            <#--挂牌结束 竞价未开始-->
            <#elseif (resource.gpEndTime?long)-(.now?long) lt 0 && (resource.xsBeginTime?long)-(.now?long) gt 0>
                <td>距限时<br/>竞价开始</td>
                <td class="value" >
                    <span class="time" id="span_${resource.resourceId}" value="${resource.xsBeginTime?long}"></span>
                </td>
            <#--限时竞价期-->
            <#else>
                <#--判断最高限价后的 竞价方式 一次报价-->
                <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
                    <#--一次报价判断当前时间 是否>一次报价开始时间-->
                    <#if ((.now?long) - endTime!?long) gt 0 >
                        <td colspan="2" class="value" >
                            一次报价开始， <a href="${base}/resource/decideResourceView?resourceId=${resource.resourceId}">点击跳转！</a>
                        </td>
                    <#else>
                        <td>距一次<br/>报价开始</td>
                        <td class="value" >
                            <span class="time" id="span_${resource.resourceId}" value="${endTime!}"></span>
                        </td>
                    </#if>
                <#else>
                    <td colspan="2" class="value" >
                        竞价已结束，等待公示摇号结果！
                    </td>
                </#if>
                <#--<td>距结束</td>
                <td class="value" >
                    <span class="time" id="span_${resource.resourceId}" value="${endTime!}"></span>
                    <div style="float: right;color: #d91615;display: none" id="oneminuteInfo">
                        竞拍即将结束，请竞买人抓紧时间报价！
                    </div>
                    <div class="progress" style="width:470px;display: none" id="oneminuteProgress">
                        <div class="progress-bar"><span class="sr-only" style="width:100%"></span></div>
                    </div>
                </td>-->
            </#if>
        <#else>
            <td>结束时间</td>
            <td class="value" >
                ${(resource.overTime?datetime)!}
            </td>
        </#if>
    </tr>
</#if>

<#assign bgclass="">

<#--判断是否结束 -->
<#if resource.resourceStatus==30 || resource.resourceStatus==31 || resource.resourceEditStatus==3 || resource.resourceEditStatus==4>
    <#include "offer/view-over.ftl">
<#else>
    <#--判断是否登录 -->
    <#if userId??>
    <#assign applyStatus=0>
    <#if transResourceApply??>
        <#assign bgclass="">
        <#assign applyStatus=transResourceApply.applyStep>
    </#if>
    <#if applyStatus!=3>
        <#assign bgclass="price-disable">
    </#if>
    <tr class="price ${bgclass}">
        <td>
            <p style="margin-bottom:0px">
            出&nbsp;&nbsp;&nbsp;&nbsp;价</p><p style="margin-bottom:0px">
            <#if isTopOffer?? && isTopOffer>
               <#if resource.publicHouse?? && resource.publicHouse==0 >(平方米)</#if>
            <#else>
                (万元)
            </#if>

            </p>
        </td>
        <td class="value" style="color: #d91615;">
            <#if isTopOffer?? && isTopOffer>
                <#if maxOffer?? && maxOffer.offerType==2>
                    <input type="text" class="input-text  size-L" style="width:236px" id="txtoffer" value="${maxOffer.offerPrice+resource.addHouse}">
                <#else>
                    <input type="text" class="input-text  size-L" style="width:236px" id="txtoffer" value="${resource.beginHouse}">
                </#if>
            <#else>
                <#if maxOfferPrice??>
                    <input type="text" class="input-text  size-L" style="width:236px" id="txtoffer" value="${maxOfferPrice.offerPrice!}">
                <#else>
                    <input type="text" class="input-text  size-L" style="width:236px" id="txtoffer" value="${resource.beginOffer!}">
                </#if>
            </#if>
            <a class="btn btn-default size-L" onclick="addOffer()" ><i class="iconfont">&#xf0175;</i></a>
            <a class="btn btn-default size-L" onclick="cutOffer()"><i class="iconfont">&#xf0176;</i></a>
            <div class="r"><#include "common/resource-status.ftl"></div>
        </td>
    </tr>
        <#include "offer/view-offerbtn.ftl">
    <#else>
        <#include "offer/view-nologin.ftl">
    </#if>
</#if>



