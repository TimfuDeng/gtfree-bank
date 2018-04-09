<table class="table table-border table-bordered">
    <tr>
        <td rowspan="2" width="200" style="background-color: #F5F5F5;">
            竞买人信息
        </td>
        <td style="line-height:32px">
            企业或个人名称
        </td>
        <td style="line-height:32px">
        ${WebUtil.loginUserViewName!}
        </td>
        <td style="line-height:32px">
            联系人
        </td>
        <td style="line-height:32px">
            <input type="text" class="input-text  size-M" style="background-color: #FFFFCC;width:100%" name="contactPerson" maxlength="32"
                   value="${transUserApplyInfo.contactPerson!}">
        </td>
    </tr>
    <tr>
        <td style="line-height:32px">
            联系人地址
        </td>
        <td style="line-height:32px">
            <input type="text" class="input-text  size-M" style="background-color: #FFFFCC;width:100%" name="contactAddress" maxlength="100"
                   value="${transUserApplyInfo.contactAddress!}">
        </td>
        <td style="line-height:32px">
            联系人手机号码
        </td>
        <td style="line-height:32px">
            <input type="text" class="input-text  size-M" style="background-color: #FFFFCC;width:100%" name="contactTelephone" maxlength="15"
                   value="${transUserApplyInfo.contactTelephone!}">
        </td>
    </tr>
    <input type="hidden" name="infoId" value="${transUserApplyInfo.infoId!}">
    <input type="hidden" name="userId" value="${transUserApplyInfo.userId!}">
</table>