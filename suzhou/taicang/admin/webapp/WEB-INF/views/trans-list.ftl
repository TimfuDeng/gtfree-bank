<html>
<head>
    <title>出让资源</title>
    <meta name="menu" content="menu_admin_translist"/>
    <style type="text/css">
        /* 表单条件搜索  */
        .leftRight_border {
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
        }

        .padding_left10 {
            padding: 0px 0px 0px 10px;
        }

        .label-select {
            background-color: #d91615;
            color: #fff;
        }

        .red {
            color: #cc0000;
        }

        .blod {
            font-weight: bold;
        }

        .a_style {
            text-decoration: underline;
            color: #f4523a;
        }

        .order-bd td {
            padding: 10px 5px;
            overflow: hidden;
            vertical-align: top;
            border-color: #e8e8e8;
            border: 1px solid #e8e8e8;
        }
        .l span{
            margin-left:15px;
        }
        .border_style {
            border: 1px solid #ddd;

        }
        .label-select{
            background-color: #d91615;
            color: #fff;
        }
        .active{
            border: 1px solid #e8e8e8;
        }
    </style>
    <script type="text/javascript">
        function updateDisplayStatus(resourceId,value){
            var url = '${base}/console/trans/list/status.f';
            var btnElement = $(this);
            $.post(url,{displayStatus:value,resourceId:resourceId},function(data){
                if(data=='true'){
                    if(value==0){
                        btnElement.attr('value','取消推送');
                    }else{
                        btnElement.attr('value','推送到大屏');
                    }
                }
            })
        }
    </script>
</head>
<body>
<!-- 表单条件搜索 -->
<div class="search_bar leftRight_border padding_left10">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="title" value="" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="-1">
                <button type="submit" class="btn">查询</button>
            </div>
            <div class="l" style="margin-left:10px">

                <span>推送状态：</span>
                <a href="${base}/console/trans/list">
                    <span class="${(displayStatus==-1)?string('label label-select','')}">不限</span>
                </a>
                <a href="${base}/console/trans/list?displayStatus=0">
                    <span class="${(displayStatus==0)?string('label label-select','')}">未推送</span>
                </a>
                <a  href="${base}/console/trans/list?displayStatus=1">
                    <span class="${(displayStatus==1)?string('label label-select','')}">已推送</span>
                </a>

            </div>
            <div class="r">
                <#--<a class="btn btn-primary" type="button"  id="btnShowTrans" target="_blank" href="${base}/console/trans/view">显示大屏</a>-->
            </div>
        </form>
    </div>
</div>
<!--/表单条件搜索  -->

<!-- 表单部分 -->
<table class="table"
       style="table-layout: fixed;width: 100%;border-collapse: collapse;border-spacing: 2px;border-color: gray;">
    <thead>
    <tr class="text-c" style="background-color: #f5f5f5">
        <th style="width:50%; border-right: 1px solid #e8e8e8;">资源描述</th>
        <th style="width:30%;border-right: 1px solid #e8e8e8;">交易情况</th>
        <th style="width:;"></th>
    </tr>
    </thead>
    <tbody>
    <#list transList.items as transItem>
    <tr class="sep-row">
        <td colspan="3"></td>
    </tr>
    <tr class="active border_style">
        <td colspan="3" style="padding-left:5px">
            <div class="l" style="margin-top: 4px">
                <#if transItem.ggId??>
                    <#assign crgg=ResourceUtil.getCrgg( transItem.ggId)/>
                    <#if crgg??>
                    ${crgg.ggNum!}
                    </#if>
                </#if>
                <i class="icon-th-large"></i>
                ${transItem.resourceCode!}
            </div>
            <div id="div_status_${transItem.resourceId!}" style="margin-top: 2px ">
                <div class="l" id="div_status" style="margin-left:10px">
                    <#if transItem.resourceEditStatus==2>
                        <span class="label label-success">已发布</span>
                    <#else>
                        <span class="label label-primary">已结束</span>
                    </#if>

                </div>


                <div class="r">
                     <#if transItem.displayStatus==0>
                            <input type="button" onclick="window.location.href='${base}/console/trans/list/status?resourceId=${transItem.resourceId!}&displayStatus=1'" value="推送到大屏" class="btn size-S btn-success">
                         <#else>
                            <input type="button" onclick="window.location.href='${base}/console/trans/list/status?resourceId=${transItem.resourceId!}&displayStatus=0'" value="取消推送" class="btn size-S btn-danger">
                     </#if>
                </div>

                <div id="btn_action" class="r">
                </div>
            </div>
        </td>
    </tr>
    <tr class="order-bd">
        <td colspan="3">
            <a style="float: left">
                <img style="width:148px;height:126px;border: 1px solid #e9e9e9;" src="${base}/${ImageUtil.url(transItem.resourceId,'402_320')}" onerror="this.src='${base}/static/image/blank.jpg'">
            </a>

            <div class="l" style="padding:0px 0px 0px 10px;">
                <a href="${base}/console/resource/view?resourceId=${transItem.resourceId!}" target="_blank">
                    <span>${transItem.resourceLocation!}</span>
                    <br><span>保证金：${transItem.fixedOffer!}&nbsp;&nbsp;
                        <br><span>面积：${transItem.crArea?string("0.##")}平方米&nbsp;&nbsp;
                    ${(transItem.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                    ${(transItem.crArea*0.0001)?string("0.##")}公顷</span>
                    </span></a>
            </div>
        </td>

        <#--<td valign="top">-->
            <#--<span>当前最高价：<font class="red blod"></font>万元</span>-->
            <#--</br>-->
            <#--<span>累计报价：<a href="#" class="a_style"></a>次</span>-->
            <#--</br>-->
            <#--<span>起始价：${transItem.beginOffer!}万元  ${(transItem.beginOffer*10000/transItem.crArea)?string("0")}元/平方米 ${(transItem.beginOffer/(transItem.crArea*0.0015))?string("0.#")}万元/亩</span>-->
            <#--</br>-->
            <#--<span>加价幅度： ¥${transItem.addOffer!} 万元</span>-->
        <#--</td>-->
        <#--<td valign="top">-->
            <#--<span>开始时间：</span>-->
            <#--</br>-->
            <#--<span></span>-->
            <#--</br>-->
            <#--<span>距离结束：</span>-->
            <#--</br>-->
            <#--<span class="red blod"></span>-->
        <#--</td>-->
    </tr>

    <tr class="sep-row">
        <td colspan="3"></td>
    </tr>
    </#list>






    </tbody>
</table>
<@PageHtml pageobj=transList formId="frmSearch" />
<!--/表单部分  -->
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>