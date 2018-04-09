<html>
<head>
    <title>-组织管理</title>
    <meta name="menu" content="menu_admin_organizelist"/>
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

        td input {
            padding-right: 0px;
        }

        .btn-upload {
            position: relative;
            display: inline-block;
            overflow: hidden;
            vertical-align: middle;
            cursor: pointer;
            height: 60px !important;
        }

        .upload-url {
            width: 200px;
            cursor: pointer;
        }

        .input-file {
            position: absolute;
            right: 0;
            top: 0;
            cursor: pointer;
            font-size: 17px;
            opacity: 0;
            filter: alpha(opacity=0)
        }

        .uploadifive-button {
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        .btn-upload input {
            height: 65px;
            cursor: pointer;
        }

        .fileUploader {
            margin-left: 10px;
            clear: both;
        }

        #attachments {
            width: 500px;
            float: left;
            overflow: hidden;
            text-overflow-ellipsis: '..';
        }

        #attachmentsOperation {
            float: right
        }
        .upload-progress-bar{
            display: none;
            width: 400px;
        }
    </style>
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
//                beforeClick: beforeClick,
                onClick: onClick
            }
        };

        $(document).ready(function () {
            $.ajax({
                type: "post",
                dataType: "json",
//                data: {},
                url: "${base}/region/getRegionTree",
                success: function (data) {
                    if (data != null) {
                        $.fn.zTree.init($("#treeOrganize"), setting, data);
                    }
                }
            });

        });

        function checkInputNull(name,info){
            if($("input[name='"+name+"']").val()==""){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }

        function checkForm(){
            if (!checkInputNull('organizeName','组织名称必须填写!'))
                return false;
            if (!checkInputNull('regionNames','行政区必须选择!'))
                return false;
            return true;
        }

        function submitForm() {
            if (checkForm()) {
                var url;
                if (isEmpty($("#organizeId").val())) {
                    url = "${base}/organize/addOrganize";
                } else {
                    url = "${base}/organize/editOrganize";
                }
                $.ajax({
                    type: "post",
                    url: url,
                    data: $("form").serialize(),
                    success: function (data) {
                        $("#organizeId").val(data.empty.organizeId);
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    }
                });
            }
        }

//        function beforeClick(treeId, treeNode) {
//            var zTree = $.fn.zTree.getZTreeObj("treeOrganize"),
//                nodes = zTree.getSelectedNodes();
//            var check = (treeNode && !treeNode.isParent);
//            if (!check) alert("只能选择城市...");
//            return check;
//        }

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeOrganize"),
                nodes = zTree.getSelectedNodes(),
                regionCodes = "",
                regionNames = "";
            if (nodes.length > 1) {
                alert("只能选择一个城市...");
                return false;
            }
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, len = nodes.length; i < len; i++) {
                regionCodes += nodes[i].id + ",";
                regionNames += nodes[i].name.substring(0, nodes[i].name.indexOf("(")) + ",";
            }
            if (regionCodes.length > 0) {
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
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/organize/index')">组织管理</a>

</nav>

<#--<div id="ajaxResultDiv" style="display:none"></div>-->
<form  defaultHtmlEscape="true"  class="form-base" method="post" action="${base}/organize/save">
    <#--spring:htmlEscape-->
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td><label class="control-label">组织名称：</label></td>
            <td><input type="text" class="input-text" name="organizeName" value="${transOrganize.organizeName!}"
                       style="width:100%;background-color: background-color: #ffffcc;" maxlength="25"></td>
            <td width="180px"><label class="control-label">负责人名称：</label></td>
            <td width="300px"><input type="text" class="input-text" name="organizeResponsibleName" value="${transOrganize.organizeResponsibleName!}"
                                     style="width:100%;" maxlength="25"></td>
        </tr>
        <tr>
            <td><label class="control-label">负责人联系方式：</label></td>
            <td><input type="text" class="input-text" name="organizeResponsiblePhone" value="${transOrganize.organizeResponsiblePhone!}"
                       style="width:100%;" maxlength="25"></td>
            <td width="180px"><label class="control-label">所辖岗位：</label></td>
            <td width="300px"><input type="text" class="input-text" name="organizePost" value="${transOrganize.organizePost!}"
                                     style="width:100%;" maxlength="50"></td>
        </tr>
        <tr>
            <td width="180px"><label class="control-label">地址：</label></td>
            <td colspan="3"><input type="text" class="input-text" name="organizeAddress" value="${transOrganize.organizeAddress!}"
                                   style="width: 50%;" maxlength="50"></td>
        </tr>
        <tr>
            <td width="180px"><label class="control-label">所辖行政区：</label></td>
            <td colspan="3">
                <input type="text" class="input-text" id="regionNames" name="regionNames" value="${regionNames!}" readonly
                                   style="width: 50%;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="regionCodes" name="regionCodes" value="${regionCodes!}" />
                <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                <div id="menuContent" class="menuContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 204px;width: 250px; ">
                    <ul id="treeOrganize" class="ztree" style="margin-top:0; width:160px;z-index: 1;"></ul>
                </div>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">备注信息：</label></td>
            <td colspan="3">
                <textarea type="text"  name="organizeDescribe"  style="width: 100%"
                          rows="15" maxlength="64">${transOrganize.organizeDescribe!}</textarea>
            </td>
        </tr>
    </table>



    <input type="hidden" id="organizeId" name="organizeId" value="${transOrganize.organizeId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="submitForm()">保存</button>
            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/organize/index')">返回</button>
        </div>
    </div>
</form>

</body>
</html>