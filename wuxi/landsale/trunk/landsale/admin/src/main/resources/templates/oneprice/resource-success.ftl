<html>
<head>
    <title>-出让地块</title>
    <meta name="menu" content="menu_oneprice_resourcelist"/>
    <style type="text/css">
        .title-info h1, .title-info h3 {
            margin: 0;
            padding: 0 0 0 20px;
            font-family: "Microsoft Yahei";
            font-weight: 400;
        }
        .btn-upload input{
            height:55px;
            cursor: pointer;
        }
        .image a{
            position: relative;
            top:-30px;
            filter: alpha(opacity=0);
            opacity: 0;

        }
        .image:hover a{
            filter: alpha(opacity=100);
            opacity: 1;
        }
        .offer-h1{
            padding-left:200px;
            color:#ba2f2f;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function(){
            loadChecked();
            chooseCheckBox();
           /* $('#btn_modal').click(function(){
                setAndReloadStatus();
            });*/
        });

        //加载选中项
        function loadChecked(){
            //1为一次报价列表，2为公开系统报价列表
            var type = $("#offerType").val();
            if(type == 1) {
                $("table input:checkbox").each(function(){
                    if($(this).next().val()== '${ResourceUtil.getOfferUserId(transResource.offerId)!}'){
                        $("input[name='logId']").val($(this).val());
                        $(this).prop("checked",true);
                    }
                });
            }else if(type==2){
                $("table input:checkbox").each(function(){
                if($(this).val()== '${transResource.offerId!}'){
                    $("input[name='offerId']").val($(this).val());
                    $(this).prop("checked",true);
                }
            });
            }

        }
        //选中事件
        function chooseCheckBox(){
            //showConfirm("是否确认竞得人："+$(obj).parent().next().val()+"竞得价："+$(obj).parent().next().next().val(),obj);
            $("table input:checkbox").click(function(){
                //1为一次报价列表，2为公开系统报价列表
                var type = $("#offerType").val();
                $("table input:checkbox").not($(this)).prop("checked",false);//不选当前对象本身之外的对象
                $(this).prop("checked",true);//选中当前
                if(type == 1) {
                    $("input[name='logId']").val($(this).val().trim());
                }else if(type == 2){
                    $("input[name='offerId']").val($(this).val().trim());
                }
                $("input[name='successUnit']").val($(this).parent().next().html().trim());
                if(type == 1) {
                    $("input[name='successPrice']").val($(this).parent().next().next().html().trim());
                }else if(type == 2){
                    $("input[name='successPrice']").val($(this).parent().next().next().html().trim() * 10000);
                }
            });
        }

        function checkForm(){
            //1为一次报价列表，2为公开系统报价列表
            var type = $("#offerType").val();
            if(type == 1) {
                if (!checkInputNull('logId', '必须在报价列表中选中一个报价单位!'))
                    return false;
            }else if(type == 2) {
                if (!checkInputNull('offerId', '必须在报价列表中选中一个报价单位!'))
                    return false;
            }
            if (!checkInputNull('successUnit','竞得单位必须填写!'))
                return false;
            if (!checkInputNull('successPrice','竞得价须填写!'))
                return false;
            if (!checkInputNull('priceAvg','平均报价须填写!'))
                return false;
            if (!checkInputNumber('successPrice','竞得价必须是数字!'))
                return false;
            if (!checkInputNumber('priceAvg','平均报价必须是数字!'))
                return false;
            if (!checkInputInteger('successPrice','竞得价必须大于0!'))
                return false;
            if (!checkInputInteger('priceAvg','平均报价必须大于0!'))
                return false;
            submitForm();
//            return true;
        }

        function checkInputNumber(name,info){
            if(isNaN($("input[name='"+name+"']").val().trim())){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
            return true;
        }
        function checkInputInteger(name,info){
            if($("input[name='"+name+"']").val().trim()!="" && $("input[name='"+name+"']").val().trim()<=0){
                $("input[name='"+name+"']").addClass("error_input");
                $("input[name='"+name+"']").focus();
                alert(info);
                return false;
            }else{
                $("input[name='"+name+"']").removeClass("error_input");
            }
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

        String.prototype.trim = function () {
            return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
        }

        function submitForm() {
            $.ajax({
                url:"${base}/oneprice/resource/success/save",//提交地址
                data:$("#form1").serialize(),
                type:"POST",
                async:"false",
                dataType:"json",
                success:function(result){
                    if (result.flag == true){
                        <#--changeSrc('${base}/oneprice/resource/success/edit');-->
                        _alertResult('ajaxResultDiv', true, "保存成功");
                    }else{
                        _alertResult('ajaxResultDiv', false, "保存失败");
                    }
                }
            });
        }


    </script>

</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="javascript:changeSrc('${base}/index')" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/oneprice/resource/list')">出让地块</a>
    <span class="c_gray en">&gt;</span><span class="c_gray"> 设置成交信息</span>
</nav>

<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<form class="form-base" method="post" id="form1" action="${base}/oneprice/resource/success/save">
    <table class="table table-border table-bordered table-striped">
        <tbody>
        <#if oneTarget??>
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">设置成交信息</td>
        </tr>
        <tr>
            <td width="180"><label class="control-label">地块编号：</label></td>
            <td>${transTargetName!}</td>
            <td width="180"><label class="control-label">平均报价（元）：</label></td>
            <td width="300"><input  class="input-text" name="priceAvg" value="${oneTarget.priceAvg!}"></td>
        </tr>
        <tr>
            <td><label class="control-label">竞得单位：</label></td>
            <td >
                <input  class="input-text" name="successUnit" value="${oneTarget.successUnit!}">
            </td>
            <td><label class="control-label">竞得价（元）：</label></td>
            <td >
                <input readonly class="input-text" name="successPrice" value="${oneTarget.successPrice!}">
            </td>

            <input type="hidden"  name="id" value="${oneTarget.id!}">
            <input type="hidden" name="transUserId" value="${oneTarget.transUserId!}">
            <input type="hidden" name="transNo" value="${oneTarget.transNo!}">
            <input type="hidden" name="transName" value="${oneTarget.transName!}">
            <input type="hidden" name="transResourceId" value="${oneTarget.transResourceId!}">
            <input type="hidden" name="transAddress" value="${oneTarget.transAddress!}">
            <#if oneTarget.stopDate??>
                <input type="hidden" name="stopDate" value="${oneTarget.stopDate!?datetime}">
            </#if>
            <#if oneTarget.waitBeginDate??>
                <input type="hidden" name="waitBeginDate" value="${oneTarget.waitBeginDate!?datetime}">
            </#if>
            <#if oneTarget.waitEndDate??>
                <input type="hidden" name="waitEndDate" value="${oneTarget.waitEndDate!?datetime}">
            </#if>
            <#if  oneTarget.queryBeginDate??>
                <input type="hidden" name="queryBeginDate" value="${oneTarget.queryBeginDate!?datetime}">
            </#if>
            <#if oneTarget.queryEndDate??>
                <input type="hidden" name="queryEndDate" value="${oneTarget.queryEndDate!?datetime}">
            </#if>
            <#if oneTarget.priceBeginDate??>
                <input type="hidden" name="priceBeginDate" value="${oneTarget.priceBeginDate!?datetime}">
            </#if>
            <#if oneTarget.priceEndDate??>
                <input type="hidden" name="priceEndDate" value="${oneTarget.priceEndDate!?datetime}">
            </#if>
            <input type="hidden" name="priceMin" value="${oneTarget.priceMin!}">
            <input type="hidden" name="priceMax" value="${oneTarget.priceMax!}">
            <#if oneTarget.createDate??>
                <input type="hidden" name="createDate" value="${oneTarget.createDate!?datetime}">
            </#if>
            <input type="hidden" name="createUserId" value="${oneTarget.createUserId!}">
            <input type="hidden" name="priceGuid" value="${oneTarget.priceGuid!}">
            <input type="hidden" name="isStop" value="${oneTarget.isStop!}">
            <input type="hidden" name="transArea" value="${oneTarget.transArea!}">
            <input type="hidden" name="transUseLand" value="${oneTarget.transUseLand!}">



        </tr>
        </#if>

        </tbody>
    </table>


<#if oneTarget??>
    <#if oneTarget.priceEndDate?? && ((oneTarget.priceEndDate?long)-(nowDate?long)) lt 0 >
        <table class="table table-border table-bordered">



                <#if onePriceLogList?? && (onePriceLogList?size >0)>
                <thead>
                    <tr>
                        <td colspan="5" style="text-align: center;font-size: 14px;font-weight:700"><span style="color: #f4523b;">(一次报价系统)</span>报价列表</td>
                    </tr>
                    <tr>
                        <td style="text-align: center" width="120">#</td>
                        <td style="text-align: center" width="280">报价单位</td>
                        <td style="text-align: center"  width="280">报价（元）</td>
                        <td style="text-align: center" width="280">报价时间</td>
                    </tr>
                </thead>
                    <input type="hidden" value="1" name="offerType" id="offerType">
                    <input type="hidden" name="logId" id="logId">
                <tbody>
                    <#list onePriceLogList as onePriceLog>

                        <tr>
                        <td style="text-align: center">
                            <input type="checkbox"  value="${onePriceLog.id!}">
                            <input type="hidden"  value="${onePriceLog.transUserId!}">
                        </td>
                        <td style="text-align: center">
                        ${onePriceLog.priceUnit!}
                        </td>
                        <td style="text-align: center">
                        ${onePriceLog.price!}
                        </td>
                        <td style="text-align: center">
                        ${onePriceLog.priceDate!?datetime}
                        </td>
                    </tr>
                    </#list>
                </tbody>
                <#else>
                <thead>
                    <tr>
                        <td colspan="5" style="text-align: center;font-size: 14px;font-weight:700"><span style="color: #f4523b;">(公开出让系统)</span>报价列表</td>
                    </tr>
                    <tr>
                        <td style="text-align: center" width="120">#</td>
                        <td style="text-align: center" width="280">报价单位</td>
                        <td style="text-align: center"  width="280">报价（<span><@unitText value=ResourceUtil.getResource(oneTarget.transResourceId).offerUnit! /></span>）</td>
                        <td style="text-align: center" width="280">报价时间</td>
                    </tr>
                </thead>
                <input type="hidden" value="2" name="offerType" id="offerType">
                <input type="hidden" name="offerId" id="offerId">
                <tbody>
                    <#list transResourceOfferList as transResourceOffer>
                        <#assign transOfferUser=ResultUtil.getTransUser(transResourceOffer.userId!) >
                        <tr>
                            <td style="text-align: center">
                                <input type="checkbox"  value="${transResourceOffer.offerId!}">
                            </td>
                            <td style="text-align: center">
                            ${transOfferUser.userName!}
                            </td>
                            <td style="text-align: center">
                            ${transResourceOffer.offerPrice!}
                            </td>
                            <td style="text-align: center">
                            ${transResourceOffer.offerTime?number_to_datetime}
                            </td>
                        </tr>
                        </#list>
                </tbody>
                </#if>


                    <#--没有在一次报价系统里面报价情况-->







        </table>
        <div class="row-fluid">
            <div class="span10 offset2">
                <button type="button" class="btn btn-primary" onclick="checkForm()">保存</button>
                <button type="button" class="btn" onclick="javascript:changeSrc('${base}/oneprice/resource/list')">返回</button>
            </div>
        </div>
    <#else>
        <p>
        <h1 class="offer-h1">一次报价还未结束，请耐心等待结果！</h1>
        </p>
    </#if>
<#else>
    <p>
    <h1 class="offer-h1">此地块还未进行过一次报价，暂无结果！</h1>
    </p>
</#if>




</form>
<#--<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h4 id="myModalLabel"><i class="icon-warning-sign"></i> 确认对话框</h4><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
    </div>
    <div class="modal-body">
        <p id="modal_content">对话框内容…</p>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal">确定</button> <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>-->
<#--<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>-->
</body>

</html>