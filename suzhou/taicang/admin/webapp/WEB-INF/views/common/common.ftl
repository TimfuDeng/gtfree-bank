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

<#macro SelectHtml SelectName  SelectObjects  Width SelectValue onChange="">
<#if Width gt 0>
<span class="select-box" style="width: ${Width}px;">
<#else>
<span class="select-box">
</#if>
    <select class="select"  id="${SelectName}" name="${SelectName}" <#if onChange!=""> onchange="${onChange}()"</#if> >
       <#list SelectObjects as obj>
           <#if SelectValue??>
               <option value="${obj[0]}" <#if SelectValue==obj[0]>  selected="selected" </#if>>${obj[1]}</option>
           </#if>
       </#list>
    </select>
</span>
</#macro>

<#macro LandUseSelect selectName selectValue>
<span class="select-box">
    <select name="${selectName}" class="select">
        <option value="SFYD" <#if selectValue?? && selectValue=="商服用地" >selected="selected"</#if>>商服用地</option>
        <option value="GKCCYD" <#if selectValue?? && selectValue=="工矿仓储用地" >selected="selected"</#if>>工矿仓储用地</option>
        <option value="GYYD" <#if selectValue?? && selectValue=="工业用地" >selected="selected"</#if>>工业用地</option>
        <option value="CCYD" <#if selectValue?? && selectValue=="仓储用地" >selected="selected"</#if>>仓储用地</option>
        <option value="ZZYD" <#if selectValue?? && selectValue=="住宅用地" >selected="selected"</#if>>住宅用地</option>
        <option value="SZYD" <#if selectValue?? && selectValue=="商住用地" >selected="selected"</#if>>商住用地</option>
        <option value="QT" <#if selectValue?? && selectValue=="其他用地" >selected="selected"</#if>>其他用地</option>
    </select>
</span>
</#macro>

<#macro unitText value>
    <#if value?? && value==0 >万元（总价）</#if>
    <#if value?? && value==1 >元/平方米（单价）</#if>
    <#if value?? && value==2 >万元/亩（单价）</#if>
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
    <#assign userObj=UserUtil.getUser(userId)!/>
    <#if userObj??>
        ${userObj.viewName!}
    </#if>
</#macro>
