<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <style type="text/css">
        .apply-content {
            padding: 0px 100px;
        }

        .apply-title {
            text-align: center;
            font-weight: 700;
            font-size: 24px;
            margin-bottom: 10px;
        }

        .apply-content p {
            margin-bottom: 0px;
            font-size: 16px;
            line-height: 29px;
            text-indent: 2em
        }

        .apply-sucess {
            margin-top: 10px;
            margin-bottom: 10px;
            font-size: 16px;
            line-height: 29px;
            font-weight: 700;
            font-size: 16px;
            text-indent: 0em
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("iframe", window.parent.document).attr("height", document.body.scrollHeight);
        });

        function checkBtn() {
            if ($("#chkApply").prop("checked")) {
                $("#btnYes").removeClass("btn-default");
                $("#btnYes").addClass("btn-primary");
                $("#btnYes").prop("disabled", false);
                $("#btnYes").attr('disabled', false);
            } else {
                $("#btnYes").removeClass("btn-primary");
                $("#btnYes").addClass("btn-default");
                $("#btnYes").attr("disabled", true);
            }
        }

        function resourceApply(resourceId) {
            $("#mainFrame", window.parent.document).attr("src", "${base}/resourceApply/apply-over?resourceId=" + resourceId);
        }

        function toResource(resourceId) {
            var regionCode = $("#regionCode", window.parent.document).val();
            $("#mainFrame", window.parent.document).attr("src", "${base}/resource/index?regionCode=" + regionCode);
        }
    </script>
