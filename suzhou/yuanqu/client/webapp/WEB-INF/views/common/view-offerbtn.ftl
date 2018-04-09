<#--判断报名状态 -->
<#if transResourceApply?? && transResourceApply.applyStep=1>
<tr class="price price-disable">
    <td>&nbsp;&nbsp;</td>
    <td class="value" style="color: #d91615;">
        <#-- 已经报名-->
        <#--
         /**判断地块是土地储备中心或者国土环保局
          *如地块行政区编码是国土环保局则默认地块流程:公告期内进行资格审核，否则在挂牌期审核
          *@annotation
          *@author liushaoshuai【liushaoshuai@gtmap.cn】
          *@date 2017/6/7 9:12
          *@param
          *@return
          */
          -->
        <#if resource.regionCode="320503001">
            <#if ggBeginTime?long gt .now?long>
                <a class="btn btn-default size-L"  style="width: 236px" >未开始申请竞买资格</a>
            <#elseif ggEndTime?long lt .now?long >
                <a class="btn btn-default size-L"  style="width: 236px" >已结束申请竞买资格</a>
            <#elseif transBuyQualified?? && transBuyQualified.qualifiedStatus=0>
                <a class="btn btn-default size-L" href="javascript:void(0)" onclick="checkResourceApplyFail('${base}/console/apply-fail?id=${transResourceApply.applyId}');"  type="button" style="width: 236px">审核失败并查看原因</a>
            <#else>
                <a class="btn btn-danger size-L"  style="width: 236px"  href="javascript:void(0)" onclick="return checkResourceApplyBank('${base}/console/apply-bank?id=${resource.resourceId}');">选择竞买方式和币种</a>
            </#if>
        <#else>
            <#if resource.bzjBeginTime?long gt .now?long>
                <a class="btn btn-default size-L"  style="width: 236px" >未开始保证金交纳</a>
            <#elseif resource.bzjEndTime?long lt .now?long >
                <a class="btn btn-default size-L"  style="width: 236px" >已结束保证金交纳</a>
            <#elseif transBuyQualified?? && transBuyQualified.qualifiedStatus=0>
                <a class="btn btn-default size-L" href="javascript:void(0)" onclick="checkResourceApplyFail('${base}/console/apply-fail?id=${transResourceApply.applyId}');"  type="button" style="width: 236px">审核失败并查看原因</a>
            <#else>
                <a class="btn btn-danger size-L"  style="width: 236px"  href="javascript:void(0)" onclick="return checkResourceApplyBank('${base}/console/apply-bank?id=${resource.resourceId}');">选择竞买方式和币种</a>
            </#if>

        </#if>
    </td>
</tr>
<#elseif transResourceApply?? && transResourceApply.applyStep=2>
<tr class="price price-disable">
    <td>&nbsp;&nbsp;</td>
    <td class="value" style="color: #d91615;">
    <#--
    /**判断地块是土地储备中心或者国土环保局
     *如地块行政区编码是国土环保局则默认地块流程:公告期内进行资格审核，否则在挂牌期审核
     *@annotation
     *@author liushaoshuai【liushaoshuai@gtmap.cn】
     *@date 2017/6/7 9:12
     *@param
     *@return
     */
     -->
    <#if resource.regionCode="320503001">
    <#-- 已经报名，正在资格审核中-->
        <#if ggBeginTime?long gt .now?long>
            <a class="btn btn-default size-L"  style="width: 236px" >未开始申请竞买资格</a>
        <#elseif ggEndTime?long lt .now?long >
            <a class="btn btn-default size-L"  style="width: 236px" >已结束申请竞买资格</a>
        <#else>
            <a class="btn btn-default size-L"  style="width: 236px"  >竞买资格审核</a>

            <span style="height:50px;line-height:25px" class="label label-warning  "> 请带好挂牌文件要求的相关资料，于公告期内前往苏州工业园区国土环保局进行资格审查，<br/>审查通过后方可网上报名，咨询电话：0512-66680873！</span>

        </#if>
    <#else>
        <input class="btn btn-default size-L" type="button" value="申购资格审核中..." style="width: 236px">
    </#if>

    </td>
