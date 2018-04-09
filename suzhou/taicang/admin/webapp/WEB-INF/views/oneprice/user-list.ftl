<html>
<head>
    <title>人员管理</title>
    <meta name="menu" content="menu_admin_userlist"/>
    <style>
        .queryText{
            width: 200px;
        }
        .l span{
            margin-left:15px;
        }
        .table-striped th{
            text-align: center;
        }
        .table-striped td{
            text-align: center;
        }

    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/oneprice/resource/list" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">人员授权</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text"  class="input-text input-mini queryText" placeholder="请输入人员姓名" name="userAlias" value="${userAlias!}">
            <button type="submit" class="btn">查询</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped ">
    <tr>
        <th>人员姓名</th>
        <th style="width: 120px;">状态</th>
        <th style="width: 120px;">类型</th>
        <th style="width: 200px;">创建时间</th>
        <th style="width: 100px;">操作</th>
    </tr>
   <#list transUserList.items as user>
    <tr>

        <td>${user.viewName!}</td>
        <td>${user.status.title!}</td>
        <td>
            <#if user.type=='MANAGER'>
                <span>后台管理者</span>
            <#else>
                <span>前台交易用户</span>
            </#if>
        </td>
        <td>${user.createAt!}</td>
        <td style="text-align: center">
            <#if user.userId!='0'&&user.type=='MANAGER' ><a href="${base}/oneprice/user/grant?userId=${user.userId!}" class="btn btn-default size-S" >授权</a></#if>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transUserList formId="frmSearch" />
</body>
</html>