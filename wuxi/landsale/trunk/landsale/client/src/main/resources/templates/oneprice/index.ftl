<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9; IE=8; IE=7; IE=EDGE">
    <meta charset="utf-8">
    <title>国有建设用地网上交易系统 - ${title!}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/js/dist/H-ui-ie9.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css"/>
    <link href="${base}/css/bootstrap.min.css" type="text/css" rel="stylesheet"  />

    <!--[if IE 7]>
    <link href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
    <![endif]-->


    <!--[if IE 6]>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <link rel="stylesheet" href="${base}/js/dist/index.css" >
</head>
<body>
<div class="wp" id="mainFrame">
    <div class="row cl" >
        <form id="frmSearch" method="get">
            <table class="table table-border table-bordered" style="margin-top:20px;background-color: #f6f6f6;">
                <tr>
                    <td  height="40" width="128" >
                        <div class="searchBar">
                            <input name="title" value="${title!}" placeholder="请输入地块编号" class="searchTxt" autocomplete="off" >
                            <input type="submit" value="搜索" class="searchBtn" >
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="row cl" style="width:1210px" id="oneTargetContent">
    </div>
    <div style=" width:600px; margin:0 auto;text-align:center;">
        <ul class="pagination" id="pagination">
        </ul>
        <input type="hidden" id="currentPage" runat="server" value="1" />
        <input type="hidden" id="totalPages" runat="server" value="10"/>
        <input type="hidden" id="pageSize" runat="server" value="10" />
        <!--设置最多显示的页码数 可以手动设置 默认为7-->
        <input type="hidden" id="visiblePages" runat="server" value="7" />
    </div>
</div>
<#--<@PageHtml pageobj=oneTargetList formId="frmSearch" />-->

<script type="text/javascript" src="${base}/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/thridparty/layer/layer.js"></script>
<script src="${base}/js/jqPaginator.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
<script type="text/javascript" src="${base}/js/myPage.js"></script>
<script type="text/javascript" src="${base}/js/index.js"></script>
<script>
    var _serverTime=${.now?long};
    var _serverTimeUrl="${base}/getServerTime.f";
    $(document).ready(function () {
        //获取服务器时间
        setServerTime();
        loadData();
    });

    // 查询列表
    function loadData(data) {
        if (isEmpty(data)) {
            data = $("#frmSearch").serializeArray();
            $("#currentPage").val(1);
        }
        $("#oneTargetContent").load("${base}/oneprice/content", data, function (response, status, xhr) {
        });
    }


    var window_offer = window.parent.getWindowOffer();
    var currentPage = isEmpty($("#currentPage").val()) ? 1 : $("#currentPage").val();
    function resourceOffer(resourceId, resourceCode) {
        // 未打开窗口
        if (window_offer == null) {
            window_offer = window.open('${base}/resourceOffer/index?resourceId=' + resourceId , 'window_offer', 'height=600,width=1200,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
            window_offer.focus();
        } else {
            // 窗口被关闭
            if (window_offer.closed) {
                window_offer = window.open('${base}/resourceOffer/index?resourceId=' + resourceId, 'window_offer', 'height=600,width=1200,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
                window_offer.focus();
            } else {
                window_offer.focus();
                window_offer.selectTab(resourceCode);
            }
        }
        window.parent.setWindowOffer(window_offer);
    }
</script>
</body>
</html>