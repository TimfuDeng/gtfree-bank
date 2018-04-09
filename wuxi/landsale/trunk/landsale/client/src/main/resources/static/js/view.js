$(document).ready(function () {
    changeMaxOffer();
    //开始长连接,接受报价
    if($("#resourceEditStatus").val()==2 && ($("#resourceStatus").val()==1 || $("#resourceStatus").val()==10)){
        beginAcceptOffer();
    }

    //$(window).on("scroll",$backToTopFun);$backToTopFun();

    //$.Huitab("#resourceDescTab .tabBar span","#resourceDescTab .tabCon","current","click","0");

    //gtmapMap.initializeMap();
    /*try{
        $('#showInfodialog').modal({
            keyboard:false,
            backdrop: true,
            width:875
        });
    }catch(ex){}
*/
    //showToolTip();
});

function changeMaxOffer() {
    var maxOffer = parseInt($("#maxOffer").val());
    var addOffer = parseInt($("#addOffer").val());
    if (maxOffer != null && maxOffer != '' && maxOffer != 'undefined') {
        maxOffer = parseInt(maxOffer);
        addOffer = parseInt(addOffer);
        var newOffer=parseFloat((maxOffer*1 + addOffer*1).toFixed(6));
        $("#txtoffer").val(newOffer);
    }
}

function beginAcceptOffer(){

    var resourceId=$("#resourceId").val();
    //超时时间1.2分钟
    $.ajax({
        type: "POST",
        url: "./getoffer",
        data: {id:$("#resourceId").val(), time:$("#maxOfferTime").val()},
        cache:false,
        timeout: 90*1000,
        success: function(data){
            if (data=="refresh"){
                window.location.reload();
            }else if(data!=null&&data!=''){
                if ("string" == typeof data) {
                    data = JSON.parse(data);
                }
                $("#maxOfferTime").val(data.time);
                acceptNewOffer(data);
                if (!isEmpty(data.result) && data.result[0].maxOffer) {
                    window.location.reload();
                }
                beginAcceptOffer();
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
        /*if (resultObj.result.length>0){
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
        var gettpl1 =document.getElementById('tpl_offer1').innerHTML;*/
        getOfferHistory();
        var cuserId=$("#userId").attr("value");
        for (var i = resultObj.result.length-1; i>-1; i--) {
            resultObj.result[i].cUser=cuserId;
            if (i==0) {
                maxOffer=resultObj.result[0];  //最高报价
                /*laytpl(gettpl0).render(resultObj.result[i], function(html){
                    $(".table-price tbody").prepend(html);
                });*/
            }else {
                /*laytpl(gettpl1).render(resultObj.result[i], function(html){
                    $(".table-price tbody").prepend(html);
                });*/
            }
        }
        if (maxOffer) {
            $("#maxOffer").val(maxOffer.offerPrice*1);
            var newOffer=parseFloat((maxOffer.offerPrice*1 + $("#addOffer").val()*1).toFixed(6));
            var topOffer = parseFloat($("#topOffer").val()*1);
            if(topOffer>0&&(maxOffer.offerType==0 || maxOffer.offerType == 1)&&(maxOffer.offerPrice*1==topOffer || newOffer>topOffer)){
                window.location.reload();
            }
            $("#txtoffer").val(newOffer);
            /*if(maxOffer.offerType==2){
                $("#priceHouseInfo").html(maxOffer.offerPrice);
            }else {*/
                $("#priceInfo").html(maxOffer.offerPrice);
            //}
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
    var htmlObj={};
    currOffer=$("#txtoffer").val();
    htmlObj.offer = $("#txtoffer").val();
    //检查报价是否合法
    if(!htmlObj.offer.match(/^(:?(:?\d+.\d+)|(:?\d+))$/)){
        showError(false,"报价不是数字，请重新输入！");
        return false;
    }
    var acceptOffer=getAcceptOffer();
    acceptOffer=acceptOffer.toFixed(6);
    if(htmlObj.offer*1.0<acceptOffer*1.0){
        showError(false,"报价必须高于最高价，增价幅度必须是"+$("#addOffer").val()+"的整数倍！");
        return false;
    }
    var addValue=(htmlObj.offer*1.0-acceptOffer*1.0)/($("#addOffer").val() *1.0);
    addValue=parseFloat(addValue.toFixed(6));
    if (!IsNum(addValue+"")){
        showError(false,"报价增价幅度必须是"+$("#addOffer").val()+"的整数倍！");
        return false;
    }

    if (!topOffer()) {
        htmlObj.offerChinese = Chinese(htmlObj.offer*1);
        laytpl(document.getElementById('tpl_beginoffer').innerHTML).render(htmlObj, function (html) {
            $("#modal_content").html(html);
        });
    } else {
        showError(false,"已达到最高限价,请等待限时竞价开始！");
        return false;
       /* htmlObj.housePrice = htmlObj.offer;
        htmlObj.offer=$("#maxOfferPrice").val();
        htmlObj.offerChinese = Chinese(htmlObj.offer*10000);
        laytpl(document.getElementById('tpl_beginoffer_top').innerHTML).render(htmlObj, function (html) {
            $("#modal_content").html(html);
        });*/
    }
    $('#myModal').modal({
        backdrop: false
    });
}

function postOffer() {
    var resourceId=$("#resourceId").val();
    var offerType=0;
    if ($("#maxOfferType") && $("#maxOfferType").val()) {
        offerType = $("#maxOfferType").val();
    }
    if($("#txtoffer").val()*1.0>currOffer*1.0){
        //showError(false,"您当前报价"+currOffer+"已小于最新报价"+$("#txtoffer").val()+"请重新报价！");
        showError(false,"您当前报价："+currOffer+"万元，已小于最新报价："+$("#txtoffer").val()+"万元，请重新报价！");
        return false;
    }
    var params ={
        resourceId:$("#resourceId").val(),
        offer:$("#txtoffer").val(),
        userId:$("#userId").val(),
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
    $.ajax({
        type: "POST",
        url: "./offer",
        data: params,
        cache:false,
        success: function(data){
            if(data.flag){
                showError(true,"接受报价成功！");
            }else{
                showError(false, data.message);
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
    $(".icon-remove").click(function () {
        $("#showinfo").fadeOut(2000);
    });

}


function _TimeOutEvent(spanObj){
    $("#refreshValue").html("3");
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

function redirect(){
    var value=$("#refreshValue").text()*1;
    value=value-1;
    if (value>1) {
        $("#refreshValue").html(value);
        setTimeout("redirect()", 1000);
    }else{
        window.location.reload();
    }
}

function addOffer(){
    var value=$("#txtoffer").val()*1 + $("#addOffer").val()*1;
    value=parseFloat(value.toFixed(6));
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
        value=parseFloat(value.toFixed(6));
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
    num=num.toFixed(6)*1;
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