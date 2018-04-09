package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransNoticeTargetRel;
import cn.gtmap.landsale.model.TransTargetGoodsRel;

import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public interface TransTargetGoodsRelService {
    public void saveTransTargetGoodsRel(TransTargetGoodsRel transTargetGoodsRel);

    public TransTargetGoodsRel findTransTargetGoodsRelByGoodsId(String goodsId);

    /**
     * 通过标的id得到地块
     * @param targetId
     * @return
     */
    public List<TransTargetGoodsRel> findTransTargetGoodsRelTargetId(String targetId);


}
