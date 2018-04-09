<html>
<head>
    <title>参数设置</title>
    <meta name="menu" content="menu_oneprice_betweenlist"/>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="javascript:changeSrc('${base}/oneprice/resource/list')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">参数设置</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/oneprice/between/edit')">新增参数</button>
        </div>
    </form>
</div>
<table class="table table-border table-bordered" >
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="width:200px;">等待时长（分钟）</th>
            <th style="width:200px">询问时长（分钟）</th>
            <th style="width:200px;">报价时长（分钟）</th>
            <#--<th style="width:200px;">最高限价间隔时间（分钟）</th>-->
            <th style="width:200px;">行政区部门</th>
            <th >操作</th>
        </tr>
    </thead>
    <tbody>
    <#if oneParamList?? && oneParamList.items?? && oneParamList.items?size gt 0>
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
                <#--<td style="text-align: center">-->
                    <#--${oneParam.maxIntervalTime!}-->
                <#--</td>-->
                <td>
                    <div>
                        <#list regionList as region >
                            <#if oneParam.regionCode == region.regionCode>
                                <span id="regionName" name="regionName" value="${region.regionName!}"
                                      style="width: auto;background-color: background-color: #ffffcc;">${region.regionName!}</span>
                            </#if>
                        </#list>
                    </div>
                </td>
                <td style="text-align: center">
                    <a class="btn btn-primary size-S" href="javascript:changeSrc('${base}/oneprice/between/edit?id=${oneParam.id!}')">参数修改</a>
                </td>
            </tr>
        </#list>
    <#else>
    <tr>
        <td colspan="6" style="text-align: center">
            <a class="btn btn-primary size-S" href="javascript:changeSrc('${base}/oneprice/between/edit')">参数录入</a>
        </td>
    </tr>
    </#if>
    </tbody>
</table>




</body>
</html>