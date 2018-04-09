<html>
<head>
    <title>-成交公示</title>
    <meta name="menu" content="menu_admin_dealNotice"/>
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

        .error_input{
            background-color: #FFFFCC;
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
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
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
    <script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
        <#if _result?? && _result>
            _alertResult('ajaxResultDiv',${_result?string('true','false')}, '${_msg!}');
        </#if>
            getAttachments('${dealNotice.noticeId!}');
            showAttachmentUploader();
        });

        function showAttachmentUploader() {
            var noticeId = '${dealNotice.noticeId!}';
            if (noticeId != null && noticeId != '') {
                var url = '${base}/file/upload.f?fileKey=' + noticeId;
                $('#fileName').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if (data != null && data != '') {
                            if (data.result.hasOwnProperty("ret"))
                                alert(data.result.msg);
                            else
                                insertAttachment(data.result.fileId, data.result.fileName);
                        }
                        var activeCount = $('#fileName').fileupload('active');
                        if(activeCount==1){
                            resetProgressBar('attachmentProgressBar');
                            setProgressBarVisible('attachmentProgressBar',false);
                        }
                    },
                    progressall: function (e, data){
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        $('#attachmentProgressBar span').css(
                                'width',
                                progress + '%'
                        );
                    },
                    start:function(e){
                        setProgressBarVisible('attachmentProgressBar',true);
                    },
                    fail:function (e, data) {
                        resetProgressBar('attachmentProgressBar');
                        setProgressBarVisible('attachmentProgressBar',false);
                        alert('附件上传失败');
                    }
                });

            }
        }


        function getAttachments(noticeId) {
            checkInputFileter();
            var url = '${base}/file/list.f?fileKey=' + noticeId;
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
            $("#attachments").prepend("<div class='attachment" + fileId + "'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" + fileId + "'>" + fileName + "</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment' + fileId + '"><a class="btn btn-link" href="javascript:removeAttachment(\'' + fileId + '\')">删除</a><br/></div>');
        }
        function removeAttachment(fileId) {
            if (confirm("确定要删除数据吗？")) {
                var url = '${base}/file/remove.f';
                $.post(url, {fileIds: fileId}, function (data) {
                    if (data != null && data == 'true') {
                        $('#attachments div[class=attachment' + fileId + ']').each(function () {
                            $(this).remove();
                        });
                        $('#attachmentsOperation div[class=attachment' + fileId + ']').each(function () {
                            $(this).remove();
                        });
                    }
                });
            }

        }
        function resetProgressBar(id){
            $('#'+id+' span').removeAttr("style");
        }

        function setProgressBarVisible(id,visible){
            if(visible==true)
                $('#'+id).show();
            else
                $('#'+id).hide();
        }

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
            checkInputFileter();
            if (!checkInputNull('noticeTitle','成交公示标题必须填写!')) {
                return false;
            }else if(!checkInputNull('deployTime','发布时间必须填写!')){
                return false;
            } else if($("#noticeContent").val()==""){
                alert("成交公示内容必须填写！");
                return false;
            }else{
                return true;
            }
        }

    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/dealNotice/list">成交公示</a>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/dealNotice/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="120"><label class="control-label">成交公示标题：</label></td>
            <td colspan="6"><input type="text" class="input-text"  name="noticeTitle" value="${dealNotice.noticeTitle!}"
                                   style="width:100%;background-color: #FFFFCC" maxlength="100"></td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">公告编号：</label></td>
            <td><input type="text" class="input-text" name="noticeNum" value="${dealNotice.noticeNum!}"
                       style="width:100%;" maxlength="32"></td>
            <td width="120"><label class="control-label">地块编号：</label></td>
            <td>
                <#if dealNotice.resourceCode??>
                    <input type="text" class="input-text" name="resourceCode" value="${dealNotice.resourceCode!}" style="width:100%;;background-color: #FFFFCC" readonly="readonly">
                <#else>
                    <input type="text" class="input-text" name="resourceCode" value="${dealNotice.resourceCode!}"
                           style="width:100%;" maxlength="32">
                </#if>
            </td>
        </tr>

        <tr>
            <td width="120"><label class="control-label">作者：</label></td>
            <td><input type="text" class="input-text" name="noticeAuthor" value="${dealNotice.noticeAuthor!}"
                       style="width:100%;;background-color: #FFFFCC" maxlength="32"></td>
            <td><label class="control-label">发布时间：</label></td>
            <td>
            <#if dealNotice.deployTime??>
                <input type="text" name="deployTime" class="input-text Wdate"
                       value="${(dealNotice.deployTime!)?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">
            <#else>
                <input type="text" name="deployTime" class="input-text Wdate"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">
            </#if>
            </td>
        </tr>
<#--
    <#if dealNotice.noticeId??>
        <tr>
            <td rowspan="2"><label class="control-label">附件：</label></td>
            <td colspan="6">
                <div id="attachments">
                </div>
                <div id="attachmentsOperation">
                </div>
                <div class="fileUploader">
                    <span class="btn-upload">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input id="fileName" name="file" type="file" multiple class="input-file"
                               accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf"/>
                    </span>

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <span style="color: red;">注：只允许传入以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过5M</span>
                <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                    <span class="sr-only"/>
                </div>
            </td>
        </tr>
    </#if>
-->
        <tr>
            <td><label class="control-label">成交公示详情：</label></td>
            <td colspan="3">
                <textarea type="text" id="noticeContent" name="noticeContent" class="xheditor" style="width: 100%;;background-color: #FFFFCC"
                          rows="50">${dealNotice.noticeContent!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" name="noticeId" value="${dealNotice.noticeId!}"/>
    <input type="hidden" name="resourceId" value="${resourceId!}"/>
    <input type="hidden" name="deployStatus" value="${dealNotice.deployStatus!}"/>

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/dealNotice/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
</body>
</html>