<div class="header-section ">
    <#--src="${base}/${ImageUtil.url(resource.resourceId,'280_185')}" -->
    <img class="pic" src="${base}/image/blank.jpg" onerror="this.src='${base}/image/blank.jpg'">
    <p class="pic-title">${resource.resourceLocation}</p>
</div>
<div class="info-section">
    <p class="price">
    <#include "common/resource-price.ftl">
    </p>
    <p class="price price-assess" style="padding-top: 0px">
        <span>编&nbsp;&nbsp;&nbsp;号</span>
        <span class="value" >
             <em>${resource.resourceCode!}</em>
         </span>
    </p>
    <p class="price price-assess" style="padding-top: 2px">
        <span>面&nbsp;&nbsp;&nbsp;积</span>
        <#if resource.crArea??>
        <span class="value"><em>${resource.crArea!}平方米-${(resource.crArea!*0.0015)?string("0.##")}亩</em></span>
        </#if>
    </p>
    <p class="price price-assess" style="padding-top: 2px">
    <#include "common/resource-time.ftl">
    </p>

</div>
<div>
    <div class="flag">
    <#include "common/resource-status.ftl">
    </div>
</div>