<!DOCTYPE html>

<html lang="zh-CN">
<head>
    <title>首页</title>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <link rel="stylesheet" href="${base}/static/js/dist/index.css" >
</head>
<body>
<div class="wp">
    <div class="row cl" >

        <form id="frmSearch" method="get">
            <table class="table table-border table-bordered" style="margin-top:20px;background-color: #f6f6f6;">
                <tr>
                    <td  height="40" width="128" >
                        <div class="searchBar">
                            <input name="title" value="${title!}" placeholder="请输入地块编号" class="searchTxt" autocomplete="off" >
                            <input type="submit" value="搜索" class="searchBtn" >
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="row cl" style="width:1210px">
        <#list oneTargetList.items as oneTarget>
            <#if oneTarget.stopDate?? ><#--// 提前录入的最大有效报价，最小有效报价地块不显示-->
                <div class="col-3" style="padding-right:20px;padding-top:10px">
                    <a href="/client/oneprice/view?id=${oneTarget.id!}" target="frm_${oneTarget.id!}" class="link-wrap">
                        <h3 style="text-align: center;background:#3bb4f2;color:#fff; padding-top:10px; ">${oneTarget.transName!}</h3>
                        <div class="info-section">
                            <#if ((oneTarget.priceEndDate?long)-(nowDate?long)) gt 0 ><#--//报价结束时间大于现在时间-->
                                <p class="price">
                                    <span>最小报价(元):</span>
                            <span class="value" style="color: #d91615;">
                                <em class="price-font-small">
                                ${oneTarget.priceMin!}
                                </em>
                             </span>
                                </p>
                                <p class="price">
                                    <span>最大报价(元):</span>
                            <span class="value" style="color: #d91615;">
                                <em class="price-font-small">
                                ${oneTarget.priceMax!}
                                </em>
                             </span>
                                </p>
                            <#else><#--//报价结束时间过了-->
                                <p class="price">
                                    <span>最小报价(元):</span>
                            <span class="value" style="color: ##777;">
                                <em class="price-font-small">
                                ${oneTarget.priceMin!}
                                </em>
                             </span>
                                </p>
                                <p class="price">
                                    <span>最大报价(元):</span>
                            <span class="value" style="color: ##777;">
                                <em class="price-font-small">
                                ${oneTarget.priceMax!}
                                </em>
                             </span>
                                </p>
                            </#if>


                            <p class="price price-assess" style="padding-top: 12px">
                                <span>等待结束时间</span>
                            <span class="value">
                                 <span class="value"  >${oneTarget.waitEndDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                             </span>
                            </p>
                            <p class="price price-assess" style="padding-top: 0px">
                                <span>询问结束时间</span>
                            <span class="value">
                                 <span class="value"  >${oneTarget.queryEndDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                             </span>
                            </p>
                            <p class="price price-assess" style="padding-top: 0px">
                                <span>报价结束时间</span>
                            <span class="value">
                                 <span class="value"  >${oneTarget.priceEndDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                             </span>
                            </p>
                        </div>
                        <div>
                            <div class="flag">
                                <#if ((oneTarget.priceEndDate?long)-(nowDate?long)) gt 0 ><#--//报价结束时间大于现在时间-->
                                    <span class="label label-danger">一次报价</span>
                                <#else><#--//报价结束时间过了-->
                                    <span class="label label-over">已结束</span>
                                </#if>


                            </div>
                        </div>            </a>
                </div>
            </#if>
        </#list>
    </div>
</div>
<@PageHtml pageobj=oneTargetList formId="frmSearch" />
</body>
</html>