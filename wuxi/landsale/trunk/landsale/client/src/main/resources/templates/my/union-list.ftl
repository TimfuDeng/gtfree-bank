<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>联合竞买列表</title>
    <meta name="menu" content="menu_client_unionlist"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${base}/js/ajaxDoResult.js"></script>
    <style type="text/css">

        .table-bordered td {
            border-left: 1px solid #ddd;
            border-top: 1px solid #ddd;
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
        }

    </style>
    <script type="text/javascript">
        function agreeFunction(unionId){
            if(confirm("是否确认同意本次联合竞买申请？")){
//                var unionId = $("#unionId").val();
                $.ajax({
                    type: "POST",
                    url: '${base}/union/union-agree',
                    data: {"unionId":unionId},
                    success: function (data) {
                        if (data.flag==true){
                            location.reload();
                        }else {
                            alert(data.message);
                            location.reload();
                        }
                    }
                });
            }
        }

        function viewResource(resourceUrl) {
            $("#mainFrame", window.parent.document).attr("src", resourceUrl);
        }
    </script>
</head>
<body>
<div class="wp">
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <span class="c_gray en">&gt;</span><a href="${base}/resource/index" class="maincolor">首页</a>
    <#--<span class="c_gray en">&gt;</span><a href="${base}/resource/my/menu" ">我的交易</a>-->
    <span class="c_gray en">&gt;</span><span class="c_gray">被联合竞买列表</span>
</nav>
    <form class="navbar-form" id="frmSearch" action="${base}/my/union-list">
    </form>
<form id="frmSearch" >
<table class="table table-border table-bordered table-bg">
    <thead>
        <tr>
            <td width="200">地块信息</td>
            <td>竞买申请人</td>
            <td>被联合人</td>
            <td >编号</td>
            <td width="120">地址</td>
            <td width="40">操作</td>
        </tr>
    </thead>
    <tbody>
    <#list transUserUnionPage.items as transUserUnion>
        <#assign resourceApply=ResourceUtil.getResourceApply(transUserUnion.applyId)!/>
        <#if resourceApply.resourceId??>
            <#assign resource=ResourceUtil.getResource(resourceApply.resourceId)/>
        <#if resource != null && resource.resourceId??>
            <tr>
                <td>
                    <a onclick="viewResource('${base}/resource/view?resourceId=${resource.resourceId}');" target="frm_${resource.resourceId}" style="z-index: 1;">
                        <p>编号：${resource.resourceCode!}</p>
                        <p>面积：${resource.crArea}平方米</p>
                        <p>保证金：${resource.fixedOffer}万元</p>
                    </a>
                </td>
                <td>
                ${UserUtil.getUserName(resourceApply.userId)}
                </td>
                <td>
                ${transUserUnion.userName!}
                </td>
                <td>
                ${transUserUnion.userCode!}
                </td>
                <td>
                ${transUserUnion.userAddress!}
                </td>
                <td>
                    <#if transUserUnion.agree>
                        已同意
                    <#else>
                        <button type="button" class="btn btn-primary" onclick="agreeFunction('${transUserUnion.unionId!}');">同意</button>
                    </#if>
                </td>
            </tr>
        </#if>
        </#if>
    </#list>
    </tbody>
</table>
</form>
<@PageHtml pageobj=transUserUnionPage formId="frmSearch" />
</div>
</body>
</html>