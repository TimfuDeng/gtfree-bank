<@Head_SiteMash title="我的竞买地块" />
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
                <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息
                <#assign applyedCount=ResourceUtil.getIsApplyedCountByStauts()>
                <#if applyedCount gt 0>
                    <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyedCount}</span>
                </#if>
                </a></li>
            </ul>
        </div>
        <div class="col-10">
            <nav class="breadcrumb pngfix">
                <i class="iconfont">&#xf012b;</i>
                <a href="${base}/index" class="maincolor">首页</a>
                <span class="c_gray en">&gt;</span><span class="c_gray">我的竞拍地块</span>
            </nav>
            <form class="navbar-form" id="frmSearch">
            </form>

            <table class="table table-border table-bordered table-bg">
                <thead>
                <tr>
                    <td>地块描述</td>
                    <td width="160">挂牌时间</td>
                    <td width="160">购买状态</td>
                    <td>出资比例(%)</td>
                    <td width="60">附件材料</td>
                    <td width="200">操作</td>
                </tr>
                </thead>
                <tbody>
                <#list transResourceApplyPage.items as resourceApply>
                    <#assign resource=ResourceUtil.getResource(resourceApply.resourceId)!>
                    <#if resource?? && resource.resourceId??>
                    <tr>
                        <td>
                            <a href="${base}/view?id=${resource.resourceId}" target="frm_${resource.resourceId}">
                                <p>名称：${resource.resourceCode}
                                    <#if resource.resourceEditStatus==2 >
                                        <#if resource.resourceStatus==1>
                                            <span class="label label-success">正在竞价</span>
                                        <#elseif resource.resourceStatus==10 >
                                            <span class="label label-success">正在挂牌</span>
                                            <!--资格审核-->
                                            <#if resourceApply.qualifiedId??>
                                                <#assign resourceBuyQualified=ResourceUtil.getTransBuyQualifiedById(resourceApply.qualifiedId)!>
                                                <#if resourceBuyQualified?? && resourceBuyQualified.qualifiedStatus=0>
                                                    <span class="label label-danger">资格审核未通过</span>
                                                <#--<#else>
                                                    <span class="label label-success">资格审核通过</span>-->
                                                </#if>
                                            </#if>
                                        <#else >
                                            <span class="label label-success">正在公告</span>
                                        </#if>
                                    <#elseif resource.resourceEditStatus==9 >
                                        <#if resource.resourceStatus==30 >
                                            <!--成交审核过-->
                                            <#if resource.resourceVerifyId??>
                                                <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
                                                <!--成交审核未通过-->
                                                <#if resouerceVerify.verifyStatus==0>
                                                    <span class="label label-danger">审核未通过</span>
                                                <#else>
                                                    <span class="label label-success">已成交</span>
                                                </#if>
                                            <#else>
                                                <span class="label label-success">已成交</span>
                                            </#if>
                                        <#elseif resource.resourceStatus==31 >
                                            <span class="label label-danger">已流拍</span>
                                        </#if>
                                    </#if>
                                </p>
                                <p>坐落：${resource.resourceLocation}</p>

                            </a>
                        </td>
                        <td>
                            <p>
                                开始：${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}
                            </p>
                            <p>
                                结束：${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}
                            </p>
                        </td>
                        <td>
                            <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)!/>
                            <#if resourceApply.applyStep==1||resourceApply.applyStep==2 >
                                <span class="label label-default">未选择银行</span>
                                <span class="label label-default">未缴纳保证金</span>
                            <#elseif resourceApply.applyStep==3&&resourceApply.fixedOfferEnough==false >
                                <span class="label label-success">已选择银行</span>
                                <span class="label label-default">未缴足保证金</span>
                            <#elseif resourceApply.applyStep==3 >
                                <span class="label label-success">已选择银行</span>
                                <span class="label label-default">未缴纳保证金</span>
                            <#elseif resourceApply.applyStep==4&&resourceApply.fixedOfferEnough >
                                <span class="label label-success">已选择银行</span>
                                <span class="label label-success">已缴纳保证金</span>
                                <#if resourceApply.applyStep==4&&resourceApply.fixedOfferBack==true >
                                    <span class="label label-success">已退还保证金</span>
                                <#else>
                                    <#if maxOffer??>
                                        <#if maxOffer.userId??&&maxOffer.userId!=resourceApply.userId>
                                            <span class="label label-default">未退还保证金</span>
                                        </#if>
                                    </#if>

                                </#if>
                                <#if  resource.resourceStatus==30>
                                    <#if maxOffer??&&(maxOffer.userId==resourceApply.userId)>
                                        <!--成交审核过-->
                                        <#if resource.resourceVerifyId??>
                                            <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
                                            <!--成交审核未通过-->
                                            <#if resouerceVerify.verifyStatus==0>
                                                <#if resourceApply.fixedOfferBack!=true >
                                                    <span class="label label-default">未退还保证金</span>
                                                </#if>
                                            <#else>
                                                <span class="label label-danger">已转入国库</span>
                                            </#if>
                                        <#else>
                                            <span class="label label-danger">已转入国库</span>
                                        </#if>
                                    </#if>
                                </#if>
                            </#if>

                        </td>
                        <#assign scale=ResourceUtil.getAllScale(resourceApply.applyId)!>
                        <#if scale??>
                            <td style="text-align:center">
                            ${100-scale?number}
                            </td>
                        <#else>
                            <td>
                                100
                            </td>
                        </#if>
                        <td style="text-align: center">
                            <#if resource.resourceEditStatus gt 2 >
                                <p><a class="btn btn-link size-MINI" href="${base}/my/attachment-list?resourceId=${resource.resourceId}">查看</a></p>
                            </#if>
                        </td>
                        <td style="text-align: center">
                        <#-- <#if resource.resourceEditStatus==2 >
                             <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px"  href="${base}/console/apply-bank?id=${resource.resourceId}" target="frm_${resource.resourceId}">选择银行并交纳保证金>>></a></p>
                         </#if>-->
                            <#if resourceApply.applyStep gt 0 >
                                <#if resource.regionCode="320503001">
                                    <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/jmsqs.f?resourceId=${resource.resourceId}">竞买申请书>>></a></p>
                                <#else>
                                    <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/jmsqs.f?resourceId=${resource.resourceId}">竞买承诺书>>></a></p>
                                </#if>


                            </#if>
                            <#if resourceApply.applyStep gt 1 >
                                <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/pay-list?resourceId=${resource.resourceId}">查看保证金>>></a></p>
                            </#if>
                            <#if resourceApply.applyStep == 3 >
                                <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/offer-list?resourceId=${resource.resourceId}">查看我的报价>>></a></p>
                            </#if>
                            <#if resource.resourceStatus==30 >
                                <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)/>
                                <#assign account = ResourceUtil.getAccountByApplyId(resourceApply.applyId)!/>
                                <!--成交审核过-->
                                <#if resource.resourceVerifyId??>
                                    <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
                                    <!--成交审核未通过-->
                                    <#if resouerceVerify.verifyStatus==1>
                                        <#if UserUtil.isCurrentUser(maxOffer.userId)>
                                            <#if resource.regionCode=="320503001">
                                                <p><a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a></p>

                                            <#else>
                                                <p><a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a></p>

                                            </#if>

                                        <#else>
                                            <#if account.accountCode??>
                                                <p><a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款申请书>>></a></p>
                                            </#if>
                                        </#if>
                                    <#else>
                                        <p><a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款申请书>>></a></p>
                                    </#if>
                                <#else>
                                    <#if UserUtil.isCurrentUser(maxOffer.userId)>
                                        <#if resource.regionCode=="320503001">
                                            <p><a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a></p>
                                        <#else>
                                            <p><a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a></p>
                                        </#if>
                                    <#else>
                                        <#if account.accountCode??>
                                            <p><a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款申请书>>></a></p>
                                        </#if>
                                    </#if>
                                </#if>
                            </#if>

                        </td>
                    </tr>
                    </#if>
                </#list>
                </tbody>
            </table>
        <@PageHtml pageobj=transResourceApplyPage formId="frmSearch" />
        </div>
    </div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>