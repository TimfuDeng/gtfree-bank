<tr class="price price-disable">
    <td colspan="2" style="line-height: 140px;text-align: center;font-size:20px;font-weight:700" >

    <#if resource.resourceStatus==30>
        <!--已经审核的情况下-->
        <#if resource.resourceVerifyId??>
            <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
            <!--审核未通过-->
            <#if resouerceVerify.verifyStatus==0>
                <p style="line-height:80px">
                    该竞得人因${resouerceVerify.verifySuggestion!}原因，成交审核未通过。
                </p>
            <#else>
                <p style="line-height:80px">
                    <#if resource.dealNoticeId??>
                        本场竞买已成交！<a href="http://jsyd.sipac.gov.cn/cjggnr/index.jhtml?noticeId=${resource.dealNoticeId!}">查看成交公告</a>
                    <#else>
                        本场竞买已成交！
                    </#if>
                </p>
                <p style="line-height:50px;font-size: 14px;">竞得人：<span id="owner">${UserUtil.getUserName(maxOffer.userId)}</span></p>
            </#if>
        <#else>
            <p style="line-height:80px">
                <#if resource.dealNoticeId??>
                    本场竞买已成交！<a href="http://jsyd.sipac.gov.cn/cjggnr/index.jhtml?noticeId=${resource.dealNoticeId!}">查看成交公告</a>
                <#else>
                    本场竞买已成交！
                </#if>
            </p>
            <p style="line-height:50px;font-size: 14px;">竞得人：<span id="owner">${UserUtil.getUserName(maxOffer.userId)}</span></p>
        </#if>
    <#elseif resource.resourceStatus==31 >
        本场竞买已流拍 ！
    <#elseif resource.resourceEditStatus==3 >
        <#if resource.suspendNoticeId??>
            本场竞买已中止 ！<a href="http://jsyd.sipac.gov.cn/zzggnr/index.jhtml?noticeId=${resource.suspendNoticeId!}" >查看中止公告</a>
        <#else>
            本场竞买已中止 ！
        </#if>
    <#elseif resource.resourceEditStatus==4 >
        本场竞买已终止 ！
    </#if>
    </td>
</tr>


