<div id="statusId_${note.noteId!}">
   <#-- <td style="text-align: center"><#if note.deployStatus=="1">发布中<#else>已撤回</#if></td>-->
    <#if note.deployStatus=="1">
        <a href="#" onclick="revoke('${note.noteId!}')" class="btn btn-default size-S" >撤回</a>
    <#else>
        <a href="${base}/console/notification/edit?noteId=${note.noteId!}"  class="btn btn-default size-S" >编辑通知</a>
        <a href="#" onclick="deploy('${note.noteId!}')" class="btn size-S btn-primary" >发布</a>
    </#if>
</div>

