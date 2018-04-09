<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国有建设用地网上出让系统</title>
    <link rel="stylesheet" href="${base}/static/css/Login.css">
    <style type="text/css">
        .mianBody a{
            font-family:'\5FAE\8F6F\96C5\9ED1';
            border:1px solid #ddd;

            padding:10px;
            text-decoration:none;
            color:#fff;
            display:block;
            width:240px;
            text-align:center;
        }
        .mianBody .a1{
            background:#ff0000;
        }
        .a2{
            background:#0088cc;
        }
        .a3{
            background:#5cb85c;
        }
    </style>
</head>

<body class="bg">

<!-- 背景 -->
<div class="bodyBg">
    <div class="loginContent">
        <!-- LOGO -->
        <h1 href="#" class="LOGO">
        </h1>
        <div class="LoginPosition">
            <div>
                <div class="mianBody">
                    <a href="http://jsyd.sipac.gov.cn/client/login" class="a2">网上出让系统（竞买人登录）</a><br>
                    <a href="http://jsyd.sipac.gov.cn/view/index" class="a3">网上观摩大厅（公众登录）</a><br>
                    <a href="http://jsyd.sipac.gov.cn/view/help" class="a1">网上出让系统（系统帮助）</a>
                </div>
            </div>
        </div>
        <!--end LOGO -->
        <!-- 登录 -->
    </div>

    <#--<div class="mianBody">
        <a href="http://221.226.28.93:7777/admin/login" class="a1">网上出让系统（管理员登录）</a>
        <a href="http://221.226.28.93:7777/client/login" class="a2">网上出让系统（竞买人登录）</a>
        <a href="http://221.226.28.93:7777/view/index" class="a3">网上观摩大厅（公众登录）</a>
    </div>-->
    <!--end 登录 -->

</div>

<!-- 底部背景 -->

<div class="bottomBg">
    <div id="heightFix" style="height: 100px; width: 23px;"></div>
</div>


<!--end 底部背景 -->

</div>
<!--end 背景 -->


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

</body>
</html>
