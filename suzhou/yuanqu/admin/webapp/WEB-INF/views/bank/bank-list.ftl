<html>
<head>
    <title>银行列表</title>
    <meta name="menu" content="menu_admin_banklist"/>
    <script type="text/javascript">
        $(document).ready(function(){
        });
        function delConfirm(){
            if(confirm("是否确认删除！")){
                return true;
            }
            return false;
        }
        function bankTest(bankId){
            $.ajax({
                type: "POST",
                url: "${base}/console/bank/test.f",
                data: {bankId:bankId},
                cache:false,
                success: function(data){
                    if(data=="1"){
                        $("span[bankid='"+bankId+"']").each(function(){
                            $(this).removeClass("label-danger");
                            $(this).addClass("label label-success");
                            $(this).html("测试成功！");
                        });
                    }else{
                        $("span[bankid='"+bankId+"']").each(function(){
                            $(this).removeClass("label-success");
                            $(this).addClass("label label-danger");
                            $(this).html("测试失败！");
                        });
                    }
                }
            });

        }
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">银行管理</span>
</nav>
<div class="search_bar">
    <div class="navbar-inner">
        <form class="navbar-form" id="frmSearch">
            <div class="l" style="margin-left:10px">
                <span>行政区部门：</span>
                <a href="${base}/console/bank/list">
                    <span <#if regionCode??><#else>class="label label-select"</#if>>不限</span>
                </a>
                <#list regionAllList as regions>
                <a href="${base}/console/bank/list?regionCode=${regions[0]}" style="margin-left: 20px">
                    <span <#if regionCode?? && regionCode==regions[0]>class="label label-select"</#if>>${regions[1]}</span>
                </a>
                </#list>
            </div>
        </form>
    </div>
</div>
    <table class="table table-border table-bordered">
        <thead>
            <tr>
                <td>已开通接口银行名称</td>
                <td>账户名称</td>
                <td width="220">操作</td>
            </tr>
        </thead>
        <tbody>
            <#list transBankList as bank>
            <tr>
                <td>
                    <div class="l"><img src="${base}/static/bankimages/${bank.bankCode}.gif"  style="width: 120px"></div>
                    <div class="l">${bank.bankName!}<span bankId="${bank.bankId}"></span>
                        <p style="margin-bottom:0px">${bank.moneyUnit!}</p></div>
                </td>
                <td>${bank.accountName!}<p style="margin-bottom:0px">${bank.accountCode!}</p></td>
                <td>
                    <a href="${base}/console/bank/edit?bankId=${bank.bankId}" class="btn  btn-primary size-MINI">编辑</a>
                    <a href="${base}/console/bank/del?bankId=${bank.bankId}" class="btn  btn-danger size-MINI" onclick="return delConfirm()">删除</a>
                    <a href="javascript:bankTest('${bank.bankId}')" class="btn  btn-default size-MINI" >测试链路</a>
                    <a href="${base}/console/bank/sendpay?bankId=${bank.bankId}" class="btn  btn-default size-MINI" >发送到账通知</a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
<br/>
    <#list bankAllList.bankList as transBank>

    <div style="float: left;width:196px;margin-top:10px;margin-bottom:5px;margin-right:-1px;border: 1px solid #ddd;">
        <div style="padding:5px 5px;">
            <img src="${base}/static/bankimages/${transBank[0]}.gif"  style="width: 120px">

            <a href="${base}/console/bank/edit?bankCode=${transBank[0]}" style="margin-top: 4px" class="btn  btn-primary size-MINI">
                开通接口
            </a>
        </div>
    </div>
    </#list>
</body>
</html>