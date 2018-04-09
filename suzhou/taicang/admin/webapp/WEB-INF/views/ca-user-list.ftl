<html>
<head>
    <title>CA注册用户管理</title>
    <meta name="menu" content="menu_admin_causerlist"/>
    <script type="text/javascript">
        $(document).ready(function(){
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
                        }
                    });
                }

            });
        });

        function getCheckedGgIds(){
            var userIds='';
            $('table input:checkbox:checked').each(function(){
                userIds += $(this).val()+';';
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
            <input type="text"  class="input-text input-mini queryText" placeholder="请输入单位名或联系人" name="userName" value="${userName!}">
            <button type="submit" class="btn">查询</button>
        </div>
        <div class="l" style="margin-left:10px">
            <span>行政区：</span>
            <a href="${base}/console/ca-user/list">
                <span <#if regionCode??><#else>class="label label-select"</#if>>不限</span>
            </a>
            <#if regionAllList??>
                <#list regionAllList as regions>
                    <a href="${base}/console/ca-user/list?regionCode=${regions[0]}" style="margin-left: 20px">
                        <span <#if regionCode?? && regionCode==regions[0]>class="label label-select"</#if>>${regions[1]}</span>
                    </a>
                    <#if regionCode?? && regionCode==regions[0]>
                        <input name="regionCode" type="hidden" value="${regionCode!}" />
                    </#if>
                </#list>
            </#if>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreateUser" onclick="window.location.href='${base}/console/ca-user/edit'">新增人员</button>
            <#--<button class="btn btn-danger" type="button"  id="btnDeleteUser" >删除人员</button>-->
        </div>
    </form>
</div>
<table  class="table table-hover table-striped span12">
    <tr>
        <th style="width: 35px;text-align: center;">#</th>
        <th style="text-align: center;">单位名称</th>
        <th style="width: 120px;text-align: center;">联系人</th>
        <th style="width: 120px;text-align: center;">联系电话</th>
        <th style="width: 100px;text-align: center;">类型</th>
        <th style="width: 100px;text-align: center;">状态</th>
        <th style="width: 120px;text-align: center;">创建时间</th>
        <th style="width: 100px;text-align: center;">操作</th>
    </tr>
    <#list transCaUserList.items as user>
    <tr>
        <td style="text-align: center;"><input type="checkbox" value="${user.userId!}"></td>
        <td style="text-align: center;">${user.caName!}</td>
        <td style="text-align: center;"><a href="${base}/console/ca-user/edit?userId=${user.userId!}">${user.userName!}</a> </td>
        <td style="text-align: center;">${user.mobile!}</td>
        <td style="text-align: center;">
            <#if user.type=='COMPANY'>
                <span>企业</span>
            <#else>
                <span>自然人</span>
            </#if>
        </td>
        <td style="text-align: center;">
            <#if user.status=='ENABLED'>
                <span class="label label-success">${user.status.title!}</span>
            <#else>
                <span class="label label-danger">${user.status.title!}</span>
            </#if>

        </td>
        <td style="text-align: center;">${user.createAt!}</td>
        <td style="text-align: center">
            <a href="${base}/console/ca-user/edit?userId=${user.userId!}" class="btn btn-default size-S" >编辑</a>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transCaUserList formId="frmSearch" />
</body>
</html>