<#if communion?? >
    <div class="modal-body">
        <p id="communionModal_content">

        <table class="table table-border table-bordered table-striped">
            <#--<tr>
                <td width="120"><label class="control-label">信件编号：</label></td>
            </tr>
            <tr>

                <td><input type="text" class="input-text" name="publicNo" value="${communion.publicNo!}"
                           style="width:100%"></td>
            </tr>
            -->
           <#-- <tr>
                <td><label class="control-label">信件标题：</label></td>
            </tr>
            <tr>

                <td><input readonly="true" type="text" class="input-text" name="publicTitle" value="${communion.publicTitle!}"
                           style="width:100%"></td>

            </tr>-->
           <#-- <tr>
                <td><label class="control-label">信件接受时间：</label></td>
            </tr>
            <tr>

                <td><input type="text" name="publicTime" class="input-text Wdate"
                           value="${communion.publicTime?string("yyyy-MM-dd HH:mm:ss")}">
                </td>

            </tr>-->

           <#-- <tr>
                <td><label class="control-label">信件内容：</label></td>
            </tr>
            <tr>
                <td >
                <textarea readonly="true" type="text"  name="publicContent" style="width: 100%;height: 120px"
                          rows="50">${communion.publicContent!}</textarea>
                </td>
            </tr>-->
            <tr>
                <td><label class="control-label">回复时间：</label></td>
            </tr>
            <tr>

                <td><input type="text"  id="replyTime" name="replyTime" class="input-text Wdate"
                           value="${communion.replyTime?string("yyyy-MM-dd HH:mm:ss")}"
                           onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',startDate:'%y-%M-%d 09:00:00',alwaysUseStartDate:true})">
                </td>

            </tr>
            <tr>
                <td><label class="control-label">回复内容：</label></td>
            </tr>
            <tr >

                <td >
                <textarea type="text" id="replyContent" name="replyContent"  class="xheditor"  style="width: 100%;height: 120px"
                          rows="50">${communion.replyContent!}</textarea>
                </td>
            </tr>
            <input type="hidden" name="communionId" value="${communion.communionId!}">
        </table>



        </p>
    </div>


</#if>