<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/html5.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/respond.min.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/PIE_IE678.js"></script>

<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>


<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<div>

<#if oneTarget?? >
    <div id="oneTargetDiv">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">网上竞价中止价格：<span id='priceMin' class='c1'>${(oneTarget.priceMin/10000)!}万元</span></font>
    </div>
</#if>

<#if oneTarget?? >
    <div id="priceMinDiv">
        <dd ><font color="red"> 网上竞价中止价格：<span id='priceMinSpan'>${(oneTarget.priceMin/10000)!}万元</span></font></dd>
    </div>
</#if>



<#if onePriceLogList?? && onePriceLogList.items?size gt 0 >
    <div id="onePriceLogListDiv">

        <form class="navbar-form" id="frmSearch" acton="jsyd.szgtj.gov.cn/admin/trans/onePriceLog.f">
            <input type="hidden" name="transTargetId" value="${transTargetId!}">
        </form>
        <table width='98%' class="i-grid-default mt10 " id="onePriceLogTable" >
            <thead>
            <tr  >
                <td  colspan="3" style="text-align: center" class="f15 h_bk s_rb tr pr5" >一次报价记录</th>
            </tr>
            </thead>
            <tr  >
                <td  class="f15 s_b pl5"  style="text-align: center;border-left: 1px solid #ddd">竞买人</th>
                <td class="f15 s_b pl5"  style="text-align: center;border-left: 1px solid #ddd" >报价(元)</th>
                <td class="f15 s_b pl5"  style="text-align: center;border-left: 1px solid #ddd" >报价时间</th>
            </tr>

            <#list onePriceLogList.items as onePriceLog>
                <tr >
                    <td  class="f15 s_b pl5" style="text-align: center;border-left: 1px solid #ddd" >
                    ${onePriceLog.priceUnit!}
                    </td>
                    <td  class="f15 s_b pl5" style="text-align: center;border-left: 1px solid #ddd" >
                    ${onePriceLog.price!}
                    </td>
                    <td  class="f15 s_b pl5" style="text-align: center;border-left: 1px solid #ddd" >
                    ${onePriceLog.priceDate?string("yyyy-MM-dd HH:mm:ss")}
                    </td>

                </tr>
            </#list>

            <tr class="text-c">
                <td colspan="2" class="f15 s_b pl5" style="text-align: center;border-left: 1px solid #ddd" >平均价(元)：</td>
                <td  class="f15 s_b pl5" style="text-align: center;border-left: 1px solid #ddd" >${avgPrice!}</td>
            </tr>
        </table>
        <div id="onePriceLogPageDiv" class="tc page_list mt20 pb20">


            <#if onePriceLogList.isFirst()>
                <a   href="#">&laquo;</a>
            <#else>
                <a  href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=1')" title="第一页">&laquo;</a>
            </#if>

            <#if onePriceLogList.index-2 &gt; 0>
                <a   href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=${onePriceLogList.index-2}')">${onePriceLogList.index-2}</a>
            </#if>

            <#if onePriceLogList.index-1 &gt; 0>
                <a   href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=${onePriceLogList.index-1}')">${onePriceLogList.index-1}</a>
            </#if>

            <a   href="#" class="active">${onePriceLogList.index}</a>

            <#if onePriceLogList.index+1<=onePriceLogList.pageCount>

                <a   href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=${onePriceLogList.index+1}')">${onePriceLogList.index+1}</a>
            </#if>

            <#if onePriceLogList.index+2<=onePriceLogList.pageCount>
                <a  href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=${onePriceLogList.index+2}')">${onePriceLogList.index+2}</a>
            </#if>

            <#if onePriceLogList.index==1 && onePriceLogList.index+3<=onePriceLogList.pageCount>
                <a  href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=${onePriceLogList.index+3}')">${onePriceLogList.index+3}</a></li>
            </#if>

            <#if onePriceLogList.isLast()>
                <a   href="#">&raquo;</a>
            <#else>
                <a   href="javascript:SubmitPageForm('/onePriceLog?transTargetId=${transTargetId!}&index=${onePriceLogList.pageCount}')" title="最后一页">&raquo;</a>
            </#if>


        </div>

    </div>
</#if>


</div>

<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/layer1.8/layer.min.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/laypage/laypage.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/xdate/xdate.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
<script type="text/javascript" src="${base}/static/js/ajaxDoResult.js"></script>
