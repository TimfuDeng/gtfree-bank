package cn.gtmap.landsale.mapper;

import cn.gtmap.landsale.model.TransResourceOffer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by JIBO on 2016/9/14.
 */
public interface ResourceOfferMapper {

    int insert(TransResourceOffer record);


    List<TransResourceOffer> selectOfferListByPage(@Param("resourceId") String resourceId,
                                                   @Param("limit")int limit,
                                                   @Param("offset")int offset);

    int selectCount(String resourceId);
}
