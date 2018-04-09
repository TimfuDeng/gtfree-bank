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
            <span style="color: red">*</span> 联系人
        </td>
        <td style="line-height:32px">
            <input type="text" class="input-text  size-M" style="width:100%" name="contactPerson"
                   value="${transUserApplyInfo.contactPerson!}">
        </td>
    </tr>
    <tr>
        <td style="line-height:32px">
            <span style="color: red">*</span> 联系人地址
        </td>
        <td style="line-height:32px">
            <input type="text" class="input-text  size-M" style="width:100%" name="contactAddress"
                   value="${transUserApplyInfo.contactAddress!}">
        </td>
        <td style="line-height:32px">
            <span style="color: red">*</span> 联系人电话
        </td>
        <td style="line-height:32px">
            <input type="text" class="input-text  size-M" style="width:100%" name="contactTelephone"
                   value="${transUserApplyInfo.contactTelephone!}">
        </td>
    </tr>
    <input type="hidden" name="infoId" value="${transUserApplyInfo.infoId!}">
    <input type="hidden" name="userId" value="${transUserApplyInfo.userId!}">
</table>