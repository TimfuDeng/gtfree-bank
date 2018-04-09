<html>
<head>
    <title>-已发布地块详情</title>
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
        .btn-upload{ position: relative;display: inline-block;overflow: hidden; vertical-align: middle; cursor: pointer;}
        .upload-url{ width: 200px;cursor: pointer;}
        .input-file{ position:absolute; right:0; top:0; cursor: pointer; font-size:17px;opacity:0;filter: alpha(opacity=0)}
        .uploadifive-button{
            display: block;
            width: 60px;
            height: 60px;
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }
        .btn-upload input{
            height:55px;
            cursor: pointer;
        }
        .image{
            padding-right:10px;
            float: left;
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

    </style>
    <script type="text/javascript">
        var fileTypeMap;
        $(document).ready(function(){
            $('select').attr("disabled","disabled");
            $('.select-box').css('background-color','rgb(235, 235, 228)');
            $('select').css('background-color','rgb(235, 235, 228)');
            getAttachments('${transResource.resourceId!}');

            $('#attachmentType').removeAttr("disabled");

            fileTypeMap = {};
            $('#attachmentType option').each(function(){
                fileTypeMap[$(this).val()]=$(this).text();
            });

        <#if transResource.resourceId??>
            $.get("${base}/file/thumbnails.f?fileKey=${transResource.resourceId}&resolution=280_185", function(result){
                for(var i=0;i<result.length;i++){
                    insertImage(result[i].fileId);
                }
            });
        </#if>
        });

        function insertImage(fileId){
            $("<img width='100' height='60' class='image' onerror='this.src=\"${base}/static/image/blank.jpg\"' src='${base}/file/view.f?fileId=" +fileId+ "'></image>").insertBefore("#imagePic");
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

        function getFileTypeName(fileType){
            return fileTypeMap[fileType];
        }
        function insertAttachment(fileId,fileName,fileType) {
            $("<a class='btn btn-link' >"+getFileTypeName(fileType)+"</a><br/>").appendTo("#attachmentsTitle");
            $("<a class='btn btn-link' target='_blank' href='${base}/file/get?fileId=" +fileId+ "'>"+fileName+"</a><br/>").appendTo("#attachments");
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/qualified/list/land?">资格审核</a>
    <span class="c_gray en">&gt;</span>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/resource/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">交易地块基本信息</td>
        </tr>
        <tr>
            <td width="180"><label class="control-label">地块编号：</label></td>
            <td><input type="text" class="input-text" name="resourceCode" value="${transResource.resourceCode!}" disabled="disabled"></td>
            <td width="180"><label class="control-label">行政区部门：</label></td>
            <td width="300">
            <@SelectHtml SelectName="regionCode"  SelectObjects=regionAllList Width=0 SelectValue=transResource.regionCode onChange="regionChanged"/>

            </td>
        </tr>
        <tr>
            <td><label class="control-label">地块座落：</label></td>
            <td colspan="3"><input type="text" class="input-text" name="resourceLocation" value="${transResource.resourceLocation!}" disabled="disabled"></td>
        </tr>

        <tr>
            <td><label class="control-label">竞价单位：</label></td>
            <td>
            <span class="select-box">
                <select name="offerUnit" class="select" onChange="unitChange()">
                    <option value="0" <#if transResource.offerUnit?? && transResource.offerUnit==0 >selected="selected"</#if>>万元（总价）</option>
                    <option value="06" <#if transResource.offerUnit?? && transResource.offerUnit==1 >selected="selected"</#if>>元/平方米（单价）</option>
                    <option value="07" <#if transResource.offerUnit?? && transResource.offerUnit==2 >selected="selected"</#if>>万元/亩（单价）</option>
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
            <td><input type="text" name="fixedOffer" class="input-text"  value="${transResource.fixedOffer!}" disabled="disabled"></td>
            <td><label class="control-label">保证金（美元）：</label></td>
            <td><input type="text" name="fixedOfferUsd" class="input-text" value="${transResource.fixedOfferUsd!}" disabled="disabled"></td>
        </tr>
        <tr>
            <td><label class="control-label">起始价<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
            <td><input type="text" name="beginOffer" class="input-text"  value="${transResource.beginOffer!}" disabled="disabled"></td>
            <td><label class="control-label">增价幅度<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
            <td><input type="text" name="addOffer" class="input-text" value="${transResource.addOffer!}" disabled="disabled"></td>
        </tr>

        <tr>
            <td><label class="control-label">出让面积（平方米）：</label></td>
            <td><input type="text" name="crArea" class="input-text" value="${transResource.crArea!}" disabled="disabled"></td>
            <td><label class="control-label">是否有底价：</label></td>
            <td>
                <input type="checkbox" name="minOffer" value="${transResource.minOffer?string(1,0)}" <#if transResource.minOffer==true>checked</#if> disabled="disabled">
                <label class="minOfferInfo">
                <#if transResource.minOffer>
                    请务必在挂牌截至前30分钟内录入底价，否则底价默认为0！
                <#else>
                    无底价！
                </#if>
                </label>
            </td>
        </tr>

        <tr>
            <td><label class="control-label">挂牌开始时间：</label></td>
            <td><input type="text" name="gpBeginTime" disabled="disabled" class="input-text " onchange="beginDateChange()"  value="${transResource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
            <td><label class="control-label">挂牌截止时间：</label></td>
            <td><input type="text" name="gpEndTime" disabled="disabled" class="input-text " value="${transResource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
        </tr>
        <tr>
            <td><label class="control-label">报名开始时间：</label></td>
            <td><input type="text" name="bmBeginTime" disabled="disabled" class="input-text "  value="${transResource.bmBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
            <td><label class="control-label">报名截止时间：</label></td>
            <td><input type="text" name="bmEndTime" disabled="disabled" class="input-text " value="${transResource.bmEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
        </tr>
        <tr>
            <td><label class="control-label">保证金交纳开始时间：</label></td>
            <td><input type="text" name="bzjBeginTime" disabled="disabled" class="input-text "  value="${transResource.bzjBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
            <td><label class="control-label">保证金交纳截止时间：</label></td>
            <td><input type="text" name="bzjEndTime" disabled="disabled" class="input-text " value="${transResource.bzjEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
        </tr>
    </table>
<#if transResourceVerify??>
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">交易地块审核信息</td>
        </tr>

        <tr>
            <td width="180"><label class="control-label">审核人：</label></td>
            <td><input type="text" class="input-text"  value="${auditor!}" disabled="disabled"></td>
            <td width="180"><label class="control-label">审核时间:</label></td>
            <td width="300">
                <input type="text" class="input-text"  value="${(transResourceVerify.verifyTime!)?string("yyyy-MM-dd HH:mm:ss")}" disabled="disabled">
            </td>
        </tr>
        <tr>
            <td><label class="control-label">是否审核</label></td>
            <td>
                <#if transResourceVerify.verifyType==1>
                    <input type="text" class="input-text"  value="已审核" disabled="disabled">
                <#else>
                    <input type="text" class="input-text"  value="未审核" disabled="disabled">
                </#if>
            </td>
            <td><label class="control-label">是否通过</label></td>
            <td>
                <#if transResourceVerify.verifyStatus==1>
                    <input type="text" class="input-text"  value="已通过审核" disabled="disabled">
                <#else>
                    <input type="text" class="input-text"  value="未通过审核" disabled="disabled">
                </#if>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">审核意见</label></td>
            <td colspan="3">
                <textarea style="width: 100%" rows="5" disabled="disabled">${transResourceVerify.verifySuggestion!}</textarea>
            </td>
        </tr>
    </table>
</#if>
    <input type="hidden" name="resourceId" value="${transResource.resourceId!}">
</form>
</body>
</html>