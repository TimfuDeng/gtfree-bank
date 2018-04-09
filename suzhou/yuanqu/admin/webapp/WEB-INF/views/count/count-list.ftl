<html>
<head>
    <title>查询统计</title>
    <meta name="menu" content="menu_admin_countlist"/>
    <style>
        .l span,.l button{
            margin-left: 10px;
        }
        .l input{
            width: auto;
        }
        .table th {
            text-align: center;
        }
        .table td {
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        function importExcel(){
            window.location.href="${base}/console/count/excel.f?resourceCode="+$("#resourceCode").val()+"&&zdCode="+$("#zdCode").val()+"&&resourcePurpose="+$("#resourcePurpose").val()+"&&beginTime="+$("#beginTime").val()+"&&endTime="+$("#endTime").val();
        }


    </script>


</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">查询统计</span>
</nav>
<div  class="search_bar_count_list">
    <form class="navbar-form" id="frmSearch">
        <div class="1">
            <span>地块编号：</span>
            <input type="text"   class="input-text" style="width: auto" id="resourceCode" name="resourceCode" value="${resourceCode!}" >
            <span>宗地编号：</span>
            <input type="text"  class="input-text" style="width: auto" id="zdCode" name="zdCode" value="${zdCode!}" >
            <span>土地用途：</span>
            <select name="resourcePurpose" style="width: auto" id="resourcePurpose" class="select" >
                <option value="">不限</option>
                <option value="0" <#if resourcePurpose?? && resourcePurpose=="0" >selected="selected"</#if>>商服用地</option>
                <option value="1" <#if resourcePurpose?? && resourcePurpose=="1"  >selected="selected"</#if>>工矿仓储用地</option>
                <option value="2" <#if resourcePurpose?? && resourcePurpose=="2"  >selected="selected"</#if>>住宅用地</option>
                <option value="3" <#if resourcePurpose?? &&  resourcePurpose=="3"  >selected="selected"</#if>>公共管理与公共服务用地</option>
                <option value="4" <#if resourcePurpose?? &&  resourcePurpose=="4"  >selected="selected"</#if>>特殊用地</option>
                <option value="5" <#if resourcePurpose?? &&  resourcePurpose=="5"  >selected="selected"</#if>>交通运输用地</option>
                <option value="6" <#if resourcePurpose?? &&  resourcePurpose=="6"  >selected="selected"</#if>>水域及水利设施用地</option>
                <option value="7" <#if resourcePurpose?? && resourcePurpose=="7"  >selected="selected"</#if>>其它用地</option>
            </select>

            <br/>

            <span>成交时间：</span>
            <input type="text" id="beginTime" name="beginTime" class="input-text Wdate" style="width: auto;" value="<#if beginTime??&&beginTime?is_date>${beginTime?string("yyyy-MM-dd HH:mm:ss")!}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss'})">

            <span >-</span>
            <input type="text" id="endTime" name="endTime" class="input-text Wdate" style="width: auto;margin-left: 55px" value="<#if endTime??&&endTime?is_date>${endTime?string("yyyy-MM-dd HH:mm:ss")!}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
            <button type="submit" class="btn " style="margin-left: 65px">查询</button>

        </div>
    </form>
</div>
<button onclick="importExcel()" class="btn btn-primary">导出Excel</button>
<table   class="table table-border table-bordered" >
    <tr class="text-c" style="background-color: #f5f5f5" >
        <th > 地块编号</th>
        <th > 宗地编号</th>
        <th > 面积</th>
        <th > 土地用途</th>
        <th > 成交价格</th>
        <th > 竞得人</th>
        <th > 溢出率</th>
        <th > 成交时间</th>
    </tr>
<#list transResourceList.items as transResource>
    <#assign transResourceOffer = ResourceUtil.getMaxOfferByresourceId(transResource.resourceId)!/>
    <tr >
        <td >${transResource.resourceCode!}</td>
        <td >
            <#if transResource.transResourceSon??>
                <table style="border: 1px solid #ddd;" >
                    <#list transResource.transResourceSon as son>
                        <tr >
                            <td >
                            ${son.zdCode!}
                            </td>
                        </tr>
                    </#list>
                </table>
            </#if>
        </td>
        <td > <#if transResource.transResourceSon??>
            <table style="border: 1px solid #ddd;">
                <#list transResource.transResourceSon as son>
                    <tr >
                        <td >
                        ${son.zdArea!}平方米
                        </td>
                    </tr>
                </#list>
            </table>
        </#if></td>
        <td >${transResource.landUse!}</td>
        <td >${transResourceOffer.offerPrice!}万元</td>
        <td><@userInfo userId=transResourceOffer.userId /></td>
        <td >${((transResourceOffer.offerPrice-transResource.beginOffer)/transResource.beginOffer*100)?string("0.##")}%</td>
        <td style="text-align: center;">${(transResourceOffer.offerTime!)?number?number_to_date}</td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=transResourceList formId="frmSearch" />
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>