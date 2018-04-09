package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransDept;
import cn.gtmap.landsale.model.TransNews;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2015/10/9.
 */
public interface TransDeptService {

    /**
     * 查寻组织机构列表
     * @param title 部门名称
     * @param request
     * @return page对象
     */
    public Page<TransDept> findTransDept(String title,Pageable request);

    /**
     * 查找所有的机构
     * @return  所有机构list
     */
    public List<TransDept> findAllTransDept();

    /**
     * 保存组织机构
     * @param transDept
     * @return
     */
    public TransDept saveTransDept(TransDept transDept);

    /**
     * 删除组织机构
     * @param deptIds
     */
    public void deleteTransDept(Collection<String> deptIds);

    /**
     * 得到组织机构
     * @param deptId 组织机构id
     * @return
     */
    public TransDept getTransDept(String deptId);
}
