<@Head_SiteMash title="" />
    <style type="text/css">
        .apply-content p{
            margin-bottom: 0px;
            font-size: 16px;
            line-height:29px;
            text-indent:2em
        }
        .span-info{
            color: #990000;
            font-weight: 700;
        }
        .import-note p{
            margin-bottom:0px;font-size:15px;
        }
    </style>
    <script type="application/javascript">
        $(document).ready(function () {
        });



        function confirmRefresh(){
            if (confirm("是否确认重新生成！")){

            }
        }
    </script>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><a href="${base}/view?id=${transResource.resourceId}">${transResource.resourceCode}</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">交纳保证金</span>
    </nav>
    <#if _msg??>
        <div class="Huialert Huialert-danger" style="margin: 10px 0px;"><i class="icon-remove"></i>${_msg}</div>
    </#if>
    <#if transResource.regionCode="320503001">
        <div class="row" style="text-align: center;font-weight:700;font-size:18px;margin-top:10px">
            竞买保证金缴款通知单
        </div>
        <div class="row" style="margin: 5px 100px;">
            <div class="apply-content">
                <p style=" text-indent:0em">${UserUtil.getUserName(transResourceApply.userId)}：</p>
                <p >
                    欢迎申请购买地块<span class="span-info">${transResource.resourceCode}</span>，
                    您选择了
                <span class="span-info">
                    <#if transResourceApply.applyType==1>
                        联合竞买
                    <#else>
                        独立竞买
                    </#if>
                </span>
                    方式，请按公告要求于<span class="span-info">${transResource.bzjEndTime?string("yyyy年MM月dd日HH时mm分")}</span>之前，足额向以下账户交纳本次竞买活动的保证金
                <span class="span-info"><#if "CNY"==transResourceApply.moneyUnit>${transResource.fixedOffer!}</span>万元（人民币）<#elseif "USD"==transResourceApply.moneyUnit>${transResource.fixedOfferUsd!}</span>万元（美元）<#elseif "HKD"==transResourceApply.moneyUnit>${transResource.fixedOfferHkd!}</span>万元（港币）</#if>：
                </p>

            </div>
        </div>
    <#else>
        <div class="row" style="text-align: center;font-weight:700;font-size:18px;margin-top:10px">
            履约保证金入账通知单
        </div>
        <div class="row" style="margin: 5px 100px;">
            <div class="apply-content">
                <p style=" text-indent:0em">${UserUtil.getUserName(transResourceApply.userId)}：</p>
                <p >
                    经审核，你单位已具备<span class="span-info">${transResource.resourceCode}</span>地块的竞买资格。请按照苏州工业园区国有建设用地使用权网上挂牌出让公告（<span class="span-info">${ggNum!}</span>）要求，于<span class="span-info">${transResource.bzjEndTime?string("yyyy年MM月dd日HH时mm分")}</span>之前，
                    足额向以下账户交纳本次竞买活动的保证金
                <span class="span-info"><#if "CNY"==transResourceApply.moneyUnit>${transResource.fixedOffer!}</span>万元（人民币）<#elseif "USD"==transResourceApply.moneyUnit>${transResource.fixedOfferUsd!}</span>万元（美元）<#elseif "HKD"==transResourceApply.moneyUnit>${transResource.fixedOfferHkd!}</span>万元（港币）</#if>：

                </p>

            </div>
        </div>

    </#if>


    <div class="row" style="margin: 5px 100px;">
        <table class="table table-border table-bordered">
            <tr>
                <td rowspan="4" style="background-color: #F5F5F5;width:100px;font-size: 16px">收款人</td>
                <td width="120">开户银行：</td>
                <td>${bank.bankName!}</td>
            </tr>
            <tr>
                <td>户名：</td>
                <td>${bank.accountName!}</td>
            </tr>
            <tr>
                <td>帐号：</td>
                <td>
                    <span id="applyAccount">${transBankAccount.accountCode!}</span>
                   <#-- <input type="button" class="btn btn-default size-MINI r" value="复制" style="margin-left: 10px" onclick="window.clipboardData.setData('Text','${transBankAccount.accountCode!}'); ">-->
                    <#--<input id="btnAccount" type="button" class="btn btn-default size-MINI r"
                          style="display: none" value="重新生成" onclick="confirmRefresh()" >-->
                </td>
            </tr>
            <tr>
                <td>竞买流水号：</td>
                <td>${transBankAccount.applyNo!}</td>
            </tr>
        </table>
        <#if transResource.regionCode="320503001">
            <table class="table table-border table-bordered">
                <tr>
                    <td style="background-color: #F5F5F5;font-size:15px;">重要提示</td>
                </tr>
                <tr>
                    <td>
                        <div class="import-note">
                            <p >
                                （1）通知单生成后，请务必仔细阅读并打印通知单相关信息；
                            </p>
                            <p >
                            <span class="span-info">（2）竞买人可选择银行柜台、网上银行等方式缴纳竞买保证金。如选择到银行柜台缴款，请携带本通知单；
                            </span>
                            </p>
                            <p >
                            <span class="span-info">（3）银行转账手续办理完成后，请务必登录系统查看保证金到账情况；跨行转账，由于人民银行数据交换系统导致备注信息丢失，可能导致出让系统无法识别入账，若发生上述情况请及时联系银行或我局工作人员；为避免银行间的结算影响保证金到账通知，最好提前1或2个工作日完成保证金交纳事宜。
                            </span>
                            </p>


                        </div>

                    </td>
                </tr>

            </table>
        <#else>
            <table class="table table-border table-bordered">
                <tr>
                    <td style="background-color: #F5F5F5;font-size:15px;">重要提示</td>
                </tr>
                <tr>
                    <td>
                        <div class="import-note">
                            <p >
                                （1）通知单生成后，请务必仔细阅读并打印通知单相关信息；
                            </p>
                            <p >
                        <span class="span-info">（2）若交纳的保证金为除人民币以外的其它币种，请务必在交纳保证金时或者交纳保证金后到银行柜台提交带有竞买流水号与账号的履约保证金入账通知单；
                        </span>
                            </p>
                            <p >
                        <span class="span-info">（3）银行转账手续办理完成后，请务必登录系统查看保证金到账情况；跨行转账，由于人民银行数据交换系统导致备注信息丢失，可以导致出让系统无法识别入账，若发生上述情况请及时联系银行或交易中心工作人员；为避免银行间的结算影响保证金到账通知，最好能提前1或2个工作日完成保证金交纳事宜；
                        </span>
                            </p>

                            <p >
                                （4）交易完成后，可凭银行转账单和本通知书，申请退款或收据；
                            </p>
                        </div>

                    </td>
                </tr>

            </table>
        </#if>

    </div>
    <div class="row" style="margin: 5px 100px;text-align: center">
        <a class="btn btn-default" href="${base}/view?id=${transResource.resourceId}">返回地块</a>
    </div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>