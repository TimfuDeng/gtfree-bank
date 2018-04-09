$(document).ready(function () {
    //开始长连接,接受报价|| $("#resourceStatus").val()==10
    if($("#resourceEditStatus").val()==2 ){
        beginAcceptOffer();
    }

    $(window).on("scroll",$backToTopFun);$backToTopFun();

    $.Huitab("#resourceDescTab .tabBar span","#resourceDescTab .tabCon","current","click","0");

    //gtmapMap.initializeMap();
    try{
        $('#showInfodialog').modal({
            keyboard:false,
            backdrop: true,
            width:875
        });
    }catch(ex){}

    //计算溢价和平均地价
    applyCountPriceOver();

});



function beginAcceptOffer(){

    var resourceId=$("#resourceId").val();
    //超时时间1.2分钟
    $.ajax({
        type: "POST",
        url: "./getoffer.f",
        data: {id:resourceId, time:$("#maxOfferTime").val()},
        cache:false,
        timeout: 90*1000,
        success: function(data){
            if (data=="refresh"){
                window.location.reload();
            }else if(data!=null&&data!=''){
                var result = eval("(" + data + ")");
                $("#maxOfferTime").val(result.time);
                acceptNewOffer(result);
                beginAcceptOffer();
                //计算溢价和平均地价
                applyCountPriceOver();
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status!=0){
                $('#myModalError').modal({
                    keyboard:false,
                    backdrop: true
                });
            }
        },
        complete : function(XMLHttpRequest,status){
            if(status=='timeout'){//超时,status还有success,error等值的情况
                //beginAcceptOffer();
                //alert(status + "-timeout");
            }else if (status=='error'){
                //alert(status + "-error");
            }
        }
    });
}

function acceptNewOffer(resultObj){
    var resourceId = $("#resourceId").val();
    $.ajax({
        url:"view/offer/getMaxOfferCount",
        type:"post",
        data:{resourceId:resourceId},
        success:function(data){
            //当前报价次数
            $("#offerCount").attr("value",data);
        },error:function(){
            alert("error");
        }
    });
    var maxOfferCount = $("#offerCount").val();
    if (resultObj.result) {
        var maxOffer;
        //将领先的标志去掉
        if (resultObj.result.length>0){
            $("span.label-max").each(function(){
                $(this).removeClass("label-default");
                $(this).removeClass("label-danger");
                $(this).removeClass("label-max");
                $(this).removeClass("label");
                $(this).html("第"+maxOfferCount+"轮");
                //$(this).addClass("label-default");

            });
           /* $("span.label-my-max").each(function(){
                $(this).removeClass("label-danger");
                $(this).removeClass("label-my-max");
                $(this).addClass("label-default");

            });*/

        }
        if($(".table-price tbody tr").length>14){
            for (var i =0; i<resultObj.result.length;  i++) {
                $(".table-price tbody tr:last").remove();
            }
        }
        var gettpl0 =document.getElementById('tpl_offer0').innerHTML;
        var gettpl1 =document.getElementById('tpl_offer1').innerHTML;
        var cuserId=$("#aUser").attr("value");
        for (var i = resultObj.result.length-1; i>-1; i--) {
            resultObj.result[i].cUser=cuserId;
            if (i==0) {
                maxOffer=resultObj.result[0];  //最高报价
                laytpl(gettpl0).render(resultObj.result[i], function(html){
                    $(".table-price tbody").prepend(html);
                });
            }else {
                laytpl(gettpl1).render(resultObj.result[i], function(html){
                    $(".table-price tbody").prepend(html);
                });
            }
        }
        if (maxOffer) {
            $("#maxOffer").val(maxOffer.offerPrice*1);
            var newOffer=parseFloat((maxOffer.offerPrice*1 + $("#addOffer").val()*1).toFixed(4));
            var topOffer = parseFloat($("#topOffer").val()*1);
            if(topOffer>0&&(maxOffer.offerType==0 || maxOffer.offerType == 1)&&(maxOffer.offerPrice*1==topOffer || newOffer>topOffer)){
                window.location.reload();
            }
            $("#txtoffer").val(newOffer);
            if(maxOffer.offerType==2){
                $("#priceHouseInfo").html(maxOffer.offerPrice);
            }else {
                $("#priceInfo").html(maxOffer.offerPrice);
            }
            if ("1"==$("#resourceStatus").val()){
                $("#span_" + $("#resourceId").val()).attr("value",maxOffer.offerTime*1+1000*60*4);
                resetTime($("#resourceId").val());  //重新启动定时器
                hideOneMinuteInfoBar();
            }
        }
    }
}

