<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>保证金</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.iframe-transport.js"></script>
    <style type="text/css">
        table p{
            margin-bottom: 0px;
        }
        thead  td{
            text-align: center !important;
            background-color: #f5fafe;
        }
        .table-bordered td {
            border-left: 1px solid #ddd;
            border-top: 1px solid #ddd;
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>
<body>
<div class="wp">
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <span class="c_gray en">&gt;</span><a href="${base}/resource/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span>
    <a href="${base}/my/resource-list" class="maincolor">我的保证金</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">保证金</span>
</nav>

<table class="table table-border table-bordered">
    <tr>
        <td rowspan="5" style="background-color: #F5F5F5;width:100px;font-size: 16px">收款人</td>
        <td width="120">开户银行：</td>
        <td>${bank.bankName}</td>
    </tr>
    <tr>
        <td>户名：</td>
        <td>${bank.accountName}</td>
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
        <td>保证金总额：</td>
        <td >${transResource.fixedOffer!}万元</td>
    </tr>
</table>
<table class="table table-border table-bordered table-bg">
    <thead>
    <tr>
        <td width="120">银行流水号</td>
        <td>交款信息</td>
        <td width="110">交款数额</td>
        <td width="120">到账时间</td>
        <td width="200">备注</td>
    </tr>
    </thead>
    <tbody>
    <#list transBankPayList as transBankPay>
    <tr>
        <td>
        ${transBankPay.payNo}
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
            ${transBankPay.amount}万元
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
    </tbody>
</table>
</div>
</body>
</html>