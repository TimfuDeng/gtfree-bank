var _serverTimeObj;
var _timeIntervalFun;
var _serverTimeIntervalFun;

/**
 * 获取服务端时间
 * @param
 */
function setServerTime(){
    _serverTimeObj= new XDate(_serverTime*1.0);
    WriteToPage();
    _timeIntervalFun=setInterval(WriteToPage,1000);
    _serverTimeIntervalFun=setInterval(getServerTime,1000*60);  //每隔60秒从服务器取个时间
}

function getServerTime(){
    $.ajax({
        type: "GET",
        url: _serverTimeUrl,
        cache:false,
        success: function(data){
            _serverTime= data*1.0;
            _serverTimeObj= new XDate(_serverTime*1.0);
        },
        complete : function(XMLHttpRequest,status){
            if(status=='timeout'){//超时,status还有success,error等值的情况

            }else if (status=='error'){

            }
        }
    });
}
function WriteToPage(){
    //alert(formateTime(_serverTime));
    _serverTime=_serverTime+1000;
    _serverTimeObj=_serverTimeObj.addSeconds(1);
    $("#serverTimeDiv").html(formateTime(_serverTimeObj));
    //$(htmlObj).html("");
    try{
        _OneSecondEvent();
    }catch(ex){}
}

function formateTime(dateTime){
    //return _serverTime.getFullYear()+"-" +_serverTime.getMonth() + "-" +_serverTime.getDate()
    //    + " " + _serverTime.getHours() + ":" +  _serverTime.getMinutes() + ":" + _serverTime.getSeconds();
    return  _serverTimeObj.toString("yyyy年MM月dd日 HH:mm:ss");
}
