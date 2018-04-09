<html>
<head>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
    <style>
        .table_list {
            BACKGROUND-COLOR: rgb(255,255,255); WIDTH: 760px; BORDER-TOP: #c7d9e7 1px solid; BORDER-RIGHT: #c7d9e7 1px solid
        }
        .table_list auto{
            BACKGROUND-COLOR: rgb(255,255,255); BORDER-TOP: #c7d9e7 1px solid; BORDER-RIGHT: #c7d9e7 1px solid
        }
        .table_list TD {
            BORDER-BOTTOM: #c7d9e7 1px solid; BORDER-right: #c7d9e7 1px solid; BORDER-top: #c7d9e7 1px solid;BORDER-LEFT: #c7d9e7 1px solid;  PADDING-BOTTOM: 0px;  PADDING-LEFT: 6px;  PADDING-RIGHT: 6px;  HEIGHT: 28px;  COLOR: #333;  PADDING-TOP: 0px
        }

        .table_list TD A {
            COLOR: #ff0000; TEXT-DECORATION: underline
        }
        .table_list TH {
            BORDER-BOTTOM: #c7d9e7 1px solid; BORDER-LEFT: #c7d9e7 1px solid; PADDING-BOTTOM: 0px; PADDING-LEFT: 6px; PADDING-RIGHT: 6px; WHITE-SPACE: nowrap; HEIGHT: 29px; FONT-SIZE: 12px; BORDER-RIGHT: #fff 1px solid; PADDING-TOP: 0px
        }
        .table_list TH SPAN {
            COLOR: #3f6617; FONT-SIZE: 14px; CURSOR: pointer; FONT-WEIGHT: bold
        }
        .table_list .td_center {
            TEXT-ALIGN: center
        }
        .contentRowLight {
            BACKGROUND-COLOR: #ffffff; HEIGHT: 25px; COLOR: #000000; FONT-SIZE: 12px
        }
        .label_td {
            TEXT-ALIGN: left; BACKGROUND-COLOR: #f2f6f9; FONT-SIZE: 12px
        }
        .label {
            TEXT-ALIGN: left; COLOR: #000000; FONT-SIZE: 12px;
        }
        .label_tr {
            TEXT-ALIGN: center; BACKGROUND-COLOR: #f2f6f9; FONT-SIZE: 12px; FONT-WEIGHT: bold
        }
        .button_style {
            /*background:url(/wxmh/skins/images/button_bg.gif) repeat-x;*/
            height:24px;
            padding:0 5px;
            cursor:hand;
            line-height:20px;
            vertical-align:middle;
            font-weight:bold;
            border:1px solid #9dabb6;
        }
    </style>
</head>
<body>
<div id="resource">
    <!--检索栏-->
    <!--结果-->
    <div style="text-align: center;padding-top: 30px">
        <table class="table_list" cellspacing="1" cellpadding="1" align="center" style="table-layout:fixed">
            <tr class='contentRowLight'>
                <th style="text-align: center;"> 地块编号</th>
                <th style="text-align: center"> 起始价</th>
                <th style="text-align: center;"> 成交时间</th>
                <th style="width:30%;text-align: center;"> 竞得人</th>
                <th style="text-align: center;"> 成交价</th>

            </tr>
        <#list transResourceList.items as transResource>
            <#assign transResourceOffer = ResourceUtil.getMaxOfferByresourceId(transResource.resourceId)!/>
            <tr class='contentRowLight'>
                <td style="width:180px;text-align: center;overflow:hidden;white-space:nowrap;"><a target="_blank" href="/view/resource/front?id=${transResource.resourceId}">${transResource.resourceCode!}</a></td>
                <td style="text-align: center;">${transResource.beginOffer!}万元</td>
                <td style="text-align: center;">${(transResourceOffer.offerTime!)?number?number_to_date}</td>
                <td style="width:225px;text-align: center;overflow:hidden;white-space:nowrap;" name="jdr"><@userInfo userId=transResourceOffer.userId! /></td>
                <td style="text-align: center;">${transResourceOffer.offerPrice!}万元</td>
            </tr>
        </#list>
        </table>
    </div>

    <div style="text-align: center;">
    <@PageHtml pageobj=transResourceList formId="frmSearch" />
    </div>
</div>
</body>
</html>
