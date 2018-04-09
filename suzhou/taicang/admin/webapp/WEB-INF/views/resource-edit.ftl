<html>
<head>
    <title>-出让地块</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
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
        .btn-upload{ position: relative;display: inline-block;overflow: hidden; vertical-align: middle; cursor: pointer;}
        .upload-url{ width: 200px;cursor: pointer;}
        .input-file{ position:absolute; right:0; top:0; cursor: pointer; font-size:17px;opacity:0;filter: alpha(opacity=0)}
        .uploadifive-button{
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }
        .btn-upload input{
            height:55px;
            cursor: pointer;
        }
        .image{
            padding-right:5px;
            float: left;
        }
        .image:hover{
            cursor: pointer;
        }
        .image a{
            position: relative;
            top:-30px;
            filter: alpha(opacity=0);
            opacity: 0;

        }
        .image:hover a{
            filter: alpha(opacity=100);
            opacity: 1;
        }
        .unit_span{
            font-size:10px;
            font-style:italic;
            color: #f4523b;
        }
        .minOfferInfo{
            font-size:10px;
            font-style:italic;
            color: #f4523b;
        }
        .unit_house{
            font-size:10px;
            font-style:italic;
            color: #f4523b;
        }
        .error_input{
            background-color: #FFFFCC;
        }
        #attachmentsTitle{width:100px;float:left}
        #attachments{width: 500px;float: left;overflow:hidden;text-overflow-ellipsis: '..';}
        #attachmentsOperation{float: right}

        .upload-progress-bar{
            display: none;
            width: 400px;
            margin-top: 5px;
        }

    </style>

    <script type="text/javascript">
        var fileTypeMap;
        var uploadFileQueue= [];
        var uploadIndex = -1;
        $(document).ready(function(){
            <#if _result?? && _result>
                _alertResult('ajaxResultDiv',${_result?string('true','false')},'${_msg!}');
            </#if>



        <#if transResource.resourceId??>
            $.get("${base}/file/thumbnails.f?fileKey=${transResource.resourceId}&resolution=280_185", function(result){
                for(var i=0;i<result.length;i++){
                    insertImage(result[i].fileId);
                }
            });
        </#if>

            getAttachments('${transResource.resourceId!}');

            initializeImageUploader('${transResource.resourceId!}');
            initializeAttachmentUploader('${transResource.resourceId!}','CRGG');

            $('#attachmentType').change(function(){
                var type = $(this).children('option:selected').val();
                $('#attachmentFile').fileupload('destroy');
                initializeAttachmentUploader('${transResource.resourceId!}',type);
            });
            fileTypeMap = {};
            $('#attachmentType option').each(function(){
                fileTypeMap[$(this).val()]=$(this).text();
            });
        });

        function initializeAttachmentUploader(resourceId,type){
            if(resourceId!=null&&resourceId!=''){
                var url = '${base}/file/upload.f?fileKey='+resourceId+'&fileType='+type;
                $('#attachmentFile').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if(data!=null&&data!=''){
                            if(data.result.hasOwnProperty("ret"))
                                alert(data.result.msg);
                            else{
                                insertAttachment(data.result.fileId,data.result.fileName,type);
                            }

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

        function initializeImageUploader(resourceId){
            if(resourceId!=null&&resourceId!=''){
                var url = '${base}/file/upload.f?fileType=THUMBNAIL&fileKey='+resourceId;
                $('#fileName').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if(data!=null&&data!=''){
                            if(data.result.hasOwnProperty("ret"))
                                alert(data.result.msg);
                            else{
                                insertImage(data.result.fileId);
                            }

                        }
                        var activeCount = $('#fileName').fileupload('active');
                        if(activeCount==1){
                            resetProgressBar('imageProgressBar');
                            setProgressBarVisible('imageProgressBar',false);
                        }

                    },
                    progressall: function (e, data){
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        $('#imageProgressBar span').css(
                                'width',
                                progress + '%'
                        );
                    },
                    start:function(e){
                        setProgressBarVisible('imageProgressBar',true);
                    },
                    fail:function (e, data) {
                        resetProgressBar('imageProgressBar');
                        setProgressBarVisible('imageProgressBar',false);
                        alert('图片上传失败');
                    }
                });
            }

        }

        function getAttachments(resourceId){
            var url ='${base}/file/attachments.f?fileKey='+resourceId;
            $.get(url,function(data){
                $('#attachments').empty();
                if(data!=null&&data.length>0){
                    for(var i=0;i<data.length;i++){
                        insertAttachment(data[i].fileId,data[i].fileName,data[i].fileType);
                    }

                }
            })
        }

        function insertImage(fileId){
            $("<div style='width:110px;height=60px' class='image'><img width='100' height='60' onerror='this.src=\"${base}/static/image/blank.jpg\"' src='${base}/file/view.f?fileId=" +fileId+ "'></image><a onclick='removeImage(this,\""+fileId+"\");'><i class='icon-remove'></i></a></div>").insertBefore("#imagePic");
        }

        function insertAttachment(fileId,fileName,fileType){
            $('#attachmentsTitle').prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/></div>");
            $("#attachments").prepend("<div class='attachment"+fileId+"'><a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment'+fileId+'"><a class="btn btn-link" href="javascript:removeAttachment(\''+fileId+'\')">删除</a><br/></div>');
        }

        function regionChanged(){
            var regionCode=$("select#regionCode").val();
            $("#bankList").load("${base}/console/resource/edit/banks.f?regionCode="+regionCode+"&resourceId=${transResource.resourceId!}");
        }
        function unitChange(){
            $(".unit_span").html($("select[name='offerUnit']").find("option:selected").text());
        }

        function houseChange(){
            $(".unit_house").html($("select[name='publicHouse']").find("option:selected").text());
        }

        function minOfferChange(){
            if ($("input[name='minOffer']").is(':checked')) {
                $("input[name='minOffer']").val(1);
                $(".minOfferInfo").html("请务必在挂牌截至前30分钟内录入底价，否则底价默认为0！");
            }else{
                $("input[name='minOffer']").val(0);
                $(".minOfferInfo").html("无底价！");

            }
        }

        function beginDateChange(){
            var bDate=$("input[name='gpBeginTime']").val();
            bDate=new XDate(bDate);
            var endDate=bDate.addDays(9);
            $("input[name='gpEndTime']").val(endDate.toString("yyyy-MM-dd")+" 16:00:00");
            $("input[name='bmEndTime']").val(endDate.addDays(-2).toString("yyyy-MM-dd")+" 16:00:00");
            $("input[name='bzjEndTime']").val(endDate.toString("yyyy-MM-dd")+" 16:00:00");

            $("input[name='bmBeginTime']").val(bDate.addDays(-33).toString("yyyy-MM-dd")+" 09:00:00");
            $("input[name='bzjBeginTime']").val(bDate.toString("yyyy-MM-dd")+" 09:00:00");
        }

        function checkForm(){
            if (!checkInputNull('resourceCode','地块编号必须填写!'))
                return false;
            if (!checkInputNull('resourceLocation','地块座落必须填写!'))
                return false;
            if (!checkInputNull('fixedOffer','保证金必须填写!'))
                return false;
            if (!checkInputNull('crArea','出让面积必须填写!'))
                return false;
            if (!checkInputNull('beginOffer','起始价必须填写!'))
                return false;
            if (!checkInputNull('addOffer','增价幅度必须填写!'))
                return false;
            if($("input[name='maxOffer']").val()!="" && $("input[name='maxOffer']").val()>0 ){
                if (!checkInputNull('beginHouse','起始公租房必须填写!'))
                    return false;
                if (!checkInputNull('addHouse','公租房增幅必须填写!'))
                    return false;
            }

            try {
                var fixedOfferValue = $('input[name="fixedOffer"]').val();
            	if((!fixedOfferValue.match(/^[\+]?\d*?\.?\d*?$/))){
                	alert("保证金不是数字，请重新输入！");
                	return false;
            	}
                var qishijia = $('input[name="beginOffer"]').val();
                if((!qishijia.match(/^[\+]?\d*?\.?\d*?$/))){
                	alert("起始价不是数字，请重新输入！");
                	return false;
            	}
                /*var baozhengjin = $('input[name="fixedOffer"]').val() * 1.0;
                var minBaozhengjin = qishijia * 0.2;
                var maxBaozhengjin = qishijia * 0.25;
                if (baozhengjin < minBaozhengjin || baozhengjin > maxBaozhengjin) {
                    alert('保证金价格必须为起始价的20%-25%');
                    return false;
                }*/
            } catch (e) {
            }

            var banks=false;
            if ($("input[name='banks']").is(':checked')){
                banks=true;
            }
            if (!banks){
                alert("请务必选择银行否则无法报名!");
                return false;
            }
            return true;
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

        function getFileTypeName(fileType){
            return fileTypeMap[fileType];
        }

        function removeAttachment(fileId){
            if(confirm("确定要删除数据吗？")){
                var url = '${base}/file/remove.f';
                $.post(url,{fileIds:fileId},function(data){
                    if(data!=null&&data=='true'){
                        $('#attachmentsTitle div[class=attachment'+fileId+']').each(function(){
                            $(this).remove();
                        });
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
        function removeImage(event,fileId){
            var imgDiv = $(event).parent('.image');
            if(confirm("确定要删除数据吗？")){
                var url = '${base}/file/remove.f';
                $.post(url,{fileIds:fileId},function(data){
                    if(data!=null&&data=='true'){
                        $(imgDiv).remove();
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
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource/list">出让地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> ${transResource.resourceCode!"新增地块"}</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<form class="form-base" method="post" action="${base}/console/resource/edit/save">
    <table class="table table-border table-bordered table-striped">
    <tr>
        <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">交易地块基本信息（以下必填）</td>
    </tr>
    <tr>
        <td width="180"><label class="control-label">地块编号：</label></td>
        <td><input type="text" class="input-text" name="resourceCode" value="${transResource.resourceCode!}"></td>
        <td width="180"><label class="control-label">行政区：</label></td>
        <td width="300"><@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=0 SelectValue=transResource.regionCode onChange="regionChanged"/></td>
    </tr>
    <tr>
        <td><label class="control-label">地块座落：</label></td>
        <td colspan="3"><input type="text" class="input-text" name="resourceLocation" value="${transResource.resourceLocation!}"></td>
    </tr>

    <tr>
        <td><label class="control-label">竞价单位：</label></td>
        <td>
            <span class="select-box">
                <select name="offerUnit" class="select" onChange="unitChange()" disabled="disabled">
                    <option value="0" <#if transResource.offerUnit?? && transResource.offerUnit==0 >selected="selected"</#if>>万元（总价）</option>
                    <option value="1" <#if transResource.offerUnit?? && transResource.offerUnit==1 >selected="selected"</#if>>元/平方米（单价）</option>
                    <option value="2" <#if transResource.offerUnit?? && transResource.offerUnit==2 >selected="selected"</#if>>万元/亩（单价）</option>
                </select>
            </span>
        </td>
        <td><label class="control-label">规划用途：</label></td>
        <td>
        <@LandUseSelect selectName="landUse" selectValue=transResource.landUse!  />
        </td>

    </tr>
    <tr>
        <td><label class="control-label">保证金（万元）：</label></td>
        <td><input type="text" name="fixedOffer" class="input-text"  value="${transResource.fixedOffer!}" ></td>
        <td><label class="control-label">保证金（美元）：</label></td>
        <td><input type="text" name="fixedOfferUsd" class="input-text" value="${transResource.fixedOfferUsd!}" ></td>
    </tr>
    <tr>
        <td><label class="control-label">起始价<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
        <td><input type="text" name="beginOffer" class="input-text"  value="${transResource.beginOffer!}" ></td>
        <td><label class="control-label">增价幅度<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
        <td><input type="text" name="addOffer" class="input-text" value="${transResource.addOffer!}"></td>
    </tr>
        <tr>
            <td><label class="control-label">出让面积（平方米）：</label></td>
            <td><input type="text" name="crArea" class="input-text" value="${transResource.crArea!}" ></td>
            <td><label class="control-label">是否有底价：</label></td>
            <td>
                <input type="checkbox" name="minOffer" value="${transResource.minOffer?string(1,0)}" onchange="minOfferChange()" <#if transResource.minOffer==true>checked</#if> >
                <label class="minOfferInfo">
                <#if transResource.minOffer>
                    请务必在挂牌截至前30分钟内录入底价，否则底价默认为0！
                <#else>
                    无底价！
                </#if>
                </label>
            </td>
        </tr>
    <#--<tr>-->

        <#--<td><label class="control-label">是否有底价：</label></td>-->
        <#--<td colspan="3">-->
            <#--<input type="checkbox" name="minOffer" value="${transResource.minOffer?string(1,0)}" onchange="minOfferChange()" <#if transResource.minOffer==true>checked</#if> >-->
            <#--<label class="minOfferInfo">-->
            <#--<#if transResource.minOffer>-->
                <#--请务必在挂牌截至前30分钟内录入底价，否则流标！-->
            <#--<#else>-->
                <#--无底价！-->
            <#--</#if>-->
            <#--</label>-->
        <#--</td>-->

    <#--</tr>-->

    <tr>
        <td><label class="control-label">挂牌开始时间：</label></td>
        <td><input type="text" name="gpBeginTime" class="input-text Wdate" onchange="beginDateChange()"  value="${transResource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
        <td><label class="control-label">挂牌结束时间：</label></td>
        <td><input type="text" name="gpEndTime" class="input-text Wdate" value="${transResource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
    </tr>
    <tr>
        <td><label class="control-label">报名开始时间：</label></td>
        <td><input type="text" name="bmBeginTime" class="input-text Wdate"  value="${transResource.bmBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
        <td><label class="control-label">报名结束时间：</label></td>
        <td><input type="text" name="bmEndTime" class="input-text Wdate" value="${transResource.bmEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
    </tr>
    <tr>
        <td><label class="control-label">保证金交纳开始时间：</label></td>
        <td><input type="text" name="bzjBeginTime" class="input-text Wdate"  value="${transResource.bzjBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
        <td><label class="control-label">保证金交纳结束时间：</label></td>
        <td><input type="text" name="bzjEndTime" class="input-text Wdate" value="${transResource.bzjEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
    </tr>
    <tr>
        <td><label class="control-label">资格审核类型：</label></td>
        <td>
            <select name="qualificationType" class="select">
                <option value="PRE_TRIAL" <#if transResource.qualificationType??&&transResource.qualificationType=='PRE_TRIAL'>selected</#if>>资格前审</option>
                <option value="POST_TRIAL" <#if transResource.qualificationType??&&transResource.qualificationType=='POST_TRIAL'>selected</#if>>资格后审</option>
            </select>
        </td>
        <td><label class="control-label"></label></td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><label class="control-label">建设要求：</label></td>
        <td colspan="3">
            <textarea name="constructRequirement" style="width: 100%" rows="5">${transResourceInfo.constructRequirement!}</textarea>
        </td>
    </tr>
    </table>
    <table class="table table-border table-bordered table-striped">
    <tr>
        <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">交易地块限价交易信息（选填）</td>
    </tr>

    <tr>
        <td width="180"><label class="control-label">最高限价（万元）：</label></td>
        <td><input type="text" name="maxOffer" class="input-text"  value="${transResource.maxOffer!}" ></td>
        <td width="180"><label class="control-label">公租房竞价类型:</label></td>
        <td width="300">
             <span class="select-box">
            <select name="publicHouse" class="select" onChange="houseChange()">
                <option value="0" <#if transResource.publicHouse?? && transResource.publicHouse==0 >selected="selected"</#if>>平方米（面积）</option>
                <option value="1" <#if transResource.publicHouse?? && transResource.publicHouse==1 >selected="selected"</#if>>万元（资金）</option>
            </select>
             </span>
        </td>
    </tr>
    <tr>
        <td><label class="control-label">起始公租房&nbsp;<span class="unit_house"><#if transResource.publicHouse?? && transResource.publicHouse==1>万元（资金）<#else>平方米（面积）</#if></span>：</label></td>
        <td><input type="text" name="beginHouse" class="input-text"  value="${transResource.beginHouse!}" ></td>
        <td><label class="control-label">公租房增幅&nbsp;<span class="unit_house"><#if transResource.publicHouse?? && transResource.publicHouse==1>万元（资金）<#else>平方米（面积）</#if></span>:</label></td>
        <td><input type="text" name="addHouse" class="input-text"  value="${transResource.addHouse!}" ></td>
    </tr>
    </table>

    <table class="table table-border table-bordered table-striped">
    <tr>
        <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700" >选择保证金交纳银行（必选）--<a href="#" onclick="">全选</a> </td>
    </tr>
    <tr>
        <td colspan="4" id="bankList" >
            <#include "common/resource-bank.ftl"/>
        </td>
    </tr>
    </table>

    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700" >其他地块规划信息</td>
        </tr>
        <tr>
            <td width="210"><label class="control-label">建筑面积（平方米）：</label></td>
            <td><input type="text" name="buildingArea" class="input-text"  value="${transResourceInfo.buildingArea!}"></td>
            <td width="210"><label class="control-label">出让年限（年）：</label></td>
            <td><input type="text" name="yearCount" class="input-text" value="${transResourceInfo.yearCount!}"></td>
        </tr>
        <tr>
            <td><label class="control-label">容积率(＜、＞、＝、≥、≤)：</label></td>
            <td><input type="text" name="plotRatio" class="input-text"  value="${transResourceInfo.plotRatio!}"></td>
            <td><label class="control-label">绿化率(＜、＞、＝、≥、≤)：</label></td>
            <td><input type="text" name="greeningRate" class="input-text" value="${transResourceInfo.greeningRate!}"></td>
        </tr>
        <tr>
            <td><label class="control-label">建筑密度：</label></td>
            <td><input type="text" name="buildingDensity" class="input-text"  value="${transResourceInfo.buildingDensity!}"></td>
            <td><label class="control-label">建筑限高（米）</label></td>
            <td><input type="text" name="buildingHeight" class="input-text" value="${transResourceInfo.buildingHeight!}"></td>
        </tr>
        <tr>
            <td><label class="control-label">办公与服务设施用地比例（%）：</label></td>
            <td><input type="text" name="officeRatio" class="input-text"  value="${transResourceInfo.officeRatio!}"></td>
            <td><label class="control-label">投资强度：</label></td>
            <td><input type="text" name="investmentIntensity" class="input-text" value="${transResourceInfo.investmentIntensity!}"></td>
        </tr>
        <tr>
            <td><label class="control-label">建设内容</label></td>
            <td colspan="3">
                <textarea name="constructContent" style="width: 100%" rows="5">${transResourceInfo.constructContent!}</textarea>
            </td>
        </tr>
        <#if transResource.resourceId??>
            <tr style="height: 90px">
                <td rowspan="2"><label class="control-label">上传图片：</label></td>
                <td colspan="3" style="padding-left:8px;">

                    <div style="float: left;margin-right:5px;height:60px" id="imagePic">
                    <span class="btn-upload" style="height:60px">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input id="fileName" name="file" type="file" multiple="true" class="input-file"
                               accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp">
                    </span>
                    </div>
                </td>
            </tr>
        <tr>
            <td colspan="3">
                <span style="color: red;">注：只允许上传jpg等图片文件，其大小不超过300K</span>
                <div class="progress-bar upload-progress-bar" id="imageProgressBar">
                    <span class="sr-only"/>
                </div>
            </td>

        </tr>
            <tr>
                <td rowspan="2"><label class="control-label">附件：</label></td>
                <td style="width: 200px">
                    <span style="color: red;">注：只允许上传以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过5M</span>
                </td>
                <td colspan="2" style="padding-left:8px;">
                    <div style="float: left;">
                        <label class="control-label">附件类型：</label>
                        <select id="attachmentType">
                            <#list attachmentCategory?keys as key>
                                <option value="${key}" <#if key_index==0>selected</#if>>${attachmentCategory[key]}</option>
                            </#list>
                        </select>
                    </div>

                    <div id="attachmentBtn">
                    <span class="btn-upload" style="height:60px;margin-left: 10px">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input id="attachmentFile" name="file" type="file" multiple="true" class="input-file"
                               accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf,application/msword">
                    </span>
                    </div>
                    <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                        <span class="sr-only"/>
                    </div>

                </td>

            </tr>

            <tr style="height: 90px">
                <td colspan="3" style="padding-left:8px;">
                    <div id="attachmentsTitle">
                    </div>
                    <div id="attachments">
                    </div>
                    <div id="attachmentsOperation">
                    </div>


                </td>

            </tr>
        </#if>

    </table>

    <input type="hidden" name="resourceId" value="${transResource.resourceId!}">
    <input type="hidden" name="ggId" value="${transResource.ggId!}">
    <input type="hidden" name="infoId" value="${transResourceInfo.infoId!}">
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/resource/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
</body>
</html>