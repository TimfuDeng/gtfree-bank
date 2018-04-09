<html>
<head>
    <title>竞买人附件材料</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
    <script type="text/javascript">
        var filemaps;
        $(document).ready(function () {
            $.ajaxSetup({cache:false});
            getAttachments('${applyId!}');
            fileTypeMap = {};
            $('#attachmentType option').each(function(){
                fileTypeMap[$(this).val()]=$(this).text();
            });
        });

        function getAttachments(applyId){
            var url ='${base}/file/attachments.f?fileKey='+applyId;
            $.get(url,function(data){
                $('#attachments').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachment(data[i].fileId,data[i].fileName,data[i].fileType);
                    }

                }
            })
        }
        function getFileTypeName(fileType){

            var fileType = fileTypeMap[fileType];
            if(fileType.length>30){
                fileType = fileType.substring(0,30)+"...";
            }
            return fileType;
        }
        function insertAttachment(fileId,fileName,fileType){
            if(fileName.length>40){
                fileName = fileName.substring(0,40)+"...";
            }
            $("#fileTitle").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/></div>");
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/land">资格审核</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/resourceApply?resourceId=${resourceId!}">人员审核</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">附件材料</span>
</nav>
<table class="table table-border table-bordered" >
    <tr>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">竞买人账号：</label></td>
        <td>${transUser.userName!}</td>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">竞买人：</label></td>
        <td>${transUser.viewName!}</td>
    </tr>
    <#if transUserApplyInfo??>
    <tr>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系人：</label></td>
        <td>${transUserApplyInfo.contactPerson!}</td>
        <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系方式：</label></td>
        <td>${transUserApplyInfo.contactTelephone!}</td>
    </tr>
    </#if>
    <tr>
        <td width="100px" style="background-color: #F5F5F5;">
            附件材料
        </td>
        <td width="600px" colspan="3">
            <div id="fileTitle" style="float:left">
            </div>
            <div id="attachments" style="float:left">
            </div>
        </td>
    </tr>
</table>
<select id="attachmentType" style="display:none">
<#list attachmentTypeList?keys as key>
    <option value="${key}" <#if key_index==0>selected</#if>>${attachmentTypeList[key]}</option>
    <option value="SFZ">身份证(个人)</option>
    <option value='GRQTZMCL'>其他证明材料(个人)</option>
</#list>
</select>
</body>
</html>