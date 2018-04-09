<#macro compareSon minNum maxNumTag minNumTag maxNum name>
    <#if minNum?exists && maxNum?exists>
    ${minNum}<#if maxNumTag == "00">&lt;<#else>&le;</#if>${name}<#if minNumTag == "00">&lt;<#else>&le;</#if>${maxNum}
    <#elseif minNum?exists>
    ${minNum}<#if maxNumTag == "00">&lt;<#else>&le;</#if>${name}
    <#elseif maxNum?exists>
    ${name}<#if minNumTag == "00">&lt;<#else>&le;</#if>${maxNum}
    </#if>
</#macro>
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
    <#if resource.ybmSign?? && resource.ybmSign == "是" && resource.ybmBeginTime?? && resource.ybmEndTime??>
    <tr>
        <td class="table-td-title">预报名开始时间</td>
        <td>${resource.ybmBeginTime?string("yyyy-MM-dd HH:mm")}</td>
        <td class="table-td-title">预报名截止时间</td>
        <td>${resource.ybmEndTime?string("yyyy-MM-dd HH:mm")}</td>
    </tr>
    </#if>
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
        <td class="table-td-title" style="vertical-align: middle">规划信息</td>
        <td colspan="3" style="padding:0;">
        <#if transResourceSonList?? && (transResourceSonList?size>0)>
        <table id="addSonTable" class="table table-border table-bordered" style="border:0;margin-bottom: 0">
            <tr id="addSonTr" <#if transResourceSonList?? && (transResourceSonList?size>0)><#else>style="display: none"</#if> >
                <td width="22%" align="center" class="table-td-title">面积(编号)</td>
                <td width="22%" align="center" class="table-td-title">用途</td>
                <td width="10%" align="center" class="table-td-title">出让年限</td>
                <td width="10%" align="center" class="table-td-title">容积率</td>
                <td width="10%" align="center" class="table-td-title">建筑密度</td>
                <td width="10%" align="center" class="table-td-title">绿化率</td>
                <td width="10%" align="center" class="table-td-title">建筑限高</td>
            </tr>
            <#list transResourceSonList as resourceSon>
                <tr>
                    <td>${resourceSon.zdArea!}(${resourceSon.zdCode!})</td>
                    <td>${resourceSon.tdytName!}</td>
                    <td>${resourceSon.sonYearCount!}</td>
                    <td><@compareSon minNum=resourceSon.minRjl maxNumTag=resourceSon.maxRjlTag minNumTag=resourceSon.minRjlTag maxNum=resourceSon.maxRjl name="FAR"/></td>
                    <td><@compareSon minNum=resourceSon.minJzMd maxNumTag=resourceSon.maxJzMdTag minNumTag=resourceSon.minJzMdTag maxNum=resourceSon.maxJzMd name="BD"/></td>
                    <td><@compareSon minNum=resourceSon.minLhl maxNumTag=resourceSon.maxLhlTag minNumTag=resourceSon.minLhlTag maxNum=resourceSon.maxLhl name="GSP"/></td>
                    <td><@compareSon minNum=resourceSon.minJzxg maxNumTag=resourceSon.maxJzXgTag minNumTag=resourceSon.minJzXgTag maxNum=resourceSon.maxJzxg name="BHR"/></td>
                </tr>
            </#list>
        </table>
        </#if>
        </td>
    </tr>
    <tr>
        <td class="table-td-title">竞买保证金</td>
        <td>¥${resource.fixedOffer!}&nbsp;万元<#if resource.fixedOfferUsd??>，$${resource.fixedOfferUsd?string(",##0.##")}万美元</#if></td>
        <td class="table-td-title">起始价（万元）</td>
        <td>${resource.beginOffer!}</td>
    </tr>
    <tr>
        <td class="table-td-title">竞价幅度（万元）</td>
        <td>${resource.addOffer!}</td>
        <td class="table-td-title">出价方式</td>
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
    <#--<tr>-->
        <#--<td class="table-td-title">容积率</td>-->
        <#--<td>${resourceInfo.plotRatio!}</td>-->
        <#--<td class="table-td-title">绿化率</td>-->
        <#--<td>${resourceInfo.greeningRate!}</td>-->
    <#--</tr>-->
    <#--<tr>-->
        <#--<td class="table-td-title">建筑密度</td>-->
        <#--<td>${resourceInfo.buildingDensity!}</td>-->
        <#--<td class="table-td-title">建筑限高（米）</td>-->
        <#--<td>${resourceInfo.buildingHeight!}</td>-->
    <#--</tr>-->
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
    <tr>
        <td class="table-td-title">履约保证金（万元）</td>
        <td colspan="3"><#if resource.performanceBond??>¥${resource.performanceBond!}&nbsp;万元</#if></td>
    </tr>
    </tbody>
</table>