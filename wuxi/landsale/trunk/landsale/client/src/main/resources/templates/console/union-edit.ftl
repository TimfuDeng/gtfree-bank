

<div class="row cl form-row">
    <label class="form-label col-2">被联合人<span style="color:red; position: relative;top: 3px;">*</span>：</label>
    <div class="formControls col-10">
        <input type="text" required class="input-text"  name="userName">
        <span style="color: red">注：请填写被联合人的完整CA名称</span>
    </div>
</div>
<div class="row cl form-row">
    <label class="form-label col-2">类型<span style="color:red; position: relative;top: 3px;">*</span>：</label>
    <div class="formControls col-10">
        <input type="radio" class="input-text"  name="type" value="COMPANY" style="width:15px" <#if transUserUnion.type=="COMPANY"> checked="checked"</#if>>企业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" class="input-text"  name="type" value="PERSON" style="width:15px" <#if  transUserUnion.type!="COMPANY"> checked="checked"</#if>>自然人
    </div>
</div>
<div class="row cl form-row">
    <label class="form-label col-2">联系人<span style="color:red; position: relative;top: 3px;">*</span>：</label>
    <div class="formControls col-10">
        <input type="text" required class="input-text"   name="linkMan">
    </div>
</div>
<div class="row cl form-row">
    <label class="form-label col-2">证件号<span style="color:red; position: relative;top: 3px;">*</span>：</label>
    <div class="formControls col-10">
        <input type="text" required class="input-text"   name="userCode">
    </div>
</div>
<div class="row cl form-row">
    <label class="form-label col-2">联系方式<span style="color:red; position: relative;top: 3px;">*</span>：</label>
    <div class="formControls col-10">
        <input type="text" required class="input-text"   name="linkManTel">
    </div>
</div>
<div class="row cl form-row">
    <label class="form-label col-2">出资比例<span style="color:red; position: relative;top: 3px;">*</span>：</label>
    <div class="formControls col-10">
        <input type="text" required class="input-text"   name="amountScale">
    </div>
</div>
