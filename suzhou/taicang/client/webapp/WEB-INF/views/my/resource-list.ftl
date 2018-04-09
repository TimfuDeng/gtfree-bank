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
                <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息</a></li>
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
                                        <#else >
                                            <span class="label label-success">正在公告</span>
                                        </#if>
                                    <#elseif resource.resourceEditStatus==9 >
                                        <#if resource.resourceStatus==30 >
                                            <span class="label label-danger">已成交</span>
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
                            <#if resourceApply.applyStep==1 >
                                <span class="label label-default">未选择银行</span>
                                <span class="label label-default">未缴纳保证金</span>
                            <#elseif resourceApply.applyStep==2 >
                                <span class="label label-success">已选择银行</span>
                                <span class="label label-default">未缴纳保证金</span>
                            <#elseif resourceApply.applyStep==3 >
                                <span class="label label-success">已选择银行</span>
                                <span class="label label-success">已缴纳保证金</span>
                            </#if>

                        </td>
                        <td style="text-align: center">
                            <#if resource.resourceEditStatus gt 2 >
                                <p><a class="btn btn-link size-MINI" href="${base}/my/attachment-list?resourceId=${resource.resourceId}">查看</a></p>
                            </#if>
                        </td>
                        <td style="text-align: center">
                            <#if resource.resourceEditStatus==2 >
                                <#if resourceApply.trialType??&&resourceApply.trialType=='COMMIT_TO_TRIAL'>
                                    <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px"  href="${base}/console/apply-bzj?id=${resource.resourceId}" target="frm_${resource.resourceId}">竞买资格审核中>>></a></p>
                                <#elseif resourceApply.trialType??&&resourceApply.trialType=='PASSED_TRIAL'>
                                    <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px"  href="${base}/console/apply-bzj?id=${resource.resourceId}" target="frm_${resource.resourceId}">查看保证金入账通知单>>></a></p>
                                <#else>
                                    <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px"  href="${base}/console/apply-bank?id=${resource.resourceId}" target="frm_${resource.resourceId}">选择银行并交纳保证金>>></a></p>
                                </#if>
                            </#if>
                            <#if resourceApply.applyStep gt 0 >
                                <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/jmsqs.f?resourceId=${resource.resourceId}">竞买申请书>>></a></p>
                            </#if>
                            <#if resourceApply.applyStep gt 1 >
                                <#if resource.qualificationType??&&resource.qualificationType=='POST_TRIAL'>
                                    <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/pay-list?resourceId=${resource.resourceId}">查看保证金>>></a></p>
                                <#else>
                                    <#if resourceApply.trialType??&&resourceApply.trialType=='PASSED_TRIAL'>
                                        <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/pay-list?resourceId=${resource.resourceId}">查看保证金>>></a></p>
                                    </#if>
                                </#if>
                            </#if>
                            <#if resourceApply.applyStep == 3 >
                                <p style="margin-bottom:3px"><a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/offer-list?resourceId=${resource.resourceId}">查看我的报价>>></a></p>
                            </#if>
                            <#if resource.resourceStatus==30 >
                                <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)/>
                                <#if UserUtil.isCurrentUser(maxOffer.userId)>
                                    <#if resource.qualificationType == 'PRE_TRIAL'>
                                        <p><a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a></p>
                                    <#else>
                                        <#if resourceApply.trialType?? && resourceApply.trialType=='PASSED_TRIAL'>
                                            <p><a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a></p>
                                        <#elseif resourceApply.trialType?? && resourceApply.trialType=='FAILED_TRIAL'>
                                            <p><a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);" title="${resourceApply.trialReason!}">资格审核不通过</a></p>
                                        <#else>
                                            <p><a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a></p>
                                        </#if>
                                    </#if>
                                <#else>
                                    <p><a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款申请书>>></a></p>
                                </#if>
                            </#if>
                            <#if resource.resourceStatus==31>
                                <p><a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款说明书>>></a></p>
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