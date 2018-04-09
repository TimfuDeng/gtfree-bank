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
            var type=$("input:radio[name='applyType']:checked").val();
            $.ajaxSetup({cache:false});
            if(type==1)   showUnion();
            chooseApplyer();
        });

        function doInits(){
            clearAttachments();
            getAttachments('${transResourceApply.applyId!}');
            $("input[name='materialType']").each(function(){
                var fileType = $(this).val();
                initializeAttachmentUploader('${transResourceApply.applyId!}',fileType);
            });
        }

        function clearAttachments(){
            $('input[id^=attachments_]').each(function(){
                $(this).empty();
            });
            $("input[id^=attachmentsOperation_]").each(function(){
                $(this).empty();
            });
        }


        function chooseApplyer(){
            var flag = $('input[name="applyerType"]:checked').val();
            if(flag=="DWSQ"){
                $("#personalText").hide();
                $("#groupText").show();
                $("#DW").html("<table style='border-top: 1px solid #ddd;border-right:1px solid #ddd;'><#list materialGroup as material><tr><td>${material.materialName}<input type='hidden' name='materialType' value='${material.materialCode}'></td><td><span id='attachments_${material.materialCode}' style='float: left;text-align: center'></span></td><td width='60px'><span class='btn-upload' name='${material.materialCode}' style='float-left;margin-left: 10px'><a href='javascript:void();' class='btn size-S btn-primary'>导入</a><input id='${material.materialCode}' name='file' type='file' multiple='true'class='input-file' accept='image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf'></span><span id='attachmentsOperation_${material.materialCode}' style='float-left;margin-left:10px'></span></td></tr></#list></table>");
                $("#GR").empty();
                doInits();
            }
            if(flag=="GRSQ"){
                $("#personalText").show();
                $("#groupText").hide();
                $('#GR').html("<table style='border-top: 1px solid #ddd;border-right:1px solid #ddd;'><#list materialPersonal as material><tr><td>${material.materialName}<input type='hidden' name='materialType' value='${material.materialCode}'></td><td><span id='attachments_${material.materialCode}' style='float: left;text-align: center'></span></td><td width='60px'><span class='btn-upload' name='${material.materialCode}' style='float-left;margin-left: 10px'><a href='javascript:void();' class='btn size-S btn-primary'>导入</a><input id='${material.materialCode}' name='file' type='file' multiple='true'class='input-file' accept='image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf'></span><span id='attachmentsOperation_${material.materialCode}' style='float-left;margin-left:10px'></span></td></tr></#list></table>");
                $("#DW").empty();
                doInits();
            }
        }

        function checkAttchments(){
            var flag = $('input[name="applyerType"]:checked').val();
            var applyType;
            if(flag=="DWSQ"){
                applyType = "GROUP";
            }
            if(flag=="GRSQ"){
                applyType = "PERSONAL";
            }
            $.ajax({
                url:"${base}/console/apply/fileCheck?applyId=${transResourceApply.applyId!}&applyType="+applyType+"&userId=${transResourceApply.userId}",
                type:"post",
                success:function(data){
                    if(data){
                        return true;
                    }else{
                        alert("您所上传的材料不完整，请上传完整的材料");
                        return false;
                    }
                }
            });
        }

        function hiddenUnion(){
            $("#divUnion").html("");

        }

        function showUnion(){
            $("#divUnion").load("${base}/union-list.f?applyId=${transResourceApply.applyId}");
        }
        function addUnion(){

            if($("#divUnion").find("tbody").find("tr").length==2){
                alert("联合竞买时，联合竞买人最多为2人！");
                return false;
            }
            $("#myFormUnion").load("${base}/union-edit.f",null,function(){
                $('#myModalUnion').modal({
                    backdrop: false
                });
            });
        }

        function edtUnion(id){
            $("#myFormUnion").load("${base}/union-edit.f",{unionId:id},function(){
                $('#myModalUnion').modal({
                    backdrop: false
                });
            });
        }

        function saveUnion(){
            var contactor = $("input[name='linkMan']").val();
            var userCode = $("input[name='userCode']").val();
            var tel = $("input[name='linkManTel']").val();
            var telReg = /^1[3|4|5|7|8]\d{9}$/;
            if (!checkInputNull('userName','联合竞买人必须填写!'))
                return false;
            if ($.trim(userCode)==""){
                alert("证件号必须填写！");
                return false;
            }
            if ($.trim(contactor)==""){
                alert("联系人必须填写！");
                return false;
            }
            if (!checkInputNull('linkManTel','联系人联系方式必须填写!'))
                return false;
            if(!telReg.test(tel)){
                alert("请输入正确的联系方式");
                return false;
            }
            if (!checkInputNull('amountScale','出资比例必须填写!'))
                return false;
            var amountScale = $("input[name='amountScale']").val();//弹出框出资比例

            if((!amountScale.match(/^[\+]?\d*?\.?\d*?$/))){
                alert("出资比例格式不正确，请输入数字！");
                return false;
            }else if(1.0*amountScale>=100){
                alert("出资比例不能大于100%，请重新输入！");
                return false;
            }else if(1.0*amountScale<=0){
                alert("出资比例不能小于或等于0，请重新输入！");
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

        function initializeAttachmentUploader(applyId,fileType){
            if(applyId!=null&&applyId!=''){
                var url = '${base}/file/upload.f?fileKey='+applyId+'&fileType='+fileType;;
                $('#'+fileType).fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if(data!=null&&data!=''){
                            if(data.result.hasOwnProperty("ret")) {
                                alert(data.result.msg);
                            } else {
                                insertAttachment(data.result.fileId, data.result.fileName, fileType);
                                //文件上传成功之后，隐藏导入按钮
                                $("span[name="+fileType+"]").css("display","none");
                            }
                        }
                        var activeCount = $('#'+fileType).fileupload('active');
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

        function insertAttachment(fileId,fileName,fileType){
            $("#attachments_"+fileType).prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
            $("#attachmentsOperation_"+fileType).prepend('<span class="attachment'+fileId+'"><a class="btn size-S btn-primary" href="javascript:removeAttachment(\''+fileId+'\',\''+fileType+'\')">删除</a><br/></span>');
        }
        function removeAttachment(fileId,fileType){
            if(confirm("确定要删除数据吗？")){
                var url = '${base}/file/remove.f';
                $.post(url,{fileIds:fileId},function(data){
                    if(data!=null&&data=='true'){
                        $("#attachments_"+fileType).empty();
                        $('#attachmentsOperation_'+fileType).empty();
                        $("span[name="+fileType+"]").show();
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
                        insertAttachment(data[i].fileId,data[i].fileName,data[i].fileType);
                        $("span[name="+data[i].fileType+"]").css("display","none");
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



        function qualifiedNote(){
            checkInputFileter();
            if(confirm('是否确认提交审核！')){
                var resourceId=$("#resourceId").val();

                var contactPhone = $("input[name='contactTelephone']").val();
                var telReg = /^1[3|4|5|7|8]\d{9}$/;
                if(!telReg.test(contactPhone)){
                    alert("请输入正确的联系人手机号码");
                    return false;
                }
                if(!checkInputNull("contactPerson","请填写联系人")){
                    return false;
                }
                if(!checkInputNull("contactAddress","请填写联系人地址")){
                    return false;
                }
                if(!checkInputNull("contactTelephone","请填写联系人手机号码")){
                    return false;
                }
                var amountUnion=0;//所有联合人出资比例
                $("td.lable-union").each(
                        function(){
                            amountUnion=amountUnion+$(this).html()*1;

                        }
                );
                if(amountUnion>=100){
                    alert("所有被联合人出资比例之和不能大于等于100%，请重新输入！");
                    return false;
                }
                var flag = $('input[name="applyerType"]:checked').val();
                var applyType;
                if(flag=="DWSQ"){
                    applyType = "GROUP";
                }
                if(flag=="GRSQ"){
                    applyType = "PERSONAL";
                }
                $.ajax({
                    type: "POST",
                    url: "/client/console/apply/resourceCheck",
                    async:false,
                    data: {id:resourceId},
                    success: function(data){
                        if (data==false){
                            alert("当前地块已经被中止！");
                            return ;
                        }else{
                            $("#_form").submit();

                        }
                    }

                });

            }
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
        <span class="c_gray en">&gt;</span><span class="c_gray">选择竞买方式和币种</span>
    </nav>
<#if _msg??>
    <div class="Huialert Huialert-danger" style="margin: 10px 0px;"><i class="icon-remove"></i>${_msg}</div>
</#if>
    <form id="_form" action="${base}/console/apply-bank-over?id=${transResource.resourceId}" method="post">

        <div class="row">
        <#include "apply-user-info.ftl"/>
            <table class="table table-border table-bordered">
                <tr>
                    <td rowspan="3" width="200" style="background-color: #F5F5F5;">
                        选择竞买方式
                    </td>
                    <td style="line-height:32px">
                        <div class="radio-box">
                            <input type="radio" name="applyType" onclick="hiddenUnion();"<#if transResourceApply.applyType==0> checked="checked"</#if> value="0"/>
                            <label>独立竞买</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="radio-box">
                            <input type="radio" name="applyType" onclick="showUnion();" <#if transResourceApply.applyType==1> checked="checked"</#if> value="1"/>
                            <label>联合竞买</label>
                        </div>

                        <div id="divUnion">

                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="line-height:32px;">
                        <div class="check-box l">
                            <input type="checkbox" name="isComName" value="1" id="checkbox-1" <#if transResourceApply.isComName?? && transResourceApply.isComName=="1"> checked="checked"</#if>>
                            <label for="checkbox-1">拟成立新公司</label>
                        </div>
                        <div class="l">
                            <label for="checkbox-1"> 拟成立的新公司名称</label>
                            <input type="text" class="input-text  size-M" style="width:400px" name="createNewComName"
                                   value="${transResourceApply.createNewComName!}">
                        </div>
                    </td>
                </tr>
            </table>
            <table class="table table-border table-bordered">
                <tr>
                    <td  width="200" style="background-color: #F5F5F5;">
                        选择币种
                    </td>

                    <td>
                    <#assign bankCode=""/>
                    <#assign bankOther=false />
                    <#if transResourceApply.bankCode??><#assign bankCode=transResourceApply.bankCode/></#if>
                    <#-- <table class="table ">
                         <tr><td>-->
                    <#if transResource.fixedOffer??&&transResource.fixedOffer gt 0>
                        <div style="float: left;margin-right:10px" >

                            <input type="radio" name="moneyUnit" value="CNY"
                                   <#if transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="CNY">checked="checked" </#if> >
                            人民币 </div>
                    </#if>

                    <#if transResource.fixedOfferUsd??&&transResource.fixedOfferUsd gt 0>
                        <div style="float: left;margin-right:10px" >
                            <input type="radio" name="moneyUnit" value="USD"
                                   <#if transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="USD">checked="checked" </#if> >
                            美元 </div>
                    </#if>

                    <#if transResource.fixedOfferHkd??&&transResource.fixedOfferHkd gt 0>
                        <div style="float: left;margin-right:10px" >
                            <input type="radio" name="moneyUnit" value="HKD"
                                   <#if transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="HKD">checked="checked" </#if> >
                            港币 </div>
                    </#if>


                    <#-- </td></tr>-->
                    <#--<tr><td>
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
                    </td></tr>-->
                    <#-- </table>-->

                    </td>
                </tr>
                <tr>
                    <td  width="200" style="background-color: #F5F5F5;">
                        选择申请人类型
                    </td>
                    <td>
                    <#if transResource.regionCode="320503001">
                        <input type="radio" id="DWSQ"  checked="checked" name="applyerType" value="DWSQ" onclick="chooseApplyer()"/>单位申请
                        <input type="radio" id="GRSQ" name="applyerType" value="GRSQ"  onclick="chooseApplyer()"/>个人申请

                    <#else>
                            <input type="radio" id="GRSQ" name="applyerType" value="GRSQ" checked="checked" onclick="chooseApplyer()"/>个人申请
                            <input type="radio" id="DWSQ" name="applyerType" value="DWSQ" onclick="chooseApplyer()"/>单位申请
                    </#if>

                    </td>
                </tr>
            </table>
            <input type="hidden" id="resourceId" value="${transResource.resourceId}">
            <#include "apply-bank-material.ftl">
            <div style="text-align: center;margin-top: 20px">
                <input type="button" class="btn btn-primary"  <#if !WebUtil.isCaEnabled()>onclick="qualifiedNote()"<#else> onclick="return btnSubmit(this)"</#if>  value="提交审核"></input>
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

<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.form.min.js"></script>
<script type="text/javascript" src="${base}/static/js/checkFilter.js"></script>

<#if WebUtil.isCaEnabled()>
    <@Ca autoSign=0  />
<script>
    function btnSubmit(){
        gtmapCA.initializeCertificate(document.all.caOcx);
        var result = gtmapCA.signContent(new Date().toLocaleTimeString(),1);
        if(result==true){
            qualifiedNote();
        }else{
            alert(gtmapCA.getErrorString(result));
        }
    }
</script>
</#if>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>