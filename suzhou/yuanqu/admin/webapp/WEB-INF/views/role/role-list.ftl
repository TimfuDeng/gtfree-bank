<html>
<head>
    <title>角色管理</title>
    <meta name="menu" content="menu_admin_rolelist"/>
    <style type="text/css">
        .table th{
            text-align: center;
        }
        .table td{
            text-align: center;
        }
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

        td input {
            padding-right: 0px;
        }

        .btn-upload {
            position: relative;
            display: inline-block;
            overflow: hidden;
            vertical-align: middle;
            cursor: pointer;
            height: 60px !important;
        }

        .upload-url {
            width: 200px;
            cursor: pointer;
        }

        .error_input{
            background-color: #FFFFCC;
        }

        .input-file {
            position: absolute;
            right: 0;
            top: 0;
            cursor: pointer;
            font-size: 17px;
            opacity: 0;
            filter: alpha(opacity=0)
        }

        .uploadifive-button {
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        .btn-upload input {
            height: 65px;
            cursor: pointer;
        }

        .fileUploader {
            margin-left: 10px;
            clear: both;
        }

        #attachments {
            width: 500px;
            float: left;
            overflow: hidden;
            text-overflow-ellipsis: '..';
        }

        #attachmentsOperation {
            float: right
        }
        .upload-progress-bar{
            display: none;
            width: 400px;
        }

        .paddingUserList{
            padding: 5px;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function(){
            deleteRoles();
            $("#btn_modal").click(function(){
                getUser();
            });

        });
        function deleteRoles(){
            $('#btnDelete').click(function(){
                var roleIds = getCheckedRoleIds();
                if(roleIds==null||roleIds==''){
                    alert('请选择需要删除的角色！');
                    return;
                }
                if(confirm('确认删除选择的角色吗？')){
                    $.post('${base}/console/role/delete.f',{roleIds:roleIds},function(data){
                        if(data!=null&&data=='true'){
                            window.location.reload();
                        }else if(data!=null&&data=='false'){
                            alert('请先删除角色对应的人员');
                        }

                    });
                }

            });
        }

        function showConfirm(){

            $('#myModal').modal({
                backdrop: false
            });
        }
        var _roleId;
        //加载数据
        function chooseRoles(roleId){
            _roleId=roleId;
            $("#userList").load('${base}/console/role/getTransUserRole.f?roleId='+roleId);
            showConfirm();

        }

        //得到确定的
        function getUser(){
            var userIds='';
            $('#userList input:checkbox:checked').each(function(){
                userIds += $(this).val()+',';
            });
            $("#myModal").hide();
            $.post('${base}/console/role/changeTransUserRole.f',{userIds:userIds,roleId:_roleId},function(data){
                if(data!=null&&data=='true'){
                   alert("操作完成！");
                }else if(data!=null&&data=='false'){
                    alert('操作失败！');
                }
            });
        }

        /**
         * 用于删除
         * @returns {string}
         */
        function getCheckedRoleIds(){
            var roleIds='';
            $('#roleList input:checkbox:checked').each(function(){
                roleIds += $(this).val()+';';
            });
            return roleIds;
        }

        function checkForm(){
            checkInputFileter();
            return true;
        }

    </script>
    <style>
        #title{
            width: 200px;
        }
        table th{
            text-align: center;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">角色管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入角色名称">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/role/edit'">新增角色</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除角色</button>
        </div>
    </form>
</div>
<table id="roleList" class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>角色编号</th>
        <th style="width:180px;">角色名称</th>
        <th style="width: 300px;">备注信息</th>
        <th style="width: 300px;">操作</th>
    </tr>

<#list transRolePage.items as role>
    <tr>
        <td><input type="checkbox" value="${role.roleId!}"></td>
        <td>${role.roleNo!}</td>
        <td><a href="${base}/console/role/view?roleId=${role.roleId!}">${role.roleName!}</a></td>
        <td>${role.roleComment!}</td>
        <td style="text-align: center;width: 150px">
            <a href="${base}/console/role/edit?roleId=${role.roleId!}" class="btn btn-default size-S" >编辑角色</a>
            <a href="${base}/console/role/grant?roleId=${role.roleId!}" class="btn btn-default size-S" >授权</a>
            <a href="javascript:chooseRoles('${role.roleId!}')"  class="btn btn-default size-S" >添加人员</a>
        </td>
    </tr>
</#list>

</table>
<@PageHtml pageobj=transRolePage formId="frmSearch" />
<div id="myModal" class="modal hide fade" style="margin-left: -400px;width: 800px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel">人员添加</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
    <#include "../common/user-list.ftl" >
    </div>
    <div class="modal-footer"  >
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>



<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>