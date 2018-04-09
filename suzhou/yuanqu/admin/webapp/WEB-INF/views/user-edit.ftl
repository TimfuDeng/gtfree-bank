<html>
<head>
    <title>人员信息</title>
    <meta name="menu" content="menu_admin_userlist"/>
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

        .error_input{
            background-color: #FFFFCC;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function(){


            $('#btnCA').click(function(){

                gtmapCA.initializeCertificate(document.all.caOcx);
                if(gtmapCA.checkValidCertificate()){
                    var userName = gtmapCA.getCertificateFriendlyUser();

                    $('#viewName').val(userName);
                    $('#caThumbprint').val(gtmapCA.getCertificateThumbprint());
                    var beforeTime=new XDate(gtmapCA.getCertificateNotBeforeSystemTime());
                    $('#caNotBeforeTime').val(beforeTime.toString("yyyy-MM-dd HH:mm:ss"));
                    var afterTime=new XDate(gtmapCA.getCertificateNotAfterSystemTime());
                    $('#caNotAfterTime').val(afterTime.toString("yyyy-MM-dd HH:mm:ss"));
                    $('#caCertificate').val(gtmapCA.getCertificateContent());
                    $('#userName').val(userName);
                    var numberIndex=isNumberIndex(userName);
                    $('#certificateId').val(userName.substring(numberIndex));

                }
            });
        });

        /**
         * 判断字符串的第N个是数字，返回下标
         * @param obj
         * @returns {number}
         */
        function isNumberIndex(obj){
            var userName=obj+"";
            if(userName.length>0){
                for(var i=0;i<userName.length;i++){
                    if(!isNaN(parseInt(userName.charAt(i))))
                        return i;
                }
            }
        }



        function checkForm(){
            checkInputFileter();
            if (!checkInputNull('userName','登录名必须填写!'))
                return false;
            if (!checkInputNull('password','密码必须填写!'))
                return false;
            if (!checkInputNull('viewName','姓名必须填写!'))
                return false;
            if (!checkInputNull('certificateId','证件号必须填写!'))
                return false;
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
    </script>
</head>
<body>
<nav class="breadcrumb pngfix">
    <a href="${base}/console/index" class="maincolor">首页</a>
    <span class="c_gray en">&gt;</span><a href="${base}/console/user/list">人员管理</a>
    <span class="c_gray en">&gt;</span><span class="c_gray">${transUser.viewName!}</span>
</nav>
<#if _result??>
    <#if true==_result>
    <div class="Huialert Huialert-success"><i class="icon-remove"></i>${_msg!}</div>
    <#else>
    <div class="Huialert Huialert-danger"><i class="icon-remove"></i>${_msg!}</div>
    </#if>
</#if>
<div id="ajaxResultDiv" style="display:none"></div>
<form class="form-base" method="post" action="${base}/console/user/save">
    <table class="table table-border table-bordered table-striped">
        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">人员基本信息</td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">登录名：</label></td>
            <td><input type="text" class="input-text" id="userName"  name="userName" placeholder="登录名" value="${transUser.userName!}" style="width:100%;background-color: #ffffcc" maxlength="32"></td>
            <td><label class="control-label">密码：</label></td>
            <td><input type="password" class="input-text" placeholder="密码" name="password" value="${transUser.password!}" style="width:100%;background-color: #ffffcc" maxlength="32"></td>

        </tr>
        <tr>
            <td><label class="control-label">证件号：</label></td>
            <td><input type="text" id="certificateId" class="input-text" placeholder="证件号" name="certificateId" value="${transUser.certificateId!}" style="width:100%;background-color: #ffffcc" maxlength="32"></td>
            <td colspan="2"></td>

        </tr>
        <tr>
            <td width="120"><label class="control-label">类型：</label></td>
            <td>
                <select name="type" class="select">
                    <option value="MANAGER" <#if transUser.type??&&transUser.type=='MANAGER'>selected</#if>>后台管理者</option>
                    <option value="CLIENT" <#if transUser.type??&&transUser.type=='CLIENT'>selected</#if>>前台交易人员</option>
                </select>
            </td>
            <td width="120"><label class="control-label">状态：</label></td>
            <td>
                <select name="status" class="select">
                    <option value="ENABLED" <#if transUser.status??&&transUser.status=='ENABLED'>selected</#if>>启用</option>
                    <option value="DISABLED" <#if transUser.status??&&transUser.status=='DISABLED'>selected</#if>>停用</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">姓名：</label></td>
            <td><input type="text" class="input-text" id="viewName" name="viewName" placeholder="姓名" value="${transUser.viewName!}" style="width:100%;background-color: #ffffcc" maxlength="32"></td>
            <td><label class="control-label">编号：</label></td>
            <td><input type="text" class="input-text" name="userCode" placeholder="编号" value="${transUser.userCode!}" style="width:100%" maxlength="32"></td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">性别：</label></td>
            <td>
                <select name="gender" class="select">
                    <option value="男" <#if transUser.gender??&&transUser.gender=='男'>selected</#if>>男</option>
                    <option value="女" <#if transUser.gender??&&transUser.gender=='女'>selected</#if>>女</option>
                </select>
            </td>
            <td><label class="control-label">手机号：</label></td>
            <td><input type="text" class="input-text" name="mobile" placeholder="手机号" value="${transUser.mobile!}" style="width:100%" maxlength="32"></td>
        </tr>
        <tr>
            <td><label class="control-label">所属行政区：</label></td>
            <td>
            <@SelectHtml SelectName="regionCode" SelectObjects=regionAllList Width=0 SelectValue=transUser.regionCode!''/>
            </td>
            <td><label class="control-label">所属机构：</label></td>
            <td>
                <select class="select" style="width: 100%" name="deptId">
                    <option value="">请选择</option>
                <#if allTransDept??>
                <#list allTransDept as dept>
                    <option value="${dept.deptId}" <#if transUser.deptId?? && transUser.deptId==dept.deptId >selected="selected"</#if>>${dept.deptName}</option>
                </#list>
                </#if>
                </select>
            </td>
        </tr>
        <tr>
            <td><label class="control-label">描述：</label></td>
            <td colspan="3">
                <textarea type="text" placeholder="描述" id="description" name="description" style="width: 100%" rows="5" maxlength="255">${transUser.description!}</textarea>
            </td>
        </tr>

        <tr>
            <td colspan="4" style="text-align: center;font-size: 14px;font-weight:700">数字证书信息</td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">证书指纹：</label></td>
            <td><input type="text" class="input-text" id="caThumbprint" placeholder="数字证书指纹" name="caThumbprint" value="${transUser.caThumbprint!}" style="width:100%" readonly></td>
            <td><label class="control-label"></label></td>
            <td></td>
        </tr>
        <tr>
            <td width="120"><label class="control-label">证书启用时间：</label></td>
            <td><input type="text" class="input-text" id="caNotBeforeTime" readonly placeholder="证书启用时间" name="caNotBeforeTime" value="${transUser.caNotBeforeTime!}" style="width:100%"></td>
            <td><label class="control-label"></label>证书失效时间：</td>
            <td><input type="text" class="input-text" id="caNotAfterTime" readonly placeholder="证书失效时间" name="caNotAfterTime" value="${transUser.caNotAfterTime!}" style="width:100%"></td>
        </tr>
        <tr>
            <td><label class="control-label">证书内容：</label></td>
            <td colspan="3">
                <textarea type="text" placeholder="证书内容" id="caCertificate" name="caCertificate" style="width: 100%" rows="20" readonly>${transUser.caCertificate!}</textarea>
            </td>
        </tr>
    </table>

    <input type="hidden" name="userId" value="${transUser.userId!}">

    <div class="row-fluid">
        <div class="span10 offset2">
          <button type="button" class="btn btn-primary" id="btnCA">导入CA证书</button>
            <button type="submit" class="btn btn-primary" onclick="return checkForm()">保存</button>
            <button type="button" class="btn" onclick="javascript:history.back()">返回</button>
        </div>
    </div>
</form>
<@Ca autoSign=0  />
</body>
</html>