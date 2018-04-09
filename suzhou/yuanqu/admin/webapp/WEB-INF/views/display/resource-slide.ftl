<!-- 表单部分 -->
<form class="navbar-form" id="frmSearch">
    <table class="table"
           style="table-layout: fixed;width: 100%;border-collapse: collapse;border-spacing: 2px;border-color: gray;padding-top: 0">
        <tbody>
        <#list transList.items as transItem>
        <tr class="active border_style">
            <td colspan="3" style="padding-left:5px">
                <div class="l" style="margin-top: 4px">
                    <#if transItem.ggId??>
                        <#assign crgg=ResourceUtil.getCrgg( transItem.ggId)/>
                        <#if crgg??>
                            <#if crgg.ggNum?length gt 20>
                            ${crgg.ggNum[0..8]}..
                            <#else>
                            ${crgg.ggNum!}
                            </#if>
                        </#if>
                    </#if>
                    <i class="icon-th-large"></i>
                ${transItem.resourceCode!}
                </div>
                <div id="div_status_${transItem.resourceId!}" style="margin-top: 2px ">
                    <div class="l" id="div_status" style="margin-left:10px">
                        <#if transItem.resourceEditStatus==2>
                            <span class="label label-success">已发布</span>
                        <#else>
                            <span class="label label-primary">已结束</span>
                            <#if transItem.resourceStatus==30>
                                <span class="label label-success">已成交</span>
                            </#if>
                            <#if transItem.resourceStatus==31>
                                <span class="label label-danger">已流拍</span>
                            </#if>
                        </#if>

                    </div>


                    <div class="r">
                        <#if transItem.resourceStatus==30>
                            <input type="button" onclick="send('${transItem.resourceId!}')" value="推送到大屏" class="btn size-S btn-success">
                        <#elseif transItem.resourceStatus==31>
                            <input type="button" onclick="deal('${transItem.resourceId!}')" value="推送到大屏" class="btn size-S btn-success">
                        <#elseif transItem.resourceStatus==10>
                            <input type="button" onclick="send('${transItem.resourceId!}')" value="推送到大屏" class="btn size-S btn-success">
                        </#if>
                    </div>

                    <div id="btn_action" class="r">
                    </div>
                </div>
            </td>
        </tr>
        <tr class="order-bd">
            <td colspan="3">
                <a style="float: left">
                    <img style="width:148px;height:90px;border: 1px solid #e9e9e9;" src="${base}/${ImageUtil.url(transItem.resourceId,'402_320')}" onerror="this.src='${base}/static/image/blank.jpg'">
                </a>

                <div class="l" style="padding:0px 0px 0px 10px;">
                <#--<a href="${base}/console/resource/view?resourceId=${transItem.resourceId!}" target="_blank">-->
                    <span>${transItem.resourceLocation!}</span>
                    <br><span>保证金：${transItem.fixedOffer!}&nbsp;&nbsp;万
                        <br><span>面积：${transItem.crArea?string("0.##")}平方米&nbsp;&nbsp;
                    ${(transItem.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                    ${(transItem.crArea*0.0001)?string("0.##")}公顷</span>
                    </span>
<#--   </a>-->
</div>
</td>
</tr>

<tr class="sep-row">
<td colspan="3"></td>
</tr>
</#list>
</tbody>
</table>
</form>
<div>
<@PageHtml pageobj=transList formId="frmSearch" />
</div>