function getAcceptOffer(){
    var maxOffer=$("#maxOffer").val();
    if (maxOffer==0){
        return $("#beginOffer").val()*1;
    }else{
        return maxOffer*1+$("#addOffer").val()*1;
    }
}

//点击报价按钮时，报价人的报价
var currOffer;
function beginOffer() {
    $("#modal_number").empty();
    var cuserId=$("#aUser").attr("value");
    var htmlObj={};
    currOffer=$("#txtoffer").val();
    htmlObj.offer = $("#txtoffer").val();

    //检查报价是否合法
    checkInputFileter();
    if($("#txtoffer").val()==""){
        showError(false,"报价必须填写！");
        return false;
    }
    if(!htmlObj.offer.match(/^(:?(:?\d+.\d+)|(:?\d+))$/)){
        showError(false,"报价不是数字，请重新输入！");
        return false;
    }
    var acceptOffer=getAcceptOffer();
    acceptOffer=acceptOffer.toFixed(4);
    if(htmlObj.offer*1.0<acceptOffer*1.0){
        showError(false,"报价必须高于最高价，增价幅度必须是"+$("#addOffer").val()+"的整数倍！");
        return false;
    }
    var addValue=(htmlObj.offer*1.0-acceptOffer*1.0)/($("#addOffer").val() *1.0);
    addValue=parseFloat(addValue.toFixed(4));
    if (!IsNum(addValue+"")){
        showError(false,"报价增价幅度必须是"+$("#addOffer").val()+"的整数倍！");
        return false;
    }
    //增加幅度不能大于5倍
    if(addValue>5){
        showError(false,"报价增价幅度不能超过5倍！");
        return false;
    }
    if (!topOffer()) {
        htmlObj.offerChinese = Chinese(htmlObj.offer*10000);
       //最高报价或者当增幅大于5倍，在报价时提示1-最高价，2-增幅大于5
        htmlObj.offerNoteType=0;
        if(parseInt(addValue)>=5){
            htmlObj.offerNoteType=2;
        }else if($(".table-price tbody tr:first td:nth-child(2) span").text()=="我的报价"){
            htmlObj.offerNoteType=1;
        }
        if($("#applyNumber:has(em)").length>0){//如果领取号牌则将报价信息加入弹出框
            laytpl(document.getElementById('tpl_beginoffer').innerHTML).render(htmlObj, function (html) {
                $("#modal_content").html(html);
            });
        }

    }else{
        htmlObj.housePrice = htmlObj.offer;
        htmlObj.offer=$("#maxOfferPrice").val();
        htmlObj.offerChinese = Chinese(htmlObj.offer*10000);
        laytpl(document.getElementById('tpl_beginoffer_top').innerHTML).render(htmlObj, function (html) {
            $("#modal_content").html(html);
        });
    }
    var resourceId=$("#resourceId").val();
    var currentDate = new Date();
    var loadUrl = "view/selectApplyNumber.f?id="+resourceId+"&t="+currentDate.getTime();
    $("#modal_number").load(loadUrl,function(){
        if($("#numberTable").length>0)
            $(".modal.fade.in").css('top','30%');
        else
            $(".modal.fade.in").css('top','50%');

    });
    $('#myModal').modal({
        backdrop: false
    });


}


