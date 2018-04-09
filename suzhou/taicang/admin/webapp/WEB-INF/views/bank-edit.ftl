<html>
<head>
    <title>-银行开通</title>
    <meta name="menu" content="menu_admin_banklist"/>
    <style type="text/css">
        td input{
            padding-right: 0px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function(){

        });
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/bank/list">银行管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transBank.bankName!}</span>
</nav>
<#if _result?? && _result>
    <div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg}</div>
</#if>
<form class="form-base" method="post" action="${base}/console/bank/save">
    <table class="table table-border table-bordered table-striped">
        <tr>

        </tr>
        <tr>
            <td width="120"><label class="control-label">银行名称：</label></td>
            <td><input type="text" class="input-text" name="bankName" value="${transBank.bankName!}" style="width:400px">
                <img src="${base}/static/bankimages/${transBank.bankCode!}.gif"  >
                <label class="control-label">行政区：</label>
                <@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=200 SelectValue=transBank.regionCode/>
            </td>
        </tr>

        <tr>
            <td><label class="control-label">银行账号：</label></td>
            <td><input type="text" class="input-text" name="accountCode" value="${transBank.accountCode!}" style="width:400px">
                <div class="radio-box">
                    <label for="control-label">账户类型：</label>
                    <#if "CNY"==transBank.moneyUnit>
                        <input type="radio" id="radio-1" name="moneyUnit" value="CNY" checked="checked" >
                        <label for="control-label">人民币</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="radio-2" name="moneyUnit" value="USD">
                        <label for="control-label">美元</label>
                    <#else>
                        <input type="radio" id="radio-1" name="moneyUnit" value="CNY" >
                        <label for="control-label">人民币</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="radio-2" name="moneyUnit" value="USD" checked="checked">
                        <label for="control-label">美元</label>
                    </#if>
                </div>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">账户名称：</label></td>
            <td><input type="text" class="input-text" name="accountName" value="${transBank.accountName!}" style="width:400px">例如：**市国土资源局</td>
        </tr>
        <tr>
            <td><label class="control-label">银行接口IP：</label></td>
            <td><input type="text" class="input-text" name="interfaceIp" value="${transBank.interfaceIp!}" style="width:400px">例如：192.168.10.109</td>
        </tr>
        <tr>
            <td><label class="control-label">银行接口端口：</label></td>
            <td><input type="text" class="input-text" name="interfacePort" value="${transBank.interfacePort!}" style="width:400px">例如：5106</td>
        </tr>
        <tr>
            <td><label class="control-label">银行联系电话：</label></td>
            <td><input type="text" class="input-text" name="telephone" value="${transBank.telephone!}" style="width:400px">例如：0512-88888888</td>
        </tr>
    </table>

    <input type="hidden" name="id" value="${transBank.bankId!}">
    <input type="hidden" name="bankCode" value="${transBank.bankCode!}">
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/bank/list'">返回</button>
        </div>
    </div>
</form>
</body>
</html>