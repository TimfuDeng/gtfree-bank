<#if transResource.banks??>
    <#assign banks=transResource.banks?split(",")/>
<#else>
    <#assign banks=""?split(",")/>
</#if>
<#list openBankList as bank>
<div style="float: left;margin-right:10px" ><input type="checkbox" name="banks" value="${bank.bankCode}" <#if banks?seq_contains(bank.bankCode)>checked="checked" </#if> >
    <img src="${base}/static/bankimages/${bank.bankCode}.gif"></div>
</#list>