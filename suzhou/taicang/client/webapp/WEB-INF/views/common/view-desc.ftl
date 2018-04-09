<style type="text/css">
    .first td {
        color: #cc0000;
        font-weight: bold;
    }
    .attachment{
        font-size: 12px!important;
    }
</style>
<div class="pm-addition">
    <div id="swiftMenu" class="row cl">
        <div id="TabMenu" style="background-color: rgb(246, 246, 246); border-bottom: 1px solid rgb(230, 230, 230);
    bottom: auto; left: auto;position: static;top: 0;height: 56px; width: 1188px">
            <div id="tab_demo" class="HuiTab">
                <ul class="tabBar cl">
                    <li class="active">地块介绍</li>
                    <li>出让公告</li>
                    <li>竞买记录</li>
                    <#if stopPrice?? && resource.resourceStatus==30>
                    <li>一次报价记录</li>
                    </#if>
                    <li>地图</li>
            </div>
        </div>
    </div>
    <div class="tabCon ">
        <div class="tCon" style="display: block">
            <div class="row">
                <table class="table table-border table-bordered">
                    <tbody>
                    <tr>
                        <td width="15%" class="table-td-title">公告地块编号</td>
                        <td width="25%">${resource.resourceCode!}</td>
                        <td width="15%" class="table-td-title">公告编号</td>
                        <td width="25%">${ggNum!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">挂牌起始时间</td>
                        <td>${resource.gpBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="table-td-title">挂牌截止时间</td>
                        <td>${resource.gpEndTime?string("yyyy-MM-dd HH:mm")}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">报名开始时间</td>
                        <td>${resource.bmBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="table-td-title">报名截止时间</td>
                        <td>${resource.bmEndTime?string("yyyy-MM-dd HH:mm")}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">保证金交纳开始时间</td>
                        <td>${resource.bzjBeginTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td class="table-td-title">保证金到账截止时间</td>
                        <td>${resource.bzjEndTime?string("yyyy-MM-dd HH:mm")}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">土地权属单位</td>
                        <td>${resource.ownershipUnit!}</td>
                        <td class="table-td-title">交易方式</td>
                        <td>${resource.offerType!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">地块名称</td>
                        <td colspan="3">${resource.resourceLocation!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">土地位置</td>
                        <td colspan="3">${resource.resourceLocation!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">竞价规则</td>
                        <td>${resource.bidRule!}</td>
                        <td class="table-td-title">所属行政区</td>
                        <td>${RegionUtil.getRegionNameByCode(resource.regionCode)}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">出让面积（平方米）</td>
                        <td>${resource.crArea!}</td>
                        <td class="table-td-title">建筑面积（平方米）</td>
                        <td>${resourceInfo.buildingArea!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">规划用途</td>
                        <td>${resource.landUse!}</td>
                        <td class="table-td-title">出让年限（年）</td>
                        <td>${resourceInfo.yearCount!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">竞买保证金</td>
                        <td>¥${resource.fixedOffer!}&nbsp;万元<#if resource.fixedOfferUsd??>，$${resource.fixedOfferUsd?string(",##0.##")}美元</#if></td>
                        <td class="table-td-title">起始价（万元）</td>
                        <td>${resource.beginOffer!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">竞价幅度（万元）</td>
                        <td>${resource.addOffer!}</td>
                        <td class="table-td-title">出价方式</td>
                        <td>${resource.bidType!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">出价单位</td>
                        <td>
                        <#if resource.offerUnit??&&resource.offerUnit==0>
                            万元（总价）
                        <#elseif resource.offerUnit??&&resource.offerUnit==1>
                            元/平方米（单价）
                        <#elseif resource.offerUnit??&&resource.offerUnit==2>
                            万元/亩（单价）
                        <#else>
                            万元（总价）
                        </#if>
                        </td>
                        <td class="table-td-title">最高限价（万元）</td>
                        <td>
                            <#if resource.maxOffer??&&resource.maxOffer gt 0>
                            ${resource.maxOffer}
                            <#else>
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-td-title">容积率</td>
                        <td>${resourceInfo.plotRatio!}</td>
                        <td class="table-td-title">绿化率</td>
                        <td>${resourceInfo.greeningRate!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">建筑密度</td>
                        <td>${resourceInfo.buildingDensity!}</td>
                        <td class="table-td-title">建筑限高（米）</td>
                        <td>${resourceInfo.buildingHeight!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">办公与服务设施用地比例（%）</td>
                        <td>${resourceInfo.officeRatio!}</td>
                        <td class="table-td-title">投资强度</td>
                        <td>${resourceInfo.investmentIntensity!}</td>
                    </tr>
                    <tr>
                        <td class="table-td-title">建设内容</td>
                        <td colspan="3">${resourceInfo.constructContent!}</td>
                    </tr>
                    <#list attachmentCategory?keys as key>
                    <tr>
                        <td class="table-td-title">${attachmentCategory[key]}</td>
                        <td colspan="3">
                            <#if attachments??>
                                <#list attachments as attachment>
                                    <#if attachment.fileType==key>
                                        <a title="${attachment.fileName!}" class="btn btn-link attachment" target="_blank" href="${base}/file/get?fileId=${attachment.fileId}">${attachment.fileName!}</a>
                                    </#if>
                                </#list>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tCon">
            <iframe src="${base}/view/crgg.f?id=${resource.resourceId!}" width="100%" height="850px" frameborder="no"
                    border="0" marginwidth="0" marginheight="0"></iframe>
            <table class="table table-border table-bordered">
                <tr>
                    <td width="100px" style="background-color: #F5F5F5;">
                        附件材料
                    </td>
                    <td>
                        <div>
                        <#list crggAttachments as crggAttachment>
                            <a title="${crggAttachment.fileName!}" class="btn btn-link attachment" target="_blank" href="${base}/file/get?fileId=${crggAttachment.fileId}">${crggAttachment.fileName!}</a>
                        </#list>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="tCon">
            <iframe src="${base}/view/offer/list.f?resourceId=${resource.resourceId!}" width="100%" height="700px"
                    frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>
        </div>
        <#if stopPrice?? && resource.resourceStatus==30>
        <div class="tCon">
            <iframe src="${base}/oneprice/offer/list.f?resourceId=${resource.resourceId!}" width="100%" height="700px"
                    frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>
        </div>
        </#if>
        <div class="tCon" style="height: 700px">
            <div id="map" style="width: 100%;height: 100%">
            </div>
            <span>*本地图仅提供示意作用，并非精确位置。具体位置以用地红线图和勘测定界图为准</span>
        </div>
    </div>

</div>

</div>
<script>
    var tabMenuoffset = 0;
    if (tabMenuoffset == 0) {
        tabMenuoffset = $("#TabMenu").offset().top;
    }

    function fixMenu() {

        if ($(window).scrollTop() <= tabMenuoffset) {
            $("#TabMenu").css('position', 'static');
        } else {
            $("#TabMenu").css('position', 'fixed');
        }
    }
    $(window).scroll(function () {
        fixMenu();
    })


    function tabMenu(tabBox, navClass) {

        var tabNavLi = document.getElementById(tabBox).getElementsByTagName("ul")[0].getElementsByTagName("li");
        var tabCon = $(".tabCon:first .tCon");

        var tabLens = tabCon.length;
        for (var i = 0; i < tabLens; i++) {

            //应用js闭包传入参数i作为当前索引值赋值给m
            (function (m) {
                tabNavLi[m].onmouseover = function () {
                    for (var j = 0; j < tabLens; j++) {
                        tabNavLi[j].className = (j == m) ? navClass : "";
                        tabCon[j].style.display = (j == m) ? "block" : "";
                        if (m == 3&&j==3) {
                            gtmapMap.resizeMapAndLocateGeometry('${base}/view/geometry.f?id=${resource.resourceId!}');
                        }
                    }

                    if ($(window).scrollTop() > tabMenuoffset) {
                        $(window).scrollTop(tabMenuoffset);
                    }
                    fixMenu();
                }
            })(i);
        }
    }

    tabMenu("TabMenu", "active")
</script>