<div class="topbar">
    <div class="cl wp">
        <span class="topLeft l">
            <a style="color: #0684CE;" href="${base}/oneprice/index">首页</a>
             <a style="color: #0684CE;" href="${base}/oneprice//view-agree">报价规则</a>
        </span>

        <div class="topLeft r">

                欢迎您：${WebUtil.getLoginUserViewName()!}&nbsp;&nbsp;&nbsp;&nbsp;
                <a  href="${base}/oneprice/my/view-resource-list" id="aUser" value="${WebUtil.loginUserId!}">
                    我的交易
                </a>
                <a href="${base}/logout">退出</a>
                <#assign ctx=request.contextPath>
            <span>当前时间：</span>
            <span id="serverTimeDiv"></span>

        </div>
    </div>
</div>
<div class="banner cl has-dots" style="margin-top:-1px; height:130px; overflow:hidden;background:url('${base}/static/image/title.png') center no-repeat  #0684CE;">

</div>

<script type="text/javascript">
    var _serverTime=${.now?long};
    var _serverTimeUrl="${base}/getServerTime.f";
    $(document).ready(function () {
        //获取服务器时间
        setServerTime();
    });

    function gotoLogin(){
        window.location="${base}/login?url=" +window.location;
    }
</script>

