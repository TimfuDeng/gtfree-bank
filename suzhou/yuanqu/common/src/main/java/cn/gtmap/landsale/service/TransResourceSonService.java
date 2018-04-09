package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.model.TransResourceSon;

import java.util.Collection;
import java.util.List;

/**
 * 地块多用途服务
 * Created by trr on 2015/12/18.
 */
public interface TransResourceSonService {
    /**
     * 查询列表
     * @param title 地块多用途标题
     * @param request
     * @return
     */
    public Page<TransResourceSon> findTransResourceSon(String title,Pageable request);
    /**
     * 保存地块多用途
     * @param transResourceSon
     * @return
     */
    public TransResourceSon saveTransResourceSon(TransResourceSon transResourceSon);

    public void deleteTransResourceSon(Collection<String> resourceSonIds);

    public void deleteTransResourceSonByResourceId(String resourceId);

    public TransResourceSon getTransResourceSon(String resourceSonIds);

    public List<TransResourceSon> getTransResourceSonList(String resourceId);

    public Collection<String> getTransResourceIdsByZdCode(String zdCode);

    /**
     * 根据宗地号和地块id查询
     * @param zdCode
     * @param resourceId
     * @return
     */
    public List<TransResourceSon> getTransResourceSons(String zdCode,String resourceId);
}
