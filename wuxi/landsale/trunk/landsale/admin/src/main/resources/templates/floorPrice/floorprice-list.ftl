<html>
<head>
    <title>底价配置</title>
    <script>
        $(document).ready(function(){
            deleteGrant();
            /* $("#btn_modal").click(function(){
                 getRole();
             });*/
        });

        function deleteGrant(){
            $('#btnDelete').click(function(){
                var floorPriceIds = getCheckedFloorPriceIds();
                if(isEmpty(floorPriceIds)){
                    alert('该用户授权未授权，请重新选择！！');
                    reloadSrc("frmSearch");
                    return;
                }
                if(confirm('确认删除选择的人员授权吗？')){
                    $.post('${base}/floorPrice/grant/deleteGrant',{floorPriceIds:floorPriceIds},function(data){
                        if (data.flag==true) {
                            reloadSrc("frmSearch");
                        }
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    });
                }

            });
        }


        function getCheckedFloorPriceIds(){
            var floorPriceIds = '';
            $('table input:checkbox:checked').each(function () {
                if (floorPriceIds == '')
                    floorPriceIds = $(this).val();
                else
                    floorPriceIds += ',' + $(this).val();
            });
            return floorPriceIds;
        }


    </script>
    <style>
        .l span,.l button{
            margin-left: 10px;
        }
        .l input{
            width: auto;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">底价配置</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">
            <input type="text" id="viewName" class="input-text input-mini" name="viewName" value="${viewName!}" placeholder="请输入人员名称">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
            <input type="hidden" name="userType" value="0">
        </div>
        <div class="r">
            <button class="btn btn-danger" type="button"  id="btnDelete" >删除授权</button>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width: 35px;">#</th>
        <th style="width: 85px;">人员姓名</th>
        <th style="width: 85px;">状态</th>
        <th style="width: 80px;">行政区</th>
        <th style="width: 90px;">用途</th>
        <th style="width: 90px;">创建时间</th>
        <th style="width: 100px;text-align: center">操作</th>
    </tr>
<#list transFloorPricList.items as user>

    <tr>
        <td><input type="checkbox" value="${user.floorPriceId!}"></td>
        <td>${user.viewName!}</td>
        <td>
            <#if user.status=='ENABLED'>
                <span class="label label-success">${user.status.title!}</span>
            <#else>
                <span class="label label-danger">${user.status.title!}</span>
            </#if>

        </td>
        <td>${user.regionName!}</td>
        <td>${user.tdytDictName!}</td>
        <td>${user.createAt?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td>
            <#if Session._USER_BUTTON?seq_contains("floorPriceAdd") && user.userId != '0'>
                <a href="javascript:changeSrc('${base}/floorPrice/grant?userId=${user.userId!}')" class="btn btn-default size-S" >授权</a>
            </#if>
        </td>
    </tr>
</#list>

</table>
<@PageHtml pageobj=transFloorPricList formId="frmSearch" />
</body>
</html>