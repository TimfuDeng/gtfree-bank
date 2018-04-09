<!DOCTYPE html>

<html lang="zh-CN">
<head>
    <title>首页</title>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <link rel="stylesheet" href="${base}/static/js/dist/index.css" >
    <style type="text/css">
        .link-wrap {
            border: 1px solid #eaeaea;
            display: block;
            height: 235px;
            position: relative;
        }
        .col-3{
            width:475px;
        }
        .header-section{
            display: block;
            height: 300px;
            line-height: 180px;
            overflow: hidden;
            position: relative;
            text-align: center;
            background:url('${base}/static/image/industry.jpg')  no-repeat;
        }
        .industry{
            background:url('${base}/static/image/industry.jpg')  no-repeat;
        }
        .house{
            background:url('${base}/static/image/house.jpg')  no-repeat;
        }
        .industry:hover{
            background:url('${base}/static/image/industry_hover.jpg')  no-repeat;
        }
        .house:hover{
            background:url('${base}/static/image/house_hover.jpg')  no-repeat;
        }

    </style>
</head>
<body>
<div class="wp">

    <div class="row cl" style="width:1210px" >
        <div class="col-3" style="padding-right:20px;padding-top:10px;margin-left:100px;">
            <a href="${base}/index?regionCode=320503001" target="frm_015c9f25d0c5016609ab5c9f22060028" class="link-wrap">
                <div class="header-section industry"></div>
            </a>
        </div>
        <div class="col-3" style="padding-right:20px;padding-top:10px;margin-left:100px;">
            <a href="${base}/index?regionCode=320503002" target="frm_015c9f25d0c5016609ab5c9f22060028" class="link-wrap">
                <div class="header-section house"></div>
            </a>
        </div>
    </div>
</div>
</body>
</html>