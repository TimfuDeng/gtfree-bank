<a href="javascript:changeRegionCode('32', 1);" class="item item-cur">江苏省</a>
<#list transRegionList as transRegion>
<a href="javascript:changeRegionCode('${transRegion.regionCode!}', ${transRegion.regionLevel!});" class="item" regionCode="${transRegion.regionCode!}">${transRegion.regionName!}</a>
</#list>