<html>
<head>
    <title>行政区管理</title>
    <link rel="stylesheet" href="${base}/tree/zTreeStyle/zTreeStyle.css" type="text/css">
<#--<link rel="stylesheet" href="${base}/tree/metroStyle/metroStyle.css" type="text/css">-->
    <style type="text/css">
        .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
        .middle {border: 1px solid #e5e5e5;width:400px;height:200px;position:absolute;left:50%;margin-left:-250px;top:50%;margin-top:10px; display: none;}
        .middle_title{width: 400px; height: 40px; background-color: #F5F5F5; font-size: 19px; padding-left: 10px; box-sizing: border-box;}
        .middle_context {width: 400px; height: 55px; line-height: 55px;}
        .middle_context span {float: left; width: 80px; height: 55px; font-size: 15px; margin-left: 10px;}
        .middle_context input {float: left; margin: 10  px 0 0 10px; width: 250px; height: 40px; font-size: 18px;}
        .error_msg {width: 200px; font-size: 15px; color: red; padding-left: 10px; box-sizing: border-box; float: left;}
        .middle_button {width: 200px; height: 50px; line-height: 50px; float: right;}
        .middle_button button {width: 75px; height: 30px; float: left; margin-left: 15px;background-color: #5a98de; border-color: #5a98de; color: #fff;}
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">行政区管理</span>
</nav>

    <ul id="treeRegion" class="ztree"></ul>

<div id="middle" class="middle">
    <div class="middle_title">新增行政区</div>
    <div class="middle_context">
        <span>行政区代码:</span>
        <input type="text" id="regionCode"/>
    </div>
    <div class="middle_context">
        <span>行政区名称:</span>
        <input type="text" id="regionName"/>
    </div>
    <div class="error_msg"></div>
    <div class="middle_button">
        <button onclick="saveNode()" type="button">保存</button>
        <button onclick="cancelAdd();" type="button">取消</button>
    </div>
</div>
<input type="hidden" id="pIdAdd"/>
<script type="text/javascript" src="${base}/tree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${base}/tree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${base}/tree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${base}/tree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${base}/tree/tree.js"></script>
<script type="text/javascript">
    var zTreeObject;
    var addParentNode;
    //父节点不显示删除按钮
    function setRemoveBtn(treeId, treeNode) {
        return !treeNode.isParent;
    }
    // 移除按钮
    function removeHoverDom (treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    }
    // 区级行政区 不显示添加按钮
    function setAddBtn (treeNode) {
        return treeNode.regionLevel < 3;
    }
    // 显示添加按钮
    var newCount = 0;
    function addHoverDom (treeId, treeNode) {
        if (setAddBtn(treeNode)) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='新增' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            newCount++;
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function () {
                $("#pIdAdd").val(treeNode.id);
                addParentNode = treeNode;
                // 弹出页面 等待回调
                $("#middle").show();
            });
        }
    }

    $(document).ready(function () {
        zTreeObject = initTree("treeRegion",
                "${base}/region/getRegionTree",
                null,
                addHoverDom,
                removeHoverDom,
                true,
                setRemoveBtn
        );
    });

    function saveNode() {
        if (checkNode ($("#regionCode").val(), $("#regionName").val(), addParentNode.id)) {
            // 添加新行政区
           $.ajax({
                type: "post",
                data: {regionCode: $("#regionCode").val(), regionName: $("#regionName").val(), regionLevel: (addParentNode.regionLevel + 1)},
                url: "${base}/region/saveRegion",
                success: function (data) {
                    _alertResult('ajaxResultDiv', data.flag, data.message);
                    if (data.flag) {
                        $("#middle").hide();
                        zTreeObject.addNodes(addParentNode, {id: $("#regionCode").val(), pId:addParentNode.id, name:$("#regionName").val() + "(" + $("#regionCode").val() + ")", regionLevel: addParentNode.regionLevel + 1});
                    }
                }
            });
        }

    }

    function cancelAdd() {
        $("#regionCode").val("");
        $("#regionName").val("");
        $("#middle").hide();
    }

    function checkNode (regionCode, regionName, pIdAdd) {
        if (isEmpty(regionCode)) {
            $(".error_msg").text("行政区代码必须填写！");
            $("#regionCode").focus();
            return false;
        }
        // 子节点Code 必须比父节点长
        if (pIdAdd.length >= regionCode.length) {
            $(".error_msg").text("子节点行政区长度必须大于父节点！");
            $("#regionCode").focus();
            return false;
        }
        // 子节点Code必须以父节点Code开头
        if (!regionCode.startsWith(pIdAdd)) {
            $(".error_msg").text("子节点行政区代码必须以父节点开头！");
            $("#regionCode").focus();
            return false;
        }
        if (isEmpty(regionName)) {
            $(".error_msg").text("行政区名称必须填写！");
            $("#regionName").focus();
            return false;
        }
        return true;
    }

    // 修改前验证
    treeOptions["beforeRename"] = function(treeId, treeNode, newName, isCancel){
        // 包含行政区代码 格式验证
        if ((newName.indexOf("(") < 0) || (newName.indexOf(")") < 0)) {
            _alertResult('ajaxResultDiv', false, "修改时需在行政区名称后的()内加上行政区代码！");
            return false;
        }
        var regionName = newName.substring(0, newName.indexOf("("));
        var regionCode = newName.substring(newName.indexOf("(") + 1, newName.lastIndexOf(")"));
        // regionCode 非空
        if (isEmpty(regionCode)) {
            _alertResult('ajaxResultDiv', false, "()内行政区代码必须填写！");
            return false;
        }
        // 子节点行政区长度必须大于父节点
        if (treeNode.pId.length >= regionCode.length) {
            _alertResult('ajaxResultDiv', false, "子节点行政区长度必须大于父节点！");
            return false;
        }
        // 子节点行政区代码必须以父节点Code开头
        if (!regionCode.startsWith(treeNode.pId)) {
            _alertResult('ajaxResultDiv', false, "子节点行政区代码必须以父节点开头！");
            return false;
        }
        // regionName 非空
        if (isEmpty(regionName)) {
            _alertResult('ajaxResultDiv', false, "()前行政区名称必须填写！");
            return false;
        }
        // 相同
        if (treeNode.name == newName) {
            return true;
        }
        // 行政区代码不同 检查数据库中是否已有相同行政区
        var url = "${base}/region/updateRegion";
        var params = {regionCode: regionCode, regionName: regionName, regionLevel: treeNode.regionLevel};
        if (treeNode.id != regionCode) {
            var flag = true;
            $.ajax({
                type: "post",
                data: {regionCode: regionCode},
                url: "${base}/region/checkRegionCode",
                async: false,
                success: function (data) {
                    if (!data.flag) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        flag = false;
                    } else {
                        url = "${base}/region/deleteAddRegion";
                        params = {regionCode: regionCode, regionName: regionName, regionLevel: treeNode.regionLevel, oldRegionCode: treeNode.id};
                    }
                }
            });
            if (!flag) {
                return flag;
            }
        }
        // 修改行政区
        $.ajax({
            type: "post",
            data: params,
            url: url,
            async: false,
            success: function (data) {
                _alertResult('ajaxResultDiv', data.flag, data.message);
                if (!data.flag) {
                    return false;
                }
                treeNode.id = regionCode;
            }
        });
        return true;
    }

    treeOptions["beforeRemove"] = function(treeId, treeNode){
        var flag = false;
        // 删除行政区
        $.ajax({
            type: "post",
            data: {regionCode: treeNode.id, regionName: treeNode.name, regionLevel: treeNode.regionLevel},
            url: "${base}/region/deleteRegion",
            async: false,
            success: function (data) {
                _alertResult('ajaxResultDiv', data.flag, data.message);
                if (data.flag) {
                    flag = true;
                }
            }
        });
        return flag;
    }

</script>
</body>
</html>