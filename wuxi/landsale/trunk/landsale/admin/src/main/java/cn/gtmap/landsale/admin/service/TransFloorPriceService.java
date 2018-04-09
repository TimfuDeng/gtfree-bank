package cn.gtmap.landsale.admin.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFloorPrice;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 底价管理
 * @author cxm
 * @version v1.0, 2017/11/07
 */
public interface TransFloorPriceService {
    /**
     * 某个用户，对不同地区有不同用途地块的底价管理权限
     * @param userId
     * @return
     */
    List<TransFloorPrice> getXzqYtList(String userId);

    /**
     * 底价授权
     * @param userId
     * @param regionCodes
     * @param tdytDictCodes
     * @return
     */
    ResponseMessage<TransFloorPrice> saveGrant(String userId,String regionCodes,String tdytDictCodes);

    /**
     * 编辑
     * @param transFloorPrice
     * @param regionCodes
     * @param tdytDictCodes
     * @return
     */
    ResponseMessage<TransFloorPrice> editGrant(TransFloorPrice transFloorPrice,String regionCodes,String tdytDictCodes);

    /**
     * 删除
     * @param floorPriceId
     * @return
     */
    ResponseMessage<TransFloorPrice> deleteGrant(String floorPriceId);


    /**
     * 获取角色的底价权限
     * @param userId
     * @return
     */
    TransFloorPrice getTransFloorPrice(String userId);
}
