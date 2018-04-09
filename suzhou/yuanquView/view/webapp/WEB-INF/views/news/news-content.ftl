<h1>${news.newsTitle!}</h1>
<div class="msgbar"><span style="font-family: '黑体'">新闻副标题:</span>${news.newsSubHead!}</div>
<div class="msgbar">
    <span style="font-family: '黑体'">发布时间：</span> ${news.newsReportTime?string("yyyy-MM-dd HH:mm:ss")}&nbsp;&nbsp;
    <span style="font-family: '黑体'">作者:</span>${news.newsAuthor!}
</div>
<div class="newsCon">
    <p>${news.newsContent!}</p>
</div>
