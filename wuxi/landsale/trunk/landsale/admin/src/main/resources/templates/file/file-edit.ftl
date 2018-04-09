<html>
<head>
    <title>-资料管理</title>
    <meta name="menu" content="menu_admin_filelist"/>
    <link rel="stylesheet" href="${base}/tree/zTreeStyle/zTreeStyle.css" type="text/css">
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

        .error_input {
            background-color: #FFFFCC;
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

        .upload-progress-bar {
            display: none;
            width: 400px;
        }
    </style>

    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${base}/tree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript">
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData : {
                    enable : true
                }
            },
            callback: {
                beforeClick: beforeClick,
                onClick: onClick
            }
        };
        $(document).ready(function () {
            //getAttachments('ziliao');
            showAttachmentUploader();

            $.ajax({
                type: "post",
                dataType: "json",
//                data: {},
                url: "${base}/region/getRegionTree",
                success: function (data) {
                    if (data != null) {
                        $.fn.zTree.init($("#treeOrganize"), setting, data);
                    }
                }
            });
        });

        var _fileId;
        function showAttachmentUploader() {
            var fileKey = 'ziliao';
            if (fileKey != null && fileKey != '') {
                var url = '${core}/transfile/upload?fileKey=' + fileKey;
                $('#fileName').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if (data != null && data != '') {
                            if (!data.result.flag) {
                                alert(data.result.message);
                            } else {
                                _fileId = data.result.empty.fileId;
                                insertAttachment(data.result.empty.fileId, data.result.empty.fileName);
                            }
                        }
                        var activeCount = $('#fileName').fileupload('active');
                        if (activeCount == 1) {
                            resetProgressBar('attachmentProgressBar');
                            setProgressBarVisible('attachmentProgressBar', false);
                        }
                    },
                    progressall: function (e, data) {
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        $('#attachmentProgressBar span').css(
                                'width',
                                progress + '%'
                        );
                    },
                    start: function (e) {
                        setProgressBarVisible('attachmentProgressBar', true);
                    },
                    fail: function (e, data) {
                        resetProgressBar('attachmentProgressBar');
                        setProgressBarVisible('attachmentProgressBar', false);
                        alert('附件上传失败');
                        $("#fileName").parent().show();
                    }
                });

            }
        }

        function getAttachments(ggId) {
            var url = '${base}/transfile/index?fileKey=' + ggId;
            $.get(url, function (data) {
                $('#attachments').empty();
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        insertAttachment(data[i].fileId, data[i].fileName);
                    }

                }
            })
        }

        function insertAttachment(fileId, fileName) {
            $("#attachments").prepend("<div class='attachment" + fileId + "'><a class='btn btn-link' target='_blank' href='${base}/transfile/get?fileId=" + fileId + "'>" + fileName + "</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment' + fileId + '"><a class="btn btn-link" href="javascript:removeAttachment(\'' + fileId + '\')">删除</a><br/></div>');
            $("#fileId").val(fileId);
            $("#fileName").parent().hide();
        }

        function removePostAttachment(fileId) {
            var url = '${base}/transfile/remove';
            $.post(url, {fileIds: fileId}, function (data) {
                if (data != null && data == 'true') {
                    $('#attachments div[class=attachment' + fileId + ']').each(function () {
                        $(this).remove();
                    });
                    $('#attachmentsOperation div[class=attachment' + fileId + ']').each(function () {
                        $(this).remove();
                    });
                    $("#fileName").parent().show();
                }
            });
        }


        function removeAttachment(fileId) {
            if (confirm("确定要删除数据吗？")) {
                removePostAttachment(fileId);
            }

        }

        function resetProgressBar(id) {
            $('#' + id + ' span').removeAttr("style");
        }

        function setProgressBarVisible(id, visible) {
            if (visible == true)
                $('#' + id).show();
            else
                $('#' + id).hide();
        }


        function checkInputNull(name, info) {
            if ($("input[name='" + name + "']").val() == "") {
                $("input[name='" + name + "']").addClass("error_input");
                $("input[name='" + name + "']").focus();
                alert(info);
                return false;
            } else {
                $("input[name='" + name + "']").removeClass("error_input");
            }
            return true;
        }


        function saveFile () {
            if (checkForm()) {
                var url = '${base}/transfile/save';
                var fileId = $("#fileId").val();
                $.ajax({
                    type: "post",
                    url: url,
                    data: $("form").serialize(),
                    success: function (data) {
                        $("#fileId").val(data.empty.fileId);
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    }
                });
            }
        }



        function checkForm() {
            checkInputFileter();
            if (!checkInputNull('fileName', '编号必须填写!'))
                return false;
            if (!checkInputNull('fileNo', '资料名称必须填写!'))
                return false;
            return true;
        }

        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeOrganize"),
                    nodes = zTree.getSelectedNodes();
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("只能选择城市...");
            return check;
        }

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeOrganize"),
                    nodes = zTree.getSelectedNodes(),
                    regionCode = "",
                    regionName = "";
            if (nodes.length > 1) {
                alert("只能选择一个城市...");
                return false;
            }
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, len = nodes.length; i < len; i++) {
                regionCode += nodes[i].id + ",";
                regionName += nodes[i].name.substring(0, nodes[i].name.indexOf("(")) + ",";
            }
            if (regionCode.length > 0 ) {
                regionCode = regionCode.substring(0, regionCode.length - 1);
                regionName = regionName.substring(0, regionName.length - 1);
            }
            $("#regionName").val(regionName);
            $("#regionCode").val(regionCode);
        }

        function showMenu() {
            if (isEmpty($("#fileId").val())) {
                $("#menuContent").slideDown("fast");
                $("body").bind("mousedown", onBodyDown);
            }
        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/transfile/index')">资料管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"></span>
</nav>

<#--<div id="ajaxResultDiv" style="display:none"></div>-->
<form class="form-base" method="post" action="${base}/transfile/save">
    <table class="table table-border table-bordered table-striped">
        <input type="hidden" name="fileId" id="fileId">
        <tr>
            <td width="120"><label class="control-label">编号：</label></td>
            <td><input type="text" id="fileNo" class="input-text" name="fileNo" value="${transFile.fileNo!}"
                       maxlength="10"
                       style="width: 100%;background-color: #FFFFCC"></td>

            <td width="120"><label class="control-label">资料名称：</label></td>
            <td><input type="text" class="input-text" name="fileName" value="${transFile.fileName!}"
                       style="width:100%;background-color: #FFFFCC"></td>

        </tr>

        <tr>
            <td><label class="control-label">所属行政区：</label></td>
            <td>
                <input type="text" class="input-text" id="regionName" name="regionName" value="${transFile.regionName!}" readonly
                       style="width: 50%;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="regionCode" name="regionCode" value="${transFile.regionCode!}" />
                <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                <div id="menuContent" class="menuContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 145px;width: 250px;z-index: 10; ">
                    <ul id="treeOrganize" class="ztree" style="margin-top:0; width:160px;"></ul>
                </div>
            </td>
            <td><label class="control-label">上传时间：</label></td>
            <td><input type="text" name="createAt" class="input-text Wdate"
                       value="${transFile.createAt?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"
                       readonly="readonly">
            </td>
        </tr>


        <tr>
            <td rowspan="2"><label class="control-label">附件：</label></td>
            <td colspan="3">
                <div id="attachments">
                </div>
                <div id="attachmentsOperation">
                </div>
                <div class="fileUploader">
                    <span class="btn-upload">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input id="fileName" name="file" type="file" multiple
                               accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf,application/msword"
                               class="input-file"
                                >

                    </span>

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <span style="color: red;">注：只允许传入以下类型的文件：pdf、zip、doc或者jpg等图片文件，其大小不超过5M</span>

                <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                    <span class="sr-only"/>
                </div>
            </td>
        </tr>

        <tr>
            <td><label class="control-label">备注：</label></td>
            <td colspan="3">
                <textarea type="text" id="transFile" name="description"
                          style="width: 100%;" rows="15">${transFile.description!}</textarea>
            </td>
        </tr>

    </table>


    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="saveFile();">保存</button>
            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/transfile/index')">返回</button>
        </div>
    </div>
</form>

</body>
</html>