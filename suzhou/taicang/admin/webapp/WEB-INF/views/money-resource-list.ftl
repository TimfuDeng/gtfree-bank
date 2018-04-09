<html>
<head>
    <title>财务管理</title>
    <meta name="menu" content="menu_admin_moneyresourcelist"/>

    <script type="text/javascript">
        var _resourceId,_status;
        $(document).ready(function(){

        });

    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">财务管理</span>
</nav>
        <form id="frmSearch"></form>
<table class="table table-border table-bordered" >
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="border-right: 1px solid #e8e8e8;">资源描述</th>
            <th style="width:170px;">挂牌开始时间</th>
            <th style="width:170px">挂牌结束时间</th>
            <th style="width:137px;border-right: 1px solid #e8e8e8;">保证金（万元）</th>
            <th style="width:137px">操作</th>
        </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>
        <tr>
            <td>
                <div class="l" style="width: 400px;">
                    <a href="${base}/console/resource/view?resourceId=${resource.resourceId!}" >
                        <div>${resource.resourceCode!}
                        <@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
                        </div>
                        <div>${resource.resourceLocation!}</div>


                    </a>
                </div>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td style="text-align: center">
                <p>
                    <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                    ${resource.fixedOffer?string("0.##")}万元</em>
                </p>
            </td>
            <td style="text-align: center">
                <a target="_blank" class="btn btn-primary size-S" href="${base}/console/money/money-list?resourceId=${resource.resourceId!}">保证金列表</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
</body>
</html>