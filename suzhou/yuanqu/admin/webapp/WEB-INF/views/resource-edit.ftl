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
        #attachments{width: 540px;float: left;overflow:hidden;text-overflow-ellipsis: '..';padding-left:45px;}
        #attachmentsOperation{float: right}

        .upload-progress-bar{
            display: none;
            width: 400px;
            margin-top: 5px;
        }

    </style>
    <link rel="stylesheet" href="${base}/static/thridparty/ztree.3.5/css/zTreeStyle/zTreeStyle.css" type="text/css">

    <script type="text/javascript">
        var fileTypeMap;
        var uploadFileQueue= [];
        var uploadIndex = -1;

        $(document).ready(function(){
            //加载土地用途
            var url="${base}/console/resource/list/znodes.f";
            loadZNodes(url,"treeDemo");
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
            var resourceId = '${resource.resourceId!}';
            if (resourceId == null || resourceId == '') {
                beginDateChange();
            }



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
            var endDate=bDate.addDays(10);
            $("input[name='gpEndTime']").val(endDate.toString("yyyy-MM-dd")+" 10:00:00");
            $("input[name='xsBeginTime']").val(endDate.toString("yyyy-MM-dd")+" 10:00:00");
            var endDate=bDate.addDays(10);
            $("input[name='yxEndTime']").val(endDate.toString("yyyy-MM-dd")+" 16:00:00");

        }

        /**
         *@annotation 如果是国土环保局就把时间默认修改为的公告默认时间
         *@author liushaoshuai【liushaoshuai@gtmap.cn】
         *@date 2017/8/21 19:59
         *@param
         *@return
         */
        /*function changeCrggDate(){
            if($("select[name='regionCode']").val()=="320503001"){
                 var bDate=$("input[name='gpBeginTime']").val();
                 bDate=new XDate(bDate);
                 var endDate=bDate.addDays(10);
                 $("input[name='gpEndTime']").val(endDate.toString("yyyy-MM-dd")+" 10:00:00");
                 $("input[name='xsBeginTime']").val(endDate.toString("yyyy-MM-dd")+" 10:00:00");
             }
        }*/


        var zdFlag=1;
        function checkInputFileter2Resource(){

            $("input:visible[type='text']").each(function(){
                $(this).val(stripscript($(this).val()));
            })

        }

        //input特殊字符过滤
        function stripscript(s) {
            var pattern=new RegExp("java|javascript|script|alert|\'|\"");
            if(undefined!=s&&s.length>0)
                for(var i=0;i<s.length;i++)
                {
                    s=s.replace(pattern,"").replace("/","").replace("\\","").replace("<","").replace(">","").replace(/(^\s*)|(\s*$)/g, "");
                }
            return s;
        }
        function checkForm(){
            checkInputFileter2Resource();
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
            if (!checkInputNull('gpBeginTime','挂牌开始时间必须填写!'))
                return false;
            if (!checkInputNull('gpEndTime','挂牌截止时间必须填写!'))
                return false;
            if (!checkInputNull('bmBeginTime','报名开始时间必须填写!'))
                return false;
            if (!checkInputNull('bmEndTime','报名截止时间必须填写!'))
                return false;
            if (!checkInputNull('bzjBeginTime','保证金交纳开始时间必须填写!'))
                return false;
            if (!checkInputNull('bzjEndTime','保证金交纳截止时间必须填写!'))
                return false;
            if (!checkInputNull('yxEndTime','首次报价截止时间必须填写!'))
                return false;
            if (!checkInputNull('xsBeginTime','限时报价开始时间必须填写!'))
                return false;
            if($("#landUseMuli").val()==""){
                alert("规划用途必须填写！");
                return false;
            }
            /*if($("input[name='maxOffer']").val()!="" && $("input[name='maxOffer']").val()>0 ){
                if (!checkInputNull('beginHouse','起始公租房必须填写!'))
                    return false;
                if (!checkInputNull('addHouse','公租房增幅必须填写!'))
                    return false;
            }*/
            var fixedOffer = $("input[name='fixedOffer']").val();
            var crArea = $("input[name='crArea']").val();
            var beginOffer = $("input[name='beginOffer']").val();
            var addOffer = $("input[name='addOffer']").val();
            var daizhArea = $("input[name='daizhArea']").val();
            var shconArea= $("input[name='shconArea']").val();
            var zzconArea= $("input[name='zzconArea']").val();
            var bgconArea= $("input[name='bgconArea']").val();
            var buildingArea= $("input[name='buildingArea']").val();
            var yearCount= $("input[name='yearCount']").val();
            var minRjl =$("input[name='minRjl']").val();
            var maxRjl =$("input[name='maxRjl']").val();
            var minJzMd =$("input[name='minJzMd']").val();
            var maxJzMd =$("input[name='maxJzMd']").val();
            var minLhl =$("input[name='minLhl']").val();
            var maxLhl =$("input[name='maxLhl']").val();
            var minJzXg = $("#zxjzxg").val();
            var maxJzXg = $("#zdjzxg").val();
            var officeRatio = $("input[name='officeRatio']").val();
            var investmentIntensity = $("input[name='investmentIntensity']").val();

            if(isNaN($.trim(investmentIntensity))){
                alert("投资强度必须是数字！");
                return false;
            }

            if($.trim(investmentIntensity)!="" && $.trim(investmentIntensity)<=0){
                alert("投资强度必须大于0！");
                return false;
            }


            if(isNaN(fixedOffer)){
                alert("保证金必须是数字！");
                return false;
            }else{
                if(fixedOffer<=0){
                    alert("请填写正确的保证金！");
                    return false;
                }
            }


            if(isNaN($("input[name='fixedOfferHkd']").val())){
                alert("保证金(港币)必须是数字！");
                return false;
            }else {
                if($("input[name='fixedOfferHkd']").val()!=""){
                    if($("input[name='fixedOfferHkd']").val()<=0){
                        alert("请填写正确的保证金(港币)！");
                        return false;
                    }
                }
            }

            if(isNaN($("input[name='fixedOfferUsd']").val())){
                alert("保证金(美元)必须是数字！");
                return false;
            }else if($("input[name='fixedOfferUsd']").val()!=""){
                if($("input[name='fixedOfferUsd']").val()<=0){
                    alert("请填写正确的保证金(美元)！");
                    return false;
                }
            }

            if(isNaN(crArea)){
                alert("出让面积必须是数字！");
                return false;
            }else if(crArea!=""){
                if(crArea<=0){
                    alert("请填写正确的出让面积！");
                    return false;
                }
            }

            if(isNaN(beginOffer)){
                alert("起始价必须是数字！");
                return false;
            }else if(beginOffer!=""){
                if(beginOffer<=0){
                    alert("请填写正确的起始价！");
                    return false;
                }
            }

            if(isNaN(addOffer)){
                alert("增价幅度必须是数字！");
                return false;
            }else if(addOffer!=""){
                if(addOffer<=0){
                    alert("请填写正确的增价幅度！");
                    return false;
                }
            }

            /*if(isNaN(daizhArea)){
                alert("代征土地面积必须是数字！");
                return false;
            }*/
            if(isNaN(shconArea)){
                alert("商业建筑面积必须是数字！");
                return false;
            }else if(shconArea!=""){
                if(shconArea<=0){
                    alert("请填写正确的商业建筑面积！");
                    return false;
                }
            }

            if(isNaN(zzconArea)){
                alert("住宅建筑面积必须是数字！");
                return false;
            }else if(zzconArea!=""){
                if(zzconArea<=0){
                    alert("请填写正确的住宅建筑面积！");
                    return false;
                }
            }

            if(isNaN(bgconArea)){
                alert("办公建筑面积必须是数字！");
                return false;
            }else if(bgconArea!=""){
                if(bgconArea<=0){
                    alert("请填写正确的办公建筑面积！");
                    return false;
                }
            }

            if(isNaN(buildingArea)){
                alert("建筑面积必须是数字！");
                return false;
            }else if(buildingArea!=""){
                if(buildingArea<=0){
                    alert("请填写正确的建筑面积！");
                    return false;
                }
            }

           /* if(isNaN(yearCount)){
                alert("出让年限必须是数字！");
                return false;
            }else if(yearCount!=""){
                if(yearCount<=0){
                    alert("请填写正确的出让年限！");
                    return false;
                }
            }*/

            if(isNaN(officeRatio)){
                alert("办公与服务设施用地比例必须是数字！");
                return false;
            }else if(officeRatio!=""){
                if(officeRatio<=0){
                    alert("请填写正确的办公与服务设施用地比例！");
                    return false;
                }
                if(officeRatio>100){
                    alert("办公与服务设施用地比例最大为100%！");
                    return false;
                }
            }

            if(isNaN(minJzXg)){
                alert("最小建筑限高必须是数字！");
                return false;
            }

            if(isNaN(maxJzXg)){
                alert("最大建筑限高必须是数字！");
                return false;
            }

            if(parseInt(minJzXg)>parseInt(maxJzXg)){
                alert("最小建筑限高不能大于最大建筑限高");
                return false;
            }

            if(isNaN(minLhl)){
                alert("最小绿化率必须是数字！");
                return false;
            }else if(minLhl!=""){
                if(minLhl<=0){
                    alert("最小绿化率必须大于零！");
                    return false;
                }
            }


            if(isNaN(maxLhl)){
                alert("最大绿化率必须是数字！");
                return false;
            }else if(maxLhl!=""){
                if(maxLhl<=0){
                    alert("最大绿化率必须大于零！");
                    return false;
                }
            }

            if(parseInt(minLhl)>parseInt(maxLhl)){
                alert("最小绿化率不能大于最大绿化率");
                return false;
            }

            if(isNaN(minJzMd)){
                alert("最小建筑密度必须是数字！");
                return false;
            }else if(minJzMd!=""){
                if(minJzMd<=0){
                    alert("最小建筑密度必须大于零！");
                    return false;
                }
            }

            if(isNaN(maxJzMd)){
                alert("最大建筑密度必须是数字！");
                return false;
            }else if(maxJzMd!=""){
                if(maxJzMd<=0){
                    alert("最大建筑密度必须大于零！");
                    return false;
                }
            }

            if(parseInt(minJzMd)>parseInt(maxJzMd)){
                alert("最小建筑密度不能大于最大建筑密度");
                return false;
            }

            if(isNaN(minRjl)){
                alert("最小容积率必须是数字！");
                return false;
            }else if(minRjl!=""){
                if(minRjl<=0){
                    alert("最小容积率必须大于零！");
                    return false;
                }
            }

            if(isNaN(maxRjl)){
                alert("最大容积率必须是数字！");
                return false;
            }else if(maxRjl!=""){
                if(maxRjl<=0){
                    alert("最大容积率必须大于零！");
                    return false;
                }
            }

            if(parseInt(minRjl)>parseInt(maxRjl)){
                alert("最小建容积率不能大于最大容积率");
                return false;
            }

            try{
               /* $("input[name='sonYearCount']").each(function(){
                    var sonYearCount=$(this).val();
                    if(isNaN(sonYearCount)){
                        alert("交易地块多规划用途信息的年限必须是数字！");
                        throw new Error();
                    }
                });*/
            }catch(e){
                return false;
            }


            try{
               /* $("input[name='sonYearCount']").each(function(){
                    var sonYearCount=$(this).val();
                    if(sonYearCount<=0 && sonYearCount!=""){
                        alert("交易地块多规划用途信息的年限必须是大于零的正数！");
                        throw new Error();
                    }
                });*/
            }catch (e){
                return false;
            }

            try{
                $("input[name='zdArea']").each(function(){
                    var zdArea=$(this).val();
                    if(isNaN(zdArea)){
                        alert("交易地块多规划用途信息的面积必须是数字！");
                        throw new Error();
                    }
                });
            }catch (e){
                return false;
            }

            try{
                $("input[name='zdArea']").each(function(){
                    var zdArea=$(this).val();
                    if(zdArea<=0 && zdArea!=""){
                        alert("交易地块多规划用途信息的面积必须是大于零的正数！");
                        throw Error();
                    }
                });
            }catch (e){
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
        //增加多用途地块

        function addResourceSon(){
            ++zdFlag;
            $("table:nth-child(2)").append("<tr>"+
            " <td width='180' align='center'><input type='button' class='btn btn-primary' name='resourceSonDel'  onclick='delResourceSon(this)' value='删除' ></td> "+
            " <td width='180' align='center'><input type='text' id='zdCode"+zdFlag+"'"+" name='zdCode' class='input-text' maxlength='30' value='${transResourceSon.zdCode!}' ></td>"+
            " <td width='180' align='center'><input type='text' id='sonLandUseMuli"+zdFlag+"'"+" name='sonLandUseMuli' class='input-text' value='${transResourceSon.sonLandUseMuli!}' ></td>"+
            "<td width='180' align='center'><input type='text'id='sonYearCount"+zdFlag+"'"+" name='sonYearCount' class='input-text' maxlength='12' value='${transResourceSon.sonYearCount!}' ></td>"+
            "<td width='180' align='center'><input type='text'id='zdArea"+zdFlag+"'"+"  name='zdArea' class='input-text' maxlength='12' value='${transResourceSon.zdArea!}' ></td>"+
            "</tr>");

            /*$(document).ready(function(){
                delResourceSon();
            });*/

        }

        //删除多用途地块
        function delResourceSon(obj){
            if(confirm("是否确认删除数据？")){
                $(obj).parent().parent().remove();
            }
            --zdFlag;


        }


        function importResource(){
            var _resourceCode=$("input[name='resourceCode']").val();
            if(_resourceCode==null||_resourceCode==''){
                alert('请填写地块编号！');
                return;
            }
            if(confirm('确认导入地块信息吗？')){
                $.post('${base}/console/houseLand/importHouseLand.f',{resourceCode:_resourceCode},function(data){
                    if(data!=null&&data!=""){
                        var dataObjArr=eval("("+data+")");
                        if(dataObjArr.length>0){
                            alert('操作成功！');
                            $("#btnImport").hide();
                            for(var i=0;i<dataObjArr.length;i++){
                                var dataObj=dataObjArr[i];
                                if(i==0){

                                    if(dataObj.addOffer!=""&&dataObj.addOffer>0)
                                        $("input[name='addOffer']").val(dataObj.addOffer);
                                    if(dataObj.beginOffer!=""&&dataObj.beginOffer>0)
                                        $("input[name='beginOffer']").val(dataObj.beginOffer);

                                    if(dataObj.offerUnit!="")
                                        $("input[name='offerUnit']").val(dataObj.offerUnit);
                                    if(dataObj.landUse!="")
                                        $("input[name='landUse']").val(dataObj.landUse);
                                    if(dataObj.fixedOffer!="")
                                        $("input[name='fixedOffer']").val(dataObj.fixedOffer);
                                    if(dataObj.bmBeginTime!="")
                                        $("input[name='fixedOfferHkd']").val(dataObj.fixedOfferHkd);
                                    if(dataObj.bmBeginTime!="")
                                        $("input[name='fixedOfferUsd']").val(dataObj.fixedOfferUsd);
                                    if(dataObj.crArea!="")
                                        $("input[name='crArea']").val(dataObj.crArea);
                                    if(dataObj.buildingArea!="")
                                        $("input[name='buildingArea']").val(dataObj.buildingArea);
                                    if(dataObj.bgconArea!="")
                                        $("input[name='bgconArea']").val(dataObj.bgconArea);
                                    if(dataObj.zzconArea!="")
                                        $("input[name='zzconArea']").val(dataObj.zzconArea);
                                    if(dataObj.shconArea!="")
                                        $("input[name='shconArea']").val(dataObj.shconArea);
                                    if(dataObj.dealType!="")
                                        $("input[name='dealType']").val(dataObj.dealType);
                                    var bDate;
                                    if(dataObj.gpBeginTime!=""){
                                        bDate=new XDate(dataObj.gpBeginTime);
                                        $("input[name='gpBeginTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                        beginDateChange()
                                    }
                                    if(dataObj.gpEndTime!=""){
                                        bDate=new XDate(dataObj.gpEndTime);
                                        $("input[name='gpEndTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                    }
                                    if(dataObj.xsBeginTime!=""){
                                        bDate=new XDate(dataObj.xsBeginTime);
                                        $("input[name='xsBeginTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                    }
                                    if(dataObj.bmBeginTime!=""){
                                        bDate=new XDate(dataObj.bmBeginTime);
                                        $("input[name='bmBeginTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                    }
                                    if(dataObj.bmEndTime!=""){
                                        bDate=new XDate(dataObj.bmEndTime);
                                        $("input[name='bmEndTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                    }
                                    if(dataObj.bzjBeginTime!=""){
                                        bDate=new XDate(dataObj.bzjBeginTime);
                                        $("input[name='bzjBeginTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                    }
                                    if(dataObj.bzjEndTime!=""){
                                        bDate=new XDate(dataObj.bzjEndTime);
                                        $("input[name='bzjEndTime']").val(bDate.toString("yyyy-MM-dd HH:mm:ss"));
                                    }
                                    if(dataObj.ZDCode!="")
                                    $("table:nth-child(2) tr:nth-child(3) input[name='zdCode']").val(dataObj.ZDCode);
                                    if(dataObj.landUse!="")
                                        $("table:nth-child(2) tr:nth-child(3) input[name='sonLandUse']").val(dataObj.landUse);
                                    if(dataObj.yearCount!="")
                                        $("table:nth-child(2) tr:nth-child(3) input[name='sonYearCount']").val(dataObj.yearCount);

                                    /*
                                    * 多块宗地时这些二义性的属性不导入
                                    * */
                                    if(dataObjArr.length==1){
                                        if(dataObj.resourceLocation!="")
                                            $("input[name='resourceLocation']").val(dataObj.resourceLocation);

                                        if(dataObj.cdPz!="")
                                            $("input[name='cdPz']").val(dataObj.cdPz);
                                        if(dataObj.constructContent!="")
                                            $("textarea[name='constructContent']").val(dataObj.constructContent);
                                        if(dataObj.deliverTerm!="")
                                            $("textarea[name='deliverTerm']").val(dataObj.deliverTerm);
                                        if(dataObj.description!="")
                                            $("textarea[name='description']").val(dataObj.description);


                                        if(dataObj.investmentIntensity!="")
                                            $("input[name='investmentIntensity']").val(dataObj.investmentIntensity);
                                        if(dataObj.jcSs!="")
                                            $("input[name='jcSs']").val(dataObj.jcSs);

                                        if(dataObj.layoutOutline!="")
                                            $("input[name='layoutOutline']").val(dataObj.layoutOutline);
                                        if(dataObj.maxJzMd!="")
                                            if(dataObj.maxJzMd.substring(0,dataObj.maxJzMd.length-1)>0)
                                                $("input[name='maxJzMd']").val(dataObj.maxJzMd.substring(0,dataObj.maxJzMd.length-1)/100);
                                        if(dataObj.maxJzMdTag!="")
                                            $("input[name='maxJzMdTag']").val(dataObj.maxJzMdTag);
                                        if(dataObj.maxJzXgTag!="")
                                            $("input[name='maxJzXgTag']").val(dataObj.maxJzXgTag);
                                        if(dataObj.maxJzxg!=""&&dataObj.maxJzxg>0)
                                            $("input[name='maxJzxg']").val(dataObj.maxJzxg);
                                        if(dataObj.maxLhl!="")
                                            $("input[name='maxLhl']").val(dataObj.maxLhl);
                                        if(dataObj.maxLhlTag!="")
                                            $("input[name='maxLhlTag']").val(dataObj.maxLhlTag);
                                        if(dataObj.maxRjl!="")
                                            $("input[name='maxRjl']").val(dataObj.maxRjl);
                                        if(dataObj.memo!="")
                                            $("textarea[name='memo']").val(dataObj.memo);
                                        if(dataObj.minJzMd!="")
                                            if(dataObj.minJzMd.substring(0,dataObj.minJzMd.length-1)>0)
                                                $("input[name='minJzMd']").val(dataObj.minJzMd.substring(0,dataObj.minJzMd.length-1)/100);
                                        if(dataObj.minJzMdTag!="")
                                            $("input[name='minJzMdTag']").val(dataObj.minJzMdTag);
                                        if(dataObj.minJzXgTag!="")
                                            $("input[name='minJzXgTag']").val(dataObj.minJzXgTag);
                                        if(dataObj.minJzxg!=""&&dataObj.minJzxg>0)
                                            $("input[name='minJzxg']").val(dataObj.minJzxg);
                                        if(dataObj.minLhl!="")
                                            $("input[name='minLhl']").val(dataObj.minLhl);
                                        if(dataObj.minLhlTag!="")
                                            $("input[name='minLhlTag']").val(dataObj.minLhlTag);
                                        if(dataObj.minOffer!="")
                                            $("input[name='minOffer']").val(dataObj.minOffer);
                                        if(dataObj.minRjl!="")
                                            $("input[name='minRjl']").val(dataObj.minRjl);

                                        if(dataObj.officeRatio!="")
                                            $("input[name='officeRatio']").val(dataObj.officeRatio);
                                        /*if(dataObj.regionCode!="")
                                            $("input[name='regionCode']").val(dataObj.regionCode);*/
                                        if(dataObj.resourceCode!="")
                                            $("input[name='resourceCode']").val(dataObj.resourceCode);

                                        if(dataObj.selrj2!="")
                                            $("input[name='selrj2']").val(dataObj.selrj2);
                                        if(dataObj.selrjl!="")
                                            $("input[name='selrjl']").val(dataObj.selrjl);

                                        if(dataObj.surroundings!="")
                                            $("input[name='surroundings']").val(dataObj.surroundings);
                                        if(dataObj.tdTjXx!="")
                                            $("input[name='tdTjXx']").val(dataObj.tdTjXx);
                                        if(dataObj.yearCount!="")
                                            $("input[name='yearCount']").val(dataObj.yearCount);

                                    }

                                }else{
                                    addResourceSon();
                                    if(dataObj.crArea!=""){
                                        if($("input[name='crArea']").val()==""){
                                            $("input[name='crArea']").val(eval($("input[name='crArea']").val()));
                                        }else{
                                            $("input[name='crArea']").val((eval(dataObj.crArea)+eval($("input[name='crArea']").val())).toFixed(2));
                                        }
                                    }
                                    if(dataObj.buildingArea!=""){
                                        if($("input[name='buildingArea']").val()==""){
                                            $("input[name='buildingArea']").val(eval($("input[name='buildingArea']").val()));
                                        }else{
                                            $("input[name='buildingArea']").val(eval(dataObj.buildingArea)+eval($("input[name='buildingArea']").val()));
                                        }
                                    }
                                    if(dataObj.shconArea!=""){
                                        if($("input[name='shconArea']").val()==""){
                                            $("input[name='shconArea']").val(eval($("input[name='shconArea']").val()));
                                        }else{
                                            $("input[name='shconArea']").val(eval(dataObj.shconArea)+eval($("input[name='shconArea']").val()));
                                        }
                                    }
                                    if(dataObj.zzconArea!=""){
                                        if($("input[name='zzconArea']").val()==""){
                                            $("input[name='zzconArea']").val(eval($("input[name='zzconArea']").val()));
                                        }else{
                                            $("input[name='zzconArea']").val(eval(dataObj.zzconArea)+eval($("input[name='zzconArea']").val()));
                                        }
                                    }


                                    if(dataObj.bgconArea!=""&&dataObj.bgconArea>0){
                                        if($("input[name='bgconArea']").val()==""){
                                            $("input[name='bgconArea']").val(eval($("input[name='bgconArea']").val()));
                                        }else{
                                            $("input[name='bgconArea']").val(eval(dataObj.bgconArea)+eval($("input[name='bgconArea']").val()));
                                        }
                                    }
                                    if(dataObj.ZDCode!="")
                                        $("table:nth-child(2) tr").last().find("input[name='zdCode']").val(dataObj.ZDCode);
                                    if(dataObj.landUse!="")
                                        $("table:nth-child(2) tr").last().find("input[name='sonLandUse']").val(dataObj.landUse);
                                    if(dataObj.yearCount!="")
                                        $("table:nth-child(2) tr").last().find("input[name='sonYearCount']").val(dataObj.yearCount);


                                }


                            }

                        }else{
                            alert("查无此地块编号的数据！")
                        }

                    }else{
                        alert('操作失败！');
                    }

                });
            }
        }

        //点击地块用途菜单执行事件
        function onClick(e, treeId, treeNode){
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            var nodes = zTree.getSelectedNodes();
            if(nodes.length>1){
                alert("请选择单一的用途！");
                return false;
            }
            var landUseMuliObj = $("#landUseMuli");
            landUseMuliObj.find("option").attr("value",nodes[0].id);
            landUseMuliObj.find("option").html(nodes[0].name)

        }
        //用途菜单展示
        function showMenu(obj){
            var menuContent = $(obj).parent().parent().parent().parent().find("div")[1].id
            $("#"+menuContent).slideDown("fast");
            $("body").bind("mousedown",onBodyDown);
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
        <td width="180"><span style="color: red;">*</span><label class="control-label">地块编号：</label></td>
        <td><input type="text" class="input-text" style="background-color: #FFFFCC;width:200px" name="resourceCode" value="${transResource.resourceCode!}" maxlength="32">
            <button id="btnImport" type="button" class="btn btn-primary" onclick="importResource()">导入</button>

        </td>
        <td width="180"><span style="color: red;">*</span><label class="control-label">行政区部门：</label></td>
        <td width="300"><@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=0 SelectValue=transResource.regionCode onChange="regionChanged"/></td>
    </tr>
    <tr>
        <td><span style="color: red;">*</span><label class="control-label">地块座落：</label></td>
        <td colspan="3"><input type="text" style="background-color: #FFFFCC" class="input-text" name="resourceLocation" value="${transResource.resourceLocation!}" maxlength="100"></td>
    </tr>

    <tr>
        <td><span style="color: red;">*</span><label class="control-label">竞价单位：</label></td>
        <td>
            <span class="select-box">
                <select onChange="unitChange()" name="offerUnit" class="select" >
                    <option value="0" <#if transResource.offerUnit?? && transResource.offerUnit==0 >selected="selected"</#if>>万元（总价）</option>
                    <#--<option value="1" <#if transResource.offerUnit?? && transResource.offerUnit==1 >selected="selected"</#if>>元/平方米（单价）</option>
                    <option value="2" <#if transResource.offerUnit?? && transResource.offerUnit==2 >selected="selected"</#if>>万元/亩（单价）</option>-->
                </select>
            </span>
        </td>
        <td><span style="color: red;">*</span><label class="control-label">规划用途：</label></td>

        <td width="300">
           <#assign landUseDictList = ResourceUtil.getLandUseDictList() />
            <@LandUseMuliSelect selectName="landUseMuli" selectValue=transResource.landUseMuli! selectValueDicts=landUseDictList />
        </td>
        <#if transResource.landUse??>
            <input name="landUse" type="hidden" value="0">
        </#if>
    </tr>


    <tr>
        <td><span style="color: red;">*</span><label class="control-label">保证金（万元）：</label></td>
        <td><input type="text" name="fixedOffer" class="input-text"  style="background-color: #FFFFCC" value="${transResource.fixedOffer!}" maxlength="12"></td>
        <td><label class="control-label">保证金（万美元）：</label></td>
        <td><input type="text" name="fixedOfferUsd" class="input-text" value="${transResource.fixedOfferUsd!}" maxlength="12"></td>
    </tr>
    <tr>
        <td><label class="control-label">保证金（万港币）：</label></td>
        <td><input type="text" name="fixedOfferHkd" class="input-text" value="${transResource.fixedOfferHkd!}" maxlength="12"></td>
        <td><span style="color: red;">*</span><label class="control-label">起始价<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
        <td><input style="background-color: #FFFFCC" type="text" name="beginOffer" class="input-text"  value="${transResource.beginOffer!}" maxlength="12"></td>

    </tr>
        <tr>

            <td><span style="color: red;">*</span><label class="control-label">增价幅度<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
            <td><input  style="background-color: #FFFFCC" type="text" name="addOffer" class="input-text" value="${transResource.addOffer!}" maxlength="12"></td>
            <td><span style="color: red;">*</span><label class="control-label">出让面积（平方米）：</label></td>
            <td><input type="text" name="crArea" style="background-color: #FFFFCC" class="input-text" value="${transResource.crArea!}" maxlength="12"></td>

        </tr>
        <tr>
            <td><label class="control-label">出让方式：</label></td>
            <td>
                <span class="select-box">
                <select name="dealType" class="select" disabled="disabled" >
                    <option value="00" <#if transResource.dealType?? && transResource.dealType=='00' >selected="selected"</#if>>出让</option>
                </select>
            </span>
            </td>
            <#--<td width="210"><label class="control-label">代征土地面积（平方米）：</label></td>
            <td><input type="text" id="daizhArea" name="daizhArea" class="input-text"  value="${transResourceInfo.daizhArea!}"></td>-->
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



    <tr>
        <td><span style="color: red;">*</span><label class="control-label">挂牌开始时间：</label></td>
        <td><input type="text" id="gpBeginTime" name="gpBeginTime" class="input-text Wdate" onchange="beginDateChange()"  value="<#if transResource.gpBeginTime??>${transResource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',maxDate:'#F{$dp.$D(\'gpEndTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
        <td><span style="color: red;">*</span><label class="control-label">挂牌截止时间：</label></td>
        <td><input type="text" id="gpEndTime" name="gpEndTime" class="input-text Wdate" value="${transResource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 10:00:00',minDate:'#F{$dp.$D(\'gpBeginTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
    </tr>
    <tr>
        <td><span style="color: red;">*</span><label class="control-label">报名开始时间：</label></td>
        <td><input type="text" id="bmBeginTime" name="bmBeginTime" class="input-text Wdate"  value="<#if transResource.bmBeginTime??>${transResource.bmBeginTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',maxDate:'#F{$dp.$D(\'bmEndTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
        <td><span style="color: red;">*</span><label class="control-label">报名截止时间：</label></td>
        <td><input type="text" id="bmEndTime" name="bmEndTime" class="input-text Wdate" value="<#if transResource.bmEndTime??>${transResource.bmEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 16:00:00',minDate:'#F{$dp.$D(\'bmBeginTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
    </tr>
    <tr>
        <td><span style="color: red;">*</span><label class="control-label">保证金交纳开始时间：</label></td>
        <td><input type="text" id="bzjBeginTime" name="bzjBeginTime" class="input-text Wdate"  value="<#if transResource.bzjBeginTime??>${transResource.bzjBeginTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 09:00:00',maxDate:'#F{$dp.$D(\'bmEndTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
        <td><span style="color: red;">*</span><label class="control-label">保证金交纳截止时间：</label></td>
        <td><input type="text" id="bzjEndTime" name="bzjEndTime" class="input-text Wdate" value="${transResource.bzjEndTime?string("yyyy-MM-dd HH:mm:ss")}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 16:00:00',minDate:'#F{$dp.$D(\'bmBeginTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
    </tr>
        <tr>
            <td><#--<label class="control-label">首次报价截止时间：</label>--></td>
            <td><input  type="hidden" id="yxEndTime" name="yxEndTime" class="input-text Wdate" value="<#if transResource.yxEndTime??>${transResource.yxEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if> " onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 15:00:00',minDate:'#F{$dp.$D(\'gpEndTime\')}',alwaysUseStartDate:true})" ></td>
            <td><span style="color: red;">*</span><label class="control-label">限时报价开始时间：</label></td>
            <td><input type="text" id="xsBeginTime" name="xsBeginTime" class="input-text Wdate" value="<#if transResource.xsBeginTime??>${transResource.xsBeginTime?string("yyyy-MM-dd HH:mm:ss")}</#if> " onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 10:00:00',minDate:'#F{$dp.$D(\'gpEndTime\')}',alwaysUseStartDate:true})" readonly="readonly"></td>
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
            <button type="button"  class="btn btn-primary" onclick="addResourceSon()">新增</button>
        </span>
        </td>
    </tr>
    <tr>
        <td width="" align="center"><label class="control-label">#</label></td>
        <td width="250" align="center"><label class="control-label">宗地编号</label></td>
        <td width="250" align="center"><label class="control-label">规划用途</label></td>
        <td width="200" align="center"><label class="control-label">年限</label></td>
        <td width="200" align="center"><label class="control-label">面积(平方米)</label></td>
    </tr>
    <#if resourceSonList?? && (resourceSonList?size>0)>
        <#list resourceSonList as resourceSon>
            <tr>
                <td width="180" align="center">
                    <input type="button" class="btn btn-primary" name="resourceSonDel" onclick="delResourceSon(this)" value="删除">
                </td>
                <td width="180"  align="center"><input type="text" id="zdCode1" name="zdCode" class="input-text"  maxlength='30' value="${resourceSon.zdCode!}" ></td>
                <td width="180"  align="center"><input type="text" id="sonLandUseMuli1" name="sonLandUseMuli" class="input-text"   value="${resourceSon.sonLandUseMuli!}" ></td>
                <td width="180"  align="center"><input id="sonYearCount1" type="text" name="sonYearCount" class="input-text" maxlength="12" value="${resourceSon.sonYearCount!}" ></td>
                <td width="180" align="center"><input id="zdArea1" type="text" name="zdArea" class="input-text" maxlength="12" value="${resourceSon.zdArea!}" ></td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td width="180" align="center"><input type="button" class="btn btn-primary" name="resourceSonDel" onclick="delResourceSon(this)" value="删除"></td>
            <td width="180"  align="center"><input type="text" id="zdCode1"  name="zdCode" class="input-text"  maxlength="30" value="${transResourceSon.zdCode!}" ></td>
            <td width="180"  align="center"><input type="text" id="sonLandUseMuli1"  name="sonLandUseMuli" class="input-text"  value="${transResourceSon.sonLandUseMuli!}" ></td>
            <td width="180" align="center"><input id="sonYearCount1" type="text" name="sonYearCount" class="input-text" maxlength="12" value="${transResourceSon.sonYearCount!}" ></td>
            <td width="180" align="center"><input id="zdArea1" type="text" name="zdArea" class="input-text" maxlength="12" value="${transResourceSon.zdArea!}" ></td>
        </tr>
    </#if>


</table>


    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700" >其他地块规划信息</td>
        </tr>


        <tr>
            <td width="210"><label class="control-label">建筑面积（平方米）：</label></td>
            <td><input type="text" name="buildingArea" class="input-text"  value="${transResourceInfo.buildingArea!}" maxlength="12"></td>
            <td width="210"><label class="control-label">商业建筑面积（平方米）：</label></td>
            <td><input type="text" name="shconArea" class="input-text" value="${transResourceInfo.shconArea!}" maxlength="12"></td>

        </tr>
        <tr>
            <td width="210"><label class="control-label">住宅建筑面积（平方米）：</label></td>
            <td><input type="text" name="zzconArea" class="input-text"  value="${transResourceInfo.zzconArea!}" maxlength="12"></td>
            <td width="210"><label class="control-label">办公建筑面积（平方米）：</label></td>
            <td><input type="text" name="bgconArea" class="input-text" value="${transResourceInfo.bgconArea!}" maxlength="12"></td>
        </tr>
        <tr>
            <td width="210"><label class="control-label">出让年限（年）：</label></td>
            <td><input type="text" name="yearCount" class="input-text" value="${transResourceInfo.yearCount!}" maxlength="12"></td>
            <td width="210"><label class="control-label">约定土地交易条件 ：</label></td>
            <td><input type="text" name="tdTjXx" class="input-text"  value="${transResourceInfo.tdTjXx!}" maxlength="64"></td>

        </tr>
        <tr>
            <td width="210"><label class="control-label">场地平整 ：</label></td>
            <td><input type="text" name="cdPz" class="input-text" value="${transResourceInfo.cdPz!}" maxlength="64"></td>
            <td width="210"><label class="control-label">基础设施 ：</label></td>
            <td ><input type="text" name="jcSs" class="input-text"  value="${transResourceInfo.jcSs!}" maxlength="64"></td>

        </tr>




       <#-- <tr>
            <td><label class="control-label">容积率：</label></td>
            <td><input type="text" name="plotRatio" class="input-text"  value="${transResourceInfo.plotRatio!}"></td>
            <td><label class="control-label">绿化率：</label></td>
            <td><input type="text" name="greeningRate" class="input-text" value="${transResourceInfo.greeningRate!}"></td>
        </tr>
        <tr>
            <td><label class="control-label">建筑密度：</label></td>
            <td><input type="text" name="buildingDensity" class="input-text"  value="${transResourceInfo.buildingDensity!}"></td>
            <td><label class="control-label">建筑限高（米）</label></td>
            <td><input type="text" name="buildingHeight" class="input-text" value="${transResourceInfo.buildingHeight!}"></td>
        </tr>-->
        <tr>
            <td><label class="control-label">办公与服务设施用地比例（%）：</label></td>
            <td><input type="text" name="officeRatio" class="input-text"  value="${transResourceInfo.officeRatio!}" maxlength="12"></td>
            <td><label class="control-label">投资强度（万元/亩）：</label></td>
            <td><input type="text" name="investmentIntensity" class="input-text" value="${transResourceInfo.investmentIntensity!}" maxlength="32"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="text" name="minRjl" class="input-text"  style="width: 23%" value="${transResourceInfo.minRjl!}" maxlength="12">
                <span class="select-box" style="width: 15%">
                    <select name="selrj2" class="select" >
                        <option value="00" <#if transResourceInfo.selrj2?? && transResourceInfo.selrj2=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.selrj2?? && transResourceInfo.selrj2=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>

                <label class="control-label">容积率&nbsp;&nbsp;：</label>

                <span class="select-box" style="width: 15%;margin-left:18px">
                    <select name="selrjl" class="select" >
                        <option value="00" <#if transResourceInfo.selrjl?? && transResourceInfo.selrjl=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.selrjl?? && transResourceInfo.selrjl=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" name="maxRjl" class="input-text"   style="width:23%" value="${transResourceInfo.maxRjl!}" maxlength="12">
            </td>

            <td colspan="2">
                <input type="text" name="minJzMd" class="input-text" value="${transResourceInfo.minJzMd!}" maxlength="12" style="width: 23%">
                <span class="select-box" style="width: 15%">
                    <select name="maxJzMdTag" class="select" >
                        <option value="00" <#if transResourceInfo.maxJzMdTag?? && transResourceInfo.maxJzMdTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.maxJzMdTag?? && transResourceInfo.maxJzMdTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                 </span>


                <label class="control-label">建筑密度 (%)：</label>

                <span class="select-box" style="width: 15%">
                    <select name="minJzMdTag" class="select" >
                        <option value="00" <#if transResourceInfo.minJzMdTag?? && transResourceInfo.minJzMdTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.minJzMdTag?? && transResourceInfo.minJzMdTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" name="maxJzMd" class="input-text"  value="${transResourceInfo.maxJzMd!}" maxlength="12" style="width: 23%">

            </td>
        </tr>

        <tr>
            <td colspan="2">
                <input type="text" name="minLhl" class="input-text" value="${transResourceInfo.minLhl!}" maxlength="12" style="width: 23%">
                <span class="select-box" style="width: 15%">
                    <select name="maxLhlTag" class="select" >
                        <option value="00" <#if transResourceInfo.maxLhlTag?? && transResourceInfo.maxLhlTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.maxLhlTag?? && transResourceInfo.maxLhlTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>

                <label class="control-label">绿化率(%) ：</label>

                <span class="select-box" style="width: 15%">
                    <select name="minLhlTag" class="select" >
                        <option value="00" <#if transResourceInfo.minLhlTag?? && transResourceInfo.minLhlTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.minLhlTag?? && transResourceInfo.minLhlTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" name="maxLhl" class="input-text"  value="${transResourceInfo.maxLhl!}" maxlength="12" style="width: 23%">
            </td>
            <td colspan="2">
                <input type="text" id="zxjzxg" name="minJzxg" class="input-text" value="${transResourceInfo.minJzxg!}" maxlength="12" style="width: 23%">
                <span class="select-box" style="width: 15%">
                    <select name="maxJzXgTag" class="select" >
                        <option value="00" <#if transResourceInfo.maxJzXgTag?? && transResourceInfo.maxJzXgTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.maxJzXgTag?? && transResourceInfo.maxJzXgTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>

                <label class="control-label">建筑限高(m) ：</label>

                <span class="select-box" style="width: 15%">
                    <select name="minJzXgTag" class="select" >
                        <option value="00" <#if transResourceInfo.minJzXgTag?? && transResourceInfo.minJzXgTag=='00' >selected="selected"</#if>>&lt;</option>
                        <option value="01" <#if transResourceInfo.minJzXgTag?? && transResourceInfo.minJzXgTag=='01' >selected="selected"</#if>>&le;</option>
                    </select>
                </span>
                <input type="text" id="zdjzxg" name="maxJzxg" class="input-text"  value="${transResourceInfo.maxJzxg!}" maxlength="12" style="width: 23%">
            </td>
        </tr>
        <tr>
            <td><label class="control-label">建设内容</label></td>
            <td colspan="3">
                <textarea name="constructContent" style="width: 100%" rows="5" maxlength="255">${transResourceInfo.constructContent!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">土地交付条件</label></td>
            <td colspan="3">
                <textarea name="deliverTerm" style="width: 100%" rows="5" maxlength="255">${transResourceInfo.deliverTerm!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">规划要点</label></td>
            <td colspan="3">
                <textarea name="layoutOutline" style="width: 100%" rows="5" maxlength="255">${transResourceInfo.layoutOutline!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">地块介绍</label></td>
            <td colspan="3">
                <textarea name="description" style="width: 100%" rows="5" maxlength="255">${transResourceInfo.description!}</textarea>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">周边环境</label></td>
            <td colspan="3">
                <textarea name="surroundings" style="width: 100%" rows="5" maxlength="255">${transResourceInfo.surroundings!}</textarea>
            </td>
        </tr>

        <tr>
            <td><label class="control-label">备注</label></td>
            <td colspan="3">
                <textarea name="memo" style="width: 100%" rows="5" maxlength="255">${transResourceInfo.memo!}</textarea>
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
                <span style="color: red;">注：只允许上传jpg等图片文件，其大小不超过10M</span>
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
    <input type="hidden" name="dkCode" value="${transResource.dkCode!}">
    <input type="hidden" name="resourceId" value="${transResource.resourceId!}">
    <input type="hidden" name="ggId" value="${transResource.ggId!}">
    <input type="hidden" name="infoId" value="${transResourceInfo.infoId!}">
    <div class="row-fluid">
        <div class="span10 offset2">
           <#-- <button type="button" class="btn btn-primary" onclick="return checkForm()">导入</button>-->
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/resource/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/ztree.3.5/js/ztree.min.js"></script>
<script type="text/javascript" src="${base}/static/js/landUseMuliDict.js"></script>

</body>
</html>