<#--判断报名状态 -->
<#if transResourceApply?? && transResourceApply.applyStep=1>
    <tr class="price price-disable">
        <td>&nbsp;&nbsp;</td>
        <td class="value" style="color: #d91615;">
            <#-- 已经报名-->
            <#if resource.bzjBeginTime?long gt .now?long>
            <a class="btn btn-default size-L"  style="width: 236px" >未开始保证金交纳</a>
            <#elseif resource.bzjEndTime?long lt .now?long >
            <a class="btn btn-default size-L"  style="width: 236px" >已结束保证金交纳</a>
            <#else>
            <a class="btn btn-danger size-L"  style="width: 236px" href="${base}/console/apply-bank?id=${resource.resourceId}" >选择竞买方式和银行</a>
            </#if>
        </td>
    </tr>
<#elseif transResourceApply?? && transResourceApply.applyStep=2>
    <tr class="price price-disable">
        <td>&nbsp;&nbsp;</td>
        <td class="value" style="color: #d91615;">
            <#-- 已经报名-->
            <#if resource.bzjBeginTime?long gt .now?long>
            <a class="btn btn-default size-L"  style="width: 236px" >未开始保证金交纳</a>
            <#elseif resource.bzjEndTime?long lt .now?long >
            <a class="btn btn-default size-L"  style="width: 236px" >已结束保证金交纳</a>
            <#else>
            <a class="btn btn-danger size-L"  style="width: 236px" href="${base}/console/apply-bzj?id=${resource.resourceId}" >交纳保证金</a>
            </#if>
        </td>
    </tr>
<#elseif transResourceApply?? && transResourceApply.applyStep=3>

<#elseif transResourceApply?? && transResourceApply.applyStep=4>
    <#-- 保证金已经到位-->
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
                   <span style="height:25px;line-height:25px" class="label label-warning  "> 挂牌截止前1小时停止报价，限时开始时间后进入4分钟限时竞价！</span>
                </td>
            </tr>
        <#else>
            <tr class="price">
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                    <input class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
                           onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px"></div>
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
    <#--最高限价-->
    <#elseif resource.resourceStatus==11>
        <tr class="price">
            <#--判断当前时间是否在竞价期-->
            <#if (resource.xsBeginTime?long)-(.now?long) gt 0>
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                    <span style="height:25px;line-height:25px" class="label label-warning  ">已达到最该限价,等待进入下一步！</span>
                </td>
            <#else>
                <#--&lt;#&ndash;判断最高限价后的 竞价方式 一次报价&ndash;&gt;
                <#if transResource.maxOfferChoose?? && transResource.maxOfferChoose.code==1>
                &lt;#&ndash;一次报价判断当前时间 是否>一次报价开始时间&ndash;&gt;
                    <#if ((.now?long) - endTime!?long) gt 0 >
                        <td colspan="2" class="value" >
                            一次报价开始， 点击跳转！
                        </td>
                    <#else>
                        <td>距一次<br/>报价开始</td>
                        <td class="value" >
                            <span class="time" id="span_${resource.resourceId}" value="${endTime!}"></span>
                        </td>
                    </#if>
                <#else>
                    <td colspan="2" class="value" style="color: #d91615;">
                        竞价已结束，等待公示摇号结果！
                    </td>
                </#if>-->
            </#if>
        </tr>
    </#if>
<#else>
    <tr class="price price-disable">
        <td>&nbsp;&nbsp;</td>
        <td class="value" style="color: #d91615;">
            <#if resource.bmBeginTime?long gt .now?long>
            <a class="btn btn-default size-L"  style="width: 236px" >未开始报名</a>
            <#elseif resource.bmEndTime?long lt .now?long >
            <a class="btn btn-default size-L"  style="width: 236px" >已结束报名</a>
            <#else>
            <a class="btn btn-danger size-L"  style="width: 236px" href="${base}/console/apply?id=${resource.resourceId}">开始竞买报名</a>
            </#if>
        </td>
    </tr>
</#if>



