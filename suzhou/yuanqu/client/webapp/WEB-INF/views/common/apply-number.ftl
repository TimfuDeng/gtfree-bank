<#if transResourceApply?? && !(transResourceApply.applyNumber gt 0)>
<span><span style="color: red;font-size: 16px;">*请选择一个有效的号牌</span><#--您第一次出价，请选择一个有效的号牌--><#--,<span style="color: red">选择号牌即默认报价一次</span>--><#--蓝色表示可用，红色表示当前选择，灰色表示已占用！--></span>
<table class="table" id="numberTable">
    <#assign baseNumberLen = baseNumber?size>
    <#list baseNumber as group>
        <#if group_index lt applyNumberCount>
            <tr>
                <#list baseNumber as item>
                    <#assign newApplyNumber = group_index*baseNumberLen+item>
                    <td>
                        <#if usedApplyNumbers?? && usedApplyNumbers?seq_contains(newApplyNumber)>
                           <#-- <span class="label label-disabled label-number">${newApplyNumber}</span>-->
                            <a class="label label-primary label-number choose-apply-number"  onclick="chooseApplyNumber(this)">${newApplyNumber}</a>
                        <#else >
                            <a class="label label-primary label-number" onclick="chooseApplyNumber(this)">${newApplyNumber}</a>
                        </#if>
                    </td>
                </#list>
            </tr>
        </#if>
    </#list>
</table>
</#if>