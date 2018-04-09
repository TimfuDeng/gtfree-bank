<div class="header-section ">
    <#if resource.regionCode=="320503001">
        <img class="pic" src="${base}/${ImageUtil.url(resource.resourceId,'280_185')}" onerror="this.src='${base}/static/image/blank-g.jpg'">
    <#else>
        <img class="pic" src="${base}/${ImageUtil.url(resource.resourceId,'280_185')}" onerror="this.src='${base}/static/image/blank.jpg'">
    </#if>

    <p class="pic-title">${resource.resourceCode!}</p>
</div>
<div class="info-section">
    <p class="price">
    <#include "resource-price.ftl">
    </p>
    <p class="price price-assess" style="padding-top: 0px">
        <span>位&nbsp;&nbsp;&nbsp;置</span>
        <span class="value" >
             <em>
             <#if (resource.resourceLocation?length gt 15)>
             ${resource.resourceLocation?substring(0,15)}...
             <#else>
             ${resource.resourceLocation!}
             </#if>
             </em>
         </span>
    </p>
    <p class="price price-assess" style="padding-top: 2px">
        <span>面&nbsp;&nbsp;&nbsp;积</span>
        <span class="value"><em>${resource.crArea}平方米-${(resource.crArea*0.0015)?string("0.##")}亩</em></span>
    </p>
    <p class="price price-assess" style="padding-top: 2px">
    <#include "resource-time.ftl">
    </p>

</div>
<div>
    <div class="flag">
    <#include "resource-status.ftl">
    </div>
</div>