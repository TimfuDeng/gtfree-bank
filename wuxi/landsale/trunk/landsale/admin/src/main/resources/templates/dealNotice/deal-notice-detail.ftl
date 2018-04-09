<html>
<head>
    <title>-成交公示</title>
    <meta name="menu" content="menu_admin_dealNotice"/>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
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
    <script type="text/javascript" src="${base}/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
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
            if (!checkInputNull('noticeTitle','成交公示标题必须填写!')) {
                return false;
            }else if(!checkInputNull('noticeNum','公告编号必须填写!')){
                return false;
            }else if(!checkInputNull('resourceCode','地块必须填写!')){
                return false;
            }else if(!checkInputNull('noticeAuthor','作者必须填写!')){
                return false;
            }else if(!checkInputNull('deployTime','发布时间必须填写!')){
                return false;
            } else if($("#noticeContent").val()==""){
                alert("成交公示内容必须填写！");
                return false;
            }else{
                return true;
            }
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/dealNotice/index')">成交公示</a>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/dealNotice/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="120"><label class="control-label">成交公示标题：</label></td>
            <td colspan="6"><input type="text" class="input-text"  name="noticeTitle" value="${dealNotice.noticeTitle!}"
                                   style="width:100%" readonly="true"></td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">公告编号：</label></td>
            <td><input type="text" class="input-text" name="noticeNum" value="${dealNotice.noticeNum!}"
                       style="width:100%" readonly="true"></td>
            <td width="120"><label class="control-label">地块编号：</label></td>
            <td><input type="text" class="input-text" name="resourceCode" value="${dealNotice.resourceCode!}"
                       style="width:100%" readonly="true"></td>
        </tr>

        <tr>
            <td width="120"><label class="control-label">作者：</label></td>
            <td><input type="text" class="input-text" name="noticeAuthor" value="${dealNotice.noticeAuthor!}"
                       style="width:100%;" readonly="true"></td>
            <td><label class="control-label">发布时间：</label></td>
            <td>
                <input type="text" name="deployTime" value="${(dealNotice.deployTime!)?string("yyyy-MM-dd HH:mm:ss")}" class="input-text" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">成交公示详情：</label></td>
            <td colspan="3">
                <textarea type="text" id="noticeContent" name="noticeContent" class="xheditor" style="width: 100%;;background-color: #FFFFCC"
                          rows="50">${dealNotice.noticeContent!}</textarea>
            </td>
        </tr>

    </table>

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="javascript:changeSrc('${base}/dealNotice/index')">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>

</body>
</html>
