<html>
<head>
    <title>-新闻管理</title>
    <meta name="menu" content="menu_admin_newslist"/>
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
    <span class="c_gray en">&gt;</span><a href="${base}/console/news/list">新闻管理</a>

</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/news/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="120"><label class="control-label">新闻主标题：</label></td>
            <td><input type="text" class="input-text" name="newsTitle" readonly="true" value="${transNews.newsTitle!}"
                       style="width:100%"></td>
            <td><label class="control-label">新闻副标题：</label></td>
            <td><input type="text" class="input-text" name="newsSubHead"readonly="true"  value="${transNews.newsSubHead!}"
                       style="width:100%"></td>
        </tr>
        <tr>
            <td><label class="control-label">作者：</label></td>
            <td><input type="text" class="input-text" name="newsAuthor" readonly="true" value="${transNews.newsAuthor!}"
                       style="width:100%"></td>
            <td><label class="control-label">发布时间：</label></td>
            <td><input type="text" name="newsReportTime" readonly="true" class="input-text "
                       value="${transNews.newsReportTime?string("yyyy-MM-dd HH:mm:ss")}">
            </td>
        </tr>

        <#--
        <tr>
            <td><label class="control-label">新闻内容：</label></td>
            <td colspan="3">
                <textarea type="text" id="newsContent" name="newsContent" readonly="true" style="width: 100%"
                          rows="50">${transNews.newsContent!}</textarea>
            </td>
        </tr>-->

        <tr>
            <td><label class="control-label">新闻内容：</label></td>
            <td colspan="3">
                <textarea type="text" id="newsContent" name="newsContent" class="xheditor" style="width: 100%;background-color: #FFFFCC"
                          rows="50">${transNews.newsContent!}</textarea>
            </td>
        </tr>

    </table>
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="window.location.href='${base}/console/news/list'">返回</button>
        </div>
    </div>

    <input type="hidden" name="newsId" value="${transNews.newsId!}">


</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
</body>
</html>