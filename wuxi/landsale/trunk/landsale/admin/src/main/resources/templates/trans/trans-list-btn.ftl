<#if transItem.displayStatus==0>
    <input type="button"
           onclick="javascript:updateDisplayStatus('${transItem.resourceId!}','1', this);"
           value="推送到大屏" class="btn size-S btn-success"/>
<#else>
<input type="button"
       onclick="javascript:updateDisplayStatus('${transItem.resourceId!}','0', this);"
       value="取消推送" class="btn size-S btn-danger"/>
</#if>