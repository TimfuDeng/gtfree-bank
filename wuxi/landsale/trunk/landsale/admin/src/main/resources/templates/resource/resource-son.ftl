<html>
    <head>
        <title>多规划用途</title>
        <meta name="menu" content="menu_admin_resourcelist"/>
        <!--[if lt IE 9]>
        <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/html5.js"></script>
        <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/respond.min.js"></script>
        <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/PIE_IE678.js"></script>
        <![endif]-->
        <link href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
        <link href="${base}/thridparty/H-ui.2.0/static/h-ui/style.css" rel="stylesheet" type="text/css" />
        <link href="${base}/thridparty/H-ui.2.0/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
        <link href="${base}/thridparty/H-ui.2.0/lib/bootstrapSwitch/bootstrapSwitch.css" rel="stylesheet" type="text/css" />
        <link href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!--[if IE 7]>
        <link href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
        <![endif]-->
        <link href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
        <!--[if IE 6]>
        <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
        <script>DD_belatedPNG.fix('*');</script>
        <![endif]-->
        <link href="${base}/css/landsale.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="${base}/tree/zTreeStyle/zTreeStyle.css" type="text/css">
        <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
        <script type="text/javascript" src="${base}/layer/layer.js"></script>
        <script type="text/javascript" src="${base}/js/main.js"></script>
        <script type="text/javascript" src="${base}/tree/js/jquery.ztree.core.js"></script>
        <script type="text/javascript" src="${base}/tree/js/jquery.ztree.excheck.js"></script>
        <script type="text/javascript">
            //土地用途
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
                    beforeCheck: beforeTdytClick,
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
                });
                $.ajax({
                    type: "post",
                    dataType: "json",
                    data: {regionCode: '${regionCodes!}', tdytDictCode: '${transFloorPriceView.tdytDictCode!}'},
                    url: "${base}/resource/getLandUseDictTree",
                    success: function (data) {
                        if (data != null) {
                            $.fn.zTree.init($("#treeTdyt"), tdytSetting, data);
                        }
                    }
                });
            });

            //  点击地块用途菜单执行事件
            function beforeTdytClick(treeId, treeNode) {
                /*var check = (treeNode && !treeNode.isParent);
                if (!check) alert("只能选择一个用途...");*/
                var zTree = $.fn.zTree.getZTreeObj("treeTdyt");
                var nodes = zTree.getCheckedNodes(true);
                if (nodes.length > 0) {
                    if (treeNode.id != nodes[0].id) {
                        alert("只能选择一个用途...");
                        return false;
                    }
                }
                return true;
            }

            function onTdytClick(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("treeTdyt"),
                        nodes = zTree.getCheckedNodes(true),
                        tdytCode = "",
                        tdytName = "";
               /* if (nodes.length > 1) {
                    alert("只能选择一个用途...");
                    return false;
                }*/
                nodes.sort(function compare(a, b) {
                    return a.id - b.id;
                });
                for (var i = 0, len = nodes.length; i < len; i++) {
                    tdytCode += nodes[i].id + ",";
                    tdytName += nodes[i].name + ",";
                }
                if (tdytCode.length > 0 ) {
                    tdytCode = tdytCode.substring(0, tdytCode.length - 1);
                    tdytName = tdytName.substring(0, tdytName.length - 1);
                }
                $("#tdytName").val(tdytName);
                $("#tdytCode").val(tdytCode);
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
                $("#tdytName").val("");
                $("#tdytCode").val("");
            }

            function checkForm() {
                var minRjl = $("input[name='minRjl']").val();
                var maxRjl = $("input[name='maxRjl']").val();
                var minJzMd = $("input[name='minJzMd']").val();
                var maxJzMd = $("input[name='maxJzMd']").val();
                var minLhl = $("input[name='minLhl']").val();
                var maxLhl = $("input[name='maxLhl']").val();
                var minJzXg = $("#zxjzxg").val();
                var maxJzXg = $("#zdjzxg").val();

                var chenkArr = [{"number": minRjl, "name": "最小容积率"},
                    {"number": maxRjl, "name": "最大容积率"},
                    {"number": minJzMd, "name": "最小建筑密度"},
                    {"number": maxJzMd, "name": "最大建筑密度"},
                    {"number": minLhl, "name": "最小绿化率"},
                    {"number": maxLhl, "name": "最大绿化率"},
                    {"number": minJzXg, "name": "最小建筑限高"},
                    {"number": maxJzXg, "name": "最大建筑限高"}
                ];
                // 数字验证
                for (var i = 0, len = chenkArr.length; i < len; i++) {
                    if (isNaN(chenkArr[i].number)) {
                        alert(chenkArr[i].name + "必须是数字！");
                        return false;
                    } else if (chenkArr[i].number != "") {
                        if ((chenkArr[i].number * 1) <= 0) {
                            alert(chenkArr[i].name + "必须大于零！");
                            return false;
                        }
                    }
                }

                if(parseInt(minJzXg)>parseInt(maxJzXg)){
                    alert("最小建筑限高不能大于最大建筑限高");
                    return false;
                }

                if(parseInt(minLhl)>parseInt(maxLhl)){
                    alert("最小绿化率不能大于最大绿化率");
                    return false;
                }

                if(parseInt(minJzMd)>parseInt(maxJzMd)){
                    alert("最小建筑密度不能大于最大建筑密度");
                    return false;
                }

                if(parseInt(minRjl)>parseInt(maxRjl)){
                    alert("最小建容积率不能大于最大容积率");
                    return false;
                }

                $("input[name='sonYearCount']").each(function(){
                    var sonYearCount=$(this).val();
                    if(isNaN(sonYearCount)){
                        alert("交易地块多规划用途信息的年限必须是数字！");
                        return false;
                    }
                });

                $("input[name='sonYearCount']").each(function(){
                    var sonYearCount=$(this).val();
                    if(sonYearCount<=0 && sonYearCount!=""){
                        alert("交易地块多规划用途信息的年限必须是大于零的正数！");
                        return false;
                    }
                });

                $("input[name='zdArea']").each(function(){
                    var zdArea=$(this).val();
                    if(isNaN(zdArea)){
                        alert("交易地块多规划用途信息的面积必须是数字！");
                        return false;
                    }
                });

                $("input[name='zdArea']").each(function(){
                    var zdArea=$(this).val();
                    if(zdArea<=0 && zdArea!=""){
                        alert("交易地块多规划用途信息的面积必须是大于零的正数！");
                        return false;
                    }
                });
                return true;
            }

            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            function addSon () {
                if (checkForm()) {
                    $.ajax({
                        type: "post",
                        url: "${base}/resource/addResourceSon",
                        data: $("form").serialize(),
                        success: function (data) {
                            parent.afterResourceSon();
                            parent.layer.close(index);
                        }
                    });
                }
            }

            function compareNum (minNum, maxNumTag, minNumTag, maxNum) {
                var result = "";
                if (!isEmpty(minNum)) {
                    result = minNum + (maxNumTag == 00 ? "<" : "<=");
                }
                if (!isEmpty(maxNum)) {
                    if (isEmpty(result)) {
                        result = (minNumTag == 00 ? "<" : "<=") + maxNum;
                    } else {
                        result += "," + (minNumTag == 00 ? "<" : "<=") + maxNum;
                    }
                }
                return result;
            }

            //删除多用途地块
            function delResourceSon(obj){
                if(confirm("是否确认删除数据？")){
                    $(obj).parent().parent().remove();
                }
//            --zdFlag;
            }
        </script>
    </head>
    <body>
        <form class="form-base" method="post" >
        <table class="table table-border table-bordered table-striped">
            <tr>
                <td width="25%" align="center"><label class="control-label">宗地编号</label></td>
                <td width="25%" align="center"><label class="control-label">规划用途</label></td>
                <td width="25%" align="center"><label class="control-label">年限</label></td>
                <td width="25%" align="center"><label class="control-label">面积(平方米)</label></td>
            </tr>
            <tr>
                <td width="180"  align="center"><input type="text"id="zdCode" name="zdCode" class="input-text"  maxlength="12"></td>
                <td width="180" align="center">
                    <input type="text" class="input-text" id="tdytName" name="tdytName" value="${tdytName!}" readonly
                           style="width: 75%;background-color: background-color: #ffffcc;" maxlength="50">
                    <input type="hidden" id="tdytCode" name="tdytCode" value="${tdytCode!}" />
                    <a id="tdytMenuBtn" href="#" onclick="showTdytMenu(); return false;">选择</a>
                    <a href="#" onclick="clearTdyt(); return false;">清空</a>
                    <div id="tdytContent" class="RegionContent" style="display:none; position: absolute; background-color:#F5F5F5;left: 245px;width: 250px;z-index: 1; ">
                        <ul id="treeTdyt" class="ztree" style="margin-top:0; width:160px;z-index: 1;"></ul>
                    </div>
                </td>
                <td width="180" align="center"><input id="sonYearCount" type="text" name="sonYearCount" class="input-text" maxlength="12"></td>
                <td width="180" align="center"><input id="zdArea" type="text" name="zdArea" class="input-text" maxlength="12"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="text" name="minRjl" class="input-text"  style="width: 22%" maxlength="12">
                            <span class="select-box" style="width: 15%">
                                <select name="maxRjlTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>

                    <label class="control-label">容积率&nbsp;&nbsp;：</label>

                            <span class="select-box" style="width: 15%; margin-left: 18px;">
                                <select name="minRjlTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>
                    <input type="text" name="maxRjl" class="input-text"   style="width:22%"maxlength="12">
                </td>

                <td colspan="2">

                    <input type="text" name="minJzMd" class="input-text" maxlength="12" style="width: 22%">
                            <span class="select-box" style="width: 15%">
                                <select name="maxJzMdTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                             </span>


                    <label class="control-label">建筑密度 (%)：</label>

                            <span class="select-box" style="width: 15%">
                                <select name="minJzMdTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>
                    <input type="text" name="maxJzMd" class="input-text" maxlength="12" style="width: 22%">

                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="text" name="minLhl" class="input-text" maxlength="12" style="width: 22%">
                            <span class="select-box" style="width: 15%">
                                <select name="maxLhlTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>

                    <label class="control-label">绿化率(%) ：</label>

                            <span class="select-box" style="width: 15%">
                                <select name="minLhlTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>
                    <input type="text" name="maxLhl" class="input-text"  maxlength="12" style="width: 22%">
                </td>
                <td colspan="2">
                    <input type="text" id="zxjzxg" name="minJzxg" class="input-text" maxlength="12" style="width: 22%">
                            <span class="select-box" style="width: 15%">
                                <select name="maxJzXgTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>

                    <label class="control-label">建筑限高(m) ：</label>

                            <span class="select-box" style="width: 15%">
                                <select name="minJzXgTag" class="select" >
                                    <option value="00">&lt;</option>
                                    <option value="01">&le;</option>
                                </select>
                            </span>
                    <input type="text" id="zdjzxg" name="maxJzxg" class="input-text"  maxlength="12" style="width: 22%">
                </td>
            </tr>
        </table>
        <input type="hidden" id="resourceId" name="resourceId" value="${resourceId!}">
        </form>
        <div style="margin-top: 20px;width: 100%; text-align: center;">
            <button id="transmit" type="button" class="btn btn-primary" onclick="addSon();">保存</button>
        </div>
    </body>
</html>