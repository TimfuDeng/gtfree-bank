<!DOCTYPE HTML>
<html>
<head>
<title>附件管理</title>
<meta name="keywords" content="附件管理">
<meta name="description" content="附件管理">
<link href="/txgc/css/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="/txgc/css/h-ui/css/style.css" rel="stylesheet" type="text/css" />
<link href="/txgc/css/h-ui/css/H-ui.doc.css" rel="stylesheet" type="text/css">
<link href="/txgc/css/h-ui/css/H-ui.admin.css" rel="stylesheet" type="text/css">
<link href="/txgc/css/h-ui/css/laypage.css" rel="stylesheet" type="text/css">
<link href="/txgc/css/h-ui/css/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    th{ background-color: #EEEEEE;}
</style>
</head>
<body style="min-width:100%;">
<div id="mask" align="center" class="mask opacity" onclick="javacscropt:void(0);" style="display:none;z-index:1000;">
	<img id="smMask" alt="" align="middle"  src="/txgc/images/loading.gif" style="margin:auto;">
</div><!--  主要内容区域   -->
<div style="overflow-y: auto; height: 270px; width:600px;"">
	<table class="table table-border table-bordered">
		<form id="fjForm" action="fjsc" enctype="multipart/form-data" method="post" target="_self">
			<input type="hidden" name="guid" value="${guid!}">
			<input type="hidden" name="username" value="${username!}">
			<tr>
        		<th class="text-r" style="width:60px;">
        		附件上传:
        		</th>
        		<td>
        			<input type="file" name="fujian"  id = "file" />
        		</td>
        		<td colspan="1" style="width:80px;">
        			<input type="button" class="btn btn-primary radius" onclick="javascript:fjsc()" value="上传" />
        		</td>
        	</tr>
        	</form>
    		<tr class="text-c">
        		<th>
        			附件列表
        		</th>
        		<th>
        			文件名 
        		</th>
        		<th> 操作
        		</th>
        	</tr>
        	<#if fjList??>
        	<#list fjList as fj>
        		<tr id="${fj['FJ_GUID']}" style="font-size:15px;background:#fff;">
        			<th class="text-c">
        				${fj_index+1}
        			</th>
        			<td style="font-size:15px;">
        				${fj['FJ_MC']}
        			</td>
        			<td class="text-c" style="font-size:18px;">
        				<a style='margin-left:10px;' title='删除' href='javascript:deleteFj("${fj['FJ_GUID']}")'><i class='Hui-iconfont'>&#xe60b;</i></a>
        				<a title='下载' style='margin-left:10px;' href='javascript:downloadFj("${fj['FJ_GUID']}")'><i class='Hui-iconfont'>&#xe641;</i></a>
        			</td>
        		</tr>
        	</#list>
        	</#if>
	</table>
	</div>
<!--end  主要内容区域   -->
<!--end 弹出层 -->
<script type="text/javascript" src="/txgc/js/jquery.min.js"></script> 
<!--<script type="text/javascript" src="/txgc/static/h-ui/js/H-ui.js"></script> -->

<!--  调用jQueryUI实现拖动 --> 
<script src="/txgc/js/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	window.$("#mask").hide();
});

function fjsc(){
	var file = $("#file").val();
	if(file.length==0){
		alert("请选择文件！");
		return;
	}
	window.$("#mask").show();
	$("#fjForm").submit();
}
   
function deleteFj(guid){
  if(confirm("确定要删除此附件?")){
   	$.ajax({
    	url:"/txgc/deleteFj?guid="+guid,
		type:'POST',
		contentType: 'text/json',
		success:function(text){
			var txt = "删除成功!";
			document.location.reload();
		}
    });
  }
  else{
    return false;
  }
}

function downloadFj(guid){
	window.open("/txgc/download?guid="+guid);
}
</script>
</body>
</html>
<!--H-ui前端框架提供前端技术支持 h-ui.net @2015-4-30 -->