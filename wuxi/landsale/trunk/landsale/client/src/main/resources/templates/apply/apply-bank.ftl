<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title></title>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/static/h-ui/css/H-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/js/dist/layout.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/font-awesome/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base}/thridparty/H-ui.2.0/lib/iconfont/iconfont.css">
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/bootstrap-modal.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/bootstrap-modalmanager.js"></script>
    <script type="text/javascript" src="${base}/thridparty/H-ui.2.0/lib/jquery.form.min.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${base}/thridparty/fileupload/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${base}/js/checkFilter.js"></script>
    <style type="text/css">
        .form-row {
            padding-left: 10px;
            padding-right: 10px;
            padding-top: 5px;
            padding-bottom: 5px;
        }

        .btn-upload {
            position: relative;
            display: inline-block;
            overflow: hidden;
            vertical-align: middle;
            cursor: pointer;
        }

        .uploadifive-button {
            display: block;
            width: 60px;
            height: 65px;
            background: #fff url(${base}/image/icon-add.png) no-repeat 0 0;
            text-indent: -99em;
        }

        .table-bordered td {
            border-left: 1px solid #ddd;
            border-top: 1px solid #ddd;
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
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

        .error_input {
            background-color: #FFFFCC;
        }

        .upload-progress-bar {
            display: none;
            width: 300px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("iframe", window.parent.document).attr("height", document.body.scrollHeight);
            $.ajaxSetup({cache: false});
            var type = $("input:radio[name='applyType']:checked").val();
            if (type == 1) showUnion();
            initializeAttachmentUploader('${transResourceApply.applyId!}');
            getAttachments('${transResourceApply.applyId!}');
        });

        function hiddenUnion() {
            $("#divUnion").html("");
        }

        function showUnion() {
            determinFileTypeShow('1');
            $("#divUnion").load("${base}/union/union-list?applyId=${transResourceApply.applyId}");
        }

        function addUnion() {
            $("#myFormUnion").load("${base}/union/union-edit", null, function () {
                $('#myModalUnion').modal({
                    backdrop: false
                });
            });
        }

        function determinFileTypeShow(jinmaiType) {
            $('[sign^="BAOMING_LH_"]').hide();
            $('[sign^="BAOMING_ZRR_"]').hide();
            $('[sign^="BAOMING_FR_"]').hide();
            if (jinmaiType == '2') {
                $('[sign^="BAOMING_ZRR_"]').show();
            } else if (jinmaiType == '1') {
                $('[sign^="BAOMING_LH"]').show();
            } else if (jinmaiType == '0') {
                $('[sign^="BAOMING_FR_"]').show();
            }
        }

        function saveUnion() {
            if (!checkInputNull('userName', '被联合人必须填写!'))
                return false;
            if (!checkInputNull('userCode', '证件号必须填写!'))
                return false;
            if (!checkInputNull('linkMan', '联系人必须填写!'))
                return false;
            if (!checkInputNull('linkManTel', '联系人联系方式必须填写!'))
                return false;
            if (!checkInputNull('amountScale', '出资比例必须填写!'))
                return false;
            var amountScale = $("input[name='amountScale']").val();
            if ((!amountScale.match(/^[\+]?\d*?\.?\d*?$/))) {
                alert("出资比例不是数字，请重新输入！");
                return false;
            } else if (1.0 * amountScale >= 100) {
                alert("出资比例不正确，请重新输入！");
                return false;
            }
            var options = {
                url: '${base}/union/union-save?applyId=${transResourceApply.applyId}',
                success: function (result) {
                    alert(result.message);
                    if (result.flag) {
                        $('#myModalUnion').modal('hide');
                        showUnion();
                    }
                }
            };
            $('#myFormUnion').ajaxSubmit(options);
        }

        function delUnion(id) {
            if (confirm("是否确认删除该联合竞买人？")) {
                $.ajax({
                    type: "POST",
                    url: "${base}/union/union-del.f",
                    data: {unionId: id},
                    cache: false,
                    success: function (data) {
                        showUnion();
                    }
                });
            }
        }

        function checkInputNull(name, info) {
            if ($("input[name='" + name + "']").val() == "") {
                $("input[name='" + name + "']").addClass("error_input");
                $("input[name='" + name + "']").focus();
                alert(info);
                return false;
            } else {
                $("input[name='" + name + "']").removeClass("error_input");
            }
            return true;
        }

        function checkInputRadioNull(name, info) {
            var checkName = $("input[name='" + name + "']:checked").val();
            if (null == checkName || "" == checkName) {
                alert(info);
                return false;
            } else {
                return true;
            }
        }

        function initializeAttachmentUploader(applyId) {
            if (applyId != null && applyId != '') {
                var url = "${core}/transfile/upload?fileKey=" + applyId + "&fileType=''" ;
                $('#attachmentFile').fileupload({
                    url: url,
                    dataType: 'json',
                    done: function (e, data) {
                        if (data != null && data != '') {
                            if (!data.result.flag)
                                alert(data.result.msg);
                            else
                                insertAttachment(data.result.empty.fileId, data.result.empty.fileName);
                        }
                        var activeCount = $('#attachmentFile').fileupload('active');
                        if (activeCount == 1) {
                            resetProgressBar('attachmentProgressBar');
                            setProgressBarVisible('attachmentProgressBar', false);
                        }
                    },
                    progressall: function (e, data) {
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        $('#attachmentProgressBar span').css(
                                'width',
                                progress + '%'
                        );
                    },
                    start: function (e) {
                        setProgressBarVisible('attachmentProgressBar', true);
                    },
                    fail: function (e, data) {
                        resetProgressBar('attachmentProgressBar');
                        setProgressBarVisible('attachmentProgressBar', false);
                        alert('附件上传失败');
                    }
                });

            }
        }

        function insertAttachment(fileId, fileName) {
            /*$("#attachments").prepend("<div class='attachment" + fileId + "'><a class='btn btn-link' target='_blank' href='${core}/file/get?fileId=" + fileId + "'>" + fileName + "</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment' + fileId + '"><a class="btn btn-link" href="javascript:removeAttachment(\'' + fileId + '\')">删除</a><br/></div>');*/

            $("#attachments").prepend("<div class='attachment" + fileId + "'><a class='btn btn-link' target='_blank' href='${core}/transfile/get?fileId=" + fileId + "'>" + fileName + "</a><br/></div>");
            $("#attachmentsOperation").prepend('<div class="attachment' + fileId + '"><a class="btn btn-link" href="javascript:removeAttachment(\'' + fileId + '\')">删除</a><br/></div>');
        }

        function removeAttachment(fileId) {
            if (confirm("确定要删除数据吗？")) {
                var url = '${core}/transfile/remove?fileIds=' + fileId;
                $.get(url, function (data) {
                    if (data != null && data.flag) {
                        $('#attachments div[class=attachment' + fileId + ']').each(function () {
                            $(this).remove();
                        });
                        $('#attachmentsOperation div[class=attachment' + fileId + ']').each(function () {
                            $(this).remove();
                        });
                    }
                });
            }

        }

        function getAttachments(applyId) {
            var url = '${core}/transfile/getTransFileAttachments?fileKeys=' + applyId;
            $.get(url, function (data) {
                $('#attachments').empty();
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        insertAttachment(data[i].fileId, data[i].fileName);
                    }
                }
            })
        }

        function resetProgressBar(id) {
            $('#' + id + ' span').removeAttr("style");
        }

        function setProgressBarVisible(id, visible) {
            if (visible == true)
                $('#' + id).show();
            else
                $('#' + id).hide();
        }

        function qualifiedNote() {
            checkInputFileter();
            var contactPhone = $("input[name='contactTelephone']").val();
            var telReg = /^1[3|4|5|7|8]\d{9}$/;
            if (!telReg.test(contactPhone)) {
                alert("请输入正确的联系人手机号码");
                return false;
            }
            if (!checkInputNull("contactPerson", "请填写联系人")) {
                return false;
            }
            if (!checkInputNull("contactAddress", "请填写联系人地址")) {
                return false;
            }
            if (!checkInputNull("contactTelephone", "请填写联系人手机号码")) {
                return false;
            }
            if (!checkInputRadioNull("bankId", "请选择银行")) {
                return false;
            }
            return true;
        }

        function clickApplyForm() {
            if (qualifiedNote() == true) {
                if (confirm('是否确认提交！')) {
                    $("#applyForm").submit();
                }
            }
        }
    </script>
