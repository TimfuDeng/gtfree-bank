<p id="modal_content">确认发布时间：
<#if oneTarget?? && oneTarget.stopDate??>
    <input type="text" id="stopDate" name="stopDate" value="${oneTarget.stopDate!}" class="input-text Wdate" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 50%;" readonly="readonly">
<#else>
    <#--当前时间 < 限时竞价开始时间 设置限时竞价开始时间-->
    <#if (.now?long) - (transResource.xsBeginTime?long) lt 0>
        <input type="text" id="stopDate" name="stopDate" value="${transResource.xsBeginTime!}" class="input-text Wdate" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 50%;" readonly="readonly">
    <#--否则 当前时间-->
    <#else>
        <input type="text" id="stopDate" name="stopDate" value="${.now}" class="input-text Wdate" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 50%;" readonly="readonly">
    </#if>
</#if>
</p>
<p style="color: #f37b1d">一次报价在该设定时间后自动开始</p>