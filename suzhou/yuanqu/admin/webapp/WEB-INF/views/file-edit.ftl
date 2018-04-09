<html>
<head>
    <title>-资料管理</title>
    <meta name="menu" content="menu_admin_filelist"/>
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

        .error_input{
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
            //getAttachments('ziliao');
            showAttachmentUploader();

            <#if transFile?? && transFile.fileId??>
                insertAttachment("${transFile.fileId!}","${transFile.fileName!}")
            </#if>
        });
        var _fileId;
        function showAttachmentUploader() {
            var fileKey = 'ziliao';
            if (fileKey != null && fileKey != '') {
                var url = '${base}/file/upload.f?fileKey=' + fileKey;
                $('#fileName').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if (data != null && data != '') {
                            if (data.result.hasOwnProperty("ret")){
                                alert(data.result.msg);
                            }
                            else{
                                _fileId=data.result.fileId;
                                insertAttachment(data.result.fileId, data.result.fileName);
                            }
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
                        $("#fileName").parent().show();
                    }
                });

            }
        }

        function getAttachments(ggId) {
            var url = '${base}/file/list.f?fileKey=' + ggId;
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
            //$("#attachmentsOperation").prepend('<div class="attachment' + fileId + '"><a class="btn btn-link" href="javascript:removeAttachment(\'' + fileId + '\')">删除</a><br/></div>');
            $("#fileId").val(fileId);
            $("#fileName").parent().hide();
        }

        function removePostAttachment(fileId){
            var url = '${base}/file/remove.f';
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

        function returnList(){
            if(_fileId!=undefined&&_fileId!=''&&_fileId!=null){
                removePostAttachment(_fileId);
            }

            window.location.href='${base}/console/file/list'


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
            if (!checkInputNull('fileName','资料名称必须填写!'))
                return false;
            if (!checkInputNull('fileNo','编号必须填写!'))
                return false;
            return true;
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/file/list">资料管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"></span>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/file/save">
    <table class="table table-border table-bordered table-striped">
        <input type="hidden"  name="fileId" id="fileId" >
        <tr>
            <td width="120"><label class="control-label">编号：</label></td>
            <td><input type="text" id="fileNo" class="input-text" name="fileNo" value="${transFile.fileNo!}" maxlength="10"
                       style="width: 100%;background-color: #FFFFCC"></td>

            <td width="120"><label class="control-label">资料名称：</label></td>
            <td><input type="text" class="input-text" name="fileName" value="${transFile.fileName!}"
                       style="width:100%;background-color: #FFFFCC"></td>

        </tr>

        <tr>
            <td><label class="control-label">上传时间：</label></td>
            <td><input type="text" name="createAt" class="input-text Wdate"
                       value="${transFile.createAt?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">
            </td>
            <td width="180"><label class="control-label">行政区部门：</label></td>
            <td width="300"><@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=0 SelectValue=transFile.regionCode onChange=""/></td>
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
                                <input id="fileName" name="file" type="file" multiple accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf,application/msword" class="input-file"
                                       >

                            </span>

                        </div>
                    </td>
                </tr>
            <tr>
                <td colspan="3">
                    <span style="color: red;">注：只允许传入以下类型的文件：pdf、zip、doc或者jpg等图片文件，其大小不超过10M</span>
                    <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                        <span class="sr-only"/>
                    </div>
                </td>
            </tr>



        <tr>
            <td><label class="control-label">备注：</label></td>
            <td colspan="3">
                <textarea type="text" id="transFile" name="description"
                          style="width: 100%;"  rows="15">${transFile.description!}</textarea>
            </td>
        </tr>

    </table>








    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="returnList();">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
</body>
</html>