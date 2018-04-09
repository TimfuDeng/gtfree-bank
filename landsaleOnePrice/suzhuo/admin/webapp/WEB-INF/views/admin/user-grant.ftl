<html>
<head>
    <title>人员授权</title>
    <meta name="menu" content="menu_admin_userlist"/>
    <style type="text/css">

        td input{
            padding-right: 0px;
        }


    </style>

    <script type="text/javascript">
        $(document).ready(function(){
            <#if _result?? && _result>
                _alertResult('ajaxResultDiv',${_result?string('true','false')},'${_msg!}');
            </#if>
            $('input[type=checkbox]').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });
            $('#btnSubmit').click(function(){
                var privilege = {};
                var resources=[];
                $('input[type=checkbox][class=resource]:checked').each(function(){
                    var resource={};
                    resource.url=$($(this).parent().parent().parent()).prev().text();
                    resource.name=$($($(this).parent().parent().parent()).prev()).prev().text();
                    resource.operation="view";
                    resources.push(resource);
                });
                privilege.resources = resources;
                $('#privileges').val(JSON.stringify(privilege));
                document.grantForm.submit();
            });
        });
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/oneprice/resource/list" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/oneprice/user/list">人员管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${sysUser.userName!}</span>
</nav>
<#if _result?? && _result>
<div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<#--<div id="ajaxResultDiv" style="display:none"></div>-->
<form class="form-base" name="grantForm" method="post" action="${base}/oneprice/user/grant/save">

    <h4><small>资源授权：</small></h4>
    <table class="table table-border table-bordered table-bg">
        <thead>
            <tr>
                <th width="120px">资源名称</th>
                <th width="180px">资源路径</th>
                <th width="220px">权限</th>
            </tr>

        </thead>
        <tbody>
            <#list urlResources as urlResource>
            <tr>
                <td>${urlResource.name!}</td>
                <td>${urlResource.url!}</td>
                <td>
                    <div class="check-box">
                        <input type="checkbox" class="resource" id="checkbox${urlResource_index}"<#if resourcePrivileges??&&resourcePrivileges[urlResource.name]??>checked</#if>>
                        <label for="checkbox${urlResource_index}">查看</label>
                    </div>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>


    <input type="hidden" name="userId" value="${sysUser.id!}">
    <input type="hidden" id="privileges" name="privileges">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" id="btnSubmit" class="btn btn-primary">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/oneprice/user/list'">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/icheck/jquery.icheck.min.js"></script>
</body>

</html>