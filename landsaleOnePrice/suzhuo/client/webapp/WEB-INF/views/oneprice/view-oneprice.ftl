<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta charset="utf-8">
    <title></title>
    <style type="text/css">
        .table_resource tr{
            height: 27px;
        }
        .value{
            padding-left: 20px;
        }
        .currency{
            font-size: 16px;
        }
        .price-font-small{
            font-size: 26px;
            font-family: 'Microsoft Yahei', 'Hiragino Sans GB', 'Helvetica Neue', Helvetica, tahoma, arial, Verdana, sans-serif, 'WenQuanYi Micro Hei', 宋体;
            font-style: normal;
            font-weight: bold;
        }

        .time em{
            font-weight: 400;
            font-style: normal;
            color: #666;
            font-size: 12px;
            margin-left: 3px;
        }
        .time var{
            font-size: 26px;
            font-style: normal;
            font-weight: bold;
            margin-left: 3px;
        }
        .table-info td{
            padding-top:0px;
        }

        .offer-info div{
            margin-bottom:0px;
        }
        .table-price td{
            padding:3px 3px;
        }
        .price-disable{
            background-color: #f3f3f3;
        }
        .table-offer td{
            padding-left:20px;
        }
        .currency{
            font-style: normal !important;
        }
    .table-money{
        width:480px!important;
        overflow: hidden;

    }
        .table-money td{
            padding:0px!important;
        }
        .table-money td input{
            width:30px!important;
            border:none!important;
            background: none!important;
        }
    </style>
    <link rel="stylesheet" href="${base}/static/thridparty/leaflet/leaflet.css" />
    <link rel="stylesheet" href="${base}/static/js/dist/view.css" />

</head>
<body>

