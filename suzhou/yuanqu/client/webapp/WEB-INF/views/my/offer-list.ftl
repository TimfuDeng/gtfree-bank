<@Head_SiteMash title="我的报价列表" />
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
<#include "../common/head.ftl"/>
<div class="wp">
    <div class="row cl" >
        <div class="col-2" style="padding-right: 10px">
            <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
                <li role="presentation" class="active">
                    <a href="${base}/my/resource-list">
                        <i class="icon-th-large"></i>&nbsp;&nbsp;我的交易
                    <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                    <#if applyCount gt 0>
                        <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                    </#if>
                    </a>
                </li>
                <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息</a></li>
            </ul>
        </div>
        <div class="col-10">
            <nav class="breadcrumb pngfix">
                <i class="iconfont">&#xf012b;</i>
                <a href="${base}/index" class="maincolor">首页</a>
                <span class="c_gray en">&gt;</span>
                <a href="${base}/my/resource-list" class="maincolor">我的竞拍地块</a>
                <span class="c_gray en">&gt;</span><span class="c_gray">我的报价列表</span>
            </nav>

            <form class="navbar-form" id="frmSearch">
                <input type="hidden" name="resourceId" value="${resourceId}">
            </form>
            <table class="table table-border table-bordered table-bg">
                <thead>
                <tr>
                    <td>报价时间</td>
                    <td width="190">报价金额</td>
                    <td width="190">报价类型</td>
                </tr>
                </thead>
                <tbody>
                <#list transResourceOfferPage.items as transResourceOffer>
                <tr>
                    <td class="offerText">
                    ${transResourceOffer.offerTime}
                    </td>
                    <td>
                        <p>
                        ${transResourceOffer.offerPrice}
                            <#if transResourceOffer.offerType==2>
                                平方米
                            <#else>
                                万元
                            </#if>
                        </p>
                    </td>
                    <td>
                        <#if transResourceOffer.offerType==1>
                            <span class="label label-secondary ">限时竞买报价</span>
                        <#elseif transResourceOffer.offerType==2>
                            <span class="label label-secondary ">限价竞拍</span>
                        <#else>
                            <span class="label label-primary ">挂牌报价</span>
                        </#if>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        <@PageHtml pageobj=transResourceOfferPage formId="frmSearch" />

            <script type="text/javascript">
                $(document).ready(function () {
                    $(".offerText").each(function(){
                        var timeStr=$(this).html();
                        var dateObj= new XDate(timeStr*1);
                        $(this).html(dateObj.toString("yyyy年MM月dd日 HH:mm:ss"));
                    });
                });


            </script>
        </div>
    </div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>