
var _serverTimeObj;
var _resourceObj;

$(document).ready(function () {
    //获取服务器时间
    _serverTimeObj=new servertime($("#serverTimeSpan"),null);
    var resourceId=getUrlParam("id");
    if (resourceId && resourceId!=null && resourceId!="") {
        _resourceObj = new resource(_serverTimeObj, resourceId);
        _resourceObj.getFromServer(_resourceObj);
        _serverTimeObj.setSecFunction(function(){
            _resourceObj.refresh(_resourceObj);
        });
    }
    $.Huitab("#tab_demo .tabBar span","#tab_demo .tabCon","current","click","0");
});

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

