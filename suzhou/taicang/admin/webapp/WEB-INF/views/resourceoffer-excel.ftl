<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>报价列表</title>
    <style type="text/css">
        <!--
        .STYLE1 {
            font-family: "黑体";
            font-size: 24px;
            font-weight: bold;
        }
        .STYLE2 {
            font-family: "黑体";
            font-style: italic;
            font-size: 14px;
        }
        -->
    </style>
</head>
<body>
<table border="1" >
    <tr class="text-c" >
        <td  style="width:355px;">竞买人</th>
        <td style="width:155px;">报价时间</th>
        <td style="width:150px">报价</th>
        <td style="width:137px;">报价类型</th>
    </tr>
    <#list transResourceOfferList as transResourceOffer>
    <tr class="order-bd">
        <td>
        <@userInfo userId=transResourceOffer.userId />
        </td>
        <td class="offerText" style='mso-style-parent:style0;mso-number-format:"\@";'>
        ${transResourceOffer.offerTime?number_to_datetime}
        </td>
        <td>
        ${transResourceOffer.offerPrice}万
        </td>
        <td style="text-align: center">
            <#if transResourceOffer.offerType==0>
                <span>挂牌报价</span>
            <#else>
                <span>限时报价</span>
            </#if>
        </td>
    </tr>
    </#list>
</table>
</body>
</html>