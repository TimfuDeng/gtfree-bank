<@Head_SiteMash title="" />
    <style type="text/css">
        .apply-content{
            padding:0px 100px;
        }
        .apply-title{
            text-align: center;font-weight: 700;font-size: 24px;
            margin-bottom: 10px;
        }
        .apply-content p{
            margin-bottom: 0px;
            font-size: 16px;
            line-height:29px;
            text-indent:2em
        }
        .apply-sucess{
            margin-top:10px;
            margin-bottom: 10px;
            font-size: 16px;
            line-height:29px;
            font-weight: 700;font-size: 16px;
            text-indent:0em
        }
    </style>
    <script>

        function checkBtn(){
            if($("#chkApply").prop("checked")){
                $("#btnYes").removeClass("btn-default");
                $("#btnYes").addClass("btn-primary");
                $("#btnYes").prop("disabled",false);
                $("#btnYes").attr('disabled', false);
            }else{
                $("#btnYes").removeClass("btn-primary");
                $("#btnYes").addClass("btn-default");
                $("#btnYes").attr("disabled",true);
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
        <span class="c_gray en">&gt;</span><span class="c_gray">竞买承诺书</span>
    </nav>
    <div class="row" style="border: 1px solid #eeecec;">
        <table align="center" style="width:921px">
            <tbody>
                <tr>
                    <td>
                        <img src="${base}/static/image/jj_notice_top.jpg"  height="98">
                    </td>
                </tr>
                <tr>
                    <td style="background: url(${base}/static/image/jj_notice_bg.jpg) center repeat-y;">
                        <div class="apply-content">

                            <!-- 国土环保局和土储-->
                        <#if transResource.regionCode="320503001">

                            <p><div class="apply-title">${RegionUtil.getDefaultRegionName()}国有建设用地使用权挂牌出让竞买申请书（请仔细阅读）</div></p>
                            <p style=" text-indent:0em">${RegionUtil.getDefaultRegionName()}国土环保局：</p>
                            <p>
                                在切实遵守中华人民共和国和江苏省、苏州市有关土地使用权出让规定前提下，经过实地踏勘编号为&nbsp;<span style="text-decoration:underline">${transResource.resourceCode!}</span>&nbsp;号地块，
                                并认真审阅《江苏省国有建设用地使用权网上交易规则》（以下简称《规则》）和该地块挂牌出让文件后，接受出让文件的全部条款及要求。我们理解并同意遵守《规则》和出让文件的要求和规定，
                                现申请通过苏州工业园区国有建设用地使用权网上出让系统（工业）参与竞买该地块国有建设用地使用权。
                            </p>
                            <p>我（单位）同意在参加竞价前按规定支付本次竞价履约保证金&nbsp;<span style="text-decoration:underline">${transResource.fixedOffer!}</span>&nbsp;万元（大写人民币 <span id="price" value="${transResource.fixedOffer*10000}"></span>）。</p>
                            <p>如能竞得，我（单位）保证按规定时间签订《国有建设用地使用权网上挂牌出让成交确认书》、《苏州工业园区产业发展协议》和《国有建设用地使用权出让合同》，并按出让文件的要求付清土地出让金。
                                否则，属我（单位）违约，按本次挂牌出让文件相关规定处置，并由我（单位）承担由此产生的违约赔偿等法律责任。本申请书将作为与你局签订的《国有建设用地使用权出让合同》的附件，同样具有法律效力。
                            </p>

                        <#else>
                            <p><div class="apply-title">${RegionUtil.getDefaultRegionName()}国有建设用地使用权挂牌出让竞买承诺书（请仔细阅读）</div></p>
                            <p style=" text-indent:0em">${RegionUtil.getDefaultRegionName()}国土环保局：</p>
                            <p>在切实遵守中华人民共和国和江苏省、苏州市有关土地使用权出让规定前提下，经过实地踏勘编号为&nbsp;<span style="text-decoration:underline">${transResource.resourceCode!}</span>&nbsp;号地
                                块，并认真审阅《江苏省国有建设用地使用权网上交易规则》和该地块挂牌文件后，我（单位）愿意按照出让文件的规定参加该地块土地使用权挂牌/拍卖，接受出让文件的全部条款及要求。</p>
                            <p>现申请参加苏州工业园区国有建设用地网上出让系统举行的国有建设用地使用权挂牌/拍卖出让竞价，并同意按照规定支付保证金。</p>
                            <p>如能竞得，我（单位）保证2个工作日内向苏州工业园区土地储备中心提交竞买须知要求的相关书面材料进行竞买资格审查，审查通过后2个工作日内与出让方签订《国有建设用地使用权网上挂牌
                                出让成交确认书》及《国有建设用地使用权出让合同》并切实履行合同，按出让文件的要求、出让合同的约定支付土地成交价款。否则，属我（单位）违约，贵局有权不退还保证金，并向我（单位）追究相关法律责任。</p>
                            <p>我（单位）清楚了解数字证书的相关规定、苏州工业园区国有建设用地使用权网上出让系统的操作程序及网上挂牌出让的有关规则，通过苏州工业园区国有建设用地使用权网上出让系统提交的竞买
                                申请文件，均为我（单位）真实意思表示，具备法律效力，我（单位）通过CA数字证书在系统实施的所有行为，均视为我（单位）真实意思表示或经过我（单位）的合法授权，行为所引起的法律后
                                果由我（单位）无条件全面承担。</p>
                            <p>因我（单位）计算机的硬软件或系统网络遭遇故障、安全事故（包括黑客攻击、病毒入侵等）、网络堵塞等导致不能正常登陆系统参与网上挂牌出让活动的，由此引起的法律后果由我（单位）自行承担，网上挂牌出让活动不因此而暂停、中止或终止。</p>
                            <p>我（单位）承诺具备挂牌文件规定的竞买资格以及该地块对竞买人资格的特别说明，保证所提交的资料真实合法有效，并承担相应的法律责任。</p>

                        </#if>
                        <#if (applyEnabled??)&& applyEnabled>
                            <div class="apply-sucess">
                                <div class="check-box"><input type="checkbox" value="" name="" id="chkApply" style="height:20px;width: 50px" onclick="checkBtn();">我已经阅读并同意</div>
                            </div>
                        <#else >
                            <p style="color: #CC0000;font-weight:700">提醒：${msg!}</p>
                        </#if>


                            <#if WebUtil.isCaEnabled()>
                                <form name="applyForm" action="${base}/console/apply-over">
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

                                <a class="btn btn-default" disabled="disabled" id="btnYes" <#if !WebUtil.isCaEnabled()>href="javascript:void(0)" onclick="checkResource('${base}/console/apply-over?id=${transResource.resourceId}');"<#else>onclick="return btnSubmit(this)" </#if>>同意并报名</a>
                                </#if>
                                <a class="btn btn-default" href="${base}/view?id=${transResource.resourceId}" >返回地块</a>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="${base}/static/image/jj_notice_bottom.jpg" height="98">
                    </td>
                </tr>
            </tbody>
        </table>
        <input type="hidden" id="resourceId" value="${transResource.resourceId}">
    </div>
</div>
<#if WebUtil.isCaEnabled()>
    <@Ca autoSign=0  />
<script>
    function btnSubmit(){
        gtmapCA.initializeCertificate(document.all.caOcx);
        var result = gtmapCA.signContent(new Date().toLocaleTimeString(),1);
        if(result==true){
            $('#sxcertificate').val(gtmapCA.getCertificateContent());
            $('#certFriendlyName').val(gtmapCA.getCertificateFriendlyUser());
            $('#certThumbprint').val(gtmapCA.getCertificateThumbprint());
            document.applyForm.submit();
        }else{
            alert(gtmapCA.getErrorString(result));
        }
    }
</script>
</#if>

<script>
    function checkResource(obj){

        var objUrl=obj;
        var resourceId=$("#resourceId").val();
        $.ajax({
            type: "POST",
            url: "/client/console/apply/resourceCheck",
            data: {id:resourceId},
            cache:false,
            success: function(data){

                if (data==false){
                    //alert("")
                    confirm("当前地块已经被中止！");
                    return false;
                }else{
                    if(objUrl!=undefined){
                        window.location.href=objUrl;
                    }

                    return true;
                }
            }

        });
    }

</script>
<script type="text/javascript">

    $(document).ready(function () {
        if(undefined!=$("#price").attr("value")){
            var value=  $("#price").attr("value");
            $("#price").html(Chinese(value));
        }
    });

    function Chinese(num) {
        if (!/^\d*(\.\d*)?$/.test(num)) {
            alert("你输入的不是数字，请重新输入!");
            return false;
        }
        var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
        var BB = new Array("", "拾", "佰", "仟", "万", "亿", "点", "");
        var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = "";
        for (var i = a[0].length - 1; i >= 0; i--) {
            switch (k) {
                case 0 :
                    re = BB[7] + re;
                    break;
                case 4 :
                    if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0]))
                        re = BB[4] + re;
                    break;
                case 8 :
                    re = BB[5] + re;
                    BB[7] = BB[5];
                    k = 0;
                    break;
            }
            if (k % 4 == 2 && a[0].charAt(i) == "0" && a[0].charAt(i + 2) != "0") re = AA[0] + re;
            if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re;
            k++;
        }
        if (a.length > 1) {
            re += BB[6];
            for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)];
        }
        return re;
    }

</script>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>