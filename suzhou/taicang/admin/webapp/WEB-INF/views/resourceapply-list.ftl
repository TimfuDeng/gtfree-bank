<html>
<head>
    <title>竞买人列表</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
    <style type="text/css">
    </style>
    <script type="text/javascript">
        var globalApplyId;
        function showTrialWindow(applyId){
            globalApplyId = applyId;
            $.ajax({
                url: '${base}/console/resource-apply/getTrial.f',
                type: 'post',
                data: {
                    applyId: globalApplyId
                },
                dataType: 'json',
                success: function(msg){
                    if(msg.trialType){
                        $('#trialType').val(msg.trialType);
                    }
                    if(msg.trialReason){
                        $('#trialReason').val(msg.trialReason);
                    }
                    $('#myModal').modal({
                        backdrop: false
                    });
                }
            });
        }

        function doPostTrialInfo(){
            $.ajax({
                url: '${base}/console/resource-apply/doTrial.f',
                type: 'post',
                data: {
                    applyId: globalApplyId,
                    trialType: $('#trialType').val(),
                    trialReason: $('#trialReason').val()
                },
                success: function(msg){
                    if(msg == 'success'){
                        $('#myModal').modal('hide');
                        alert('操作成功！');
                        window.location.reload();
                    }
                }
            });
        }

        function clearFailTrialReason(){
            var trialType = $('#trialType').val();
            if(trialType == 'PASSED_TRIAL'){
                $('#trialReason').val('');
            }
        }

        function deleteApplyInfo(applyId) {
            var userName = $.trim($('span[apply_id="' + applyId + '"]').text());
            if (confirm('是否确认删除"' + userName + '"用户的报名信息？')) {
                $.ajax({
                    url: '${base}/console/resource-apply/delete',
                    type: 'post',
                    data: {
                        applyId: applyId
                    },
                    success: function (responseObj) {
                        alert('操作成功！');
                        window.location.reload();
                    }
                })
            }
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource/list">出让地块(${resource.resourceCode!})</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人列表</span>
</nav>
<div id="tab_demo" class="HuiTab">
    <div class="tabBar cl" style="border-bottom: 2px solid #999999;"><span class="current">竞买人列表</span><a href="${base}/console/resource-apply/offerlist?resourceId=${resource.resourceId!}"> <span>竞买人报价</span></a></div>
</div>
<table class="table table-border table-bordered table-striped table-bg " >
    <thead>
    <tr class="text-c" >
        <th >竞买人</th>
        <th style="width:100px;">报名时间</th>
        <th style="width:92px;">保证金账户信息</th>
        <th style="width:100px;">竞买方式</th>
        <th style="width:50px">状态</th>
        <th style="width:120px">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list transResourceApplyList as resourceApply>

    <tr class="order-bd">
        <td>
        <span apply_id="${resourceApply.applyId!}"><@userInfo userId=resourceApply.userId /></span>
        <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)! />
        <#if resource.resourceStatus == 30>
            <#if maxOffer.userId?? && (resourceApply.userId == maxOffer.userId)>
                <#if resource.qualificationType == 'PRE_TRIAL'>
                    <span class="label label-danger">竞买成功</span>
                <#else>
                    <#if resourceApply.trialType?? && resourceApply.trialType=='PASSED_TRIAL'>
                        <a class="btn btn-success size-MINI" style="width:150px;margin-top: 5px;" href="javascript:void(0);" onclick="showTrialWindow('${resourceApply.applyId!}');">竞得人[资格审核通过]</a>
                    <#elseif resourceApply.trialType?? && resourceApply.trialType=='FAILED_TRIAL'>
                        <a class="btn btn-danger size-MINI" style="width:150px;margin-top: 5px;" href="javascript:void(0);" onclick="showTrialWindow('${resourceApply.applyId!}');">竞得人[资格审核不通过]</a>
                    <#else>
                        <a class="btn btn-primary size-MINI" style="width:150px;margin-top: 5px;" href="javascript:void(0);" onclick="showTrialWindow('${resourceApply.applyId!}');">竞得人[资格审核]</a>
                    </#if>
                </#if>
            </#if>
        </#if>
        </td>
        <td>
        ${resourceApply.applyDate?string("yyyy-MM-dd HH:mm")}
        </td>
        <td>
            <#assign bankAccount=UserApplyUtil.getBankAccount(resourceApply.applyId)! />
            <#if bankAccount??>
                <div>银行：${UserApplyUtil.getBankName(resourceApply.bankCode)}</div>
                帐号：${bankAccount.accountCode!}
            </#if>
        </td>
        <td style="text-align: center">
            <#if resourceApply.applyType==0>
                <span>独立竞买</span>
            <#else>
                <span>联合竞买</span>
            </#if>
            <a class="btn btn-danger size-MINI" style="width:150px;margin-top: 5px;" href="javascript:void(0);" onclick="deleteApplyInfo('${resourceApply.applyId!}');">删除报名</a>
        </td>
        <td style="text-align: center">
            <#if resource.qualificationType?? && resource.qualificationType == 'PRE_TRIAL'>
                <#if resourceApply.trialType?? && resourceApply.trialType == 'NONE_COMMIT_TRIAL'>
                <span class="label label-primary">未提交资格审核</span>
                <#elseif resourceApply.trialType?? && resourceApply.trialType == 'COMMIT_TO_TRIAL'>
                <span class="label label-primary">资格审核中</span>
                <#elseif resourceApply.trialType?? && resourceApply.trialType == 'PASSED_TRIAL'>
                <span class="label label-success">资格审核通过</span>
                <#else>
                <span class="label label-danger">资格审核不通过</span>
                </#if>
            </#if>
        <#if resourceApply.applyStep==1>
            <!--<span class="label label-danger">已报名</span>-->
        <#elseif resourceApply.applyStep==2>
            <span class="label label-danger">未交保证金</span>
        <#elseif resourceApply.applyStep==3>
            <span class="label label-danger">已交保证金</span>
        </#if>
        </td>
        <td style="text-align: center">
            <a target="_blank" class="btn btn-primary size-MINI" style="width:150px" href="${base}/console/resource-apply/attachment?userId=${resourceApply.userId!}&resourceId=${resource.resourceId!}">查看附件材料>>></a>
            <#if resource.resourceStatus != 30 && resource.resourceStatus != 31>
                <#if resourceApply.trialType?? && resourceApply.trialType != 'NONE_COMMIT_TRIAL'>
                    <a class="btn btn-primary size-MINI" style="width:150px;margin-top: 5px;" href="javascript:void(0);" onclick="showTrialWindow('${resourceApply.applyId!}');">资格审核</a>
                </#if>
            </#if>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel">请参阅附件后给出资格审核结果</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">
        <table>
            <tr>
                <td><label class="control-label">审核结果：</label></td>
                <td>
                    <select id="trialType" class="select" style="width: 300px;" onchange="clearFailTrialReason();">
                        <option value="PASSED_TRIAL">资格审核通过</option>
                        <option value="FAILED_TRIAL">资格审核不通过</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label class="control-label">审核原因：</label></td>
                <td><textarea id="trialReason" style="width: 300px;height: 100px;margin-top: 10px;"></textarea></td>
            </tr>
        </table>
        </p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal" onclick="doPostTrialInfo();">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>