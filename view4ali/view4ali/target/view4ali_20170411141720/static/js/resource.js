
function resource(servertimeObj,resourceId){
    this.servertimeObj=servertimeObj;
    this.resourceId=resourceId;
    this.beginGetOffer=false;
    this.obj=null;
}

//从服务器获取数据
resource.prototype.getFromServer=function(__self){
    $.ajax({
        type: "GET",
        url: "./resource?resourceId=" + __self.resourceId,
        cache:false,
        success: function(data){

            var gettpl =$("#resourceInfo").html();
            laytpl(gettpl).render(data, function(html){
                $("#resourceDiv").html(html);
            });
           $(".resourceCode").html(data.resourceCode);
            var gpBeginTime=convertToDateLongValue(data.gpBeginTime);
            var gpEndTime=convertToDateLongValue(data.gpEndTime);
            var xsBeginTime=convertToDateLongValue(data.xsBeginTime);
            if (!__self.obj){
                $(".spanPrice").html(data.beginOffer);
                $(".spanTime").attr("value",0);
            }
            __self.obj=data;
            __self.updatePriceInfo();
            var serverTime=__self.servertimeObj.timeLongValue;
            if (serverTime < gpBeginTime ) {
                //公告中
                $(".spanTime").attr("value",gpBeginTime);
                $("#spanStatus").html('<span class="label  label-success">公告中</span>');
                __self.beginGetOffer=false;
            }else if (serverTime > gpBeginTime && serverTime < gpEndTime){
                //挂牌中
                $(".spanTime").attr("value",gpEndTime);
                $("#spanStatus").html('<span class="label  label-secondary">挂牌中</span>');
                __self.beginGetOffer=true;
            }else if(serverTime>gpEndTime && serverTime < xsBeginTime){
                $(".spanTime").attr("value",xsBeginTime);
                $("#spanStatus").html('<span class="label  label-danger">即将竞价</span>');
                __self.beginGetOffer=false;
            }else if(serverTime>xsBeginTime){
                //限时竞价中
                if(data.overStatus && data.overStatus!=null && data.overStatus!=""){
                    $("#spanStatus").html('<span class="label  label-default">已结束</span>');
                    __self.beginGetOffer=false;
                    var templet =$("#spanOver").html();
                    laytpl(templet).render(data, function(html){
                        $(".infoDiv").html(html);
                    });
                }else{
                    $("#spanStatus").html('<span class="label  label-danger">竞价中</span>');
                    __self.beginGetOffer=true;
                    $(".spanTime").attr("value",xsBeginTime+1000*60*4);
                }
            }
            __self.getoffer();
        }
    });
};


resource.prototype.getoffer=function(){
    if (this.beginGetOffer){
        var offer=new offerObj(this.servertimeObj,this.resourceId);
        offer.refresh(this);
        if (this.intervalOfferFun) {
            clearInterval(this.intervalOfferFun);
            this.intervalOfferFun=null;
        }
        this.intervalOfferFun=setInterval(offer.refresh,3000);
    }
};

//根据时间判断，每秒
resource.prototype.refresh=function(_self){
    var servertimeObj=_self.servertimeObj;

    $(".spanTime").each(function(){
        if ($(this).attr("value") && $(this).attr("value")>0){
            var diffValue=$(this).attr("value")*1.0-servertimeObj.timeLongValue;
            if (diffValue>0) {
                $(this).html(showDateTime(diffValue));
            }else{
                var dot=$("#spandot").html();
                if (dot.length<6){
                    $("#spandot").html(dot+ ".");
                }else{
                    $("#spandot").html("");
                }
                setTimeout(_self.getFromServer(_self), 3000);
            }
        }
    });
};


//更新价格相关单价信息
resource.prototype.updatePriceInfo=function(){
    var price=$(".spanPrice").html();
    if (price) price=price*1.0;
    var area=this.obj.crArea*1.0;
    var html="";
    if (area>0){
        html="参考地价：" + Math.round(price*10000/area) + "<em>元／平方米</em>";
        html=html+"  "+Math.round(price*666.67/area) + "<em>万元／亩</em>";
        var rjl=this.obj.rjl*1.0;
        // rjl=2.0;
        if (rjl>0){
            html=html+"  参考楼面价："+Math.round(price*10000.0/area/rjl) + "<em>元／平方米</em>";
        }
    }
    
    $(".spanPriceInfo").html(html);
};

function convertToDateLongValue(dateString){
    return XDate.parse(dateString);
}