<html>
<head>
    <title>出让公告</title>
    <meta name="menu" content="menu_admin_crgglist"/>
    <script type="text/javascript">
        $(document).ready(function(){

            deleteCrgg();
        });

        function deleteCrgg(){
            $('#btnDelete').click(function(){
                var ggIds = getCheckedGgIds();
                if(-1==ggIds){
                    alert("已经发布的公告不能被删除，请先撤回再删除！");
                    return;
                }
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
        }

        function checkForm(){
            checkInputFileter();
            return true;
        }

        function getCheckedGgIds(){
            var ggIds='';
            $('table input:checkbox:checked').each(function(){
                if($(this).next().val()==1){
                    ggIds=-1;
                    return;
                }
                ggIds += $(this).val()+';';
            });
            return ggIds;
        }

        function reportLandMarketList(){
            layer.load(1,{
                shade: [0.1,'#fff']
            });
            var ggIds=getCheckedGgIds();
            if(ggIds==null||ggIds==''){
                alert('请选择需要上传的出让公告！');
                return;
            }
            if(confirm('确认上传选择的出让公告吗？')){
                $.post('${base}/console/landMarket/crgg-report.f',{ggIds:ggIds},function(data){
                    if(data!=null&&data=='true'){
                        alert('上传出让公告成功！');
                        window.location.reload();
                    }else{
                        alert('上传出让公告失败！');
                    }

                });
            }
        }

        function PreRelease(crggId){
            _crggId=crggId;_status=1;
            if(confirm("是否确认在门户申请发布公告！")){
                setAndReloadStatus(_crggId,_status);
            }


        }
        function BackEdit(crggId){
            _crggId=crggId;_status=0;
            if(confirm("是否确认撤回公告！")){
                setAndReloadStatus(_crggId,_status);
            }



        }

        function PreReleaseAllResource(crggId){
            _crggId=crggId;_status=2;
            if(confirm("是否确认正式发布此公告下面的所有地块！")){
                setAndReloadStatusResource(_crggId,_status);
            }


        }

        function setAndReloadStatusResource(_crggId,_status){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/crgg/status/resource/change.f",{"crggId":_crggId,"status":_status},
                    function(data){
                        $('#myModal').modal('hide');
                        if(data.ret!=null&&data.ret==true)
                            alert("发布成功，请到出让地块中查看！");
                        else
                            alert(data.msg);
                    },'json'
            );
        }



        function setAndReloadStatus(_crggId,_status){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/crgg/status/change.f",{"crggId":_crggId,"status":_status},
                    function(data){
                        $('#myModal').modal('hide');
                        if(data.ret!=null&&data.ret==true)
                            $("#div_status_" + _crggId).load("${base}/console/crgg/status/view.f?crggId="+_crggId );
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
            <button type="submit" class="btn" onclick="return checkForm()">查询</button>
        </div>
        <div class="r">
            <#--<button class="btn btn-primary" type="button"  id="btnImport" onclick="reportLandMarketList();">上报公告</button>-->
           <#-- <button class="btn btn-primary" type="button"  id="btnImport" onclick="showLandMarketList();">导入公告</button>-->
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/crgg/edit'">新增公告</button>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除公告</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th>公告标题</th>
        <th style="width:150px;">公告编号</th>
        <th style="width: 80px;">开始时间</th>
        <th style="width: 80px;">截止时间</th>
        <th style="width: 300px;">操作</th>
    </tr>
    <#list transCrggList.items as crgg>
    <tr>
        <td><input type="checkbox" value="${crgg.ggId!}">
        <input type="hidden" value="${crgg.crggStauts!}">
        </td>
        <td><a href="${base}/console/crgg/edit?ggId=${crgg.ggId!}">
        <#if (crgg.ggTitle?length gt 30)>
            ${crgg.ggTitle?substring(0,30)}.
        <#else>
            ${crgg.ggTitle!}
        </#if>
        </a> </td>
        <td>
            <#if (crgg.ggNum?length gt 20)>
            ${crgg.ggNum?substring(0,20)}
            <#else>
            ${crgg.ggNum!}
            </#if>
       </td>
        <td>${crgg.ggBeginTime?string("yyyy-MM-dd ")}</td>
        <td>${crgg.ggEndTime?string("yyyy-MM-dd ")}</td>
        <td style="text-align: center;width: 150px">
            <#include "common/crgg-status.ftl">
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transCrggList formId="frmSearch" />
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