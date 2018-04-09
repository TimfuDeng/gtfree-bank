<@Head_SiteMash title="联合竞买列表" />
    <style type="text/css">
        td p{
            margin-bottom:3px;
        }
    </style>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="wp">
    <div class="row cl" >
        <div class="col-2" style="padding-right: 10px">
            <ul class="nav nav-pills nav-stacked nav-pills-stacked-example">
                <li role="presentation" class="">
                    <a href="${base}/my/resource-list">
                        <i class="icon-th-large"></i>&nbsp;&nbsp;我的交易
                    <#assign applyCount=ResourceUtil.getApplyCountByStauts()>
                    <#if applyCount gt 0>
                        <span class="label label-success radius" style="width:15px!important;text-align: center;">${applyCount}</span>
                    </#if>
                    </a>
                </li>
                <li role="presentation" class="active"><a href="${base}/my/union-list"><i class="icon-money"></i>&nbsp;&nbsp;被联合竞买信息</a></li>
            </ul>
        </div>
        <div class="col-10">
            <nav class="breadcrumb pngfix">
                <i class="iconfont">&#xf012b;</i>
                <a href="${base}/index" class="maincolor">首页</a>
                <span class="c_gray en">&gt;</span><span class="c_gray">被联合竞买列表</span>
            </nav>

            <form id="frmSearch" >

            </form>
            <table class="table table-border table-bordered table-bg">
                <thead>
                <tr>
                    <td width="200">地块信息</td>
                    <td>竞买申请人</td>
                    <td>被联合人</td>
                    <td >编号</td>
                    <td width="120">地址</td>
                    <td width="40">操作</td>
                </tr>
                </thead>
                <tbody>
                <#list transUserUnionPage.items as transUserUnion>
                    <#assign resourceApply=ResourceUtil.getResourceApply(transUserUnion.applyId)!/>
                    <#if resourceApply.resourceId??>
                        <#assign resource=ResourceUtil.getResource(resourceApply.resourceId)/>
                    <tr>
                        <td>
                            <a href="${base}/view?id=${resource.resourceId}" target="frm_${resource.resourceId}">
                                <p>编号：${resource.resourceCode!}</p>
                                <p>面积：${resource.crArea}平方米</p>
                                <p>保证金：${resource.fixedOffer}万元</p>
                            </a>
                        </td>
                        <td>
                        ${UserUtil.getUserName(resourceApply.userId)}
                        </td>
                        <td>
                        ${transUserUnion.userName!}
                        </td>
                        <td>
                        ${transUserUnion.userCode!}
                        </td>
                        <td>
                        ${transUserUnion.userAddress!}
                        </td>
                        <td>
                            <#if transUserUnion.agree>
                                已同意
                            <#else>
                                <a href="#" class="btn btn-secondary" onclick="agreeFunction('${transUserUnion.unionId}')">同意</a>
                            </#if>
                        </td>
                    </tr>
                    </#if>
                </#list>
                </tbody>
            </table>
        <@PageHtml pageobj=transUserUnionPage formId="frmSearch" />

            <script type="text/javascript">
                function agreeFunction(unionId){
                    if(confirm("是否确认同意本次联合竞买申请？")){
                        $.ajax({
                            type: "POST",
                            url: "${base}/union-agree.f",
                            data: {unionId:unionId},
                            cache:false,
                            success: function(data){
                                if (data && data!=""){
                                    alert(data);
                                }else{
                                    window.location.reload();
                                }
                            }
                        });
                    }
                }
            </script>
        </div>
    </div>
</div>
<#include "../common/foot.ftl"/>

<script type="text/javascript" src="${base}/static/js/dist/layout.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/layer/layer.js"></script>
</body>
</html>