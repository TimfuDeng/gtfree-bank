<html>
<head>
    <title>CA注册管理</title>
    <meta name="menu" content="menu_admin_userlist"/>
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

        .error_input{
            background-color: #FFFFCC;
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
                beforeClick: beforeClick,
                onClick: onClick
            }
        };
        $(document).ready(function(){


            $('#btnCA').click(function(){

                gtmapCA.initializeCertificate(document.all.caOcx);
                if(gtmapCA.checkValidCertificate()){
                    var userName = gtmapCA.getCertificateFriendlyUser();

                    $('#viewName').val(userName);
                    $('#caThumbprint').val(gtmapCA.getCertificateThumbprint());
                    var beforeTime=new XDate(gtmapCA.getCertificateNotBeforeSystemTime());
                    $('#caNotBeforeTime').val(beforeTime.toString("yyyy-MM-dd HH:mm:ss"));
                    var afterTime=new XDate(gtmapCA.getCertificateNotAfterSystemTime());
                    $('#caNotAfterTime').val(afterTime.toString("yyyy-MM-dd HH:mm:ss"));
                    $('#caCertificate').val(gtmapCA.getCertificateContent());
                    $('#userName').val(userName);
                    var numberIndex=isNumberIndex(userName);
                    $('#certificateId').val(userName.substring(numberIndex));

                }
            });

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

        /**
         * 判断字符串的第N个是数字，返回下标
         * @param obj
         * @returns {number}
         */
        function isNumberIndex(obj){
            var userName=obj+"";
            if(userName.length>0){
                for(var i=0;i<userName.length;i++){
                    if(!isNaN(parseInt(userName.charAt(i))))
                        return i;
                }
            }
        }


        function saveUser () {
            if (checkForm()) {
                var url = '${base}/ca-user/addCaRegister';
                var registerId = $("#registerId").val();
                if (!isEmpty(registerId)) {
                    var url = '${base}/ca-user/editCaRegister';
                }
                $.ajax({
                    type: "post",
                    url: url,
                    data: $("form").serialize(),
                    success: function (data) {
                        _alertResult('ajaxResultDiv', data.flag, data.message);
                        $("#registerId").val(data.empty.registerId);
                    }
                });
            }
        }

        function checkForm(){
            if (!checkInputNull('contactUser','联系人必须填写!'))
                return false;
            if (!checkInputNull('contactPhone','联系电话必须填写!'))
                return false;
            if (!checkInputNull('identityNum','组织机构代码（身份证）必须填写!'))
                return false;
//            if (!checkInputNull('certificateId','证件号必须填写!'))
//                return false;
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

        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeOrganize"),
                nodes = zTree.getSelectedNodes();
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("只能选择城市...");
            return check;
        }

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeOrganize"),
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
                regionCode += nodes[i].id + ",";
                regionName += nodes[i].name.substring(0, nodes[i].name.indexOf("(")) + ",";
            }
            if (regionCode.length > 0 ) {
                regionCode = regionCode.substring(0, regionCode.length - 1);
                regionName = regionName.substring(0, regionName.length - 1);
            }
            $("#regionName").val(regionName);
            $("#regionCode").val(regionCode);
        }

        function showMenu() {
            if (isEmpty($("#registerId").val())) {
                $("#menuContent").slideDown("fast");
                $("body").bind("mousedown", onBodyDown);
            }
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
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/ca-user/index')">CA注册管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transUser.viewName!}</span>
</nav>
<#--<#if _result??>
    <#if true==_result>
    <div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg!}</div>
    <#else>
    <div class="Huialert Huialert-danger"><i class="icon-remove"></i>${_msg!}</div>
    </#if>