function postOffer() {//先领号牌，再报价。

        var resourceId=$("#resourceId").val();
        var offerType=0;
        if ($("#maxOfferType") && $("#maxOfferType").val()) {
            offerType = $("#maxOfferType").val();
        }
        var selectNumber;
        var selectNumbers = $('#numberTable a[class="label label-primary label-number label-danger"]');
        if($('#numberTable a[class="label label-primary label-number choose-apply-number label-danger"]').length>0){
            alert("号牌已被领取，请重新选择号牌！")
            return;
        }

        if(selectNumbers.length>0){
            selectNumber= $(selectNumbers[0]).text();
        }

        if($("#applyNumber:has(em)").length==0&&selectNumbers.length==0){
            alert("请选择号牌！")
            return;
        }
        /*if($("#applyNumber:has(em)").length==0&&!confirm("您当前的报价为："+currOffer+"万元，您已选取："+selectNumber+"号牌，是否确认报价？")){
            return false;
        }*/


        if($("#txtoffer").val()*1.0>currOffer*1.0){
            alert("您当前报价："+currOffer+"万元，已小于最新报价："+$("#txtoffer").val()+"万元，请重新报价！");
            return false;
        }



            if(selectNumber!=undefined){
                var paramsNumber ={
                    id:resourceId,
                    applyNumber:selectNumber
                };
                $.ajax({
                    type: "POST",
                    url: "./selectNumber.f",
                    data: paramsNumber,
                    cache:false,
                    success: function(data){
                        if("true"==data){
                            if($("#applyNumber:has(em)").length==0){
                                $("#applyNumber").load("view/applyNumber.f?id="+resourceId);
                                //领取号牌，并刷新页面
                                window.location.reload(true);
                            }
                        }else{
                            if('OfferErrorChange'==data){
                                window.location.reload(true);
                            }
                            showError(false,data);
                        }
                    },
                    complete : function(XMLHttpRequest,status){
                        if(status=='timeout'){//超时,status还有success,error等值的情况
                            window.location.reload();
                            showError(false,"领取号牌超时！");

                        }else if (status=='error'){
                            window.location.reload();
                            showError(false,"领取号牌错误！");

                        }
                    }
                });
            }else{
                var params ={
                    id:resourceId,
                    offer:$("#local_offer_value").val(),
                    type:offerType,
                    applyNumber:selectNumber
                };
                var caEnabled = $('#caEnabled').val();
                if(caEnabled!=null&&caEnabled=='true'){
                    var signResult = gtmapCA.signContent(JSON.stringify(params),1);
                    if(signResult!=true){
                        alert(gtmapCA.getErrorString(signResult));
                        return;
                    }
                    params.sxinput = gtmapCA.getSxInput();
                    params.pkcs1 = gtmapCA.getSxSignature();
                    params.sxcertificate = gtmapCA.getSxCertificate();
                    params.sxaction = "PKCS1";
                    params.sxdigest = "MD5";
                }
                $.ajax({
                    type: "POST",
                    url: "./offer.f",
                    data: params,
                    cache:false,
                    success: function(data){
                        if("true"==data){
                            showError(true,"接受报价成功！");
                            if($("#applyNumber:has(em)").length==0){
                                $("#applyNumber").load("view/applyNumber.f?id="+resourceId);
                                //领取号牌，并刷新页面
                                window.location.reload(true);
                            }
                        }else{
                            if('OfferErrorChange'==data){
                                window.location.reload(true);
                            }
                            showError(false,data);

                        }
                    },
                    complete : function(XMLHttpRequest,status){
                        if(status=='timeout'){//超时,status还有success,error等值的情况
                            window.location.reload();
                            showError(false,"接受报价超时！");

                        }else if (status=='error'){
                            window.location.reload();
                            showError(false,"接受报价错误！");

                        }
                    }
                });
            }







}

function topOffer(){
    if($("#maxOfferType") && $("#maxOfferType").val()=="2"){
        return true;
    }
    return false;
}

function showError(infoType,info){
    $('#myModal').modal('hide');
    var html="";
    if(infoType){
        html=('<div class="Huialert Huialert-success info-style" ><i class="icon-remove"></i>'+info+'</div>');
    }else{
        html=('<div class="Huialert Huialert-danger info-style"  ><i class="icon-remove"></i>'+info+'</div>');
    }
    $("#showinfo").html(html);
    $("#showinfo").fadeIn(2000,function(){
        setTimeout(function(){
            $("#showinfo").fadeOut(2000);
        },5000);
    });
}


function _TimeOutEvent(spanObj){
    //$("#refreshValue").html("3");
    $("#refreshValue").html("数据处理中，请稍后...");
    //亲，时间到了要刷新页面了
    $('#myModalRefresh').modal({
        keyboard:false,
        backdrop: true
    });
    setTimeout("redirect()", 3000);
}

function _OneMinuteEvent(value){
    if (value>0) {
        showOneMinuteInfoBar();
        $(".progress-bar").attr("style", "width:" + Math.round(value * 100 / 60000) + "%;");
    }else{
        hideOneMinuteInfoBar();
    }
}

function _4MinuteEvent(value){
    if (value>0)
        $("#limitOffer").attr("style","color: #d91615;");
}

function hideOneMinuteInfoBar(){
    $("#oneminuteInfo").css('display', 'none');
    $("#oneminuteProgress").css('display', 'none');
}

function showOneMinuteInfoBar(){
    $("#oneminuteInfo").css('display', 'block');
    $("#oneminuteProgress").css('display', 'block');
}

