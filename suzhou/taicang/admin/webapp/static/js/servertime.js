var _serverTime;
var _timeIntervalFun;
var _serverTimeIntervalFun;
/**
 * 获取服务端时间
 * @param htmlObj
 */
function setServerTime(url,htmlObj){
    if (_serverTimeIntervalFun) clearInterval(_serverTimeIntervalFun);
    _serverTimeIntervalFun=setInterval(setServerTime,1000*60,url,htmlObj);  //每隔60秒从服务器取个时间
    $.ajax({
        type: "GET",
        url: url,
        cache:false,
        success: function(data){
            _serverTime= new XDate(data*1.0);
            WriteToPage(htmlObj);
            if (_timeIntervalFun) clearInterval(_timeIntervalFun);
            _timeIntervalFun=setInterval(WriteToPage,1000,htmlObj);
        },
        complete : function(XMLHttpRequest,status){
            if(status=='timeout'){//超时,status还有success,error等值的情况

            }else if (status=='error'){

            }
        }
    });
}

function WriteToPage(htmlObj){
    //alert(formateTime(_serverTime));
    _serverTime=_serverTime.addSeconds(1);
    //alert(formateTime(_serverTime));
    $(htmlObj).html(formateTime(_serverTime));
}

function formateTime(dateTime){
    //return _serverTime.getFullYear()+"-" +_serverTime.getMonth() + "-" +_serverTime.getDate()
    //    + " " + _serverTime.getHours() + ":" +  _serverTime.getMinutes() + ":" + _serverTime.getSeconds();
    return  _serverTime.toString("yyyy年MM月dd日 HH:mm:ss");
}
