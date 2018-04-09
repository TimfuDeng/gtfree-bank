<html>
<head>
    <title>-法律依据</title>
    <meta name="menu" content="menu_admin_transLaw"/>
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
            getAttachments('${transLaw.lawId!}');
            showAttachmentUploader();
        });

        function showAttachmentUploader() {
            var lawId = '${transLaw.lawId!}';
            if (lawId != null && lawId != '') {
                var url = '${base}/file/upload.f?fileKey=' + lawId;
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


        function getAttachments(lawId) {
            checkInputFileter();
            var url = '${base}/file/list.f?fileKey=' + lawId;
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
            if (!checkInputNull('title','法律依据标题必须填写!')) {
                return false;
            } else if($("#content").val()==""){
                alert("法律依据内容必须填写！");
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
    <span class="c_gray en">&gt;</span><a href="${base}/console/transLaw/list">法律依据</a>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/transLaw/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="120"><label class="control-label">法律依据标题：</label></td>
            <td colspan="6"><input type="text" class="input-text"  name="title" value="${transLaw.title!}"
                                   style="width:100%;background-color: #FFFFCC" maxlength="100"></td>
        </tr>
        <tr>
            <#--<td width="120"><label class="control-label">公告编号：</label></td>-->
        </tr>

        <#--<tr>-->
            <#--<td width="120"><label class="control-label">作者：</label></td>-->
            <#--<td><input type="text" class="input-text" name="noticeAuthor" value="${transLaw.noticeAuthor!}"-->
                       <#--style="width:100%;;background-color: #FFFFCC" maxlength="32"></td>-->
            <#--<td><label class="control-label">发布时间：</label></td>-->
            <#--<td>-->
            <#--<#if transLaw.lawId??>-->
                <#--<input type="text" name="deployTime" class="input-text Wdate"-->
                       <#--value="${(transLaw.deployTime!)?string("yyyy-MM-dd HH:mm:ss")}"-->
                       <#--onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">-->
            <#--<#else>-->
                <#--<input type="text" name="deployTime" class="input-text Wdate"-->
                       <#--onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">-->
            <#--</#if>-->
            <#--</td>-->
        <#--</tr>-->

        <tr>
            <td><label class="control-label">法律依据详情：</label></td>
            <td colspan="3">
                <textarea type="text" id="content" name="content" class="xheditor" style="width: 100%;;background-color: #FFFFCC"
                          rows="50">${transLaw.content!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" name="lawId" value="${transLaw.lawId!}"/>
    <input type="hidden" name="lawStatus" value="${transLaw.lawStatus!}"/>

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/transLaw/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
</body>
</html>