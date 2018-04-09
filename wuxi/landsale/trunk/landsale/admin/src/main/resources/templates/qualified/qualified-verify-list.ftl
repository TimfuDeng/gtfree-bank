<html>
<head>
    <title>资格审核</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
    <style type="text/css">
    </style>
    <script type="text/javascript">
        var _applyId,_status;
        $(document).ready(function(){

        });

        function BackEdit(applyId){
            _applyId=applyId;_status=2;
            if(confirm("是否确认退回！")){
                setAndReloadStatus();
            }
        }




        function setAndReloadStatus(){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/qualified/status/change.f",{"applyId":_applyId,"status":_status},
                    function(data){

                        if(data.ret!=null&&data.ret==true){
                            alert("退回成功");
                            $("#div_status_" + _applyId).load("${base}/qualified/status/qualified-list-status.f?applyId="+_applyId );
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
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/index')">资格审核</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/resourceApply?resourceId=${transResourceApply.resourceId!}')">竞买人审核</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">审核记录</span>
</nav>

<table class="table table-border table-bordered table-striped table-bg " >
    <thead>
    <tr class="text-c" >
        <th >竞买人</th>
        <th style="width:20%;">审核人</th>
        <th style="width:20%;">审核时间</th>
        <th style="width:10%;">是否通过</th>
        <th style="width:20%">操作</th>
    </tr>
    </thead>
    <tbody>
    <#if transBuyQualifiedList??>
        <#list transBuyQualifiedList as transBuyQualified>
        <tr class="order-bd">
            <td style="text-align: center;">
            ${transUser.viewName!}
            </td>
            <td style="text-align: center">
            ${transBuyQualified.qualifiedAuditor!}
            </td>
            <td style="text-align: center">
                <#if transBuyQualified.qualifiedTime?exists>
                    ${transBuyQualified.qualifiedTime!?string("yyyy-MM-dd HH:mm")}
                </#if>
            </td>
            <td style="text-align: center">
                <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==1 >是<#elseif transBuyQualified?? && transBuyQualified.qualifiedStatus==2 >否<#else>未审核</#if>
            </td>
            <td style="text-align: center">
                <a   href="javascript:changeSrc('${base}/qualified/verify/view?qualifiedId=${transBuyQualified.qualifiedId!}&applyId=${transResourceApply.applyId!}')" class="btn size-S btn-primary">详情</a>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
</body>
</html>