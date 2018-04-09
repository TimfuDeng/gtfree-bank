
function offerObj(servertime,resourceId){
    this.servertime=servertime;
    this.resourceId=resourceId;


    this.refresh=function(){
        var geturl="./offer?resourceId=" + resourceId;
        if (offerObj.prototype.cTime){
            geturl=geturl+"&time=" +offerObj.prototype.cTime;
        }
        var getTime=servertime.timeLongValue;
        $.ajax({
            type: "GET",
            url: geturl,
            cache:false,
            success: function(data){
                if (data) {
                    var gettpl = $("#offers").html();
                    laytpl(gettpl).render(data, function (html) {
                        $("#tblOffersBody").html(html);
                    });
                    if (data.list && data.list.length>0){
                        $(".spanPrice").html(data.list[0].offerPrice);
                        var endTime=data.list[0].offerTime+1000*60*4;
                        $(".spanTime").attr("value",endTime);
                    }
                }
                offerObj.prototype.cTime=getTime;
            }
        });
    };

}

