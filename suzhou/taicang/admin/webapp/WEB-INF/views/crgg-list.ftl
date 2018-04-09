<html>
<head>
    <title>出让公告</title>
    <meta name="menu" content="menu_admin_crgglist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#btnDelete').click(function(){
                var ggIds = getCheckedGgIds();
                if(ggIds==null||ggIds==''){
                    alert('请选择需要删除的出让公告！');
                    return;
                }
                if(confirm('确认删除选择的出让公告吗？')){
                    $.post('${base}/console/crgg/delete.f',{ggIds:ggIds},function(data){
                        if(data!=null&&data=='true')
                            window.location.reload();
                    });
                }

            });
        });

        function getCheckedGgIds(){
            var ggIds='';
            $('table input:checkbox:checked').each(function(){
                ggIds += $(this).val()+';';
            });
            return ggIds;
        }

        function showLandMarketList(){
            layer.load(1, {
                shade: [0.1,'#fff']
            });
            window.location.href='${base}/console/landMarket/crgg-list';
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
    <span class="c_gray en">&gt;</span><span class="c_gray">出让公告</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入公告标题">
            <button type="submit" class="btn ">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnImport" onclick="showLandMarketList();">导入公告</button>
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/crgg/edit'">新增公告</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除公告</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>公告标题</th>
        <th style="width:185px;">公告编号</th>
        <th style="width: 120px;">开始时间</th>
        <th style="width: 120px;">结束时间</th>
        <th style="width: 120px;">操作</th>
    </tr>
    <#list transCrggList.items as crgg>
    <tr>
        <td><input type="checkbox" value="${crgg.ggId!}"></td>
        <td><a href="${base}/console/crgg/edit?ggId=${crgg.ggId!}">${crgg.ggTitle!}</a> </td>
        <td>${crgg.ggNum!}</td>
        <td>${crgg.ggBeginTime?string("yyyy-MM-dd HH:mm")}</td>
        <td>${crgg.ggEndTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center;width: 150px">
            <a href="${base}/console/resource/edit?ggId=${crgg.ggId!}" class="btn btn-default size-S" >新增地块</a>
            <a href="${base}/console/resource/list?ggId=${crgg.ggId!}" class="btn btn-default size-S" >查看地块</a>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transCrggList formId="frmSearch" />
</body>
</html>