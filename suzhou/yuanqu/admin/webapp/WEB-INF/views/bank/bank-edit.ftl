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

        function isIP(ip) {
            if(ip=="")
                return false;
            var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/
            return pattern.test(ip);
        }


        function checkInputNull(name,info){
            if($("input[name='"+name+"']").val()==""){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }

        function checkForm(){
            checkInputFileter();
            if(!checkInputNull("accountCode","银行账号必须填写"))
                return false;
            if(!checkInputNull("accountName","账户名称必须填写"))
                return false;
            if(!checkInputNull("interfaceIp","银行接口IP必须填写"))
                return false;
            if(!checkInputNull("interfacePort","银行接口端口必须填写"))
                return false;
            if(isNaN($("input[name='accountCode']").val())){
                alert("请填写正确的银行账号");
                return false;
            }
            if(isNaN($("input[name='interfacePort']").val())){
                alert("请填写正确的银行接口端口");
                return false;
            }
            if(!isIP($("input[name='interfaceIp']").val())){
                alert("请填写正确的银行接口IP");
                return false;
            }
            return true;
        }
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
            <td><input type="text" class="input-text" name="bankName" value="${transBank.bankName!}" style="width:400px" maxlength="32">
                <img src="${base}/static/bankimages/${transBank.bankCode!}.gif"  >
                <label class="control-label">行政区部门：</label>
                <@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=200 SelectValue=transBank.regionCode/>
            </td>
        </tr>

        <tr>
            <td><label class="control-label">银行账号：</label></td>
            <td><input type="text" class="input-text" name="accountCode" value="${transBank.accountCode!}" style="width:400px;background-color: #FFFFCC" maxlength="32" >
                <div class="radio-box">
                    <label for="control-label">账户类型：</label>

                        <input type="radio" id="radio-1" name="moneyUnit" value="CNY" <#if "CNY"==transBank.moneyUnit> checked="true" </#if>>
                        <label for="control-label">人民币</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="radio-2" name="moneyUnit" value="USD"<#if "USD"==transBank.moneyUnit> checked="true" </#if>>
                        <label for="control-label">美元</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="radio-3" name="moneyUnit" value="HKD" <#if "HKD"==transBank.moneyUnit> checked="true" </#if>>
                        <label for="control-label">港币</label>

                </div>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">账户名称：</label></td>
            <td><input type="text" class="input-text" name="accountName" value="${transBank.accountName!}" style="width:400px;background-color: #FFFFCC" maxlength="50">例如：**市国土资源局</td>
        </tr>
        <tr>
            <td><label class="control-label">银行接口IP：</label></td>
            <td><input type="text" class="input-text" name="interfaceIp" value="${transBank.interfaceIp!}" style="width:400px;background-color: #FFFFCC" maxlength="32">例如：192.168.10.109</td>
        </tr>
        <tr>
            <td><label class="control-label">银行接口端口：</label></td>
            <td><input type="text" class="input-text" name="interfacePort" value="${transBank.interfacePort!}" style="width:400px;background-color: #FFFFCC" maxlength="10" >例如：5006</td>
        </tr>
    </table>

    <input type="hidden" name="id" value="${transBank.bankId!}">
    <input type="hidden" name="bankCode" value="${transBank.bankCode!}">
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/bank/list'">返回</button>
        </div>
    </div>
</form>
</body>
</html>