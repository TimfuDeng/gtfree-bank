<html>
<head>
    <title>CA注册管理</title>
    <meta name="menu" content="menu_admin_userlist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            deleteUsers();
            /* $("#btn_modal").click(function(){
                 getRole();
             });*/
        });

        function deleteUsers () {
            $('#btnDeleteUser').click(function () {
                var userIds = getCheckedGgIds();
                if(isEmpty(userIds)){
                    alert('请选择需要删除的用户！');
                    return;
                }
                if (confirm('确认删除选择的用户吗？')) {
                    $.post('${base}/user/deleteUser', {userIds:userIds}, function (data) {
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
    <span class="c_gray en">&gt;</span><span class="c_gray">CA注册管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text"  class="input-text input-mini queryText" placeholder="请输入人员姓名" name="contactUser" value="${contactUser!}">
            <button type="button" class="btn" onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <#--<input type="hidden" name="userType" value="0">-->
    <#--<div class="l" style="margin-left:10px">
        <span>行政区：</span>
        <a href="${base}/console/user/list">
            <span <#if regionCode??><#else>class="label label-select"</#if>>不限</span>
        </a>
        <#if regionAllList??>
            <#list regionAllList as regions>
                <a href="${base}/console/user/list?regionCode=${regions[0]}" style="margin-left: 20px">
                    <span <#if regionCode?? && regionCode==regions[0]>class="label label-select"</#if>>${regions[1]}</span>
                </a>
            </#list>
        </#if>
    </div>-->
        <div class="r">
        <#if Session._USER_BUTTON?seq_contains("caAdd")>
            <button class="btn btn-primary" type="button"  id="btnCreateUser" onclick="javascript:changeSrc('${base}/ca-user/add')">新增人员</button>
        </#if>
        </div>
    </form>
</div>
<table  id="userList" class="table table-hover table-striped " class="span12">
    <tr>
        <th style="width: 35px;">#</th>
        <th>联系人</th>
        <th style="width:100px;">联系电话</th>
        <th style="width: 120px;">类型</th>
        <th style="width: 120px;">状态</th>
        <th style="width: 120px;">创建时间</th>
        <th style="width: 160px;text-align: center">操作</th>
    </tr>
<#list transCaRegisterList.items as user>
    <tr>
        <td><input type="checkbox" value="${user.userId!}"></td>
        <td>${user.contactUser!}</td>
        <td>${user.contactPhone!}</td>
        <td>
            <#if user.registerType=='COMPANY'>
                <span>企业</span>
            <#else>
                <span>自然人</span>
            </#if>

        </td>
        <td>
            <#if user.registerStatus=='ENABLED'>
                <span class="label label-success">${user.registerStatus.title!}</span>
            <#else>
                <span class="label label-danger">${user.registerStatus.title!}</span>
            </#if>

        </td>
        <td>${user.registerTime!?datetime}</td>
        <td style="text-align: center">
        <#--<#if &lt;#&ndash;Session._REGION_BUTTON?seq_contains(user.region) && &ndash;&gt;Session._USER_BUTTON?seq_contains("userEdit") && user.userId != '0' && user.type == 'MANAGER'>-->
        <#--&lt;#&ndash; <a href="${base}/console/user/grant?userId=${user.userId!}" class="btn btn-default size-S" >授权</a>&ndash;&gt;-->
        <#--<a href="javascript:changeSrc('${base}/user/jmr/edit?userId=${user.userId!}')"  class="btn btn-default size-S" >修改用户</a>-->
        <#--</#if>-->
            <#if Session._USER_BUTTON?seq_contains("caEdit")>
                <a href="javascript:changeSrc('${base}/ca-user/edit?registerId=${user.registerId!}')"  class="btn btn-default size-S" >修改用户</a>
            </#if>
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=transCaRegisterList formId="frmSearch" />
</body>
</html>