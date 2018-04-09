<html>
<head>
    <title>日志管理</title>
    <meta name="menu" content="menu_admin_loglist"/>
    <style>
        .l span,.l button{
            margin-left: 10px;
        }
        .l input{
            width: auto;
        }
    </style>
</head>
<body>
<nav class="breadcrumb pngfix">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">日志管理</span>
</nav>
<div  class="search_bar">

    <form class="navbar-form" id="frmSearch">
        <div class="l">
            <span>日志来源：</span>
            <select class="select" style="width: auto" name="producer">
                <option value="" >不限</option>
                <option value="ADMIN" <#if producer?? && producer=="ADMIN" >selected="selected"</#if>>管理系统</option>
                <option value="CLIENT" <#if producer?? && producer=="CLIENT" >selected="selected"</#if>>客户端系统</option>
            </select>
            <span>类别：</span>
            <select class="select" style="width: auto" name="category">
                <option value="">不限</option>
                <option value="USER_LOGIN" <#if category?? && category=="USER_LOGIN" >selected="selected"</#if>>用户登录</option>
                <option value="DATA_VIEW" <#if category?? && category=="DATA_VIEW" >selected="selected"</#if>>数据浏览</option>
                <option value="DATA_SAVE" <#if category?? && category=="DATA_SAVE" >selected="selected"</#if>>数据保存</option>
                <option value="DATA_DELETE" <#if category?? && category=="DATA_DELETE" >selected="selected"</#if>>数据删除</option>
                <option value="DATA_RECEIVE" <#if category?? && category=="DATA_RECEIVE" >selected="selected"</#if>>数据接收</option>
                <option value="CUSTOM_APPLY" <#if category?? && category=="CUSTOM_APPLY" >selected="selected"</#if>>用户报名</option>
                <option value="CUSTOM_OFFER" <#if category?? && category=="CUSTOM_OFFER" >selected="selected"</#if>>用户报价</option>
                <option value="OTHER" <#if category?? && category=="OTHER" >selected="selected"</#if>>其他操作</option>
            </select>
            <span 时间：</span>
            <input type="text" id="beginTime" name="beginTime" class="input-text Wdate" value="<#if beginTime??&&beginTime?is_date>${beginTime?string("yyyy-MM-dd HH:mm:ss")!}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
            -<input type="text" id="endTime" name="endTime" class="input-text Wdate" value="<#if endTime??&&endTime?is_date>${endTime?string("yyyy-MM-dd HH:mm:ss")!}</#if>" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
            <button type="submit" class="btn ">查询</button>

        </div>
    </form>
</div>
<table  class="table table-hover table-striped" >
    <tr>
        <th style="width:85px;">类别</th>
        <th style="width:85px;">时间</th>
        <th style="width: 80px;">访问IP</th>
        <th style="width: 100px;">用户</th>
        <th style="width: 220px;">操作</th>
    </tr>
    <#list transAuditLogList.items as log>
    <tr>
        <td>${log.category!}</td>
        <td>${log.createAt?string("yyyy-MM-dd HH:mm:ss")}</td>
        <td>${log.ip!}</td>
        <td>${log.userViewName!}</td>
        <td style="width: 150px">
            <a href="${base}/console/log/content?auditId=${log.auditId!}" class="btn btn-default size-S" >查看内容</a>
        </td>
    </tr>
    </#list>
</table>
<@PageHtml pageobj=transAuditLogList formId="frmSearch" />
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>