<#if dealNotice??>
<h1>${dealNotice.noticeTitle!}</h1>
<div class="msgbar">
    <span style="font-family: '黑体'">公告编号:</span>${dealNotice.noticeNum!}&nbsp;&nbsp;
    <span style="font-family: '黑体'">地块编号:</span>${dealNotice.resourceCode!}&nbsp;&nbsp;
    <span style="font-family: '黑体'">作者:</span>${dealNotice.noticeAuthor}
</div>
<div class="msgbar">
    <span style="font-family: '黑体'">发布时间：</span> ${dealNotice.deployTime?string("yyyy-MM-dd HH:mm:ss")}&nbsp;&nbsp;

</div>
<div class="noticeCon">
    <p>${dealNotice.noticeContent!}</p>
</div>
</#if>