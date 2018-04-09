
<div id="userList" style="overflow:auto;width: 100%;height: 80px">
<table  style="border-style: none">
<#if transUserList?? >
    <#list transUserList as transUser>
        <#if transUser_index%5==0>
            <#if transUser_index!=0>
                </tr>
            </#if>
        <tr class="paddingUserList">
        </#if>
        <td style="border-style: none;background-color: #FFF"> <input   <#if transUser.foo==true> checked="checked" </#if>   type="checkbox" name="userIds" value="${transUser.userId}">
            <#if (transUser.viewName?length gt 8)>
            ${transUser.viewName?substring(0,8)}
            <#else>
            ${transUser.viewName!}
            </#if></td>
        <#if !transUser_has_next>
        </tr>
        </#if>
    </#list>
</#if>
</table>
 </div>