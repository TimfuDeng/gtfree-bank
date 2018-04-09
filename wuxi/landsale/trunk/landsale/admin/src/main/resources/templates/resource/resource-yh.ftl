<html>
<head>
    <title>-出让地块</title>
    <meta name="menu" content="menu_admin_yhresourcelist"/>
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
        .offer-h1{
            padding-left:200px;
            color:#ba2f2f;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function(){
            loadChecked();
            chooseCheckBox();
//            offerFormat();
        });

        //加载选中项
        function loadChecked(){
            $("table input:checkbox").each(function(){
                if($(this).val()==$("input[name='offerUserId']").val()){
                    $(this).prop("checked",true);
                }
            });
        }
        //选中事件
        function chooseCheckBox(){

            //showConfirm("是否确认竞得人："+$(obj).parent().next().val()+"竞得价："+$(obj).parent().next().next().val(),obj);
            $("table input:checkbox").bind("click",function(){
                $("table input:checkbox").not($(this)).prop("checked",false);//不选当前对象本身之外的对象
                $(this).prop("checked",true);//选中当前
                $("input[name='offerUserId']").val("");
                $("input[name='offerUserId']").val($.trim($(this).val()));
                $("input[name='successUnit']").val($(this).parent().next().html().trim());
            });
        }

        function checkForm(){
            if (!checkInputNull('offerUserId','必须在报名列表中选中一个报名单位!'))
                return false;
            if (!checkInputNull('successPrice','必须设置竞得价!'))
                return false;
            submitForm();
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

        String.prototype.trim = function () {
            return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
        }

//        function offerFormat(){
//            $(".offerText").each(function(){
//                var timeStr=$(this).html();
//                var dateObj= new XDate(timeStr*1);
//                $(this).html(dateObj.toString("yyyy年MM月dd日 HH:mm:ss"));
//            });
//        }

        function submitForm() {
            $.ajax({
                url:"${base}/resource/yh/success/save",//提交地址
                data:$("#form1").serialize(),
                type:"POST",
                async:"false",
                dataType:"json",
                success:function(result){
                    if (result.flag == true){
                        _alertResult('ajaxResultDiv', true, result.message);
                        changeSrc('${base}/resource/yh/resource?resourceId=${resource.resourceId!}');
                    }else{
                        _alertResult('ajaxResultDiv', false, result.message);
                    }
                }
            });
        }

        <#--function postResult(resourceId) {-->
            <#--$.ajax({-->
                <#--url:"${base}/resource/yh/success/post",//提交地址-->
                <#--data:{resourceId :resourceId},-->
                <#--type:"POST",-->
                <#--async:"false",-->
                <#--dataType:"json",-->
                <#--success:function(result){-->
                    <#--if (result.flag == true){-->
                        <#--_alertResult('ajaxResultDiv', true, result.message);-->
                        <#--changeSrc('${base}/resource/yh/resource?resourceId=${resource.resourceId!}');-->
                    <#--}else{-->
                        <#--_alertResult('ajaxResultDiv', false, result.message);-->
                    <#--}-->
                <#--}-->
            <#--});-->
        <#--}-->


    </script>

</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="javascript:changeSrc('${base}/index')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/resource/yh/list')">设置摇号地块(成交)</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> 设置成交信息</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<form class="form-base" id="form1" method="post" action="${base}/resource/yh/success/save">
    <table class="table table-border table-bordered table-striped">
        <tbody>
        <#if resource??>
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">设置成交信息（单位：<span><@unitText value=resource.offerUnit! /></span>）</td>
        </tr>
        <tr>
            <td width="180"><label class="control-label">地块编号：</label></td>
            <td>${resource.resourceCode!}</td>
            <td width="180"><label class="control-label">最高限价：</label></td>
            <td width="300">${resource.maxOffer!}</td>
        </tr>
        <tr>
           <td><label class="control-label">竞得单位：</label></td>
            <td >
                <input  type="hidden" name="offerUserId" value="${selectedAgree.userId!}">
                <#if selectedAgree??>
                        <input  class="input-text" name="successUnit" value="${UserUtil.getUserName(selectedAgree.userId)}">
                    <#else>
                        <input  class="input-text" name="successUnit" value="">
                </#if>
            </td>
            <td><label class="control-label">设置竞得价：</label></td>
            <td >
                <input  class="input-text" name="successPrice" value="${yhResult.resultPrice!}">
            </td>


               <input type="hidden"  name="resourceId" value="${resource.resourceId!}">

        </tr>
        </#if>

        </tbody>
    </table>
        <table class="table table-border table-bordered">
                <#if agreeList?? && (agreeList?size >0)>
                <thead>
                    <tr>
                        <td colspan="5" style="text-align: center;font-size: 14px;font-weight:700"><span style="color: #f4523b;"></span>报名列表</td>
                    </tr>
                    <tr>
                        <td style="text-align: center" width="120">#</td>
                        <td style="text-align: center" width="280">报名人</td>
                        <td style="text-align: center" width="280">报名时间</td>
                    </tr>
                </thead>
                <tbody>
                    <#list agreeList as agree>
                        <tr>
                        <td style="text-align: center">
                            <input type="checkbox" value="${agree.userId!}">
                        </td>
                        <td style="text-align: center">
                            <#assign viewName=UserUtil.getUserName(agree.userId)>
                        ${viewName!}
                        </td>
                        <td class="offerText" style="text-align: center">
                        ${agree.agreeTime!}
                        </td>
                    </tr>
                    </#list>
                </tbody>
                </#if>
        </table>
        <div class="row-fluid">
            <div class="span10 offset2">
                <button type="button" class="btn btn-primary" onclick="checkForm()">保存</button>
                <#--<button type="button" class="btn btn-primary" onclick="postResult('${resource.resourceId!}')">发布</button>-->
                <button type="button" class="btn" onclick="javascript:changeSrc('${base}/resource/yh/list')">返回</button>
            </div>
        </div>
</form>
</body>

</html>