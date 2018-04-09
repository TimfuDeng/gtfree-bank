<html>
<head>
    <title>中止(终止)公告</title>
    <meta name="menu" content="menu_admin_suspendNotice"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#btnDelete').click(function(){
                var noticeIds = getCheckedGgIds();
                if(noticeIds==null||noticeIds==''){
                    alert('请选择需要删除的中止(终止)公告！');
                    return;
                }
                if(confirm('确认删除选择的中止(终止)公告吗？')){
                    $.post('${base}/suspendNotice/deleteSuspend', {noticeIds: noticeIds}, function (data) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        reloadSrc('frmSearch');
                    });
                }

            });
        });

        function getCheckedGgIds () {
            var noticeIds = '';
            $('table input:checkbox:checked').each(function(){
                noticeIds += $(this).val() + ',';
            });
            return noticeIds;
        }

        function revoke (noticeId) {
            if(confirm('确认要撤销此中止(终止)公告吗？')){
                $.ajax({
                    url: '${base}/suspendNotice/revoke',
                    type: "post",
                    data: {noticeId: noticeId},
                    success: function (data) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        reloadSrc('frmSearch');
                    }
                });
            }
        }

        function deploy (noticeId) {
            if(confirm('确认要发布通知吗？')){
                $.ajax({
                    url: '${base}/suspendNotice/deploy',
                    type: "post",
                    data: {noticeId: noticeId},
                    success: function (data) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        reloadSrc('frmSearch');
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
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">中止(终止)公告</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="noticeTitle" value="${noticeTitle!}" placeholder="请输入中止(终止)公告标题">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/suspendNotice/add')">新增中止(终止)公告</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除中止(终止)公告</button>
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
<#list transSuspendNoticeList.items as notice>
    <tr>
        <td><input type="checkbox" value="${notice.ggId!}"></td>
        <td style="text-align: center">
            <a href="javascript:changeSrc('${base}/suspendNotice/detail', {noticeId: '${notice.ggId!}'})">${notice.ggTitle!}</a>
        </td>
        <td style="text-align: center">${notice.passMan}</td>
        <td style="text-align: center">${notice.postDate?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center">
            <div id="statusId_${notice.ggId!}">
                <#if notice.crggStauts==1>
                    <input type="button" onclick="revoke('${notice.ggId!}')" class="btn btn-primary size-S" value="撤回"/>
                <#else>
                    <a href="javascript:changeSrc('${base}/suspendNotice/edit', {noticeId: '${notice.ggId!}'})"  class="btn size-S btn-default" >编辑</a>
                    <input class="btn size-S btn-primary" type="button" onclick="deploy('${notice.ggId!}')"  value="发布"/>
                </#if>
            </div>
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=transSuspendNoticeList formId="frmSearch" />
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
</body>
</html>