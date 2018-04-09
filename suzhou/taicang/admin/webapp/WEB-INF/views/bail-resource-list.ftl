<html>
<head>
    <title>保证金管理</title>
    <meta name="menu" content="menu_admin_bailresourcelist"/>
    <script>
        $(function () {
            $('#frmSearch').submit(function () {
                var caThumbprint = $('#caThumbprint').val();
                if (caThumbprint == null || caThumbprint == '') {
                    alert('请插入CA数字证书，并点击读取按钮！');
                    return false;
                }
                return true;
            });
            $('#btnCA').click(function () {
                gtmapCA.initializeCertificate(document.all.caOcx);
                if (gtmapCA.checkValidCertificate()) {
                    $('#caThumbprint').val(gtmapCA.getCertificateThumbprint());
                    $('#caName').val(gtmapCA.getCertificateFriendlyUser());
                }
            });
        });
    </script>
    <style>
        .caName {
            width: 300px;
        }
    </style>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">保证金查询</span>

</nav>
<div class="search_bar">
    <form class="navbar-form" id="frmSearch" method="post" action="${base}/console/bail/resource/list">
        <div class="l" style="">
            <input type="text" id="caName" readonly class="input-text caName" name="caName"
                   value="${caName!}" placeholder="请插入CA数字证书">
            <button type="button" id="btnCA" class="btn btn-primary">读取CA</button>
            <button type="submit" class="btn">查询</button>
        </div>
        <div class="r">
        </div>
        <input type="hidden" name="resourceId" value="${resourceId!}">
        <input type="hidden" id="caThumbprint"  name="caThumbprint"
               value="${caThumbprint!}">
    </form>
</div>
<table class="table table-border table-bordered" >
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="border-right: 1px solid #e8e8e8;">资源描述</th>
            <th style="width:170px;">挂牌开始时间</th>
            <th style="width:170px">挂牌结束时间</th>
            <th style="width:137px;border-right: 1px solid #e8e8e8;">保证金（万元）</th>
            <th style="width:137px">操作</th>
        </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>
        <tr>
            <td>
                <div class="l" style="width: 400px;">
                    <a href="${base}/console/resource/view?resourceId=${resource.resourceId!}" >
                        <div>${resource.resourceCode!}
                        <@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
                        </div>
                        <div>${resource.resourceLocation!}</div>


                    </a>
                </div>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td style="text-align: center">
                <p>
                    <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                    ${resource.fixedOffer?string("0.##")}万元</em>
                </p>
            </td>
            <td style="text-align: center">
                <a class="btn btn-primary size-S" href="${base}/console/bail/resource/bail?resourceId=${resource.resourceId!}&caThumbprint=${caThumbprint!}">保证金列表</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
<@Ca autoSign=0  />
</body>
</html>