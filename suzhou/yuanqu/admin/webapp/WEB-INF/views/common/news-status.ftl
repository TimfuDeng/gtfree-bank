<div id="div_status_${news.newsId!}" style="margin-top: 2px">
<#if news.newsStauts==1>

    <input class="btn size-S btn-secondary" type="button" value="撤回"  onclick="BackEdit('${news.newsId!}')">

<#elseif news.newsStauts==0>
    <a href="${base}/console/news/edit?newsId=${news.newsId!}" class="btn btn-default size-S" >编辑新闻</a>

    <input class="btn size-S btn-primary" type="button" value="发布"  onclick="PreRelease('${news.newsId!}')" >


</#if>
</div>