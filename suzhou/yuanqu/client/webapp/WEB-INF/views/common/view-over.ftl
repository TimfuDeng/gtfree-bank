<tr class="price price-disable">
    <td colspan="2" style="line-height: 140px;text-align: center;font-size:20px;font-weight:700" >

    <#if resource.resourceStatus==30>
        <!--已经审核的情况下-->
        <#assign resouerceVerify=ResourceUtil.getResourceVerifyById(resource.resourceVerifyId)!/>
        <!--审核未通过-->
        <#if resource.resourceVerifyId?? && resouerceVerify.verifyStatus==0>
                <p style="line-height:80px">
                    该竞买人因${resouerceVerify.verifySuggestion!}原因，成交审核未通过。
                </p>
        <#else>
            <p style="line-height:80px">
                <#if resource.dealNoticeId??>
                    本场竞买已成交！<a href="http://jsyd.sipac.gov.cn/cjggnrnew/index.jhtml?noticeId=${resource.dealNoticeId!}">查看成交公告</a>
                <#else>
                    本场竞买已成交！
                </#if>
            </p>
            <p style="line-height:50px;font-size: 14px;">
                竞得人：${UserUtil.getUserName(successOffer.userId)}<br>
                <a class="btn btn-primary size-MINI" href="${base}/my/resource-list">查看我的交易>>></a>
            </p>

            <#if UserUtil.isCurrentUser(successOffer.userId)>
                <div id="showInfodialog" class="modal hide fade" tabindex="-1" style="height:579px;width: 857px;">
                    <div class="modal-body" style="background: url('${base}/static/image/sucess.jpg');height:579px;width: 857px ">
                        <div style="font-size: 25px; padding-top: 290px;text-align: center">竞得人：${UserUtil.getUserName(successOffer.userId)}</div>
                        <#if successOffer.offerType==2>
                            <div style="font-size: 20px; padding-top: 10px;text-align: center">竞得价：
                                <span style="color: rgb(204,0,0)">${maxOfferPrice.offerPrice}万元</span>
                                &nbsp;&nbsp;&nbsp;&nbsp;公租房<#if resource.publicHouse?? && resource.publicHouse==1 >资金<#else>面积</#if>：
                                <span style="color: rgb(204,0,0)">${successOffer.offerPrice}<#if resource.publicHouse?? && resource.publicHouse==1 >万元<#else>平方米</#if></span>
                            </div>

                        <#else>
                            <div style="font-size: 20px; padding-top: 10px;text-align: center">竞得价：<span style="color: rgb(204,0,0)">${successOffer.offerPrice}万元</span></div>
                        </#if>
                        <div style="font-size: 14px; padding-top: 10px;text-align: center">
                            请进入<a href="${base}/my/resource-list" style="color:rgb(204,0,0) ">我的交易</a>打印
                            <a href="${base}/my/offersuccess?resourceId=${resource.resourceId}" style="color:rgb(204,0,0) ">
                                    <#if resource.regionCode=="320503001">
                                        《成交通知书》
                                        <#else>
                                         《成交通知书》
                                    </#if>
                            </a>
                        </div>
                    </div>
                </div>
            </#if>
        </#if>
    <#elseif resource.resourceStatus==31 >
        本场竞买已流拍 ！
    <#elseif resource.resourceEditStatus==3 >
        <!-- 判断地块是否被设置成成交 -->
        <#if resource.resourceStatus==30>
            <p style="line-height:80px">
                <#if resource.dealNoticeId??>
                    本场竞买已成交！<a href="http://jsyd.sipac.gov.cn/cjggnrnew/index.jhtml?noticeId=${resource.dealNoticeId!}">查看成交公告</a>
                <#else>
                    本场竞买已成交！
                </#if>
            </p>
            <p style="line-height:50px;font-size: 14px;">
                竞得人：${oneTarget.successUnit!}<br>
                竞得人价：${oneTarget.successPrice!}<br>
            </p>
        <#else>
            <#if resource.suspendNoticeId??>
               <#-- 本场竞买已中止 ！<a href="http://jsyd.sipac.gov.cn/zzggnr/index.jhtml?noticeId=${resource.suspendNoticeId!}" >查看中止公告</a>
                <br/>-->
                <!-- 如果在挂牌期间 -->
                <!-- /**
                  *@annotation 判断如果不是环保局则有中止一次报价
                  *@author liushaoshuai【liushaoshuai@gtmap.cn】
                  *@date 2017/6/20 8:59
                  *@param
                  *@return
                  */-->
                <#if resource.regionCode!="320503001">
                    <#if (resource.gpEndTime?long)-(.now?long) gt 0>
                    <p style="line-height: 40px;font-size: 16px;">
                        本场竞买已中止 ！<a href="http://jsyd.sipac.gov.cn/zzggnrnew/index.jhtml?noticeId=${resource.suspendNoticeId!}" >查看中止公告</a>
                        <br/>
                        当前价格已经超过网上竞价中止价格，此时不允许加价！<br/>
                        本地块将在<span style="color: #a40700;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm")}</span>进行一次报价，<br/>
                        所有缴交过保证金的竞买人届时均可前往一次报价系统参加！<br/>
                    </p>

                    <a  href="http://jsyd.sipac.gov.cn/clientOneprice/login">
                        <p style="line-height: 40px;;color: #a40700;font-size: 18px;">
                            一次报价系统入口，请点击
                        </p></a>
                <#else >
                    本场竞买已中止 ！<a href="http://jsyd.sipac.gov.cn/zzggnrnew/index.jhtml?noticeId=${resource.suspendNoticeId!}" >查看中止公告</a>
                    <br/>
                    <a href="http://jsyd.sipac.gov.cn/clientOneprice/login">
                    <span style="color: #a40700;font-size: 18px;">
                    本标的在出让系统竞价已中止，现已开通一次报价功能,请点击
                    </span></a>
                </#if>
                <#else >
                    本场竞买已中止 ！<a href="http://jsyd.sipac.gov.cn/zzggnrnew/index.jhtml?noticeId=${resource.suspendNoticeId!}" >查看中止公告</a>
                    <br/>
                </#if>

            <#else>
                <#if resource.regionCode!="320503001">
                    <#if (resource.gpEndTime?long)-(.now?long) gt 0>
                    <p style="line-height: 40px;font-size: 16px;">
                        本场竞买已中止 ！
                        <br/>
                        当前价格已经超过网上竞价中止价格，此时不允许加价！<br/>
                        本地块将在<span style="color: #a40700;">${resource.gpEndTime?string("yyyy-MM-dd HH:mm")}</span>进行一次报价，<br/>
                     所有缴交过保证金的竞买人届时均可前往一次报价系统参加！<br/>
                    </p>

                    <a  href="http://jsyd.sipac.gov.cn/clientOneprice/login">
                    <p style="line-height: 40px;;color: #a40700;font-size: 18px;">
                     一次报价系统入口，请点击
                    </p></a>
                <#else >
                    本场竞买已中止 ！
                    <br/>
                    <a href="http://jsyd.sipac.gov.cn/clientOneprice/login">
                    <span style="color: #a40700;font-size: 18px;">
                    本标的在出让系统竞价已中止，现已开通一次报价功能,请点击
                    </span></a>
                </#if>
                <#else >
                    本场竞买已中止 ！
                    <br/>
                </#if>
            </#if>
        </#if>

    <#elseif resource.resourceEditStatus==4 >
        本场竞买已终止 ！
    </#if>
    </td>
</tr>