</#if>
<div id="ajaxResultDiv" style="display:none"></div>-->
<form class="form-base" method="post">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">CA注册信息</td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">联系人：</label></td>
            <td><input type="text" class="input-text" id="contactUser"  name="contactUser" placeholder="联系人" value="${transCaRegister.contactUser!}" style="width:100%;background-color: #ffffcc;"></td>
            <td><label class="control-label">移动电话：</label></td>
            <td><input type="text" class="input-text" placeholder="移动电话" id="contactPhone" name="contactPhone" value="${transCaRegister.contactPhone!}" style="width:100%;background-color: #ffffcc;"></td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">类型：</label></td>
            <td>
                <select name="registerType" class="select">
                    <option value="COMPANY" <#if transCaRegister.registerType??&&transCaRegister.type=='COMPANY'>selected</#if>>企业</option>
                    <option value="PERSON" <#if transCaRegister.registerType??&&transCaRegister.type=='PERSON'>selected</#if>>自然人</option>
                </select>
            </td>
            <td width="120"><label class="control-label">组织机构代码<br>(身份证号)</label></td>
            <td>
                <input type="text" class="input-text" id="identityNum" placeholder="组织机构代码/身份证号" name="identityNum" value="${transCaRegister.identityNum!}" style="width:100%;background-color: #ffffcc;">
            </td>
        </tr>
        <tr>
            <td><label class="control-label">所属行政区：</label></td>
            <td>
                <input type="text" class="input-text" id="regionName" name="regionName" value="${transRegion.regionName!}" readonly
                       style="width: 50%;background-color: background-color: #ffffcc;" maxlength="50">
                <input type="hidden" id="regionCode" name="regionCode" value="${transRegion.regionCode!}" />
                <#if !transCaRegister.registerId?exists>
                <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
                </#if>
                <div id="menuContent" class="menuContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 145px;width: 250px; ">
                    <ul id="treeOrganize" class="ztree" style="margin-top:0; width:160px;"></ul>
                </div>
            </td>
            <td><label class="control-label">状态：</label></td>
            <td>
                <select name="registerStatus" class="select">
                    <option value="ENABLED" <#if transCaRegister.registerStatus??&&transCaRegister.registerStatus=='ENABLED'>selected</#if>>启用</option>
                    <option value="DISABLED" <#if transCaRegister.registerStatus??&&transCaRegister.registerStatus=='DISABLED'>selected</#if>>停用</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">描述：</label></td>
            <td colspan="3">
                <textarea type="text" placeholder="描述" id="registerDescribe" name="registerDescribe" style="width: 100%" rows="5">${transCaRegister.registerDescribe!}</textarea>
            </td>
        </tr>

        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">数字证书信息</td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">证书指纹：</label></td>
            <td><input type="text" class="input-text" id="caThumbprint" placeholder="数字证书指纹" name="caThumbprint" value="${chackUser.caThumbprint!}" style="width:100%" readonly></td>
            <td><label class="control-label"></label></td>
            <td></td>
        </tr>
        <#--<tr>-->
            <#--<td width="120"><label class="control-label">证书启用时间：</label></td>-->
            <#--<td><input type="text" class="input-text" id="caNotBeforeTime" readonly placeholder="证书启用时间" name="caNotBeforeTime" value="${chackUser.caNotBeforeTime!}" style="width:100%"></td>-->
            <#--<td><label class="control-label"></label>证书失效时间：</td>-->
            <#--<td><input type="text" class="input-text" id="caNotAfterTime" readonly placeholder="证书失效时间" name="caNotAfterTime" value="${chackUser.caNotAfterTime!}" style="width:100%"></td>-->
        <#--</tr>-->
        <tr>
            <td width="120"><label class="control-label">证书启用时间：</label></td>
            <td><input type="text" class="input-text" id="caNotBeforeTime" readonly placeholder="证书启用时间" name="caNotBeforeTime" value="${chackUser.caNotBeforeTime!}" style="width:100%"></td>
            <td><label class="control-label"></label>证书失效时间：</td>
            <td><input type="text" class="input-text" id="caNotAfterTime" readonly placeholder="证书失效时间" name="caNotAfterTime" value="${chackUser.caNotAfterTime!}" style="width:100%"></td>
        </tr>
        <tr>
            <td><label class="control-label">证书内容：</label></td>
            <td colspan="3">
                <textarea type="text" placeholder="证书内容" id="caCertificate" name="caCertificate" style="width: 100%" rows="20" readonly>${chackUser.caCertificate!}</textarea>
            </td>
        </tr>
    </table>

    <input type="hidden" id="registerId" name="registerId" value="${transCaRegister.registerId!}">
    <input type="hidden" id="userId" name="userId" value="${transCaRegister.userId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <#if !transCaRegister.registerId?exists >
            <button type="button" class="btn btn-primary" id="btnCA">导入CA证书</button>
            </#if>
            <button type="button" class="btn btn-primary" onclick="saveUser();">保存</button>
            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/ca-user/index?userType=1')">返回</button>
        </div>
    </div>
</form>
<@Ca autoSign=0  />
</body>
</html>