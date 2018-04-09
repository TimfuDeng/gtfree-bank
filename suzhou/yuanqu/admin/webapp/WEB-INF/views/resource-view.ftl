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
    <#--<link href="${base}/static/thridparty/uploadifive/uploadifive.css" rel="stylesheet" type="text/css" />-->
    <script type="text/javascript" src="${base}/static/thridparty/uploadifive/jquery.uploadifive.min.js"></script>
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
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">交易地块基本信息（以下带有红色*号为必填）</td>
        </tr>
        <tr>
            <td width="180"><label class="control-label">地块编号：</label></td>
            <td><input type="text" class="input-text" name="resourceCode" disabled="disabled" value="${transResource.resourceCode!}"></td>
            <td width="180"><label class="control-label">行政区部门：</label></td>
            <td width="300"><@SelectHtml SelectName="regionCode"  SelectObjects=regionAllList Width=0 SelectValue=transResource.regionCode onChange="regionChanged"/></td>
        </tr>
        <tr>
            <td><label class="control-label">地块座落：</label></td>
            <td colspan="3"><input type="text" class="input-text" disabled="disabled" name="resourceLocation" value="${transResource.resourceLocation!}"></td>
        </tr>

        <tr>
            <td><label class="control-label">竞价单位：</label></td>
            <td>
            <span class="select-box">
                <#--onChange="unitChange()"-->
                    <select disabled="disabled" name="offerUnit" class="select" >
                        <option value="0" <#if transResource.offerUnit?? && transResource.offerUnit==0 >selected="selected"</#if>>万元（总价）</option>
                        <option value="1" <#if transResource.offerUnit?? && transResource.offerUnit==1 >selected="selected"</#if>>元/平方米（单价）</option>
                        <option value="2" <#if transResource.offerUnit?? && transResource.offerUnit==2 >selected="selected"</#if>>万元/亩（单价）</option>
                    </select>
            </span>
            </td>
            <td><label class="control-label">规划用途：</label></td>

          <#-- <span class="select-box">
                <select name="landUse" class="select" >
                    <option value="SFYD" <#if transResource.landUse?? && transResource.landUse.code=="05" >selected="selected"</#if>>商服用地</option>
                    <option value="GKCCYD" <#if transResource.landUse?? && transResource.landUse.code=="06"  >selected="selected"</#if>>工矿仓储用地</option>
                    <option value="CKYD" <#if transResource.sonLandUse?? && transResource.sonLandUse.code=="062"  >selected="selected"</#if>>采矿用地</option>
                    <option value="CCYD" <#if transResource.sonLandUse?? && transResource.sonLandUse.code=="063"  >selected="selected"</#if>>仓储用地</option>

                    <option value="GY" <#if transResource.landUse?? && transResource.landUse.code=="064" >selected="selected"</#if>>工业</option>
                    <option value="CC" <#if transResource.landUse?? && transResource.landUse.code=="065" >selected="selected"</#if>>仓储</option>
                    <option value="KJ" <#if transResource.landUse?? && transResource.landUse.code=="066" >selected="selected"</#if>>科教</option>
                    <option value="GYBZCF" <#if transResource.landUse?? && transResource.landUse.code=="067" >selected="selected"</#if>>工业(标准厂房)</option>
                    <option value="GYGBZCF" <#if transResource.landUse?? && transResource.landUse.code=="068" >selected="selected"</#if>>工业(高标准厂房)</option>
                    <option value="GYYF" <#if transResource.landUse?? && transResource.landUse.code=="069" >selected="selected"</#if>>工业(研发)</option>
                    <option value="GYWHCY" <#if transResource.landUse?? && transResource.landUse.code=="070" >selected="selected"</#if>>工业(文化创意)</option>
                    <option value="GYZBJJ" <#if transResource.landUse?? && transResource.landUse.code=="071" >selected="selected"</#if>>工业(总部经济)</option>
                    <option value="KJYF" <#if transResource.landUse?? && transResource.landUse.code=="072" >selected="selected"</#if>>科教(研发)</option>
                    <option value="KJWHCY" <#if transResource.landUse?? && transResource.landUse.code=="073" >selected="selected"</#if>>科教(文化创意)</option>
                    <option value="KJZBJJ" <#if transResource.landUse?? && transResource.landUse.code=="074" >selected="selected"</#if>>科教(总部经济)</option>

                    <option value="ZZYD" <#if transResource.landUse?? && transResource.landUse.code=="07"  >selected="selected"</#if>>住宅用地</option>
                    <option value="GGGLYGGFWYD" <#if transResource.landUse?? && transResource.landUse.code=="08"  >selected="selected"</#if>>公共管理与公共服务用地</option>
                    <option value="TSYD" <#if transResource.landUse?? && transResource.landUse.code=="09"  >selected="selected"</#if>>特殊用地</option>
                    <option value="JTYSYD" <#if transResource.landUse?? && transResource.landUse.code=="10"  >selected="selected"</#if>>交通运输用地</option>
                    <option value="SYJSLSSYD" <#if transResource.landUse?? && transResource.landUse.code=="11"  >selected="selected"</#if>>水域及水利设施用地</option>
                    <option value="QT" <#if transResource.landUse?? && transResource.landUse.code=="99"  >selected="selected"</#if>>其它用地</option>
                </select>
            </span>-->
            <#--  <@LandUseSelect selectName="landUse" selectValue=transResource.landUse.code!  />-->
            <td width="300">
            <#assign landUseDictList = ResourceUtil.getLandUseDictList() />
                <div  align="left" style="position:absolute;z-index:90;margin-top:-15px">
                    <div class="zTreeDemoBackground left">
                        <ul class="list">
                            <li class="title">
                                <select id="landUseMuli" name="landUseMuli" type="select" style="width: 250px;height: 31px;" disabled="disabled" >
                                <#if transResource.landUseMuli??>
                                    <option  selected="selected">
                                        ---请选择---
                                    </option>
                                </#if>
                                <#list landUseDictList as obj>
                                    <#if transResource.landUseMuli??&&transResource.landUseMuli==obj.code>
                                        <option value="${selectValue!}" selected="selected">
                                        ${obj.name!}
                                        </option>
                                    </#if>
                                </#list>
                                </select>
                            </li>
                        </ul>
                    </div>
            </td>


        </tr>
        <tr>
            <td><label class="control-label">保证金（万元）：</label></td>
            <td><input disabled="disabled" type="text" name="fixedOffer" class="input-text"  value="${transResource.fixedOffer!}" ></td>
            <td><label class="control-label">保证金（美元）：</label></td>
            <td><input disabled="disabled"  type="text" name="fixedOfferUsd" class="input-text" value="${transResource.fixedOfferUsd!}" ></td>
        </tr>
        <tr>
            <td><label class="control-label">保证金（万港币）：</label></td>
            <td><input disabled="disabled" type="text" name="fixedOfferHkd" class="input-text" value="${transResource.fixedOfferHkd!}" maxlength="32"></td>
            <td><label class="control-label">起始价<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
            <td><input disabled="disabled"  type="text" name="beginOffer" class="input-text"  value="${transResource.beginOffer!}" ></td>

        </tr>
        <tr>

            <td><label class="control-label">增价幅度<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
            <td><input disabled="disabled"  type="text" name="addOffer" class="input-text" value="${transResource.addOffer!}"></td>
            <td><label class="control-label">出让面积（平方米）：</label></td>
            <td><input disabled="disabled"  type="text" name="crArea" class="input-text" value="${transResource.crArea!}" ></td>
        </tr>
        <tr>

            <td><label class="control-label">供地方式：</label></td>
            <td>
                <span class="select-box">
                <select disabled="disabled"  name="offerUnit" class="select" >
                    <option value="00" <#if transResource.dealType?? && transResource.dealType=='00' >selected="selected"</#if>>出让</option>
                </select>
            </span>
            </td>
            <td><label class="control-label">是否有底价：</label></td>
            <td>
                <input disabled="disabled" type="checkbox" name="minOffer" value="${transResource.minOffer?string(1,0)}" onchange="minOfferChange()" <#if transResource.minOffer==true>checked</#if> >
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
            <td><input disabled="disabled"  type="text" name="gpBeginTime" class="input-text Wdate" onchange="beginDateChange()"  value="${transResource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
            <td><label class="control-label">挂牌截止时间：</label></td>
            <td><input disabled="disabled"  type="text" name="gpEndTime" class="input-text Wdate" value="${transResource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
        </tr>
        <tr>
            <td><label class="control-label">报名开始时间：</label></td>
            <td><input disabled="disabled"  type="text" name="bmBeginTime" class="input-text Wdate"  value="${transResource.bmBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
            <td><label class="control-label">报名截止时间：</label></td>
            <td><input disabled="disabled"  type="text" name="bmEndTime" class="input-text Wdate" value="${transResource.bmEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
        </tr>
        <tr>
            <td><label class="control-label">保证金交纳开始时间：</label></td>
            <td><input disabled="disabled" type="text" name="bzjBeginTime" class="input-text Wdate"  value="${transResource.bzjBeginTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})"></td>
            <td><label class="control-label">保证金交纳截止时间：</label></td>
            <td><input disabled="disabled" type="text" name="bzjEndTime" class="input-text Wdate" value="${transResource.bzjEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',alwaysUseStartDate:true})"></td>
        </tr>
        <tr>
            <td><label class="control-label">首次报价截止时间：</label></td>
            <td><input disabled="disabled" type="text" id="yxEndTime" name="yxEndTime" class="input-text Wdate" value="<#if transResource.yxEndTime??>${transResource.yxEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if> " onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',minDate:'#F{$dp.$D(\'gpBeginTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
            <td><label class="control-label">限时报价开始时间：</label></td>
            <td><input  disabled="disabled" type="text" id="xsBeginTime" name="xsBeginTime" class="input-text Wdate" value="<#if transResource.xsBeginTime??>${transResource.xsBeginTime?string("yyyy-MM-dd HH:mm:ss")}</#if> " onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',minDate:'#F{$dp.$D(\'gpEndTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
        </tr>
        <tr>
            <td><label class="control-label">产业用地</label></td>
            <td colspan="3">
                <textarea name="industryType" style="width: 100%" rows="3" maxlength="300">${transResourceInfo.industryType!}</textarea>
            </td>
        </tr>
    </table>

    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="5" style="text-align: center;font-size: 14px;font-weight:700">交易地块多规划用途信息（选填）
        <span style="float: right">

        </span>
            </td>
        </tr>
        <tr>
            <td  align="center"><label class="control-label">#</label></td>
            <td width="250" align="center"><label class="control-label">宗地编号</label></td>
            <td width="250" align="center"><label class="control-label">规划用途</label></td>
            <td width="200" align="center"><label class="control-label">年限</label></td>
            <td width="200" align="center"><label class="control-label">面积</label></td>
        </tr>
    <#if resourceSonList?? && (resourceSonList?size>0)>
        <#list resourceSonList as resourceSon>
            <tr>
                <td width="180" align="center"><input type="hidden" name="sonId" value="${resourceSon.sonId!}"></td>
                <td width="180" align="center"><input type="text" name="zdCode" class="input-text"   value="${resourceSon.zdCode!}" ></td>
            <#--    <td>
             <span class="select-box">
                 <select name="sonLandUse" class="select" >
                     <option value="SFYD" <#if resourceSon.sonLandUse?? && resourceSon.sonLandUse.code=="05" >selected="selected"</#if>>商服用地</option>
                     <option value="GKCCYD" <#if resourceSon.sonLandUse?? && resourceSon.sonLandUse.code=="06"  >selected="selected"</#if>>工矿仓储用地</option>
                     <option value="ZZYD" <#if resourceSon.sonLandUse?? && resourceSon.sonLandUse.code=="07"  >selected="selected"</#if>>住宅用地</option>
                     <option value="GGGLYGGFWYD" <#if resourceSon.sonLandUse?? &&  resourceSon.sonLandUse.code=="08"  >selected="selected"</#if>>公共管理与公共服务用地</option>
                     <option value="TSYD" <#if resourceSon.sonLandUse?? &&  resourceSon.sonLandUse.code=="09"  >selected="selected"</#if>>特殊用地</option>
                     <option value="JTYSYD" <#if resourceSon.sonLandUse?? &&  resourceSon.sonLandUse.code=="10"  >selected="selected"</#if>>交通运输用地</option>
                     <option value="SYJSLSSYD" <#if resourceSon.sonLandUse?? &&  resourceSon.sonLandUse.code=="11"  >selected="selected"</#if>>水域及水利设施用地</option>
                     <option value="QT" <#if resourceSon.sonLandUse?? && resourceSon.sonLandUse.code=="99"  >selected="selected"</#if>>其它用地</option>
                 </select>
            </span>

                </td>-->
                <td width="180" align="center"><input type="text" name="sonLandUseMuli" class="input-text"   value="${resourceSon.sonLandUseMuli!}" ></td>
                <td width="180" align="center"><input type="text" name="sonYearCount" class="input-text"  value="${resourceSon.sonYearCount!}" ></td>
                <td width="180" align="center"><input type="text" name="zdArea" class="input-text"  value="${resourceSon.zdArea!}" ></td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td width="180" align="center"><input type="hidden" value=""></td>
            <td width="180" align="center"><input type="text" name="zdCode" class="input-text"   value="${transResourceSon.zdCode!}" ></td>
            <#--<td>
             <span class="select-box">
                  <select name="sonLandUse" class="select" >
                      <option value="SFYD" <#if transResourceSon.sonLandUse?? && transResourceSon.sonLandUse.code=="05" >selected="selected"</#if>>商服用地</option>
                      <option value="GKCCYD" <#if transResourceSon.sonLandUse?? && transResourceSon.sonLandUse.code=="06"  >selected="selected"</#if>>工矿仓储用地</option>
                      <option value="ZZYD" <#if transResourceSon.sonLandUse?? && transResourceSon.sonLandUse.code=="07"  >selected="selected"</#if>>住宅用地</option>
                      <option value="GGGLYGGFWYD" <#if transResourceSon.sonLandUse?? &&  transResourceSon.sonLandUse.code=="08"  >selected="selected"</#if>>公共管理与公共服务用地</option>
                      <option value="TSYD" <#if transResourceSon.sonLandUse?? &&  transResourceSon.sonLandUse.code=="09"  >selected="selected"</#if>>特殊用地</option>
                      <option value="JTYSYD" <#if transResourceSon.sonLandUse?? &&  transResourceSon.sonLandUse.code=="10"  >selected="selected"</#if>>交通运输用地</option>
                      <option value="SYJSLSSYD" <#if transResourceSon.sonLandUse?? &&  transResourceSon.sonLandUse.code=="11"  >selected="selected"</#if>>水域及水利设施用地</option>
                      <option value="QT" <#if transResourceSon.sonLandUse?? && transResourceSon.sonLandUse.code=="99"  >selected="selected"</#if>>其它用地</option>
                  </select>
            </span>
            </td>-->
            <td width="180" align="center"><input type="text" name="sonLandUseMuli" class="input-text"   value="${transResourceSon.sonLandUseMuli!}" ></td>
            <td width="180" align="center"><input type="text" name="sonYearCount" class="input-text"  value="${transResourceSon.sonYearCount!}" ></td>
            <td width="180" align="center"><input type="text" name="zdArea" class="input-text"  value="${transResourceSon.zdArea!}" ></td>
        </tr>
    </#if>


    </table>


    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700" >其他地块规划信息</td>
        </tr>


        <tr>
            <td width="210"><label class="control-label">建筑面积（平方米）：</label></td>
            <td><input disabled="disabled" type="text" name="buildingArea" class="input-text"  value="${transResourceInfo.buildingArea!}"></td>
            <td width="210"><label class="control-label">商业建筑面积（平方米）：</label></td>
            <td><input disabled="disabled" type="text" name="shconArea" class="input-text" value="${transResourceInfo.shconArea!}"></td>

        </tr>
        <tr>
            <td width="210"><label class="control-label">住宅建筑面积（平方米）：</label></td>
            <td><input disabled="disabled" type="text" name="zzconArea" class="input-text"  value="${transResourceInfo.zzconArea!}"></td>
            <td width="210"><label class="control-label">办公建筑面积（平方米）：</label></td>
            <td><input disabled="disabled" type="text" name="bgconArea" class="input-text" value="${transResourceInfo.bgconArea!}"></td>
        </tr>
        <tr>
            <td width="210"><label class="control-label">出让年限（年）：</label></td>
            <td><input disabled="disabled" type="text" name="yearCount" class="input-text" value="${transResourceInfo.yearCount!}"></td>
            <td width="210"><label class="control-label">约定土地交易条件 ：</label></td>
            <td><input disabled="disabled" type="text" name="tdTjXx" class="input-text"  value="${transResourceInfo.tdTjXx!}"></td>

        </tr>
        <tr>
            <td width="210"><label class="control-label">场地平整 ：</label></td>
            <td><input disabled="disabled" type="text" name="cdPz" class="input-text" value="${transResourceInfo.cdPz!}"></td>
            <td width="210"><label class="control-label">基础设施 ：</label></td>
            <td ><input disabled="disabled" type="text" name="jcSs" class="input-text"  value="${transResourceInfo.jcSs!}"></td>

        </tr>



        <tr>
            <td><label class="control-label">办公与服务设施用地比例（%）：</label></td>
            <td><input disabled="disabled" type="text" name="officeRatio" class="input-text"  value="${transResourceInfo.officeRatio!}"></td>
            <td><label class="control-label">投资强度：</label></td>
            <td><input disabled="disabled" type="text" name="investmentIntensity" class="input-text" value="${transResourceInfo.investmentIntensity!}"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="text" name="minRjl" class="input-text"  style="width: 23%" value="${transResourceInfo.minRjl!}" maxlength="12" readonly="readonly">
                <span class="select-box" style="width: 15%">
                    <select name="selrj2" class="select" disabled="disabled">
                        <option value="00" <#if transResourceInfo.selrj2?? && transResourceInfo.selrj2=='02' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.selrj2?? && transResourceInfo.selrj2=='03' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>

                <label class="control-label">容积率&nbsp;&nbsp;：</label>

                <span class="select-box" style="width: 15%;margin-left:18px">
                    <select name="selrjl" class="select" disabled="disabled">
                        <option value="02" <#if transResourceInfo.selrjl?? && transResourceInfo.selrjl=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="03" <#if transResourceInfo.selrjl?? && transResourceInfo.selrjl=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" name="maxRjl" class="input-text"   style="width:23%" value="${transResourceInfo.maxRjl!}" maxlength="12" readonly="readonly">
            </td>

            <td colspan="2">
                <input type="text" name="minJzMd" class="input-text" value="${transResourceInfo.minJzMd!}" maxlength="12" style="width: 23%" readonly="readonly">
                <span class="select-box" style="width: 15%">
                    <select name="maxJzMdTag" class="select" disabled="disabled">
                        <option value="00" <#if transResourceInfo.maxJzMdTag?? && transResourceInfo.maxJzMdTag=='02' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.maxJzMdTag?? && transResourceInfo.maxJzMdTag=='03' >selected="selected"</#if>>&le;</option>
                    </select>
                 </span>


                <label class="control-label">建筑密度 (%)：</label>

                <span class="select-box" style="width: 15%">
                    <select name="minJzMdTag" class="select" disabled="disabled">
                        <option value="02" <#if transResourceInfo.minJzMdTag?? && transResourceInfo.minJzMdTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="03" <#if transResourceInfo.minJzMdTag?? && transResourceInfo.minJzMdTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" name="maxJzMd" class="input-text"  value="${transResourceInfo.maxJzMd!}" maxlength="12" style="width: 23%" readonly="readonly">

            </td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="text" name="minLhl" class="input-text" value="${transResourceInfo.minLhl!}" maxlength="12" style="width: 23%" readonly="readonly">
                <span class="select-box" style="width: 15%">
                    <select name="maxLhlTag" class="select" disabled="disabled">
                        <option value="00" <#if transResourceInfo.maxLhlTag?? && transResourceInfo.maxLhlTag=='02' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.maxLhlTag?? && transResourceInfo.maxLhlTag=='03' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>

                <label class="control-label">绿化率(%) ：</label>

                <span class="select-box" style="width: 15%">
                    <select name="minLhlTag" class="select" disabled="disabled">
                        <option value="02" <#if transResourceInfo.minLhlTag?? && transResourceInfo.minLhlTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="03" <#if transResourceInfo.minLhlTag?? && transResourceInfo.minLhlTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" name="maxLhl" class="input-text"  value="${transResourceInfo.maxLhl!}" maxlength="12" style="width: 23%" readonly="readonly">
            </td>
            <td colspan="2">
                <input type="text" id="zxjzxg" name="minJzxg" class="input-text" value="${transResourceInfo.minJzxg!}" maxlength="12" style="width: 23%" readonly="readonly">
                <span class="select-box" style="width: 15%">
                    <select name="maxJzXgTag" class="select" disabled="disabled">
                        <option value="00" <#if transResourceInfo.maxJzXgTag?? && transResourceInfo.maxJzXgTag=='02' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.maxJzXgTag?? && transResourceInfo.maxJzXgTag=='03' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>

                <label class="control-label">建筑限高(m) ：</label>

                <span class="select-box" style="width: 15%">
                    <select name="minJzXgTag" class="select" disabled="disabled">
                        <option value="02" <#if transResourceInfo.minJzXgTag?? && transResourceInfo.minJzXgTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="03" <#if transResourceInfo.minJzXgTag?? && transResourceInfo.minJzXgTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" id="zdjzxg" name="maxJzxg" class="input-text"  value="${transResourceInfo.maxJzxg!}" maxlength="12" style="width: 23%" readonly="readonly">
            </td>
        </tr>
        <tr>
            <td><label class="control-label">建设内容</label></td>
            <td colspan="3">
                <textarea disabled="disabled" name="constructContent" style="width: 100%" rows="5">${transResourceInfo.constructContent!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">土地交付条件</label></td>
            <td colspan="3">
                <textarea disabled="disabled"  name="deliverTerm" style="width: 100%" rows="5">${transResourceInfo.deliverTerm!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">规划要点</label></td>
            <td colspan="3">
                <textarea disabled="disabled" name="layoutOutline" style="width: 100%" rows="5">${transResourceInfo.layoutOutline!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">地块介绍</label></td>
            <td colspan="3">
                <textarea disabled="disabled" name="description" style="width: 100%" rows="5">${transResourceInfo.description!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">周边环境</label></td>
            <td colspan="3">
                <textarea disabled="disabled" name="surroundings" style="width: 100%" rows="5">${transResourceInfo.surroundings!}</textarea>
            </td>
        </tr>

        <tr>
            <td><label class="control-label">备注</label></td>
            <td colspan="3">
                <textarea disabled="disabled" name="memo" style="width: 100%" rows="5">${transResourceInfo.memo!}</textarea>
            </td>
        </tr>
    <#if transResource.resourceId??>
        <tr style="height: 90px">
            <td rowspan="2"><label class="control-label">上传图片：</label></td>
            <td colspan="3" style="padding-left:8px;">

                <div style="float: left;margin-right:5px;height:60px" id="imagePic">
                    <span class="btn-upload" style="height:60px">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input disabled="disabled"  id="fileName" name="file" type="file" multiple="true" class="input-file"
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
                <span style="color: red;">注：只允许上传以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过10M</span>
            </td>
            <td colspan="2" style="padding-left:8px;">
                <div style="float: left;">
                    <label class="control-label">附件类型：</label>
                    <select disabled="disabled"  id="attachmentType">
                        <#list attachmentCategory?keys as key>
                            <option value="${key}" <#if key_index==0>selected</#if>>${attachmentCategory[key]}</option>
                        </#list>
                    </select>
                </div>

                <div id="attachmentBtn">
                    <span class="btn-upload" style="height:60px;margin-left: 10px">
                        <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                        <input disabled="disabled"  id="attachmentFile" name="file" type="file" multiple="true" class="input-file"
                               accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf">
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