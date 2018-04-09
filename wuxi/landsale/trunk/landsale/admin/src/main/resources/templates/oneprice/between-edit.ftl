<html>
<head>
    <title>-参数录入</title>
    <meta name="menu" content="menu_oneprice_betweenlist"/>
    <link rel="stylesheet" href="${base}/tree/zTreeStyle/zTreeStyle.css" type="text/css">
    <style type="text/css">
        .title-info h1, .title-info h3 {
            margin: 0;
            padding: 0 0 0 20px;
            font-family: "Microsoft Yahei";
            font-weight: 400;
        }
        .btn-upload input{
            height:55px;
            cursor: pointer;
        }


    </style>

    <script type="text/javascript" src="${base}/thridparty/uploadifive/jquery.uploadifive.min.js"></script>
    <script type="text/javascript" src="${base}/tree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript">
        function checkForm(){
            if (!checkInputNull('waitTime','等待时长必须填写!'))
                return false;
            if (!checkInputNull('queryTime','查询时长必须填写!'))
                return false;
            if (!checkInputNull('priceTime','报价时长必须填写!'))
                return false;
            if (!checkInputNumber('waitTime','等待时长必须是数字!'))
                return false;
            if (!checkInputNumber('queryTime','查询时长必须是数字!'))
                return false;
            if (!checkInputNumber('priceTime','报价时长必须是数字!'))
                return false;
            if (!checkInputInteger('waitTime','等待时长必须是正整数!'))
                return false;
            if (!checkInputInteger('queryTime','查询时长必须是正整数!'))
                return false;
            if (!checkInputInteger('priceTime','报价时长必须是正整数'))
                return false;
            if (!checkInputNull('regionCode','行政区必须选择!'))
                return false;
            var priceGuid = $("input[name='priceGuid']").val();
            var priceMin = $("input[name='priceMin']").val();
            var priceMax = $("input[name='priceMax']").val();
            submitForm();
//            return true;
        }
        function checkInputNumber(name,info){
            if(isNaN($("input[name='"+name+"']").val())){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }
        function checkInputInteger(name,info){
            if($("input[name='"+name+"']").val()!="" && $("input[name='"+name+"']").val()<=0){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else if($("input[name='"+name+"']").val().indexOf(".")>=0){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
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

        function submitForm() {
            $.ajax({
                url:"${base!}/oneprice/between/save",//提交地址
                data:$("#form1").serialize(),
                type:"POST",
                async:"false",
                dataType:"json",
                success:function(result){
                    if (result.flag == true){
                        _alertResult('ajaxResultDiv', true, result.message);
                    }else{
                        _alertResult('ajaxResultDiv', false, result.message);
                    }
                }
            });
        }
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
        $(document).ready(function () {
            loadTree();
        });

        function loadTree () {
            $.ajax({
                type: "post",
                dataType: "json",
                data: {regionCode: '${regionCodes!}'},
                url: "${base}/region/getRegionTree",
                success: function (data) {
                    if (data != null) {
                        if (data.length == 1) {
                            $("#regionCode").val(data[0].id);
                            $("#regionName").val(data[0].name.substring(0, data[0].name.indexOf("(")));
                            regionChanged();
                        }
                        $.fn.zTree.init($("#treeRegion"), setting, data);
                    }
                }
            });
        }
        // Tree操作
        function beforeClick(treeId, treeNode) {
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("只能选择城市...");
            return check;
        }

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeRegion"),
                    nodes = zTree.getSelectedNodes(),
                    regionCode = "",
                    regionName = "";
            if (nodes.length > 1) {
                alert("只能选择一个城市...");
                return false;
            }
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, len = nodes.length; i < len; i++) {
                regionCode += nodes[i].id;
                regionName += nodes[i].name.substring(0, nodes[i].name.indexOf("("));
            }
            $("#regionName").val(regionName);
            $("#regionCode").val(regionCode);
        }

        function showMenu() {
            var regionName = $("#regionName");
            var regionNameOffset = $("#regionName").get(0).offsetLeft;
            $("#menuContent").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
//        regionChanged();
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        function getCheckedMaterials(){
            if ($("#materialGroupList input[name='materialId']").length > 0) {
                $("#materialGroupTr").show();
            }
            if ($("#materialPersonList input[name='materialId']").length > 0) {
                $("#materialPersonTr").show();
            }
            var ggId =$("#ggId").val();
            $.ajax({
                url:"${base}/crgg/materials/checked",
                type:"post",
                data:{ggId:ggId},
                async:false,
                success:function(data){
                    if(data!=""){//如果没有选择默认选择所有
                        $.each(data,function(k,v){
                            $("input[name='materialId']").each(function(){
                                if($(this).val()== v.materialId){
                                    $(this).attr("checked","checked");
                                }
                            });
                        });
                    }else{
                        checkedMaterials();
                    }
                },
                error:function(){
                    alert("error");
                }
            });
        }

    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="javascript:changeSrc('${base}/oneprice/resource/list')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/oneprice/between/list')">参数设置</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> 参数录入</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg!}</div>
</#if>

<form class="form-base" method="post" id="form1" action="${base!}/oneprice/between/save">
    <table class="table table-border table-bordered table-striped">
        <tbody>
        <tr>
            <td colspan="2"><label class="control-label">等待时长（分钟）：</label></td>
            <td colspan="3"><input type="input" class="input-text" name="waitTime" value="${oneParam.waitTime!}"></td>

        </tr>
        <tr>
            <td colspan="2"><label class="control-label">询问时长（分钟）：</label></td>
            <td colspan="3"><input type="input"  class="input-text" name="queryTime" value="${oneParam.queryTime!}"></td>

        </tr>
        <tr>
            <td colspan="2"><label class="control-label">报价时长（分钟）：</label></td>
            <td colspan="3"><input type="input" class="input-text" name="priceTime" value="${oneParam.priceTime!}"></td>
        </tr>
        <#--<tr>-->
            <#--<td colspan="2"><label class="control-label">最高限价间隔时间（分钟）：</label></td>-->
            <#--<td colspan="3"><input type="input" class="input-text" name="priceTime" value="${oneParam.maxIntervalTime!}"></td>-->
        <#--</tr>-->
        <tr>
            <td colspan="2"><label class="control-label">行政区部门 ：</label></td>
            <td colspan="3">
                <div>
                    <input type="text" class="input-text" id="regionName" name="regionName" value="${transRegion.regionName!}" readonly
                           style="width: 80%;background-color: background-color: #ffffcc;" maxlength="50">
                    <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                    <input type="hidden" id="regionCode" name="regionCode" value="${transRegion.regionCode!}" />
                    <div id="menuContent" class="menuContent" style="display:none; position: absolute; background-color:#F5F5F5; width: 250px;z-index: 101; ">
                        <ul id="treeRegion" class="ztree" style="margin-top:0; width:160px;"></ul>
                    </div>
                </div>
            </td>
        </tr>
        <input type="hidden"  name="id" value="${oneParam.id!}">
        <#--<tr>

            <td colspan="2"><input type="input" class="input-text" name="priceMin" value="125"></td>

            <td class="text-c"> <label class="control-label">报价区间（%）</label></td>

            <td colspan="2"><input type="input"  class="input-text" name="priceMax" value="150"></td>
        </tr>-->
        </tbody>
    </table>
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="checkForm()">保存</button>
            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/oneprice/between/list')">返回</button>
        </div>
    </div>
</form>
</body>
</html>