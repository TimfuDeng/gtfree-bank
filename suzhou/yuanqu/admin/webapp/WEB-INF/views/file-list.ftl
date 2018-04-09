<html>
<head>
    <title>资料管理</title>
    <meta name="menu" content="menu_admin_filelist"/>
    <script type="text/javascript">
        var _resourceId,_status;
        $(document).ready(function(){

            $('#btnDelete').click(function(){
                var materialIds = getCheckedMaterialIds();
                if(materialIds==null||materialIds==''){
                    alert('请选择需要删除的资料！');
                    return;
                }
                if(confirm('确认删除选择的资料吗？')){
                    $.post('${base}/file/remove.f',{fileIds:materialIds},function(data){
                        if(data!=null&&data=='true')
                            window.location.reload();
                    });
                }

            });
        });




        /**
         * 用于材料删除
         * @returns {string}
         */
        function getCheckedMaterialIds(){
            var materialIds='';
            $('table input:checkbox:checked').each(function(){
                materialIds += $(this).val()+';';
            });
            return materialIds;
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
    <span class="c_gray en">&gt;</span><span class="c_gray">资料管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入名称">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/file/edit'">新增资料</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除资料</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>编号</th>
        <th style="width:185px;">名称</th>
        <th style="width: 300px;">描述</th>
        <th style="width: 120px;">时间</th>
        <th style="width: 120px;">操作</th>
    </tr>

<#list transFilePage.items as filePage>
    <tr>
        <td><input type="checkbox" value="${filePage.fileId!}"></td>
        <td>${filePage.fileNo!}</td>
        <td>${filePage.fileName!}</td>
        <td>${filePage.description!}</td>
        <td>${filePage.createAt?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center;width: 150px">
            <a href="${base}/console/file/edit?fileId=${filePage.fileId!}" class="btn btn-secondary size-S">编辑</a>
            <#if filePage.storePath?? >
            <a href="${base}/file/get?fileId=${filePage.fileId!}" class="btn btn-success radius"><i class="icon-download"/>下载</a>
            <#else>
                <a href="javascript:void(0)" class="btn size-S btn-secondary">无文件</a>
            </#if>
            </td>
    </tr>
</#list>

</table>
<@PageHtml pageobj=transFilePage formId="frmSearch" />


<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>