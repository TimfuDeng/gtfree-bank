<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>竞得明细</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <style type="text/css">
        table p{
            margin-bottom: 0px;
        }
        thead  td{
            text-align: center !important;
            background-color: #f5fafe;
        }
        .offer-h1{
            padding-left:200px;
            color:#ba2f2f;
        }
    </style>
</head>
<body>
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/oneprice/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span>
        <a href="${base}/oneprice/my/view-resource-list" class="maincolor">成交结果</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">查看结果</span>
    </nav>

    <table class="table table-border table-bordered">
    <tbody>
    <tr>
        <td rowspan="4" style="background-color: #F5F5F5;width:20%;font-size: 16px">${oneTarget.transName!}</td>
        <td >竞得单位：</td>
        <td>${oneTarget.successUnit!}</td>

    </tr>
    <tr >

        <td style="width:15%;">竞得价（元）：</td>
        <td>${oneTarget.successPrice!}</td>
    </tr>
    <tr>

        <td >平均报价（元）：</td>
        <td>${oneTarget.priceAvg!}</td>
    </tr>




    </tbody>
    </table>
    <#if oneTarget??>
        <#if ((oneTarget.priceEndDate?long)-(nowDate?long)) lt 0 >
        <table class="table table-border table-bordered">

            <thead>
            <tr>
                <td colspan="3">报价列表</td>
            </tr>
            <tr>
                <td width="120">报价单位</td>
                <td width="110">报价（元）</td>
                <td width="110">报价时间</td>
            </tr>
            </thead>
            <tbody>
            <#if onePriceLogList?? && (onePriceLogList?size >0)>
                <#list onePriceLogList as onePriceLog>
                <tr>
                    <td style="text-align: center">
                    ${onePriceLog.priceUnit!}
                    </td>
                    <td style="text-align: center">
                    ${onePriceLog.price!}
                    </td>
                    <td style="text-align: center">
                    ${(onePriceLog.priceDate)?string("yyyy-MM-dd HH:mm:ss")}
                    </td>
                </tr>
                </#list>
            <#else>
                <#list transOnePriceLogList as transOnePriceLog>
                <tr>
                    <td style="text-align: center">
                    ${transOnePriceLog.PRICEUNIT!}
                    </td>
                    <td style="text-align: center">
                    ${transOnePriceLog.PRICE!}
                    </td>
                    <td style="text-align: center">
                    ${(transOnePriceLog.PRICEDATE)?string("yyyy-MM-dd HH:mm:ss")}
                    </td>
                </tr>
                </#list>
            </#if>

            </tbody>
        </table>
        <#else>
        <p>
        <h1 class="offer-h1">一次报价还未结束，请耐心等待结果！</h1>
        </p>
        </#if>
    <#else>
    <p>
    <h1 class="offer-h1">此地块还未进行过一次报价，暂无结果！</h1>
    </p>
    </#if>
</body>
</html>