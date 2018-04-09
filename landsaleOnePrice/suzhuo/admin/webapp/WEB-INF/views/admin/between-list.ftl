<html>
<head>
    <title>参数设置</title>
    <meta name="menu" content="menu_admin_betweenlist"/>

</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/oneprice/resource/list" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">参数设置</span>
</nav>
<form name="frmSearch" id="frmSearch" method="get"></form>
<table class="table table-border table-bordered" >
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="width:220px;">等待时长（分钟）</th>
            <th style="width:220px">询问时长（分钟）</th>
            <th style="width:220px;">报价时长（分钟）</th>

            <th >操作</th>
        </tr>
    </thead>
    <tbody>
        <#list oneParamList.items as oneParam >
            <tr  class="text-c">
                <td>
                    ${oneParam.waitTime!}
                </td>
                <td>
                    ${oneParam.queryTime!}
                </td>
                <td style="text-align: center">
                    ${oneParam.priceTime!}
                </td>
                <td style="text-align: center">
                    <a class="btn btn-primary size-S" href="${base}/oneprice/between/edit?id=${oneParam.id}">参数录入</a>
                </td>
            </tr>
        </#list>


    </tbody>
</table>




</body>
</html>