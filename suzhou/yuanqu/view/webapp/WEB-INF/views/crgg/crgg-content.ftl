<h1>${crgg.ggTitle!}</h1>
<div class="msgbar">
    <span style="font-family: '黑体'">公告开始时间： </span>${crgg.ggBeginTime?string("yyyy-MM-dd HH:mm:ss")}&nbsp;&nbsp;
    <span style="font-family: '黑体'">公告截止时间： </span>${crgg.ggEndTime?string("yyyy-MM-dd HH:mm:ss")}
</div>
<div class="newsCon">
    <p>${crgg.ggContent!}</p>
</div>
<div class="tCon" style="display: block">
    <table class="table_list">
        <tr class="contentRowLight">
            <td width="100px" style="background-color: #F5F5F5;font-size: 20px;">
                <span style="color:red">附件材料</span>
            </td>
            <td>
                <div>
                <#list crggAttachments as crggAttachment>
                    <a title="${crggAttachment.fileName!}" class="btn btn-link attachment" target="_blank" href="${base}/file/get?fileId=${crggAttachment.fileId}">${crggAttachment.fileName!}</a>
                </#list>
                </div>
            </td>
        </tr>
    </table>
</div>

