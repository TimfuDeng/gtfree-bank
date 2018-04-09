package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.landsale.core.service.AttachmentCategoryService;
import cn.gtmap.landsale.core.service.MaterialCrggService;
import cn.gtmap.landsale.core.service.TransFileService;
import cn.gtmap.landsale.core.service.TransMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 附件类型服务
 * @author zsj
 * @version v1.0, 2017/11/1
 */
public class AttachmentCategoryServiceImpl implements AttachmentCategoryService {

    @Autowired
    TransFileService transFileService;

    @Autowired
    MaterialCrggService materialCrggService;

    @Autowired
    TransMaterialService transMaterialService;

//    @Autowired
//    TransUserUnionService transUserUnionService;

    private final String APPLY_TYPE_PERSONAL = "PERSONAL";//附件上传类型 个人上传
    private final String APPLY_TYPE_GROUP = "GROUP";//附件上传类型 单位上传
    private final String APPLY_OTHER_MATERIAL_PERSONAL = "QTPER";//其他需要上传的材料（个人）
    private final String APPLY_OTHER_MATERIAL_GROUP = "QTGRP";//其他需要上传的材料（单位）
    private final String APPLY_UNION_GROUP = "GYLHJMXYS";//联合竞买材料（单位）
    private final String APPLY_UNION_PERSONAL = "LHJMXYS";//联合竞买材料（个人）
    /**
     * 竞买人申请地块时上传材料分类
     */
    Map applyAttachmentCategory;

    /**
     * 出让地块附件材料分类
     */
    Map resourceAttachmentCategory;

    public void setApplyAttachmentCategory(Map applyAttachmentCategory) {
        this.applyAttachmentCategory = applyAttachmentCategory;
    }

    public void setResourceAttachmentCategory(Map resourceAttachmentCategory) {
        this.resourceAttachmentCategory = resourceAttachmentCategory;
    }

    /**
     * 地块报名时上传服务分类
     *
     * @return
     */
    @Override
    public Map getTransResourceApplyAttachmentCategory() {
        return applyAttachmentCategory;
    }

    /**
     * 获取出让地块附件分类，用于后台地块信息录入
     *
     * @return
     */
    @Override
    public Map getTransResourceAttachmentCategory() {
        return resourceAttachmentCategory;
    }

    /**
     * 检查client端报名时上传资料是否完整
     *
     * @return
     */
    @Override
    public boolean checkAttachmentNessesary(String applyId,String applyType,String ggId) {
//        List<MaterialCrgg> materialCrggs = materialCrggService.getMaterialCrggByCrggId(ggId);
//        List<TransMaterial> necessaryCategories = Lists.newArrayList();//所有勾选材料
//        List necessaryCategoriesPersonal = Lists.newArrayList();//个人需要上传的材料
//        List necessaryCategoriesGroup = Lists.newArrayList();//非个人需要上传的材料
//        List<TransUserUnion> userUnion = transUserUnionService.findTransUserUnion(applyId);
//
//        for(MaterialCrgg materialCrgg:materialCrggs){
//            necessaryCategories.add(transMaterialService.getMaterialsById(materialCrgg.getMaterialId()));
//        }
//        for(TransMaterial transMaterial:necessaryCategories){
//            if(transMaterial.getMaterialType().equals(APPLY_TYPE_PERSONAL)){
//                if(!transMaterial.getMaterialCode().equals(APPLY_UNION_GROUP)){
//                    necessaryCategoriesPersonal.add(transMaterial.getMaterialCode());
//                }else{
//                    if(userUnion.size()>0){
//                        necessaryCategoriesPersonal.add(transMaterial.getMaterialCode());
//                    }
//                }
//
//            }else{
//                if(!transMaterial.getMaterialCode().equals(APPLY_UNION_PERSONAL)) {
//                    necessaryCategoriesGroup.add(transMaterial.getMaterialCode());
//                }else{
//                    if(userUnion.size()>0){
//                        necessaryCategoriesPersonal.add(transMaterial.getMaterialCode());
//                    }
//                }
//            }
//        }
//        List uploadedFiles = Lists.newArrayList();
//        if(StringUtils.isNotBlank(applyId)){
//            List<TransFile> transFiles = transFileService.getTransFileByKey(applyId);
//            for(TransFile file:transFiles) {
//                uploadedFiles.add(file.getFileType());
//            }
//            if(StringUtils.isNotBlank(applyType)){
//                if(applyType.equals(APPLY_TYPE_PERSONAL)){//个人
//                    return uploadedFiles.containsAll(necessaryCategoriesPersonal);
//                }else{//单位
//                    return uploadedFiles.containsAll(necessaryCategoriesGroup);
//                }
//            }
//        }
        return false;
    }
}
