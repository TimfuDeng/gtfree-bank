<!DOCTYPE html>

<html lang="zh-CN">
<head>
    <title>首页</title>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <link rel="stylesheet" href="${base}/static/js/dist/index.css" >
</head>
<body>
<div class="wp">
    <div class="row cl" >
        <table class="table table-border table-bordered" style="margin-top:20px">
            <tr>
                <td class="va-m" height="40" width="128" style="text-align: center; background-color: #f6f6f6;">
                    <span style="text-align: right;width:100px">招拍挂状态</span>
                </td>
                <td class="va-m" width="467">
                    <span class="label <#if !param.resourceStatus??  || param.resourceStatus==0>label-danger<#else>label-none</#if>">
                        <a href="#" onclick="submitValue('resourceStatus','0')">
                            不限
                        </a>
                    </span>

                    <span class="label <@OutClassValue obj=param.resourceStatus value=20 classValue='label-danger' defaultValue='label-none'/>">
                        <a href="#" onclick="submitValue('resourceStatus','20')">
                            公告中
                        </a>
                    </span>


                    <span class="label <@OutClassValue obj=param.resourceStatus value=10 classValue='label-danger' defaultValue='label-none'/>">
                        <a href="#" onclick="submitValue('resourceStatus','10')">
                            挂牌中
                        </a>
                    </span>

                    <span class="label <@OutClassValue obj=param.resourceStatus value=1 classValue='label-danger' defaultValue='label-none'/>">
                        <a href="#" onclick="submitValue('resourceStatus','1')">
                            竞价中
                        </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.gtResourceStatus value=20 classValue='label-danger' defaultValue='label-none'/>">
                        <a href="#" onclick="submitValue('resourceStatus','30')">
                            已结束
                        </a>
                    </span>
                </td>
                <#--<#if regionCode="320503001">
                <td class="va-m" height="40" width="128" style="  background-color: #f6f6f6;text-align: center">土地用途</td>
                <td class="va-m">
                    <span class="label <#if !param.useType??  || param.useType==''>label-danger<#else>label-none</#if>">
                    <a href="#" onclick="submitValue('useType','')">
                        不限
                    </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.useType! value='GY' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','GY')">
                             工业
                         </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.useType! value='CC' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','CC')">
                             仓储
                         </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.useType! value='KJ' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','KJ')">
                             科教
                         </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.useType! value='QT' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','QT')">
                             其他
                         </a>
                    </span>
                </td>
            <#else>
                <td class="va-m" height="40" width="128" style="  background-color: #f6f6f6;text-align: center">土地用途</td>
                <td class="va-m">
                    <span class="label <#if !param.useType??  || param.useType==''>label-danger<#else>label-none</#if>">
                    <a href="#" onclick="submitValue('useType','')">
                        不限
                    </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.useType! value='ZZYD' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','ZZYD')">
                             住宅
                         </a>
                    </span>
                    <span class="label <@OutClassValue obj=param.useType! value='SFYD' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','SFYD')">
                             商服
                         </a>
                    </span>
                   <span class="label <@OutClassValue obj=param.useType! value='QT' classValue='label-danger' defaultValue='label-none'/>">
                         <a href="#" onclick="submitValue('useType','QT')">
                             其他
                         </a>
                    </span>
                </td>
            </#if>-->
                <td class="va-m" height="40" width="128" style="  background-color: #f6f6f6;text-align: center">土地用途</td>
                <td class="va-m">
                    <span class="label <#if !param.tdyt??  || param.tdyt==''>label-danger<#else>label-none</#if>">
                    <a href="#" onclick="submitValue('tdyt','')">
                        不限
                    </a>
                    </span>

                <#if regionCode??&&regionCode=="320503001">
                    <span class="label <@OutClassValue obj=param.tdyt! value='061' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','061')">
                                 工业
                             </a>
	                    </span>
	                    <span class="label <@OutClassValue obj=param.tdyt! value='062' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','062')">
                                 采矿
                             </a>
	                    </span>
	                    <span class="label <@OutClassValue obj=param.tdyt! value='063' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','063')">
                                 仓储
                             </a>
	                    </span>
	                    <span class="label <@OutClassValue obj=param.tdyt! value='064' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','064')">
                                 其他
                             </a>
	                    </span>
                <#else>
                    <span class="label <@OutClassValue obj=param.tdyt! value='05' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','05')">
                                 商业
                             </a>
	                    </span>
	                    <span class="label <@OutClassValue obj=param.tdyt! value='07' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','07')">
                                 住宅
                             </a>
	                    </span>
	                    <span class="label <@OutClassValue obj=param.tdyt! value='12' classValue='label-danger' defaultValue='label-none'/>">
	                         <a href="#" onclick="submitValue('tdyt','12')">
                                 其他
                             </a>
	                    </span>
                </#if>
                </td>
            </tr>
        </table>
        <form name="frmSearch" id="frmSearch" method="get">
            <input type="hidden" name="resourceStatus" value="${param.resourceStatus!}">
            <input type="hidden" name="useType" value="${param.useType!}">
            <input type="hidden" name="tdyt" value="${param.tdyt!}">
            <input type="hidden" name="ggLx" value="${regionCode!}">
            <input type="hidden" name="regionCode" value="${regionCode!}">
            <input type="hidden" name="orderBy" value="${param.orderBy!}">
            <table class="table table-border table-bordered" style="margin-top:20px;background-color: #f6f6f6;">
                <tr>
                    <td  height="40" width="128" >
                        <div class="searchBar">
                            <input name="title" value="${param.title!}" placeholder="请输入地块编号" class="searchTxt" autocomplete="off" >
                            <input type="submit" value="搜索" class="searchBtn" onclick="b_onclick()">
                        </div>
                    </td>
                    <td class="va-m">
                    <span>
                        <a href="#" onclick="submitValue('orderBy','0')" style=" <#if !param.orderBy??  || param.orderBy==0>color: #d91615;</#if>">
                            默认
                        </a>
                    </span>
                    <span>
                        <a href="#" onclick="submitValue('orderBy','2')"  style="<@OutClassValue obj=param.orderBy value=2 classValue='color: #d91615;' defaultValue=''/>">
                            价格&nbsp;&nbsp;<i class="icon-arrow-down"></i>
                        </a>
                    </span>
                    <span>
                        <a href="#" onclick="submitValue('orderBy','1')"  style="<@OutClassValue obj=param.orderBy value=1 classValue='color: #d91615;' defaultValue=''/>">
                            价格&nbsp;&nbsp;<i class="icon-arrow-up"></i>
                        </a>
                    </span>
                    <span>
                        <a href="#" onclick="submitValue('orderBy','4')"  style="<@OutClassValue obj=param.orderBy value=4 classValue='color: #d91615;' defaultValue=''/>">
                            面积&nbsp;&nbsp;<i class="icon-arrow-down"></i>
                        </a>
                    </span>
                    <span>
                        <a href="#" onclick="submitValue('orderBy','3')"  style="<@OutClassValue obj=param.orderBy value=3 classValue='color: #d91615;' defaultValue=''/>">
                            面积&nbsp;&nbsp;<i class="icon-arrow-up"></i>
                        </a>
                    </span>
                    </td>
                </tr>
            </table>

        </form>

    </div>
    <div class="row cl" style="width:1210px" >
    <#list transResourcePage.items as resource>
        <div class="col-3" style="padding-right:20px;padding-top:10px">
            <a href="${base}/resource/index?id=${resource.resourceId}" target="frm_${resource.resourceId}" class="link-wrap" >
                <#include "common/resource-content.ftl">
            </a>
        </div>
    </#list>
    </div>
<@PageHtml pageobj=transResourcePage formId="frmSearch" />
</div>
<script type="text/javascript" src="${base}/static/js/index.js"></script>
<script type="text/javascript">
    function _TimeOutEvent(obj){
        var idvalue=$(obj).attr("id");
        var url="${base}/content.f?resourceId=" + idvalue;
        $("a[target='frm_"+idvalue+"']").load(url,null,function(){
            $("span[id='"+idvalue+"']").each(function(){
                if ($(this).attr("value")){
                    var timevalue=$(this).attr("value");
                    var idvalue=$(this).attr("id");
                    $(this).html(setTimeString(timevalue));
                    var funObj=setInterval(refreshTime,1000,this);
                    if(idvalue) {
                        _functionMap[idvalue] = funObj;
                    }
                }
            });
        });

    }
</script>
</body>
</html>