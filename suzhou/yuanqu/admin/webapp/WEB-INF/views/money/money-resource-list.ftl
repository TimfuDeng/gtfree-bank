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
            <th style="width:10%;">挂牌开始时间</th>
            <th style="width:10%">挂牌截止时间</th>
            <th style="width:20%;border-right: 1px solid #e8e8e8;">保证金（万元）</th>
            <th style="width:30%;border-right: 1px solid #e8e8e8;">成交信息</th>
            <th style="width:10%">操作</th>
        </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>
        <tr>
            <td>
                <div class="l">
                    <a href="${base}/console/resource/view?resourceId=${resource.resourceId!}" >
                        <div>
                            <#if resource.resourceCode?length gt 5>
                                ${resource.resourceCode[0..4]}...
                            <#else>
                                ${resource.resourceCode!}
                            </#if>
                            <!--成交审核已审核-->
                            <#if resource.resourceVerifyId??>
                                <@resource_edit_status_verify resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus resourceVerifyId=resource.resourceVerifyId/>
                            <#else>
                                <@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
                            </#if>
                        </div>
                        <div>
                            <#if resource.resourceCode?length gt 14>
                                 ${resource.resourceLocation?string[0..13]}
                            <#else>
                                ${resource.resourceLocation!}
                            </#if>
                        </div>
                    </a>
                </div>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;text-align: center">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;text-align: center">${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td style="text-align: center">
                <p>
                    <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                    ${resource.fixedOffer?string("0.##")}万元</em>
                </p>
            </td>
            <td style="text-align: center">
                <#if  resource.resourceStatus==30>
                    <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)!/>
                    <#if maxOffer??>
                    <p>竞得人：<@userInfo userId=maxOffer.userId /></p>
                    <p>竞得价：${maxOffer.offerPrice}万元</p>
                    </#if>
                </#if>
            </td>
            <td style="text-align: center">
                <a class="btn btn-primary size-S" href="${base}/console/money/money-list?resourceId=${resource.resourceId!}">保证金列表</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
</body>
</html>