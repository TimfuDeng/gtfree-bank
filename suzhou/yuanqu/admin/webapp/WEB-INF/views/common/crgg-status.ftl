<div id="div_status_${crgg.ggId!}" style="margin-top: 2px;float:left">
<#if crgg.crggStauts==1>
    <a href="${base}/console/resource/edit?ggId=${crgg.ggId!}" class="btn btn-default size-S" >新增地块</a>
    <a href="${base}/console/resource/list?ggId=${crgg.ggId!}" class="btn btn-default size-S" >查看地块</a>
    <input class="btn size-S btn-primary" type="button" value="发布所有地块"  onclick="PreReleaseAllResource('${crgg.ggId!}')">
    <input class="btn size-S btn-secondary" type="button" value="撤回公告"  onclick="BackEdit('${crgg.ggId!}')">

<#elseif crgg.crggStauts==0>
    <a href="${base}/console/resource/edit?ggId=${crgg.ggId!}" class="btn btn-default size-S" >新增地块</a>
    <a href="${base}/console/resource/list?ggId=${crgg.ggId!}" class="btn btn-default size-S" >查看地块</a>
    <input class="btn size-S btn-primary" type="button" value="发布公告"  onclick="PreRelease('${crgg.ggId!}')">
</#if>
</div>