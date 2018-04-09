<html>
<head>
    <title>-互动交流</title>
    <meta name="menu" content="menu_admin_communionlist"/>
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


</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/communion/list">互动交流</a>

</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" >
        <table class="table table-border table-bordered table-striped">
            <tr>
                <td width="120"><label class="control-label">编号：</label></td>
                <td><input type="text" readonly="true" class="input-text" name="publicNo" value="${communion.publicNo!}"
                           style="width:100%" maxlength="32"></td>
            </tr>


            <tr>
                <td><label class="control-label">标题：</label></td>
                <td><input readonly="true" type="text" class="input-text" name="publicTitle" value="${communion.publicTitle!}"
                           style="width:100%" maxlength="32"></td>
            </tr>

            <tr>
                <td><label class="control-label">接受时间：</label></td>
                <td><input type="text" name="publicTime" class="input-text Wdate"
                           value="${communion.publicTime?string("yyyy-MM-dd HH:mm:ss")}">
                </td>
            </tr>

            <tr>
                <td><label class="control-label">联系人：</label></td>
                <td><input readonly="true" type="text" class="input-text" name="contacter" value="${communion.contacter!}"
                           style="width:100%" maxlength="32"></td>
            </tr>
            <tr>
                <td><label class="control-label">联系方式：</label></td>
                <td><input readonly="true" type="text" class="input-text" name="phoneNum" value="${communion.phoneNum!}"
                           style="width:100%" maxlength="32"></td>
            </tr>

            <tr>
                <td><label class="control-label">内容：</label></td>
                <td >
                <textarea readonly="true" type="text"  name="publicContent" style="width: 100%;height: 120px"
                          rows="50" maxlength="1000">${communion.publicContent!}</textarea>
                </td>
            </tr>
            <tr>
                <td><label class="control-label">回复时间：</label></td>
                <td><input type="text"  id="replyTime" name="replyTime" class="input-text Wdate"
                           readonly="true"   value="<#if communion.replyTime??>${communion.replyTime?string("yyyy-MM-dd HH:mm:ss")}</#if>">
                </td>
            </tr>


            <tr >
                <td><label class="control-label">回复内容：</label></td>
                <td >
                <textarea type="text" id="replyContent" name="replyContent"    style="width: 100%;height: 120px"
                          readonly="true"  rows="50" maxlength="1000">${communion.replyContent!}</textarea>
                </td>
            </tr>
            <input type="hidden" name="communionId" value="${communion.communionId!}">
        </table>
</form>
</body>
</html>