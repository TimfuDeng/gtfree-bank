<div class="topbar">
    <div class="cl wp">
        <span class="topLeft l">
          <a class="active" href="${base}/index">首页</a>
            <a href="${base}/material">资料下载</a>
            <a href="${base}/help">系统帮助</a>
        </span>

        <div class="topLeft r">
                <#assign ctx=request.contextPath>
            <span>当前时间：</span>
            <span id="serverTimeDiv"></span>

        </div>
    </div>
</div>
<div class="banner cl has-dots" style="margin-top:-1px; height:130px; overflow:hidden;background:url('${base}/static/image/title.png') center no-repeat  #ba2f2f;">

</div>

<script type="text/javascript">
    var _serverTime=${.now?long};
    var _serverTimeUrl="${base}/getServerTime.f";
    $(document).ready(function () {
        //获取服务器时间
        setServerTime();
    });
</script>

