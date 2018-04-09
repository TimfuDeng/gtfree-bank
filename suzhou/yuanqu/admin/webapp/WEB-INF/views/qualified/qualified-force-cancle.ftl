<html>
<head>
    <title>-资格审核</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
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
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/land">资格审核</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/resourceApply?resourceId=${resourceId!}">竞买人审核</a>
    <span class="c_gray en">&gt;</span>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<form class="form-base" method="post" action="${base}/console/qualified/backEdit/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td><label class="control-label">竞买人账号：</label></td>
            <td>${transUser.userName!}</td>
            <td><label class="control-label">竞买人：</label></td>
            <td>${transUser.viewName!}</td>
        </tr>
        <tr>
            <td><label class="control-label">联系人：</label></td>
            <td>${transUserApplyInfo.contactPerson!}</td>
            <td><label class="control-label">联系方式：</label></td>
            <td>${transUserApplyInfo.contactTelephone!}</td>
        </tr>

        <tr>
            <td width="180px"><label class="control-label">竞买方式：</label></td>
            <td width="300px">
            <#if transResourceApply.applyType==0>
                <span>独立竞买</span>
            <#else>
                <span>联合竞买</span>
            </#if>
            </td>
            <td width="180px"><label class="control-label">所选币种：</label></td>
            <td width="300px">
            <#if transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="CNY">
                <span>人民币</span>
            <#elseif transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="USD">
                <span>美元</span>
            <#elseif transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="HKD">
                <span>港币</span>
            </#if>
            </td>

        </tr>
    <#if transResourceApply.applyType??&&transResourceApply.applyType==1&&transUserUnionList??>
        <#list transUserUnionList as transUserUnion>
            <tr>
                <td width="180px"><label class="control-label">被联合人：</label></td>
                <td width="300px">
                ${transUserUnion.userName!}
                </td>
                <td width="180px"><label class="control-label">被联合人地址：</label></td>
                <td width="300px">
                ${transUserUnion.userAddress!}
                </td>
            </tr>
        </#list>
    </#if>

        <tr>
            <td width="180px"><label class="control-label">强制退回人：</label></td>
            <td width="300px">${WebUtil.loginUserViewName!}</td>
            <td width="180px"><label class="control-label">强制退回时间：</label></td>
            <td width="300px">
                <input type="text" name="qualifiedTime" class="input-text Wdate"
                       value="${transBuyQualified.qualifiedTime?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})">
            </td>
        </tr>

        <tr>

            <td>
                <label class="control-label">是否强制退回：</label>
            </td>
            <td>
                <input  type="radio" name="qualifiedStatus" value="2" <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==2 >checked="true" </#if> />是
                <input type="radio" name="qualifiedStatus" value="3" <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==3 >checked="true" </#if> />否
            </td>
            <td>
            </td>
            <td>
            </td>



        </tr>

        <tr id="qualifiedContacter">
            <td><label class="control-label">强制退回人联系方式：</label></td>
            <td colspan="3"><input type="text" name="contacter" class="input-text" value="${transBuyQualified.contacter!}"></td>
        </tr>

        <tr id="qualifiedReason">
            <td><label class="control-label">强制退回原因：</label></td>
            <td colspan="3">
                <textarea type="text"  name="qualifiedReason"  style="width: 100%"
                          rows="15">${transBuyQualified.qualifiedReason!}</textarea>
            </td>
        </tr>
        <input type="hidden" name="qualifiedId" value="${transBuyQualified.qualifiedId!}">
        <input type="hidden" name="qualifiedAuditor" value="${WebUtil.loginUserViewName!}">
        <input type="hidden" name="infoId" value="${infoId!}">
        <input type="hidden" name="resourceId" value="${resourceId!}">
        <input type="hidden" name="applyId" value="${applyId!}">


    </table>



    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/qualified/list/resourceApply?resourceId=${resourceId!}'">返回</button>
        </div>
    </div>
</form>

<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel"><i class="icon-warning-sign"></i> 确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
    <#if _result?? && _result>
        _alertResult('ajaxResultDiv',${_result?string('true','false')}, '${_msg!}');
    </#if>
        qualifiedStatusChange();
    });


    function qualifiedStatusChange(){
        if("${transBuyQualified.qualifiedStatus}"==3){
            $("#qualifiedReason").hide()
            $("#qualifiedContacter").hide();
        }else{
            $("#qualifiedReason").show();
            $("#qualifiedContacter").show();
        }

        $("input[name='qualifiedStatus']").change(function(){
            if($(this).val()==2){
                $("#qualifiedReason").show();
                $("#qualifiedContacter").show();
            }else{
                $("#qualifiedReason").hide();
                $("#qualifiedContacter").hide();
            }
        });
    }

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
        if(!checkInputNull('contacter','联系人必须填写!'))
            return false;
        return confirm("是否确认保存！");
    }
</script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>