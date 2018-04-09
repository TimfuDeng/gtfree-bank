<html>
<head>
    <title>法律依据</title>
    <meta name="menu" content="menu_admin_transLaw"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#btnDelete').click(function(){
                var lawIds = getCheckedLawIds();
                if(lawIds==null||lawIds==''){
                    alert('请选择需要删除的法律依据！');
                    return;
                }
                if(confirm('确认删除选择的法律依据吗？')){
                    $.post('${base}/console/transLaw/delete',{lawIds:lawIds},function(data){
                        window.location.reload();
                    });
                }

            });
        });

        function getCheckedLawIds(){
            var lawIds='';
            $('table input:checkbox:checked').each(function(){
                lawIds += $(this).val()+';';
            });
            return lawIds;
        }


        function revoke(lawId){
            if(confirm('确认要撤销此法律依据吗？')){
                $.ajax({
                    url:'${base}/console/transLaw/revoke.f',
                    type:"post",
                    data:{lawId:lawId},
                    success:function(data){
                        $("#statusId_" + lawId).html(data);
                    }
                });
            }
        }

        function deploy(lawId){
            if(confirm('确认要发布通知吗？')){
                $.ajax({
                    url:'${base}/console/transLaw/deploy.f',
                    type:"post",
                    data:{lawId:lawId},
                    success:function(data){
                        $("#statusId_" + lawId).html(data);
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
    <span class="c_gray en">&gt;</span><span class="c_gray">法律依据</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入法律依据标题">
            <button type="submit" class="btn " onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <#--<button class="btn btn-primary" type="button"  id="btnImport" onclick="reporttransLawList();">上报法律依据</button>-->
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/transLaw/edit'">新增法律依据</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除法律依据</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>法律依据标题</th>
        <#--<th>作者</th>-->
        <th style="width: 120px;">修改时间</th>
        <th style="width: 200px;">操作</th>
    </tr>
<#list transLawList.items as transLaw>
    <tr>
        <td><input type="checkbox" value="${transLaw.lawId!}"></td>
        <td style="text-align: center">
            <a href="${base}/console/transLaw/detail?lawId=${transLaw.lawId!}">${transLaw.title!}</a>
        </td>
        <#--<td style="text-align: center">${transLaw.transLawAuthor}</td>-->
        <td style="text-align: center">${transLaw.updateTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center">
            <#include "../common/law-status.ftl">
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=transLawList formId="frmSearch" />
</body>
</html>