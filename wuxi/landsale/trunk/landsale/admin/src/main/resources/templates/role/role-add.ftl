<html>
<head>
    <title>-角色管理</title>
    <meta name="menu" content="menu_admin_rolelist"/>
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

    <script type="text/javascript">
        /*$(document).ready(function () {
            _alertResult('ajaxResultDiv', true, "保存成功！");
        });*/

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
            if (!checkInputNull('roleName','角色名称必须填写!'))
                return false;
            if (isEmpty($("#organizeId").val())) {
                alert("所属组织必须选择！");
                return false;
            }
            submitForm();
        }

        function submitForm() {
            var url;
            if (isEmpty($("#roleId").val())) {
                url = "${base}/role/addRole";
            } else {
                url = "${base}/role/editRole";
            }
            $.ajax({
                type: "post",
                url: url,
                data: $("form").serialize(),
                success: function (data) {
                    $("#roleId").val(data.transRole.roleId);
                    _alertResult('ajaxResultDiv', data.result, data.msg);
                }
            });
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/role/index')">角色管理</a>

</nav>

<#--<div id="ajaxResultDiv" style="display:none"></div>-->
<form  defaultHtmlEscape="true"  class="form-base" method="post" action="${base}/role/save">
    <#--spring:htmlEscape-->
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td><label class="control-label">角色名称：</label></td>
            <td><input type="text" class="input-text" name="roleName" value="${transRole.roleName!}"
                       style="180px; background-color: #ffffcc;" maxlength="25"></td>
            <td width="180px"><label class="control-label">所属组织：</label></td>
            <td width="300px">
            <#--<#if organizeId! == organize.organizeId!>selected="selected" </#if>
            ${organize.organizeId!}
            ${organizeId!}-->
                <select style="background-color: background-color: #ffffcc;" id="organizeId" name="organizeId" class="select" >
                    <option style="background-color: background-color: #ffffcc;" value="">--请选择--</option>
                    <#list transOrganizeList as organize>
                        <option <#if organizeId! == organize.organizeId!>selected="selected" </#if> value="${organize.organizeId!}">${organize.organizeName!}</option>
                    </#list>
                </select>
                <#--<input type="text" class="input-text" name="roleName" value="${transRole.roleName!}"
                                     style="180px;" maxlength="32">--></td>
        </tr>
        <tr>
            <td><label class="control-label">备注信息：</label></td>
            <td colspan="3">
                <textarea type="text"  name="roleDescribe"  style="width: 100%"
                          rows="15" maxlength="64">${transRole.roleDescribe!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" id="roleId" name="roleId" value="${transRole.roleId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="checkForm()">保存</button>
            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/role/index')">返回</button>
        </div>
    </div>
</form>

</body>
</html>