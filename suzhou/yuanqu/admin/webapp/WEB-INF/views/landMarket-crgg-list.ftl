<html>
<head>
    <title>导入出让公告列表</title>
    <meta name="menu" content="menu_admin_crgglist"/>
    <script type="text/javascript">
        $(document).ready(function(){
            checkCrggTag();
            $('#btnImport').click(function(){
                importLandMarketCrgg();
            });
        });

        function checkCrggTag(){
            $('table input[type="checkbox"]').each(function(){
                var gyggId = $(this).val();
                getCrggTag($(this).parent().parent().children().last(),gyggId);
            });
        }
        function getCrggTag(tagElement,gyggId){
            $.get('${base}/console/crgg/gygg.f?gyggId='+gyggId,function(data){
                if(data!=null&&data!=''){
                    $(tagElement).empty();
                    $(tagElement).append('<span class="label label-danger radius">已导入</span>');
                }

            });
        }

        function importLandMarketCrgg(){
            var gyggIds = getCheckedGyggId();
            if(gyggIds==null||gyggIds==''){
                alert('请勾选需要导入的出让公告！');
                return;
            }
            var params={
                gyggIds:gyggIds
            };
            var loading = layer.load(1, {
                shade: [0.1,'#fff']
            });
            $.ajax({
                url:'${base}/console/landMarket/crgg-import.f',
                data:params,
                timeout:60000,
                error:function(){
                    layer.close(loading);
                    alert('导入错误！');
                },
                success:function(data){
                    layer.close(loading);
                    if(data!=null&&data!=''){
                        if(data=="-999"){
                            alert('您还没有数据授权！');
                        }else{
                            alert('导入成功！');
                            checkCrggTag();
                        }

                    }

                }
            });
        }

        function getCheckedGyggId(){
            var gyggIds='';
            $('table input:checkbox:checked').each(function(){
                gyggIds += $(this).val()+';';
            });
            return gyggIds;
        }
    </script>
    <style>
        #title{
            width: 200px;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/crgg/list">出让公告</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">导入</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" class="input-text input-mini" id="title" name="title" value="${title!}" placeholder="请输入公告标题">
            <button type="submit" class="btn ">查询</button>
        </div>
        <div class="r">
            <button class="btn btn-primary" type="button"  id="btnImport">导入</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/crgg/list'">返回</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped " class="span12">
    <tr>
        <th style="width: 35px;">#</th>
        <th>公告标题</th>
        <th style="width:185px;">公告编号</th>
        <th style="width: 120px;">发布时间</th>
        <th style="width: 60px;">状态</th>
    </tr>
    <#list transCrggList.items as crgg>
    <tr>
        <td><input type="checkbox" value="${crgg.gyggId!}"></td>
        <td>${crgg.ggTitle!}</td>
        <td>${crgg.ggNum!}</td>
        <td>${crgg.ggBeginTime?string("yyyy-MM-dd HH:mm")}</td>
        <td></td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transCrggList formId="frmSearch" />
</body>
</html>