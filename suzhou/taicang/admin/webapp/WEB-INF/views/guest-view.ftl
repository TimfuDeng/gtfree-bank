<html>
<head>
    <title>大屏</title>
    <style>

        body {
            margin: 0px;
            padding-top: 3%;
        }

        * {
            font-family: '微软雅黑';
            color: #333
        }

        .head {
            font-size: 60px;
            font-weight: bold;
            color: #fff;
            height: 106px;
            line-height: 106px;
            background: #d91615;
            padding-left: 40px;
        }

        table {
            font-size: 36px;
        }

        th {
            font-weight: 600;
            color: #000
        }

        .price-font-small {
            font-size: 60px;
            font-weight: bold;
            width: 300px;
            color: #d91615
        }

        .detial * {
            font-size: 24px;
            color: #555
        }

        .time * {
            font-style: normal;
            font-weight: bold
        }

        .time var {
            font-size: 60px;
            color: #d91615;
        }

        .time em {
            font-size: 36px;
            padding: 12px
        }

        .showInfo {
            font-size: 23px;
            font-weight: bold;
            line-height: 60px;
            padding-left: 30px;
            clear: both
        }

        .showInfo span {
            width: 350px;
            display: block;
            float: left;
        }

        .recInfo {
            font-size: 26px;
            font-weight: bold;
            padding-top: 30px;
        }

        .recInfo span {
            font-size: 40px;
        }

        .tableRight * {
            font-size: 20px;
            font-weight: bold;
            line-height: 50px;
        }

        .tableRight .td1 {
            text-align: center
        }

        .active .rad {
            background: #cc0000;
            color: #fff;
            margin-left: 15px;
            width: 70px;
            height: 36px;
            line-height: 36px;
            -moz-border-radius: 10px;
            -webkit-border-radius: 10px;
            border-radius: 10px;
        }

        .rad {
            background: #777;
            color: #fff;
            margin-left: 15px;
            width: 70px;
            height: 36px;
            line-height: 36px;
            -moz-border-radius: 10px;
            -webkit-border-radius: 10px;
            border-radius: 10px;
        }

        .active .td2, .active .td3 {
            color: #cc0000;
        }

        .fr {
            text-align: right;
        }

        .pdr30 {
            padding-right: 30px;
        }

        .td2 {
            color: #777;
            padding-right: 30px;
            text-align: right;
        }

        .td3 {
            color: #777;
            text-align: center
        }

        .tableRight td, .tableRight tr {
            border-bottom: 1px solid #c1c1c1
        }

        .point {
            width: 20px;
            height: 20px;
            border-radius: 20px;
            background: #ececec;
            float: left;
            margin-left: 12px;
        }

        .pointActive {
            width: 20px;
            height: 20px;
            border-radius: 20px;
            background: #d91615;
            float: left;
            margin-left: 12px;
        }

        .traded {
            color: #5eb95e
        }

        .tradedInfo {
            text-align: center;
            background: #5eb95e;
            height: 120px;
        }

        .tradedInfo div {
            color: #fff;
            line-height: 50px;
        }

        .tradedhead {
            background-color: #5eb95e
        }

        #orderEnd {
            width: 800px
        }

        #noOrder {
            display: none;
            height: 210px;
            width: 800px;
            text-align: center;
            background: rgb(119, 119, 119)
        }

    </style>
    <script type="text/javascript" src="${base}/static/thridparty/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/artTemplate/template.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/xdate/xdate.js"></script>
    <script type="text/javascript" src="${base}/static/js/servertime.js"></script>
    <script>
        var dikuai;
        var pageindex = 0;
        var t;
        var currentpage=0;
        var _serverTime=${.now?long};
        $(function () {
            //获取服务器时间
            setServerTime("${base}/getServerTime.f", "#serverTimeDiv");
            getResourceItem();
        });

        function show() {
            try {
                if (pageindex < dikuai.dikuai.length) {
                    currentpage = pageindex;
                    setAttr(dikuai.dikuai[pageindex]);
                    pageindex++;
                } else {
                    pageindex = 0;
                }
            } catch (e) {
                currentpage = 0;
                pageindex = 0;
            } finally {
                clearTimeout(t);
                if(dikuai!=null&&dikuai.dikuai.length>0){
                    t = setTimeout(function () {
                        getResourceItem();
                    }, dikuai.dikuai[currentpage].splittime);
                    $("#showResourceItem").show(200);
                    $("#noResourceItem").hide(200);

                }else{
                    t = setTimeout(function () {
                        getResourceItem();
                    }, 1000);
                    $("#showResourceItem").hide(200);
                    $("#noResourceItem").show(200);

                }

            }

        }
        function getResourceItem() {
            $.get('${base}/console/trans/resource', function (data) {
                dikuai = data;
                show();
            });
        }

        function setAttr(obj) {
//            $(".time").each(function(){
//                var timevalue = $(this).attr("value");
//                if (timevalue!=null&&timevalue!=''){
//                    var idvalue=$(this).attr("id");
//                    if(idvalue && _functionMap[idvalue])
//                        clearInterval(_functionMap[idvalue]);
//                }
//            });
            formatResourceInfo(obj);
            var html = template('test', obj);
            document.getElementById('showResourceItem').innerHTML = html;

            $("#currentInfo").hide(200);
            $("#headTitle").hide(200);
            $("#noOrder").hide(200);
            $("#orderEnd").hide(200);
            switch (obj.resource.resourceStatus) {
                case 1://正在竞价
                    $("#headTitle").attr("class", "head");
                    $('#currentInfo').show(300);
                    break;
                case 10://挂牌
                    $("#headTitle").attr("class", "head");
                    $('#currentInfo').show(300);
                    break;
                case 20://公告
                    $("#headTitle").attr("class", "head");
                    $('#currentInfo').show(300);
                    break;
                case 30://已经完成
                    $("#headTitle").attr("class", "head tradedhead");
                    $("#orderEnd").show(300);
                    break;
                case 31://流标
                    $("#headTitle").attr("class", "head");
                    $("#noOrder").show(300);
                    break;

            }
            $("#headTitle").show(200);

            $("#sliderBar").html('');
            for (var i = 0; i < obj.itemcount; i++) {
                if (i == obj.currentindex - 1) {
                    $("#sliderBar").html($("#sliderBar").html() + "<div class='pointActive'></div>");
                } else {
                    $("#sliderBar").html($("#sliderBar").html() + "<div class='point'></div>");
                }
            }

            $(".time").each(function(){
                var timevalue = $(this).attr("value");
                if (timevalue!=null&&timevalue!=''){
                    var idvalue=$(this).attr("id");
                    $(this).html(setTimeString(timevalue));
                    var funObj=setInterval(refreshTime,1000,this);
                    if(idvalue) {
                        _functionMap[idvalue] = funObj;
                    }
                }
            });
        }

        function formatResourceInfo(value){
            if(value.resource.maxOffer==0){
                value.resource.maxOffer='无';
            }
            if(value.resource.gpBeginTime!=null){
                var dateObj= new XDate(value.resource.gpBeginTime*1);
                value.resource.gpBeginTime=dateObj.toString("yyyy年MM月dd日 HH:mm:ss");
            }

            if(value.resource.gpEndTime!=null){
                var dateObj= new XDate(value.resource.gpEndTime*1);
                value.resource.gpEndTime=dateObj.toString("yyyy年MM月dd日 HH:mm:ss");
            }
            for(var i =0;i<value.offerList.length;i++){
                var dateObj= new XDate(value.offerList[i].offerTime*1);
                value.offerList[i].offerTime=dateObj.toString("MM月dd日 HH:mm:ss");

            }
        }


        var _dayLength=24*60*60*1000;
        var _hourLength=60*60*1000;
        var _minLength=60*1000;
        var _functionMap=new Object();

        function setTimeString(timevalue){
            var days=(timevalue-_serverTime)/_dayLength;
            var hours=((timevalue-_serverTime)% _dayLength)/_hourLength;
            var min=(((timevalue-_serverTime)% _dayLength) % _hourLength)/_minLength;
            var sec=(((timevalue-_serverTime)% _dayLength) % _hourLength)%_minLength /1000;
            if (days>1)
                return  buildTimeStr(days) + "<em>天</em>" +buildTimeStr(hours) + "<em>时</em>" +  buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(sec) + "<em>秒</em>";
            else if(hours>1)
                return buildTimeStr(hours) + "<em>时</em>" +  buildTimeStr(Math.floor(min)) + "<em>分</em>" +  buildTimeStr(Math.floor(sec)) + "<em>秒</em>";
            else if(min>1)
                return buildTimeStr(min) + "<em>分</em>" +  buildTimeStr(Math.floor(sec)) + "<em>秒</em>";
            else if(sec>0)
                return buildTimeStr(sec) + "<em>秒</em>";
        }

        function buildTimeStr(value){
            value=value*1.0;
            if (value>10){
                return "<var>" + Math.floor(value) + "</var>";
            }else{
                return "<var>0" + Math.floor(value) + "</var>";
            }
        }

        function refreshTime(obj){
            var timevalue=$(obj).attr("value");
            if ((timevalue-_serverTime)<0) {
                try{
                    var idvalue=$(obj).attr("id");
                    if(idvalue && _functionMap[idvalue])
                        clearInterval(_functionMap[idvalue]);
                }catch(ex){

                }
            }else{
                $(obj).html(setTimeString(timevalue));
            }
        }


    </script>
