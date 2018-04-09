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
            <li role="presentation" class=""><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息
            <#assign applyedCount=ResourceUtil.getIsApplyedCountByStauts()>
            <#if applyedCount gt 0>
                <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyedCount}</span>
            </#if>
            </a></li>
        </ul>
    </div>
    <div class="col-10">
        <nav class="breadcrumb pngfix">
            <i class="iconfont">&#xf012b;</i>
            <a href="${base}/index" class="maincolor">首页</a>
            <span class="c_gray en">&gt;</span><a href="${base}/my/resource-list">我的竞拍地块</a>

        <#if resource.regionCode=="320503001">
            <span class="c_gray en">&gt;</span><span class="c_gray">成交通知书</span>

        <#else>
            <span class="c_gray en">&gt;</span><span class="c_gray">竞得通知书</span>

        </#if>
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
                        <#if resource.regionCode=="320503001">

                            <p><div class="apply-title">${RegionUtil.getDefaultRegionName()}国有建设用地使用权网上挂牌出让成交通知书</div></p>
                        <#else>

                            <p><div class="apply-title">${RegionUtil.getDefaultRegionName()}国有建设用地使用权网上挂牌出让成交通知书</div></p>
                        </#if>

                            <p style=" text-indent:0em">${user.viewName!}：</p>
                        <#if resource.regionCode=="320503001">
                            <p>你方于&nbsp;<span style="text-decoration:underline">${resource.overTime?string("yyyy年MM月dd日")}</span>&nbsp;竞得了<span style="text-decoration:underline">${resource.resourceCode!}</span>号地块的国有建设用地使用权。
                                请你方自竞得之日起5个工作日内持本通知书与出让人签订《国有建设用地使用权网上挂牌出让成交确认书》（以下简称《成交确认书》），
                                并与出让公告中明确的园区管委会授权单位签订《苏州工业园区产业发展协议》（以下简称《产业发展协议》）。未按期签订《成交确认书》和《产业发展协议》的，视为你方主动放弃竞得资格及单方违约，
                                我局有权没收你方所支付的保证金，并保留要求你方赔偿该地块组织网上挂牌活动支出的全部费用的权利。
                            </p>
                            <p style=" text-indent:2em">
                                成交确认书签订联系电话：0512-66680873；

                            </p>
                            <p style=" text-indent:2em">
                                地址：苏州工业园区现代大道999号现代大厦8楼。

                            </p>
                            <p style=" text-indent:2em">
                                特此通知。

                            </p>
                        <#else>
                            <#if resourceInfo??&&resourceInfo.industryType??>
                                    <p>你方参加${RegionUtil.getDefaultRegionName()}国有建设用地使用权网上挂牌出让，于&nbsp;<span style="text-decoration:underline">${resource.overTime?string("yyyy年MM月dd日")}</span>&nbsp;<#--竞得<span style="text-decoration:underline">${crgg.ggNum!}</span>号地块的国有建设用地使用权-->
                                        竞得&nbsp;<span style="text-decoration:underline">${resource.resourceCode!}</span>&nbsp;地块的国有建设用地使用权。 现将相关事项通知如下：
                                    </p>
                                    <p style=" text-indent:2em">
                                        本宗国有建设用地使用权出让价款为人民币大写<span id="price" value="${offerPrice.offerPrice*10000}"></span>元整（小写${offerPrice.offerPrice*10000!}元）。

                                    </p>
                                    <p style=" text-indent:2em">

                                        你方应于本宗地网上出让系统确认成交之日起2个工作日内，持本通知书以及相关有效证件、文件的原件和复印件（具体提交材料详见出让须知），提交苏州工业园区土地储备中心进行竞买资格审查，审查通过后，当日与我局签订《网上挂牌出让成交确认书》；在签订成交确认书后2个工作日内，提交与苏州工业园区管委会授权部门签订的《产业发展协议》，并于提交当日与我局签订《国有建设用地使用权出让合同》。逾期未提交相关资料进行资格审查，或者未按期签订《网上挂牌出让成交确认书》、《产业发展协议》及《国有建设用地使用权出让合同》的，视为你方主动放弃竞得资格及单方违约，你方缴纳的地块竞买保证金不予退还，我局有权将本标的再行出让，并保留向你方索赔的权利。
                                    </p>
                                    <p style="text-indent:2em">
                                    <#--联系地址：${address!}-->
                                        联系地址：苏州工业园区现代大道999号现代大厦5楼
                                    </p>
                                    <p style="text-indent:2em">
                                    <#--联系单位：${regionCode!}-->
                                        联系单位：苏州工业园区土地储备中心

                                    </p>
                                    <p style="text-indent:2em">
                                    <#--联系电话：${phone!}-->
                                        联系电话：0512-66680507
                                    </p>
                                    <p style=" text-indent:26em">    &nbsp;
                                    </p>

                            <#else>
                                    <p>你方参加${RegionUtil.getDefaultRegionName()}国有建设用地使用权网上挂牌出让，于&nbsp;<span style="text-decoration:underline">${resource.overTime?string("yyyy年MM月dd日")}</span>&nbsp;<#--竞得<span style="text-decoration:underline">${crgg.ggNum!}</span>号地块的国有建设用地使用权-->
                                        竞得&nbsp;<span style="text-decoration:underline">${resource.resourceCode!}</span>&nbsp;地块的国有建设用地使用权。 现将相关事项通知如下：
                                    </p>
                                    <p style=" text-indent:2em">
                                        <#if offer.offerType==2>
                                            本宗国有建设用地使用权出让价款为人民币大写<span id="price" value="${offerPrice.offerPrice*10000}"></span>元整（小写${offerPrice.offerPrice!}元），
                                            竞得人须配建<#if resource.publicHouse?? && resource.publicHouse==1 >资金<#else>面积</#if>${offer.offerPrice!} <#if resource.publicHouse?? && resource.publicHouse==1 >万元<#else>平方米</#if>的公共租屋。
                                        <#else>
                                            本宗地竞得总价为人民币大写<span id="price" value="${offerPrice.offerPrice*10000}"></span>元整（小写${offerPrice.offerPrice*10000!}元）。
                                        </#if>
                                    </p>
                                    <p style=" text-indent:2em">

                                        你方应于本宗地网上出让系统确认成交之日起2个工作日内，持本通知书以及相关有效证件、文件的原件和复印件（具体提交材料详见出让须知），提交苏州工业园区土地储备中心进行竞买资格审查，审查通过后，当日与我局签订《国有建设用地使用权网上挂牌出让成交确认书》及《国有建设用地使用权出让合同》。逾期未提交相关资料进行资格审查或未按期签订成交确认书及出让合同的，视为你方主动放弃竞得资格及单方违约，你方缴纳的地块竞买保证金不予退还，我局有权将本标的再行出让，并保留向你方索赔的权利。

                                    </p>
                                    <p style="text-indent:2em">
                                    <#--联系地址：${address!}-->
                                        联系地址：苏州工业园区现代大道999号现代大厦5楼
                                    </p>
                                    <p style="text-indent:2em">
                                    <#--联系单位：${regionCode!}-->
                                        联系单位：苏州工业园区土地储备中心

                                    </p>
                                    <p style="text-indent:2em">
                                    <#--联系电话：${phone!}-->
                                        联系电话：0512-66680507
                                    </p>
                                    <p style=" text-indent:26em">    &nbsp;
                                    </p>

                            </#if>

                        </#if>
                            <p style=" text-indent:26em">
                                苏州工业园区国土环保局
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
                if(undefined!=$("#price").attr("value")){
                    var value=  $("#price").attr("value");
                    $("#price").html(Chinese(value));
                }


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