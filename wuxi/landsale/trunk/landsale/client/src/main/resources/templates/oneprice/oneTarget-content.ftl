<#list oneTargetList.items as oneTarget>
<#--// 提前录入的最大有效报价，最小有效报价地块不显示-->
<div class="col-3" style="padding-right:20px;padding-top:10px">
    <input type="hidden" id="HideOneTargetId" name="HideOneTargetId" value="${oneTarget.id!}">
    <a onclick="javascript:resourceOffer('${oneTarget.transResourceId!}')" class="link-wrap">
        <h3 style="text-align: center;background:#3bb4f2;color:#fff; padding-top:10px; ">${oneTarget.transName!}</h3>
        <div class="info-section">
            <#if ((oneTarget.priceEndDate?long)-(nowDate?long)) gt 0 ><#--//报价结束时间大于现在时间-->
                <p class="price">
                    <span>最小报价(元):</span>
                    <span class="value" style="color: #d91615;">
                            <em class="price-font-small">
                            ${oneTarget.priceMin!}
                            </em>
                         </span>
                </p>
                <p class="price">
                    <span>最大报价(元):</span>
                    <span class="value" style="color: #d91615;">
                            <em class="price-font-small">
                            ${oneTarget.priceMax!}
                            </em>
                         </span>
                </p>
            <#else><#--//报价结束时间过了-->
                <p class="price">
                    <span>最小报价(元):</span>
                    <span class="value" style="color: ##777;">
                            <em class="price-font-small">
                            ${oneTarget.priceMin!}
                            </em>
                         </span>
                </p>
                <p class="price">
                    <span>最大报价(元):</span>
                    <span class="value" style="color: ##777;">
                            <em class="price-font-small">
                            ${oneTarget.priceMax!}
                            </em>
                         </span>
                </p>
            </#if>


            <p class="price price-assess" style="padding-top: 12px">
                <span>等待结束时间</span>
                <span class="value">
                             <span class="value">${oneTarget.waitEndDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                         </span>
            </p>
            <p class="price price-assess" style="padding-top: 0px">
                <span>询问结束时间</span>
                <span class="value">
                             <span class="value">${oneTarget.queryEndDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                         </span>
            </p>
            <p class="price price-assess" style="padding-top: 0px">
                <span>报价结束时间</span>
                <span class="value">
                             <span class="value">${oneTarget.priceEndDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                         </span>
            </p>
        </div>
        <div>
            <div class="flag">
                <#if ((oneTarget.priceEndDate?long)-(nowDate?long)) gt 0 ><#--//报价结束时间大于现在时间-->
                    <span class="label label-danger">一次报价</span>
                <#else><#--//报价结束时间过了-->
                    <span class="label label-over">已结束</span>
                </#if>


            </div>
        </div>
    </a>
</div>
</#list>
<script>
    //分页参数
    var currentPage = isEmpty($("#currentPage").val()) ? 1 : $("#currentPage").val();

    currentPage = ${oneTargetList.pageCount!}
    ==
    0 ? 1 : currentPage;
    var totalPages = ${oneTargetList.pageCount!}
    ==
    0 ? 1 : '${oneTargetList.pageCount!}';
    $("#totalPages").val(totalPages);
    $("#currentPage").val(currentPage);
    loadpage();

</script>