<#if transResource.banks??>
    <#assign banks=transResource.banks?split(",")/>
<#else>
    <#assign banks=""?split(",")/>
</#if>
<#list openBankList as bank>
<div style="float: left;margin-right:10px" ><input type="checkbox" name="banks" value="${bank.bankCode}" <#if banks?seq_contains(bank.bankCode) || !transResource.banks??>checked="checked" </#if> >
    <img src="${base}/static/bankimages/${bank.bankCode}.gif"><p style="text-align:center;font-size: 14px;font-weight:700">（${bank.bankName!}）</p></img></div>
</#list>