<html>
<head>
    <title>-银行配置</title>
    <meta name="menu" content="menu_admin_organizelist"/>
    <link href="${base}/upload/uploadfile.css" rel="stylesheet">
    <style type="text/css">
        .title-info {
            width: 100%;
            height: 90px;
            position: relative;
            top: 18px;
            padding-right: 10px;
        }

        .title-info h1, .title-info h3 {
            margin: 0;
            padding: 0 0 0 20px;
            font-family: "Microsoft Yahei";
            font-weight: 400;
        }

        td input {
            padding-right: 0px;
        }

        .btn-upload {
            position: relative;
            display: inline-block;
            overflow: hidden;
            vertical-align: middle;
            cursor: pointer;
            height: 60px !important;
        }

        .upload-url {
            width: 200px;
            cursor: pointer;
        }

        .input-file {
            position: absolute;
            right: 0;
            top: 0;
            cursor: pointer;
            font-size: 17px;
            opacity: 0;
            filter: alpha(opacity=0)
        }

        .uploadifive-button {
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        .btn-upload input {
            height: 65px;
            cursor: pointer;
        }

        .fileUploader {
            margin-left: 10px;
            clear: both;
        }

        #attachments {
            width: 500px;
            float: left;
            overflow: hidden;
            text-overflow-ellipsis: '..';
        }

        #attachmentsOperation {
            float: right
        }
        .upload-progress-bar{
            display: none;
            width: 400px;
        }
    </style>
    <script type="text/javascript" src="${base}/upload/jquery.form.js"></script>
    <script type="text/javascript" src="${base}/upload/jquery.uploadfile.js"></script>
    <script type="text/javascript">
        var uploadObj;

        $(document).ready(function () {
            uploadObj = $('#fileuploader').uploadFile({
                url: "${storage}/storage/upload",
                allowedTypes: "PG,JPEG,GIF,PNG,JPG",
                extErrorStr: "上传文件类型错误,支持类型：",
                maxFileSize: 4194304,
                sizeErrorStr: "超过最大限制,最大上传限制：",
                fileCounterStyle: ".",
                uploadQueueOrder: "bottom",
                dragDrop: true,
                dragDropStr: "<span><b>拖拽上传文件</b></span>",
                uploadStr: "点击上传",
                showPreview: true,
                showProgress: true,
                showAbort: false,
                showDelete: true,
                deletelStr: "删除",
                maxFileCount: 1,
                maxFileCountErrorStr: "超过上传数量,最大上传数量：",
                previewHeight: 100,
                previewWidth: 100,
                statusBarWidth: 120,
                onSelect: function (files) {
                },
                onSuccess: function (files, response, xhr, pd) {
                    $("#bankIcon").val(response[0].id);
                    $("#bankIconName").val(response[0].name);
                },
                deleteCallback: function (data, pd) {
                    $("#bankIcon").val("");
                    $("#bankIconName").val("");
                    return true;
                }
            });

            // 图片
            var bankIcon = $("#bankIcon").val();
            var bankIconName = $("#bankIconName").val();
            if (!isEmpty(bankIcon)) {
                uploadObj.createProgress(bankIconName, "${storage}/storage/download/" + bankIcon);
            }
        });

        function checkInputNull(name,info){
            if($("input[name='"+name+"']").val()==""){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }

        function checkForm(){
            if (!checkInputNull('bankName','银行名称必须填写!'))
                return false;
            if (!checkInputNull('bankCode','银行缩写必须填写!'))
                return false;
            if (!checkInputNull('bankIcon','图片必须选择!'))
                return false;
            return true;
        }

        function submitForm() {
            if (checkForm()) {
                var url;
                if (isEmpty($("#configId").val())) {
                    url = "${base}/bank/config/addBankConfig";
                } else {
                    url = "${base}/bank/config/editBankConfig";
                }
                $.ajax({
                    type: "post",
                    url: url,
                    data: $("form").serialize(),
                    success: function (data) {
                        $("#configId").val(data.empty.configId);
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    }
                });
            }
        }

    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/bank/config/index')">银行配置</a>

</nav>

<#--<div id="ajaxResultDiv" style="display:none"></div>-->
<form  defaultHtmlEscape="true"  class="form-base" method="post">
    <#--spring:htmlEscape-->
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td><label class="control-label">银行名称：</label></td>
            <td><input type="text" class="input-text" name="bankName" value="${transBankConfig.bankName!}"
                       style="width:100%;background-color: background-color: #ffffcc;" maxlength="25">
            </td>
            <td width="180px"><label class="control-label">银行缩写：</label></td>
            <td width="300px"><input type="text" class="input-text" name="bankCode" value="${transBankConfig.bankCode!}"
                                     style="width:100%;" maxlength="25"></td>
        </tr>
        <tr>
            <td><label class="control-label">图标：</label></td>
            <td colspan="3">
                <input type="hidden" id="bankIcon" name="bankIcon" value="${transBankConfig.bankIcon!}"/>
                <input type="hidden" id="bankIconName" name="bankIconName" value="${transBankConfig.bankIconName!}"/>
                <div style="border: none;width: 100%;height:65px;" id="fileuploader"></div>
                <p>最多上传1张，单张照片最大4MB，支持：PG/JPEG/GIF/PNG格式</p>
            </td>
        </tr>
    </table>

    <input type="hidden" id="configId" name="configId" value="${transBankConfig.configId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="submitForm()">保存</button>
            <button type="button" class="btn" onclick="changeSrc('${base}/bank/config/index')">返回</button>
        </div>
    </div>
</form>

</body>
</html>