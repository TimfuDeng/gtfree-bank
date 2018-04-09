<#--&lt;#&ndash;判断报名状态 &ndash;&gt;
<#if transResourceApply?? && transResourceApply.applyStep=1>
<tr class="price price-disable">
    <td>&nbsp;&nbsp;</td>
    <td class="value" style="color: #d91615;">
        &lt;#&ndash; 已经报名&ndash;&gt;
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
        &lt;#&ndash; 已经报名&ndash;&gt;
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
    &lt;#&ndash; 保证金已经到位&ndash;&gt;
    <#if resource.resourceStatus==10 || resource.resourceStatus==1>
    &lt;#&ndash; 判断是否挂牌前一个小时&ndash;&gt;
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
            &lt;#&ndash;
            <tr class="price">
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td class="offer-info">
                    <div id="showinfo" style="margin:0px;padding:0px;background-color: #FFFFCC;font-size:16px">
                        <i class="iconfont" style="font-size:18px;color:#dd514c ">&#xf00b7;</i>
                        <#if transResourceApply.limitTimeOffer>
                            您已确认进入限时竞价，请稍候！
                        <#else>
                            是否确认进入限时竞价？选择&nbsp;
                            <input class="btn btn-danger  " type="button" value="是" onclick="$('#myModalLimit').modal();">&nbsp;进入限时竞价！
                        </#if>
                    </div>
                </td>
            </tr>
            &ndash;&gt;
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
</#if>-->


<tr class="price">
    <td>&nbsp;&nbsp;</td>
    <td class="value" style="color: #d91615;">
        <input class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
               onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px"></div>
    </td>
</tr>



