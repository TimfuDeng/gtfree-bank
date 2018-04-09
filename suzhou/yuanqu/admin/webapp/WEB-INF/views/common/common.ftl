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


<#--
    根据resourceEditStatus(编辑状态) 生成按钮(考虑成交审核状态)，
    状态描述如下
    编辑中	0
    申请发布 1
    发布    2
    中止    3
    终止    4
    撤销    5
-->
<#macro resource_edit_status_verify resourceEditStatus resourceStatus resourceVerifyId>
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
        <!--成交审核过-->
            <#if resourceVerifyId??>
                <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resourceVerifyId)!/>
            <!--成交审核未通过-->
                <#if resouerceVerify??>
                    <#if resouerceVerify.verifyStatus==0>
                        <span class="label label-danger">审核未通过</span>
                    <#else>
                        <span class="label label-success">已成交</span>
                    </#if>

                </#if>
            <#else>
                <span class="label label-success">已成交</span>
            </#if>

        <#elseif resourceStatus==31 >
        <span class="label label-danger">已流拍</span>
        </#if>
    </#if>


</#macro>
<#--
    deployStatus(发布状态) 生成按钮，
    状态描述如下
    撤回	0
    发布 1
-->
<#macro deploy_status deployStatus>
    <#if deployStatus==1>
    <a href="${base}/console/notification/revoke?noteId=${note.noteId!}" onclick="confirm('确认要发布通知？')" class="btn btn-default size-S" >撤回通知</a>
    <#elseif deployStatus==0>
    <a href="${base}/console/notification/deploy?noteId=${note.noteId!}" onclick="confirm('确认要发布通知？')" class="btn btn-default size-S" >发布通知</a>
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
        <option value="SFYD" <#if selectValue?? && selectValue=="SFYD" >selected="selected"</#if>>商服用地</option>
        <option value="GKCCYD" <#if selectValue?? && selectValue=="GKCCYD" >selected="selected"</#if>>工矿仓储用地</option>

        <option value="GY" <#if selectValue?? && selectValue=="GY" >selected="selected"</#if>>工业</option>
        <option value="CC" <#if selectValue?? && selectValue=="CC" >selected="selected"</#if>>仓储</option>
        <option value="KJ" <#if selectValue?? && selectValue=="KJ" >selected="selected"</#if>>科教</option>
        <option value="GYBZCF" <#if selectValue?? && selectValue=="GYBZCF" >selected="selected"</#if>>工业(标准厂房)</option>
        <option value="GYGBZCF" <#if selectValue?? && selectValue=="GYGBZCF" >selected="selected"</#if>>工业(高标准厂房)</option>
        <option value="GYYF" <#if selectValue?? && selectValue=="GYYF" >selected="selected"</#if>>工业(研发)</option>
        <option value="GYWHCY" <#if selectValue?? && selectValue=="GYWHCY" >selected="selected"</#if>>工业(文化创意)</option>
        <option value="GYZBJJ" <#if selectValue?? && selectValue=="GYZBJJ" >selected="selected"</#if>>工业(总部经济)</option>
        <option value="KJYF" <#if selectValue?? && selectValue=="KJYF" >selected="selected"</#if>>科教(研发)</option>
        <option value="KJWHCY" <#if selectValue?? && selectValue=="KJWHCY" >selected="selected"</#if>>科教(文化创意)</option>
        <option value="KJZBJJ" <#if selectValue?? && selectValue=="KJZBJJ" >selected="selected"</#if>>科教(总部经济)</option>

        <option value="ZZYD" <#if selectValue?? && selectValue=="ZZYD" >selected="selected"</#if>>住宅用地</option>
        <option value="QT" <#if selectValue?? && selectValue=="QT" >selected="selected"</#if>>其他用地</option>
    </select>
</span>
</#macro>

<#macro LandUseMuliSelect selectName selectValue selectValueDicts>
    <div  align="left" style="position:absolute;z-index:90;margin-top:-15px">
        <div class="zTreeDemoBackground left">
            <ul class="list">
                <li class="title">
                    <select id="${selectName}" name="${selectName}" type="select" style="width: 250px;height: 31px;" onclick="showMenu(this); return false;" >
                        <#if selectValue??>
                            <option value="" selected="selected">
                                ---请选择---
                            </option>
                        </#if>
                        <#list selectValueDicts as obj>
                            <#if selectValue??&&selectValue==obj.code>
                                <option value="${selectValue!}" selected="selected">
                                ${obj.name!}
                                </option>
                            </#if>
                        </#list>
                    </select>
                </li>
            </ul>
        </div>
        <div id="menuContent" class="menuContent" style="display:none;background-color: #ddd">
            <ul id="treeDemo" class="ztree" ></ul>
        </div>
    </div>
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
    <#assign userObj=(UserUtil.getUser(userId)!)!/>
    <#if userObj??>
    ${userObj.viewName!}
    </#if>
</#macro>
