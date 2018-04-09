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
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人审核</span>
</nav>

<table class="table table-border table-bordered table-striped table-bg " >
    <thead>
    <tr class="text-c" >
        <th >竞买人</th>
        <th style="width:10%;">币种</th>
        <th style="width:10%;">竞买方式</th>
        <th style="width:15%;">报名时间</th>
        <th style="width:20%">资料</th>
        <th style="width:20%">操作</th>
    </tr>
    </thead>
    <tbody>
    <#if transResourceApplyList??>
    <#list transResourceApplyList as resourceApply>

    <tr class="order-bd">
        <td style="text-align: center;">
            <@userInfo userId=resourceApply.userId />
            <#if resourceApply.applyStep==2>
                <span class="label label-danger" style="margin-top: 0px">未审核</span>
            <#elseif resourceApply.applyStep==3||resourceApply.applyStep==4>
                <span class="label label-success">通过</span>
            <#elseif resourceApply.applyStep==5>
                <span class="label label-danger" style="margin-top: 0px">未通过</span>
            <#else>
                <span class="label label-danger" style="margin-top: 0px">报名中</span>
            </#if>
        </td>
        <td style="text-align: center">
        <#if resourceApply.applyStep!=1>
            <#if resourceApply.moneyUnit=="USD">
                <span>美元</span>
            <#elseif resourceApply.moneyUnit=="HKD">
                <span>港币</span>
            <#else>
                <span>人民币</span>
            </#if>
        </#if>

        </td>

        <td style="text-align: center">
        <#if resourceApply.applyStep!=1>
            <#if resourceApply.applyType==0>
                <span>独立竞买</span>
            <#else>
                <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/console/qualified/list/scale?applyId=${resourceApply.applyId}">
                    联合竞买
                </a>
            </#if>
        </#if>

        </td>
        <td style="text-align: center">
        <#if resourceApply.applyStep??&&(resourceApply.applyStep gt 1)>
            ${resourceApply.applyDate?string("yyyy-MM-dd HH:mm")}
        </#if>
        </td>
        <td style="text-align: center">
            <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/console/qualified/attachment?userId=${resourceApply.userId!}&resourceId=${resourceId!}">查看附件材料>>></a>
        </td>
        <td style="text-align: center">
                <#include "../common/qualified-list-status.ftl">
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