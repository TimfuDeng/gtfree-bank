<#if transResource.banks??>
    <#assign banks=transResource.banks?split(",")/>
<#else>
    <#assign banks=""?split(",")/>
</#if>
<#list bankList as bank>
<div style="float: left;margin-right:10px" >
    <input type="checkbox" name="banks" value="${bank.bankId}" <#if banks?seq_contains(bank.bankId)>checked="checked" </#if> >
    <#--<img src="${base}/static/bankimages/${bank.bankCode}.gif">-->
    <img src="${storage}/storage/thumbnail/${bank.bankIcon!}"  style="width: 120px;height: 40px;">
</div>
</#list>