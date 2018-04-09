<html>
<head>
    <title>出让金管理</title>
    <meta name="menu" content="menu_admin_moneyresourcelist"/>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="javascript:changeSrc('${base}/index')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/money/list')"> <span class="c_gray">财务管理</span></a>
    <span class="c_gray en">&gt;</span><span class="c_gray">保证金列表</span>
</nav>

<div id="tab_demo" class="HuiTab">
    <div class="tabBar cl" style="border-bottom: 2px solid #999999;"><a href="javascript:changeSrc('${base}/money/money-list?resourceId=${resourceId!}')"><span>保证金列表</span></a>
         <span class="current">错转款列表</span></div>
</div>
<table class="table table-border table-bordered">
    <thead>
    <tr class="text-c" style="background-color: #f5f5f5">
        <th>转账账户</th>
        <th style="width:180px;">转账银行账号</th>
        <th style="width:80px">转账金额</th>
        <th style="width:130px">转账时间</th>
        <th style="width:130px">接受银行</th>
        <th style="width:130px">接受银行账号</th>
    </tr>
    </thead>
    <tbody>
    <#if transBankPayList??>
    <#list transBankPayList as transBankPay>
    <tr>
        <td style="text-align: center">
             ${transBankPay.payName!}
        </td>
        <td style="text-align: center">
            ${transBankPay.payBankAccount!}
        </td>
        <td style="text-align: center">
             ${transBankPay.amount!}${transBankPay.moneyUnit!}
        </td>
        <td style="text-align: center">
             ${transBankPay.payTime!}
        </td>
        <td style="text-align: center">
             ${UserApplyUtil.getBankName(transBankPay.bankCode)!}
        </td>
        <td style="text-align: center">
             ${transBankPay.accountCode!}
        </td>
    </tr>
    </#list>
    </#if>
    </tbody>
</table>
<@Ca autoSign=0  />
</body>
</html>