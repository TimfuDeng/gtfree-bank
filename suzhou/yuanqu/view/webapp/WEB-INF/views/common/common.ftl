<#--
    用来生成分页的html
    pageobj-具有page接口的分页对象
    formId-分页提交参数的表单id
-->
<#macro PageHtml pageobj formId>
<div id="pageNav" class="pageNav">
        <#if pageobj.isFirst()>
            <a href="#">&laquo;</a>
        <#else>
            <a href="javascript:SubmitPageForm('${formId}','1')" title="第一页">&laquo;</a>
        </#if>

        <#if pageobj.index-2 &gt; 0>
            <a href="javascript:SubmitPageForm('${formId}','${pageobj.index-2}')">${pageobj.index-2}</a>
        </#if>

        <#if pageobj.index-1 &gt; 0>
            <a href="javascript:SubmitPageForm('${formId}','${pageobj.index-1}')">${pageobj.index-1}</a>
        </#if>

        <a href="#" class="active">${pageobj.index}</a>

        <#if pageobj.index+1<=pageobj.pageCount>

           <a href="javascript:SubmitPageForm('${formId}','${pageobj.index+1}')">${pageobj.index+1}</a>
        </#if>

        <#if pageobj.index+2<=pageobj.pageCount>
            <a href="javascript:SubmitPageForm('${formId}','${pageobj.index+2}')">${pageobj.index+2}</a>
        </#if>

        <#if pageobj.index==1 && pageobj.index+3<=pageobj.pageCount>
            <a href="javascript:SubmitPageForm('${formId}','${pageobj.index+3}')">${pageobj.index+3}</a></li>
        </#if>

        <#if pageobj.isLast()>
            <a href="#">&raquo;</a>
        <#else>
            <a href="javascript:SubmitPageForm('${formId}','${pageobj.pageCount}')" title="最后一页">&raquo;</a>
        </#if>
</div>
</#macro>
<#--
    根据resourceEditStatus(编辑状态) 生成按钮，
    状态描述如下
    编辑中	0
    申请发布 1
    发布    2
    中止    3
    终止    4
    撤销    5
-->
<#macro resource_edit_status resourceEditStatus resourceStatus>
    <#if resourceEditStatus==0>
        <span class="label label-secondary">正在编辑</span>
    <#elseif resourceEditStatus==1 >
        <span class="label label-primary">申请发布</span>
    <#elseif resourceEditStatus==2 >
        <span class="label label-success">已发布</span>
        <#if resourceStatus==1>
            <span class="label label-success">正在竞价</span>
            <#elseif resourceStatus==2 >
            <span class="label label-success">即将竞价</span>
            <#elseif resourceStatus==10 >
            <span class="label label-success">正在挂牌</span>
        </#if>
    <#elseif resourceEditStatus==3 >
        <span class="label label-danger">中止</span>
    <#elseif resourceEditStatus==4 >
        <span class="label label-danger">终止</span>
    <#elseif resourceEditStatus==9 >
        <span class="label label-success">结束</span>
        <#if resourceStatus==30 >
            <span class="label label-success">已成交</span>
        <#elseif resourceStatus==31 >
            <span class="label label-danger">已流拍</span>
        </#if>
    </#if>


</#macro>

<#macro UploadImage>
$(function() {

});
</#macro>

<#macro OutClassValue obj value classValue defaultValue>
    <#if obj?? >
        <#if obj==value >
        ${classValue}
        <#else>
        ${defaultValue}
        </#if>
    <#else>
    ${defaultValue}
    </#if>
</#macro>

<#macro LandUseConvert selectValue>
    <#if selectValue?? && selectValue=="05" >商服用地</#if>
    <#if selectValue?? && selectValue=="06" >工矿仓储用地</#if>
    <#if selectValue?? && selectValue=="07" >住宅用地</#if>
    <#if selectValue?? && selectValue=="08" >公共管理与公共服务用地</#if>
    <#if selectValue?? && selectValue=="09" >特殊用地</#if>
    <#if selectValue?? && selectValue=="10" >交通运输用地</#if>
    <#if selectValue?? && selectValue=="11" >水域及水利设施用地</#if>
    <#if selectValue?? && selectValue=="99" >其他</#if>
</#macro>

<#macro LandUseListConvert selectValue>
    <#if selectValue?? && selectValue=="05" >商服</#if>
    <#if selectValue?? && selectValue=="06" >工矿仓储</#if>
    <#if selectValue?? && selectValue=="07" >住宅</#if>
    <#if selectValue?? && selectValue=="08" >公共管理与公共服务</#if>
    <#if selectValue?? && selectValue=="09" >特殊</#if>
    <#if selectValue?? && selectValue=="10" >交通运输</#if>
    <#if selectValue?? && selectValue=="11" >水域及水利设施</#if>
    <#if selectValue?? && selectValue=="99" >其他</#if>
</#macro>

<#macro unitText resourceObj>
    <#if resourceObj.offerUnit??>
        <#if resourceObj.offerUnit==1 >
<label class="offer_unit">元/平方米</label>
        <#elseif resourceObj.offerUnit==2 >
<label class="offer_unit">万元/亩</label>
        <#else>
万
        </#if>
    <#else>
万
    </#if>
</#macro>

<#--判断是否达到限价 -->
<#macro TopOffer resourceObj maxOfferObj>
    <#if maxOfferObj?? && maxOfferObj.offerType?? && maxOfferObj.offerType==2>
        <#assign isTopOffer=true/>
    <#elseif resourceObj.maxOffer?? && maxOfferObj?? && resourceObj.maxOffer gt 0>
        <#if maxOfferObj.offerPrice?? && (maxOfferObj.offerPrice == resourceObj.maxOffer || (maxOfferObj.offerPrice+resourceObj.addOffer) gt resourceObj.maxOffer) >
            <#assign isTopOffer=true/>
        </#if>
    </#if>
</#macro>


<#macro Ca autoSign>
<OBJECT id="caOcx" name="caOcx" classid="CLSID:5C457383-C43E-4F0F-BACD-8CAD3CE597C5"
        CODEBASE='${base}/static/ocx/SignerX.dll' style="display: none" VIEWASTEXT width="0" height="0">
    <param name="AutoSign" value="${autoSign!1}">
</OBJECT>
    <#if autoSign==0>
    <script src="${base}/static/js/ca.js"></script>
    </#if>
</#macro>

<#macro userInfo userId>
    <#assign userObj=(UserUtil.getUser(userId)!)!/>
    <#if userObj??>
    ${userObj.viewName!}
    </#if>
</#macro>

