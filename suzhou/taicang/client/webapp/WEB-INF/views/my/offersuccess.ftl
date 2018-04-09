<@Head_SiteMash title="成交通知书" />
    <style type="text/css">
        .apply-content{
            padding:0px 100px;
        }
        .apply-title{
            text-align: center;font-weight: 700;font-size: 18px;
            margin-bottom: 10px;
        }
        .apply-content p{
            margin-bottom: 0px;
            font-size: 16px;
            line-height:29px;
            text-indent:2em
        }
        .apply-sucess{
            margin-top:10px;
            margin-bottom: 10px;
            font-size: 16px;
            line-height:29px;
            font-weight: 700;font-size: 16px;
            text-indent:0em
        }
    </style>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <div class="row cl" >
        <div class="col-2" style="padding-right: 10px">
            <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
                <li role="presentation" class="active">
                    <a href="${base}/my/resource-list">
                        <i class="icon-th-large"></i>&nbsp;&nbsp;我的交易
                    <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                    <#if applyCount gt 0>
                        <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                    </#if>
                    </a>
                </li>
                <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息</a></li>
            </ul>
        </div>
        <div class="col-10">
            <nav class="breadcrumb pngfix">
                <i class="iconfont">&#xf012b;</i>
                <a href="${base}/index" class="maincolor">首页</a>
                <span class="c_gray en">&gt;</span><a href="${base}/my/resource-list">我的竞拍地块</a>
                <span class="c_gray en">&gt;</span><span class="c_gray">成交通知书</span>
            </nav>

            <div class="row" style="border: 1px solid #eeecec;">
                <table align="center" style="width: 989px" >
                    <tbody>
                    <tr>
                        <td style="text-align: center">
                            <img src="${base}/static/image/jj_notice_top.jpg"  height="98">
                        </td>
                    </tr>
                    <tr>
                        <td style="background: url(${base}/static/image/jj_notice_bg.jpg) center repeat-y;">
                            <div class="apply-content" style="padding:0px 150px">
                                <p><div class="apply-title">${RegionUtil.getDefaultRegionName()}国有建设用地使用权网上挂牌出让成交通知书</div></p>
                                <p style=" text-indent:0em">${user.viewName!}：</p>
                                <p>在${resource.gpBeginTime?string("yyyy年MM月dd日")}太仓市国有建设用地使用权网上挂牌出让活动中，你公司竞得编号为${resource.resourceCode!}地块的国有建设用地使用权。
                                    现将相关成交事项通知如下：

                                <p style=" text-indent:2em">
                                <#if offer.offerType==2>
                                    本宗地成交总价为人民币大写<span id="price" value="${offerPrice.offerPrice*10000}"></span>元整（小写${offerPrice.offerPrice*10000}元）。
                                    竞得人另须配建<#if resource.publicHouse?? && resource.publicHouse==1 >资金<#else>面积</#if>${offer.offerPrice!} <#if resource.publicHouse?? && resource.publicHouse==1 >万元<#else>平方米</#if>的公共租屋。
                                <#else>
                                    本宗地成交总价为人民币大写<span id="price" value="${offerPrice.offerPrice*10000}"></span>元整（小写${offerPrice.offerPrice*10000}元）。
                                    <#if resource.beginHouse?? && resource.beginHouse gt 0>竞得人另须配建<#if resource.publicHouse?? && resource.publicHouse==1 >资金<#else>面积</#if>${resource.beginHouse!} <#if resource.publicHouse?? && resource.publicHouse==1 >万元<#else>平方米</#if>的公共租屋。</#if>
                                </#if>
                                </p>
                                <p style=" text-indent:2em">
                                    你公司应于成交之日起5个工作日内，持本通知书和以下材料原件及复印件提交我局605室进行资格复核：（1）国有建设用地使用权网上挂牌出让竞买申请书，联合竞买的需提交联合协议；（2）公司营业执照副本；（3）法定代表人身份证；（4）若法定代表人委托代理人代办的，必须出具授权委托书及受托人的身份证。
                                </p>

                                <p style=" text-indent:2em">
                                    审查通过后，与我局签订《${RegionUtil.getDefaultRegionName()}国有建设用地使用权网上挂牌出让成交确认书》。不按期签订成交确认书的，视为你方自动放弃竞得资格及单方违约，你方交纳的地块竞买保证金不予退还，并承担其他相应的法律责任。
                                </p>
                                <p style=" text-indent:26em">    &nbsp;
                                </p>
                                <p style=" text-indent:28em">
                                ${RegionUtil.getDefaultRegionName()}国土资源局
                                </p>
                                <p style=" text-indent:28em">
                                ${resource.overTime?string("yyyy年MM月dd日")}
                                </p>
                                <div style="text-align: center;">
                                    <a class="btn btn-default" href="${base}/my/offersuccess/download.f?resourceId=${resource.resourceId!}" >打印</a>
                                </div>


                            </div>

                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: center">
                            <img src="${base}/static/image/jj_notice_bottom.jpg" height="98">
                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <script type="text/javascript">

                $(document).ready(function () {
                    var value=  $("#price").attr("value");
                    $("#price").html(Chinese(value));

                });

                function Chinese(num) {
                    if (!/^\d*(\.\d*)?$/.test(num)) {
                        alert("你输入的不是数字，请重新输入!");
                        return false;
                    }
                    var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
                    var BB = new Array("", "拾", "佰", "仟", "万", "亿", "点", "");
                    var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = "";
                    for (var i = a[0].length - 1; i >= 0; i--) {
                        switch (k) {
                            case 0 :
                                re = BB[7] + re;
                                break;
                            case 4 :
                                if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0]))
                                    re = BB[4] + re;
                                break;
                            case 8 :
                                re = BB[5] + re;
                                BB[7] = BB[5];
                                k = 0;
                                break;
                        }
                        if (k % 4 == 2 && a[0].charAt(i) == "0" && a[0].charAt(i + 2) != "0") re = AA[0] + re;
                        if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re;
                        k++;
                    }
                    if (a.length > 1) {
                        re += BB[6];
                        for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)];
                    }
                    return re;
                }

            </script>
        </div>
    </div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>