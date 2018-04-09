<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国有建设用地网上出让系统</title>
    <link rel="stylesheet" href="${base}/static/css/Login.css">
</head>

<body class="bg" style="overflow:hidden">

<!-- 背景 -->
<div class="bodyBg" >
    <div class="loginContent" style="overflow:hidden">
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
                        <p style="position: relative;margin: 20px 0 0 25px;color: #ff0000;line-height:20px"><span >
					        CA证书必须注册后才能登录<br>
				            注册地点：苏州工业园区现代大道999号现代大厦8楼国土环保局（联系电话：0512-66680873）或苏州工业园区现代大道999号现代大厦5楼土地储备中心（联系电话：0512-66680507）
                            </span></p>
                        <div class="tip-btn">

                            <a class="btn-link" tabindex="7" href="http://jsyd.sipac.gov.cn/">门户</a>
                            <a class="btn-link tip-margin-left-1" tabindex="8" href="${base}/help">系统帮助</a>
                            <a class="btn-link tip-margin-left-1" tabindex="9" target="_blank" href="http://www.jsca.com.cn/serviceoperation/263.jhtml">CA办理指南</a>
                            <a class="btn-link tip-margin-left-1" tabindex="9" target="_blank" href="http://www.jsca.com.cn/servicedip/266.jhtml">行助手下载</a>
                        </div>


                </form>
                </div>
            </div>

        </div>

    </div>


    <!--end 登录 -->


</div>

<!-- 底部背景 -->
<div class="bottomBgRepeat">
    <div class="bottomBg"></div>
</div>


<!--end 底部背景 -->

</div>
<!--end 背景 -->
<!-- 版权 -->
<div class="footer" >
    <span >Copyright ©  ${RegionUtil.getDefaultRegionName()!}国土环保局、${RegionUtil.getDefaultRegionName()!}土地储备中心 版权所有 </span>
</div>
<!--end 版权 -->
<!--end 底部背景 -->
<script src="${base}/static/js/dist/login.js"></script>

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
                return false;
            }
        }
    </script>
</#if>

</body>
</html>
