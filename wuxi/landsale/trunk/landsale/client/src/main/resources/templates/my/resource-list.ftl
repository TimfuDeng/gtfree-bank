<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>我的竞买地块</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
    <style type="text/css">
        table p {
            margin-bottom: 0px;
        }

        thead td {
            text-align: center !important;
            background-color: #f5fafe;
        }

        .table-bordered td {
            border-left: 1px solid #ddd;
            border-top: 1px solid #ddd;
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
        }
    </style>
    <script type="text/javascript">
        $(function () {
        });
        function viewResource(resourceUrl) {
            $("#mainFrame", window.parent.document).attr("src", resourceUrl);
        }
    </script>
</head>
<body>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <#--<span class="c_gray en">&gt;</span><a href="${base}/resource/index" class="maincolor">首页</a>-->
        <span class="c_gray en">&gt;</span><a href="${base}/my/menu" ">我的交易</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">我的保证金</span>
    </nav>
    <form class="navbar-form" id="frmSearch" action="${base}/my/resource-list">
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
            <#assign resourceVerify=ResourceUtil.getTransResourceVerify(resource.resourceId)/>
            <#if resource?? && resource.resourceId??>
            <tr>
                <td>
                    <a onclick="viewResource('${base}/resource/view?resourceId=${resource.resourceId}');"
                       target="frm_${resource.resourceId}" style="z-index: 1;">
                        <p>名称：${resource.resourceCode}
                            <!--地块已发布 resourceEditStatus=2-->
                            <#if resource.resourceEditStatus==2 >
                                <!--地块正在竞价 resourceStatus=1-->
                                <#if resource.resourceStatus==1>
                                    <span class="label label-success">正在竞价</span>
                                <#elseif resource.resourceStatus==10 >
                                    <span class="label label-success">正在挂牌</span>
                                <#elseif resource.resourceStatus==30 >
                                    <span class="label label-danger">已成交</span>
                                <#elseif resource.resourceStatus==31 >
                                    <span class="label label-danger">已流拍</span>
                                <#elseif resource.resourceStatus==20 >
                                    <span class="label label-success">正在公告</span>
                                </#if>
                            <!--地块已结束 resourceEditStatus=9-->
                            <#elseif resource.resourceEditStatus==9 >
                                <#if resource.resourceStatus==30 >
                                <#--判断是否通过审核-->
                                    <#assign resouerceVerify = ResourceUtil.getTransResourceVerify(resource.resourceId)!/>
                                    <!--获取成交审核信息-->
                                    <#if resouerceVerify != null>
                                        <!--审核通过-->
                                        <#if resouerceVerify.verifyStatus == 1>
                                            <span class="label label-danger">已成交</span>
                                        <#else>
                                            <span class="label label-danger">已结束</span>
                                        </#if>
                                    <#else>
                                        <span class="label label-danger">已结束</span>
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
                    <#elseif resourceApply.applyStep==3 >
                        <span class="label label-success">已选择银行</span>
                        <span class="label label-default">未缴纳保证金</span>
                    <#elseif resourceApply.applyStep==3&&resourceApply.fixedOfferEnough==true >
                        <span class="label label-success">已选择银行</span>
                        <span class="label label-default">未缴足保证金</span>
                    <#elseif resourceApply.applyStep==4 >
                        <span class="label label-success">已选择银行</span>
                        <span class="label label-success">已缴纳保证金</span>
                    </#if>

                </td>
                <td style="text-align: center">
                    <#if resource.resourceEditStatus gt 1 >
                        <p><a class="btn btn-link size-MINI"
                              href="${base}/my/attachment?userId=${resourceApply.userId}&resourceId=${resource.resourceId}">查看</a></p>
                    </#if>
                </td>
                <td style="text-align: center">
                    <#if resource.resourceEditStatus==2 >
                        <p style="margin-bottom:3px">
                            <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/resourceApply/apply-bank?resourceId=${resource.resourceId}">选择银行并交纳保证金>>></a>
                        </p>
                    </#if>
                    <!--表示已经报名的都可以查看竞买申请书-->
                    <#if resourceApply.applyStep gt 0 >
                        <p style="margin-bottom:3px">
                            <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/jmsqs.f?resourceId=${resource.resourceId}">竞买申请书>>></a>
                        </p>
                    </#if>
                    <!--表示已经报名并且已选择银行的，可以查看保证金是否到账-->
                    <#if resourceApply.applyStep gt 1 >
                        <!--地块后审 或者 审核通过的-->
                        <#assign transBuyQualified=ResourceUtil.getTransBuyQualifiedById(resourceApply.applyId)!/>
                        <#if resource.beforeBzjAudit==0||transBuyQualified.qualifiedStatus==1>
                            <p style="margin-bottom:3px">
                                <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/pay-list?resourceId=${resource.resourceId}">查看保证金>>></a>
                            </p>
                        </#if>
                    </#if>
                    <!--表示已经报名并且已缴纳保证金-->
                    <#if resourceApply.applyStep == 4 >
                        <p style="margin-bottom:3px">
                            <a class="btn btn-primary size-MINI" style="width:180px" href="${base}/my/offer-list?resourceId=${resource.resourceId}">查看我的报价>>></a>
                        </p>
                    </#if>
                    <!--表示地块已成交-->
                    <#if resource.resourceStatus==30 >
                        <#assign maxOffer=PriceUtil.getMaxOffer(resource.resourceId)/>
                        <!--当前用户是地块竞得人-->
                        <#if UserUtil.isCurrentUser(maxOffer.userId)>
                           <#-- <!--表示地块是前审&ndash;&gt;
                            <#if resource.beforeBzjAudit==1>
                                <p>
                                    <a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>
                                </p>
                            <#else>-->
                            <#--判断是否存在最高限价-->
                            <#--无最高限价-->
                            <#if resource.maxOfferExist == 0>
                                <#--判断 后审是否通过-->
                                <#if resourceVerify != null>
                                    <!--表示地块是后审，并且审核通过-->
                                    <#if resourceVerify.verifyStatus==1>
                                        <p>
                                            <a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>
                                        </p>
                                        <!--表示地块是后审，并且审核未通过-->
                                    <#elseif resourceVerify.verifyStatus==0>
                                        <p>
                                            <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);" title="${resourceApply.trialReason!}">资格审核不通过</a>
                                        </p>
                                    <#else>
                                        <p>
                                            <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                        </p>
                                    </#if>
                                <#else>
                                    <p>
                                        <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                    </p>
                                </#if>
                            <#--存在最高限价-->
                            <#else>
                                <#--有最高限价 但是没达到 正常成交了-->
                                <#if resource.maxOffer gt maxOffer.offerPrice >
                                    <!--表示地块是后审，并且审核通过-->
                                    <#if resourceVerify != null>
                                        <#if resourceVerify.verifyStatus==1>
                                            <p>
                                                <a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>
                                            </p>
                                            <!--表示地块是后审，并且审核未通过-->
                                        <#elseif resourceVerify.verifyStatus==0>
                                            <p>
                                                <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);" title="${resourceApply.trialReason!}">资格审核不通过</a>
                                            </p>
                                        <#else>
                                            <p>
                                                <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                            </p>
                                        </#if>
                                    <#else>
                                        <p>
                                            <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                        </p>
                                    </#if>
                                <#else>
                                    <#--判断最高限价后的成交方式-->
                                    <#--竞价方式 一次报价-->
                                    <#if resource.maxOfferChoose?? && resource.maxOfferChoose.code==1>
                                        <!--一次报价目前采用后审形式,仍保留原有判断逻辑-->
                                        <#--<#assign oneTarget = ResultUtil.getOneTargetByTransResourceId(resource.resourceId)/>-->
                                        <#--<#if oneTarget.isStop?? && oneTarget.isStop == 1>-->
                                            <#--<p>-->
                                                <#--<a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>-->
                                            <#--</p>-->
                                        <#--<#else>-->
                                            <#--<p>-->
                                                <#--<a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>-->
                                            <#--</p>-->
                                        <#--</#if>-->
                                        <#if resourceVerify != null>
                                            <#if resourceVerify.verifyStatus==1>
                                                <p>
                                                    <a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>
                                                </p>
                                                <!--表示地块是后审，并且审核未通过-->
                                            <#elseif resourceVerify.verifyStatus==0>
                                                <p>
                                                    <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);" title="${resourceApply.trialReason!}">资格审核不通过</a>
                                                </p>
                                            <#else>
                                                <p>
                                                    <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                                </p>
                                            </#if>
                                        <#else>
                                            <p>
                                                <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                            </p>
                                        </#if>
                                    <#--判断最高限价后的 竞价方式 摇号-->
                                    <#elseif resource.maxOfferChoose?? && resource.maxOfferChoose.code==2>
                                        <!--摇号目前采用后审形式,仍保留原有判断逻辑-->
                                        <#--<#assign yhResult = ResultUtil.getYHResultByResourceId(resource.resourceId)/>-->
                                        <#--<#if yhResult != null && yhResult.yhPostStatus?? && yhResult.yhPostStatus == 1>-->
                                            <#--<p>-->
                                                <#--<a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>-->
                                            <#--</p>-->
                                        <#--<#else>-->
                                            <#--<p>-->
                                                <#--<a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>-->
                                            <#--</p>-->
                                        <#--</#if>-->
                                        <#if resourceVerify != null>
                                            <#if resourceVerify.verifyStatus==1>
                                                <p>
                                                    <a class="btn btn-success size-MINI" style="width:180px" href="${base}/my/offersuccess?resourceId=${resource.resourceId}">成交通知书>>></a>
                                                </p>
                                                <!--表示地块是后审，并且审核未通过-->
                                            <#elseif resourceVerify.verifyStatus==0>
                                                <p>
                                                    <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);" title="${resourceApply.trialReason!}">资格审核不通过</a>
                                                </p>
                                            <#else>
                                                <p>
                                                    <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                                </p>
                                            </#if>
                                        <#else>
                                            <p>
                                                <a class="btn btn-danger size-MINI" style="width:180px" href="javascript:void(0);">成交确认资格审核中...</a>
                                            </p>
                                        </#if>
                                    <#else>

                                    </#if>
                                </#if>
                            </#if>
                            <#--</#if>-->
                            <!--当前用户不是地块竞得人。则退款申请书-->
                        <#else>
                            <p>
                                <#if resourceApply.applyStep == 4>
                                    <a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款申请书>>></a>
                                </#if>
                            </p>
                        </#if>
                    <#elseif resource.resourceStatus==31>
                        <#if resourceApply.applyStep == 4>
                            <a class="btn btn-danger size-MINI" style="width:180px" href="${base}/my/offerfail.f?resourceId=${resource.resourceId}">退款申请书>>></a>
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
</body>
</html>