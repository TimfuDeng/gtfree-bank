/**
 * 调用ajax并返回执行结果
 * @constructor
 */
var _ShowAjaxResult=function(ajaxUrl,divId,callBackFun){
    $.get(ajaxUrl, function(result){
        var divObj=$("#" + divId);
        if (result && divObj){
            var html="";
            if (!result.bool){
                html='<div class="alert alert-danger">'+
                    '<strong> '+
                    result.msg+
                    '</strong>'+
                    '<br> '+
                    '</div>';
            }else{
                html='<div class="alert alert-success">'+
                '<strong> '+
                    result.msg+
                '</strong>'+
                '<br> '+
                '</div>';
            }
            $("#" + divId).html(html);
            $("#" + divId).fadeIn(1000,function(){
                $("#" + divId).fadeOut(2000);
            });
            callBackFun(result);
        }
    });
};

var _FormAjaxResult=function(formId,url,divId,callBackFun){
    var options = {
        url: url,
        success: function(result) {
            var divObj=$("#" + divId);
            if (result && divObj){
                _alertResult(divId,result.bool,result.msg);
                callBackFun(result);
            }
        }
    };
    $('#'+formId).ajaxSubmit(options);
};

/**
 * 弹出提示
 * @private
 */
var _alertResult=function(msgDivId,boolsucess,msg){
    if (!boolsucess){
        html='<div class="alert alert-danger">'+
            '<strong> '+
            msg+
            '</strong>'+
            '<br> '+
            '</div>';
    }else{
        html='<div class="alert alert-success">'+
            '<strong> '+
            msg+
            '</strong>'+
            '<br> '+
            '</div>';
    }
    $("#" + msgDivId).html(html);
    $("#" + msgDivId).fadeIn(1000,function(){
        $("#" + msgDivId).fadeOut(2000);
    });
};

var _confirm=function(msg,callbackFun){
    var confirmFormName="_confirmForm";
    var confirmObj= $('#'+confirmFormName);
    if (confirmObj.length>0){
        $(confirmObj).find("modal-body").html(msg);
    }else{
        var html='<div class="modal modal-sm fade" id="'+confirmFormName+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
                '<div class="modal-content">'+
                    '<div class="modal-body">'+ msg +
                    '</div>'+
                    ' <div class="modal-footer">'+
                        '<button type="button" class="btn btn-primary" >确定</button>'+
                        '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button> '+
                    '</div>'+
                '</div>'+
            '</div>';
        confirmObj=$(html).appendTo(document.body);
    }
    $(confirmObj).find(".btn-primary").one("click",function(){
        $(confirmObj).modal('hide');
        callbackFun(true);
    });
    $(confirmObj).find(".btn-primary").one("click",function(){
        $(confirmObj).modal('hide');
        callbackFun(true);
    });
    $(confirmObj).modal();
}

/**
 * 分页提交
 * @param formId
 * @param index
 * @constructor
 */
function SubmitPageForm(formId, index){
    var data = $("#" + formId).serializeArray();
    data.push({name: "index", value: index});
    loadData(data);
}

/**
 * 分页提交 老系统
 * @param formId
 * @param index
 * @constructor
 */
function SubmitPageFormOld(formId, index){
    var url = $("#" + formId).attr("action");
     url = url.indexOf("?") > -1 ? (url + "&index=" + index) : (url + "?index=" + index);
     $("#mainFrame", window.parent.document).attr("src", url);
}