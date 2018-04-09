var _dayLength=24*60*60*1000;
var _hourLength=60*60*1000;
var _minLength=60*1000;
var _functionObj=new Array();

var __sto = setInterval;
window.setInterval = function(callback,timeout,param){
    var args = Array.prototype.slice.call(arguments,2);
    var _cb = function(){
        callback.apply(null,args);
    }
    __sto(_cb,timeout);
}

$(document).ready(function () {
    $("span.time").each(function(){
        if ($(this).attr("value")) {
            var timevalue = $(this).attr("value");
            var idvalue = $(this).attr("id");
            var spanTimeObj = {id: idvalue, time: timevalue, disable: false};
            _functionObj.push(spanTimeObj);
        }
    });
    _OneSecondEvent();
});

function _OneSecondEvent(){
    for(var i=0;i<_functionObj.length;i++){
        if (!_functionObj[i].disable){
            var diffTime=_functionObj[i].time-_serverTime;
            $("span[id='"+_functionObj[i].id+"']").html(setTimeString(_functionObj[i].time));
            if ((diffTime)<1000*60*60 && (diffTime)>(1000*60*59+1000*58)) {
                //window.location.reload();    //考虑挂牌截止前1小时的的问题
                try{
                    _TimeOutEvent($("span[id='"+_functionObj[i].id+"']"));  //1小时事件
                    _functionObj[i].disable=true;
                }catch(ex){}
            }
            if ((diffTime)<(60*1000)) {
                try{
                    _OneMinuteEvent(diffTime);  //1分钟到计时事件
                }catch(ex){}
            }

            if ((diffTime)<0) {
                try {
                    _TimeOutEvent($("span[id='"+_functionObj[i].id+"']"));  //除非时间到期
                    _functionObj[i].disable=true;
                } catch (ex) {}
            }
        }
    }
}

function resetTime(spanId){
    for(var i=0;i<_functionObj.length;i++){
        if(_functionObj[i].id=="span_" + spanId){
            _functionObj[i].time=$("#span_" + spanId).attr("value");
            _functionObj[i].disable=false;
        }
    }
}


function setTimeString(timevalue){
    //alert(timevalue-_serverTime);
    var days=(timevalue-_serverTime)/_dayLength;
    var hours=((timevalue-_serverTime)% _dayLength)/_hourLength;
    var min=(((timevalue-_serverTime)% _dayLength) % _hourLength)/_minLength;
    var sec=(((timevalue-_serverTime)% _dayLength) % _hourLength)%_minLength /1000;
    if (days>1)
        return  buildTimeStr(days) + "<em>天</em>" +buildTimeStr(hours) + "<em>时</em>" +  buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(sec) + "<em>秒</em>";
    else if(hours>1)
        return buildTimeStr(hours) + "<em>时</em>" +  buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(sec) + "<em>秒</em>";
    else if(min>1)
        return buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(sec) + "<em>秒</em>";
    else if(sec>0)
        return buildTimeStr(sec) + "<em>秒</em>";
}

function buildTimeStr(value){
    value=Math.floor(value*1.0);
    if (value>9){
        return "<var>" + value + "</var>";
    }else{
        return "<var>0" + value + "</var>";
    }
}

function submitValue(name,value){
    var inputObj=$("input[name='"+name+"']");
    if(inputObj){
        inputObj.val(value);
    }
    document.forms[0].submit();
}