function redirect(){
    var value=$("#refreshValue").text()*1;
    value=value-1;
    if (value>1) {
        //$("#refreshValue").html(value);
        $("#refreshValue").html("数据处理中，请稍后...");
        setTimeout("redirect()", 3000);
    }else{
        window.location.reload();
    }
}

function addOffer(){
    var value=$("#txtoffer").val()*1 + $("#addOffer").val()*1;
    value=parseFloat(value.toFixed(5));
    var topOffer=$("#topOffer").val()*1;
    if(topOffer>0 && value>topOffer){
        showError(false,"已达最高限价！");
        return;
    }
    $("#txtoffer").val(value);
}

function cutOffer(){
    var acceptOffer=getAcceptOffer();
    if(($("#txtoffer").val()*1 - $("#addOffer").val()*1)>=acceptOffer*1) {
        var value=$("#txtoffer").val() * 1 - $("#addOffer").val() * 1;
        value=parseFloat(value.toFixed(5));
        $("#txtoffer").val(value);
    }
}

function confirmLimit(resourceId){
    $.ajax({
        type: "POST",
        url: "./offerLimit.f",
        data: {id:$("#resourceId").val()},
        cache:false,
        success: function(data){
            window.location.reload();
        },
        complete : function(XMLHttpRequest,status){
            window.location.reload();
        }
    });
}


function Chinese(num) {
    if (!/^\d*(\.\d*)?$/.test(num)) {
        //alert("你输入的不是数字，请重新输入!");
        return false;
    }
    num=num.toFixed(5)*1;
    var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
    var BB = new Array("", "拾", "佰", "仟", "万", "亿", "点", "");
    var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = "";
    for (var i = a[0].length - 1; i >= 0; i--) {
        switch (k) {
            case 0 :
                re = BB[7] + re;
                break;
            case 4 :
                if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0]))
                    re = BB[4] + re;
                break;
            case 8 :
                re = BB[5] + re;
                BB[7] = BB[5];
                k = 0;
                break;
        }
        if (k % 4 == 2 && a[0].charAt(i) == "0" && a[0].charAt(i + 2) != "0") re = AA[0] + re;
        if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re;
        k++;
    }
    if (a.length > 1) {
        re += BB[6];
        for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)];
    }
    return re;
}

function IsNum(s) {
    if(s!=null){
        var r,re;
        re = /\d*/i; //\d表示数字,*表示匹配多个数字
        r = s.match(re);
        return (r==s)?true:false;
    }
    return false;
}


function chooseApplyNumber(e){

    var clickTarget = e;
    $('#numberTable a').each(function(){
        $(this).removeClass("label-danger");
    });
    $(clickTarget).addClass("label-danger");
}
//计算溢价和平均地价
function applyCountPriceOver(){

    var tooltipobj={};
    tooltipobj.over=Math.round(($('#priceInfo').html()*1.0-$("#offer0").val()*1.0)*100/$("#offer0").val()*1.0);
    tooltipobj.price=Math.round($('#priceInfo').html()*10000/$("#crArea").val()*1.0);
    var maxRjl = $("#maxRjl").val();
    if(maxRjl!=0 && maxRjl!=""){
        tooltipobj.buildingPrice =Math.round(tooltipobj.price/maxRjl);
        //参考楼面价
        $('#buildingPrice').html(tooltipobj.buildingPrice);
    }
    //溢价
    $('#applyNumberOver').html(tooltipobj.over);
    //平均地价
    $('#applyNumberPrice').html(tooltipobj.price);
}

//检查地块
function checkResource(obj){
    var objUrl=obj;
    if(objUrl!=undefined){
        var resourceId=$("#resourceId").val();
        $.ajax({
            type: "POST",
            url: "/client/console/apply/resourceCheck",
            data: {id:resourceId},
            cache:false,
            success: function(data){

                if (data==false){
                    //alert("")
                    confirm("当前地块已经被中止！");
                    return false;
                }else{
                    window.location.href=objUrl;
                    return true;
                }
            }

        });
    }

}
function checkResourceApplyFail(obj){
    checkResource(obj);
}
function checkResourceApplyBank(obj){
    checkResource(obj);
}
function checkResourceForceCancle(obj){
    checkResource(obj);
}

function checkResourceApplyBzj(obj){
    checkResource(obj);
}

function checkResourceApply(obj){
    checkResource(obj);
}