</head>

<body>
<span id="serverTimeDiv" style="display: none"></span>
<div id="noResourceItem" style="font-size: 60px;color:#d91615;width: 450px; margin: auto; padding-top: 10%">暂　无　信　息</div>
<div id="showResourceItem" style="display: none">
    <script id="test" type="text/html">
        <div id="headTitle" class="head">{{resource.resourceCode}}</div>
        <div style="padding: 30px 0px 0px 30px; float: left">
            <table id="currentInfo">
                <tr class="price">
                    <th width="200">当前价：</th>
                    <td class="value" style="color: #d91615;">
                        <div class="price-font-small " id="maxOffer">
                            {{maxOffer}}万
                        </div>
                        <div class="detial">
                            <div class="det1">溢价率：{{((maxOffer/resource.beginOffer)*100).toFixed(0)}}%　单价：{{(maxOffer*10000/resource.crArea).toFixed(0)}}元/平方米　{{(maxOffer/(resource.crArea*0.0015)).toFixed(0)}}万元/亩</div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>距结束：</th>
                    <td class="value">
                        <span class="time" id="span_{{resource.resourceId}}" value="{{offerTime}}">

                        </span>
                    </td>
                </tr>
            </table>

            <table id="orderEnd" style="display: none; ">
                <tr class="price">
                    <th width="200">当前价：</th>
                    <td class="value" style="color: #d91615;">
                        <div id="orderPrice" class="price-font-small traded">
                            {{maxOffer}}万
                        </div>
                        <div class="detial">
                            <div class="det1">溢价率：{{((maxOffer/resource.beginOffer)*100).toFixed(0)}}%　单价：{{(maxOffer*10000/resource.crArea).toFixed(0)}}元/平方米　{{(maxOffer/(resource.crArea*0.0015)).toFixed(0)}}万元/亩</div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tradedInfo" colspan="2">
                        <div style="font-size: 35px">本地块已成交</div>
                        <div style="font-size: 24px;">成交人：{{offerUser}}</div>
                    </td>
                </tr>
            </table>

            <table id="noOrder">
                <tr>
                    <td>
                        <div style="color: #fff;">本地块已流拍</div>
                        <div style="color: #fff;">因未有人出价，本地块流拍</div>
                    </td>
                </tr>
            </table>


            <div class="showInfo">
                <span>起始价：￥{{resource.beginOffer}}万元</span>
                <span>加价幅度：￥{{resource.addOffer}}万元</span>
            </div>
            <div class="showInfo">
                <span>出让面积：{{resource.crArea}}平方米</span>
                <span>保证金：￥{{resource.fixedOffer}}万元</span>
            </div>
            <div class="showInfo">
                <span>规划用途：{{resource.landUse}}</span>
                <span>最高限价：{{resource.maxOffer}}</span>
            </div>
            <div class="showInfo">
                挂牌开始时间：{{resource.gpBeginTime}}
            </div>
            <div class="showInfo">
                挂牌结束时间：{{resource.gpEndTime}}
            </div>
        </div>
        <div style="float: right;padding-right: 20px;">
            <div class="recInfo">竞拍总计<span>{{offerFrequency}}</span>次</div>
            <table class="tableRight" width="700">
                <thead>
                <tr>
                    <th width="100">状态</th>
                    <th class="fr pdr30" width="300">价格（万元）</th>
                    <th width="300">时间</th>
                </tr>
                </thead>
                <tbody>
                {{each offerList as offer i}}
                    <tr>
                        <td class="td1">
                            <div class="rad">{{if i==0}}领先{{else}}出局{{/if}}</div>
                        </td>
                        <td class="td2">{{offer.offerPrice}}</td>
                        <td class="td3">{{offer.offerTime}}</td>
                    </tr>
                {{/each}}
                <#--<tr class="active">-->
                    <#--<td class="td1">-->
                        <#--<div class="rad">领先</div>-->
                    <#--</td>-->
                    <#--<td class="td2">129,874</td>-->
                    <#--<td class="td3">12:00:12　03月12日</td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td class="td1">-->
                        <#--<div class="rad">出局</div>-->
                    <#--</td>-->
                    <#--<td class="td2">119,874</td>-->
                    <#--<td class="td3">11:30:22　03月12日</td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td class="td1">-->
                        <#--<div class="rad">出局</div>-->
                    <#--</td>-->
                    <#--<td class="td2">109,874</td>-->
                    <#--<td class="td3">11:20:32　03月12日</td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td class="td1">-->
                        <#--<div class="rad">出局</div>-->
                    <#--</td>-->
                    <#--<td class="td2">89,874</td>-->
                    <#--<td class="td3">11:10:32　03月12日</td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td class="td1">-->
                        <#--<div class="rad">出局</div>-->
                    <#--</td>-->
                    <#--<td class="td2">79,874</td>-->
                    <#--<td class="td3">11:10:13　03月12日</td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td class="td1">-->
                        <#--<div class="rad">出局</div>-->
                    <#--</td>-->
                    <#--<td class="td2">5,874</td>-->
                    <#--<td class="td3">11:00:32　03月12日</td>-->
                <#--</tr>-->
                </tbody>
            </table>

        </div>
        <div id="sliderBar" style="clear: both; margin: auto; padding-top: 12px;">
            <div class="pointActive"></div>
        </div>
    </script>

</div>
</body>
</html>