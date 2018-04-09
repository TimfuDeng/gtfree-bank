<table class="table table-border table-bordered">
    <tr>
        <td  width="200px" style="background-color: #F5F5F5;">
            附件材料
        </td>
        <td>
                        <span>
                            <span id="personalText">个人申请，须上传如下材料:<#list materialPersonal as personal> ${personal_index+1}、${personal.materialName};</#list>。<br/></span>
                            <span id="groupText">单位申请，须上传如下材料：<#list materialGroup as group> ${group_index+1}、${group.materialName};</#list><br/></span>
                            <span style="color: red">注：只允许传入以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过10M</span>
                        </span>
            <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                <span class="sr-only"/>
            </div>
        </td>
        <td width="600px">
            <div>
                <div id="DW"></div>
                <div id="GR"></div>
            </div>

            <#--<div style="background-color: #bbbbbb">
                <div id="attachmentsTitle" style="float: left;">
                </div>
                <div id="attachments" style="float: left;width:350px;text-align: center;">
                </div>
                <div id="attachmentsOperation" style="float: left;">
                </div>
            </div>-->
        </td>
    </tr>

</table>