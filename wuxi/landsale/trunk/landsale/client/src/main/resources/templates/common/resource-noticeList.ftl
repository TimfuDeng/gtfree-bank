<div width="100%">
        <#if noticeList?size == 0>
            暂无公告
        <#else>
            <#list noticeList as notice>
                <#--中止状态-->
                <#if transResource.resourceEditStatus == 3>
                    ${notice.ggContent!}
                <#--终止状态-->
                <#elseif transResource.resourceEditStatus == 4>
                    ${notice.ggContent!}
                <#--成交状态-->
                <#elseif transResource.resourceStatus == 30>
                    ${notice.noticeContent!}
                </#if>
                <hr style="border:black 0.5px dashed ;margin-top: 20px;"/>
            </#list>
        </#if>
</div>
