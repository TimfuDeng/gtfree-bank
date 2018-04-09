<html>
<head>
    <title>-出让公告</title>
    <meta name="menu" content="menu_admin_landjs"/>
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

    </style>
</head>
<body>

<ul class="breadcrumb">
    <li><a href="${base}/admin/landjs">江苏土地市场网</a> <span class="divider">/</span></li>
    <li class="active">${transCrgg.ggNum!}</li>
</ul>

<div class="title-info">
    <h1>${transCrgg.ggTitle!}</h1>
    <h3>${transCrgg.ggNum!}</h3>

    <span class="title-triangle"></span>
</div>
<div class="job-main main-message ">
    <p class="release-time">开始时间：<em>${transCrgg.ggBeginTime?string("yyyy-MM-dd")}</em>   截止时间：<em>${transCrgg.ggEndTime?string("yyyy-MM-dd")}</em></p>
</div>
<div class="job-main main-message ">
    <h3>公告信息：</h3>
    <div class="content content-word">
    ${transCrgg.ggContent!}
    </div>
</div>
</body>
</html>