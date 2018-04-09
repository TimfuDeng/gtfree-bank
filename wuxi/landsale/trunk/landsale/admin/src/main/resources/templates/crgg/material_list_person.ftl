<div id="materialPersonList" style="overflow:auto;width: 100%;height: auto">
    <table  style="border-style: none">
    <#if materialPersonList?? >
        <#list materialPersonList as material>
            <#if material_index%2==0>
                <#if material_index!=0>
                    </tr>
                </#if>
            <tr class="paddingMaterialListList">
            </#if>
            <td style="border-style: none">
                <input  type="checkbox" name="materialId" value="${material.materialId}"/>
            ${material.materialName!}
            </td>
            <#if !material_has_next>
            </tr>
            </#if>
        </#list>
    </#if>
    </table>
</div>