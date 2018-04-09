<html>
<head>
    <title>角色管理</title>
    <style>
        .l span,.l button{
            margin-left: 10px;
        }
        .l input{
            width: auto;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">角色管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="roleName" class="input-text input-mini" name="roleName" value="${roleName!}" placeholder="请输入角色名称">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">
            <#if Session._USER_BUTTON?seq_contains("roleAdd")>
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/role/add')">新增角色</button>
            </#if>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width:85px;">角色名称</th>
        <th style="width:85px;">角色描述</th>
        <th style="width: 80px;">创建时间</th>
        <th style="width: 220px;">操作</th>
    </tr>
    <#list transRoleList.items as role>
    <tr>
        <td>${role.roleName!}</td>
        <td>${role.roleDescribe!}</td>
        <td>${role.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td>
            <#if Session._USER_BUTTON?seq_contains("roleEdit")>
            <a href="javascript:changeSrc('${base}/role/edit?roleId=${role.roleId!}')" class="btn btn-default size-S" >编辑角色</a>
            </#if>
            <#if Session._USER_BUTTON?seq_contains("roleDelete")>
            <a href="javascript:deleteRole('${role.roleId!}')"  class="btn btn-default size-S" >删除角色</a>
            </#if>
            <#if Session._USER_BUTTON?seq_contains("roleGrant")>
            <a href="javascript:changeSrc('${base}/role/grant?roleId=${role.roleId!}')" class="btn btn-default size-S" >授权</a>
            </#if>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transRoleList formId="frmSearch" />
<script type="text/javascript">
    /*$(document).ready(function () {
        _alertResult('ajaxResultDiv', true, "操作成功！");
    });*/
    function deleteRole (roleId) {
        if (confirm('确认删除选择的角色吗？')) {
            $.ajax({
                type: "post",
                data: {roleId: roleId},
                url: "${base}/role/deleteRole",
                success: function (data) {
                    if (data.result) {
                        reloadSrc("frmSearch");
                    }
                    _alertResult('ajaxResultDiv', data.result, data.msg);
                }
            });
        }
    }
</script>
</body>
</html>