<div class="topbar">
    <div class="cl wp">
    <span class="topLeft l">
      <a class="active" href="${base}/index">管理页面</a>
      <a href="${client}/index">我要买地</a>
      <a class="c_red" href="${base}/trans/view_2" target="_blank">交易大屏幕</a>
    </span>

        <div class="topRight r">
            <ul>
                <li class="pos-r" style="display:inline-block">
                    <span>欢迎您：${WebUtil.loginUserViewName!}&nbsp;&nbsp;&nbsp;&nbsp;<a href="${base}/logout">退出</a></span>
                    <span>当前时间：</span>
                    <span id="serverTimeDiv"></span>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="banner cl has-dots"
     style="margin-top:-1px; height:130px; overflow:hidden;background:url('${base}/image/title.png') center no-repeat  #1486C7;">

</div>

<script type="text/javascript">
    $(document).ready(function () {
        //获取服务器时间
        setServerTime("${base}/getServerTime.f", "#serverTimeDiv");
    });
</script>

