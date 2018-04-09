<@Head_SiteMash title="" />
    <style type="text/css">
        .form-row{
            padding-left: 10px;
            padding-right: 10px;
            padding-top: 5px;
            padding-bottom: 5px;
        }

        .btn-upload{ position: relative;display: inline-block;overflow: hidden; vertical-align: middle; cursor: pointer;}
        .uploadifive-button{
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/static/image/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        #attachments{width: 500px;float: left;overflow:hidden;text-overflow-ellipsis: '..';}
        #attachmentsOperation{float: right}

        .error_input{
            background-color: #FFFFCC;
        }
        .upload-progress-bar{
            display: none;
            width: 300px;
        }
    </style>
    <script type="application/javascript">

        function resetProgressBar(id){
            $('#'+id+' span').removeAttr("style");
        }

        function setProgressBarVisible(id,visible){
            if(visible==true)
                $('#'+id).show();
            else
                $('#'+id).hide();
        }
    </script>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><a href="${base}/view?id=${transBuyQualified.resourceId}">审核失败</a>
    </nav>
<#if _msg??>
    <div class="Huialert Huialert-danger" style="margin: 10px 0px;"><i class="icon-remove"></i>${_msg}</div>
</#if>
    <div class="row">
        <table class="table table-border table-bordered">
            <tr>
                <td  width="200px" style="background-color: #F5F5F5;">
                    审核人：
                </td>
                <td>
                    ${transBuyQualified.qualifiedAuditor!}
                </td>
                <td  width="200px" style="background-color: #F5F5F5;">
                    审核时间：
                </td>
                <td width="400px">
                ${transBuyQualified.qualifiedTime?string("yyyy-MM-dd HH:mm:ss")}
                </td>
            </tr>
            <tr>
                <td width="200px" style="background-color: #F5F5F5;" >
                    联系方式：
                </td>
                <td colspan="3">
                ${transBuyQualified.contacter!}
                </td>
            </tr>
            <tr>
                <td height="150px" width="200px" style="background-color: #F5F5F5;" >
                    审核未通过原因：
                </td>
                <td colspan="3">
                ${transBuyQualified.qualifiedReason!}
                </td>
            </tr>
        </table>
        <div style="text-align: center;margin-top: 20px">
            <a class="btn btn-danger size-L"  style="width: 236px" href="${base}/console/apply-bank?id=${resource.resourceId}" >重新选择竞买方式和币种</a>
        </div>
    </div>

</div>

<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.form.min.js"></script>

<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>