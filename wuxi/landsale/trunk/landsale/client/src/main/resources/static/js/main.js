$(document).ready(function(){alert(222)
    $("body").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            if ($("#frmSearch").length > 0) {
                // 回车执行查询
                reloadSrc("frmSearch");
            }
            return false;
        }
    });
});

function showConfirm(content){
    $('#modal_content').html(content);
    $('#myModal').modal({
        backdrop: false
    });
}

// 跳转
function changeSrc(url, data) {
    $("#mainDiv").load(url, data);
    $("#mainUrl").attr("value", url);
    return;
}

// 刷新
function reloadSrc (formId, params) {
    var formObj = $("#"+formId);
    var indexObjs = $("#" + formId).serializeArray();
    var data = {};
    if (indexObjs != null && indexObjs.length > 0) {
        for (var i = 0, len = indexObjs.length; i < len; i++) {
            data[indexObjs[i].name] = indexObjs[i].value;
        }
    }
    jQuery.extend(data, params)
    $("#mainDiv").load($("#mainUrl").val(), data);
}

function isEmpty (arg) {
    return (arg == null || arg == '' || arg == 'undefined');
}



/**
 * 扩展startWith方法
 * @param str
 * @return
 */
String.prototype.startsWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
        return false;
    if(this.substr(0,str.length)==str)
        return true;
    else
        return false;
    return true;
};