<html>
<head>
    <title>-审核地块</title>
    <meta name="menu" content="menu_admin_verify"/>
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
            if($.trim($("#verifySuggestion").val())==""){
                alert("审核内容必须填写！");
                return false;
            }else{
                return true;
            }
        }
        function setAndReloadStatus () {
            if (checkForm()) {
                $.ajax({
                    url: "${base}/verify/save",
                    data: $("#verify_form").serialize(),
                    type: "post",
                    success: function(data){
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if (data.flag) {
                            changeSrc('${base}/verify/list');
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="javascript:changeSrc('${base}/index')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/verify/list')">审核管理</a>
    <span class="c_gray en">&gt;</span>审核地块
</nav>
<form id="verify_form" action="${base}/verify/save">
    <table  class="table table-border table-bordered table-striped">
        <tr style="margin: 5px">
            <td>
                <label class="control-label">审核人</label>
            </td>
            <td>
            ${WebUtil.loginUserViewName}
                <input type="hidden" name="auditor" value="${WebUtil.loginUserId!}" />
            </td>
        </tr>
        <tr style="margin: 5px">
            <td>
                <label class="control-label">审核时间</label>
            </td>
            <td>
            ${verifyTime!}
                <input type="hidden" name="verifyTime" value="${verifyTime!}">
            </td>
        </tr>
        <tr style="margin: 5px">
            <div style="margin-top: 5px">
                <td>
                    <label class="control-label">是否审核通过</label>
                </td>
                <td>
                    <input type="radio" name="verifyStatus" value="1" checked="true"/>通过&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="verifyStatus" value="2"/>不通过
                </td>
            </div>
        </tr>
        <tr style="margin: 5px">
            <td>审核意见</td>
            <td><textarea placeholder="请输入审核意见..." rows="5" cols="20" id="verifySuggestion" name="verifySuggestion" style="width: 100%;margin-top: 5px;resize: none"></textarea></td>
        </tr>
    </table>
    <input type="hidden" name="resourceId" value="${resourceId!}"/>
    <input type="hidden" name="offerId" value="${offerId!}"/>
    <input type="hidden" name="userId" value="${userId!}"/>
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="setAndReloadStatus()">保存</button>
            <button type="button" class="btn" onclick="changeSrc('${base}/verify/list')">返回</button>
        </div>
    </div>
</form>
</body>
</html>