package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransNews;

import java.util.Collection;

/**
 * Created by www on 2015/9/23.
 */
public interface TransNewsService {

    /**
     * 查询新闻列表
     * @param title 新闻标题
     * @param request
     * @return
     */
    public Page<TransNews> findTransNews(String title,Pageable request);

    /**
     * 查询已发布的新闻
     * @param request
     * @return
     */
    public Page<TransNews> findTransNewsDeployed(Pageable request);

    /**
     * 保存新闻
     * @param transNews
     * @return
     */
    public TransNews saveTransNews(TransNews transNews);

    public void deleteTransNews(Collection<String> newsIds);

    public TransNews getTransNews(String newsId);
}
