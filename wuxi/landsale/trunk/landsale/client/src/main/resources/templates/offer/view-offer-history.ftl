<form id="frmSearch">
    <table width="1138" style="margin:0 auto;  line-height:35px;" border="0" cellpadding="0" cellspacing="0">
        <tr style="background-color:#f6f6f6; text-align:center;">
            <td>轮次</td>
            <td>状态</td>
            <td>竞买人</td>
            <td>竞价时间</td>
            <td>竞买出价</td>
            <td>溢价率</td>
            <#if transResource.offerUnit == 0>
                <td>平均地价</td>
                <#if maxRjl gt 0>
                    <td>楼面价</td>
                </#if>
            <#elseif transResource.offerUnit == 1>
                <td>总价</td>
                <#if maxRjl gt 0>
                    <td>楼面价</td>
                </#if>
            <#elseif transResource.offerUnit == 2>
                <td>总价</td>
                <td>平均地价</td>
            <#elseif transResource.offerUnit == 3>
                <td>总价</td>
                <td>平均地价</td>
                <#if maxRjl gt 0>
                    <td>楼面价</td>
                </#if>
            </#if>
        </tr>
        <#list transResourceOfferList.items as transResourceOffer >
            <tr style="text-align:center;">
                <td>
                    <#--${transResourceOfferList.totalCount!}总条数-->
                    <#--${transResourceOfferList.pageCount!}总页数-->
                    <#--${transResourceOfferList.size!}分页大小-->
                    <#--${transResourceOfferList.index!}当前页数-->
                    <#--轮次 = 总条数 - （分页大小 * （当前页数 - 1）） - 下标-->
                    ${transResourceOfferList.totalCount - (transResourceOfferList.size * (transResourceOfferList.index - 1)) - transResourceOffer_index}
                </td>
                <td>
                    <#if transResourceOffer_index == 0 >
                        <span class="label label-danger label-max">领先</span>
                    <#else>
                        <span>---</span>
                    </#if>
                </td>
                <td>
                    <#if userId?? && transResourceOffer.userId==userId>
                        <span class="label label-default">我的报价</span>
                    <#else>
                        <span class="other-price">其他报价</span>
                    </#if>
                </td>
                <td>
                    ${transResourceOffer.offerTime?number_to_datetime}
                </td>
                <td>${transResourceOffer.offerPrice}<label class="offer_unit"><@unitText offerUnit = transResource.offerUnit/></label>
                <td>${transResourceOffer.premiumRate}%</td>
                <#if transResource.offerUnit == 0>
                    <td>${transResourceOffer.avgPrice}元/平方米</td>
                    <#if maxRjl gt 0>
                        <td>${transResourceOffer.gatePrice}元/平方米</td>
                    </#if>
                <#elseif transResource.offerUnit == 1>
                    <td>${transResourceOffer.totalPrice}万元</td>
                    <#if maxRjl gt 0>
                        <td>${transResourceOffer.gatePrice}元/平方米</td>
                    </#if>
                <#elseif transResource.offerUnit == 2>
                    <td>${transResourceOffer.totalPrice}万元</td>
                    <td>${transResourceOffer.avgPrice}元/平方米</td>
                <#elseif transResource.offerUnit == 3>
                    <td>${transResourceOffer.totalPrice}万元</td>
                    <td>${transResourceOffer.avgPrice}元/平方米</td>
                    <#if maxRjl gt 0>
                        <td>${transResourceOffer.gatePrice}元/平方米</td>
                    </#if>
                </#if>

            </tr>
        </#list>
    </table>
</form>
<@PageHtml pageobj = transResourceOfferList formId = 'frmSearch'/>
<script type="text/javascript">
    /**
     * 分页提交
     * @param formId
     * @param index
     * @constructor
     */
    function SubmitPageFormOld(formId,index){
        $("#offerHistory").load("${base}/resourceOffer/getOfferHistory", {resourceId: $("#resourceId").val(), index : index});
    }
</script>