</head>
<body>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/resource/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><a
            href="${base}/resource/view?resourceId=${transResource.resourceId}">${transResource.resourceCode}</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">竞买申请</span>
    </nav>
    <div class="row" style="border: 1px solid #eeecec;">
        <table align="center" style="width:921px">
            <tbody>
            <tr>
                <td>
                    <img src="${base}/image/jj_notice_top.jpg" height="98">
                </td>
            </tr>
            <tr>
                <td style="background: url(${base}/image/jj_notice_bg.jpg) center repeat-y;">
                    <div class="apply-content">
                        <p>
                        <div class="apply-title">${transRegion.regionName!}国有建设用地使用权挂牌出让竞买申请书</div>
                        </p>
                        <p style=" text-indent:0em">${transRegion.regionName!}公共资源交易中心：</p>
                        <p>经过认真查阅&nbsp;&nbsp;<span style="text-decoration:underline">${transCrgg.ggNum!}</span> &nbsp;
                            ，名称为：<span style="text-decoration:underline">${transResource.resourceCode!}</span>（以下称“该宗地”）的国有建设用地使用权挂牌出让文件，我们明确知晓网上挂牌出让文件之条款，对网上挂牌出让文件无异议并全面接受。
                        </p>

                        <p>我们清楚了解数字证书的相关规定、${transRegion.regionName!}国有建设用地使用权网上交易系统的操作程序及网上挂牌出让的有关规则，
                            现登录${transRegion.regionName!}国有建设用地使用权网上交易系统在互联网竞买申请本宗地的国有建设用地使用权。在交纳竞买保证金前，联合竞买申请的各方（联合人、被联合人）须先行上传相关附件，主要内容包括：股东的名称、个数、各股东出资比例，以便${transRegion.regionName!}公共资源交易中心在成交后进行审核。现我们承诺如下：</p>

                        <p>
                            一、愿意遵守包括《${transRegion.regionName!}国土资源局国有土地使用权挂牌出让公告》（以下称“出让公告”）、《${transRegion.regionName!}国有建设用地使用权网上挂牌出让竞买须知》（下称“竞买须知”）在内的所有网上挂牌出让文件的要求和规定。
                        </p>
                        <p>
                            二、通过${transRegion.regionName!}国有建设用地使用权网上交易系统提交的竞买申请文件，均为我们真实意思表示或经过我们的合法授权，行为所引起的法律后果由我们无条件全面承担。
                        </p>
                        <p style="color: #CC0000;font-weight:700">
                            三、我们承诺具备竞买资格，否则，${transRegion.regionName!}国土资源局有权在我们竞得后撤销我们的竞得资格，不予退还竞买保证金，并由我们承担相应的法律责任。
                        </p>
                        <p style=" text-indent:2em">
                            四、如能竞得，我们保证按照竞买须知规定的时间签订《${transRegion.regionName!}国有建设用地使用权网上挂牌出让成交确认书》和《${transRegion.regionName!}国有建设用地使用权出让合同》，并按竞买须知要求的时间向${transRegion.regionName!}公共资源交易中心缴纳交易服务费，否则，视为我方违约，同意${transRegion.regionName!}国土资源局撤销我们竞得资格，竞买保证金作为违约金不予退还，并将我们的违约行为作为不良记录，限制我们以后参与${transRegion.regionName!}乃至江苏省的国有建设用地使用权的出让活动。
                        </p>
                        <p style=" text-indent:2em">
                            五、如能竞得，保证按竞买须知及出让公告等规定办理相关手续，如需成立新项目公司开发本宗地，成立的新项目公司不改变竞买申请时已经明确的内容，即：新项目公司各股东的名称不更改、股东的个数不增减、各股东的出资比例不变化。否则${transRegion.regionName!}国土资源局有权不予办理相关变更手续。

                        </p>
                        <p style=" text-indent:2em">
                            六、已清晰了解国家[建设部等六部委《关于规范房地产市场外资准入和管理的意见》（建住房〔2006〕171号）]、江苏省和${transRegion.regionName!}关于加强外资进入房地产市场管理、规范房地产市场开发秩序的法律、法规、政策与文件，如能竞得，我们将严格按照法律、法规、政策和文件的要求以及《国有建设用地使用权出让合同》的约定，按期足额交纳土地出让金，按期完成开发建设，不以国家外资管理等理由延迟交纳土地出让金或要求推迟交纳土地出让金的期限，不以拆迁补偿安置等理由拖延开发建设时间，否则${transRegion.regionName!}国土资源局有权通过不予退还竞买保证金、追收违约金、无偿收回本宗地国有建设用地使用权等措施追究我们的法律责任，我们愿意无条件承担由此引发的一切后果并承担全部法律责任。
                        </p>
                        <p style=" text-indent:2em">
                            七、参与本次网上挂牌出让活动所发生的全部费用由我们自行承担。
                        </p>
                        <p style=" text-indent:2em">
                            八、因我们计算机的硬软件或系统网络遭遇故障、安全事故（包括黑客攻击、病毒入侵等）、网络堵塞等导致申请人不能正常登录系统参与网上挂牌出让活动的，由此引起的法律后果由我们自行承担，网上挂牌出让活动不因此而暂停、中止或终止。
                        </p>
                        <p style=" text-indent:2em">
                            在签订本宗地的《国有建设用地使用权出让合同》前，本竞买申请书作为对我们有法律约束力的文件。
                        </p>
                    <#if (applyEnabled??) && applyEnabled>
                        <div class="apply-sucess">
                            <div class="check-box"><input type="checkbox" value="" name="" id="chkApply"
                                 style="height:20px;width: 50px" onclick="checkBtn();">我已经阅读并同意该申请书
                            </div>
                        </div>
                    <#else >
                        <p style="color: #CC0000;font-weight:700">提醒：${msg!}</p>
                    </#if>

                    <#if caEnabled>1437
                        <form name="applyForm" action="${base}/resourceApply/apply-over">
                            <input type="hidden" name="sxaction" value="pkcs1"/>
                            <input type="hidden" name="sxdigest" value="md5"/>
                            <input type="hidden" name="sxoptcertfilter" value="0"/>
                            <input type="hidden" id="sxcertificate" name="sxcertificate" value=""/>
                            <input type="hidden" id="pkcs1" name="pkcs1" value=""/>
                            <input type="hidden" id="sxinput" name="sxinput" value=""/>
                            <input type="hidden" id="certFriendlyName" name="certFriendlyName" value=""/>
                            <input type="hidden" id="certThumbprint" name="certThumbprint" value=""/>
                            <input type="hidden" name="id" value="${transResource.resourceId}">
                        </form>
                    </#if>
                        <div style="text-align: center;">
                        <#if (applyEnabled??)&& applyEnabled>
                            <button class="btn btn-default" disabled="disabled" id="btnYes"
                               <#if !caEnabled>onclick="resourceApply('${transResource.resourceId}')"
                               <#else>onclick="btnSubmit()" </#if>>同意并报名</button>
                        </#if>
                            <button class="btn btn-default" onclick="toResource();">返回地块</button>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="${base}/image/jj_notice_bottom.jpg" height="98">
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</div>
<#if caEnabled>
    <@Ca autoSign=0  />
<script type="text/javascript">
    function btnSubmit() {
        gtmapCA.initializeCertificate(document.all.caOcx);
        var result = gtmapCA.signContent(new Date().toLocaleTimeString(), 1);
        if (result == true) {
            $('#sxcertificate').val(gtmapCA.getCertificateContent());
            $('#certFriendlyName').val(gtmapCA.getCertificateFriendlyUser());
            $('#certThumbprint').val(gtmapCA.getCertificateThumbprint());
            document.applyForm.submit();
        } else {
            alert(gtmapCA.getErrorString(result));
        }
    }
</script>
</#if>

</body>
</html>