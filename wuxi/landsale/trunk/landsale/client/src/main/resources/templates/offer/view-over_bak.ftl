<tr class="price price-disable">
<td colspan="2" style="line-height: 140px;text-align: center;font-size:20px;font-weight:700" >
    <#if resource.resourceStatus==30>
        <p style="line-height:80px">本场竞买已成交,请等待审核结果！</p>
        <p style="line-height:50px;font-size: 14px;">竞得人：${UserUtil.getUserName(maxOffer.userId)}
         <#if maxResourceApply??>
             <#assign userUnionList=UserUtil.getUserUnionByApplyId(maxResourceApply.applyId)!/>
             <#if userUnionList??>
                <#list userUnionList as userUnionObj>
                   ，${userUnionObj.userName!}
                </#list>
             </#if>
        </#if>
        </p>

        <#if UserUtil.isCurrentUser(maxOffer.userId)>
            <div id="showInfodialog" class="modal hide fade" tabindex="-1" style="height:579px;width: 857px;">
                <div class="modal-body" style="background: url('${base}/static/image/sucess.jpg');height:579px;width: 857px ">
                    <div style="font-size: 25px; padding-top: 290px;text-align: center">竞得人：${UserUtil.getUserName(maxOffer.userId)}<br/>
                        <#if maxResourceApply??>
                            <#assign userUnionList=UserUtil.getUserUnionByApplyId(maxResourceApply.applyId)!/>
                                <#if userUnionList??>
                                    <#list userUnionList as userUnionObj>
                                        <span style="padding-left:100px">${userUnionObj.userName!} </span><br/>
                                    </#list>
                                </#if>
                        </#if>
                    </div>
                    <#if maxOffer.offerType==2>
                        <div style="font-size: 20px; padding-top: 10px;text-align: center">竞得价：
                            <span style="color: rgb(204,0,0)">${maxOfferPrice.offerPrice}万元</span>
                            &nbsp;&nbsp;&nbsp;&nbsp;公租房<#if resource.publicHouse?? && resource.publicHouse==1 >资金<#else>面积</#if>：
                            <span style="color: rgb(204,0,0)">${maxOffer.offerPrice}<#if resource.publicHouse?? && resource.publicHouse==1 >万元<#else>平方米</#if></span>
                        </div>

                    <#else>
                        <div style="font-size: 20px; padding-top: 10px;text-align: center">竞得价：<span style="color: rgb(204,0,0)">${maxOffer.offerPrice}万元</span></div>
                    </#if>
                    <div style="font-size: 14px; padding-top: 10px;text-align: center">
                        请进入<a href="${base}/my/resource-list" style="color:rgb(204,0,0) ">我的交易</a>打印
                        <a href="${base}/my/offersuccess?resourceId=${resource.resourceId}" style="color:rgb(204,0,0) ">《成交通知书》</a>
                    </div>
                </div>
            </div>
        </#if>
    <#elseif resource.resourceStatus==31 >
        本场竞买已流拍 ！
    <#elseif resource.resourceEditStatus==3 >
        本场竞买已中止 ！
    <#elseif resource.resourceEditStatus==4 >
        本场竞买已终止 ！
    </#if>
</td>
</tr>

