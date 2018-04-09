<html>
<head>
    <title>竞买人管理</title>
    <meta name="menu" content="menu_admin_userlist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            deleteUsers();
        });

        function deleteUsers () {
            $('#btnDeleteUser').click(function () {
                var userIds = getCheckedGgIds();
                if(isEmpty(userIds)){
                    alert('请选择需要删除的用户！');
                    return;
                }
                if (confirm('确认删除选择的用户吗？')) {
                    $.post('${base}/jmr/deleteJmr', {userIds:userIds}, function (data) {
                        if (data.flag) {
                            reloadSrc("frmSearch");
                        }
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    });
                }
            });
        }

        function getCheckedGgIds(){
            var userIds='';
            $('#userList input:checkbox:checked').each(function(){
                if (userIds == '')
                    userIds = $(this).val();
                else
                    userIds += ',' + $(this).val();
            });
            return userIds;
        }
    </script>

    <style>
        .queryText{
            width: 200px;
        }
        .l span{
            margin-left:15px;
        }
        .label-select{
            background-color: #d91615;
            color: #fff;
        }
        .table td{
            text-align: center;
        }
        .table th{
            text-align: center;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">竞买人管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text"  class="input-text input-mini queryText" placeholder="请输入人员姓名" name="viewName" value="${viewName!}">
            <button type="button" class="btn" onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <input type="hidden" name="userType" value="0">
        <div class="r">
        <#if Session._USER_BUTTON?seq_contains("jmrAdd")>
            <button class="btn btn-primary" type="button"  id="btnCreateUser" onclick="javascript:changeSrc('${base}/jmr/add')">新增人员</button>
        </#if>
       <#-- <#if Session._USER_BUTTON?seq_contains("userDelete")>
            <button class="btn btn-danger" type="button"  id="btnDeleteUser" >删除人员</button>
        </#if>-->
        </div>
    </form>
</div>
<table  id="userList" class="table table-hover table-striped " class="span12">
    <tr>
        <th style="width: 35px;">#</th>
        <th>人员姓名</th>
        <th style="width:100px;">登录名</th>
        <th style="width: 120px;">状态</th>
        <th style="width: 120px;">类型</th>
        <th style="width: 120px;">创建时间</th>
        <th style="width: 160px;text-align: center">操作</th>
    </tr>
<#list transUserList.items as user>
    <tr>
        <td><input type="checkbox" value="${user.userId!}"></td>
        <td>${user.viewName!}</td>
        <td>${user.userName!}</td>
        <td>
            <#if user.status=='ENABLED'>
                <span class="label label-success">${user.status.title!}</span>
            <#else>
                <span class="label label-danger">${user.status.title!}</span>
            </#if>

        </td>
        <td>
            <#if user.type=='MANAGER'>
                <span>后台管理者</span>
            <#else>
                <span>前台交易用户</span>
            </#if>

        </td>
        <td>${user.createAt!?datetime}</td>
        <td style="text-align: center">
            <#if Session._USER_BUTTON?seq_contains("jmrEdit")>
                <a href="javascript:changeSrc('${base}/jmr/edit?userId=${user.userId!}')"  class="btn btn-default size-S" >修改用户</a>
            </#if>
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=transUserList formId="frmSearch" />
</body>
</html>