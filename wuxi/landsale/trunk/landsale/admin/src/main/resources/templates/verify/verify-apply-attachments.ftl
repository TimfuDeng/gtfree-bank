<html>
<head>
    <title>竞得人附件材料</title>
    <meta name="menu" content="menu_admin_verify"/>
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
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png)  no-repeat 0 0;

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
    <script type="text/javascript">
        var filemaps;
        $(document).ready(function () {
            $.ajaxSetup({cache:false});
            <#--getAttachmentsByUserId('${transUser.userId!}');-->
            getAttachments('${applyId!}');

            var fileType = $('#attachmentTypes').children('option:selected').val();
            //initializeAttachmentUploader('${transUser.userId!}',fileType);
           /* $('#attachmentTypes').change(function(){
                var fileType = $('#attachmentTypes').children('option:selected').val();
                $('#attachmentFile').fileupload('destroy');
                initializeAttachmentUploader('${transUser.userId!}',fileType);
            });*/

            /*fileTypeMap = {};
            $('#attachmentType option').each(function(){
                fileTypeMap[$(this).val()]=$(this).text();
            });*/
        });

        function getAttachments(applyId){
            var url ='${core}/transfile/getTransFileAttachments?fileKeys='+applyId;
            $.get(url,function(data){
                $('#attachments').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachment(data[i].fileId,data[i].fileName,data[i].fileType);
                    }
                }
            })
        }

        function getAttachmentsByUserId(userId){
            var url ='${base}/file/attachments.f?fileKey='+userId;
            $.get(url,function(data){
                $('#attachmentsUpload').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachmentUpload(data[i].fileId,data[i].fileName,data[i].fileType);
                    }
                }
            })
        }


        function initializeAttachmentUploader(userId,type){
            if(userId!=null&&userId!=''){
                var url = '${base}/file/upload.f?fileKey='+userId+'&fileType='+type;
                $('#attachmentFile').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if(data!=null&&data!=''){
                            if(data.result.hasOwnProperty("ret"))
                                alert(data.result.msg);
                            else{
                                insertAttachmentUpload(data.result.fileId,data.result.fileName,type);
                            }

                        }
                        var activeCount = $('#attachmentFile').fileupload('active');
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

        function removeAttachment(fileId){
            if(confirm("确定要删除数据吗？")){
                var url = '${base}/file/remove.f';
                $.post(url,{fileIds:fileId},function(data){
                    if(data!=null&&data=='true'){
                        $('#attachmentsTitleUpload div[class=attachment'+fileId+']').each(function(){
                            $(this).remove();
                        });
                        $('#attachmentsUpload div[class=attachment'+fileId+']').each(function(){
                            $(this).remove();
                        });
                        $('#attachmentsOperationUpload div[class=attachment'+fileId+']').each(function(){
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

        /*function getFileTypeName(fileType){
            return fileTypeMap[fileType];
        }*/
        <#--function insertAttachment(fileId,fileName,fileType){-->
            <#--$("#fileTitle").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/></div>");-->
            <#--$("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${core}/transfile/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");-->
        <#--}-->

        function insertAttachment(fileId,fileName){
            if(fileName.length>40){
                fileName = fileName.substring(0,40)+"...";
            }
//            $("#fileTitle").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/></div>");
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${core}/transfile/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
        }

        function insertAttachmentUpload(fileId,fileName,fileType){
            $("#attachmentsTitleUpload").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/></div>");
            $("#attachmentsUpload").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
            $("#attachmentsOperationUpload").prepend('<div class="attachment'+fileId+'"><a class="btn btn-link" href="javascript:removeAttachment(\''+fileId+'\')">删除</a><br/></div>');
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/verify/list?status=9&dealStatus=30')">审核管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞得人附件材料</span>
</nav>

<table class="table table-border table-bordered" >
    <tr>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">竞得人账号：</label></td>
        <td>${transUser.userName!}</td>
        <input type="hidden" id="userId" value="${transUser.userId}"/>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">竞得人：</label></td>
        <td>${transUser.viewName!}</td>
    </tr>
    <tr>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系人：</label></td>
        <td>${transUserApplyInfo.contactPerson!}</td>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系方式：</label></td>
        <td>${transUserApplyInfo.contactTelephone!}</td>
    </tr>
    <tr>
        <td width="100px" style="background-color: #F5F5F5;">
            原始附件
        </td>
        <td width="600px" colspan="3">
            <div id="fileTitle" style="float:left">
            </div>
            <div id="attachments" style="float:left">
            </div>
        </td>
    </tr>
    <#--<tr>
        <td rowspan="2" style="background-color: #F5F5F5;"><label class="control-label">上传附件：</label></td>
        <td style="width: 200px">
            <span style="color: red;">注：只允许上传以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过10M</span>
        </td>
        <td colspan="2" style="padding-left:8px;">
            <div style="float: left;">
                <label class="control-label">附件类型：</label>
                <select id="attachmentTypes">
                    <option value="CJQRS" selected="selected">成交确认书</option>
                    <option value="QTCL" >其他材料</option>
                </select>
            </div>
            <div id="attachmentBtn">
                <span class="btn-upload" style="height:60px;margin-left: 10px">
                    <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                    <input id="attachmentFile" name="file" type="file" multiple="true" class="input-file"
                           accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf">
                </span>
            </div>
            <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                <span class="sr-only"/>
            </div>
        </td>
    </tr>
    <tr style="height: 90px">
        <td colspan="3" style="padding-left:8px;">
            <div id="attachmentsTitleUpload" style="float: left;">
            </div>
            <div id="attachmentsUpload" style="float: left;text-align: center;">
            </div>
            <div id="attachmentsOperationUpload" style="float: left;">
            </div>
        </td>
    </tr>-->
</table>
<select id="attachmentType" style="display:none">
<#list attachmentTypeList?keys as key>
    <option value="${key}" <#if key_index==0>selected</#if>>${attachmentTypeList[key]}</option>
</#list>
</select>
</body>
</html>