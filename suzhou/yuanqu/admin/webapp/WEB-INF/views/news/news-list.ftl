<html>
<head>
    <title>新闻管理</title>
    <meta name="menu" content="menu_admin_newslist"/>
    <script type="text/javascript">
        var _resourceId,_status;
        $(document).ready(function(){

            $('#btn_modal').click(function(){
                setAndReloadStatus();
            });

            $('#btnDelete').click(function(){
                var newsIds = getCheckedNewsIds();
                if(newsIds==null||newsIds==''){
                    alert('请选择需要删除的新闻！');
                    return;
                }
                if(confirm('确认删除选择的新闻吗？')){
                    $.post('${base}/console/news/delete.f',{newsIds:newsIds},function(data){
                        if(data!=null&&data=='true')
                            window.location.reload();
                    });
                }

            });
        });




        /**
         * 用于新闻删除
         * @returns {string}
         */
        function getCheckedNewsIds(){
            var newsIds='';
            $('table input:checkbox:checked').each(function(){
                newsIds += $(this).val()+';';
            });
            return newsIds;
        }

        function PreRelease(newsId){
            _newsId=newsId;_status=1;
            showConfirm("是否确认申请发布！",newsId,1);
        }
        function BackEdit(newsId){
            _newsId=newsId;_status=0;
            showConfirm("是否确认返回编辑！",newsId,0);
        }

        function showConfirm(content){
            $('#modal_content').html(content);
            $('#myModal').modal({
                backdrop: false
            });
        }

        function setAndReloadStatus(){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/news/status/change.f",{"newsId":_newsId,"status":_status},
                    function(data){
                        $('#myModal').modal('hide');
                        if(data.ret!=null&&data.ret==true)
                            $("#div_status_" + _newsId).load("${base}/console/news/status/view.f?newsId="+_newsId );
                        else
                            alert(data.msg);
                    },'json'
            );
        }


        function showLandMarketList(){
            layer.load(1, {
                shade: [0.1,'#fff']
            });
            window.location.href='${base}/console/landMarket/crgg-list';
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
        .table th{
            text-align: center;
        }
        .table td{
            text-align: center;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">新闻管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入新闻主标题">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/news/edit'">新增新闻</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除新闻</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>新闻主标题</th>
        <th style="width:185px;">新闻副标题</th>
        <th style="width: 120px;">作者</th>
        <th style="width: 120px;">发布时间</th>
        <th style="width: 120px;">操作</th>
    </tr>

<#list transNewsPage.items as news>
    <tr>
        <td><input type="checkbox" value="${news.newsId!}"></td>
        <td><a href="${base}/console/news/view?newsId=${news.newsId!}">${news.newsTitle!}</a> </td>
        <td>${news.newsSubHead!}</td>
        <td>${news.newsAuthor!}</td>
        <td>${news.newsReportTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center;width: 150px">
            <#include "../common/news-status.ftl">


        </td>
    </tr>
</#list>

</table>
<@PageHtml pageobj=transNewsPage formId="frmSearch" />
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel"><i class="icon-warning-sign"></i> 确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>