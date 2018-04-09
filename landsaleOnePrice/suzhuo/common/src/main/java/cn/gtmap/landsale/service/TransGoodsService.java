package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransGoods;
import cn.gtmap.landsale.model.TransTarget;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public interface TransGoodsService {
    Page<TransGoods> findTransGoodsList(String title, Collection<String> goodsIds, Pageable request);

    public TransGoods saveTransGoods(TransGoods transGoods);

    List<TransGoods> findTransGoodsListByNo(String name);

    /**
     * 根据id得到地块
     * @param id
     * @return
     */
    TransGoods getTransGoods(String id);




}
