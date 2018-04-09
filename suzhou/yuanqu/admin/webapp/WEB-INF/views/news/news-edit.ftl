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

        .error_input{
            background-color: #FFFFCC;
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

        function checkForm(){
            checkInputFileter();
            if (!checkInputNull('newsTitle','新闻主标题必须填写!'))
                return false;
            if (!checkInputNull('newsSubHead','新闻副标题必须填写!'))
                return false;
            if (!checkInputNull('newsContent','新闻内容必须填写!'))
                return false;
            return true;
        }
    </script>
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
            <td><input type="text" class="input-text" name="newsTitle" value="${transNews.newsTitle!}"
                       style="width:100%;;background-color: #FFFFCC" maxlength="50"></td>
            <td><label class="control-label">新闻副标题：</label></td>
            <td><input type="text" class="input-text" name="newsSubHead" value="${transNews.newsSubHead!}"
                       style="width:100%;;background-color: #FFFFCC" maxlength="50"></td>
        </tr>
        <tr>
            <td><label class="control-label">作者：</label></td>
            <td><input type="text" class="input-text" name="newsAuthor" value="${transNews.newsAuthor!}"
                       style="width:100%" maxlength="32"></td>
            <td><label class="control-label">发布时间：</label></td>
            <td><input type="text" name="newsReportTime" class="input-text Wdate"
                       value="${transNews.newsReportTime?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">
            </td>
        </tr>


        <tr>
            <td><label class="control-label">新闻内容：</label></td>
            <td colspan="3">
                <textarea type="text" id="newsContent" name="newsContent" class="xheditor" style="width: 100%;background-color: #FFFFCC"
                          rows="50">${transNews.newsContent!}</textarea>
            </td>
        </tr>


    </table>

    <input type="hidden" name="newsId" value="${transNews.newsId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/news/list'">返回</button>
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