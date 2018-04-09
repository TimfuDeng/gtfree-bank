<html>
<head>
    <title>报名查询</title>
    <meta name="menu" content="menu_admin_applyresourcelist"/>

    <style>
        .caName {
            width: 300px;
        }
    </style>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">报名查询</span>

</nav>
<div class="search_bar">
    <form class="navbar-form" id="frmSearch" method="post" action="${base}/apply/index">
        <div class="l">
            <input type="text"  class="input-text"" style="width:200px"  name="title"  value="${title!}"
                   placeholder="请插入地块编号">
            <button type="button" class="btn" onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">
        </div>


    </form>
</div>
<table class="table table-border table-bordered" >
    <thead>
    <tr class="text-c" style="background-color: #f5f5f5">
        <th style="border-right: 1px solid #e8e8e8;">资源描述</th>
        <th style="width:170px;">挂牌开始时间</th>
        <th style="width:170px">挂牌结束时间</th>
        <th style="width:137px;border-right: 1px solid #e8e8e8;">已报名</th>
        <th style="width:137px">已缴纳保证金</th>
    </tr>
    </thead>
    <tbody>
    <#list transResourcePage.items as resource>
    <tr>
        <td>
            <div class="l" style="width: 400px;">
                <a href="javascript:changeSrc('${base}/resource/view?resourceId=${resource.resourceId!}')" >
                    <div>${resource.resourceCode!}
                        <@resource_edit_status resourceEditStatus=resource.resourceEditStatus resourceStatus=resource.resourceStatus/>
                    </div>
                    <div>${resource.resourceLocation!}</div>


                </a>
            </div>
        </td>
        <td>
            <p>
                <em style="font-family: verdana;font-style: normal;">${resource.gpBeginTime?string("yyyy-MM-dd HH:mm:ss")}</em>
            </p>
        </td>
        <td>
            <p>
                <em style="font-family: verdana;font-style: normal;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm:ss")}</em>
            </p>
        </td>
        <td style="text-align: center">
            <#assign baoming=ResourceUtil.getTransResourceApply(resource.resourceId,1)>
                <#assign applyBank=ResourceUtil.getTransResourceApply(resource.resourceId,2)>
                <#assign applyed=ResourceUtil.getTransResourceApply(resource.resourceId,4)>
                ${(baoming+applyBank+applyed)!}
        </td>
        <td style="text-align: center">
        ${applyed!}
        </td>
    </tr>
    </#list>
    </tbody>
</table>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
<@Ca autoSign=0  />
</body>
</html>