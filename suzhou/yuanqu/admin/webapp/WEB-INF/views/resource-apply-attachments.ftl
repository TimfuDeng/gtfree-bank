<html>
<head>
    <title>竞买人附件材料</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
    <script type="text/javascript">
        $(document).ready(function () {
            getAttachments('${applyId!}');
        });

        function getAttachments(applyId){
            var url ='${base}/file/attachments.f?fileKey='+applyId;
            $.get(url,function(data){
                $('#attachments').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachment(data[i].fileId,data[i].fileName);
                    }

                }
            })
        }
        function insertAttachment(fileId,fileName){
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource/list">出让地块</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource-apply/list?resourceId=${resourceId!}">竞买人列表</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">附件材料</span>
</nav>
<table class="table table-border table-bordered" >
    <tr>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">竞买人账号：</label></td>
        <td>${transUser.userName!}</td>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">竞买人：</label></td>
        <td>${transUser.viewName!}</td>
    </tr>
    <tr>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系人：</label></td>
        <td>${transUserApplyInfo.contactPerson!}</td>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">手机号码：</label></td>
        <td>${transUserApplyInfo.contactTelephone!}</td>
    </tr>
    <tr>
        <td width="100px" style="background-color: #F5F5F5;">
            附件材料
        </td>
        <td width="600px" colspan="3">
            <div id="attachments">
            </div>
        </td>
    </tr>
</table>
</body>
</html>