</tr>
<#elseif transResourceApply?? && transResourceApply.applyStep=3>
<tr class="price price-disable">
    <td>&nbsp;&nbsp;</td>
    <td class="value" style="color: #d91615;">
        <#-- 已经报名-->
        <#--
        /**判断地块是土地储备中心或者国土环保局
        *如地块行政区编码是国土环保局则默认地块流程:公告期内进行资格审核，否则在挂牌期审核
        *@annotation
        *@author liushaoshuai【liushaoshuai@gtmap.cn】
        *@date 2017/6/7 9:12
        *@param
        *@return
        */
        -->
        <#if resource.regionCode="320503001">
            <#-- 已经报名，通过资格审核-->
                <#if resource.bzjBeginTime?long gt .now?long>
                    <a class="btn btn-default size-L"  style="width: 256px" >申请竞买资格通过，等待报名<#--未开始保证金交纳--></a>
                <#elseif resource.bzjEndTime?long lt .now?long >
                    <a class="btn btn-default size-L"  style="width: 256px" >申请竞买资格通过，报名已结束</a>
                <#elseif transBuyQualified?? && transBuyQualified.qualifiedStatus=2>
                    <a class="btn btn-default size-L"   href="javascript:void(0)" onclick="checkResourceForceCancle('${base}/console/force-cancle?id=${transResourceApply.applyId}');" type="button" style="width: 236px">被强制退回并查看原因</a>
                <#else>
                    <a class="btn btn-danger size-L"  style="width: 236px" href="javascript:void(0)" onclick="checkResourceApplyBzj('${base}/console/apply-bzj?id=${resource.resourceId}');"  >报名</a>
                <#--<a class="btn btn-danger size-L"  style="width: 236px" href="javascript:void(0)" onclick="checkResourceApplyBzj('${base}/console/apply-bzj?id=${resource.resourceId}');"  >交纳保证金</a>-->
                </#if>
        <#else>
                <#if resource.bzjBeginTime?long gt .now?long>
                    <a class="btn btn-default size-L"  style="width: 236px" >未开始保证金交纳</a>
                <#elseif resource.bzjEndTime?long lt .now?long >
                    <a class="btn btn-default size-L"  style="width: 236px" >已结束保证金交纳</a>
                <#elseif transBuyQualified?? && transBuyQualified.qualifiedStatus=2>
                    <a class="btn btn-default size-L"   href="javascript:void(0)" onclick="checkResourceForceCancle('${base}/console/force-cancle?id=${transResourceApply.applyId}');" type="button" style="width: 236px">被强制退回并查看原因</a>
                <#else>
                    <a class="btn btn-danger size-L"  style="width: 236px" href="javascript:void(0)" onclick="checkResourceApplyBzj('${base}/console/apply-bzj?id=${resource.resourceId}');"  >交纳保证金</a>
                </#if>
        </#if>

    </td>
</tr>

