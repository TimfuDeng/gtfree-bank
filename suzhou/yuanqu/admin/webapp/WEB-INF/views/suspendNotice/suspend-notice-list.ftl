<html>
<head>
    <title>中止公告</title>
    <meta name="menu" content="menu_admin_suspendNotice"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#btnDelete').click(function(){
                var noticeIds = getCheckedGgIds();
                if(noticeIds==null||noticeIds==''){
                    alert('请选择需要删除的中止公告！');
                    return;
                }
                if(confirm('确认删除选择的中止公告吗？')){
                    $.post('${base}/console/suspendNotice/delete',{noticeIds:noticeIds},function(data){
                        window.location.reload();
                    });
                }

            });
        });

        function getCheckedGgIds(){
            var noticeIds='';
            $('table input:checkbox:checked').each(function(){
                noticeIds += $(this).val()+';';
            });
            return noticeIds;
        }

        function revoke(noticeId){
            if(confirm('确认要撤销此中止公告吗？')){
                $.ajax({
                    url:'${base}/console/suspendNotice/revoke.f',
                    type:"post",
                    data:{noticeId:noticeId},
                    success:function(data){
                        $("#statusId_" + noticeId).html(data);
                    }
                });
            }
        }

        function deploy(noticeId){
            if(confirm('确认要发布通知吗？')){
                $.ajax({
                    url:'${base}/console/suspendNotice/deploy.f',
                    type:"post",
                    data:{noticeId:noticeId},
                    success:function(data){
                        $("#statusId_" + noticeId).html(data);
                    }
                });
            }
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
    <span class="c_gray en">&gt;</span><span class="c_gray">其他公告</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="noticeTitle" value="${noticeTitle!}" placeholder="请输入其他公告标题">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/suspendNotice/edit'">新增其他公告</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除其他公告</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>公告标题</th>
        <th>作者</th>
        <th style="width: 120px;">发布时间</th>
        <th style="width: 200px;">操作</th>
    </tr>
<#list suspendNoticeList.items as notice>
    <tr>
        <td><input type="checkbox" value="${notice.noticeId!}"></td>
        <td style="text-align: center">
            <a href="${base}/console/suspendNotice/detail?noticeId=${notice.noticeId!}">${notice.noticeTitle!}</a>
        </td>
        <td style="text-align: center">${notice.noticeAuthor}</td>
        <td style="text-align: center">${notice.deployTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center">
            <#include "../common/suspend-notice-status.ftl">
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=suspendNoticeList formId="frmSearch" />
</body>
</html>