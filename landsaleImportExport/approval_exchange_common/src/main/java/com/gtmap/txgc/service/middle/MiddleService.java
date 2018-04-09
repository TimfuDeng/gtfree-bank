package com.gtmap.txgc.service.middle;

import com.gtmap.txgc.common.CommonSqlString;
import com.gtmap.txgc.config.DataSource;
import com.gtmap.txgc.config.DataSourceContextHolder;
import com.gtmap.txgc.config.RegionAfficheList;
import com.gtmap.txgc.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 中间库查询调用服务
 * 组织最终的查询条件
 * 指定要使用的数据源
 * Created by liushaoshuai on 2018/1/4.
 */
@Service("MiddleService")
public class MiddleService {
    Logger logger = LoggerFactory.getLogger(MiddleService.class);
    @Autowired
    CommonService commonService;

    @Autowired
    @Qualifier("industryRegionAfficheList")
    RegionAfficheList regionAfficheList;

    /**
     *
     * 依据条件查询公告
     *
     * @return
     */
    public List<HashMap<String,Object>> hashMapListCrgg(){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
            return commonService.commonQuery(CommonSqlString.queryCrgg(regionAfficheList.getRegionList(),regionAfficheList.getAfficheList()));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }

    /**
     * 查询公告下面的地块
     * @param ggId
     * @return
     */
    public List<HashMap<String,Object>> hashMapListResource(String ggId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
            return commonService.commonQuery(CommonSqlString.queryResource(ggId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }

    /**
     * 查询地块附表
     * @param resourceId
     * @return
     */
    public List<HashMap<String,Object>> hashMapListResourceInfo(String resourceId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
            return commonService.commonQuery(CommonSqlString.queryResourceInfo(resourceId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }

    /**
     * 查询地块的小地块表
     * @param resourceId
     * @return
     */
    public List<HashMap<String,Object>> hashMapListResourceSon(String resourceId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
            return commonService.commonQuery(CommonSqlString.queryResourceSon(resourceId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }

    /**
     * 根据公告id更新地块控制状态
     * @param ggId 公告表id
     * @param controlStatus 控制状态：0-初始状态 1-交易系统读取成功状态 2-交易系统写入成功状态 3-批供用补系统读取成功状态
     */
    public void updateCrggControlStatus(String ggId, String controlStatus) {
        DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
        if (null==ggId){//公告id不能为空
            throw new NullPointerException("ggId is null!");
        }
        commonService.updateCommon(CommonSqlString.updateCrgg(ggId, controlStatus));
    }

    /**
     * 根据地块id更新地块控制状态
     * @param resourceId 地块表id
     * @param controlStatus 控制状态：0-初始状态 1-交易系统读取成功状态 2-交易系统写入成功状态 3-批供用补系统读取成功状态
     */
    public void updateResourceControlStatus(String resourceId, String controlStatus) {
        DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
        if (null==resourceId){//地块id不能为空
            throw new NullPointerException("resourceId is null!");
        }
        commonService.updateCommon(CommonSqlString.updateResource(resourceId, controlStatus));
    }

    /**
     * 根据地块附表id更新地块控制状态
     * @param infoId 地块附表id
     * @param controlStatus 控制状态：0-初始状态 1-交易系统读取成功状态 2-交易系统写入成功状态 3-批供用补系统读取成功状态
     */
    public void updateInfoControlStatus(String infoId, String controlStatus) {
        DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
        if (null==infoId){//地块附表id不能为空
            throw new NullPointerException("infoId is null!");
        }
        commonService.updateCommon(CommonSqlString.updateResourceInfo(infoId, controlStatus));
    }

    /**
     * 更新中间库的成交信息，将一天以内的数据更新
     * @param parameterMap 必须传入RESOURCE_ID-值和要更新的键值对数据 键值对
     * @return
     */
    public List<HashMap<String,Object>> updateMiddleSuccessInfo(HashMap<String, Object> parameterMap){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
            return commonService.commonSqlQuery(CommonSqlString.updateResourceSucessInfo(parameterMap));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }


}
