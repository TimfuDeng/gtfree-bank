package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransResourceOffer;

import java.util.List;

/**
 * Created by JIBO on 2016/9/14.
 */
public interface ResourceOfferService {
    int insert(TransResourceOffer resourceOffer);

    TransResourceOffer selectMaxOffer(String resourceId);

    List<TransResourceOffer> selectOfferListByPage(String resourceId,
                                                   int limit,
                                                   int offset);
    int selectCount(String resourceId);
}
