<style>

    body {
        margin: 0px;
    }

    * {
        font-family: '微软雅黑';
        color: #333
    }

    .head {
        font-size: 30px;
        font-weight: bold;
        color: #fff;
        height: 50px;
        background: #5eb95e;
        padding-left: 40px;
    }

    table {
        font-size: 36px;
    }

    th {
        font-weight: 600;
        color: #000
    }

    .price-font-small {
        font-size: 60px;
        font-weight: bold;
        width: 300px;
        color: #d91615
    }

    .detial * {
        font-size: 24px;
        color: #555
    }

    .time * {
        font-style: normal;
        font-weight: bold
    }

    .time var {
        font-size: 60px;
        color: #d91615;
    }

    .time em {
        font-size: 36px;
        padding: 12px
    }

    .showInfo {
        font-size: 15px;
        font-weight: bold;
        line-height: 30px;
        clear: both
    }

    .showInfo span {
        width: 200px;
        display: block;
        float: left;
    }

    .recInfo {
        font-size: 18px;
        font-weight: bold;
        padding-top: 30px;
    }

    .recInfo span {
        font-size: 40px;
    }

    .tableRight * {
        font-size: 20px;
        font-weight: bold;
        line-height: 50px;
        text-align: center;
    }

    .tableRight .td1 {
        text-align: center
    }

    .active .rad {
        background: #cc0000;
        color: #fff;
        margin-left: 15px;
        width: 70px;
        height: 36px;
        line-height: 36px;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
        border-radius: 10px;
    }

    .rad {
        background: #777;
        color: #fff;
        margin-left: 15px;
        width: 70px;
        height: 36px;
        line-height: 36px;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
        border-radius: 10px;
    }

    .active .td2, .active .td3 {
        color: #cc0000;
    }

    .fr {
        text-align: right;
    }

    .pdr30 {
        padding-right: 30px;
    }

    .td2 {
        color: #777;
        padding-right: 30px;
        text-align: center;
    }

    .td3 {
        color: #777;
        text-align: center
    }

    .tableRight td, .tableRight tr {
        border-bottom: 1px solid #c1c1c1
    }

    .point {
        width: 20px;
        height: 20px;
        border-radius: 20px;
        background: #ececec;
        float: left;
        margin-left: 12px;
    }

    .pointActive {
        width: 20px;
        height: 20px;
        border-radius: 20px;
        background: #d91615;
        float: left;
        margin-left: 12px;
    }

    .traded {
        color: #d91615
    }

    .tradedInfo {
        text-align: center;
        background: #5eb95e;
        height: 100px;
    }

    .tradedInfo div {
        color: #fff;
        line-height: 50px;
    }

    .reserveInfo {
        text-align: center;
        background: #5eb95e;
        height: 100px;
        background: rgb(119, 119, 119);
    }

    .reserveInfo div {
        color: #fff;
        line-height: 50px;
    }



    #orderEnd {
        width: 430px
    }

    #noOrder {
        height: 100px;
        width: 400px;
        text-align: center;
        background: rgb(119, 119, 119)
    }

</style>
<div id="headTitle" class="head" style="width:900px">${transResource.resourceCode!}</div>
    <input type="hidden" value="${transResource.resourceId!}" id="resourceId"/>
    <input type="hidden" value="${maxOfferTime!}" id="maxOfferTime"/>
    <div style="padding: 30px 0px 0px 30px; float: left">
        <table id="orderEnd">
            <tr class="price">
                <th><span style="front-size:30px">当前价：</span></th>
                <td class="value" style="color: #d91615;font-size: 30px">
                    <div id="orderPrice" class="price-font-small traded">
                        <span class="price-font-small traded" id="maxOffer">${maxOffer!}万
                    </div>
                </td>
            </tr>
            <#if transResource.resourceStatus==30>
            <tr>
                <td class="tradedInfo" colspan="2">
                    <div style="font-size: 30px">本地块已成交</div>
                    <div style="font-size: 20px;">成交人：${offerUser!}</div>
                </td>
            </tr>
            </#if>
            <#if transResource.resourceStatus==10>
                <tr>
                    <td class="tradedInfo" colspan="2">
                        <div style="font-size: 30px">本地块正在拍卖</div>
                    </td>
                </tr>
            </#if>
        </table>

    <#if transResource.resourceStatus==31>
        <table id="noOrder" class="reserveInfo">
            <tr>
                <td>
                    <div style="font-size: 35px;">本地块已流拍</div>
                    <div style="font-size: 24px;">因未有人出价，本地块流拍</div>
                </td>
            </tr>
        </table>
    </#if>

        <div class="showInfo">
            <span>起始价：￥${transResource.beginOffer!}万元</span>
            <span>加价幅度：￥${transResource.addOffer!}万元</span>
        </div>
        <div class="showInfo">
            <span>出让面积：${transResource.crArea!}平方米</span>
            <span>保证金：￥${transResource.fixedOffer!}万元</span>
        </div>
        <#--
        <div class="showInfo">
            <span>规划用途：${transResource.landUse!}</span>
            <span>最高限价：${transResource.maxOffer!}</span>
        </div>
        <div class="showInfo">
            挂牌开始时间：${transResource.gpBeginTime!}
        </div>
        <div class="showInfo">
            挂牌截止时间：${transResource.gpEndTime!}
        </div>
        -->
    </div>
    <div style="float: right;padding-right: 20px;">
        <div class="recInfo">竞拍总计<span id="offerFrequency">${offerFrequency!}</span>次</div>
        <table class="tableRight">
            <thead>
            <tr>
                <th>状态</th>
                <th style="text-align: center">价格（万元）</th>
                <th>时间</th>
            </tr>
            </thead>
            <tbody>
            <#list offerList as offer>
            <#if offer_index lt 3>
            <tr>
                <#if offer_index==0>
                <td class="td1">
                    <div class="rad" style="background: red;">领先</div>
                </td>
                <td class="td2"><span style="color: red">${offer.offerPrice}</span></td>
                <td class="td3"><span style="color: red">${offer.offerTime?number?number_to_datetime}</span></td>
                <#else>
                <td class="td1">
                    <div class="rad">出局</div>
                </td>
                <td class="td2">${offer.offerPrice}</td>
                <td class="td3">${offer.offerTime?number?number_to_datetime}</td>
                </#if>
            </tr>
            </#if>
            </#list>
            </tbody>
        </table>
    </div>