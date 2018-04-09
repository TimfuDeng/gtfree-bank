var _dayLength=24*60*60*1000;
var _hourLength=60*60*1000;
var _minLength=60*1000;
var _serverTime;
var _formQuery=new formQuery();

$(document).ready(function () {
    //获取服务器时间
    var beginGetDate=(new XDate()).valueOf()*1.0;
    $.ajax({
        type: "GET",
        url: "./time",
        cache:false,
        success: function(data){
            var endGetDate=(new XDate()).valueOf()*1.0;
            _serverTime= data*1.0+endGetDate-beginGetDate;
            $("#serverTimeSpan").html(formateTime(_serverTime));
        },
        complete : function(XMLHttpRequest,status){
            if(status=='timeout'){//超时,status还有success,error等值的情况

            }else if (status=='error'){

            }
        }
    });
    setInterval(function(){
        _serverTime=_serverTime+1000;
        $("#serverTimeSpan").html(formateTime(_serverTime));
    },1000);

    //获取地块列表
    _formQuery.init(_formQuery);
    BeginQueryResourceList(_formQuery.getQueryData());

});

function BeginQueryResourceList(param){
    $.ajax({
        type: "GET",
        url: "./list",
        cache:false,
        data:param,
        success: function(result){
            var data=result.data;
            if (data) {
                var gettpl = $("#resourceInfo").html();
                laytpl(gettpl).render(data, function (html) {
                    $("#resourceList").html(html);
                    timeDownAndUp();
                });
            }
            updatePageInfo(result);
        }
    });
}

function timeDownAndUp(){
    timeUp();
    setInterval(timeUp,1000);
    timeDown();
    setInterval(timeDown,1000);
}
function timeUp(){
    $(".timeUp").each(function() {
        var value=$(this).attr("value")*1.0;
        $(this).html(setTimeString(_serverTime-value));
    });
}

function timeDown() {
    $(".spanTimeDown").each(function () {
        var value = $(this).attr("value") * 1.0;
        $(this).html(setTimeString(value - _serverTime));
    });
}

function updatePageInfo(data){
    var cPage=data.condition.page+1;
    var pageCount=data.pageCount;
    if (pageCount==0) pageCount=1;

    $("#pageCurrent").html(cPage);
    $("#pageCount").html(pageCount);
    _formQuery.setPageIndex(data.condition.page);
    _formQuery.setPageCount(pageCount);

    if (cPage==1){
        $("#pagePre").attr("disabled",true);
        $("#pageBegin").attr("disabled",true);
    }else{
        $("#pagePre").attr("disabled",false);
        $("#pageBegin").attr("disabled",false);
    }

    if (cPage==pageCount){
        $("#pageNext").attr("disabled",true);
        $("#pageEnd").attr("disabled",true);
    }else{
        $("#pageNext").attr("disabled",false);
        $("#pageEnd").attr("disabled",false);
    }
}

function formateTime(dateTime){
    var serverTimeObj= new XDate(_serverTime*1.0);
    return  serverTimeObj.toString("yyyy年MM月dd日 HH:mm:ss");
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function convertToDateLongValue(dateString){
    return XDate.parse(dateString);
}

function setTimeString(diffvalue){
    //alert(timevalue-_serverTime);
    var days=(diffvalue)/_dayLength;
    var hours=((diffvalue)% _dayLength)/_hourLength;
    var min=(((diffvalue)% _dayLength) % _hourLength)/_minLength;
    var sec=(((diffvalue)% _dayLength) % _hourLength)%_minLength /1000;
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

//加入收藏函数
function addFavorite() {
    if(document.all){
        window.external.addFavorite("http://www.landjs.com", "江苏土地市场网");
    }else if(window.sidebar){
        window.sidebar.addPanel('江苏土地市场网', 'http://www.landjs.com','');
    }else{
        alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
    }
    return false; //阻止a标签继续执行
}
