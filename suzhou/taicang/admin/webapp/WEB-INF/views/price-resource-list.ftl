<html>
<head>
    <title>底价管理</title>
    <meta name="menu" content="menu_admin_priceresourcelist"/>

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
    <span class="c_gray en">&gt;</span><span class="c_gray">底价管理</span>
</nav>

<table class="table table-border table-bordered" >
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th >资源描述</th>
            <th style="width:190px;">挂牌开始时间</th>
            <th style="width:190px">挂牌结束时间</th>
            <th style="width:120px;">当前价</th>
            <th style="width:120px;">底价</th>
            <th >操作</th>
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
                    ${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}
            </td>
            <td>
                    ${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}
            </td>
            <td style="text-align: center">
                <p>
                    <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                        <#assign maxResourceOffer= PriceUtil.getMaxOffer(resource.resourceId)!/>
                        <#if maxResourceOffer.offerPrice??>
                        ${maxResourceOffer.offerPrice?string("0.####")}万元
                        </#if>
                    </em>
                </p>
            </td>
            <td style="text-align: center">
                已设置<span style="color: #dd514c" >${PriceUtil.getMinPriceCount(resource.resourceId)!}</span>人
            <p>

                <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                    <#assign minPrice= PriceUtil.getMinPrice(resource.resourceId)/>
                    <#if minPrice gt 0>
                    ${minPrice?string("0.####")}万元
                    <#else>
                    <span style="color: #f4523b">本人未设置</span>
                    </#if>
                </em>
            </p>
            </td>
            <td style="text-align: center">
                <a class="btn btn-primary size-S" href="${base}/console/price/price-edit?resourceId=${resource.resourceId!}">底价录入</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />



</body>
</html>