var _dayLength=24*60*60*1000;
var _hourLength=60*60*1000;
var _minLength=60*1000;
function showDateTime(diffvalue){
    if (diffvalue>0) {
        var days = (diffvalue) / _dayLength;
        var hours = ((diffvalue) % _dayLength) / _hourLength;
        var min = (((diffvalue) % _dayLength) % _hourLength) / _minLength;
        var sec = (((diffvalue) % _dayLength) % _hourLength) % _minLength / 1000;
        if (days > 1)
            return buildTimeStr(days) + "<em>天</em>" + buildTimeStr(hours) + "<em>时</em>" + buildTimeStr(min) + "<em>分</em>" + buildTimeStr(sec) + "<em>秒</em>";
        else if (hours > 1)
            return "<var>00</var><em>天</em>" +buildTimeStr(hours) + "<em>时</em>" + buildTimeStr(min) + "<em>分</em>" + buildTimeStr(sec) + "<em>秒</em>";
        else if (min > 1)
            return "<var>00</var><em>天</em><var>00</var><em>时</em>"+buildTimeStr(min) + "<em>分</em>" + buildTimeStr(sec) + "<em>秒</em>";
        else if (sec > 0)
            return "<var>00</var><em>天</em><var>00</var><em>时</em><var>00</var><em>分</em>" + buildTimeStr(sec) + "<em>秒</em>";
    }else{
        return "<var>00</var><em>天</em><var>00</var><em>时</em><var>00</var><em>分</em><var>00</var><em>秒</em>";
    }

}

function buildTimeStr(value){
    value=Math.floor(value*1.0);
    if (value>9){
        return "<var>" + value + "</var>";
    }else{
        return "<var>0" + value + "</var>";
    }
}

function formateTime(dateTime){
    var serverTimeObj= new XDate(dateTime*1.0);
    return  serverTimeObj.toString("yyyy年MM月dd日 HH:mm:ss");
}