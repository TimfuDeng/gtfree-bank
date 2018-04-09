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
    </style>
    <script type="text/javascript">
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
    <#if transResourceApply.trialType?? && transResourceApply.trialType == 'PASSED_TRIAL'>
        <div style="font-weight: bolder;font-size: 20px;color: red;">
            恭喜您已通过资格审核!
        </div>
    </#if>
    <div class="row" style="text-align: center;font-weight:700;font-size:18px;margin-top:10px">
        保证金入账通知单
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
            <span class="span-info">${transResource.fixedOffer}</span>万元（人民币）：
            </p>

        </div>
    </div>
    <div class="row" style="margin: 5px 100px;">
        <table class="table table-border table-bordered">
            <tr>
                <td rowspan="5" style="background-color: #F5F5F5;width:100px;font-size: 16px">收款人</td>
                <td width="120">开户银行：</td>
                <td>${bank.bankName}</td>
            </tr>
            <tr>
                <td>户名：</td>
                <td>${bank.accountName}</td>
            </tr>
            <tr>
                <td>帐号：</td>
                <td>
                    <span id="applyAccount">${transBankAccount.accountCode!}</span>
                    <input type="button" class="btn btn-default size-MINI r" value="复制" style="margin-left: 10px" onclick="window.clipboardData.setData('Text','${transBankAccount.accountCode!}'); ">
                    <input id="btnAccount" type="button" class="btn btn-default size-MINI r"
                          style="display: none" value="重新生成" onclick="confirmRefresh()" >
                </td>
            </tr>
            <tr>
                <td>流水号：</td>
                <td>${transBankAccount.applyNo!}</td>
            </tr>
            <tr>
                <td>银行联系电话：</td>
                <td>${bank.telephone!}</td>
            </tr>
        </table>
        <table class="table table-border table-bordered">
            <tr>
                <td style="background-color: #F5F5F5;">重要提示</td>
            </tr>
            <tr>
                <td>
                    <p style="margin-bottom:0px">
                        （1）通知单生成后，请务必仔细阅读并打印通知单相关信息；
                    </p>
                    <p style="margin-bottom:0px">
                        <span class="span-info">（2）银行转账手续办理完成后，请务必登录系统查看保证金到账情况；跨行转账，由于人民银行数据交换系统导致备注信息丢失，可以导致交易系统无法识别入账，若发生上述情况请及时联系银行或交易中心工作人员；为避免银行间的结算影响保证金到账通知，最好能提前1或2个工作日完成保证金交纳事宜；
                        </span>
                    </p>
                    <p style="margin-bottom:0px">
                        （3）交易完成后，可凭银行转账单和本通知书，申请退款或收据；
                    </p>
                </td>
            </tr>

        </table>
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