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
            _applyId=applyId;_status=1;
            if(confirm("是否确认退回！")){
                setAndReloadStatus();
            }
        }




        function setAndReloadStatus(){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/qualified/status/change.f",{"applyId":_applyId,"status":_status},
                    function(data){

                        if(data.ret!=null&&data.ret==true){
                            alert("退回成功");
                            $("#div_status_" + _applyId).load("${base}/console/qualified/status/qualified-list-status.f?applyId="+_applyId );
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
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/land">资格审核</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/resourceApply?resourceId=${transResourceApply.resourceId!}">竞买人审核</a>
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
            ${transBuyQualified.qualifiedTime?string("yyyy-MM-dd HH:mm")}
            </td>
            <td style="text-align: center">
                 <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==1 >是<#else>否</#if>
            </td>
            <td style="text-align: center">
                <a   href="${base}/console/qualified/verify/view?qualifiedId=${transBuyQualified.qualifiedId!}&&applyId=${transResourceApply.applyId!}" class="btn size-S btn-primary">详情</a>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel"><i class="icon-warning-sign"></i> 确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
</body>
</html>