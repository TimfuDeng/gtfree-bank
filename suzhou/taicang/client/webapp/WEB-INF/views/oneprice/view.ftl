<@Head_SiteMash title="" />

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
        .table-info span{
            font-size:14px;

        }
        .table-money{
            width:520px!important;
            overflow: hidden;

        }
        .table-money td{
            padding:0px!important;
        }

        .table-money td input{
            /*width:30px!important;*/
            border:none!important;
            background: none!important;
        }
    </style>
    <link rel="stylesheet" href="${base}/static/thridparty/leaflet/leaflet.css" />
    <link rel="stylesheet" href="${base}/static/js/dist/view_201611070805.css" />
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">󰄫</i>
        <a href="${base}/oneprice/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><span class="c_gray "  >${oneTarget.transName!}</span>
    </nav>
<div class="row" style="border: 1px solid #eeecec;overflow: auto">
    <div class="col-7" style="background:#f5f5f5 ">
        <div class="row cl" >
            <h3 style=" text-align: center;background:#e5e5e5;color: #0684CE;font-weight: 700;padding-top:10px;">${oneTarget.transName!}</h3>
        </div>
        <div class="row cl">
            <div class="col-12" style="background:#f5f5f5; margin-top:20px; ">
                <div class="row cl" style="">
                    <table class="table table-offer" style="-moz-user-select:none;font-size:14px;" onselectstart="return false">
                        <tbody>


                                <#if (((nowDate?long)-(oneTarget.priceEndDate?long)) lte 0)><#--//判断是否结束;结束时间小于现在时间对比-->

                                    <#if transUserId??><#--//判断是否登录-->

                                                <#if (resourceApply?? && resourceApply.applyStep==3) ><#--//判断是否有资格购买&& (transOfferCount gt 0)-->

                                                    <#if (((nowDate?long)-(oneTarget.priceBeginDate?long)) gt 0)  &&(((nowDate?long)-(oneTarget.priceEndDate?long)) lte 0)><#--是否在报价期,同意一次报价-->
                                                        <#if myOnePriceLog??><#--已经报价-->
                                                        <tr>
                                                            <td style="text-align: left"><h4>距报价结束</h4></td>
                                                            <td class="value" style="text-align: center">
                                                                <span class="time" id="span_${oneTarget.id!}" value="${oneTarget.priceEndDate?long}" ></span>
                                                            </td>
                                                        </tr>
                                                        <tr class="price price-disable"  >
                                                            <td style="color: #0684CE;">
                                                                报价必须大于：
                                                            </td>
                                                            <td style="text-align: right">

                                                                <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                ${oneTarget.priceMin!}
                                                                </em>
                                                            </td>

                                                        </tr>
                                                        <tr class="price price-disable" >
                                                            <td  style="color: #0684CE;" >

                                                                报价必须小于等于：
                                                            </td>
                                                            <td style="text-align: right">

                                                                <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                ${oneTarget.priceMax!}
                                                                </em>
                                                            </td>
                                                        </tr>

                                                        <tr class="price price-disable">
                                                            <td class="value" style="text-align: center" colspan="2">

                                                                <p style="line-height:80px;font-size:15pt;font-weight: bold;">您已报价成功，报价为：${myOnePriceLog.price!}万元</p>
                                                            </td>
                                                        </tr>

                                                        <#else><#--未报价；同意一次报价，不同意一次报价-->
                                                            <#if oneApply?? && oneApply.step==1>
                                                            <tr>
                                                                <td style="text-align: left"><h4>距报价结束</h4></td>
                                                                <td class="value" style="text-align: center">
                                                                    <span class="time" id="span_${oneTarget.id!}" value="${oneTarget.priceEndDate?long}" ></span>
                                                                </td>
                                                            </tr>
                                                            <tr class="price price-disable"  >
                                                                <td style="color: #0684CE;">
                                                                    报价必须大于：
                                                                </td>
                                                                <td style="text-align: right">

                                                                    <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                    ${oneTarget.priceMin!}
                                                                    </em>
                                                                </td>

                                                            </tr>
                                                            <tr class="price price-disable" >
                                                                <td  style="color: #0684CE;" >

                                                                    报价必须小于等于：
                                                                </td>
                                                                <td style="text-align: right">

                                                                    <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                    ${oneTarget.priceMax!}
                                                                    </em>
                                                                </td>
                                                            </tr>
                                                           <#-- //-->
                                                            <tr class="price price-disable">
                                                                <td>
                                                                    <p style="margin-bottom:0px">
                                                                        出&nbsp;&nbsp;&nbsp;&nbsp;价
                                                                    </p>
                                                                </td>
                                                                <td  style="color: #0684CE;">
                                                                    <table class="table table-border table-bordered table-money">
                                                                        <tr>
                                                                            <td class="text-c" >百</td>
                                                                            <td class="text-c">十</td>
                                                                            <td class="text-c">亿</td>
                                                                            <td  class="text-c">千</td>
                                                                            <td class="text-c">百</td>
                                                                            <td class="text-c">十</td>
                                                                            <td class="text-c">万</td>
                                                                        </tr>
                                                                        <tr class="one-price" >
                                                                            <td><input id="baiYi" class="input-text" type="text" min="0" max="9"  onpaste="return false" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="shiYi" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="yi" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="qianWan" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="baiWan" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="shiWan" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="wan" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                        </tr>
                                                                    </table>

                                                                </td>
                                                            </tr>
                                                            <tr class="price price-disable">
                                                                <td>
                                                                    <p style="margin-bottom:0px">
                                                                        确&nbsp;&nbsp;&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;&nbsp;&nbsp;价
                                                                    </p>
                                                                </td>
                                                                <td  style="color: #0684CE;">
                                                                    <table class="table table-border table-bordered table-money">
                                                                        <tr>
                                                                            <td class="text-c" >百</td>
                                                                            <td class="text-c">十</td>
                                                                            <td class="text-c">亿</td>
                                                                            <td  class="text-c">千</td>
                                                                            <td class="text-c">百</td>
                                                                            <td class="text-c">十</td>
                                                                            <td class="text-c">万</td>
                                                                        </tr>
                                                                        <tr class="one-price" >
                                                                            <td><input id="baiYiValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="shiYiValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="yiValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="qianWanValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="baiWanValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="shiWanValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                            <td><input id="wanValid" class="input-text" type="text" min="0" max="9" onpaste="return false;" onchange="keyPress(this)"  maxlength="1" /> </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            <tr class="price">
                                                                <td>&nbsp;&nbsp;</td>
                                                                <td class="value" style="color: #0684CE;">
                                                                    <input id="oneprice"  class="btn btn-primary size-L" type="button" value="报&nbsp;&nbsp;&nbsp;&nbsp;价" style="width: 236px;float: left"
                                                                           onclick="javascript:beginOffer();"><div id="showinfo" style="margin:0px;padding:0px;float: left;width:230px"></div>
                                                                </td>
                                                            </tr>
                                                            <#else><#--未报价；不同意一次报价-->
                                                            <tr class="price price-disable">
                                                                <td class="value" style="color: #d91615; text-align: center" colspan="2">
                                                                    <p style="line-height:80px;font-size:15pt;font-weight: bold;"> 您未同意参加一次报价，无竞买资格</p>
                                                                </td>
                                                            </tr>
                                                            </#if>

                                                        </#if>



                                                    <#else><#--//询问期-->
                                                        <#if (((nowDate?long)-(oneTarget.queryBeginDate?long)) gt 0) &&(((nowDate?long)-(oneTarget.queryEndDate?long)) lte 0)><#--是否在询问期-->
                                                        <tr>
                                                            <td style="text-align: left"><h4>距报价开始</h4></td>

                                                            <td style="text-align: center" class="value">
                                                                <span class="time" id="span_${oneTarget.id!}"  value="${oneTarget.queryEndDate?long}"></span>
                                                            </td>
                                                        </tr>
                                                        <tr class="price price-disable"  >
                                                            <td style="color: #0684CE;">
                                                                报价必须大于(万元)：
                                                            </td>
                                                            <td style="text-align: right">

                                                                <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                ${oneTarget.priceMin!}
                                                                </em>
                                                            </td>

                                                        </tr>
                                                        <tr class="price price-disable" >
                                                            <td  style="color: #0684CE;" >

                                                                报价必须小于等于(万元)：
                                                            </td>
                                                            <td style="text-align: right">

                                                                <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                ${oneTarget.priceMax!}
                                                                </em>
                                                            </td>
                                                        </tr>
                                                        <#if oneApply?? && oneApply.step==1><#--//判断是否已经参加一次报价-->
                                                        <tr class="price price-disable">
                                                            <td class="value" style="color: #d91615; text-align: center" colspan="2">
                                                                <p style="line-height:80px;font-size:15pt;font-weight: bold;"> 您已具备一次报价资格</p>
                                                            </td>
                                                        </tr>
                                                        <#else>
                                                        <tr class="price price-disable">
                                                            <td class="value" style="color: #0684CE; text-align: center" colspan="2">
                                                                <a class="btn btn-secondary size-L" style="width: 236px" onclick="applyConfirm();">参加一次报价</a>
                                                            </td>
                                                        </tr>
                                                        </#if>
                                                        <#else >
                                                            <#if (((nowDate?long)-(oneTarget.waitBeginDate?long)) gt 0) &&(((nowDate?long)-(oneTarget.waitEndDate?long)) lte 0)><#--是否在等待-->
                                                            <tr>
                                                                <td style="text-align: left"><h4>距询问开始</h4></td>
                                                                <td style="text-align: center" class="value">
                                                                    <span class="time" id="span_${oneTarget.id!}"  value="${oneTarget.waitEndDate?long}"></span>
                                                                </td>
                                                            </tr>
                                                            <tr class="price price-disable"  >
                                                                <td style="color: #0684CE;">
                                                                    报价必须大于(万元)：
                                                                </td>
                                                                <td style="text-align: right">

                                                                    <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                    ${oneTarget.priceMin!}
                                                                    </em>
                                                                </td>

                                                            </tr>
                                                            <tr class="price price-disable" >
                                                                <td  style="color: #0684CE;" >

                                                                    报价必须小于等于(万元)：
                                                                </td>
                                                                <td style="text-align: right">

                                                                    <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                                                    ${oneTarget.priceMax!}
                                                                    </em>
                                                                </td>
                                                            </tr>
                                                            <tr class="price price-disable">
                                                                <td class="value" style="color: #0684CE; text-align: center" colspan="2">
                                                                    <a class="btn btn-secondary size-L" style="width: 236px" >等待</a>
                                                                </td>
                                                            </tr>
                                                            <#else >
                                                            <tr class="price price-disable">
                                                                <td  class="value" style="color: #0684CE; text-align: center" colspan="2">
                                                                    <p style="line-height:80px;font-size:15pt;font-weight: bold;"> 不可以进行一次报价</p>
                                                                </td>
                                                            </tr>

                                                            </#if>
                                                        </#if>
                                                    </#if>
                                                <#else>
                                                <tr >
                                                    <td colspan="2" style="text-align: center">

                                                        <p style="line-height:80px;font-size:15pt;font-weight: bold;"> 您无一次报价资格</p>
                                                    </td>
                                                </tr>

                                                </#if>
                                            <#else>
                                                <a class="btn btn-secondary size-L" style="width: 236px" href="${base}/login">请登录</a>
                                            </#if>

                                <#else><#--//结束-->


                                    <#if oneTarget?? && oneTarget.successPrice?? &&(oneTarget.successPrice gt 0)><#--//结束-已经成交-->
                                        <tr class="price price-disable"  >
                                        <td style="color: #0684CE;">
                                            报价必须大于(万元)：
                                        </td>
                                        <td style="text-align: right">

                                            <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                            ${oneTarget.priceMin!}
                                            </em>
                                        </td>

                                    </tr>
                                            <tr class="price price-disable" >
                                        <td  style="color: #0684CE;" >

                                            报价必须小于等于(万元)：
                                        </td>
                                        <td style="text-align: right">

                                            <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                            ${oneTarget.priceMax!}
                                            </em>
                                        </td>
                                    </tr>
                                            <tr >

                                                <td colspan="2" style="text-align: center">

                                                    <p style="line-height:40px;font-size:12pt;font-weight: bold;">

                                                        竞得人： ${oneTarget.successUnit!}<br>
                                                        竞得价： ${oneTarget.successPrice!}万元<br>
                                                        平均价： ${oneTarget.priceAvg!}万元
                                                    </p>
                                                  <#--  <p style="font-size:12pt;font-weight: bold;"></p>
                                                    <p style="font-size:12pt;font-weight: bold;"></p>-->
                                                    <p style="font-size:12pt;">本场竞买已结束</p>
                                                </td>

                                            </tr>
                                    <#else><#--//结束-还未成交-->

                                        <tr class="price price-disable"  >
                                        <td style="color: #0684CE;">
                                            报价必须大于(万元)：
                                        </td>
                                        <td style="text-align: right">

                                            <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                            ${oneTarget.priceMin!}
                                            </em>
                                        </td>

                                    </tr>
                                        <tr class="price price-disable" >
                                        <td  style="color: #0684CE;" >

                                            报价必须小于等于(万元)：
                                        </td>
                                        <td style="text-align: right">

                                            <em class="price-font-small" id="priceInfo" style="letter-spacing:53px">
                                            ${oneTarget.priceMax!}
                                            </em>
                                        </td>
                                    </tr>
                                        <tr >

                                            <td colspan="2" style="text-align: center">

                                                <p style="line-height:80px;color:#d91615;font-size:15pt;font-weight: bold;"> 请等待成交结果</p>
                                            </td>
                                        </tr>
                                    </#if>

                                </#if>
                        </tbody>
                    </table>
                </div>
                <div class="row cl" style="padding: 5px 20px;">
                    <table class="table table-border table-bordered table-info" >
                        <tr>

                            <td>
                                <span class="title">规划用途：</span>
                                <span>${oneTarget.transUseLand!}</span>

                            </td>
                            <td>
                                <span class="title">面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积：</span>
                                <span class="current-price">${oneTarget.transArea!}&nbsp;平方米</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="title">地块位置：</span>
                                <span>
                                ${oneTarget.transAddress!}
                                </span>
                            </td>
                            <td>
                                <span class="title">中止时间：</span>
                                <span>
                                ${oneTarget.stopDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="title">等待开始时间：</span>
                                <span>
                                ${oneTarget.waitBeginDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                            <td>
                                <span class="title">等待结束时间：</span>
                                <span>
                                ${oneTarget.waitEndDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="title">询问开始时间：</span>
                                <span>
                                ${oneTarget.queryBeginDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                            <td>
                                <span class="title">询问结束时间：</span>
                                <span>
                                ${oneTarget.queryEndDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="title">报价开始时间：</span>
                                <span>
                                ${oneTarget.priceBeginDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                            <td>
                                <span class="title">报价结束时间：</span>
                                <span>
                                ${oneTarget.priceEndDate?string("yyyy-MM-dd HH:mm:ss")}
                                </span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

    </div>
    <div class="col-5 cl" style="border-left: 1px solid #e9e9e9;height: 495px;
            background: url(${base}/static/image/price-back.png) repeat-x 0 0 #fff;padding:0px 0px 0px 5px;">
        <h5 style="border-bottom: 1px solid #eeecec;line-height:40px;text-align: center;">一次报价</h5>
        <table class="table table-price">
            <thead>
                <tr>
                    <td style="width: 63%;text-align: center;">报价人</td>
                    <td style="text-align: center">报价</td>
                </tr>
            </thead>
            <tbody>

            <#if oneTarget?? && oneTarget.successPrice?? &&(oneTarget.successPrice gt 0)><#--//地块成交-->
                <#if onePriceLogList?? && (onePriceLogList?size gt 0)><#--//成交是在一次报价系统里面成交-->
                    <#list onePriceLogList as onePrice>
                        <tr style="text-align: center;">
                            <td style="width: 63%;text-align: center;">${onePrice.priceUnit!}</td>
                            <td style="text-align: center;">${onePrice.price!}</td>
                        </tr>
                    </#list>
                <#else><#--//地块成交-是在公开出让系统里面成交-->
                    <#list transResourceOfferList as transResourceOffer>
                        <#assign transOfferUser=ResultUtil.getTransUser(transResourceOffer.userId!) >
                        <#if transOfferUser??>
                            <tr>
                                <td style="width: 63%;text-align: center;">${transOfferUser.userName!}</td>
                                <td style="text-align: center;">${transResourceOffer.offerPrice!}</td>
                            </tr>
                        </#if>
                    </#list>
                </#if>
            <#else><#--//地块未成交-如果报价就显示自已的价格，没有就不显示自已的价格-->
                <#if myOnePriceLog?? ><#--//地块未成交-报价 -->
                    <tr>
                        <td style="width: 63%;text-align: center;">${myOnePriceLog.priceUnit!}</td>
                        <td style="text-align: center;">${myOnePriceLog.price!}</td>
                    </tr>
                <#else>

                </#if>

            </#if>

            </tbody>
        </table>

    </div>
</div>
</div>


<#if oneApply??>
    <input type="hidden"    name="applyId"  value="${oneApply.id!}">
    <input type="hidden"    name="transUserId" value="${transUserId!}">
    <input type="hidden"    name="price" >
    <input type="hidden"    name="priceUnit" value="${WebUtil.getLoginUserViewName()!}">
    <input type="hidden"    name="transTargetId" value="${oneTarget.transTargetId!}">
    <input type="hidden"    name="oneTargetId" value="${oneTarget.id!}">
</#if>


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

<div id="applyModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="applyModalLabel"><i class="icon-warning-sign"></i>确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content_apply">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal_apply" onclick="javascript:applyOnePrice('${oneTarget.id!}','${transUserId!}');">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<script id="tpl_beginoffer" type="text/html">
    <div style="text-align: center">
        <p style='color: #0684CE;font-size: 20px'>小写：<span id="spanOffer">{{ d.offer }}</span>万元</p>
        <p style='color: #0684CE;font-size: 20px'>大写：{{ d.offerChinese }}元</p>
    </div>
</script>
<script id="tpl_offer1" type="text/html">
    <tr>
        <td>
            <span >张三</span>
        </td>
        <td class="text-c">{{ d.offerPrice }}</td>
    </tr>
</script>
<script>
    function keyPress(obj){
        var price=$(obj).val();
        $(obj).val(price.replace(/\D/g,""));
    }
</script>
<#if WebUtil.isCaEnabled()>
<input type="hidden" id="caEnabled" value="${WebUtil.isCaEnabled()?c}">
    <@Ca autoSign=0  />
<script>
    $(function(){
        gtmapCA.initializeCertificate(document.all.caOcx);
    });
</script>
</#if>
<script type="text/javascript" src="${base}/static/js/slide.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/leaflet/leaflet.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/laytpl/laytpl.js"></script>
<script type="text/javascript" src="${base}/static/js/index.js"></script>
<script type="text/javascript" src="${base}/static/js/oneprice/view.js"></script>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>