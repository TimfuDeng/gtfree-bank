<html>
<head>
    <title>-参数录入</title>
    <meta name="menu" content="menu_oneprice_betweenlist"/>
    <style type="text/css">
        .title-info h1, .title-info h3 {
            margin: 0;
            padding: 0 0 0 20px;
            font-family: "Microsoft Yahei";
            font-weight: 400;
        }
        .btn-upload input{
            height:55px;
            cursor: pointer;
        }


    </style>

    <script type="text/javascript" src="${base}/static/thridparty/uploadifive/jquery.uploadifive.min.js"></script>
    <script type="text/javascript">
        function checkForm(){
            if (!checkInputNull('waitTime','等待时长必须填写!'))
                return false;
            if (!checkInputNull('queryTime','查询时长必须填写!'))
                return false;
            if (!checkInputNull('priceTime','报价时长必须填写!'))
                return false;
            if (!checkInputNumber('waitTime','等待时长必须是数字!'))
                return false;
            if (!checkInputNumber('queryTime','查询时长必须是数字!'))
                return false;
            if (!checkInputNumber('priceTime','报价时长必须是数字!'))
                return false;
            if (!checkInputInteger('waitTime','等待时长必须是正整数!'))
                return false;
            if (!checkInputInteger('queryTime','查询时长必须是正整数!'))
                return false;
            if (!checkInputInteger('priceTime','报价时长必须是正整数'))
                return false;
            var priceGuid = $("input[name='priceGuid']").val();
            var priceMin = $("input[name='priceMin']").val();
            var priceMax = $("input[name='priceMax']").val();
            return true;
        }
        function checkInputNumber(name,info){
            if(isNaN($("input[name='"+name+"']").val())){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                if($("input[name='"+name+"']").val() > 999){
                    alert('数字超出范围，最大支持999！');
                    return false;
                }

                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }
        function checkInputInteger(name,info){
            if($("input[name='"+name+"']").val()!="" && $("input[name='"+name+"']").val()<=0){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else if($("input[name='"+name+"']").val().indexOf(".")>=0){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
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

    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/oneprice/resource/list" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/oneprice/between/list">参数设置</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> 参数录入</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg!}</div>
</#if>

<form class="form-base" method="post" action="${base!}/oneprice/between/save">
    <table class="table table-border table-bordered table-striped">
        <tbody>
        <tr>
            <td colspan="2"><label class="control-label">等待时长（分钟）：</label></td>
            <td colspan="3"><input type="input" class="input-text" name="waitTime" value="${oneParam.waitTime!}"></td>

        </tr>
        <tr>
            <td colspan="2"><label class="control-label">询问时长（分钟）：</label></td>
            <td colspan="3"><input type="input"  class="input-text" name="queryTime" value="${oneParam.queryTime!}"></td>

        </tr>
        <tr>
            <td colspan="2"><label class="control-label">报价时长（分钟）：</label></td>
            <td colspan="3"><input type="input" class="input-text" name="priceTime" value="${oneParam.priceTime!}"></td>
        </tr>
        <input type="hidden"  name="id" value="${oneParam.id!}">
        <#--<tr>

            <td colspan="2"><input type="input" class="input-text" name="priceMin" value="125"></td>

            <td class="text-c"> <label class="control-label">报价区间（%）</label></td>

            <td colspan="2"><input type="input"  class="input-text" name="priceMax" value="150"></td>
        </tr>-->
        </tbody>
    </table>
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/oneprice/between/list'">返回</button>
        </div>
    </div>
</form>
</body>
</html>