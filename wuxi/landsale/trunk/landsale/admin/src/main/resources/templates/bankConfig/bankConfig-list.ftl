<html>
<head>
    <title>银行配置</title>
    <style>
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
    <span class="c_gray en">&gt;</span><span class="c_gray">银行配置</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="bankName" class="input-text input-mini" name="bankName" value="${bankName!}" placeholder="请输入银行名称">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">
            <#if Session["_USER_BUTTON"]?seq_contains("bankConfigAdd")>
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/bank/config/add')">新增银行</button>
            </#if>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width:150px;">银行名称</th>
        <th style="width:150px;">银行缩写</th>
        <th style="width:150px;">图标</th>
        <th style="width: 150px;">操作</th>
    </tr>
    <#list transBankConfigList.items as transBank>
    <tr>
        <td>${transBank.bankName!}</td>
        <td>${transBank.bankCode!}</td>
        <td>
            <img src="${storage}/storage/thumbnail/${transBank.bankIcon!}" alt=""/>
        </td>
        <td>
            <#if Session._USER_BUTTON?seq_contains("bankConfigEdit")>
            <a href="javascript:changeSrc('${base}/bank/config/edit?configId=${transBank.configId!}')" class="btn btn-default size-S" >编辑银行</a>
            </#if>
            <#if Session._USER_BUTTON?seq_contains("bankConfigDelete")>
            <a href="javascript:deleteBankConfig('${transBank.configId!}')"  class="btn btn-default size-S" >删除银行</a>
            </#if>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transBankConfigList formId="frmSearch" />
<script type="text/javascript">
    function deleteBankConfig (configId) {
        $.ajax({
            type: "post",
            data: {configId: configId},
            url: "${base}/bank/config/deleteBankConfig",
            success: function (data) {
                if (data.flag) {
                    reloadSrc("frmSearch");
                }
                _alertResult('ajaxResultDiv', data.flag, data.message);
            }
        })
    }
</script>
</body>
</html>