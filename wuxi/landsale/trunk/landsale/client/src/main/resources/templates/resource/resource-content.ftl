<style type="text/css">
    /*.label-danger {
        background-color: #ba2f2f;
    }

    .label {
        text-align: center;
        width: 48px !important;
    }*/
    *{
        padding: 0;
        margin: 0;
    }

</style>

<#list transResourcePage.items as resource>
    <#if resource_index%4 == 0>
    <div class="liebiao_heng">
    </#if>
    <div <#if resource_index%4 == 0>class="liebiao_jiaoyi"<#else>class="liebiao_jiaoyi2"</#if>>
        <a onclick="viewResource('${base}/resource/view?resourceId=${resource.resourceId}');" target="frm_${resource.resourceId}" style="z-index: 1;">
            <div class="liebiao_jiaoyi_title">
                <p>${resource.resourceLocation}</p>
            </div>
            <div class="liebiao_jiaoyi_in" style="position:absolute" >
                <div style="height:10px; width:10px;"></div>
                <ul>
                    <li>
                        <img src="${base}/images/icon1.png">
                        <#include "resource/resource-price.ftl">
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
                        <#include "resource/resource-time.ftl">
                    </li>
                </ul>
                <div class="guapai" style="text-align:right;bottom: 140px" >
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
            </div>
        </a>

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

    function resourceApply(resourceId) {
        $("#mainFrame", window.parent.document).attr("src", "${base}/resourceApply/apply?resourceId=" + resourceId);
    }

    function viewResource(resourceUrl) {
        $("#mainFrame", window.parent.document).attr("src", resourceUrl);
    }
</script>

