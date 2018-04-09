<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>附件材料</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/jquery/jquery.min.js"></script>
    <style type="text/css">
        table p {
            margin-bottom: 0px;S
        }

        thead td {
            text-align: center !important;
            background-color: #f5fafe;
        }
        #attachments{width: 500px;float: left;overflow:hidden;text-overflow-ellipsis: '..';}
        #attachmentsOperation{float: right}

    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            getAttachments('${applyId!}');
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
        <#--function insertAttachment(fileId,fileName){-->
            <#--$("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");-->
        <#--}-->

        function insertAttachment(fileId,fileName){
            if(fileName.length>40){
                fileName = fileName.substring(0,40)+"...";
            }
//            $("#fileTitle").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/></div>");
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${core}/transfile/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
        }
    </script>
</head>
<body>
<div class="wp">
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span>
    <a href="${base}/my/resource-list" class="maincolor">我的竞拍地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">附件材料</span>
</nav>
<table class="table table-border table-bordered">
    <tr>
        <td width="100px" style="background-color: #F5F5F5;">
            原始附件
        </td>
        <td width="600px" colspan="3">
            <div id="attachments" style="float:left">
            </div>
        </td>
    </tr>
</table>
</div>
</body>
</html>