</head>
<body>
<div class="wp">
    <nav class="breadcrumb pngfix">
        <i class="iconfont">&#xf012b;</i>
        <a href="${base}/resource/index" class="maincolor">首页</a>
        <span class="c_gray en">&gt;</span><a
            href="${base}/resource/view?resourceId=${transResource.resourceId}">${transResource.resourceCode}</a>
        <span class="c_gray en">&gt;</span><span class="c_gray">选择竞买方式和银行</span>
    </nav>
    <#if _msg??>
        <div class="Huialert Huialert-danger" style="margin: 10px 0px;"><i class="icon-remove"></i>${_msg}</div>
    </#if>
    <form id="applyForm" action="${base}/resourceApply/apply-bank-over" method="post">
    <#if transBuyQualified?exists && transBuyQualified.qualifiedStatus! == 2>
        <div style="font-weight: bolder;font-size: 20px;">
            抱歉您提交的信息未能通过审核，未通过原因：<span style="color: red;">${transBuyQualified.qualifiedReason!}</span>。请您编辑信息后重新提交，谢谢！
        </div>
    </#if>
        <div class="row" style="border: 1px solid #eeecec;">
        <#include "apply/apply-user-info.ftl"/>
            <table class="table table-border table-bordered">
                <tr>
                    <td rowspan="4" width="200" style="background-color: #F5F5F5;">
                        选择竞买方式
                    </td>
                    <td style="line-height:32px">
                        <div class="radio-box">
                            <input id="duliCheck" type="radio" name="applyType" <#if transResourceApply.applyType==0>
                                   checked="checked"</#if> value="0"
                                   onclick="hiddenUnion('0')">
                            <label for="duliCheck">独立竞买</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="radio-box">
                            <input id="lianheCheck" type="radio" name="applyType" <#if transResourceApply.applyType==1>
                                   checked="checked"</#if> value="1"
                                   onclick="showUnion()">
                            <label for="lianheCheck">联合竞买</label>
                        </div>

                        <div id="divUnion">

                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="line-height:32px;">
                        <div style="overflow: hidden;">
                            <div class="check-box l">
                                <input type="checkbox" id="checkbox-1" <#if transResourceApply.createNewComName??>
                                       checked="checked"</#if>>
                                <label for="checkbox-1">拟成立新公司</label>
                            </div>
                            <div class="l">
                                <label for="checkbox-1"> 拟成立的新公司名称</label>
                                <input type="text" class="input-text  size-M" style="width:400px"
                                       name="createNewComName"
                                       value="${transResourceApply.createNewComName!}">
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <table class="table table-border table-bordered">
                <tr>
                    <td width="200" style="background-color: #F5F5F5;">
                        选择银行
                    </td>

                    <td>
                    <#assign bankId=""/>
                    <#assign bankOther=false />
                    <#if transResourceApply.bankId??><#assign bankId=transResourceApply.bankId/></#if>
                        <table class="table ">
                            <tr>
                                <td>币种：人民币</td>
                            </tr>
                            <tr>
                                <td>
                                <#if transResource.banks??>
                                    <#assign banks=transResource.banks?split(",")/>
                                <#else>
                                    <#assign banks=""?split(",")/>
                                </#if>
                                <#list bankList as bank>
                                    <#if banks?seq_contains(bank.bankId)>
                                        <#if bank.moneyUnit="CNY">
                                            <div style="float: left;margin-right:10px">
                                                <input type="radio" id="bankId" name="bankId" value="${bank.bankId!}"
                                                       <#if bank.bankId==bankId>checked="checked" </#if>>
                                                <img src="${storage}/storage/thumbnail/${bank.bankIcon!}"
                                                     style="width: 120px;height: 40px;">
                                            </div>
                                        <#else>
                                            <#assign bankOther=true />
                                        </#if>
                                    </#if>
                                </#list>
                                </td>
                            </tr>
                        </table>

                    </td>
                </tr>

            </table>
            <table class="table table-border table-bordered">
                <tr>
                    <td width="200px" style="background-color: #F5F5F5;">
                        附件材料
                    </td>
                    <td>
                        <span>
                            一、个人申请，须上传有效身份证件。<br/>
                            二、单位申请，须上传如下材料：1、有效营业执照副本;2、法定代表人有效身份证件;3、授权委托书；4、受托人有效身份证件；
                            5、其他需提交的证明材料<br/>
                            三、联合竞买，须上传竞买人及被联合人联相关资料<br/>
                            <span style="color: red">注：1、只允许传入以下类型的文件：pdf、zip或者jpg等图片文件，其大小不超过5M</span>
                        </span>
                        <div class="progress-bar upload-progress-bar" id="attachmentProgressBar">
                            <span class="sr-only"/>
                        </div>
                    </td>
                    <td width="600px">
                        <span class="btn-upload" style="height:60px;margin-left: 10px">
                            <a href="javascript:void();" class="uploadifive-button">浏览文件</a>
                            <input id="attachmentFile" name="file" type="file" multiple="true" class="input-file"
                                   accept="image/png, image/gif, image/jpg, image/jpeg,image/bmp,application/zip,application/pdf">
                        </span>
                        <div>
                            <div id="attachments">
                            </div>
                            <div id="attachmentsOperation">
                            </div>
                        </div>
                    </td>
                </tr>

            </table>
            <input type="hidden" id="resourceId" name="resourceId" value="${transResource.resourceId!}">
            <div style="text-align: center;margin-top: 20px">
                <input type="button" class="btn btn-primary" onclick="clickApplyForm()" value="申请保证金账号"></input>
                <a class="btn btn-default" href="${base}/resource/index">返回地块</a>
            </div>
        </div>
    </form>
</div>
<div id="myModalUnion" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="top: 25%">
    <div class="modal-header">
        <h4 id="myModalLabel">联合竞买人</h4><a class="close" data-dismiss="modal" aria-hidden="true"
                                           href="javascript:void();">×</a>
    </div>
    <div class="modal_content">
        <form id="myFormUnion">
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" id="btn_modal" onclick="javascript:saveUnion();">确定</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

</body>
</html>