<@Head_SiteMash title="" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">

    <style type="text/css">
        .form-row{
            padding-left: 10px;
            padding-right: 10px;
            padding-top: 5px;
            padding-bottom: 5px;
        }

        .btn-upload{ position: relative;display: inline-block;overflow: hidden; vertical-align: middle; cursor: pointer;}
        .uploadifive-button{
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/static/image/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        #attachments{width: 500px;float: left;overflow:hidden;text-overflow-ellipsis: '..';}
        #attachmentsOperation{float: right}

        .error_input{
            background-color: #FFFFCC;
        }
        .upload-progress-bar{
            display: none;
            width: 300px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $.ajaxSetup({cache:false});
            var type=$("input:radio[name='applyType']:checked").val();
            if (type == 1) {
                showUnion();
            }
            initializeAttachmentUploader('${transResourceApply.applyId!}');
            getAttachments('${transResourceApply.applyId!}');
        });
        function hiddenUnion(){
            $("#divUnion").html("");
            $('[sign="nclxgs"]').hide();
        }
        function showUnion(){
            $("#divUnion").load("${base}/union-list.f?applyId=${transResourceApply.applyId}");
            $('[sign="nclxgs"]').show();
        }
        function showInfor(type){
            if(type == 'company'){
                $("#infotype").text("营业执照号");
            }else{
                $("#infotype").text("身份证号");
            }
        }
        function addUnion(){
            $("#myFormUnion").load("${base}/union-edit.f",null,function(){
                $('#myModalUnion').modal({
                    backdrop: false
                });
            });
        }

        function saveUnion(){
            if (!checkInputNull('userName','被联合人必须填写!'))
                return false;
            if (!checkInputNull('userCode','证件号必须填写!'))
                return false;
            if (!checkInputNull('linkMan','联系人必须填写!'))
                return false;
            if (!checkInputNull('linkManTel','联系人联系方式必须填写!'))
                return false;
            if (!checkInputNull('amountScale','出资比例必须填写!'))
                return false;
            var amountScale = $("input[name='amountScale']").val();
            if((!amountScale.match(/^[\+]?\d*?\.?\d*?$/))){
                alert("出资比例不是数字，请重新输入！");
                return false;
            }else if(1.0*amountScale>=100){
                alert("出资比例不正确，请重新输入！");
                return false;
            }
            var options = {
                url: '${base}/union-save.f?applyId=${transResourceApply.applyId}',
                success: function(result) {
                    $('#myModalUnion').modal('hide');
                    showUnion();
                    if(result!="ok"&&result.hasOwnProperty('msg'))
                       alert(result.msg);
                }
            };
            $('#myFormUnion').ajaxSubmit(options);
        }

        function delUnion(id){
            if(confirm("是否确认删除该联合竞买人？")){
            $.ajax({
                type: "POST",
                url: "${base}/union-del.f",
                data: {unionId:id},
                cache:false,
                success: function(data){
                    showUnion();
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

        function initializeAttachmentUploader(applyId){
            if(applyId!=null&&applyId!=''){
                var url = '${base}/file/upload.f?fileKey='+applyId;
                $('#attachmentFile').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if(data!=null&&data!=''){
                            if(data.result.hasOwnProperty("ret"))
                                alert(data.result.msg);
                            else
                                insertAttachment(data.result.fileId,data.result.fileName);
                        }
                        var activeCount = $('#attachmentFile').fileupload('active');
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
        function insertAttachment(fileId,fileName){
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment'+fileId+'"><a class="btn btn-link" href="javascript:removeAttachment(\''+fileId+'\')">删除</a><br/></div>');
        }
        function removeAttachment(fileId){
            if(confirm("确定要删除数据吗？")){
                var url = '${base}/file/remove.f';
                $.post(url,{fileIds:fileId},function(data){
                    if(data!=null&&data=='true'){
                        $('#attachments div[class=attachment'+fileId+']').each(function(){
                            $(this).remove();
                        });
                        $('#attachmentsOperation div[class=attachment'+fileId+']').each(function(){
                            $(this).remove();
                        });
                    }
                });
            }

        }

        function getAttachments(applyId){
            var url ='${base}/file/attachments.f?fileKey='+applyId;
            $.get(url,function(data){
                $('#attachments').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachment(data[i].fileId,data[i].fileName);
                    }

                }
            })
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

        function beforeTrailSubmit(){
            if(confirm('审核信息一旦提交将无法变更，请确认是否需要提交审核?')){
                return true;
            }
            return false;
        }
    </script>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><a href="${base}/view?id=${transResource.resourceId}">${transResource.resourceCode}</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">选择竞买方式和银行</span>
    </nav>
    <#if _msg??>
    <div class="Huialert Huialert-danger" style="margin: 10px 0px;"><i class="icon-remove"></i>${_msg}</div>
    </#if>
    <form action="${base}/console/apply-bank-over?id=${transResource.resourceId}" method="post">

    <div class="row">
        <#if transResourceApply.trialType?? && transResourceApply.trialType == 'FAILED_TRIAL'>
            <div style="font-weight: bolder;font-size: 20px;">
                抱歉您提交的信息未能通过审核，未通过原因：<span style="color: red;">${transResourceApply.trialReason!}</span>。请您编辑信息后重新提交，谢谢！
            </div>
        </#if>
        <#include "apply-user-info.ftl"/>
        <table class="table table-border table-bordered">
            <tr>
                <td rowspan="3" width="200" style="background-color: #F5F5F5;">
                    选择竞买方式
                </td>
                <td style="line-height:32px">
                    <div class="radio-box">
                        <input type="radio" name="applyType" <#if transResourceApply.applyType==0> checked="checked"</#if> value="0"
                               onclick="hiddenUnion()">
                        <label>独立竞买</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="radio-box">
                        <input type="radio" name="applyType" <#if transResourceApply.applyType==1> checked="checked"</#if> value="1"
                                onclick="showUnion()">
                        <label>联合竞买</label>
                    </div>

                    <div id="divUnion">

                    </div>
                </td>
            </tr>
            <tr>
                <td style="line-height:32px;">
                    <div sign="nclxgs" style="display: none;">
                        <div class="check-box l">
                            <label for="checkbox-1" style="font-weight: bolder;">拟成立新公司</label>
                        </div>
                        <div class="l">
                            <label for="checkbox-1"> 拟成立的新公司名称</label>
                            <input type="text" class="input-text  size-M" style="width:400px" name="createNewComName"
                                   value="${transResourceApply.createNewComName!}">
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <table class="table table-border table-bordered">
            <tr>
                <td  width="200" style="background-color: #F5F5F5;">
                    选择银行
                </td>

                <td>
                <#assign bankCode=""/>
                <#assign bankOther=false />
                <#if transResourceApply.bankCode??><#assign bankCode=transResourceApply.bankCode/></#if>
                <table class="table ">
                    <tr><td>币种：人民币</td></tr>
                <tr><td>
		<#if transResource.banks??>
		    <#assign banks=transResource.banks?split(",")/>
		<#else>
		    <#assign banks=""?split(",")/>
		</#if>
                <#list bankList as bank>
		    <#if banks?seq_contains(bank.bankCode)>

                    <#if bank.moneyUnit="CNY">
                        <div style="float: left;margin-right:10px" ><input type="radio" name="bankCode" value="${bank.bankCode}"
                            <#if bank.bankCode==bankCode>checked="checked" </#if> >
                            <img src="${base}/static/bankimages/${bank.bankCode}.gif"></div>
                    <#else>
                        <#assign bankOther=true />

                    </#if>

		    </#if>
                </#list>
                </td></tr>
                </table>

                </td>
            </tr>

        </table>
            <table class="table table-border table-bordered">
                <tr>
                    <td  width="200px" style="background-color: #F5F5F5;">
                    附件材料
                    </td>
                    <td>
                        <span>
                            竞买人在CA注册后须向网上交易系统上传下列文件：<br/>
                            （1）申请书；（2）营业执照；（3）法定代表人身份证；（4）若有授权委托，提供授权委托书及受托人有效身份证件；（5）其他需提交的证明材料<br/>
                            <span style="color: red">注：只允许传入以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过5M</span>
                        </span>
                        <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                            <span class="sr-only"/>
                        </div>
                    </td>
                    <td width="600px">
                        <span class="btn-upload" style="height:60px;margin-left: 10px">
                            <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                            <input id="attachmentFile" name="file" type="file" multiple="true" class="input-file" accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf">
                        </span>
                        <div>
                            <div id="attachments">
                            </div>
                            <div id="attachmentsOperation">
                            </div>
                        </div>


                    </td>
                </tr>

            </table>
        <div style="text-align: center;margin-top: 20px">
            <#if transResource.qualificationType??&&transResource.qualificationType=='PRE_TRIAL'>
            <input type="submit" onclick="return beforeTrailSubmit()" class="btn btn-primary" value="提交资格审核"></input>
            <#else>
            <input type="submit" class="btn btn-primary" value="缴纳保证金"></input>
            </#if>
            <a class="btn btn-default" href="${base}/view?id=${transResource.resourceId}" >返回地块</a>
        </div>
    </div>
    </form>
</div>
<div id="myModalUnion" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel">联合竞买人</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal_content">
        <form id="myFormUnion">
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal" onclick="javascript:saveUnion();">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.form.min.js"></script>

<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>