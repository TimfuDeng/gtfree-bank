<div>
    <p width="100%" style="color: black;text-align: left;line-height: normal">${content!}</p>
<table class="table table-border table-bordered">
    <tr>
        <td width="100px" style="background-color: #F5F5F5;">
            附件材料
        </td>
        <td>
            <div>
            <#if crggAttachments??>
                <#list crggAttachments as crggAttachment>
                    <a title="${crggAttachment.fileName!}" class="btn btn-link attachment" href="${base}/file/get?fileId=${crggAttachment.fileId}">${crggAttachment.fileName!}</a>
                </#list>
            </#if>
            </div>
        </td>
    </tr>
</table>
</div>