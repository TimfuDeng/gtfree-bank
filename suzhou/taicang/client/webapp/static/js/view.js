$(document).ready(function () {
    //开始长连接,接受报价
    if($("#resourceStatus").val()==1 || $("#resourceStatus").val()==10){
        var resourceEditStatus = $('#resourceEditStatus').val();
        if(resourceEditStatus && resourceEditStatus == '3'){

        } else {
            beginAcceptOffer();

            var timer_interval = setInterval(listenResourceEditStatus, 1000);
        }
    }

    $(window).on("scroll",$backToTopFun);$backToTopFun();

    $.Huitab("#resourceDescTab .tabBar span","#resourceDescTab .tabCon","current","click","0");

    gtmapMap.initializeMap();
    try{
        $('#showInfodialog').modal({
            keyboard:false,
            backdrop: true,
            width:875
        });
    }catch(ex){}

    showToolTip();
});

var listenResourceEditStatus = function(){
    $.ajax({
        type: 'POST',
        url: './view/resource_edit_status.f',
        data: {
            id:$("#resourceId").val()
        },
        success: function(editStatus){
            if(editStatus == 3){
                window.location.reload();
            }
        }
    })
};

function beginAcceptOffer(){

    var resourceId=$("#resourceId").val();
    //超时时间1.2分钟
    $.ajax({
        type: "POST",
        url: "./getoffer.f",
        data: {id:$("#resourceId").val(), time:$("#maxOfferTime").val()},
        cache:false,
        timeout: 90*1000,
        success: function(data){
            if (data=="refresh"){
                window.location.reload();
            }else if(data!=null&&data!=''){
                var result = eval("(" + data + ")");
                if(result.stop && result.stop == '1'){
                    window.location.reload();
                } else {
                    $("#maxOfferTime").val(result.time);
                    acceptNewOffer(result);
                    beginAcceptOffer();
                }
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
    if (resultObj.result) {
        var maxOffer;
        //将领先的标志去掉
        if (resultObj.result.length>0){
            $("span.label-max").each(function(){
                $(this).removeClass("label-danger");
                $(this).removeClass("label-max");
                $(this).removeClass("label");
                //$(this).addClass("label-default");
                $(this).html("---");
            });
        }
        if($(".table-price tbody tr").length>14){
            for (var i =0; i<resultObj.result.length;  i++) {
                $(".table-price tbody tr:last-child").remove();
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

function beginOffer() {
    var htmlObj={};
    htmlObj.offer = $("#txtoffer").val();
    //检查报价是否合法
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
    if (!topOffer()) {
        htmlObj.offerChinese = Chinese(htmlObj.offer*10000);
        laytpl(document.getElementById('tpl_beginoffer').innerHTML).render(htmlObj, function (html) {
            $("#modal_content").html(html);
        });
    }else{
        htmlObj.housePrice = htmlObj.offer;
        htmlObj.offer=$("#maxOfferPrice").val();
        htmlObj.offerChinese = Chinese(htmlObj.offer*10000);
        laytpl(document.getElementById('tpl_beginoffer_top').innerHTML).render(htmlObj, function (html) {
            $("#modal_content").html(html);
        });
    }
    $('#myModal').modal({
        backdrop: false
    });

    /*try {
        var stopPrice = $('#stopPrice').val() * 1.0;
        if (htmlObj.offer * 1.0 > stopPrice) {
            $('span[sign="stop_message"]').show();
        } else {
            $('span[sign="stop_message"]').hide();
        }
    } catch (e) {
    }*/
}

function postOffer() {
    var resourceId=$("#resourceId").val();
    var offerType=0;
    if ($("#maxOfferType") && $("#maxOfferType").val()) {
        offerType = $("#maxOfferType").val();
    }
    var params ={
        id:$("#resourceId").val(),
        offer:$("#local_offer_value").val(),
        type:offerType
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

    try {
        _czc.push(["_trackEvent", '某某开发商', '报价', '地块编号：'+$('[sign="resource_code_value"]').text()+';价格：' + $("#local_offer_value").val(), '报价按钮', 'baojia']);
    } catch (e) {
    }

    $.ajax({
        type: "POST",
        url: "./offer.f",
        data: params,
        cache:false,
        success: function(data){
            if("true"==data){
                showError(true,"接受报价成功！");
            }else{
                showError(false,data);
            }
        },
        complete : function(XMLHttpRequest,status){
            if(status=='timeout'){//超时,status还有success,error等值的情况
                showError(false,"接受报价超时！");
            }else if (status=='error'){
                showError(false,"接受报价错误！");
            }
        }
    });
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
    globalCount = 3;
    //亲，时间到了要刷新页面了
    $('#myModalRefresh').modal({
        keyboard:false,
        backdrop: true
    });
    setTimeout("redirect()", 1000);
}

function _OneMinuteEvent(value){
    if (value>0) {
        showOneMinuteInfoBar();
        $(".progress-bar").attr("style", "width:" + Math.round(value * 100 / 60000) + "%;");
    }else{
        hideOneMinuteInfoBar();
    }
}

function hideOneMinuteInfoBar(){
    $("#oneminuteInfo").css('display', 'none');
    $("#oneminuteProgress").css('display', 'none');
}

function showOneMinuteInfoBar(){
    $("#oneminuteInfo").css('display', 'block');
    $("#oneminuteProgress").css('display', 'block');
}

var globalCount = 3;
function redirect(){
    globalCount--;
    if (globalCount>1) {
        setTimeout("redirect()", 1000);
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

function showToolTip(){
    $('#priceInfo').mouseover(function () {
        var pos=$(this).offset();
        var pos_x=eval(pos.left);
        var pos_y=eval(pos.top)+30;
        pos_x=pos_x.toString();
        pos_x=pos_x+'px';
        pos_y=pos_y+'px';
        var tooltipobj={};
        tooltipobj.over=Math.round(($('#priceInfo').html()*1.0-$("#offer0").val()*1.0)*100/$("#offer0").val()*1.0);
        tooltipobj.price1=Math.round($('#priceInfo').html()*10000/$("#crArea").val()*1.0);
        tooltipobj.price2=Math.round(tooltipobj.price1*666.67/10000);
        laytpl(document.getElementById('tpl_tooltip').innerHTML).render(tooltipobj, function (html) {
            $('body').append(html);
            $('.tooltip').css("top",pos_y);
            $('.tooltip').css("left",pos_x);
        });

    }).mouseout(function () {
        //$('#relation .f-left').bind('mouseout',RemoveTooltip());
        //$('#relation .f-left').unbind('mouseout',function(){});
        //$('.tooltip').unbind('mouseout',RemoveTooltip());
        $('.tooltip').remove();
    });
}