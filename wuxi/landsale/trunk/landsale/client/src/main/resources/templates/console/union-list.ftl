<div style="padding-left: 25px">
    <span style="line-height:30px;color: #dd514c">竞买申请人出资比例为${100-scale}%</span>
    <input type="button" class="btn btn-default r" value="新增" onclick="addUnion()">
    <table class="table table-border table-bordered">
        <thead>
        <tr>
            <td>被联合人</td>
            <td width="39">类型</td>
            <td>证件号</td>
            <td width="40">联系人</td>
            <td>联系方式</td>
            <td width="70">出资比例(%)</td>
            <td width="40">信息</td>
            <td width="37">操作</td>
        </tr>
        </thead>
        <tbody>
        <#list transUserUnionList as transUserUnion>
        <tr>
            <td>${transUserUnion.userName}</td>
            <td><#if transUserUnion.type=="COMPANY">企业<#else>自然人</#if> </td>
            <td>${transUserUnion.userCode}</td>
            <td>${transUserUnion.linkMan!}</td>
            <td>${transUserUnion.linkManTel!}</td>
            <td>${transUserUnion.amountScale}</td>
            <td><#if transUserUnion.agree>已同意<#else>未同意</#if></td>
            <td><a href="#" class="btn btn-default size-MINI" onclick="delUnion('${transUserUnion.unionId}')">删除</a> </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>