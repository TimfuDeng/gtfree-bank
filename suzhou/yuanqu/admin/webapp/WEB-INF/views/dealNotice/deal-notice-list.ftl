<html>
<head>
    <title>成交公示</title>
    <meta name="menu" content="menu_admin_dealNotice"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#btnDelete').click(function(){
                var noticeIds = getCheckedGgIds();
                if(noticeIds==null||noticeIds==''){
                    alert('请选择需要删除的成交公示！');
                    return;
                }
                if(confirm('确认删除选择的成交公示吗？')){
                    $.post('${base}/console/dealNotice/delete',{noticeIds:noticeIds},function(data){
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

        function reportDealNoticeList(){
            layer.load(1,{
                shade: [0.1,'#fff']
            });
            var noticeIds=getCheckedGgIds();
            if(noticeIds==null||noticeIds==''){
                alert('请选择需要上传的成交公示！');
                return;
            }
            if(confirm('确认上传选择的成交公示吗？')){
                $.post('${base}/console/landMarket/dealnotice-report.f',{noticeIds:noticeIds},function(data){
                    if(data!=null&&data=='true'){
                        alert('上传成交公示成功！');
                        window.location.reload();
                    }else{
                        alert('上传成交公示失败！');
                    }

                });
            }
        }

        function revoke(noticeId){
            if(confirm('确认要撤销此成交公示吗？')){
                $.ajax({
                    url:'${base}/console/dealNotice/revoke.f',
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
                    url:'${base}/console/dealNotice/deploy.f',
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
    <span class="c_gray en">&gt;</span><span class="c_gray">成交公示</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="noticeTitle" value="${noticeTitle!}" placeholder="请输入成交公示标题">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <#--<button class="btn btn-primary" type="button"  id="btnImport" onclick="reportDealNoticeList();">上报成交公示</button>-->
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/dealNotice/edit'">新增成交公示</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除成交公示</button>
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
<#list dealNoticeList.items as notice>
    <tr>
        <td><input type="checkbox" value="${notice.noticeId!}"></td>
        <td style="text-align: center">
            <a href="${base}/console/dealNotice/detail?noticeId=${notice.noticeId!}">${notice.noticeTitle!}</a>
        </td>
        <td style="text-align: center">${notice.noticeAuthor!}</td>
        <td style="text-align: center">${notice.deployTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center">
            <#include "../common/deal-notice-status.ftl">
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=dealNoticeList formId="frmSearch" />
</body>
</html>