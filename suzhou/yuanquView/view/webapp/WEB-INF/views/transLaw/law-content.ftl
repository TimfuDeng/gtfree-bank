<#if transLaw??>
<h1>${transLaw.title!}</h1>
<#--<div class="msgbar">-->
    <#--<span style="font-family: '黑体'">作者:</span>${transLaw.noticeAuthor}-->
<#--</div>-->
<div class="msgbar">
    <span style="font-family: '黑体'">修改时间：</span> ${transLaw.updateTime?string("yyyy-MM-dd HH:mm:ss")}&nbsp;&nbsp;

</div>
<div class="noticeCon">
    <p>${transLaw.content!}</p>
</div>
</#if>