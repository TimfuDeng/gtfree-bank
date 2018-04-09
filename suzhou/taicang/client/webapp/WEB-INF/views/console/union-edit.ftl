

    <div class="row cl form-row">
        <label class="form-label col-2">被联合人：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"  name="userName">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">类型：</label>
        <div class="formControls col-10">
            <input type="radio" class="input-text"  name="type" value="COMPANY" style="width:15px" onclick="showInfor('company')" <#if transUserUnion.type=="COMPANY"> checked="checked"</#if>>企业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" class="input-text"  name="type" value="PERSON" style="width:15px" onclick="showInfor('person')" <#if  transUserUnion.type!="COMPANY"> checked="checked"</#if>>自然人
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">证件号：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text" style="width:80%" name="userCode">
            <label id="infotype" name="infotype" value=""><#if transUserUnion.type=="COMPANY">营业执照号<#else>身份证号</#if></label>
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">联系人：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"   name="linkMan">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">联系方式：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text"   name="linkManTel">
        </div>
    </div>
    <div class="row cl form-row">
        <label class="form-label col-2">出资比例：</label>
        <div class="formControls col-10">
            <input type="text" class="input-text" style="width:97%" name="amountScale">%
        </div>
    </div>
