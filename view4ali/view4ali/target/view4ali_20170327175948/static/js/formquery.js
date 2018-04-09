function formQuery(){
    this.init=function(_self){
        //绑定事件，行政区
        $("#regionul").find("a").each(function(){
            $(this).click(function(){
                var idvalue=$(this).parent().attr("id");
                var region = idvalue.match(/[1-9][0-9]*/g);
                if (region.length>0) {
                    _self.region = region[0];
                    _self.submit();
                    $("#regionul").find(".active").removeClass("active");
                    $(this).parent().addClass("active");
                }
            });
        });
        //查询名称
        $(".searchButton").click(function(){
            _self.name = $('#searchKeyword').val();
            _self.submit();
        });
        //状态
        $("#statusul").find("a").each(function(){
            $(this).click(function(){
                var idvalue=$(this).parent().attr("id");
                var status = idvalue.match(/[0-9][0-9]*/g);
                if (status.length>0) {
                    _self.status = status[0];
                    _self.submit();
                    $("#statusul").find(".active").removeClass("active");
                    $(this).parent().addClass("active");
                }
            });
        });
        //用途
        $("#useul").find("a").each(function(){
            $(this).click(function(){
                var idvalue=$(this).parent().attr("id");
                var use = idvalue.match(/[0-9][0-9]*/g);
                if (use.length>0) {
                    _self.use = use[0];
                    _self.submit();
                    $("#useul").find(".active").removeClass("active");
                    $(this).parent().addClass("active");
                }
            });
        });
        //翻页
        $("#pageNext").click(function(){
            _self.setPageIndex(_self.index+1);
            _self.submit();
        });
        $("#pagePre").click(function(){
            _self.setPageIndex(_self.index-1);
            _self.submit();
        });
        $("#pageBegin").click(function(){
            _self.setPageIndex(0);
            _self.submit();
        });
        $("#pageEnd").click(function(){
            _self.setPageIndex(_self.pageCount-1);
            _self.submit();
        });
        var region=getUrlParam("region");
        if (region){
            _self.region=region;
            if (region.length>4){
                region=region.substr(0,4);
            }
            $("#regionul").find(".active").removeClass("active");
            $("#regionul").find("#region" + region).addClass("active");
        }
    };

    this.setPageIndex=function(index){
        this.index=index;
    };

    this.setPageCount=function(count){
        this.pageCount=count;
    };

    this.getQueryData=function(){
        var param={};
        if (this.region){
            param.region=this.region;
        }
        if (this.name){
            param.name=this.name;
        }
        if (this.status){
            param.status=this.status;
        }
        if (this.use){
            param.use=this.use;
        }
        if (this.index){
            param.page=this.index;
        }
        return param;
    };

    this.submit=function(){
        BeginQueryResourceList(this.getQueryData());
    };
}