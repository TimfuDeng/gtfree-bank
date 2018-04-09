<html>
<head>
    <title>组织管理</title>
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
    <span class="c_gray en">&gt;</span><span class="c_gray">组织管理</span>
</nav>
<div  class="search_bar">
    <form class="navbar-form" id="frmSearch">
        <div class="l" style="">

        <#--<#list Session._USER_BUTTON! as button>
        ${button}
        </#list>-->
            <input type="text" id="organizeName" class="input-text input-mini" name="organizeName" value="${organizeName!}" placeholder="请输入组织名称">
            <button type="button" class="btn " onclick="reloadSrc('frmSearch')">查询</button>
        </div>
        <div class="r">

            <#if Session["_USER_BUTTON"]?seq_contains("orgAdd")>
            <button class="btn btn-primary" type="button"  id="btnCreate" onclick="javascript:changeSrc('${base}/organize/add')">新增组织</button>
            </#if>
        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width:85px;">组织名称</th>
        <th style="width:85px;">负责人</th>
        <th style="width: 80px;">联系方式</th>
        <th style="width: 80px;">所辖岗位</th>
        <th style="width: 80px;">地址</th>
        <th style="width: 150px;">操作</th>
    </tr>
    <#list transOrganizeList.items as organize>
    <tr>
        <td>${organize.organizeName!}</td>
        <td>${organize.organizeResponsibleName!}</td>
        <td>${organize.organizeResponsiblePhone!}</td>
        <td>${organize.organizePost!}</td>
        <td>${organize.organizeAddress}</td>
        <td>
            <#if Session._USER_BUTTON?seq_contains("orgEdit")>
            <a href="javascript:changeSrc('${base}/organize/edit?organizeId=${organize.organizeId!}')" class="btn btn-default size-S" >编辑组织</a>
            </#if>
            <#if Session._USER_BUTTON?seq_contains("orgDelete")>
            <a href="javascript:deleteOrganize('${organize.organizeId!}')"  class="btn btn-default size-S" >删除组织</a>
            </#if>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transOrganizeList formId="frmSearch" />
<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<script type="text/javascript">
    /*$(document).ready(function () {
        _alertResult('ajaxResultDiv', true, "操作成功！");
    });*/
    function deleteOrganize (organizeId) {
        $.ajax({
            type: "post",
            data: {organizeId: organizeId},
            url: "${base}/organize/deleteOrganize",
            success: function (data) {
                if (data.flag) {
                    reloadSrc("frmSearch");
                }
                _alertResult('ajaxResultDiv', data.flag, data.message);
            }
        })
    }
</script>
</body>
</html>