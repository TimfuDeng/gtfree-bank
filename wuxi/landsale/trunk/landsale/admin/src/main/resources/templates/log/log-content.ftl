<html>
<head>
    <title>日志操作内容</title>
    <meta name="menu" content="menu_admin_loglist"/>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/log/index')">日志管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">日志内容</span>
</nav>
<div>
    <table  class="table table-border table-striped" >
        <tr>
            <td width="80">日志内容</td>
            <td>
                <textarea rows="15" style="width: 100%" readonly>${content!}</textarea>
            </td>
        </tr>
    </table>
</div>
</body>
</html>