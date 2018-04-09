<html>
<head>
    <title>机构管理</title>
    <meta name="menu" content="menu_admin_deptlist"/>
    <script type="text/javascript">

        $(document).ready(function(){



            $('#btnDelete').click(function(){
                var deptIds = getCheckedDeptIds();
                if(deptIds==null||deptIds==''){
                    alert('请选择需要删除的组织机构！');
                    return;
                }
                if(confirm('确认删除选择的组织机构吗？')){
                    $.post('${base}/console/dept/delete.f',{deptIds:deptIds},function(data){
                        if(data!=null&&data=='true')
                            window.location.reload();
                    });
                }

            });
        });




        /**
         * 用于机构删除
         * @returns {string}
         */
        function getCheckedDeptIds(){
            var deptIds='';
            $('table input:checkbox:checked').each(function(){
                deptIds += $(this).val()+';';
            });
            return deptIds;
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
    <span class="c_gray en">&gt;</span><span class="c_gray">机构管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入机构名称">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/dept/edit'">新增机构</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除机构</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>机构编号</th>
        <th style="width:210px;">机构名称</th>
        <th style="width: 120px;">机构类型</th>
        <th style="width: 330px;">机构地址</th>
        <th style="width: 120px;">操作</th>
    </tr>

<#list transDeptPage.items as dept>
    <tr>
        <td><input type="checkbox" value="${dept.deptId!}"></td>
        <td>${dept.deptNo!}</td>
        <td><a href="${base}/console/dept/view?deptId=${dept.deptId!}">${dept.deptName!}</a></td>
        <td>${dept.deptTypeName!}</td>
        <td>${dept.deptAddress!}</td>
        <td style="text-align: center;width: 150px">
            <a href="${base}/console/dept/edit?deptId=${dept.deptId!}" class="btn btn-default size-S" >编辑机构</a>
        </td>
    </tr>
</#list>

</table>
<@PageHtml pageobj=transDeptPage formId="frmSearch" />
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