<html>
<head>
    <title>通知</title>
    <meta name="menu" content="menu_admin_notificationlist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#btnDelete').click(function(){
                var noteIds = getCheckedGgIds();
                if(noteIds==null||noteIds==''){
                    alert('请选择需要删除的通知！');
                    return;
                }
                if(confirm('确认删除选择的通知吗？')){
                    $.post('${base}/console/notification/delete',{noteIds:noteIds},function(data){
                            window.location.reload();
                    });
                }

            });
        });

        function getCheckedGgIds(){
            var noteIds='';
            $('table input:checkbox:checked').each(function(){
                noteIds += $(this).val()+';';
            });
            return noteIds;
        }

        function showLandMarketList(){
            layer.load(1, {
                shade: [0.1,'#fff']
            });
            window.location.href='${base}/console/landMarket/crgg-list';
        }

        function revoke(noteId){
            if(confirm('确认要撤销通知吗？')){
                $.ajax({
                    url:'${base}/console/notification/revoke',
                    type:"post",
                    data:{noteId:noteId},
                    dataType:"json",
                    success:function(){
                        $("#statusId_" + noteId).load('${base}/console/notification/status/view.f?noteId='+noteId);
                    }
                });
            }
        }

        function deploy(noteId){
            if(confirm('确认要发布通知吗？')){
                $.ajax({
                    url:'${base}/console/notification/deploy',
                    type:"post",
                    dataType:"json",
                    data:{noteId:noteId},
                    success:function(){
                        $("#statusId_" + noteId).load('${base}/console/notification/status/view.f?noteId='+noteId);
                    }
                });
            }
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
    <span class="c_gray en">&gt;</span><span class="c_gray">通知</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="noteTitle" value="${title!}" placeholder="请输入通知标题">
            <button type="submit" class="btn ">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/notification/edit'">新增通知</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除通知</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>通知标题</th>
        <th>作者</th>
        <th style="width: 120px;">发布时间</th>
        <#--<th style="width: 100px;">发布状态</th>-->
        <th style="width: 200px;">操作</th>
    </tr>
<#list transNoteList.items as note>
    <tr>
        <td><input type="checkbox" value="${note.noteId!}"></td>
        <td style="text-align: center">
            <a href="${base}/console/notification/detail?noteId=${note.noteId!}">${note.noteTitle!}</a>
        </td>
        <td style="text-align: center">${note.noteCreater}</td>
        <td style="text-align: center">${note.noteTime?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td style="text-align: center">
            <#include "../common/note-status.ftl">
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=transNoteList formId="frmSearch" />
</body>
</html>