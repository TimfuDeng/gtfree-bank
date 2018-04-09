<head>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/dist/H-ui-ie9.js"></script>
    <![endif]-->
    <link href="${base}/css/bootstrap.min.css" type="text/css" rel="stylesheet"  />
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css"/>
    <!--[if IE 7]>
    <link href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
    <![endif]-->

    <!--[if IE 6]>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->

    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
</head>
<body>
<div  class="search_bar">
    <form id="frmSearch">
        <div class="l">
            <input type="hidden" name="resourceId" value="${resourceId!}">
            <a class="btn btn-primary" type="button"  onclick=loadOffer()>刷新</a>
        </div>
    </form>
</div>
<div>
    <!-- 竞买记录  -->
    <table class="table table-border">
        <thead>
        <tr>
            <th>报价时间</th>
            <th>报价人</th>
            <th>报价</th>
        </tr>
        </thead>
        <tbody>
        <#list transResourceOffers.items as resourceOffer>
        <tr>
            <td class="offerText">${resourceOffer.offerTime?number_to_datetime?string('yyyy年MM月dd日 HH:mm:ss')}</td>
            <td>
                <#if userId?? && resourceOffer.userId==userId>
                    <span class="label label-default">我的报价</span>
                <#else>
                    其他报价
                </#if>
            </td>
            <td>${resourceOffer.offerPrice}
                <#if resource.maxOfferChoose.code==1 &&  resourceOffer.offerType==-1>
                    <label class="offer_unit">万元（总价）</label>
                <#else>
                    <label class="offer_unit"><@unitText offerUnit = resource.offerUnit/></label>
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
<#--<@PageHtml pageobj=transResourceOffers formId="frmSearch" />-->
</div>
<#--<script type="text/javascript" src="${base}/js/dist/viewOfferList.js"></script>-->
<script type="text/javascript" src="${base}/thridparty/layer/layer.js"></script>
<script src="${base}/js/jqPaginator.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
<script type="text/javascript" src="${base}/js/myPage.js"></script>
<script type="text/javascript" src="${base}/js/index.js"></script>
<script>
    var currentPage = isEmpty($("#currentPage").val()) ? 1 : $("#currentPage").val();

    currentPage = ${transResourceOffers.pageCount!} == 0 ? 1 : currentPage;
    var totalPages = ${transResourceOffers.pageCount!} == 0 ? 1 : '${transResourceOffers.pageCount!}';
    $("#totalPages").val(totalPages);
    $("#currentPage").val(currentPage);
    loadpage();

    // 查询列表
    function loadData(data) {
        if (isEmpty(data)) {
            data = $("#frmSearch").serializeArray();
            $("#currentPage").val(1);
        }
        $("#viewOffer").load("${base}/resource/view/offer/list", data, function (response, status, xhr) {
        });
    }
</script>
</body>
</html>