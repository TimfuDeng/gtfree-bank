package cn.gtmap.landsale.mapper;


import cn.gtmap.landsale.model.QueryCondition;
import cn.gtmap.landsale.model.TransResource;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by JIBO on 2016/9/14.
 */
public interface TransResourceMapper {

    int insert(TransResource record);

    int update(TransResource record);

    int updateStatusToFinish(@Param("resourceId")String resourceId,
                             @Param("overStatus")String overStatus,
                             @Param("transUser")String transUser);

    TransResource selectByKey(String resourceId);

    List<TransResource> selectByPage(@Param("condition")QueryCondition condition,
                                     @Param("time") Date cDate,
                                     @Param("limit")int limit,
                                     @Param("offset")int offset);

    int selectCount(@Param("condition")QueryCondition condition,
                    @Param("time") Date cDate);
}
