<html>
<head>
    <title>报名者附件材料</title>
    <meta name="menu" content="menu_admin_verify"/>
    <script type="text/javascript">
        function checkForm(){
            checkInputFileter();
            return true;
        }
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/verify/list?status=9&dealStatus=30">审核管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">报名者附件材料</span>
</nav>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l">
                <input type="text" class="input-text" style="width:200px" name="userName" value="${userName!}" placeholder="请输入报名者账号">
                <input type="hidden" name="resourceId" value="${resourceId}"/>
                <button type="submit" class="btn" onclick="return checkForm()">查询</button>
            </div>
            <div class="r">

            </div>
        </form>
    </div>
</div>
<#list transResourceApplyPage.items as transResourceApply>
<table class="table table-border table-bordered" >
    <#if transResourceApply.transUserApplyInfo??>
        <#assign info = transResourceApply.transUserApplyInfo>
        <#assign transUser = UserUtil.getUser(info.userId!)>
        <#if transUser??>
            <tr>
                <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">报名者账号：</label></td>
                <td>${transUser.userName!}</td>
                <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">报名者：</label></td>
                <td>${transUser.viewName!}</td>
            </tr>

            <tr>
                <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系人：</label></td>
                <td>${transResourceApply.transUserApplyInfo.contactPerson!}</td>
                <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系方式：</label></td>
                <td>${transResourceApply.transUserApplyInfo.contactTelephone!}</td>
            </tr>
            <tr>
                <td width="100px" style="background-color: #F5F5F5;"><label class="control-label">联系人地址：</label></td>
                <td colspan="3">${transResourceApply.transUserApplyInfo.contactAddress!}</td>
            </tr>
            <tr>
                <td width="100px" style="background-color: #F5F5F5;">
                    附件材料
                </td>
                <td width="600px" colspan="3">
                    <!--TODO 提供applyID获得文件然后打包-->
                    <a class="btn btn-primary size-MINI"  href="${base}/console/verify/file/zip?fileKey=${transResourceApply.applyId!}&userId=${transUser.userId!}">下载报名者附件材料</a>
                    <a class="btn btn-primary size-MINI"  href="${base}/console/verify/excel.f?applyId=${transResourceApply.applyId!}">下载报名者联系信息</a>
                </td>
            </tr>
        </#if>
    </#if>
</table>
<div style="padding-top: 20px"></div>
</#list>
<@PageHtml pageobj=transResourceApplyPage formId="frmSearch" />
</body>
</html>