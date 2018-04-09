<div class="header-section " style="height: 145px;">
    <img class="pic" src="${base}/${ImageUtil.url(resource.resourceId,'280_185')}" onerror="this.src='${base}/static/image/blank.jpg'">
    <p class="pic-title">${resource.resourceLocation}</p>
</div>
<div class="info-section">
    <p class="price">
    <#include "resource-price.ftl">
    </p>
    <p class="price price-assess" style="padding-top: 0px">
        <span>起始价</span>
        <span class="value" >
             <em>${resource.beginOffer!}</em>万
         </span>
    </p>
    <p class="price price-assess" style="padding-top: 0px">
        <span>编&nbsp;&nbsp;&nbsp;号</span>
        <span class="value" >
             <em>${resource.resourceCode!}</em>
         </span>
    </p>
    <p class="price price-assess" style="padding-top: 2px">
        <span>面&nbsp;&nbsp;&nbsp;积</span>
        <span class="value"><em>${resource.crArea}平方米-${(resource.crArea*0.0015)?string("0.##")}亩</em></span>
    </p>
    <p class="price price-assess" style="padding-top: 2px">
    <#include "resource-time.ftl">
    </p>
    <p class="price price-assess" style="padding-top: 2px">
        <span>用&nbsp;&nbsp;&nbsp;途</span>
        <span class="value"><em><@LandUseConvert selectValue= resource.landUse.getCode()! /></em></span>
    </p>
</div>
<div>
    <div class="flag">
    <#include "resource-status.ftl">
    </div>
</div>