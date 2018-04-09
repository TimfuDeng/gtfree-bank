<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国有建设用地网上出让系统</title>
    <link rel="stylesheet" href="${base}/css/Login.css">

    <script src="${base}/thridparty/jquery/jquery.min.js"></script>
    <script src="${base}/js/jq-watermark.js"></script>
    <script src="${base}/js/DD_belatedPNG_0.0.8a-min.js"></script>
</head>

<body class="bg" style="overflow:hidden">

<!-- 背景 -->
<div class="bodyBg" >
    <div class="loginContent">
        <!-- LOGO -->
        <h1 href="#" class="LOGO">
        </h1>
        <!--end LOGO -->
        <!-- 登录 -->
        <div class="LoginPosition">
            <div class="Login">
                <div id="normalLoginTab" class="loginForm">
                    <form name="loginForm" method="post" action="<#if caEnabled>${base}/calogin</#if>">
                    <#--<input type="hidden" id="username" name="username" value="0">-->
                        <input type="hidden" id="savelogin" name="savelogin" value="0">
                        <input type="hidden" id="url2" name="url2" value="">
                        <!-- 普通密码登录 -->
                    <#if !caEnabled>
                        <div id="idInputLine" class="loginFormIpt showPlaceholder">
                            <b class="ico ico-uid"></b>
                            <input class="formIpt" tabindex="1" title="请输入帐号" id="idInput" name="username" type="text"
                                   maxlength="50" value="" autocomplete="off">
                        </div>
                        <div id="normalLogin">
                            <div id="pwdInputLine" class="loginFormIpt showPlaceholder">
                                <b class="ico ico-pwd"></b>
                                <input class="formIpt" tabindex="2" title="请输入密码" id="pwdInput" name="password"
                                       type="password">
                            </div>
                            <div class="loginFormCheck autoLogin-checked">
                                <div id="lfAutoLogin" class="loginFormCheckInner">
                                    <b class="ico ico-checkbox"></b>
                                    <label id="remAutoLoginTxt" for="remAutoLogin">
                                        <input tabindex="3" title="记住密码" class="loginFormCbx" type="checkbox"
                                               id="remAutoLogin">
                                        记住密码</label>
                                </div>
                            </div>

                        </div>
                    <#else>
                        <input type="hidden" name="sxaction" value="pkcs1"/>
                        <input type="hidden" name="sxdigest" value="md5"/>
                        <input type="hidden" name="sxoptcertfilter" value="0"/>
                        <input type="hidden" id="sxcertificate" name="sxcertificate" value=""/>
                        <input type="hidden" id="pkcs1" name="pkcs1" value=""/>
                        <input type="hidden" id="sxinput" name="sxinput" value=""/>
                        <input type="hidden" id="certFriendlyName" name="certFriendlyName" value=""/>
                        <input type="hidden" id="certThumbprint" name="certThumbprint" value=""/>
                        <div align="center"><img src="static/image/nouser.jpg" alt="" width="100"></div>
                    </#if>
                        <div class="loginFormBtn">
                            <button id="loginBtn" class="btn btn-main btn-login" tabindex="6" <#if !caEnabled>type="submit" <#else>onclick="return btnSubmit(this);"</#if> >登&nbsp;&nbsp;录</button>

                        </div>


                </form>
                </div>
            </div>

        </div>


    </div>


    <!--end 登录 -->

</div>



</div>
<!--end 背景 -->
<!-- 底部背景 -->
<div class="bottomBgRepeat">
    <div class="bottomBg"></div>
</div>


<!-- 版权 -->
<div class="footer" >
    <#--<span >Copyright ©  ${RegionUtil.getDefaultRegionName()!}国土环保局、${RegionUtil.getDefaultRegionName()!}土地储备中心 版权所有 </span>-->
</div>
<!--end 版权 -->
<!--end 底部背景 -->

<#if _msg??>
<script type="text/javascript">
    alert("${_msg}");
</script>
</#if>
<script type="text/javascript">
    /*   文字水印  */
    $(function () {
        $("#idInput").watermark("请输入帐号");
        $("#pwdInput").watermark("请输入密码");
        DD_belatedPNG.fix('.LOGO,.bottomBgRepeat,.bottomBg');


    });

    function fixheight() {
        $("#heightFix").height($(window).height() + $(window).scrollTop());
    }

    $(window).resize(function () {
        fixheight();
    });
    $(document).ready(function () {
        fixheight();
    });



</script>

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
                document.loginForm.submit();
            }else{
                alert(gtmapCA.getErrorString(result));
            }
        }
    </script>
</#if>

</body>
</html>
