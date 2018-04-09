<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>电子政务平台</title>

    <link rel="stylesheet" href="${base}/static/css/landsale.css" >
</head>
<body>
<div style="margin-top: 20px">
    <div class="colBox colBoxNo1" >
        <div class="info" style="background-color:#ff9900">
            <i class="iconfont" style="color: #fff">&#xf00da;</i>
            <div class="info-title">正在公告和挂牌</div>
        </div>
        <div class="info-txt">当前共计：<span>${(countGongGao?default(0))+(countGuaPai?default(0))}</span>件

        </div>
    </div>
    <div class="colBox colBoxNo2">
        <div class="info" style="background-color:#7ab942">
            <i class="iconfont"  style="color: #fff">&#xf00b1;</i>
            <div class="info-title">已成交</div>

        </div>
        <div class="info-txt">已成交：<span>${countChengjiao?default(0)}</span>件</div>
    </div>
    <div class="colBox colBoxNo3">
        <div class="info" style="background-color:#446aad">
            <i class="iconfont"  style="color: #fff">&#xf0035;</i>
            <div class="info-title">成交金额</div>

        </div>
        <div class="info-txt">成交金额：<span>${sumOfDeal?default(0)}</span>万元</div>
    </div>
    <#---->
    <#--<dl class="modList">-->
        <#--<dt>保证金明细</dt>-->
        <#--<dd>保证金流水及明细账目查询<span><a>查看</a></span></dd>-->
    <#--</dl>-->
    <#--<dl class="modList">-->
        <#--<dt>保证金明细</dt>-->
        <#--<dd>保证金流水及明细账目查询<span><a>查看</a></span></dd>-->
    <#--</dl>-->
    <#--<dl class="modList">-->
        <#--<dt>保证金明细</dt>-->
        <#--<dd>保证金流水及明细账目查询<span><a>查看</a></span></dd>-->
    <#--</dl>-->
    <#--<dl class="modList">-->
        <#--<dt>保证金明细</dt>-->
        <#--<dd>保证金流水及明细账目查询<span><a>查看</a></span></dd>-->
    <#--</dl>-->
</div>
<script>
    $(function(){
        $(".modList").mouseover(function(){
            $(this).css('background-color','#f5f5f5')
        })
        $(".modList").mouseout(function(){
            $(this).css('background-color','#fff')
        })
        $(".colBox").mouseover(function(){
            $(this).css('background-color','#fafafa')
        })
        $(".colBox").mouseout(function(){
            $(this).css('background-color','#fff')
        })

        $(".colBoxNo1").click(function(){
            window.location.href="${base}/console/resource/list?status=2";
        });
        $(".colBoxNo2").click(function(){
            window.location.href="${base}/console/resource/list?status=9";
        });
        $(".colBoxNo3").click(function(){
            window.location.href="${base}/console/money/list";
        });
    });


</script>
</body>
</html>