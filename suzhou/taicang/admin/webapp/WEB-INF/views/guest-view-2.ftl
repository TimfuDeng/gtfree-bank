<html>
<head>
    <title>大屏</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/html5.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/respond.min.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/PIE_IE678.js"></script>
    <![endif]-->
    <link href="${base}/static/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/static/h-ui/style.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/lib/bootstrapSwitch/bootstrapSwitch.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!--[if IE 7]>
    <link href="${base}/static/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
    <![endif]-->
    <link href="${base}/static/thridparty/H-ui.2.0/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->

    <link href="${base}/static/css/landsale.css" rel="stylesheet" type="text/css" />

    <link type="text/css" rel="stylesheet" href="${base}/static/thridparty/slidebar/layout.css">
    <link type="text/css" rel="stylesheet" href="${base}/static/thridparty/slidebar/jquery.mmenu.all.css">

    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/static/js/jquery.SuperSlide.2.1.1.source.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/artTemplate/template.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/xdate/xdate.js"></script>
    <script type="text/javascript" src="${base}/static/js/servertime.js"></script>

    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.form.min.js"></script>

    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/layer1.8/layer.min.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/laypage/laypage.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/static/h-ui/js/H-ui.js"></script>

    <script type="text/javascript" src="${base}/static/thridparty/slidebar/jquery.mmenu.min.all.js"></script>

    <script type="text/javascript" src="${base}/static/thridparty/xdate/xdate.js"></script>
    <script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
    <script type="text/javascript" src="${base}/static/js/ajaxDoResult.js"></script>
    <script type="text/javascript" src="${base}/static/js/servertime.js"></script>

    <script type="text/javascript" src="${base}/static/thridparty/slidebar/layout.js"></script>

    <style type="text/css">
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
            text-align: left;
            clear: both;
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

        .orderEnd {
            width: 650px
        }

        .noOrder {
            height: 210px;
            width: 650px;
            text-align: center;
            background: rgb(119, 119, 119)
        }

        /* 本例子css */
        .slideBox{ width:100%; height:100%; overflow:hidden; position:relative;  overflow:hidden;  }
        .slideBox .hd{ height:15px; overflow:hidden; position:absolute; right:5px; bottom:5px; z-index:1; }
        .slideBox .hd ul{ overflow:hidden; zoom:1; float:left;  }
        .slideBox .hd ul li{ float:left; margin-right:2px;  width:15px; height:15px; line-height:14px; text-align:center; background:#fff; cursor:pointer; }
        .slideBox .hd ul li.on{ background:#f00; color:#fff; }
        .slideBox .bd{ position:relative; height:100%; z-index:0;   }
        .slideBox .bd li{ zoom:1; vertical-align:middle; }
        .slideBox .bd img{ width:450px; height:230px; display:block;  }

        /* 下面是前/后按钮代码，如果不需要删除即可 */
        /*.slideBox .prev,
        .slideBox .next{ position:absolute; left:3%; top:50%; margin-top:-25px; display:block; width:32px; height:40px; background:url(${base}/static/image/slider-arrow.png) -110px 5px no-repeat; filter:alpha(opacity=50);opacity:0.5;   }
        .slideBox .next{ left:auto; right:3%; background-position:8px 5px; }
        .slideBox .prev:hover,
        .slideBox .next:hover{ filter:alpha(opacity=100);opacity:1;  }*/
        .slideBox .prevStop{ display:none;  }
        .slideBox .nextStop{ display:none;  }
    </style>
    <script type="text/javascript">
        //相关代码逻辑在layout.js里面
        var baseUrl = '${base}';
    </script>
</head>

<body>
<nav id="menu">
    <div class="mm-panels">
        <ul class="mm-listview mm-fixeddivider"><li class="mm-divider"></li></ul>
        <div class="mm-panel mm-opened mm-current" id="mm-0">
            <ul class="listview-icons mm-listview">
                <li>

                </li>
            </ul>
        </div>
    </div>
</nav>

<div id="page" class="mm-slideout mm-page" style="">


    <div id="hideContent" style="width: 0px;height: 0px;overflow: hidden;">
        <div class="hd">
            <ul>
            </ul>
        </div>
        <div class="bd">
            <ul>
                <li>
                </li>
            </ul>
        </div>

        <!-- 下面是前/后按钮代码，如果不需要删除即可 -->
    <#--<a class="prev" href="javascript:void(0)"></a>
	<a class="next" href="javascript:void(0)"></a>-->

    </div>

    <div class="slideBox">

    </div>


</div>

<a id="hamburger" class="mm-slideout" href="#menu" style="display: inline-block;"><span></span></a>
<span id="serverTimeDiv" style="display: none"></span>
<div id="noResourceItem" style="display: none; font-size: 60px;color:#d91615;padding-top: 150px;width: 100%; text-align: center;">暂　无　信　息</div>
<div id="showResourceItem" style="display: none">
    <script id="test" type="text/html">
        <li>

            <div style="width: 100%;">
                <div>
                    <div sign="headTitle" class="head" style="display: block;text-align: left;">{{resource.resourceCode}}</div>
                </div>
                <div>
                    <div style="float:left;width: 50%;">
                        <div style="margin: 30px 0 0 30px;">

                            <table class="currentInfo" style="display: none;">
                                <tbody>
                                <tr class="price">
                                    <th width="80" style="font-size: 20px;">当前价：</th>
                                    <td class="value" style="color: #d91615;">
                                        <div class="price-font-small">
                                            {{maxOffer}}万
                                        </div>
                                        <div class="detial">
                                            <div class="det1">溢价率：{{((maxOffer/resource.beginOffer)*100).toFixed(0)}}%　单价：{{(maxOffer*10000/resource.crArea).toFixed(0)}}元/平方米　{{(maxOffer/(resource.crArea*0.0015)).toFixed(0)}}万元/亩</div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th style="font-size: 20px;">距结束：</th>
                                    <td class="value">
                                        <span class="time" sign="span_{{resource.resourceId}}" value="{{offerTime}}">

                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                            <table class="orderEnd" style="display: none;">
                                <tr class="price">
                                    <th width="80" style="font-size: 20px;">当前价：</th>
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

                            <table class="noOrder" style="display: none;">
                                <tr>
                                    <td>
                                        <div style="color: #fff;font-size: 36px;">本地块已流拍</div>
                                        <div style="color: #fff;font-size: 36px;">因未有人出价，本地块流拍</div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div>
                            <div class="showInfo">
                                <div style="float: left;">起始价：￥{{resource.beginOffer}}万元</div>
                                <div style="float: left;margin-left: 40px;">加价幅度：￥{{resource.addOffer}}万元</div>
                                <div style="clear: both;"></div>
                            </div>
                            <div class="showInfo">
                                <div style="float: left;">出让面积：{{resource.crArea}}平方米</div>
                                <div style="float: left;margin-left: 40px;">保证金：￥{{resource.fixedOffer}}万元</div>
                                <div style="clear: both;"></div>
                            </div>
                            <div class="showInfo">
                                <div style="float: left;">规划用途：{{resource.landUse}}</div>
                                <div style="float: left;margin-left: 40px;">最高限价：{{resource.maxOffer}}</div>
                                <div style="clear: both;"></div>
                            </div>
                            <div class="showInfo">
                                挂牌开始时间：{{resource.gpBeginTime}}
                            </div>
                            <div class="showInfo">
                                挂牌结束时间：{{resource.gpEndTime}}
                            </div>
                        </div>
                    </div>
                    <div style="float:left;width: 50%;">
                        <div class="recInfo">竞拍总计<span>{{offerFrequency}}</span>次</div>
                        <div>
                            <table class="tableRight">
                                <thead>
                                <tr>
                                    <th width="100" style="text-align: center;">状态</th>
                                    <th width="200" style="text-align: right;">价格（万元）</th>
                                    <th style="text-align: center;">时间</th>
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
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div style="clear: both;"></div>
                </div>
            </div>
        </li>
    </script>

</div>

</body>
</html>