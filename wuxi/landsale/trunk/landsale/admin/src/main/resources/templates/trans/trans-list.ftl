<html>
<head>
    <title>出让资源</title>
    <meta name="menu" content="menu_admin_transindex"/>
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

        .l span {
            margin-left: 15px;
        }

        .border_style {
            border: 1px solid #ddd;

        }

        .label-select {
            background-color: #d91615;
            color: #fff;
        }

        .active {
            border: 1px solid #e8e8e8;
        }
    </style>
    <script type="text/javascript">
        function updateDisplayStatus(resourceId, value, ele) {
            var url = '${base}/trans/index/status';
            var btnElement = $(ele);
            $.post(url, {displayStatus: value, resourceId: resourceId}, function (data) {
                _alertResult('ajaxResultDiv', data.flag, data.message);
                if (data.flag) {
                    $("#div_status_" + resourceId).load("${base}/trans/status/view?resourceId=" + resourceId);
                    /*if (value == 0) {
                        btnElement.val('推送到大屏');

                    } else {
                        btnElement.val('取消推送');
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    }*/
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
                <input type="text" class="input-text" style="width:200px" name="title" value="${title!}" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="-1">
                <button type="button" class="btn" onclick="reloadSrc('frmSearch')">查询</button>
            </div>
            <div class="l" style="margin-left:10px">

                <span>推送状态：</span>
                <a href="javascript:changeSrc('${base}/trans/index')">
                    <span class="${(displayStatus==-1)?string('label label-select','')}">不限</span>
                </a>
                <a href="javascript:changeSrc('${base}/trans/index?displayStatus=0')">
                    <span class="${(displayStatus==0)?string('label label-select','')}">未推送</span>
                </a>
                <a href="javascript:changeSrc('${base}/trans/index?displayStatus=1')">
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
            <div style="margin-top: 2px ">
                <div class="l" id="div_status" style="margin-left:10px">
                    <#if transItem.resourceEditStatus==2>
                        <span class="label label-success">已发布</span>
                    <#else>
                        <span class="label label-primary">已结束</span>
                    </#if>

                </div>
                <div class="r" id="div_status_${transItem.resourceId!}">
                    <#if transItem.displayStatus==0>
                        <input type="button"
                               onclick="javascript:updateDisplayStatus('${transItem.resourceId!}','1', this);"
                               value="推送到大屏" class="btn size-S btn-success"/>
                    <#else>
                        <input type="button"
                               onclick="javascript:updateDisplayStatus('${transItem.resourceId!}','0', this);"
                               value="取消推送" class="btn size-S btn-danger"/>
                    </#if>
                </div>

                <div id="btn_action" class="r">
                </div>
            </div>
        </td>
    </tr>
    <tr class="order-bd">
        <td colspan="3">
            <#--<a style="float: left">-->
                <#--<img style="width:148px;height:126px;border: 1px solid #e9e9e9;"-->
                     <#--src="${base}/${ImageUtil.url(transItem.resourceId,'402_320')}"-->
                     <#--onerror="this.src='${base}/static/image/blank.jpg'">-->
            <#--</a>-->

            <div class="l" style="padding:0px 0px 0px 10px;">
                <#--<a href="${base}/resource/view?resourceId=${transItem.resourceId!}" target="_blank">-->
                <a href="javascript:changeSrc('${base}/resource/view?resourceId=${transItem.resourceId!}')" target="_blank">
                    <span>${transItem.resourceLocation!}</span>
                    <br><span>保证金：${transItem.fixedOffer!}&nbsp;&nbsp;
                        <br><span>面积：${transItem.crArea?string("0.##")}平方米&nbsp;&nbsp;
                    ${(transItem.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                    ${(transItem.crArea*0.0001)?string("0.##")}公顷</span>
                    </span>
                </a>
            </div>
        </td>

    </tr>

    <tr class="sep-row">
        <td colspan="3"></td>
    </tr>
    </#list>

    </tbody>
</table>
<@PageHtml pageobj=transList formId="frmSearch" />
<!--/表单部分  -->
</body>
</html>