<div class="topbar">
    <div class="cl wp">
    <span class="topLeft l">
      <a class="active" href="${base}/oneprice/resource/list">管理页面</a>
    </span>

        <div class="topRight r">
            <ul>
                <li class="pos-r" style="display:inline-block">
                    <span>欢迎您：${WebUtil.getLoginUserViewName()!}&nbsp;&nbsp;&nbsp;&nbsp;<a href="${base}/logout">退出</a></span>
                    <span>当前时间：</span>
                    <span id="serverTimeDiv"></span>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="banner cl has-dots"
     style="margin-top:-1px; height:130px; overflow:hidden;background:url('${base}/static/image/title.png') center no-repeat  #0684CE;">

</div>

<script type="text/javascript">
    $(document).ready(function () {
        //获取服务器时间
        setServerTime("${base}/getServerTime.f", "#serverTimeDiv");
    });
</script>

