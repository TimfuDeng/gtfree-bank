<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报价窗口</title>
<link id="changeStyle" href="${base}/css/easyui/bootstrap/easyui.css" type="text/css" rel="stylesheet"  />
<#--<link href="${base}/css/easyui/black/easyui.css" type="text/css" rel="stylesheet"  />-->
<#--<link href="${base}/css/easyui/default/easyui.css" type="text/css" rel="stylesheet"  />-->
<#--<link href="${base}/css/easyui/gray/easyui.css" type="text/css" rel="stylesheet"  />-->
<#--<link href="${base}/css/easyui/material/easyui.css" type="text/css" rel="stylesheet"  />-->
<link href="${base}/css/easyui/icon.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
<script type="text/javascript" src="${base}/thridparty/easyui/jquery.easyui.min.js"></script>
</head>

<body>
<#--<select onchange="changeStyle(this);">
    <option value="bootstrap">bootstrap</option>
    <option value="black">black</option>
    <option value="default">default</option>
    <option value="gray">gray</option>
    <option value="material">material</option>
    <option value="metro">metro</option>
</select>-->
<input type="hidden" id="resourceId" value="${resourceId!}" />
<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'" style="width:1180px;height:1000px">
</div>
<#--<div id="tab-tools">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="removePanel()"></a>
</div>-->
<script type="text/javascript">
    $(function () {
        $.ajax({
            type: "post",
            url: "${base}/resourceOffer/getResource",
            success: function (data) {
                for (var i = 0, len = data.length; i < len; i++) {
                    addPanel(data[i].resourceCode, data[i].resourceId);
                }
                $('#tt').tabs('select', '${resourceCode!}');
            }
        });
    });

    function selectTab(resourceCode) {
        $('#tt').tabs('select', resourceCode);
    }

    function addPanel(resourceCode, resourceId){
        $('#tt').tabs('add',{
            title: resourceCode,
            content: '<iframe scrolling="no" frameborder="0"  src="${base}/resource/decideResourceView?resourceId=' + resourceId + '" style="width:100%;height:100%;"></iframe>',
            closable: false
        });
    }
    function removePanel(){
        var tab = $('#tt').tabs('getSelected');
        if (tab){
            var index = $('#tt').tabs('getTabIndex', tab);
            $('#tt').tabs('close', index);
        }
    }

    // 改变主题
    function changeStyle(ele) {
        $("#changeStyle").attr("href", "${base}/css/easyui/" + $(ele).val() + "/easyui.css")
    }


</script>

</body>
</html>
