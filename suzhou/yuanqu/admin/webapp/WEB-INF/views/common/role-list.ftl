<div id="roleList" style="overflow:auto;width: 100%;height: 80px">
    <table  style="border-style: none">
    <#if transRoleList?? >
        <#list transRoleList as transRole>
            <#if transRole_index%5==0>
                <#if transRole_index!=0>
                    </tr>
                </#if>
            <tr class="paddingUserList">
            </#if>
            <td style="border-style: none;background-color: #FFF"> <input   <#if transRole.foo==true> checked="checked" </#if>   type="checkbox" name="roleIds" value="${transRole.roleId}">${transRole.roleName!}</td>
            <#if !transRole_has_next>
            </tr>
            </#if>
        </#list>
    </#if>
    </table>
</div>