<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">󰄫</i>
        <a href="/client/oneprice/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">苏地2016-WG-22号</span>
    </nav>
    <div class="row" style="border: 1px solid #eeecec;overflow: auto">
        <div class="col-10">
            <div class="row cl" style="padding:0px 10px">
                <h3 style="width:500px">苏地2016-WG-22号</h3>
            </div>
            <div class="row cl">
                <div class="col-5" style="padding-left: 10px">
                    <div style="border: none;overflow: hidden;clear: none!important;">
                        <div class="picbox">
                            <div id="featured">
                                <div class="image" id="image_pic-01">
                                    <img class="imageItem" src="/client/static/image/blank.jpg" onerror="this.src='/client/static/image/blank.jpg'" width="402" height="320">
                                </div>
                            </div>
                            <div id="thumbs">
                                <ul>
                                    <li id="play_prev" class="iconfont">󰅮</li>
                                    <li class="slideshowItem"><a id="thumb_pic-01" href="javascript:" class="current"><img src="/client/static/image/blank.jpg" width="78" height="37" onerror="this.src='/client/static/image/blank.jpg'"></a></li>
                                    <li id="play_next" class="iconfont" style="float: right">󰅭</li>
                                </ul>
                                <div class="clear"></div>
                            </div>
                            <script type="text/javascript">
                                var target = ["pic-01","pic-02","pic-03","pic-04"];
                            </script>
                        </div>
                    </div>

                </div>
                <div class="col-7">
                    <div class="row cl" style="min-height:200px;">
                        <table class="table table-offer" style="-moz-user-select:none;" onselectstart="return false">
                            <tbody>
                            <tr class="price">
                                <td width="51">中止价</td>
                                <td class="value" style="color: #d91615;">
                                    <em class="currency">¥</em>
                                    <em class="price-font-small" id="priceInfo">
                                        1250
                                    </em>万
                                </td>
                            </tr>
                            <tr>
                                <td>距等待结束</td>
                                <td class="value">
                                    <span class="time" id="span_01560c10e2135a0c0658560c10ad0005" value="1470902400000"><var>5</var><em>分</em><var>00</var><em>秒</em></span>
                                </td>
                            </tr>
                            <tr class="price price-disable">
                                <td>
                                    <p style="margin-bottom:0px">
                                        出&nbsp;&nbsp;&nbsp;&nbsp;价</p><p style="margin-bottom:0px">

                                </p>
                                </td>
                                <td  style="color: #d91615;">
                                    <table class="table table-border table-bordered table-money">
                                        <tr>
                                            <td class="text-c" >百</td>
                                            <td class="text-c">十</td>
                                            <td class="text-c">亿</td>
                                            <td  class="text-c">千</td>
                                            <td class="text-c">百</td>
                                            <td class="text-c">十</td>
                                            <td class="text-c">万</td>
                                            <td class="text-c">千</td>
                                            <td class="text-c">百</td>
                                            <td class="text-c">十</td>
                                            <td class="text-c">元</td>
                                           <#-- <td class="text-c">角</td>
                                            <td class="text-c">分</td>-->
                                        </tr>
                                        <tr class="one-price" >
                                            <#--/ onkeyup="keyPress()"/-->
                                            <td><input id="baiYi" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="shiYi" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="yi" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="qianWan" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="baiWan" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="shiWan" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="wan" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="qian" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="bai" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="shi" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="yuan" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <#--<td><input id="jiao" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>
                                            <td><input id="fen" class="text-r" type="text" min="0" max="9" onpaste="return false;" onkeypress="keyPress()"  maxlength="1" /> </td>-->


                                        </tr>
                                    </table>

                                </td>
                            </tr>
                            <tr class="price">
                                <td>&nbsp;&nbsp;</td>
                                <td class="value" style="color: #d91615;">
                                    <input id="oneprice"  class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
                                           onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px"></div>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                    <div class="row cl" style="padding: 5px 20px;">
                        <table class="table table-info">
                            <tr>
                                <td>
                                    <span class="title">面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积：</span>
                                    <span class="current-price">7702&nbsp;平方米</span>
                                </td>
                                <td>
                                    <span class="title">规划用途：</span>
                                    <span>城镇住宅用地</span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="title">报价区间（万元）：</span>
                                <span>
                                (1250,1500]
                                </span>
                                </td>
                                <td>
                                    <span class="title">中止时间：</span>
                                <span>
                                 2016-07-28 10:30
                                </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="title">等待开始时间：</span>
                                <span>
                                2016-07-28 10:30
                                </span>
                                </td>
                                <td>
                                    <span class="title">等待结束时间：</span>
                                <span>
                                2016-07-28 11:00
                                </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="title">询问开始时间：</span>
                                <span>
                                2016-07-28 11:00
                                </span>
                                </td>
                                <td>
                                    <span class="title">询问结束时间：</span>
                                <span>
                                2016-07-28 11:05
                                </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="title">报价开始时间：</span>
                                <span>
                                2016-07-28 11:05
                                </span>
                                </td>
                                <td>
                                    <span class="title">报价结束时间：</span>
                                <span>
                                2016-07-28 11:10
                                </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

        </div>
        <div class="col-2 cl" style="border-left: 1px solid #e9e9e9;height: 495px;
                background: url(${base}/static/image/price-back.png) repeat-x 0 0 #fff;padding:0px 0px 0px 5px;">
            <h5 style="border-bottom: 1px solid #eeecec;line-height:40px">竞买记录<span></h5>
            <table class="table table-price">
                <thead>
                <tr>
                    <td  style="width:40px;">报价人</td>
                    <td class="text-c">报价</td>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel"><i class="icon-warning-sign"></i>出价确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal" onclick="javascript:postOffer(this);">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
<script>
    $(function(){
        $("#span_01560c10e2135a0c0658560c10ad0005").attr("value",new Date().getTime()+1000*60*5);
    });
    function keyPress(){
       var keyCode = event.keyCode;
        if ((keyCode >= 48 && keyCode <= 57))
        {
            event.returnValue = true;
        } else {
            event.returnValue = false;
        }
    }
</script>
<script type="text/javascript" src="${base}/static/js/slide.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/leaflet/leaflet.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/laytpl/laytpl.js"></script>
<script type="text/javascript" src="${base}/static/js/index.js"></script>
<script type="text/javascript" src="${base}/static/js/view.js"></script>
</body>
</html>