<#elseif transResourceApply?? && transResourceApply.applyStep=4>
    <#-- 保证金已经到位-->
    <#if resource.resourceStatus==10 || resource.resourceStatus==1>
    <#-- 判断是否挂牌前一个小时-->
        <#if ((resource.gpEndTime?long)-(.now?long) lt 1000*60*60) && ((resource.xsBeginTime?long)-(.now?long) gt 0)>
            <tr class="price">
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                    <input class="btn btn-default size-L" type="button" value="停止报价" style="width: 236px">
                </td>
            </tr>
            <tr class="price">
                <td>&nbsp;&nbsp;</td>
                <td class="value" style="color: #d91615;">
                   <span style="height:25px;line-height:25px" class="label label-warning  "> 挂牌截止前1小时停止报价，请等待进入限时报价！</span>
                </td>
            </tr>
            <tr class="price">
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td class="offer-info">
                   <#-- <div id="showinfo" style="margin:0px;padding:0px;background-color: #FFFFCC;font-size:16px">
                        <i class="iconfont" style="font-size:18px;color:#dd514c ">&#xf00b7;</i>
                        稍后将进入限时竞价，请稍候！
                    </div>-->
                </td>
            </tr>
        <#--去掉号牌判断(transResourceApply.applyNumber lt 1)||-->
        <#elseif (((resource.xsBeginTime?long)-(.now?long)) lt 0) && (transResourceApply.limitTimeOffer==false)>
        <tr class="price">
            <td>&nbsp;&nbsp;</td>
            <td class="value" style="color: #d91615;">
                <input class="btn btn-default size-L" type="button" value="禁止报价" style="width: 236px">
            </td>
        </tr>
        <tr class="price">
            <td>&nbsp;&nbsp;</td>
            <td class="value" style="color: #d91615;">
               <#-- <span style="height:25px;line-height:25px" class="label label-warning  "> 您未进行过有效报价或未在规定的时间内同意进入限时竞价！</span>-->
            </td>
        </tr>
        <#else>
        <tr class="price">
            <td>&nbsp;&nbsp;</td>
            <td class="value" style="color: #d91615;">
                <#--<input class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
                       onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px">-->
                <#if transResourceApply.applyNumber==0  && (((resource.yxEndTime?long)-(.now?long)) lt 0) >
                <tr class="price">
                    <td>&nbsp;&nbsp;</td>
                    <td class="value" style="color: #d91615;">
                        <input class="btn btn-default size-L" type="button" value="禁止报价" style="width: 236px"><span style="height:25px;line-height:25px;margin-left: 10px;" class="label label-warning  "> 您未进行过有效报价！</span>
                    </td>
                </tr>

                <#elseif (transResourceApply.applyNumber==0) >
                <span id="getNumberOfferBtn">
                    <input  class="btn btn-primary size-L" type="button" value="领取号牌" style="width: 236px;float: left"
                            onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px">
                    <#--<span style="color: red;font-size:20px">*</span>
                    <span style="font-size:14px;padding-left:5px">首次报价后领取号牌，</span><br>
                 <span style="font-size:14px;padding-left:18px">没有号牌的竞买人无法参与竞价！</span>-->

                <#else >
                    <input id="getNumberBtn" class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
                           onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:220px">
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

<#else>
<tr class="price price-disable">
    <td>&nbsp;&nbsp;</td>
    <td class="value" style="color: #d91615;">
    <#--
    /**判断地块是土地储备中心或者国土环保局
    *如地块行政区编码是国土环保局则默认地块流程:公告期内进行资格审核，否则在挂牌期审核
    *@annotation
    *@author liushaoshuai【liushaoshuai@gtmap.cn】
    *@date 2017/6/7 9:12
    *@param
    *@return
    */
    -->
    <#if resource.regionCode="320503001">
        <#if ggBeginTime?long gt .now?long>
            <a class="btn btn-default size-L"  style="width: 236px" >未开始申请竞买资格</a>
        <#elseif ggEndTime?long lt .now?long >
            <a class="btn btn-default size-L"  style="width: 236px" >已结束申请竞买资格</a>
        <#else>
            <a class="btn btn-danger size-L"  style="width: 236px" href="javascript:void(0)" onclick="checkResourceApply('${base}/console/apply?id=${resource.resourceId}');">申请竞买资格</a>
        </#if>
    <#else>
        <#if resource.bmBeginTime?long gt .now?long>
            <a class="btn btn-default size-L"  style="width: 236px" >未开始报名</a>
        <#elseif resource.bmEndTime?long lt .now?long >
            <a class="btn btn-default size-L"  style="width: 236px" >已结束报名</a>
        <#else>
            <a class="btn btn-danger size-L"  style="width: 236px" href="javascript:void(0)" onclick="checkResourceApply('${base}/console/apply?id=${resource.resourceId}');">开始竞买报名</a>
        </#if>
    </#if>

    </td>
</tr>
</#if>



