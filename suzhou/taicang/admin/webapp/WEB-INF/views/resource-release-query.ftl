<html>
<head>
    <title>出让地块</title>
    <meta name="menu" content="menu_admin_resourceReleaseQuery"/>
    <style type="text/css">
        .col-name th {
            height: 38px;
            text-align: center;
            background: #f5f5f5;
            border-top: 1px solid #e8e8e8;
            border-bottom: 1px solid #e8e8e8;
            color: #3c3c3c;
            font: 12px/1.5 Tahoma,Helvetica,Arial,'微软雅黑 Regular',sans-serif;
        }
        .sep-row{
            height: 10px;
        }
        .sep-row td{
            border: 0;
        }
        .order-hd td{
            background: #f5f5f5;
            border-bottom-color: #f5f5f5;
            height: 40px;
            line-height: 40px;
            text-align: left;
            border: 1px solid #daf3ff;
        }
        .order-bd td{
            padding: 10px 5px;
            overflow: hidden;
            vertical-align: top;
            border-color: #e8e8e8;
            border: 1px solid #e8e8e8;
        }
        .l span{
            margin-left:15px;
        }
    </style>
    <script type="text/javascript">
    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="iconfont">&#xf012b;</i>
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/resource/release/list">发布地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">地块报名情况</span>
</nav>
<table class="table table-border table-bordered">
    <thead>
        <tr class="text-c" style="background-color: #f5f5f5">
            <th style="width:2px">序号</th>
            <th style="width:155px">竞买人</th>
            <th style="width:137px">状态</th>
        </tr>
    </thead>
    <tbody>
    <#if transResourceApplyList??>
       <#list transResourceApplyList as transResourceApply>
          <tr>
              <td>${transResourceApply_index+1}</td>
              <td>${UserUtil.getUserName(transResourceApply.userId)}</td>
              <td>
                  <#if transResourceApply.applyStep==1 >
                      <span class="label label-default">未选择银行</span>
                      <span class="label label-default">未缴纳保证金</span>
                  <#elseif transResourceApply.applyStep==2 >
                      <span class="label label-success">已选择银行</span>
                      <span class="label label-default">未缴纳保证金</span>
                  <#elseif transResourceApply.applyStep==3 >
                      <span class="label label-success">已选择银行</span>
                      <span class="label label-success">已缴纳保证金</span>
                  </#if>
              </td>
          </tr>
       </#list>
    </#if>
    </tbody>
</table>
</body>
</html>