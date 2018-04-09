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
    <script type="text/javascript" src="${base}/tree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript">
        var regionSetting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData : {
                    enable : true
                }
            },
            callback: {
                beforeClick: beforeRegionClick,
                onClick: onRegionClick
            }
        };

        var tdytSetting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "", "N": "" }
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData : {
                    enable : true
                }
            },
            callback: {
//                beforeCheck: beforeTdytClick,
                onCheck: onTdytClick
            }
        };
        $(document).ready(function(){

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
                        $.fn.zTree.init($("#treeRegion"), regionSetting, data);
                    }
                }
            });

           $.ajax({
                type: "post",
                dataType: "json",
                data: {regionCode: '${regionCodes!}', tdytDictCode: '${transFloorPriceView.tdytDictCode!}'},
                url: "${base}/floorPrice/getLandUseDictTree",
                success: function (data) {
                    if (data != null) {
                        $.fn.zTree.init($("#treeTdyt"), tdytSetting, data);
                    }
                }
            });
        });

        function beforeRegionClick(treeId, treeNode) {
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("只能选择城市...");
            return check;
        }

        function onRegionClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeRegion"),
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
            if (regionCodes.length > 0 ) {
                regionCodes = regionCodes.substring(0, regionCodes.length - 1);
                regionNames = regionNames.substring(0, regionNames.length - 1);
            }
            $("#regionNames").val(regionNames);
            $("#regionCodes").val(regionCodes);
        }


        function showRegionMenu() {
            $("#RegionContent").slideDown("fast");
            $("body").bind("mousedown", onRegionBodyDown);
        }

        function hideRegionMenu() {
            $("#RegionContent").fadeOut("fast");
            $("body").unbind("mousedown", onRegionBodyDown);
        }

        function onRegionBodyDown(event) {
            if (!(event.target.id == "regionMenuBtn" || event.target.id == "RegionContent" || $(event.target).parents("#RegionContent").length > 0)) {
                hideRegionMenu();
            }
        }

        function clearRegion () {
            $("#regionNames").val("");
            $("#regionCodes").val("");
        }


        //  点击地块用途菜单执行事件
        function beforeTdytClick(treeId, treeNode) {
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("只能选择用途...");
            return check;
        }

        function onTdytClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeTdyt"),
                    nodes = zTree.getCheckedNodes(true),
                    tdytDictCodes = "",
                    tdytDictNames = "";
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, len = nodes.length; i < len; i++) {
                tdytDictCodes += nodes[i].id + ",";
                tdytDictNames += nodes[i].name + ",";
            }
            if (tdytDictCodes.length > 0 ) {
                tdytDictCodes = tdytDictCodes.substring(0, tdytDictCodes.length - 1);
                tdytDictNames = tdytDictNames.substring(0, tdytDictNames.length - 1);
            }
            $("#tdytDictNames").val(tdytDictNames);
            $("#tdytDictCodes").val(tdytDictCodes);
        }


        function showTdytMenu() {
            $("#tdytContent").slideDown("fast");
            $("body").bind("mousedown", onTdytBodyDown);
        }

        function hideTdytMenu() {
            $("#tdytContent").fadeOut("fast");
            $("body").unbind("mousedown", onTdytBodyDown);
        }

        function onTdytBodyDown(event) {
            if (!(event.target.id == "tdytMenuBtn" || event.target.id == "tdytContent" || $(event.target).parents("#tdytContent").length > 0)) {
                hideTdytMenu();
            }
        }

        function clearTdyt () {
            $("#tdytDictNames").val("");
            $("#tdytDictCodes").val("");
        }

        //保存授权信息
        function saveGrant () {
            if(checkForm()){
                $.ajax({
                    type: "post",
                    url: '${base}/floorPrice/grant/save',
                    data: $("form").serialize(),
                    success:function(data){
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        if(data.flag){
                            $("#userId").val(data.empty.userId);
                        }
                    }
                });
            }
        }

        function checkForm(){
            if (!checkInputNull('regionNames','行政区必须填写!'))
                return false;
            if (!checkInputNull('tdytDictCodes','用途必须填写!'))
                return false;
            return true;
        }

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


    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/floorPrice/index?userType=0')">底价配置</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transFloorPriceView.viewName!}</span>
</nav>

<form class="form-base" name="grantForm" method="post" action="${base}/floorPrice/grant/save">
    <h4><small>数据授权：</small></h4>
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="220px"><label class="control-label">授权行政区：</label></td>
            <td colspan="2">
                <input type="text" class="input-text" id="regionNames" name="regionNames" value="${transRegionName!}" readonly
                       style="width: 50%;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="regionCodes" name="regionCodes" value="${transRegionCode!}" />
                <a id="regionMenuBtn" href="#" onclick="showRegionMenu(); return false;">选择</a>
                <a href="#" onclick="clearRegion(); return false;">清空</a>
                <div id="RegionContent" class="RegionContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 245px;width: 250px;z-index: 1; ">
                    <ul id="treeRegion" class="ztree" style="margin-top:0; width:160px;z-index: 1;"></ul>
                </div>
            </td>
        </tr>
    </table>

    <h4><small>資源授权：</small></h4>
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td width="220px"><label class="control-label">授权土地用途：</label></td>
            <td colspan="2">
                <input type="text" class="input-text" id="tdytDictNames" name="tdytDictNames" value="${tdytNames!}" readonly
                       style="width: 50%;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="tdytDictCodes" name="tdytDictCodes" value="${transFloorPriceView.tdytDictCode!}" />
                <a id="tdytMenuBtn" href="#" onclick="showTdytMenu(); return false;">选择</a>
                <a href="#" onclick="clearTdyt(); return false;">清空</a>
                <div id="tdytContent" class="RegionContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 245px;width: 250px;z-index: 1; ">
                    <ul id="treeTdyt" class="ztree" style="margin-top:0; width:160px;z-index: 1;"></ul>
                </div>
            </td>
        </tr>
    </table>

    <input type="hidden" id="userId" name="userId" value="${transFloorPriceView.userId!}">
    <input type="hidden" id="privileges" name="privileges">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="saveGrant();">保存</button>
            <button type="button" class="btn" onclick="changeSrc('${base}/floorPrice/index?userType=0')">返回</button>
        </div>
    </div>
</form>
</body>

</html>