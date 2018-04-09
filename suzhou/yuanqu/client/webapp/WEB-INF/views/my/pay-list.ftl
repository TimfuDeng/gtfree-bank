<@Head_SiteMash title="保证金" />
    <style type="text/css">
        table p{
            margin-bottom: 0px;
        }
        thead  td{
            text-align: center !important;
            background-color: #f5fafe;
        }
    </style>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
<div class="row cl" >
    <div class="col-2" style="padding-right: 10px">
        <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
            <li role="presentation" class="active">
                <a href="${base}/my/resource-list">
                    <i class="icon-th-large"></i>&nbsp;&nbsp;我的交易
                <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                <#if applyCount gt 0>
                    <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                </#if>
                </a>
            </li>
            <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息
            <#assign applyedCount=ResourceUtil.getIsApplyedCountByStauts()>
            <#if applyedCount gt 0>
                <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyedCount}</span>
            </#if>
            </a></li>
        </ul>
    </div>
    <div class="col-10">
        <nav class="breadcrumb pngfix">
            <i class="iconfont">&#xf012b;</i>
            <a href="${base}/index" class="maincolor">首页</a>
            <span class="c_gray en">&gt;</span>
            <a href="${base}/my/resource-list" class="maincolor">我的竞拍地块</a>
            <span class="c_gray en">&gt;</span><span class="c_gray">保证金</span>
        </nav>

        <table class="table table-border table-bordered">
            <tr>
                <td rowspan="5" style="background-color: #F5F5F5;width:100px;font-size: 16px">收款人</td>
                <td width="120">开户银行：</td>
                <td><#if bank??>${bank.bankName!}</#if></td>
            </tr>
            <tr>
                <td>户名：</td>
                <td><#if bank??>${bank.accountName!}</#if></td>
            </tr>
            <tr>
                <td>帐号：</td>
                <td>
                    <span id="applyAccount">${transBankAccount.accountCode!}</span>
                </td>
            </tr>
            <tr>
                <td>流水号：</td>
                <td>${transBankAccount.applyNo!}</td>
            </tr>
            <tr>
                <td>交款总额：</td>
                <td >
                <#if bank??>
                    <#if "USD"==bank.moneyUnit>
                    ${transResource.fixedOfferUsd!}万美元
                    <#elseif "HKD"==bank.moneyUnit>
                    ${transResource.fixedOfferHkd!}万港币
                    <#else>
                    ${transResource.fixedOffer!}万元
                    </#if>
                </#if>
                </td>
            </tr>
        </table>
        <table class="table table-border table-bordered table-bg">
            <thead>
            <tr>
                <td width="120">银行流水号</td>
                <td>交款信息</td>
                <td width="110">交款数额</td>
                <td width="120">交款时间</td>
                <td width="200">备注</td>
            </tr>
            </thead>
            <tbody>
            <#if transBankPayList??>
                <#list transBankPayList as transBankPay>
                <tr>
                    <td>
                    ${transBankPay.payNo!}
                    </td>
                    <td>
                        <p>
                            转账银行：${transBankPay.payBankAddress!}
                        </p>
                        <p>
                            转账人：${transBankPay.payName!}
                        </p>
                        <p>
                            转账账户：${transBankPay.payBankAccount!}
                        </p>
                    </td>
                    <td>
                        <p>
                            <#if bank??>
                                <#if "USD"==bank.moneyUnit>
                                ${transBankPay.amount!}万美元
                                <#elseif "HKD"==bank.moneyUnit>
                                ${transBankPay.amount!}万港币
                                <#else>
                                ${transBankPay.amount!}万元
                                </#if>
                            </#if>
                        </p>
                    </td>
                    <td>
                    ${transBankPay.payTime?string("yyyy-MM-dd HH:mm:ss")}
                    </td>
                    <td>
                    ${transBankPay.remark!}
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>