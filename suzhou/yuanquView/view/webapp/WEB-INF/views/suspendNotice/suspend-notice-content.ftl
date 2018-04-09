<#if suspendNotice??>
<h1>${suspendNotice.noticeTitle!}</h1>
<div class="msgbar">
    <span style="font-family: '黑体'">公告编号:</span>${suspendNotice.noticeNum!}&nbsp;&nbsp;
    <span style="font-family: '黑体'">地块编号:</span>${suspendNotice.resourceCode!}&nbsp;&nbsp;
    <span style="font-family: '黑体'">作者:</span>${suspendNotice.noticeAuthor}
</div>
<div class="msgbar">
    <span style="font-family: '黑体'">发布时间：</span> ${suspendNotice.deployTime?string("yyyy-MM-dd HH:mm:ss")}&nbsp;&nbsp;

</div>
<div class="noticeCon">
    <p>${suspendNotice.noticeContent!}</p>
</div>
</#if>