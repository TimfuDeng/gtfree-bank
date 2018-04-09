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

    <script type="text/javascript">
        $(document).ready(function () {
        <#if _result?? && _result>
            _alertResult('ajaxResultDiv',${_result?string('true','false')}, '${_msg!}');
        </#if>

        });

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


    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/role/list">角色管理</a>

</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/role/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
        <td><label class="control-label">角色编号：</label></td>
        <td><input type="text" class="input-text" name="roleNo"  readonly="true"  value="${transRole.roleNo!}"
                   style="width:100%"></td>

            <td width="180px"><label class="control-label">角色名称：</label></td>
            <td width="300px"><input type="text" class="input-text" name="roleName" readonly="true" value="${transRole.roleName!}"
                                     style="width:100%"></td>

         </tr>


      <#--  <tr>
            <td><label class="control-label">添加人员：</label></td>
            <td colspan="3">
                <table style="border-style: none">
                <#if transUserList?? >
                    <#list transUserList as transUser>
                        <#if transUser_index%5==0>
                            <#if transUser_index!=0>
                                </tr>
                            </#if>
                        <tr >
                        </#if>
                        <td style="border-style: none;background-color: #FFF"> <input disabled="disabled" <#if transUser.foo==true> checked="checked" </#if>   type="checkbox" name="userIds" value="${transUser.userId}">${transUser.viewName!}</td>
                        <#if !transUser_has_next>
                        </tr>
                        </#if>
                    </#list>
                </#if>
                </table>
            </td>
        </tr>-->

        <tr>
            <td><label class="control-label">备注信息：</label></td>
            <td colspan="3">
                <textarea type="text"  name="roleComment"  readonly="true" style="width: 100%"
                          rows="15">${transRole.roleComment!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" name="roleId" value="${transRole.roleId!}">


</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
</body>
</html>