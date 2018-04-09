<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>我的竞买地块</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <style type="text/css">
        table p{
            margin-bottom: 0px;
        }
        thead  td{
            text-align: center !important;
            background-color: #f5fafe;
        }
    </style>
</head>
<body>
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/oneprice/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">成交地块</span>
    </nav>
    <form class="navbar-form" id="frmSearch">
    </form>

    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <td>地块描述</td>
            <td width="160">报价结束时间</td>
            <td width="160">竞得单位</td>
            <td width="160">竞得价</td>
            <td width="200">操作</td>
        </tr>
        </thead>
        <tbody>
        <#list oneApplyList.items as oneApply>

            <#assign oneTarget=ResultUtil.getOneTargetById(oneApply.targetId)!/>
            <tr>
            <td>
                <a href="${base}/oneprice/view?id=${oneTarget.id!}" target="frm_${oneTarget.id!}">
                    <p>名称：${oneTarget.transName!}
                            <#if oneTarget.successPrice??>
                                <span class="label label-danger">已成交</span>
                            <#else>
                                <span class="label label-warning">进行中</span>
                            </#if>

                    </p>
                    <p>坐落：${oneTarget.transAddress!}</p>
                </a>
            </td>
            <td>
                <p>
                ${oneTarget.priceEndDate?string("yyyy-MM-dd HH:mm:ss")}
                </p>

            </td>
            <td>
                <p>
                    ${oneTarget.successUnit!}
                </p>

            </td>
            <td>
                <p>
                    ${oneTarget.successPrice!}
                </p>
            </td>
            <td style="text-align: center">

                <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/oneprice/my/view-offer-list?id=${oneTarget.transTargetId!}">查看结果&gt;&gt;&gt;</a></p>
                <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/oneprice/my/cjtzs.f">成交通知书&gt;&gt;&gt;</a></p>
            </td>
        </tr>
        </#list>


        </tbody>
    </table>
</body>
</html>