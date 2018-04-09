<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <style type="text/css">
        .apply-content p{
            margin-bottom: 0px;
            font-size: 16px;
            line-height:29px;
            text-indent:2em
        }
        .span-info{
            color: #990000;
            font-weight: 700;
        }
    </style>
    <script type="application/javascript">

    </script>
</head>
<body>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/resource/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><a href="${base}/resource/view?resourceId=${transResource.resourceId!}">${transResource.resourceCode!}</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">交纳保证金</span>
    </nav>
    <div class="row" style="text-align: center;font-weight:700;font-size: 22px;margin-top:10px;height: 200px;line-height: 150px;">
        您的竞买信息已提交，请等待审核...
    </div>
    <div class="row" style="margin: 5px 100px;text-align: center">
        <a class="btn btn-default" href="${base}/my/resource/index">返回地块</a>
    </div>
</div>
</body>
</html>