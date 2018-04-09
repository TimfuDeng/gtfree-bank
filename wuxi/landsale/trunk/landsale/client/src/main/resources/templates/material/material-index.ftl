<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script src="${base}/js/jqPaginator.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
    <script type="text/javascript" src="${base}/js/myPage.js"></script>
    <script type="text/javascript" src="${base}/js/index.js"></script>

    <link href="${base}/css/gonggao_style.css" type="text/css" rel="stylesheet">
    <link href="${base}/css/bootstrap.min.css" type="text/css" rel="stylesheet"  />
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css"/>
</head>
<script>
    $(document).ready(function () {
        loadData();
        $("iframe", window.parent.document).attr("height", "");
    });

    function changeParentRegion(regionCode) {
        $("#regionCode").val(regionCode);
        loadData();
    }

    function loadData(data) {
        var regionCodes = $("#regionCode").val();
        if (isEmpty(data)) {
            data = {regionCodes:regionCodes};
            $("#currentPage").val(1);
        }
        $("#main_gonggao").load("${base}/material/getFileList", data, function (response, status, xhr) {
        });
    }
</script>
<body>
<div class="weizhi">
    <div class="weizhi_in">
        <img src="${base}/image/zuobiao.png">
        <p id="navBar">当前位置：<a href="${base}/resource/index">首页</a> > 资料下载 </p>
    </div>
</div>
<div class="main_file" id="main_gonggao">
</div>
<input type="hidden" id="regionCode" name="regionCodes"/>
<div style=" width:600px; margin:0 auto;text-align:center;">
    <ul class="pagination" id="pagination">
    </ul>
    <input type="hidden" id="currentPage" runat="server" value="1" />
    <input type="hidden" id="totalPages" runat="server" value="10"/>
    <input type="hidden" id="pageSize" runat="server" value="8" />
    <!--设置最多显示的页码数 可以手动设置 默认为7-->
    <input type="hidden" id="visiblePages" runat="server" value="7" />
</div>
</body>
</html>
