$(document).ready(function () {
    //开始长连接,接受报价
    /*if($("#resourceStatus").val()==1 || $("#resourceStatus").val()==10){
        beginAcceptOffer();
    }*/

    $(window).on("scroll",$backToTopFun);$backToTopFun();

    $.Huitab("#resourceDescTab .tabBar span","#resourceDescTab .tabCon","current","click","0");

    try{
        $('#showInfodialog').modal({
            keyboard:false,
            backdrop: true,
            width:875
        });
    }catch(ex){}

   /* showToolTip();*/
});

//弹出申请一次报价弹出框
function applyConfirm(){
    $("#modal_content_apply").html("确认参加一次报价！");
    $('#applyModal').modal({
        backdrop: false
    });
}

//参加一次报价
function applyOnePrice(targetId,transUserId){
    var params ={
        targetId:targetId,
        transUserId:transUserId
    };
    $.ajax({
        type: "POST",
        url: "./view-agree/edit",
        data: params,
        cache: false,
        success: function(date){
            if("true"==date){
                window.location.reload();
            }else{
                showError(false,date);
            }
        },
        complete: function(XMLHttpRequest,status){
            if(status=='timeout'){//timeout,error等情况
                showError(false,"接受申请超时！");
            }else if(status=='error'){
                showError(false,"j接受申请错误！");
            }

        }
    });
    return false;
}




//点击报价按钮时，报价人的报价
var onePrice="";//出价
function beginOffer() {
    var htmlObj={};
    if(validateOnePrice()){
        htmlObj.offer = onePrice.toFixed(0);
        //检查报价是否合法
        if(!htmlObj.offer.match(/^(:?(:?\d+.\d+)|(:?\d+))$/)){
            showError(false,"报价不是数字，请重新输入！");
            return false;
        }
        htmlObj.offerChinese = Chinese(htmlObj.offer*1);
        laytpl(document.getElementById('tpl_beginoffer').innerHTML).render(htmlObj, function (html) {
            $("#modal_content").html(html);
        });
    }else{
        $("#modal_content").html("<span style='font-size: 26px'>前后出价不一致，请重新出价！</span>");
    }
    $('#myModal').modal({
        backdrop: false
    });
}

function validateOnePrice(){
    var onePriceC="";//确认出价
    onePrice=10000000000*$("#baiYi").val()+1000000000*$("#shiYi").val()+100000000*$("#yi").val()+10000000*$("#qianWan").val()
    +1000000*$("#baiWan").val()+100000*$("#shiWan").val()+10000*$("#wan").val()+1000*$("#qian").val()+100*$("#bai").val()
    +10*$("#shi").val()+1*$("#yuan").val();
    onePriceC=10000000000*$("#baiYiValid").val()+1000000000*$("#shiYiValid").val()+100000000*$("#yiValid").val()+10000000*$("#qianWanValid").val()
    +1000000*$("#baiWanValid").val()+100000*$("#shiWanValid").val()+10000*$("#wanValid").val()+1000*$("#qianValid").val()+100*$("#baiValid").val()
    +10*$("#shiValid").val()+1*$("#yuanValid").val();
    if(onePrice==onePriceC){
        return true;
    }
    return false;
}

function postOffer() {
    $("input[name='price']").val($.trim($("#spanOffer").html()));
    if($("input[name='price']").val()==""){
        showError(false,"前后出价不一致，请重新出价！");
        return false
    }
    var caEnabled = 'false';//$('#caEnabled').val();
    if(caEnabled!=null&&caEnabled=='true'){
        var key= gtmapCA.getCertificateThumbprint().replace(/\D/g,"");
        if(key.length>3){
            key=key.substring(key.length-3)+"1";
        }else{
            key=key+"1";
        }
        $("input[name='price']").val( $("input[name='price']").val()*1*key);
    }

    var params ={
        id: $("input[name='oneTargetId']").val(),
        offer:$("input[name='price']").val(),
        applyId:$("input[name='applyId']").val()
    };

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
        params.offer="";
    }
    $.ajax({
        type: "POST",
        url: "./view-oneprice",
        data: params,
        cache:false,
        success: function(data){
            if("true"==data){
                //showError(true,"接受报价成功！");
                window.location.reload();
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
    num=num.toFixed(0)*1;
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

        $('.tooltip').remove();
    });
}