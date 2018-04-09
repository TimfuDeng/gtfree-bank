

    <div class="row cl form-row">
        <label class="form-label col-2">联合竞买人：</label>
        <div class="formControls col-10">
            <input type="hidden" name="unionId" value="${transUserUnion.unionId!}">
            <input type="text" class="input-text"  name="userName" value="${transUserUnion.userName!}">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">类型：</label>
        <div class="formControls col-10">
            <input type="radio" class="input-text"  name="type" value="COMPANY" style="width:15px" <#if transUserUnion.type=="COMPANY"> checked="checked"</#if>>企业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" class="input-text"  name="type" value="PERSON" style="width:15px" <#if  transUserUnion.type!="COMPANY"> checked="checked"</#if>>自然人
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">证件号：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"   name="userCode" value="${transUserUnion.userCode!}">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">联系人：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"   name="linkMan" value="${transUserUnion.linkMan!}">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">联系方式：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"   name="linkManTel" value="${transUserUnion.linkManTel!}">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">出资比例(%)：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"   name="amountScale" value="${transUserUnion.amountScale!}">
        </div>
    </div>
