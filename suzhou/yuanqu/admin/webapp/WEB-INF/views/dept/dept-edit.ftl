<html>
<head>
    <title>-机构管理</title>
    <meta name="menu" content="menu_admin_deptlist"/>
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

        .error_input{
            background-color: #FFFFCC;
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

        function checkForm(){
            checkInputFileter();
            if (!checkInputNull('deptName','机构名称必须填写!'))
                return false;
            if (!checkInputNull('deptNo','机构编号必须填写!'))
                return false;
            if (!checkInputNull('deptAddress','机构地址必须填写!'))
                return false;
            if (!checkInputNull('deptType','机构类型必须填写!'))
                return false;
            return true;
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/dept/list">机构管理</a>

</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/dept/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td><label class="control-label">机构编号：</label></td>
            <td><input type="text" class="input-text" name="deptNo"    value="${transDept.deptNo!}"
                       style="width:100%;background-color: #ffffcc" maxlength="32"></td>
            <td width="180px"><label class="control-label">机构名称：</label></td>
            <td><input type="text" class="input-text" name="deptName" value="${transDept.deptName!}"
                       style="width:100%;background-color: #ffffcc" maxlength="50"></td>

        </tr>
        <tr>
            <td><label class="control-label">机构地址：</label></td>
            <td><input type="text" class="input-text" name="deptAddress" value="${transDept.deptAddress!}"
                       style="width:100%;background-color: #ffffcc" maxlength="255"></td>
            <td><label class="control-label">机构类型：</label></td>
            <td>
                <select class="select" style="width: 100%" name="deptType">
                    <option value="0">请选择</option>
                    <option value="1" <#if transDept.deptType?? && transDept.deptType==1 >selected="selected"</#if>>国土局</option>
                    <option value="2" <#if transDept.deptType?? && transDept.deptType==2 >selected="selected"</#if>>土地储备中心</option>
                    <option value="3" <#if transDept.deptType?? && transDept.deptType==3 >selected="selected"</#if>>金融机构</option>
                    <option value="4" <#if transDept.deptType?? && transDept.deptType==4 >selected="selected"</#if>>监管部门</option>
                    <option value="5" <#if transDept.deptType?? && transDept.deptType==5 >selected="selected"</#if>>其它机构</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">机构负责人姓名：</label></td>
            <td><input type="text" class="input-text" name="deptChargeName" value="${transDept.deptChargeName!}"
                       style="width:100%" maxlength="32"></td>
            <td><label class="control-label">机构负责人联系方式：</label></td>
            <td><input type="text" class="input-text" name="deptChargePhone" placeholder="不填或填写正确的号码" pattern="((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)" value="${transDept.deptChargePhone!}"
                       style="width:100%" maxlength="32"></td>
        </tr>

        <tr>
            <td><label class="control-label">所辖岗位：</label></td>
            <td><input type="text" class="input-text" name="deptJurisdictionPosition" value="${transDept.deptJurisdictionPosition!}"
                       style="width:100%" maxlength="32"></td>
            <td></td>
            <td></td>
        </tr>

        <tr>
            <td><label class="control-label">备注信息：</label></td>
            <td colspan="3">
                <textarea type="text"  name="deptComment" class="xheditor" style="width: 100%"
                          rows="25">${transDept.deptComment!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" name="deptId" value="${transDept.deptId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/dept/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
</body>
</html>