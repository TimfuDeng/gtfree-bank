<html>
<head>
    <title>出让公告</title>
    <meta name="menu" content="menu_admin_crgglist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $.ajaxSetup ({
                cache: false //关闭AJAX相应的缓存
            })
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
                    $.post('${base}/crgg/deleteCrgg', {crggIds: ggIds}, function(data){
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if( data.flag)
                            reloadSrc('frmSearch');
                    });
                }

            });
        }

        function getCheckedGgIds(){
            var ggIds='';
            $('table input:checkbox:checked').each(function(){
                if($(this).next().val()==1){
                    ggIds=-1;
                    return;
                }
                ggIds += $(this).val()+',';
            });
            return ggIds;
        }

        function PreRelease(crggId){
            if(confirm("是否确认在门户申请发布公告！")){
                setAndReloadStatus(crggId, 1);
            }
        }

        function BackEdit(crggId){
            if(confirm("是否确认撤回公告！")){
                setAndReloadStatus(crggId, 0);
            }
        }

        function PreReleaseAllResource(crggId){
            if(confirm("是否确认正式发布此公告下面的所有地块！")){
                setAndReloadStatusResource(crggId, 2);
            }
        }

        // 发布公告下所有地块
        function setAndReloadStatusResource(_crggId, _status){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/crgg/status/resource/change",{"crggId": _crggId, "status": _status},
                    function (data) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if(data.flag)
                            alert("发布成功，请到出让地块中查看！");
                    },'json'
            );
        }

        // 发布 撤回公告
        function setAndReloadStatus(_crggId,_status){
            $.ajaxSetup ({
                cache: false
            });
            $.post("${base}/crgg/status/change", {"crggId":_crggId,"status":_status},
                    function(data){
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if(data.flag)
                            reloadSrc('frmSearch');
                    },'json'
            );
        }

        /*function reportLandMarketList(){
            layer.load(1,{
                shade: [0.1,'#fff']
            });
            var ggIds=getCheckedGgIds();
            if(ggIds==null||ggIds==''){
                alert('请选择需要上传的出让公告！');
                return;
            }
            if(confirm('确认上传选择的出让公告吗？')){
                $.post('${base}/landMarket/crgg-report.f',{ggIds:ggIds},function(data){
                    if(data!=null&&data=='true'){
                        alert('上传出让公告成功！');
                        window.location.reload();
                    }else{
                        alert('上传出让公告失败！');
                    }

                });
            }
        }
        function showLandMarketList(){
            layer.load(1, {
                shade: [0.1,'#fff']
            });
            window.location.href='${base}/landMarket/crgg-list';
        }*/
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
    <span class="c_gray en">&gt;</span><span class="c_gray">出让公告</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="title" class="input-text input-mini" name="title" value="${title!}" placeholder="请输入公告标题">
            <button type="button" class="btn" onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">
            <#--<button class="btn btn-primary" type="button"  id="btnImport" onclick="reportLandMarketList();">上报公告</button>-->
           <#-- <button class="btn btn-primary" type="button"  id="btnImport" onclick="showLandMarketList();">导入公告</button>-->
            <#if Session["_USER_BUTTON"]?seq_contains("crggAdd")>
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/crgg/add')">新增公告</button>
            </#if>
            <#if Session["_USER_BUTTON"]?seq_contains("crggDelete")>
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除公告</button>
            </#if>
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
        <td><a href="javascript:changeSrc('${base}/crgg/edit?crggId=${crgg.ggId!}')">
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
        <#if Session["_USER_BUTTON"]?seq_contains("crggAddResource")>
            <a href="javascript:changeSrc('${base}/resource/add?ggId=${crgg.ggId!}')" class="btn btn-default size-S" >新增地块</a>
        </#if>
        <#if Session["_USER_BUTTON"]?seq_contains("crggQueryResource")>
            <a href="javascript:changeSrc('${base}/resource/index', {ggId: '${crgg.ggId!}'})" class="btn btn-default size-S" >查看地块</a>
        </#if>
        <#if crgg.crggStauts==0>
            <#if Session["_USER_BUTTON"]?seq_contains("crggRelease")>
            <input class="btn size-S btn-primary" type="button" value="发布公告"  onclick="PreRelease('${crgg.ggId!}')">
            </#if>
        <#elseif crgg.crggStauts==1>
            <#if Session["_USER_BUTTON"]?seq_contains("crggReleaseResource")>
            <input class="btn size-S btn-primary" type="button" value="发布所有地块"  onclick="PreReleaseAllResource('${crgg.ggId!}')">
            </#if>
            <#if Session["_USER_BUTTON"]?seq_contains("crggRevoke")>
            <input class="btn size-S btn-secondary" type="button" value="撤回公告"  onclick="BackEdit('${crgg.ggId!}')">
            </#if>
        </#if>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transCrggList formId="frmSearch" />
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
</body>
</html>