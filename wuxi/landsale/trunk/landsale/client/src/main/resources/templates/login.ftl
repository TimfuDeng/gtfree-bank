<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国有建设用地网上交易系统</title>
    <link rel="stylesheet" href="${base}/css/Login.css">
</head>

<body class="bg">

<!-- 背景 -->
<div class="bodyBg">
    <div class="loginContent">
        <!-- LOGO -->
        <h1 href="#" class="LOGO">
        </h1>
        <!--end LOGO -->
        <!-- 登录 -->
        <div class="LoginPosition">
            <div class="Login">
                <div id="normalLoginTab" class="loginForm">
                    <form name="loginForm" method="post" action="<#if caEnabled>${base}/calogin<#else>/login</#if>">
                    <#--<input type="hidden" id="username" name="username" value="0">-->
                        <input type="hidden" id="savelogin" name="savelogin" value="0">
                        <input type="hidden" id="url" name="url" value="${url!}">
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
                        <div align="center"><img src="image/nouser.jpg" alt="" width="100"></div>
                    </#if>
                        <div class="loginFormBtn">
                            <button id="loginBtn" class="btn btn-main btn-login" tabindex="6" <#if !caEnabled>type="submit" <#else>onclick="return btnSubmit(this);"</#if> >登&nbsp;&nbsp;录</button>
                        </div>
                        <div class="tip-btn">
                            <a class="btn-link" tabindex="7" href="http://221.226.28.91:7777/view/">门户</a>
                            <a class="btn-link tip-margin-left" tabindex="8" href="${base}/help">系统帮助</a>
                            <div "tip-btn">
                            若无CA证书，请查看《<a href="/view/material/get?fileId=数字证书办理指南.doc" class="btn-link" >CA证书办理指南</a>》<br/>
                            未安装CA驱动，请下载《<a class="btn-link" tabindex="9" target="_blank" href="http://www.jsca.com.cn/folder169/folder241/folder282/2014-08-04/4358.html">CA证书驱动</a>》</p>
                            </div>
                        </div>


                </form>
                </div>
            </div>
            <!-- 版权 -->
            <footer class="footer">
                <p class="Copyright">Copyright © 江苏省国土资源厅 版权所有 <a href="http://www.miibeian.gov.cn" target="_blank"></a></p>
            </footer>
            <!--end 版权 -->
        </div>

    </div>


    <!--end 登录 -->

</div>

<!-- 底部背景 -->

<div class="bottomBg">
    <div id="heightFix" style="height: 100px; width: 23px;"></div>
</div>


<!--end 底部背景 -->

</div>
<!--end 背景 -->
<script src="${base}/js/dist/login.js"></script>

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

<#if false>
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
                return false;
            }
        }
    </script>
</#if>

</body>
</html>
