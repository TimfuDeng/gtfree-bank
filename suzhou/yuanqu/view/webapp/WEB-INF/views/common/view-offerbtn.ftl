<#--判断报名状态 -->
<#if transResourceApply?? && transResourceApply.applyStep=3>
    <#if resource.resourceStatus==10 || resource.resourceStatus==1>
    <#-- 判断是否挂牌前一个小时-->
        <#if (((resource.gpEndTime?long)-(.now?long)) lt 1000*60*60) && (((resource.gpEndTime?long)-(.now?long)) gt 0)>
            <tr class="price">
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                    <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 236px">
                </td>
            </tr>
            <tr class="price">
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                   <span style="height:25px;line-height:25px" class="label label-warning  "> 挂牌截止前1小时停止报价，挂牌截止时间后进入4分钟限时竞价！</span>
                </td>
            </tr>
        <#else>
            <tr class="price">
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                    <#if transResourceApply.applyNumber==0>
                        <input class="btn btn-primary size-L" type="button" value="领取号牌并报价" style="width: 236px;float: left"
                               onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px">
                        <#else >
                            <input class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
                                   onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px">
                    </#if>
                </div>
                </td>
            </tr>
        </#if>
    <#elseif resource.resourceStatus==20>
    <tr class="price">
        <td>&nbsp;&nbsp;</td>
        <td class="value" style="color: #d91615;">
            <input class="btn btn-default size-L" type="button" value="未开始挂牌" style="width: 236px">
        </td>
    </tr>
    </#if>
</#if>



