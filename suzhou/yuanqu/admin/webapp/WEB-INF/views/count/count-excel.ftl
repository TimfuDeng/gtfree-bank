<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
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
<table border="1">
    <tr class="text-c" style="background-color: #f5f5f5" >
        <th > 地块编号</th>
        <th > 宗地编号</th>
        <th > 面积</th>
        <th > 土地用途</th>
        <th > 成交价格</th>
        <th > 竞得人</th>
        <th > 溢出率</th>
        <th > 成交时间</th>
    </tr>
<#list transResourceList as transResource>
    <#assign transResourceOffer = ResourceUtil.getMaxOfferByresourceId(transResource.resourceId)!/>
    <tr >
        <td >${transResource.resourceCode!}</td>
        <td >
            <#if transResource.transResourceSon??>
                <table style="border: 1px solid #ddd;" >
                    <#list transResource.transResourceSon as son>
                        <tr >
                            <td >
                            ${son.zdCode!}
                            </td>
                        </tr>
                    </#list>
                </table>
            </#if>
        </td>
        <td > <#if transResource.transResourceSon??>
            <table style="border: 1px solid #ddd;">
                <#list transResource.transResourceSon as son>
                    <tr >
                        <td >
                        ${son.zdArea!}平方米
                        </td>
                    </tr>
                </#list>
            </table>
        </#if></td>
        <td >${transResource.landUse!}</td>
        <td >${transResourceOffer.offerPrice!}万元</td>
        <td><@userInfo userId=transResourceOffer.userId /></td>
        <td >${(transResourceOffer.offerPrice-transResource.beginOffer)/transResource.beginOffer*100}%</td>
        <td style="text-align: center;">${(transResourceOffer.offerTime!)?number?number_to_date}</td>
    </tr>
</#list>
</table>

</body>
</html>