<html>
<head>
    <title>人员信息</title>
    <meta name="menu" content="menu_admin_causerlist"/>
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
        td input{
            padding-right: 0px;
        }


    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            <#if _result?? && _result>
                _alertResult('ajaxResultDiv',${_result?string('true','false')},'${_msg!}');
            </#if>

            $('#btnCA').click(function(){
                gtmapCA.initializeCertificate(document.all.caOcx);
                if(gtmapCA.checkValidCertificate()){
                    var userName = gtmapCA.getCertificateFriendlyUser();
                    $('#caThumbprint').val(gtmapCA.getCertificateThumbprint());
                    var beforeTime=new XDate(gtmapCA.getCertificateNotBeforeSystemTime());
                    $('#caNotBeforeTime').val(beforeTime.toString("yyyy-MM-dd HH:mm:ss"));
                    var afterTime=new XDate(gtmapCA.getCertificateNotAfterSystemTime());
                    $('#caNotAfterTime').val(afterTime.toString("yyyy-MM-dd HH:mm:ss"));
                    $('#caCertificate').val(gtmapCA.getCertificateContent());
                    $('#caName').val(userName);
                }
            });
        });
        function checkForm(){
            if (!checkInputNull('userName','联系人必须填写!'))
                return;
            if (!checkInputNull('mobile','联系电话必须填写!'))
                return;

            $('#ca_user_edit_form').submit();
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
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/ca-user/list">CA注册管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transCaUser.userName!}</span>
</nav>

<div id="ajaxResultDiv" style="display:none"></div>
<form id="ca_user_edit_form" class="form-base" method="post" action="${base}/console/ca-user/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">CA注册信息</td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">联系人：</label></td>
            <td><input type="text" class="input-text" id="userName"  name="userName" placeholder="联系人" value="${transCaUser.userName!}" style="width:100%"></td>
            <td><label class="control-label">移动电话：</label></td>
            <td><input type="text" class="input-text" placeholder="移动电话" id="mobile" name="mobile" value="${transCaUser.mobile!}" style="width:100%"></td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">类型：</label></td>
            <td>
                <select name="type" class="select">
                    <option value="COMPANY" <#if transCaUser.type??&&transCaUser.type=='COMPANY'>selected</#if>>企业</option>
                    <option value="PERSON" <#if transCaUser.type??&&transCaUser.type=='PERSON'>selected</#if>>自然人</option>
                </select>
            </td>
            <td width="120"><label class="control-label">组织机构代码<br>(身份证号)</label></td>
            <td>
                <input type="text" class="input-text" id="caOrganizationCode" placeholder="组织机构代码/身份证号" name="caOrganizationCode" value="${transCaUser.caOrganizationCode!}" style="width:100%">
            </td>
        </tr>
        <tr>
            <td><label class="control-label">所属行政区：</label></td>
            <td>
            <@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=0 SelectValue=transCaUser.regionCode!''/>
            </td>
            <td><label class="control-label">状态：</label></td>
            <td>
                <select name="status" class="select">
                    <option value="ENABLED" <#if transCaUser.status??&&transCaUser.status=='ENABLED'>selected</#if>>启用</option>
                    <option value="DISABLED" <#if transCaUser.status??&&transCaUser.status=='DISABLED'>selected</#if>>停用</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">描述：</label></td>
            <td colspan="3">
                <textarea type="text" placeholder="描述" id="description" name="description" style="width: 100%" rows="5">${transCaUser.description!}</textarea>
            </td>
        </tr>

        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">数字证书信息</td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">单位名称<br>(个人姓名)</label></td>
            <td><input type="text" class="input-text" id="caName" readonly name="caName" placeholder="单位名称/个人姓名" value="${transCaUser.caName!}" style="width:100%"></td>
            <td><label class="control-label">证书指纹</label></td>
            <td>
                <input type="text" class="input-text" id="caThumbprint" placeholder="数字证书指纹" name="caThumbprint" value="${transCaUser.caThumbprint!}" style="width:100%" readonly>
            </td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">证书启用时间：</label></td>
            <td><input type="text" class="input-text" id="caNotBeforeTime" readonly placeholder="证书启用时间" name="caNotBeforeTime" value="${transCaUser.caNotBeforeTime!}" style="width:100%"></td>
            <td><label class="control-label"></label>证书失效时间：</td>
            <td><input type="text" class="input-text" id="caNotAfterTime" readonly placeholder="证书失效时间" name="caNotAfterTime" value="${transCaUser.caNotAfterTime!}" style="width:100%"></td>
        </tr>
        <tr>
            <td><label class="control-label">证书内容：</label></td>
            <td colspan="3">
                <textarea type="text" placeholder="证书内容" id="caCertificate" name="caCertificate" style="width: 100%" rows="20" readonly>${transCaUser.caCertificate!}</textarea>
            </td>
        </tr>



    </table>

    <input type="hidden" name="userId" value="${transCaUser.userId!}">
    <input type="hidden" name="createAt" value="${transCaUser.createAt!}">

    <div class="row-fluid">
        <div class="span10 offset2">
            <button type="button" class="btn btn-primary" id="btnCA">导入CA证书</button>
            <button type="button" class="btn btn-primary" onclick="checkForm();">保存</button>
            <button type="button" class="btn" onclick="window.location.href='${base}/console/user/list'">返回</button>
        </div>
    </div>
</form>
<@Ca autoSign=0  />
</body>
</html>