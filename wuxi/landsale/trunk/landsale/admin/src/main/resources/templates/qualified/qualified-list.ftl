<html>
<head>
    <title>资格审核</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
    <style type="text/css">
    </style>
    <script type="text/javascript">
        var _applyId, _status;
        $(document).ready(function () {

        });

        function BackEdit(applyId) {
            _applyId = applyId;
            _status = 2;
            if (confirm("是否确认退回！")) {
                setAndReloadStatus();
            }
        }

        function setAndReloadStatus() {
            $.ajaxSetup({
                cache: false
            });
            $.post("${base}/console/qualified/status/change.f", {"applyId": _applyId, "status": _status},
                    function (data) {

                        if (data.ret != null && data.ret == true) {
                            alert("退回成功");
                            $("#div_status_" + _applyId).load("${base}/console/qualified/status/qualified-list-status.f?applyId=" + _applyId);
                        }
                        else {
                            alert(data.msg);
                        }

                    }, 'json'
            );
        }

        // 删除报名信息
        function deleteApply(applyId) {
            if (confirm("此操作将删除该竞买人报名及联合竞买等相关信息,确认删除么?")) {
                $.ajax({
                    type: "post",
                    url: "${base}/qualified/deleteApply",
                    data: {applyId: applyId},
                    success: function (data) {
                        if (data.flag) {
                            changeSrc("${base}/qualified/resourceApply", {resourceId: '${resourceId!}'});
                        }
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    }
                });
            }
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/index')">资格审核</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人审核</span>
</nav>

<table class="table table-border table-bordered table-striped table-bg ">
    <thead>
    <tr class="text-c">
        <th>竞买人</th>
        <th>币种</th>
        <th>竞买方式</th>
        <th>报名时间</th>
        <#if transResource.beforeBzjAudit == 1>
        <th>资料</th>
        </#if>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if transResourceApplyList??>
        <#list transResourceApplyList as resourceApply>
        <tr class="order-bd">
            <!--竞买人-->
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
            <!--币种-->
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
            <!--竞买方式-->
            <td style="text-align: center">
                <#if resourceApply.applyStep!=1>
                    <#if resourceApply.applyType==0>
                        <span>独立竞买</span>
                    <#else>
                        <a class="btn btn-primary size-MINI" style="width:180px"
                           href="javascript:changeSrc('${base}/qualified/list/scale?applyId=${resourceApply.applyId}')">
                            联合竞买
                        </a>
                    </#if>
                </#if>
            </td>
            <!--报名时间-->
            <td style="text-align: center">
                <#if resourceApply.applyStep!=1>
                    ${resourceApply.applyDate?string("yyyy-MM-dd HH:mm")}
                </#if>
            </td>
            <!--资料-->
            <#if transResource.beforeBzjAudit == 1>
                <td style="text-align: center">
                    <a class="btn btn-primary size-MINI" style="width:180px"
                       href="javascript:changeSrc('${base}/qualified/attachment?userId=${resourceApply.userId!}&resourceId=${resourceId!}')">查看附件材料>>></a>
                </td>
            </#if>
            <!--操作-->
            <td style="text-align: center">
                <#include "qualified/qualified-list-status.ftl">
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
</body>
</html>