<html>
<head>
    <title>-出让地块</title>
    <meta name="menu" content="menu_oneprice_resourcelist"/>
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
        .image a{
            position: relative;
            top:-30px;
            filter: alpha(opacity=0);
            opacity: 0;

        }
        .image:hover a{
            filter: alpha(opacity=100);
            opacity: 1;
        }
    </style>
    <script type="text/javascript">
        function checkForm(){
            if (!checkInputNull('priceGuid','市场指导价必须填写!'))
                return false;
            if (!checkInputNull('priceMin','最小有效报价必须填写!'))
                return false;
            if (!checkInputNull('priceMax','最大有效报价必须填写!'))
                return false;
            if (!checkInputNumber('priceGuid','市场指导价必须是数字!'))
                return false;
            if (!checkInputNumber('priceMin','最小有效报价必须是数字!'))
                return false;
            if (!checkInputNumber('priceMax','最大有效报价必须是数字!'))
                return false;
            if (!checkInputInteger('priceGuid','场指导价必须是正整数!'))
                return false;
            if (!checkInputInteger('priceMin','最小有效报价必须是正整数!'))
                return false;
            if (!checkInputInteger('priceMax','最大有效场报价必须是正整数'))
                return false;


            var priceGuid = $("input[name='priceGuid']").val();
            var priceMin = $("input[name='priceMin']").val();
            var priceMax = $("input[name='priceMax']").val();
            if(!(priceGuid*1<priceMin*1 && priceMin*1<priceMax*1)){
                alert("最大有效报价必须大于最小有效报价，最小有效报价必须大于市场指导价!")
                return false;
            }
            return true;
        }
        function checkInputNumber(name,info){
            if(isNaN($("input[name='"+name+"']").val())){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
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
    <span class="c_gray en">&gt;</span><a href="${base}/oneprice/resource/list">出让地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> 设置报价信息</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<form class="form-base" method="post" action="${base!}/oneprice/resource/save">
    <table class="table table-border table-bordered table-striped">
        <tbody><tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">设置报价信息</td>
        </tr>
        <tr>
            <td width="180"><label class="control-label">宗地编号：</label></td>
            <td>${transTargetName!}</td>
            <td><label class="control-label">市场指导价（万元）：</label></td>
            <td >
                <input type="text" class="input-text" name="priceGuid" value="<#if oneTarget??>${oneTarget.priceGuid!}</#if>">
            </td>
        </tr>
        <tr>
            <td><label class="control-label">最小有效报价（万元）：</label></td>
            <td >
                <input type="text" class="input-text" name="priceMin" value="<#if oneTarget??>${oneTarget.priceMin!}</#if>">
            </td>
            <td><label class="control-label">最大有效报价（万元）：</label></td>
            <td >
                <input type="text" class="input-text" name="priceMax" value="<#if oneTarget??>${oneTarget.priceMax!}</#if>">
            </td>

        </tr>

        <input type="hidden"  name="transTargetId" value="${transTargetId!}">
        <input type="hidden"  name="transNo" value="${transTargetNo!}">
        <input type="hidden"  name="transName" value="${transTargetName!}">
        <input type="hidden"  name="transArea" value="${transResource.crArea!}">
        <input type="hidden"  name="transUseLand" value="${transResource.landUse!}">
        <input type="hidden"  name="transAddress" value="${transResource.resourceLocation!}">
        </tbody>
    </table>

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/oneprice/resource/list'">返回</button>
        </div>
    </div>
</form>

</body>
</html>