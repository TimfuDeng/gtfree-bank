package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransLaw;

import java.util.Collection;

/**
 * Created by trr on 2016/1/27.
 */
public interface TransLawService {
    /**
     * 新增法律条例
     * @param transLaw
     * @return
     */
    public TransLaw save(TransLaw transLaw);

    /**
     * 删除法律条例
     * @param lawIds
     */
    public void delete(Collection<String> lawIds);

    /**
     * 根据Id查询法律条例
     * @param lawId
     * @return
     */
    public TransLaw getById(String lawId);

    /**
     * 分页获取法律条例对象
     * @param title
     * @param request
     * @return
     */
    public Page<TransLaw> findByPage(String title,Pageable request);

    /**
     * 查询已经发布的法律条例
     * @param request
     * @return
     */
    public Page<TransLaw> findTransNewsDeployed(Pageable request,int lawStatus);
}
