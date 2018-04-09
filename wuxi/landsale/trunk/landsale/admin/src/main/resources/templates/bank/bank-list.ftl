<html>
<head>
    <title>银行管理</title>
    <style>
        .l{
            margin-left: 10px;
        }
        .l span,.l button{
            margin-left: 10px;
        }
        .l input{
            width: auto;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">银行管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="bankName" class="input-text input-mini" name="bankName" value="${bankName!}" placeholder="请输入银行名称">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th>已开通银行名称</th>
        <th>账户名称</th>
        <th style="width: 220px;">操作</th>
    </tr>
    <#list transBankList.items as transBank>
        <tr>
            <td>
                <div class="l"><img src="${storage}/storage/thumbnail/${transBank.bankIcon!}"  style="width: 120px;height: 40px;"></div>
                <div class="l">${transBank.bankName!}<span bankId="${transBank.bankId}"></span>
                    <p style="margin-bottom:0px">${transBank.moneyUnit!}</p></div>
            </td>
            <td>${transBank.accountName!}<p style="margin-bottom:0px">${transBank.accountCode!}</p></td>
            <td>
                <#if Session["_USER_BUTTON"]?seq_contains("bankEdit")>
                <a href="javascript:changeSrc('${base}/bank/edit?bankId=${transBank.bankId}')" class="btn  btn-primary size-MINI">编辑</a>
                </#if>
                <#if Session["_USER_BUTTON"]?seq_contains("bankDelete")>
                <a href="javascript:deleteBank('${transBank.bankId}')" class="btn  btn-danger size-MINI"">删除</a>
                </#if>
                <#if Session["_USER_BUTTON"]?seq_contains("bankSendTest")>
                <a href="javascript:bankTest('${transBank.bankId}')" class="btn  btn-default size-MINI" >测试链路</a>
                </#if>
                <#if Session["_USER_BUTTON"]?seq_contains("bankSensPay")>
                <a href="javascript:changeSrc('${base}/bank/toSendPay?bankId=${transBank.bankId}')" class="btn  btn-default size-MINI" >发送到账通知</a>
                </#if>

            </td>
        </tr>
    </#list>
</table>
<@PageHtml pageobj=transBankList formId="frmSearch" />
<br/>
<#if Session["_USER_BUTTON"]?seq_contains("bankAdd")>
<#list transBankConfigList as transBankConfig>
<div style="float: left;width:196px;margin-top:10px;margin-bottom:5px;margin-right:-1px;border: 1px solid #ddd;">
    <div style="padding:5px 5px;">
        <img src="${storage}/storage/thumbnail/${transBankConfig.bankIcon!}"  style="width: 120px;">

        <a href="javascript:changeSrc('${base}/bank/add?configId=${transBankConfig.configId}')" style="margin-top: 4px" class="btn  btn-primary size-MINI">
            开通接口
        </a>
    </div>
</div>
</#list>
</#if>
<script type="text/javascript">
    function deleteBank (bankId) {
        $.ajax({
            type: "post",
            data: {bankId: bankId},
            url: "${base}/bank/deleteBank",
            success: function (data) {
                if (data.flag) {
                    reloadSrc("frmSearch");
                }
                _alertResult('ajaxResultDiv', data.flag, data.message);
            }
        })
    }

    function bankTest(bankId){
        $.ajax({
            type: "POST",
            url: "${base}/bank/sendText",
            data: {bankId:bankId},
            cache: false,
            success: function(data){
                if(data.code == "00"){
                    $("span[bankid='"+bankId+"']").each(function(){
                        $(this).removeClass("label-danger");
                        $(this).addClass("label label-success");
                        $(this).html("测试成功！");
                    });
                }else{
                    $("span[bankid='"+bankId+"']").each(function(){
                        $(this).removeClass("label-success");
                        $(this).addClass("label label-danger");
                        $(this).html("测试失败！");
                    });
                }
            }
        });

    }
</script>
</body>
</html>