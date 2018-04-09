<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script src="${base}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/thridparty/xdate/xdate.js"></script>
    <script src="${base}/js/jqPaginator.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
    <script type="text/javascript" src="${base}/js/myPage.js"></script>
    <script type="text/javascript" src="${base}/js/index.js"></script>
    <script type="text/javascript" src="${base}/js/bootstrap-select.min.js"></script>

    <link href="${base}/css/gonggao_style.css" type="text/css" rel="stylesheet">
    <link href="${base}/css/bootstrap-select.min.css" type="text/css" rel="stylesheet"/>
    <link href="${base}/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css"/>
<#--<link href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />-->
</head>
<script>
    $(document).ready(function () {
        $("iframe", window.parent.document).attr("height", document.body.scrollHeight);


        loadData();
    });

    function loadData(data) {
        if (isEmpty(data)) {
            data = $("#frmSearch").serializeArray();
            $("#currentPage").val(1);
        }
        $("#listDiv").load("${base}/notice/getCrggList", data, function (response, status, xhr) {
        });
    }

    function clearOptions() {
        $('.selectpicker').selectpicker('val', '');
        $("#ggBeginTime").val("");
        $("#ggEndTime").val("");
        $("#ggNum").val("");
    }

    function changeParentRegion(regionCode) {
        $("#regionCode").val(regionCode);
        loadData();
    }

    function loadCrgg(id) {
        $("#main_gonggao").load("${base}/notice/view/crgg", {id: id}, function () {
            $("iframe", window.parent.document).attr("height", document.body.scrollHeight);
            $("#navBar").html("当前位置：<a href='${base}/resource/index'>首页</a> > <a href='${base}/notice/index'>通知公告</a> > 公告详情")
        });
    }

</script>
<body>
<div class="weizhi">
    <div class="weizhi_in">
        <img src="${base}/image/zuobiao.png">
        <p id="navBar">当前位置：<a href="${base}/resource/index">首页</a> > 通知公告</p>
    </div>
</div>

<div class="main_gonggao" id="main_gonggao">
    <div class="main_gonggao_select">
        <form id="frmSearch" class="form-base" method="post" action="${base}/notice/getCrggList">
            <input type="hidden" id="regionCode" name="regionCodes"/>
            <div class="main_select_part1">
                <p>公告编号</p>
                <input id="ggNum" name="ggNum" type="text"/>
            </div>
            <div class="main_select_part2">
                <div style="margin-left: 15px;margin-top: 21px;">
                    <select class="selectpicker" id="afficheType" name="afficheType" title="请选择公告类型">
                        <option value="">不限</option>
                        <option value="0">工业用地公告</option>
                        <option value="1">经营性用地公告</option>
                        <option value="8">中止公告</option>
                        <option value="9">终止公告</option>
                        <option value="2">其它公告</option>
                    <#--<option value="3">协议出让类（划拨）公告</option>-->
                    </select>
                </div>
            </div>
            <div class="main_select_part3">
                <p>公告开始时间</p>
                <input type="text" id="ggBeginTime" name="ggBeginTime" class="input-text Wdate"
                <#--value="${ggBeginTime?string("yyyy-MM-dd")}"-->
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'ggEndTime\')}',alwaysUseStartDate:true})"
                       readonly="readonly">
            </div>
            <div class="main_select_part3">
                <p>公告结束时间</p>
                <input type="text" id="ggEndTime" name="ggEndTime" class="input-text Wdate"
                <#--value="${ggEndTime?string("yyyy-MM-dd")}"-->
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'ggBeginTime\')}',alwaysUseStartDate:true})"
                       readonly="readonly">
            </div>
            <div class="main_select_part4">
                <input name="" type="button" onclick="loadData()"/>
            </div>
            <div class="main_select_part5">
                <input name="" type="button" onclick="clearOptions()"/>
            </div>
        </form>
    </div>

    <div class="crgg_list" id="listDiv">

    </div>

    <div style=" width:600px; margin:0 auto;text-align:center;">
        <ul class="pagination" id="pagination">
        </ul>
        <input type="hidden" id="currentPage" runat="server" value="1"/>
        <input type="hidden" id="totalPages" runat="server" value="10"/>
        <input type="hidden" id="pageSize" runat="server" value="8"/>
        <!--设置最多显示的页码数 可以手动设置 默认为7-->
        <input type="hidden" id="visiblePages" runat="server" value="7"/>
    </div>

</div>
</body>
</html>
