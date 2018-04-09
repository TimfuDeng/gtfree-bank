<html>
<head>
    <title>-底价录入</title>
    <meta name="menu" content="menu_admin_priceresourcelist"/>
    <style type="text/css">
        .title-info {
            width: 100%;
            height: 90px;
            position: relative;
            top: 18px;
            padding-right: 10px;
        }
        .title-info h1, .title-info h3 {
            margin: 0;
            padding: 0 0 0 20px;
            font-family: "Microsoft Yahei";
            font-weight: 400;
        }
        .btn-upload{ position: relative;display: inline-block;overflow: hidden; vertical-align: middle; cursor: pointer;}
        .upload-url{ width: 200px;cursor: pointer;}
        .input-file{ position:absolute; right:0; top:0; cursor: pointer; font-size:17px;opacity:0;filter: alpha(opacity=0)}
        .uploadifive-button{
            display: block;
            width: 60px;
            height: 60px;
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }
        .btn-upload input{
            height:55px;
            cursor: pointer;
        }
        .image{
            padding-right:10px;
            float: left;
        }
        .unit_span{
            font-size:10px;
            font-style:italic;
            color: #f4523b;
        }
        .minOfferInfo{
            font-size:10px;
            font-style:italic;
            color: #f4523b;
        }
        .unit_house{
            font-size:10px;
            font-style:italic;
            color: #f4523b;
        }
        .error_input{
            background-color: #FFFFCC;
        }
    </style>
    <#--<link href="${base}/static/thridparty/uploadifive/uploadifive.css" rel="stylesheet" type="text/css" />-->
    <script type="text/javascript" src="${base}/static/thridparty/uploadifive/jquery.uploadifive.min.js"></script>
    <script type="text/javascript">
           function checkForm(){
               return true;
           }

    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/price/list">底价管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> 底价录入</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>

<form class="form-base" method="post" action="${base}/console/price/save">
    <table class="table table-border table-bordered table-striped">

    <tr>
        <td width="180"><label class="control-label">地块编号：</label></td>
        <td colspan="3">${transResource.resourceCode!}</td>
    </tr>
    <tr>
        <td><label class="control-label">地块座落：</label></td>
        <td colspan="3">${transResource.resourceLocation!}</td>
    </tr>

    <tr>
        <td><label class="control-label">保证金（万元）：</label></td>
        <td>${transResource.fixedOffer!}</td>
        <td><label class="control-label">出让面积（平方米）：</label></td>
        <td>${transResource.crArea!}</td>
    </tr>
    <tr>
        <td><label class="control-label">起始价<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
        <td>${transResource.beginOffer!}</td>
        <td><label class="control-label">增价幅度<span class="unit_span"><@unitText value=transResource.offerUnit! /></span>：</label></td>
        <td>${transResource.addOffer!}</td>
    </tr>



    <tr>
        <td><label class="control-label">挂牌开始时间：</label></td>
        <td>${transResource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td><label class="control-label">挂牌结束时间：</label></td>
        <td>${transResource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
    <tr>
        <td><label class="control-label">底价(万元)：</label></td>
        <td colspan="3"><input type="input" class="input-text" name="price" value="${minPrice.price!}"></td>

    </tr>
    </table>

    <input type="hidden" name="resourceId" value="${resourceId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/price/list'">返回</button>
        </div>
    </div>
</form>
</body>
</html>