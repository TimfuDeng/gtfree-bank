<html>
<head>
    <title>-出让公告</title>
    <meta name="menu" content="menu_admin_crgglist"/>
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
    <script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor-1.2.2.min.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/xheditor/xheditor_lang/zh-cn.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var ggId = '${transCrgg.ggId!}';
            if (ggId == null || ggId == '') {
                beginDateChange();
            }


            getCheckedMaterials();
            //regionChanged();

        <#if _result?? && _result>
            _alertResult('ajaxResultDiv',${_result?string('true','false')}, '${_msg!}');
        </#if>
            getAttachments('${transCrgg.ggId!}');
            showAttachmentUploader();
        });

        function showAttachmentUploader() {
            var ggId = '${transCrgg.ggId!}';
            if (ggId != null && ggId != '') {
                var url = '${base}/file/upload.f?fileKey=' + ggId;
                $('#fileName').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if (data != null && data != '') {
                            if (data.result.hasOwnProperty("ret"))
                                alert(data.result.msg);
                            else
                                insertAttachment(data.result.fileId, data.result.fileName);
                        }
                        var activeCount = $('#fileName').fileupload('active');
                        if(activeCount==1){
                            resetProgressBar('attachmentProgressBar');
                            setProgressBarVisible('attachmentProgressBar',false);
                        }
                    },
                    progressall: function (e, data){
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        $('#attachmentProgressBar span').css(
                                'width',
                                progress + '%'
                        );
                    },
                    start:function(e){
                        setProgressBarVisible('attachmentProgressBar',true);
                    },
                    fail:function (e, data) {
                        resetProgressBar('attachmentProgressBar');
                        setProgressBarVisible('attachmentProgressBar',false);
                        alert('附件上传失败');
                    }
                });

            }
        }

        function beginDateChange(){
            var bDate=$("input[name='ggBeginTime']").val();
            bDate=new XDate(bDate);

            $("input[name='ggBeginTime']").val(bDate.toString("yyyy-MM-dd")+" 09:00:00");
            var ggEndDate=bDate.addDays(19);
            $("input[name='ggEndTime']").val(ggEndDate.toString("yyyy-MM-dd")+" 17:00:00");
            var signBeginDate=bDate.addDays(1);
            $("input[name='signStartTime']").val(signBeginDate.toString("yyyy-MM-dd")+" 09:00:00");
            var signEndDate=bDate.addDays(9);
            $("input[name='signEndTime']").val(signEndDate.toString("yyyy-MM-dd")+" 16:00:00");
        }

        function getAttachments(ggId) {
            var url = '${base}/file/list.f?fileKey=' + ggId;
            $.get(url, function (data) {
                $('#attachments').empty();
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        insertAttachment(data[i].fileId, data[i].fileName);
                    }

                }
            })
        }

        function checkTel(phoneNumber){
            var mobilePhone = /^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$/;
            var telePhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/
            if(mobilePhone.test(phoneNumber) || telePhone.test(phoneNumber)){
                return true;
            }else{
                alert("请输入正确的电话号码");
            }
        }

        function insertAttachment(fileId, fileName) {
            $("#attachments").prepend("<div class='attachment" + fileId + "'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" + fileId + "'>" + fileName + "</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment' + fileId + '"><a class="btn btn-link" href="javascript:removeAttachment(\'' + fileId + '\')">删除</a><br/></div>');
        }
        function removeAttachment(fileId) {
            if (confirm("确定要删除数据吗？")) {
                var url = '${base}/file/remove.f';
                $.post(url, {fileIds: fileId}, function (data) {
                    if (data != null && data == 'true') {
                        $('#attachments div[class=attachment' + fileId + ']').each(function () {
                            $(this).remove();
                        });
                        $('#attachmentsOperation div[class=attachment' + fileId + ']').each(function () {
                            $(this).remove();
                        });
                    }
                });
            }

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
            checkInputFileter();
            if (!checkInputNull('ggTitle','公告标题必须填写!'))
                return false;
            if (!checkInputNull('ggNum','公告编号必须填写!')) {
                return false;
            }
            if (!checkInputNull('signStartTime','报名开始时间必须填写!')) {
                return false;
            }
            if (!checkInputNull('signEndTime','报名截止时间必须填写!')) {
                return false;
            }
            if (!checkInputNull('ggBeginTime','公告开始时间必须填写!')) {
                return false;
            }
            if (!checkInputNull('ggEndTime','公告截止时间必须填写!')) {
                return false;
            }

            var phone = $("#linkPhone").val();
            if(phone!=""){
                if(!checkTel(phone)){
                    return false;
                }
            }
            return true;
        }

        function getCheckedMaterials(){
            var ggId =$("#ggId").val();
            $.ajax({
                url:"${base}/console/crgg/materials/checked",
                type:"post",
                data:{ggId:ggId},
                async:false,
                success:function(data){
                    if(data!=""){//如果没有选择默认选择所有
                        $.each(JSON.parse(data),function(k,v){
                            $("input[name='materialId']").each(function(){
                                if($(this).val()== v.materialId){
                                    $(this).attr("checked","checked");
                                }
                            });
                        });
                    }else{
                        checkedMaterials();
                    }
                },
                error:function(){
                    alert("error");
                }
            });
        }

        function regionChanged(){

            var regionCode = $("select[name='regionCode']").val();
            $("#groupLoad").load("${base}/console/crgg/getGroupLoad.f?regionCode="+regionCode,function(){
                checkedMaterials();
            });
            $("#personal").load("${base}/console/crgg/getPersonalLoad.f?regionCode="+regionCode,function(){
                checkedMaterials();
            });

        }

        /**
         *@annotation 默认选择所有
         *@author liushaoshuai【liushaoshuai@gtmap.cn】
         *@date 2017/5/18 20:39
         *@param
         *@return
         */
        function checkedMaterials(){
            $("input[name='materialId']").attr("checked","checked");
        }



        window.onload=function(){
            getCheckedMaterials();
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/crgg/list">出让公告</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transCrgg.ggNum!}</span>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/crgg/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="120"><span style="color: red;">*</span><label class="control-label">公告标题：</label></td>
            <td><input type="text" class="input-text" name="ggTitle" value="${transCrgg.ggTitle!}"
                                   style="width:100%;background-color: #FFFFCC" maxlength="200"></td>
            <td><span style="color: red;">*</span><label class="control-label">公告编号：</label></td>
            <td><input type="text" class="input-text" name="ggNum" value="${transCrgg.ggNum!}"
                                   style="width:100%;background-color: #FFFFCC" maxlength="32"></td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span><label class="control-label">公告类别：</label></td>
            <td>
                <span class="select-box">
                <select name="afficheType" class="select" >

                    <option value="0" <#if transCrgg.afficheType?? && transCrgg.afficheType==0> selected="selected"</#if>>工业用地公告</option>
                    <option value="1" <#if transCrgg.afficheType?? && transCrgg.afficheType==1> selected="selected"</#if>>经营性用地公告</option>
                    <option value="2" <#if transCrgg.afficheType?? && transCrgg.afficheType==2> selected="selected"</#if>>其它公告</option>
                    <option value="3" <#if transCrgg.afficheType?? && transCrgg.afficheType==3> selected="selected"</#if>>协议出让类（划拨）公告</option>
                   </select>
                </span>
            </td>
            <td><label class="control-label">资源类别：</label></td>
            <td>
                <span class="select-box">
                <select name="resourceLb" class="select" disabled="disabled">
                    <option value="01" <#if transCrgg.resourceLb?? && transCrgg.resourceLb=='01' >selected="selected"</#if>>国有建设用地使用权</option>
                   <#-- <option value="02" <#if transCrgg.resourceLb?? && transCrgg.resourceLb=='02' >selected="selected"</#if>>采矿权</option>
                    <option value="03" <#if transCrgg.resourceLb?? && transCrgg.resourceLb=='03' >selected="selected"</#if>>探矿权</option>-->
                  </select>
            </span>
            </td>
        </tr>

        <tr>
            <td><span style="color: red;">*</span><label class="control-label">公告开始时间：</label></td>
            <td><input type="text" id="ggBeginTime" name="ggBeginTime" class="input-text Wdate" onchange="beginDateChange()"
                       value="${transCrgg.ggBeginTime?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',maxDate:'#F{$dp.$D(\'ggEndTime\')}',alwaysUseStartDate:true})" readonly="readonly">

            </td>
            <td><span style="color: red;">*</span><label class="control-label">公告截止时间：</label></td>
            <td><input type="text" id="ggEndTime" name="ggEndTime" class="input-text Wdate"
                       value="${transCrgg.ggEndTime?string("yyyy-MM-dd HH:mm:ss")}"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 17:00:00',minDate:'#F{$dp.$D(\'ggBeginTime\')}',alwaysUseStartDate:true})" readonly="readonly">
            </td>
        </tr>

        <tr>
            <td><span style="color: red;">*</span><label class="control-label">报名开始时间：</label></td>
            <td>
                <input type="text" id="signStartTime" name="signStartTime" class="input-text Wdate"
                       value="<#if transCrgg.signStartTime??>${transCrgg.signStartTime?string("yyyy-MM-dd HH:mm:ss")}</#if>"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',maxDate:'#F{$dp.$D(\'signEndTime\')}',alwaysUseStartDate:true})" readonly="readonly">

            </td>
            <td><span style="color: red;">*</span><label class="control-label">报名截止时间：</label></td>
            <td>
                <input type="text" id="signEndTime" name="signEndTime" class="input-text Wdate"
                       value="<#if transCrgg.signEndTime??>${transCrgg.signEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if>"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 16:00:00',minDate:'#F{$dp.$D(\'signStartTime\')}',alwaysUseStartDate:true})" readonly="readonly">
            </td>
        </tr>

        <tr>
            <td><span style="color: red;">*</span><label class="control-label">行政区部门：</label></td>
            <td>
            <@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=0 SelectValue=transCrgg.regionCode! onChange="regionChanged"/>
            </td>

            <td><span style="color: red;">*</span><label class="control-label">竞价规则：</label></td>
            <td>
                 <span class="select-box">
                <select name="winStandard" class="select" >
                    <option value="01" <#if transCrgg.winStandard?? && transCrgg.winStandard=='01' >selected="selected"</#if>>价高者得</option>
                    <option value="02" <#if transCrgg.winStandard?? && transCrgg.winStandard=='02' >selected="selected"</#if>>综合评价高者得</option>
                    <option value="03" <#if transCrgg.winStandard?? && transCrgg.winStandard=='03' >selected="selected"</#if>>详见出让须知</option>
                   </select>
                </span>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">出让单位：</label></td>
            <td>
                <input type="text" class="input-text" name="remiseUnit" value="${transCrgg.remiseUnit!}"
                       style="width:100%" maxlength="64">
            </td>
            <td><label class="control-label">出让方式：</label></td>
            <td>
                <span class="select-box">
                <select name="remiseType" class="select" disabled="disabled" >
                    <option value="02" <#if transCrgg.remiseType?? && transCrgg.remiseType=='02' >selected="selected"</#if>>挂牌</option>
                 <#--   <option value="01" <#if transCrgg.remiseType?? && transCrgg.remiseType=='01' >selected="selected"</#if>>招标</option>
                    <option value="02" <#if transCrgg.remiseType?? && transCrgg.remiseType=='02' >selected="selected"</#if>>挂牌</option>
                    <option value="03" <#if transCrgg.remiseType?? && transCrgg.remiseType=='03' >selected="selected"</#if>>拍卖</option>-->
                </select>
                </span>

            </td>

        </tr>

       <#-- <tr>
            <td><label class="control-label">几幅地块</label></td>
            <td>
                <input type="text" class="input-text" name="parcelMsg" value="${transCrgg.parcelMsg!}"
                       style="width:100%">
            </td>
            <td><label class="control-label">批准机关：</label></td>
            <td>
                <input type="text" class="input-text" name="pzJg" value="${transCrgg.pzJg!}"
                       style="width:100%">
            </td>
        </tr>-->




       <#-- <tr>
            <td><label class="control-label">投标开始时间：</label></td>
            <td>
                <input type="text" name="bidStartTime" class="input-text Wdate"
                       value="<#if transCrgg.bidStartTime??>${transCrgg.bidStartTime?string("yyyy-MM-dd HH:mm:ss")}</#if>"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})">
            </td>
            <td><label class="control-label">投标截止时间：</label></td>
            <td>
                <input type="text" name="bidEndTime" class="input-text Wdate"
                       value="<#if transCrgg.bidEndTime??>${transCrgg.bidEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if>"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})">

            </td>
        </tr>-->


       <#-- <tr>
            <td></td>
            <td></td>
            <td><label class="control-label">缴纳保证金截止时间：</label></td>
            <td>
                <input type="text" name="paymentEndTime" class="input-text Wdate"
                       value="<#if transCrgg.paymentEndTime??>${transCrgg.paymentEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if>"
                       onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})">
            </td>


        </tr>-->
      <#--  <tr>
            <td><label class="control-label">发布人：</label></td>
            <td>
            ${WebUtil.loginUserViewName!}
                <input type="hidden" class="input-text" name="passMan" value="${WebUtil.loginUserViewName!}"
                       style="width:100%">
            </td>
            <td><label class="control-label">发布时间：</label></td>
            <td>
                <input type="text" disabled="disabled" name="postDate" class="input-text"
                       value="${transCrgg.postDate?string("yyyy-MM-dd HH:mm:ss")}">
            </td>
        </tr>-->



        <tr>

            <td><label class="control-label">联系人：</label></td>
            <td>
                <input type="text" class="input-text" name="linkMan" value="${transCrgg.linkMan!}"
                       style="width:100%" maxlength="32">
            </td>
            <td><label class="control-label">联系电话：</label></td>
            <td>
                <input type="text" class="input-text" id="linkPhone" name="linkPhone" value="${transCrgg.linkPhone!}"
                       style="width:100%" maxlength="32">
            </td>

        </tr>
        <tr>


            <td><label class="control-label">联系地址：</label></td>
            <td >
                <input type="text" class="input-text" name="linkAddress" value="${transCrgg.linkAddress!}"
                       style="width:100%" maxlength="64">
            </td>
            <td><label class="control-label">批准机关：</label></td>
            <td>
                <input type="text" class="input-text" name="pzJg" value="${transCrgg.pzJg!}"
                       style="width:100%" maxlength="32">
            </td>

        </tr>

        <tr>
            <td><label class="control-label">个人所需材料：</label></td>
            <td colspan="3">
                <!--TODO  材料列表-->
                <div id="personal" style="height: 217px"><#include "common/material_list_person.ftl"></div>
            </td>

        </tr>

        <tr>
            <td><label class="control-label">单位所需材料：</label></td>
            <td colspan="3">
                <!--TODO  材料列表-->
                <div id="groupLoad" style="height: 217px><#include "common/material_list_group.ftl"></div>
            </td>

        </tr>

        <tr>
            <td><label class="control-label">竞买资格：</label></td>
            <td colspan="3">
                <textarea style="width: 100%" rows="5" name="bidTerm" maxlength="500">${transCrgg.bidTerm!}</textarea>
            </td>

        </tr>

        <tr>
            <td><label class="control-label">其它需要公告的事项：</label></td>
            <td colspan="3">
            <textarea style="width: 100%" rows="5" name="otherTerm" maxlength="255">${transCrgg.otherTerm!}</textarea>
            </td>

        </tr>
        <tr>

            <td><label class="control-label">其它需要说明的地宗情况：</label></td>
            <td colspan="3">
                <textarea style="width: 100%" rows="5" name="otherMsg" maxlength="255">${transCrgg.otherMsg!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">备注：</label></td>
            <td colspan="3">
                <textarea style="width: 100%" rows="5" name="memo" maxlength="255">${transCrgg.memo!}</textarea>
            </td>
        </tr>

    <#if transCrgg.ggId??>
        <tr>
            <td rowspan="2"><label class="control-label">附件：</label></td>
            <td colspan="3">
                <div id="attachments">
                </div>
                <div id="attachmentsOperation">
                </div>
                <div class="fileUploader">
                    <span class="btn-upload">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input id="fileName" name="file" type="file" multiple class="input-file"
                               accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf">
                    </span>

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <span style="color: red;">注：只允许传入以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过10M</span>
                <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                    <span class="sr-only"/>
                </div>
            </td>
        </tr>
    </#if>
        <tr>
            <td><label class="control-label">公告详情：</label></td>
            <td colspan="3">
                <textarea type="text" id="ggContent" name="ggContent" class="xheditor" style="width: 100%"
                          rows="40">${transCrgg.ggContent!}</textarea>
            </td>
        </tr>

    </table>

    <input type="hidden" id="ggId" name="ggId" value="${transCrgg.ggId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/crgg/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
</body>
</html>