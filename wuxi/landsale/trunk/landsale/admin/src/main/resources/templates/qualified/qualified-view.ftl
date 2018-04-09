<html>
<head>
    <title>-资格审核</title>
    <meta name="menu" content="menu_admin_qualifiedlist"/>
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

        td input {
            padding-right: 0px;
        }

        .btn-upload {
            position: relative;
            display: inline-block;
            overflow: hidden;
            vertical-align: middle;
            cursor: pointer;
            height: 60px !important;
        }

        .upload-url {
            width: 200px;
            cursor: pointer;
        }

        .input-file {
            position: absolute;
            right: 0;
            top: 0;
            cursor: pointer;
            font-size: 17px;
            opacity: 0;
            filter: alpha(opacity=0)
        }

        .uploadifive-button {
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/static/thridparty/H-ui.1.5.6/images/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        .btn-upload input {
            height: 65px;
            cursor: pointer;
        }

        .fileUploader {
            margin-left: 10px;
            clear: both;
        }

        #attachments {
            width: 500px;
            float: left;
            overflow: hidden;
            text-overflow-ellipsis: '..';
        }

        #attachmentsOperation {
            float: right
        }
        .upload-progress-bar{
            display: none;
            width: 400px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
        <#if _result?? && _result>
            _alertResult('ajaxResultDiv',${_result?string('true','false')}, '${_msg!}');
        </#if>

        });

        function resetProgressBar(id){
            $('#'+id+' span').removeAttr("style");
        }

        function setProgressBarVisible(id,visible){
            if(visible==true)
                $('#'+id).show();
            else
                $('#'+id).hide();
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


    </script>

</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/index')">资格审核</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/resourceApply?resourceId=${transResourceApply.resourceId!}')">竞买人审核</a>
    <span class="c_gray en">&gt;</span><a href="javascript:changeSrc('${base}/qualified/listQualified?applyId=${transResourceApply.applyId!}')">审核列表</a>
    <span class="c_gray en">&gt;</span>详情
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<#if _result?? && _result>
<div class="Huialert Huialert-success" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
<#elseif _result?? && !_result>
<div class="Huialert Huialert-danger" style="margin-bottom: 0px"><i class="icon-remove"></i>${_msg!}</div>
</#if>
<form class="form-base" method="post" action="${base}/console/qualified/verify/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td><label class="control-label">竞买人账号：</label></td>
            <td>${transUser.userName!}</td>
            <td><label class="control-label">竞买人：</label></td>
            <td>${transUser.viewName!}</td>
        </tr>
        <tr>
            <td><label class="control-label">联系人：</label></td>
            <td>${transUserApplyInfo.contactPerson!}</td>
            <td><label class="control-label">联系方式：</label></td>
            <td>${transUserApplyInfo.contactTelephone!}</td>
        </tr>

        <tr>
            <td width="180px"><label class="control-label">竞买方式：</label></td>
            <td width="300px">
            <#if transResourceApply.applyType==0>
                <span>独立竞买</span>
            <#else>
                <span>联合竞买</span>
            </#if>
                <input type="hidden" id="applyType" value="${transResourceApply.applyType}">
            </td>

            <td width="180px"><label class="control-label">所选币种：</label></td>
            <td width="300px">
            <#if transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="CNY">
                <span>人民币</span>
            <#elseif transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="USD">
                <span>美元</span>
            <#elseif transResourceApply.moneyUnit??&&transResourceApply.moneyUnit=="HKD">
                <span>港币</span>
            </#if>
            </td>

        </tr>
    <#if transResourceApply??&&transResourceApply.applyType==1&&transUserUnionList??>
        <#list transUserUnionList as transUserUnion>
            <tr>
                <td width="180px"><label class="control-label">被联合人：</label></td>
                <td width="300px">
                ${transUserUnion.userName!}
                </td>
                <td width="180px"><label class="control-label">被联合人地址：</label></td>
                <td width="300px">
                ${transUserUnion.userAddress!}
                </td>
            </tr>
        </#list>
    </#if>

        <tr>

            <td width="180px"><label class="control-label">审核人：</label></td>
            <td width="300px">${transBuyQualified.qualifiedAuditor!}</td>
            <td width="180px"><label class="control-label">审核时间：</label></td>
            <td width="300px">
                <input type="text" name="qualifiedTime" class="input-text Wdate"
                       disabled="disabled"   value="${transBuyQualified.qualifiedTime!}">
            </td>

        </tr>

        <tr>

            <td>
                <label class="control-label">是否审核通过：</label>
            </td>
            <td>
                <input  disabled="disabled" type="radio" name="qualifiedStatus" value="0" <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==0 >checked="true"</#if> />：未审核
                <input  disabled="disabled" type="radio" name="qualifiedStatus" value="1" <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==1 >checked="true"</#if> />：是
                <input disabled="disabled" type="radio" name="qualifiedStatus" value="2" <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==2 >checked="true"</#if> />：否
            </td>
            <td width="180px"><lable class="control-label"></lable></td>
            <td width="300px">
                <#--<select class="select" style="width: 100%" name="bankId">-->

                <#--<#if bankList??>-->
                    <#--<#list bankList as bank>-->
                        <#--<option value="${bank.bankId!}" <#if transResourceApply.bankId?? && transResourceApply.bankId==bank.bankId >selected="selected"</#if>>${bank.bankName}</option>-->
                    <#--</#list>-->
                <#--<#else>-->
                    <#--<option >请开通币种对应的银行</option>-->
                <#--</#if>-->
                <#--</select>-->
            </td>
        </tr>

    <#if transBuyQualified?? && transBuyQualified.qualifiedStatus==2 >
        <tr>
            <td><label class="control-label">联系方式：</label></td>
            <td colspan="3">
            ${transBuyQualified.contacter!}
            </td>
        </tr>
        <tr>
            <td><label class="control-label">审核未通过原因：</label></td>
            <td colspan="3">
                <textarea disabled="disabled" type="text"  name="qualifiedReason"  style="width: 100%;resize: none"
                          rows="15">${transBuyQualified.qualifiedReason!}</textarea>
            </td>
        </tr>
    </#if>
        <input type="hidden" name="infoId" value="${infoId!}">
        <input type="hidden" name="resourceId" value="${resourceId!}">
        <input type="hidden" name="applyId" value="${applyId!}">
    </table>
    <div class="row-fluid">
        <div class="span10 offset2">

            <button type="button" class="btn" onclick="javascript:changeSrc('${base}/qualified/listQualified?applyId=${transResourceApply.applyId!}')">返回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${base}/static/thridparty/H-ui.2.0/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>