<html>
<head>
    <title>-银行开通</title>
    <meta name="menu" content="menu_admin_banklist"/>
    <link rel="stylesheet" href="${base}/tree/zTreeStyle/zTreeStyle.css" type="text/css">
    <link href="${base}/upload/uploadfile.css" rel="stylesheet">
    <style type="text/css">
        td input{
            padding-right: 0px;
        }
    </style>
    <script type="text/javascript" src="${base}/tree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${base}/upload/jquery.form.js"></script>
    <script type="text/javascript" src="${base}/upload/jquery.uploadfile.js"></script>
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

        var uploadObj;
        $(document).ready(function(){
            loadImage();
            loadTree();
        });

        function loadTree () {
            $.ajax({
                type: "post",
                dataType: "json",
                url: "${base}/region/getRegionTree",
                success: function (data) {
                    if (data != null) {
                        $.fn.zTree.init($("#treeRegion"), setting, data);
                    }
                }
            });
        }

        function loadImage () {
            uploadObj = $('#fileuploader').uploadFile({
                url: "${storage}/storage/upload",
                allowedTypes: "PG,JPEG,GIF,PNG,JPG",
                extErrorStr: "上传文件类型错误,支持类型：",
                maxFileSize: 4194304,
                sizeErrorStr: "超过最大限制,最大上传限制：",
                fileCounterStyle: ".",
                uploadQueueOrder: "bottom",
                dragDrop: true,
                dragDropStr: "<span><b>拖拽上传文件</b></span>",
                uploadStr: "点击上传",
                showPreview: true,
                showProgress: true,
                showAbort: false,
                showDelete: true,
                deletelStr: "删除",
                maxFileCount: 1,
                maxFileCountErrorStr: "超过上传数量,最大上传数量：",
                previewHeight: 100,
                previewWidth: 100,
                statusBarWidth: 120,
                onSelect: function (files) {
                },
                onSuccess: function (files, response, xhr, pd) {
                    $("#bankIcon").val(response[0].id);
                    $("#bankIconName").val(response[0].name);
                },
                deleteCallback: function (data, pd) {
                    $("#bankIcon").val("");
                    $("#bankIconName").val("");
                    return true;
                }
            });

            // 图片
            var bankIcon = $("#bankIcon").val();
            var bankIconName = $("#bankIconName").val();
            if (!isEmpty(bankIcon)) {
                uploadObj.createProgress(bankIconName, "${storage}/storage/download/" + bankIcon);
            }

        }

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
            $("#menuContent").css({left:regionNameOffset + 140 + "px"}).slideDown("fast");
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

        function isIP(ip) {
            if(ip=="")
                return false;
            var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/
            return pattern.test(ip);
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

        function checkForm(){
            checkInputFileter();
            if(!checkInputNull("bankName","银行名称必须填写"))
                return false;
            if(!checkInputNull("bankCode","银行简称代码必须填写"))
                return false;
            if(!checkInputNull("bankIcon","银行图标必须上传"))
                return false;
            if(!checkInputNull("regionName","所属行政区必须选择"))
                return false;
            if(!checkInputNull("accountCode","银行账号必须填写"))
                return false;
            if(!checkInputNull("accountName","账户名称必须填写"))
                return false;
            if(!checkInputNull("interfaceIp","银行接口IP必须填写"))
                return false;
            if(!checkInputNull("interfacePort","银行接口端口必须填写"))
                return false;
            if(isNaN($("input[name='accountCode']").val())){
                alert("请填写正确的银行账号");
                return false;
            }
            if(isNaN($("input[name='interfacePort']").val())){
                alert("请填写正确的银行接口端口");
                return false;
            }
            if(!isIP($("input[name='interfaceIp']").val())){
                alert("请填写正确的银行接口IP");
                return false;
            }
            return true;
        }

        function submitForm () {
            if (checkForm()) {
                var url;
                if (isEmpty($("#bankId").val())) {
                    url = "${base}/bank/addBank";
                } else {
                    url = "${base}/bank/editBank";
                }
                $.ajax({
                    type: "post",
                    url: url,
                    data: $("form").serialize(),
                    success: function (data) {
                        $("#bankId").val(data.empty.bankId);
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                    }
                });
            }
        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/bank/index')">银行管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transBank.bankName!}</span>
</nav>

<form class="form-base" method="post" action="${base}/bank/save">
    <table class="table table-border table-bordered table-striped">
        <tr>

        </tr>
        <tr>
            <td width="120"><label class="control-label">银行名称：</label></td>
            <td>
                <input type="text" class="input-text" name="bankName" value="${transBank.bankName!}" style="width:300px" maxlength="32">
            </td>
            <td width="120"><label class="control-label">银行简称代码：</label></td>
            <td>
                <input type="text" class="input-text" name="bankCode" value="${transBank.bankCode!}" style="width:300px" maxlength="32">
            </td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">所属行政区：</label></td>
            <td colspan="3">
                <input type="text" class="input-text" id="regionName" name="regionName" value="${transRegion.regionName!}" readonly
                       style="width: 300px;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="regionCode" name="regionCode" value="${transRegion.regionCode!}" />
                <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                <div id="menuContent" class="menuContent" style="display:none; position: absolute; background-color:#F5F5F5; width: 250px;z-index: 101; ">
                    <ul id="treeRegion" class="ztree" style="margin-top:0; width:160px;"></ul>
                </div>
            </td>
        </tr>

        <tr>
            <td width="120"><label class="control-label">银行图标：</label></td>
            <td colspan="3">
                <input type="hidden" id="bankIcon" name="bankIcon" value="${transBank.bankIcon!}"/>
                <input type="hidden" id="bankIconName" name="bankIconName" value="${transBank.bankIconName!}"/>
                <div style="border: none;width: 100%;height:65px;" id="fileuploader"></div>
                <p>最多上传1张，单张照片最大4MB，支持：PG/JPEG/GIF/PNG格式</p>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">银行账号：</label></td>
            <td colspan="3"><input type="text" class="input-text" name="accountCode" value="${transBank.accountCode!}" style="width:400px;background-color: #FFFFCC" maxlength="32" >
                <div class="radio-box">
                    <label for="control-label">账户类型：</label>

                        <input type="radio" id="radio-1" name="moneyUnit" value="CNY" <#if "CNY"==transBank.moneyUnit> checked="true" </#if>>
                        <label for="control-label">人民币</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="radio-2" name="moneyUnit" value="USD"<#if "USD"==transBank.moneyUnit> checked="true" </#if>>
                        <label for="control-label">美元</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="radio-3" name="moneyUnit" value="HKD" <#if "HKD"==transBank.moneyUnit> checked="true" </#if>>
                        <label for="control-label">港币</label>

                </div>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">账户名称：</label></td>
            <td colspan="3"><input type="text" class="input-text" name="accountName" value="${transBank.accountName!}" style="width:400px;background-color: #FFFFCC" maxlength="50">例如：**市国土资源局</td>
        </tr>
        <tr>
            <td><label class="control-label">银行接口IP：</label></td>
            <td colspan="3"><input type="text" class="input-text" name="interfaceIp" value="${transBank.interfaceIp!}" style="width:400px;background-color: #FFFFCC" maxlength="32">例如：192.168.10.109</td>
        </tr>
        <tr>
            <td><label class="control-label">银行接口端口：</label></td>
            <td colspan="3"><input type="text" class="input-text" name="interfacePort" value="${transBank.interfacePort!}" style="width:400px;background-color: #FFFFCC" maxlength="10" >例如：5006</td>
        </tr>
    </table>

    <input type="hidden" id="bankId" name="bankId" value="${transBank.bankId!}">
    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" onclick="submitForm()">保存</button>
            <button type="button" class="btn" onclick="changeSrc('${base}/bank/index')">返回</button>
        </div>
    </div>
</form>
</body>
</html>