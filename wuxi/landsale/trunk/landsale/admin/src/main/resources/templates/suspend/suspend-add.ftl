<html>
<head>
    <title>-中止公告</title>
    <meta name="menu" content="menu_admin_suspendNotice"/>
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
            $.ajaxSetup ({
                cache: false //关闭AJAX相应的缓存
            });
        });

        function submitForm () {
            if (checkForm()) {
                var url = "${base}/suspendNotice/addSuspend";
                if (!isEmpty($("#noticeId").val())) {
                    url = "${base}/suspendNotice/editSuspend";
                }
                $.ajax({
                    type: "post",
                    url: url,
                    data: $("form").serialize(),
                    success: function (data) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if (data.flag) {
                            $("#noticeId").val(data.empty.noticeId);
                            $("#deployStatus").val(data.empty.deployStatus);
                            $("#resourceId").val(data.empty.resourceId);
                        }
                    }
                });
            }
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
            if (!checkInputNull('ggTitle','中止公告标题必须填写!')) {
                return false;
            }else if(!checkInputNull('ggNum','公告编号必须填写!')){
                return false;
            }else if(!checkInputNull('resourceCode','地块必须填写!')){
                return false;
            }else if(!checkInputNull('passMan','作者必须填写!')){
                return false;
            }else if(!checkInputNull('postDate','发布时间必须填写!')){
                return false;
            } else if($("#ggContent").val()==""){
                alert("中止公告内容必须填写！");
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
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/suspendNotice/index');">中止(终止)公告</a>
    <#--<span class="c_gray en">&gt;</span><a href="javascript:jumpToSuspend();">中止公告</a>-->
</nav>
<form class="form-base" method="post">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="120"><label class="control-label">中止(终止)公告标题：</label></td>
            <td><input type="text" class="input-text"  name="ggTitle" value="${suspendNotice.ggTitle!}"
                                   style="width:100%;background-color: #FFFFCC" maxlength="100"></td>
            <td width="120"><label class="control-label">公告类型：</label></td>
            <td>
                <span class="select-box">
                <select name="afficheType" class="select" >
                    <option value="8" <#if suspendNotice.afficheType?? && suspendNotice.afficheType=='8' >selected="selected"</#if>>中止公告</option>
                    <option value="9" <#if suspendNotice.afficheType?? && suspendNotice.afficheType=='9' >selected="selected"</#if>>终止公告</option>
                  </select>
            </span>
            </td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">公告编号：</label></td>
            <td><input type="text" class="input-text" name="ggNum" value="${suspendNotice.ggNum!}"
                       style="width:100%;;background-color: #FFFFCC" maxlength="32"></td>
            <td width="120"><label class="control-label">地块编号：</label></td>
            <td>
                <#if suspendNotice.resourceCode??>
                    <input type="text" class="input-text" name="resourceCode" value="${suspendNotice.resourceCode!}" style="width:100%;;background-color: #FFFFCC" readonly="readonly">
                <#else>
                    <input type="text" class="input-text" name="resourceCode" value="${suspendNotice.resourceCode!}" style="width:100%;;background-color: #FFFFCC" maxlength="32">
                </#if>
            </td>
        </tr>

        <tr>
            <td width="120"><label class="control-label">作者：</label></td>
            <td><input type="text" class="input-text" name="passMan" value="${suspendNotice.passMan!}"
                       style="width:100%;;background-color: #FFFFCC" maxlength="32"></td>
            <td><label class="control-label">发布时间：</label></td>
            <td>
            <#if suspendNotice.ggId??>
                <input type="text" name="postDate" class="input-text Wdate"
                       value="${(suspendNotice.postDate!)?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">
            <#else>
                <input type="text" name="postDate" class="input-text Wdate"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})" readonly="readonly">
            </#if>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">中止(终止)公告详情：</label></td>
            <td colspan="3">
                <textarea type="text" id="ggContent" name="ggContent" class="xheditor" style="width: 100%;;background-color: #FFFFCC"
                          rows="50">${suspendNotice.ggContent!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" id="noticeId" name="ggId" value="${suspendNotice.ggId!}"/>
    <input type="hidden" id="deployStatus" name="crggStauts" value="${suspendNotice.crggStauts!}"/>
    <#--<input type="hidden" id="resourceId" name="resourceId" value="${suspendNotice.resourceId!}"/>-->


    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="submitForm();">保存</button>
            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/suspendNotice/index');">返回</button>
            <#--<button type="button" class="btn" onclick="jumpToSuspend();">返回</button>-->
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>