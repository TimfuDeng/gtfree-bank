
<html>
<head>
    <title>成交公示</title>
    <meta name="menu" content="menu_admin_dealNotice"/>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            deleteCjgs();
        });

        function deleteCjgs(){
            $('#btnDelete').click(function(){
                var noticeIds = getCheckedGgIds();
                if(isEmpty(noticeIds)){
                    alert('请选择需要删除的成交公示！');
                    return;
                }
                if(confirm('确认删除选择的成交公示吗？')){
                    $.post('${base}/dealNotice/deleteDealNotice',{noticeIds:noticeIds},function(data){
                        if (data.flag) {
                            reloadSrc("frmSearch");
                        }
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    });
                }

            });
        }

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
                $.post('${base}/landMarket/dealnotice-report.f',{noticeIds:noticeIds},function(data){
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
                    url:'${base}/dealNotice/revoke',
                    type:"post",
                    data:{noticeId:noticeId},
                    success:function(data){
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if (data.flag) {
                            reloadSrc("frmSearch");
                        }
//                        $("#statusId_" + noticeId).html(data);
                    }
                });
            }
        }

        function deploy(noticeId){
            if(confirm('确认要发布通知吗？')){
                $.ajax({
                    url:'${base}/dealNotice/deploy',
                    type:"post",
                    data:{noticeId:noticeId},
                    success:function(data){
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if (data.flag) {
                            reloadSrc("frmSearch");
                        }

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
        #noticeTitle{
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
    <span class="c_gray en">&gt;</span><span class="c_gray">成交公示</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l">
            <input type="text"  class="input-text" style="width:200px" name="noticeTitle" value="${noticeTitle!}" placeholder="请输入成交公示标题">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/dealNotice/edit')">新增成交公示</button>
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
            <a href="javascript:changeSrc('${base}/dealNotice/detail?noticeId=${notice.noticeId!}')">${notice.noticeTitle!}</a>
        </td>
        <td style="text-align: center">${notice.noticeAuthor}</td>
        <td style="text-align: center">${notice.deployTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center">
            <div id="statusId_${notice.noticeId!}">
                <#if notice.deployStatus==1>
                    <input type="button" onclick="revoke('${notice.noticeId!}')" class="btn btn-primary size-S" value="撤回"/>
                <#else>
                    <a href="javascript:changeSrc('${base}/dealNotice/edit?noticeId=${notice.noticeId!}')"  class="btn size-S btn-default" >编辑</a>
                    <input class="btn size-S btn-primary" type="button" onclick="deploy('${notice.noticeId!}')"  value="发布"/>
                </#if>
            </div>
        </td>
    </tr>
</#list>
</table>
<@PageHtml pageobj=dealNoticeList formId="frmSearch" />
</body>
</html>
