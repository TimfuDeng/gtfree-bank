<html>
<head>
    <title>保证金查询</title>
    <meta name="menu" content="menu_admin_bailresourcelist"/>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/bail/resource/list"> <span class="c_gray">保证金查询</span></a>
    <span class="c_gray en">&gt;</span><span class="c_gray">保证金列表</span>
</nav>
<table class="table table-border table-bordered">
    <thead>
    <tr class="text-c" style="background-color: #f5f5f5">
        <th>竞买人</th>
        <th style="width:180px;">保证金账户信息</th>
        <th style="width:470px">到账信息</th>
    </tr>
    </thead>
    <tbody>
    <#if resourceApply??>
    <tr>
        <td>
            <@userInfo userId=resourceApply.userId />
            <#if resourceApply.applyStep==3 >
                <span class="label label-success">已交纳保证金</span>
            <#else>
                <span class="label label-default">未交纳保证金</span>
            </#if>
        </td>
        <td>
            <#assign bankAccount=UserApplyUtil.getBankAccount(resourceApply.applyId)! />
            <#if bankAccount??>
                <div>银行：${UserApplyUtil.getBankName(resourceApply.bankCode)}</div>
                帐号：${bankAccount.accountCode!}
            </#if>
        </td>
        <td>
            <#if bankAccount??>
                <#assign transBankPayList=UserApplyUtil.getBankAccountPayList(bankAccount.accountId) />
                <table class="table table-border">
                    <#list transBankPayList as transBankPay>
                        <tr>
                            <td>${transBankPay.payName}</td>
                            <td>${transBankPay.payBankAccount}</td>
                            <td>${transBankPay.amount}万元</td>
                            <td>${transBankPay.payTime}</td>
                        </tr>
                    </#list>
                </table>
            </#if>
        </td>

    </tr>
    </#if>
    </tbody>
</table>

</body>
</html>