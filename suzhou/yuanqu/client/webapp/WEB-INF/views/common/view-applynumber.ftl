<#--
<#if transResourceApply?? && (transResourceApply.applyNumber gt 0)>
<td width="51">当前号牌</td>
<td class="value" style="color: #d91615;">
    <em class="price-font-small" id="priceInfo">${transResourceApply.applyNumber}</em>
    号
</td>
</#if>
-->
<#if transResourceApply?? && (transResourceApply.applyNumber gt 0)>
(我的号牌:<em class="price-font-small" id="priceInfo"><span style="color:#d91615">${transResourceApply.applyNumber}</span></em>号)
</#if>