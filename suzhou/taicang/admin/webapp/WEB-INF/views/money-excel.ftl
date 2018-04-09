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
    <thead>
        <th width="200">竞买人</th>
        <th>收款银行</th>
        <th>收款账户</th>
        <th>转账人</th>
        <th>转账账户</th>
        <th>转账金额(万元)</th>
        <th>转账时间</th>
    </thead>
    <tbody>
    <#list transResourceApplyList as resourceApply>
    <#assign bankAccount=UserApplyUtil.getBankAccount(resourceApply.applyId)! />
        <#if bankAccount??>
        <#assign transBankPayList=UserApplyUtil.getBankAccountPayList(bankAccount.accountId) />
            <#list transBankPayList as transBankPay>
            <tr>
                <td><@userInfo userId=resourceApply.userId /></td>
                <td>${UserApplyUtil.getBankName(resourceApply.bankCode)}</td>
                <td style='mso-style-parent:style0;mso-number-format:"\@";'>${bankAccount.accountCode!}</td>
                <td>${transBankPay.payName!}</td>
                <td style='mso-style-parent:style0;mso-number-format:"\@";'>${transBankPay.payBankAccount!}</td>
                <td>${transBankPay.amount!}</td>
                <td>${transBankPay.payTime?string('yyyy-MM-dd HH:mm')}</td>
            </tr>
            </#list>
        </#if>
    </#list>
    </tbody>
</table>
</table>
</body>
</html>