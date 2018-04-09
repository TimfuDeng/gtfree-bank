<html>
<head>
    <title>竞买人列表</title>
    <meta name="menu" content="menu_admin_resourcelist"/>
    <style type="text/css">
        .offer-h1{
            padding-left:200px;
            color:#ba2f2f;
        }

    </style>

</head>
<body>

<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/resource/list" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/oneprice/resource/list">出让地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人报价列表</span>
</nav>

<#if oneTarget??>
      <#if oneTarget.priceEndDate?? && (((oneTarget.priceEndDate?string("yyyy-MM-dd HH:mm:ss"))?date("yyyy-MM-dd HH:mm:ss"))
      lt ((nowDate?string("yyyy-MM-dd HH:mm:ss"))? date("yyyy-MM-dd HH:mm:ss")))>
            <a href="/admin/oneprice/offerlist-excel.f?id=${oneTarget.transTargetId!}" class="btn btn-primary">导出Excel</a>
            <table class="table table-border table-bordered table-striped table-bg ">
              <thead>
              <tr class="text-c">
                  <td colspan="4" class="text-c">${oneTarget.transName!}</td>
              </tr>
              <tr class="text-c">
                  <th>竞买人</th>
                  <th style="width:260px;">报价时间</th>
                  <th style="width:260px">报价(元)</th>
                  <th style="width:260px">（报价减去平均价）差值(元)</th>
              </tr>
              </thead>
              <tbody>
                  <#list onePriceLogList as onePriceLog>
                  <tr class="order-bd">
                      <td class="text-c">
                      ${onePriceLog.priceUnit!}
                      </td>
                      <td class="offerText text-c">
                      ${onePriceLog.priceDate?string("yyyy-MM-dd HH:mm:ss")}
                      </td>
                      <td class="text-c">
                      ${onePriceLog.price!}
                      </td>
                      <td class="text-c">
                      ${onePriceLog.price-avgPrice}
                      </td>
                  </tr>
                  </#list>
                  <tr class="text-c">
                      <td colspan="2" class="text-c">合计(元)：</td>
                      <td colspan="2" class="text-c">${totalPrice!}</td>
                  </tr>
                  <tr class="text-c">
                      <td colspan="2" class="text-c">平均价(元)：</td>
                      <td colspan="2" class="text-c">${avgPrice!}</td>
                  </tr>
              </tbody>
          </table>
            <#else>
                <p>
                    <h1 class="offer-h1">一次报价还未结束，请耐心等待结果！</h1>
                </p>
      </#if>
      <#else>
      <p >
      <h1 class="offer-h1" >此地块还未进行过一次报价，暂无结果！</h1>
      </p>
</#if>



</body>
</html>