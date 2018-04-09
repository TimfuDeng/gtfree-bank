<html>
<head>
    <title>人员授权信息</title>
    <meta name="menu" content="menu_admin_userlist"/>
    <style type="text/css">
        .title-info {
            width: 100%;
            height: 90px;
            position: relative;
            top: 18px;
            padding-right: 10px;
        }
        .title-info h1, .title-info h3 {
            margin: 0;
            padding: 0 0 0 20px;
            font-family: "Microsoft Yahei";
            font-weight: 400;
        }
        td input{
            padding-right: 0px;
        }


    </style>
    <script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/icheck/jquery.icheck.min.js"></script>
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
                var regions = [];
                $('input[type=checkbox][class=region]:checked').each(function(){
                    regions.push($(this).val());
                });
                privilege.regions = regions;
                $('#privileges').val(JSON.stringify(privilege));
                document.grantForm.submit();
            });
        });

        //----------------跨域支持代码开始(非跨域环境请删除这段代码)----------------
        var JSON = JSON || {};
        JSON.stringify = JSON.stringify || function (obj) {
            var t = typeof (obj);
            if (t != "object" || obj === null) {
                if (t == "string")obj = '"'+obj+'"';
                return String(obj);
            }
            else {
                var n, v, json = [], arr = (obj && obj.constructor == Array);
                for (n in obj) {
                    v = obj[n]; t = typeof(v);
                    if (t == "string") v = '"'+v+'"';
                    else if (t == "object" && v !== null) v = JSON.stringify(v);
                    json.push((arr ? "" : '"' + n + '":') + String(v));
                }
                return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
            }
        };
        var callback= callback || function(v){
                    v=JSON.stringify(v);
                    window.name=escape(v);
                    window.location='http://'+location.search.match(/[\?&]parenthost=(.*)(&|$)/i)[1]+'/xheditorproxy.html';//这个文件最好是一个0字节文件，如果无此文件也会正常工作
                }
        //----------------跨域支持代码结束----------------
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/user/list">人员管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transUser.viewName!}</span>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" name="grantForm" method="post" action="${base}/console/user/grant/save">
    <h4><small>行政区授权：</small></h4>
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="220px"><label class="control-label">行政区：</label></td>
            <td colspan="2">
            <#list regionAllList as region>
                <div class="check-box">
                    <input type="checkbox" class="region" id="checkboxRegion${region_index}" value="${region[0]}" <#if regionPrivileges?seq_contains(region[0])>checked</#if>>
                    <label for="checkboxRegion${region_index}">${region[1]}</label>
                </div>
            </#list>
            </td>
        </tr>
    </table>
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
                        <input type="checkbox" class="resource" id="checkbox${urlResource_index}"<#if resourcePrivileges[urlResource.name]??>checked</#if>>
                        <label for="checkbox${urlResource_index}">查看</label>
                    </div>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>


    <input type="hidden" name="userId" value="${transUser.userId!}">
    <input type="hidden" id="privileges" name="privileges">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" id="btnSubmit" class="btn btn-primary">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/user/list'">返回</button>
        </div>
    </div>
</form>
</body>

</html>