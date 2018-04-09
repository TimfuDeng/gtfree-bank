<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>成交通知书</title>
    <meta name="menu" content="menu_client_resourcelist"/>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
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
<div class="wp">
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
                <img src="${base}/image/jj_notice_top.jpg"  height="98">
            </td>
        </tr>
        <tr>
            <td style="background: url(${base}/image/jj_notice_bg.jpg) center repeat-y;">
                <div class="apply-content" style="padding:0px 150px">
                    <p><div class="apply-title">${regionName}国有建设用地使用权网上交易成交通知书</div></p>
                    <p style=" text-indent:0em">${user.viewName!}
                    <#assign userUnionList=UserUtil.getUserUnionByApplyId(resourceApply.applyId)/>
                    <#if userUnionList??>
                        <#list userUnionList as userUnionObj>
                            ，${userUnionObj.userName!}

                        </#list>

                    </#if>
                        ：</p>
                    <p>你方于挂牌开始时间${resource.gpBeginTime?string("yyyy年MM月dd日")}至结束时间${resource.overTime?string("yyyy年MM月dd日")}，
                        参加${regionName}国有建设用地使用权网上挂牌出让，竞得${crgg.ggNum!},名称为${resource.resourceCode!}地块的国有建设用地使用权。
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
                        你方应于本宗地网上交易系统确认竞得之日起5个工作日内，持本通知书以及有效证件原件、复印件材料（具体提交材料详见竞买须知），
                        提交本中心进行资格审查，审查通过后，与本中心签订《${regionName}国有建设用地使用权网上挂牌出让成交确认书》。不按期签订成交确认书的，视为你方
                        自动放弃竞得资格及单方违约，你方交纳的地块竞买保证金不予退还，并承担其他相应的法律责任。
                    </p>
                    <p style=" text-indent:26em">    &nbsp;
                    </p>
                    <p style=" text-indent:26em">
                    ${regionName!}国土资源局
                    </p>
                    <p style=" text-indent:28em">
                    ${resource.overTime?string("yyyy年MM月dd日")}
                    </p>
                    <div style="text-align: center;">
                        <a class="btn btn-default" href="${base}/my/offersuccess/download.f?resourceId=${resource.resourceId!}" >下载</a>
                    </div>


                </div>

            </td>
        </tr>
        <tr>
            <td style="text-align: center">
                <img src="${base}/image/jj_notice_bottom.jpg" height="98">
            </td>
        </tr>
        </tbody>
    </table>
</div>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        $("iframe", window.parent.document).attr("height", document.body.scrollHeight);
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
</body>
</html>