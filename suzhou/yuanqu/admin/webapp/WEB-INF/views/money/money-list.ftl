<html>
<head>
    <title>出让金管理</title>
    <meta name="menu" content="menu_admin_moneyresourcelist"/>
    <script type="text/javascript">

        $(document).ready(function(){

        });
        function backOffer(applyId){
            if(confirm("请再次确认已退还保证金！（该操作无法回退）")){
                var _status=true;
                setAndReloadStatus(applyId,_status);
            }
        }

        function setAndReloadStatus(_applyId,_status){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/money/status/change.f",{"applyId":_applyId,"status":_status},
                    function(data){
                        if(data.ret!=null&&data.ret==true){
                            alert("退还成功！");
                            $("#div_status_offer_" + _applyId).load("${base}/console/money/offer-status.f?applyId="+_applyId );
                            $("#div_status_money_" + _applyId).load("${base}/console/money/money-status.f?applyId="+_applyId );
                        }
                        else{
                            alert(data.msg);
                        }
                    },'json'
            );
        }



    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/money/list"> <span class="c_gray">财务管理</span></a>
    <span class="c_gray en">&gt;</span><span class="c_gray">保证金列表</span>
</nav>

<div id="tab_demo" class="HuiTab">
    <div class="tabBar cl" style="border-bottom: 2px solid #999999;"><span class="current">保证金列表</span>
        <a href="${base}/console/money/wrong-trun?resourceId=${resourceId!}"> <span>错转款列表</span></a></div>
</div>
<a href="${base}/console/money/excel.f?resourceId=${resourceId!}" class="btn btn-primary">导出Excel</a>

<table class="table table-border table-bordered">
    <thead>
    <tr class="text-c" style="background-color: #f5f5f5">
        <th>竞买人</th>
        <th style="width:180px;">保证金账户信息</th>
        <th style="width:470px">到账信息</th>
        <th style="width:70px">退还状态</th>
    </tr>
    </thead>
    <tbody>
    <#if transResourceApplyList??&&(transResourceApplyList?size>0)>
    <#list transResourceApplyList as resourceApply>
    <tr>
        <td>
           <#include "../money/offer-status.ftl">
        </td>
        <td>
            <#assign bankAccount=UserApplyUtil.getBankAccount(resourceApply.applyId)! />
            <#assign  moneyUnit=UserApplyUtil.getMoneyUnitById(resourceApply.bankId)!>
            <#if bankAccount??>
                <div>银行：${UserApplyUtil.getBankName(resourceApply.bankCode)}</div>
                帐号：${bankAccount.accountCode!}
            </#if>
        </td>
        <td>
            <#if bankAccount??>
                <#assign transBankPayList=UserApplyUtil.getBankAccountPayList(bankAccount.accountId) />

                <table style="border-top: 1px solid #ddd;border-right:1px solid #ddd;">
                    <#list transBankPayList as transBankPay>
                        <tr>
                            <td>${transBankPay.payName}</td>
                            <td>${transBankPay.payBankAccount}</td>
                            <td>
                                <#if "USD"==moneyUnit>
                                ${transBankPay.amount!}万美元
                                <#elseif "HKD"==moneyUnit>
                                ${transBankPay.amount!}万港币
                                <#else>
                                ${transBankPay.amount!}万元
                                </#if>
                            </td>
                            <td>${transBankPay.payTime}</td>
                        </tr>
                    </#list>
                </table>
            </#if>

        </td>
        <td>
            <#include "../money/money-status.ftl">
        </td>

    </tr>
    </#list>
    </#if>
    </tbody>
</table>
<@Ca autoSign=0  />
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>

</body>

</html>