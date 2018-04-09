<html>
<head>
    <title>人员管理</title>
    <meta name="menu" content="menu_admin_userlist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            deleteUsers();
            $("#btn_modal").click(function(){
                getRole();
            });
        });

        function deleteUsers(){
            $('#btnDeleteUser').click(function(){
                var userIds = getCheckedGgIds();
                if(userIds==null||userIds==''){
                    alert('请选择需要删除的用户！');
                    return;
                }
                if(confirm('确认删除选择的用户吗？')){
                    $.post('${base}/console/user/delete.f',{userIds:userIds},function(data){
                        if(data!=null&&data=='true')
                            window.location.reload();
                        else if(data!=null&&data.hasOwnProperty("msg")){
                            alert(data.msg);
                        }else if(data!=null&&data=='false'){
                            alert('请先删除人员对应的角色');
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
        var _userId;
        //加载数据
        function chooseUser(userId){
            _userId=userId;
            $("#roleList").load('${base}/console/user/getTransUserRole.f?userId='+userId);
            showConfirm();

        }

        //得到确定的
        function getRole(){
            var roleIds='';
            $('#roleList input:checkbox:checked').each(function(){
                roleIds += $(this).val()+',';
            });
            $("#myModal").hide();

            $.post('${base}/console/user/changeTransUserRole.f',{roleIds:roleIds,userId:_userId},function(data){
                if(data!=null&&data=='true'){
                    alert("操作完成！");
                }else if(data!=null&&data=='false'){
                    alert('操作失败！');
                }
            });


        }

        function getCheckedGgIds(){
            var userIds='';
            $('#userList input:checkbox:checked').each(function(){
                userIds += $(this).val()+';';
            });
            return userIds;
        }
        function checkForm(){
            checkInputFileter();
            return true;
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
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">人员管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text"  class="input-text input-mini queryText" placeholder="请输入人员姓名" name="userAlias" value="${userAlias!}">
            <button type="submit" class="btn" onclick="return checkForm()">查询</button>
        </div>
        <input type="hidden" name="userType" value="1">
        <div class="l" style="margin-left:10px">
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
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreateUser" onclick="window.location.href='${base}/console/user/edit'">新增人员</button>
            <button class="btn btn-danger" type="button"  id="btnDeleteUser" >删除人员</button>
        </div>
    </form>
</div>
<table  id="userList" class="table table-hover table-striped " class="span12">
    <tr>
        <th style="width: 35px;">#</th>
        <th>人员姓名</th>
        <th style="width:100px;">登录名</th>
        <th style="width:100px;">机构</th>
        <th style="width: 120px;">状态</th>
        <th style="width: 120px;">类型</th>
        <th style="width: 120px;">创建时间</th>
        <th style="width: 160px;text-align: center">操作</th>
    </tr>
    <#list transUserList.items as user>
    <tr>
        <td><input type="checkbox" value="${user.userId!}"></td>
        <td><a href="${base}/console/user/edit?userId=${user.userId!}">${user.viewName!}</a> </td>
        <td>${user.userName!}</td>
        <td>
            <#assign dept=UserUtil.getDeptById(user.deptId!)!/>
            <#if dept??>
                ${dept.deptName!}
            </#if>
        </td>
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
        <td>${user.createAt!}</td>
        <td style="text-align: center">
            <#if user.userId!='0'&&user.type=='MANAGER'>
               <#-- <a href="${base}/console/user/grant?userId=${user.userId!}" class="btn btn-default size-S" >授权</a>-->
                <a href="javascript:chooseUser('${user.userId!}')"  class="btn btn-default size-S" >添加角色</a>

            </#if>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transUserList formId="frmSearch" />
<div id="myModal" class="modal hide fade" style="margin-left: -400px;width: 800px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel">角色添加</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
    <#include "common/role-list.ftl" >
    </div>
    <div class="modal-footer"  >
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>



<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>