<html>
<head>
    <title>互动交流</title>
    <meta name="menu" content="menu_admin_communionlist"/>
    <script type="text/javascript">
        var _communionId,_status,_replyContent,_replyTime;
        var _chooseFunction;
        $(document).ready(function(){

            $('#btn_modal').click(function(){
                setAndReloadStatus();
            });

            $('#btn_communionModal').click(function(){
                replyCommunion();
            });

            $('#btnDelete').click(function(){
                var communionIds = getCheckedCommunionIds();
                if(communionIds==null||communionIds==''){
                    alert('请选择需要删除的来信！');
                    return;
                }
                if(confirm('确认删除吗？')){
                    $.post('${base}/console/communion/delete.f',{communionIds:communionIds},function(data){
                        if(data!=null&&data=='true')
                            window.location.reload();
                    });
                }

            });
        });

        function checkForm(){
            if (!checkInputNull('replyContent','内容必须填写!'))
                return false;

            return true;
        }


        /**
         * 用于来信删除
         * @returns {string}
         */
        function getCheckedCommunionIds(){
            var communionIds='';
            $('table input:checkbox:checked').each(function(){
                communionIds += $(this).val()+';';
            });
            return communionIds;
        }

        function PreRelease(communionId){
            _communionId=communionId;_status=1;
            _chooseFunction=0;
            showConfirm("是否确认审核通过！",communionId,1);
        }

   //退回审核
        function backRelease(communionId){
            _communionId=communionId;_status=0;
            _chooseFunction=1;
            showConfirm("是否退回审核！",communionId,0);
        }
//退回回复
        function backReply(communionId){
            _communionId=communionId;_status=1;
            _chooseFunction=2;
            showConfirm("是否退回回复！",communionId,0);
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
            $.post("${base}/console/communion/status/change.f",{"communionId":_communionId,"status":_status},
                    function(data){
                        $('#myModal').modal('hide');
                        if(data.ret!=null&&data.ret==true)
                            $("#div_status_" + _communionId).load("${base}/console/communion/status/view.f?communionId="+_communionId );
                        else
                            alert(data.msg);
                    },'json'
            );
        }

        /**
         * 弹出回复层
         * @param communionId
         */
        function PreReply(communionId){
            _communionId=communionId; _status=2;

          $("#communionModalData").load("${base}/console/communion/view.f?communionId="+communionId );

            $('#communionModal').modal({
                backdrop: false
            });

        }


        function replyCommunion(){
            _replyContent=$('#replyContent').val();
            _replyTime=$('#replyTime').val();
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/console/communion/reply.f",{"communionId":_communionId,"status":_status,"replyContent":_replyContent,"replyTime":_replyTime},
                    function(data){
                        $('#communionModal').modal('hide');
                        if(data.ret!=null&&data.ret==true)
                            $("#div_status_" + _communionId).load("${base}/console/communion/status/view.f?communionId="+_communionId );
                        else
                            alert(data.msg);
                    },'json'
            );
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
    <span class="c_gray en">&gt;</span><span class="c_gray">互动交流</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入来信标题">
            <button type="submit" class="btn ">查询</button>
        </div>
        <div class="r">
           <#-- <button class="btn btn-primary" type="button"  id="btnCreate" onclick="window.location.href='${base}/console/communion/save'">新增来信（接口测试）</button>-->
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" class="span12">
    <tr>
        <th style="width: 35px;">#</th>
        <th>编号</th>
        <th style="width:400px;">标题</th>
        <th style="width: 120px;">接受时间</th>
 <#--       <th style="width: 120px;">状态</th>-->
        <th style="width: 120px;">操作</th>
    </tr>

<#list pageInteractCommunion.items as communion>
    <tr>
        <td><input type="checkbox" value="${communion.communionId!}"></td>
        <td style="text-align: center">${communion.publicNo!}</td>
        <td style="text-align: center"><a href="${base}/console/communion/edit?communionId=${communion.communionId!}">${communion.publicTitle!}</a> </td>
        <td>${communion.publicTime?string("yyyy-MM-dd HH:mm")}</td>
        <td style="text-align: center;width: 150px">
            <#include "../common/communion-status.ftl">
        </td>


    </tr>
</#list>

</table>
<@PageHtml pageobj=pageInteractCommunion formId="frmSearch" />
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



<div id="communionModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div id="communionModalData">
    <#include "interact-communion.ftl">

    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_communionModal" >确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>





<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
</body>
</html>