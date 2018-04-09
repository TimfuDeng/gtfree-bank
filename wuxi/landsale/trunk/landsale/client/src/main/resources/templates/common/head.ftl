<div class="topbar">
    <div class="cl wp">
        <span class="topLeft l">
          <a class="active" href="${base}/index">首页</a>
            <a href="${base}/material">资料下载</a>
            <a href="${base}/help">系统帮助</a>
        </span>

        <div class="topLeft r">
            <#if WebUtil.isLogin()>
                欢迎您：${WebUtil.loginUserViewName!}&nbsp;&nbsp;&nbsp;&nbsp;
                <a  href="${base}/my/resource-list" id="aUser" value="${WebUtil.loginUserId!}">
                    我的交易
                    <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                    <#if applyCount gt 0>
                    <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                    </#if>
                </a>
                <a href="${base}/logout">退出</a>
            <#else>
                <#--<a id="login-url" onclick="javascript:gotoLogin();">-->
                <#--请登录-->
                <#--</a>-->
            </#if>
                <#assign ctx=request.contextPath>
            <span>当前时间：</span>
            <span id="serverTimeDiv"></span>

        </div>
    </div>
</div>
<div class="banner cl has-dots" style="margin-top:-1px; height:130px; overflow:hidden;background:url('${base}/static/image/title.png') center no-repeat  #5E71AA;">

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

