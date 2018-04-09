




    <div id="div_status_${communion.communionId!}" style="margin-top: 2px">
        <#--<td>
        <#if communion.replyStatus==0>
            未审核
        <#elseif communion.replyStatus==1>
            已审核
        <#elseif communion.replyStatus==2 >
            已回复
        </#if>
        </td>-->

    <#if communion.replyStatus==0>
        <input class="btn size-S btn-primary" type="button" value="审核" onclick="PreRelease('${communion.communionId!}')">
    <#elseif communion.replyStatus==1>
        <input class="btn size-S btn-secondary" type="button" value="退回审核" onclick="backRelease('${communion.communionId!}')">
        <a onclick="PreReply('${communion.communionId!}')" class="btn btn-secondary size-S">回复</a>
    <#elseif communion.replyStatus==2 >
    <#--<a  class="btn btn-default size-S" >已回复</a>-->
        <input class="btn size-S btn-secondary" type="button" value="退回回复" onclick="backReply('${communion.communionId!}')">

    </#if>


    </div>
