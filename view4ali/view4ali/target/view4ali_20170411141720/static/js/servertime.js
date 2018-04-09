//获取服务器时间，并倒计时，htmlObj显示日期元素
function servertime(htmlObj,secFun){
    this.htmlObj=htmlObj;
    this.secFun=secFun;
    var beginGetDate=(new XDate()).valueOf()*1.0;
    $.ajax({
        type: "GET",
        url: "./time",
        cache:false,
        success: function(data){
            var endGetDate=(new XDate()).valueOf()*1.0;
            servertime.prototype.timeLongValue= data*1.0+endGetDate-beginGetDate;
            htmlObj.html(formateTime(servertime.prototype.timeLongValue));
            setInterval(function(){
                servertime.prototype.timeLongValue=servertime.prototype.timeLongValue+1000;
                htmlObj.html(formateTime(servertime.prototype.timeLongValue));
                try{

                    servertime.prototype.secFun();
                }catch(ex){

                }
            },1000);
        },
        complete : function(XMLHttpRequest,status){
            if(status=='timeout'){//超时,status还有success,error等值的情况

            }else if (status=='error'){

            }
        }
    });

    this.setSecFunction=function(funObj){
        servertime.prototype.secFun=funObj;
    }
}