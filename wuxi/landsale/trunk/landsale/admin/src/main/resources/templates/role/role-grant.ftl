<html>
<head>
    <title>角色授权信息</title>
    <meta name="menu" content="menu_admin_rolelist"/>
    <link rel="stylesheet" href="${base}/tree/zTreeStyle/zTreeStyle.css" type="text/css">
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
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/icheck/jquery.icheck.min.js"></script>
    <script type="text/javascript" src="${base}/tree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript">
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData : {
                    enable : true
                }
            },
            callback: {
                beforeClick: beforeClick,
                onClick: onClick
            }
        };

        $(document).ready(function(){
            $('input[type=checkbox]').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });

            $('#btnSubmit').click(function(){
                var menuIds = "";
                $('input[type=checkbox][class=resource]:checked').each(function(){
                    if ("" == menuIds) {
                        menuIds = $(this).val();
                    } else {
                        menuIds += "," + $(this).val();
                    }
                });
                $.ajax({
                    type: "post",
                    data: {menuIds: menuIds, roleId: $("#roleId").val(), regionCodes: $("#regionCodes").val()},
                    url: "${base}/role/grantRole",
                    success: function (data) {
                        _alertResult('ajaxResultDiv', data.result, data.msg);
                    }
                });
            });

            $.ajax({
                type: "post",
                dataType: "json",
                data: {regionCode: '${regionCodes!}'},
                url: "${base}/region/getRegionTree",
                success: function (data) {
                    if (data != null) {
                        $.fn.zTree.init($("#treeRegion"), setting, data);
                    }
                }
            });
        });

        function beforeClick(treeId, treeNode) {
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("只能选择城市...");
            return check;
        }

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeRegion"),
                    nodes = zTree.getSelectedNodes(),
                    regionCodes = "",
                    regionNames = "";
            /*if (nodes.length > 1) {
                alert("只能选择一个城市...");
                return false;
            }*/
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, len = nodes.length; i < len; i++) {
                regionCodes += nodes[i].id + ",";
                regionNames += nodes[i].name.substring(0, nodes[i].name.indexOf("(")) + ",";
            }
            if (regionCodes.length > 0 ) {
                regionCodes = regionCodes.substring(0, regionCodes.length - 1);
                regionNames = regionNames.substring(0, regionNames.length - 1);
            }
            $("#regionNames").val(regionNames);
            $("#regionCodes").val(regionCodes);
        }

        function showMenu() {
            $("#menuContent").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        function clearRegion () {
            $("#regionNames").val("");
            $("#regionCodes").val("");
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/role/index')">角色管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transRole.roleName!}</span>
</nav>

<form class="form-base" name="grantForm" method="post" action="${base}/role/grant/save">
    <h4><small>数据授权：</small></h4>
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="220px"><label class="control-label">授权行政区：</label></td>
            <td colspan="2">
                <input type="text" class="input-text" id="regionNames" name="regionNames" value="${regionName!}" readonly
                       style="width: 50%;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="regionCodes" name="regionCodes" value="${regionCode!}" />
                <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                <a href="#" onclick="clearRegion(); return false;">清空</a>
                <div id="menuContent" class="menuContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 245px;width: 250px;z-index: 1; ">
                    <ul id="treeRegion" class="ztree" style="margin-top:0; width:160px;z-index: 1;"></ul>
                </div>
            </td>
        </tr>
    </table>
    <h4><small>资源授权：</small></h4>
    <table class="table table-border table-bordered table-bg">
        <thead>
        <tr>
            <th width="120px">资源名称</th>
            <th>权限</th>
        </tr>
        </thead>
        <tbody>
        <#list transRoleMenuList as transRoleMenu>
        <tr>
            <td>${transRoleMenu.menuName!}</td>
            <#--<td>${transRoleMenu.menuUrl!}</td>-->
            <td>
                <div class="check-box">
                    <input type="checkbox" class="resource" value="${transRoleMenu.menuId!}" id="checkbox${transRoleMenu_index}" <#if menuIdList?seq_contains(transRoleMenu.menuId)>checked</#if>>
                    <label for="checkbox${transRoleMenu_index}">${transRoleMenu.menuName!}</label>
                </div>
            <#list transRoleMenu.subMenuList as subRoleMenu>
                <div class="check-box">
                    <input type="checkbox" class="resource" value="${subRoleMenu.menuId!}" id="checkbox${subRoleMenu_index}" <#if menuIdList?seq_contains(subRoleMenu.menuId)>checked</#if>>
                    <label for="checkbox${subRoleMenu_index}">${subRoleMenu.menuName!}</label>
                </div>
            </#list>
            </td>
        </tr>
        <#--<#else>
        <tr>
            <td>${urlResource.name!}</td>
            <td>${urlResource.url!}</td>
            <td>
                <div class="check-box">
                    <input type="checkbox" class="resource" id="checkbox${urlResource_index}"<#if resourcePrivileges[urlResource.name]??>checked</#if>>
                    <label for="checkbox${urlResource_index}">操作</label>
                </div>
            </td>
        </tr>
        </#if>-->
        </#list>
        </tbody>
    </table>


    <input type="hidden" id="roleId" name="roleId" value="${transRole.roleId!}">
    <input type="hidden" id="privileges" name="privileges">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" id="btnSubmit" class="btn btn-primary">保存</button>
            <button type="button" class="btn" onclick="changeSrc('${base}/role/index')">返回</button>
        </div>
    </div>
</form>
</body>

</html>