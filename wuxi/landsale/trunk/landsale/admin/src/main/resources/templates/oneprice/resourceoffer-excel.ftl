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
    <tr class="text-c">
        <td colspan="4" class="text-c" >${oneTarget.transName!}</td>
    </tr>
    <tr class="text-c" >
        <td style="width:260px;" style="text-align: center;" >竞买人</th>
        <td style="width:160px;" style="text-align: center;">报价时间</th>
        <td style="width:260px" style="text-align: center;">报价</th>
        <th style="width:260px" style="text-align: center;">（报价减去平均价）差值(元)</th>
    </tr>
<#list onePriceLogList as onePriceLog>
    <tr class="order-bd">
        <td class="text-c">
        ${onePriceLog.priceUnit!}
        </td>
        <td class="offerText text-c">
        ${onePriceLog.priceDate?string("yyyy-MM-dd HH:mm:ss")}
        </td>
        <td class="text-c" >
        ${onePriceLog.price!}
        </td>
        <td class="text-c" >
        ${onePriceLog.price-avgPrice}
        </td>
    </tr>
</#list>
    <tr class="text-c">
        <td colspan="2" class="text-c">合计(元)：</td>
        <td  class="text-c" style="text-align: right;">${totalPrice!}</td>
    </tr>
    <tr class="text-c">
        <td colspan="2" class="text-c">平均价(元)：</td>
        <td  class="text-c" style="text-align: right;">${avgPrice!}</td>
    </tr>
</table>
</body>
</html>