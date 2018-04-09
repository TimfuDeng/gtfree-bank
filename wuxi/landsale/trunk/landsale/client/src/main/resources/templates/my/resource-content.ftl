<#list transResourcePage.items as resource>
    <#if resource_index%4 == 0>
    <div class="liebiao_heng">
    </#if>
    <div <#if resource_index%4 == 0>class="liebiao_jiaoyi"<#else>class="liebiao_jiaoyi2"</#if>>
        <div class="liebiao_jiaoyi_title">
            <p>${resource.resourceLocation}</p>
        </div>
        <div class="liebiao_jiaoyi_in">
            <a onclick="viewResource('${base}/my/resource/view?resourceId=${resource.resourceId}');" target="frm_${resource.resourceId}" style="z-index: -1; ">
                <div style="height:10px; width:10px;"></div>
                <ul>
                    <li>
                        <img src="${base}/images/icon1.png">
                        <#include "my/resource-price.ftl">
                    </li>
                    <li>
                        <img src="${base}/images/icon1.png">
                        <p>起始价：</p>
                        <span class="span_2">${resource.beginOffer!}<@unitText offerUnit=resource.offerUnit/></span>
                    </li>
                    <li>
                        <img src="${base}/images/icon1.png">
                        <p>编号：</p>
                        <span class="span_2">${resource.resourceCode!}</span>
                    </li>
                    <li>
                        <img src="${base}/images/icon1.png">
                        <p>面积：</p>
                        <#if resource.crArea??>
                            <span class="span_2">${resource.crArea!}平方米-${(resource.crArea!*0.0015)?string("0.##")}亩</span>
                        </#if>
                    </li>
                    <li>
                        <img src="${base}/images/icon1.png">
                        <#include "my/resource-time.ftl">
                    </li>
                </ul>
            </a>
            <div class="guapai_my" style="text-align:right;bottom: 140px" >
                <#if resource.resourceEditStatus==2 || resource.resourceEditStatus==9>
                    <#if resource.resourceStatus==1>
                        <span class="label label-danger">正在竞价</span>
                    <#elseif resource.resourceStatus==11 >
                        <span class="label label-danger">最高限价</span>
                    <#elseif resource.resourceStatus==10 >
                        <span class="label label-danger">正在挂牌</span>
                    <#elseif resource.resourceStatus==20 >
                        <span class="label label-success">正在公告</span>
                    <#elseif resource.resourceStatus==30>
                        <span class="label label-over ">已成交</span>
                    <#elseif resource.resourceStatus==31>
                        <span class="label label-over ">&nbsp;已流拍&nbsp;</span>
                    </#if>
                    <#if isTopOffer?? && isTopOffer && (resource.resourceStatus==1 || resource.resourceStatus==10)>
                        <br/><span class="label label-danger" style="white-space: normal;">正在竞拍公租房</span>
                    </#if>
                <#elseif resource.resourceEditStatus==3 >
                    <span class="label label-over ">&nbsp;已中止&nbsp;</span>
                <#elseif resource.resourceEditStatus==4 >
                    <span class="label label-over ">&nbsp;已终止&nbsp;</span>
                </#if>
            </div>
            <#if resource.resourceEditStatus == 2 && (resource.resourceStatus == 20 || resource.resourceStatus == 10 || resource.resourceStatus == 1 || resource.resourceStatus == 11)>
                <#assign transUser = Session["_USER"]>
                <#assign transResourceApply = ResourceUtil.getTransResourceApplyByUserId(transUser.userId, resource.resourceId)>
                <#if transResourceApply?? && transResourceApply.applyStep==1>
                <#-- 缴纳保证金开始时间 > 当前时间-->
                    <#if resource.bzjBeginTime?long gt .now?long>
                        <div class="big_data4">
                            <button type="button" class="display_button">未开始保证金交纳</button>
                        </div>
                    <#-- 缴纳保证金结束时间 < 当前时间-->
                    <#elseif resource.bzjEndTime?long lt .now?long >
                        <div class="big_data4">
                            <button type="button" class="display_button">已结束保证金交纳</button>
                        </div>
                    <#else>
                        <div class="big_data4">
                            <button type="button" onclick="resourceApplyBank('${resource.resourceId}');" >选择竞买方式和银行</button>
                        </div>
                    </#if>
                <#--已报名未审核-->
                <#elseif transResourceApply?? && transResourceApply.applyStep==2>
                    <div class="big_data4">
                        <button type="button" class="display_button" >资格审核中</button>
                    </div>
                <#--已报名 审核通过-->
                <#elseif transResourceApply?? && transResourceApply.applyStep==3>
                    <#if resource.bzjBeginTime?long gt .now?long>
                        <div class="big_data4">
                            <button type="button" class="display_button">未开始保证金交纳</button>
                        </div>
                    <#elseif resource.bzjEndTime?long lt .now?long >
                        <div class="big_data4">
                            <button type="button" class="display_button">已结束保证金交纳</button>
                        </div>
                    <#else>
                        <div class="big_data4">
                            <button type="button" onclick="checkResourceApplyBzj('${resource.resourceId}');" >交纳保证金</button>
                        </div>
                    </#if>
                <#--已报名 审核被退回-->
                <#elseif transResourceApply?? && transResourceApply.applyStep==5>
                    <div class="big_data4">
                        <button type="button" onclick="resourceApplyBank('${resource.resourceId}');">审核失败并查看原因</button>
                    </div>
                <#--已报名 保证金缴纳完成-->
                <#elseif transResourceApply?? && transResourceApply.applyStep==4>
                <#--竞价 挂牌期 允许报价-->
                    <#if resource.resourceStatus == 10 || resource.resourceStatus == 1 || resource.resourceStatus == 11>
                        <div class="big_data4">
                            <button type="button" onclick="resourceOffer('${resource.resourceId}', '${resource.resourceCode}');" >报价</button>
                        </div>
                    <#elseif resource.resourceStatus == 20>
                        <div class="big_data4">
                            <button type="button" class="display_button">未开始挂牌</button>
                        </div>
                    </#if>
                </#if>
            </#if>
        </div>
    </div>

    <#if resource_index%4 == 3>
    </div>
    </#if>
</#list>
<script type="text/javascript">

    var currentPage = isEmpty($("#currentPage").val()) ? 1 : $("#currentPage").val();

    currentPage = ${transResourcePage.pageCount!} == 0 ? 1 : currentPage;
    <#--currentPage = ${transResourcePage.items!?size} == 0 ? 1 : currentPage;-->
    var totalPages = ${transResourcePage.pageCount!} == 0 ? 1 : '${transResourcePage.pageCount!}';
    $("#totalPages").val(totalPages);
    $("#currentPage").val(currentPage);
    loadpage();

    function viewResource(resourceUrl) {
        $("#mainFrame", window.parent.document).attr("src", resourceUrl);
    }
</script>

