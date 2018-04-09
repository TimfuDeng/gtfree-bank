<html>
<head>
    <title>发布地块</title>
    <meta name="menu" content="menu_admin_resourceReleaselist"/>
    <style type="text/css">
        .col-name th {
            height: 38px;
            text-align: center;
            background: #f5f5f5;
            border-top: 1px solid #e8e8e8;
            border-bottom: 1px solid #e8e8e8;
            color: #3c3c3c;
            font: 12px/1.5 Tahoma,Helvetica,Arial,'微软雅黑 Regular',sans-serif;
        }
        .sep-row{
            height: 10px;
        }
        .sep-row td{
            border: 0;
        }
        .order-hd td{
            background: #f5f5f5;
            border-bottom-color: #f5f5f5;
            height: 40px;
            line-height: 40px;
            text-align: left;
            border: 1px solid #daf3ff;
        }
        .order-bd td{
            padding: 10px 5px;
            overflow: hidden;
            vertical-align: top;
            border-color: #e8e8e8;
            border: 1px solid #e8e8e8;
        }
        .l span{
            margin-left:15px;
        }
        .active{
            border: 1px solid #e8e8e8;
        }
        .caName {
            width: 250px;
        }
    </style>
    <script>
        $(function () {

            $('#btnReadCA').click(function () {
                gtmapCA.initializeCertificate(document.all.caOcx);
                if (gtmapCA.checkValidCertificate()) {
                    for(var i=0;i<3;i++){
                        var caName = $('#caName'+(i+1)).val();
                        if(caName==null||caName===''){
                            $('#caThumbprint'+(i+1)).val(gtmapCA.getCertificateThumbprint());
                            $('#caName'+(i+1)).val(gtmapCA.getCertificateFriendlyUser());
                            break;
                        }
                    }

                }
            });
            $('#btnClearCA').click(function(){
                $(':input','#frmCa')
                        .not(':button, :submit, :reset')
                        .val('');
            });
        });

        function viewResource(resourceId){
            $('#resourceId').val(resourceId);
            $('#frmCa').submit();
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">发布地块</span>
</nav>
<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="title" value="${title!}" placeholder="请输入地块编号">
                <input type="hidden" name="status" value="${status!}">
                <button type="submit" class="btn">查询</button>
                <span style="color: red">*请导入三个有效CA数字证书后，才能进行地块报名情况查看</span>
            </div>
        </form>
    </div>
</div>
<div class="search_bar">
    <form class="navbar-form" id="frmCa" method="post" action="${base}/console/resource/release/query">
        <div class="l">
            <input type="text" id="caName1" readonly class="input-text caName" name="caName1"
                    placeholder="请插入CA数字证书1" value="${caName1!}">
            <input type="text" id="caName2" readonly class="input-text caName" name="caName2"
                    placeholder="请插入CA数字证书2" value="${caName2!}">
            <input type="text" id="caName3" readonly class="input-text caName" name="caName3"
                    placeholder="请插入CA数字证书3" value="${caName3!}">
            <button type="button" id="btnReadCA" class="btn btn-primary">读取CA</button>
            <button type="button" id="btnClearCA" class="btn btn-danger">清空CA</button>
        </div>
        <input type="hidden" name="resourceId" id="resourceId"/>
        <input type="hidden" id="caThumbprint1"  name="caThumbprint1" value="${caThumbprint1!}"/>
        <input type="hidden" id="caThumbprint2"  name="caThumbprint2" value="${caThumbprint2!}"/>
        <input type="hidden" id="caThumbprint3"  name="caThumbprint3" value="${caThumbprint3!}"/>
    </form>
</div>
<table class="table" style="table-layout: fixed;width: 100%;border-collapse: collapse;border-spacing: 2px;border-color: gray;">
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="border-right: 1px solid #e8e8e8;">资源描述</th>
            <th style="width:155px;border-right: 1px solid #e8e8e8;">挂牌时间</th>
            <th style="width:137px;border-right: 1px solid #e8e8e8;" colspan="2">起始价（万元）</th>
        </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>

        <tr class="sep-row"><td colspan="4"></td></tr>
        <tr class="active">
            <td colspan="4" style="padding-left:5px">
                <div class="l" style="margin-top: 4px">
                    <#if resource.ggId??>
                        <#assign crgg=ResourceUtil.getCrgg(resource.ggId)/>
                        <#if crgg??>
                            ${crgg.ggNum!}
                        </#if>
                    </#if>
                    <i class="icon-th-large"></i>
                    ${resource.resourceCode!}
                </div>
                <div id="div_status_${resource.resourceId!}" style="margin-top: 2px">
                    <div class="l" id="div_status" style="margin-left:10px">
                        <@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
                    </div>
                    <div id="btn_action" class="r">
                        <button class="btn size-S btn-primary" onclick="viewResource('${resource.resourceId!}')">查看</button>
                    </div>
                </div>
            </td>
        </tr>
        <tr class="order-bd">
            <td>
                <a style="float: left">
                    <img style="width:80px;height:80px;border: 1px solid #e9e9e9;"
                         src="${base}/${ImageUtil.url(resource.resourceId,'402_320')}"  onerror="this.src='${base}/static/image/blank.jpg'" />
                </a>
                <div class="l" style="width: 400px;">
                    <a href="${base}/console/resource/view?resourceId=${resource.resourceId!}" >
                        <span>${resource.resourceLocation!}</span>
                        <br><span>保证金：人民币${resource.fixedOffer?string("0.##")}万元<#if resource.fixedOfferUsd??>，美元${resource.fixedOfferUsd?string(",##0.##")}美元</#if>&nbsp;&nbsp;
                        <br><span>面积：${resource.crArea?string("0.##")}平方米&nbsp;&nbsp;
                        ${(resource.crArea*0.0015)?string("0.##")}亩&nbsp;&nbsp;
                        ${(resource.crArea*0.0001)?string("0.##")}公顷</span>
                    </a>
                </div>
            </td>
            <td>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
                <p>
                    <em style="font-family: verdana;font-style: normal;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
                </p>
            </td>
            <td style="text-align: center" colspan="2">
                <p>
                    <em style="font-weight: 700;font-family: verdana;font-style: normal;text-align: center;color: #3c3c3c;">
                    ${resource.beginOffer!}</em>
                    <p style="margin-bottom:0px"><em>${(resource.beginOffer*10000/resource.crArea)?string("0")}元/平方米</em></p>
                    <p style="margin-bottom:0px"><em>${(resource.beginOffer/(resource.crArea*0.0015))?string("0.#")}万元/亩</em></p>
                </p>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
<@Ca autoSign=0  />
</body>
</html>