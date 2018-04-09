<tr class="price price-disable">
    <td colspan="2" style="line-height: 140px;text-align: center;font-size:20px;font-weight:700" >

    <#if resource.resourceStatus==30>
        <p style="line-height:80px">本场竞买已成交！</p>
        <p style="line-height:50px;font-size: 14px;">竞得人：${UserUtil.getUserName(maxOffer.userId)}</p>
    <#elseif resource.resourceStatus==31 >
        本场竞买已流拍 ！
    <#elseif resource.resourceEditStatus==3 >
        本场竞买已中止 ！
    <#elseif resource.resourceEditStatus==4 >
        本场竞买已终止 ！
    </#if>
    </td>
</tr>
