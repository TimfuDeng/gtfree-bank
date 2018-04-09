package com.gtmap.txgc.service.industry;

import com.gtmap.txgc.common.CommonSqlString;
import com.gtmap.txgc.common.IndustrySqlString;
import com.gtmap.txgc.common.SqlString;
import com.gtmap.txgc.config.DataSource;
import com.gtmap.txgc.config.DataSourceContextHolder;
import com.gtmap.txgc.service.CommonService;
import com.gtmap.txgc.service.middle.MiddleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liushaoshuai on 2018/1/4.
 */
@Service("industryService")
public class IndustryService {
    Logger logger = LoggerFactory.getLogger(IndustryService.class);
    @Autowired
    CommonService commonService;

    @Autowired
    MiddleService middleService;

    @Value("${industry.days}")
    String days;


    /**
     * 传入表名，字段名和查询条件进行执行
     * @param parametersMap
     * @return
     */
    @Deprecated
    public List<HashMap<String,Object>> hashMapList(HashMap<String,Object> parametersMap){
        DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
        try {
            return commonService.commonQuery(SqlString.queryTest());
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }



    /**
     * 中间库的数据转化为工业数据库数据方法
     * 完成中间件所有的业务除了附件
     * 1、读取数据到交易
     * 2、跟新已经读取的数据 设置控制状态为1
     * 3、从交易获取成交信息到中间库  只读取规定时间内的成交数据
     * 4、保存信息到中间库
     * 5、更新中间库的状态为交易结束已经写入
     */
    public void Middle2Industry() {
        try {
            List<HashMap<String,Object>> crggs = null;
            List<HashMap<String,Object>> resources = null;
            List<HashMap<String,Object>> resourceInfos = new ArrayList<HashMap<String, Object>>();
            List<HashMap<String,Object>> successInfos = null;
            //List<HashMap<String,Object>> resourceSons = null;
            //查询出一条公告记录
            crggs = middleService.hashMapListCrgg();

            //根据公告主键查询出公告下面的所有地块
            for (HashMap<String,Object> crggMap:crggs){
                String ggId = crggMap.get("GG_ID").toString();
                resources = middleService.hashMapListResource(ggId);

            }
            //根据地块主键查询出所有地块附表，并把地块附表放进list
            for (HashMap<String,Object> resourceMap:resources){
                List<HashMap<String,Object>> resourceInfo = null;
                String resourceId = resourceMap.get("RESOURCE_ID").toString();
                resourceInfo = middleService.hashMapListResourceInfo(resourceId);
                resourceInfos.addAll(resourceInfo);

            }

            //把中间库的trans_crgg 转化成工业库trans_crgg 对应的字段-字段值
            crggs = parseIndusrtyCrggs(crggs);
            //把中间库的trans_resource 转化成工业库trans_resource 对应的字段-字段值
            resources = parseIndusrtyResource(resources);
            //把中间库的trans_resource_info 转化成工业库 trans_resource_info 对应的字段-字段值
            resourceInfos = parseIndusrtyResourceInfos(resourceInfos);

            //保存转化后的trans_crgg、trans_resource、trans_resource_info 信息，
            saveIndustryCrggResourceResourceInfo(crggs,resources,resourceInfos);
            //并把保存成功的信息控制状态设置为1
            //控制状态：0-初始状态 1-交易系统读取成功状态 2-交易系统写入成功状态 3-批供用补系统读取成功状态
            updateMiddleControlStauts(crggs,resources,resourceInfos,"1");

            //查找成交人信息：成交价、成交单位、成交时间、经办人、经办人联系方式、经办人联系地址（联合竞买只推送主联合人信息）
            successInfos =  this.querySuccessInfo();
            for (HashMap<String,Object> successInfo:successInfos){
                //设置成交人信息到中间库，必须有RESOURCE_ID键值对、必须有有要更新的字段
                if (successInfo.size()>1&&null!=successInfo.get("RESOURCE_ID")){
                    List<HashMap<String,Object>> result = middleService.updateMiddleSuccessInfo(successInfo);
                }else {
                    logger.error("缺少RESOURCE_ID或者没有要更新的字段！");
                }

            }

            //并把保存成功的信息控制状态设置为2
            //控制状态：0-初始状态 1-交易系统读取成功状态 2-交易系统写入成功状态 3-批供用补系统读取成功状态
            updateMiddleControlStauts(crggs,resources,resourceInfos,"2");


        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }


    }


    /**
     * 保存一条记录
     * 传入表名 和 列名-值
     * @param parametersMap
     */
    public void saveCommon (HashMap<String,Object> parametersMap) {
        DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
        try {
             commonService.saveCommon(parametersMap);
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }

    }

    /**
     * 根据地块id查询地块记录
     * @param resourceId
     * @return
     */
    public List<HashMap<String,Object>> queryResource(String resourceId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
            return commonService.commonQuery(IndustrySqlString.queryResource(resourceId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }


    /**
     * 根据报价id查询报价记录
     * @param offerId
     * @return
     */
    public List<HashMap<String,Object>> queryResourceOffer(String offerId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
            return commonService.commonQuery(IndustrySqlString.queryResourceOffer(offerId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }

    /**
     * 根据地块id查询地块记录
     * @param userId
     * @return
     */
    public List<HashMap<String,Object>> queryUser(String userId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
            return commonService.commonQuery(IndustrySqlString.queryUser(userId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }

    /**
     * 根据地块id查询地块记录
     * @param userId
     * @return
     */
    public List<HashMap<String,Object>> queryApplyInfo(String userId){
        try {
            DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
            return commonService.commonQuery(IndustrySqlString.queryApplyInfo(userId));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }






    /**
     * 查询地块成交信息将成交信息放在list<map>里面
     * @param
     * @return
     */
    public List<HashMap<String,Object>> querySuccessInfo(){

        try {
            DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
            return commonService.commonSqlQuery(IndustrySqlString.querySuccessInfo(days));
        } catch (Exception e) {
            logger.error(e.toString(),e.getStackTrace());
        }
        return null;
    }


    /**
     *保存公告、地块、地块附属表
     * @param crggs 保存到工业库的公告
     * @param resources 保存到工业库的地块
     * @param resourceInfos 保存到工业库的地块附表
     * @throws Exception
     */
    @Transactional
    public void saveIndustryCrggResourceResourceInfo(List<HashMap<String,Object>> crggs,List<HashMap<String,Object>> resources,List<HashMap<String,Object>> resourceInfos) throws Exception{

        for (HashMap<String,Object> crgg:crggs){
            this.saveCrgg(crgg);
        }
        for (HashMap<String,Object> resource:resources){
            this.saveResource(resource);
        }
        for (HashMap<String,Object> resourceInfo:resourceInfos){
            this.saveResourceInfo(resourceInfo);
        }
    }

    /**
     * 将中间库的数据的读取的表控制状态设置为1
     * @param crggs
     * @param resources
     * @param resourceInfos
     * @throws Exception
     */
    @Transactional
    public void updateMiddleControlStauts(List<HashMap<String,Object>> crggs,List<HashMap<String,Object>> resources,List<HashMap<String,Object>> resourceInfos,String controlStatus) throws Exception{
        for (HashMap<String,Object> crgg: crggs) {
            middleService.updateCrggControlStatus(crgg.get("GG_ID").toString(),controlStatus);
        }
        for (HashMap<String,Object> resource:resources) {
            middleService.updateResourceControlStatus(resource.get("RESOURCE_ID").toString(), controlStatus);
        }

        for (HashMap<String,Object> resourceInfo:resourceInfos) {
            middleService.updateInfoControlStatus(resourceInfo.get("INFO_ID").toString(), controlStatus);
        }
    }



    /**
     * 测试spring事务开启之后能否进行数据源切换
     * @throws Exception
     */
    @Deprecated
    @Transactional( readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = {java.lang.Exception.class})
    public void updateMiddleControlStautsTest3() throws Exception{
        DataSourceContextHolder.setDataSourceType(DataSource.commonDataSource);
        commonService.updateCommon(CommonSqlString.updateCrgg2("015c3d66f0fb802962785c3aa92800c8", "2"));

        DataSourceContextHolder.setDataSourceType(DataSource.industryDataSource);
        commonService.updateCommon(CommonSqlString.updateCrgg2("015c3d66f0fb802962785c3aa92800c8", "2"));



    }





    /**
     * 保存一条公告记录
     * @param crggMap 公告列名-列名对应的值
     */
    public void saveCrgg(HashMap<String,Object> crggMap) throws Exception {
        this.saveCommon(IndustrySqlString.saveCrgg(crggMap));
    }

    /**
     * 保存一条地块记录
     * @param resourceMap 地块列名-列名对应的值
     */
    public void saveResource(HashMap<String,Object> resourceMap) throws Exception {
        this.saveCommon(IndustrySqlString.saveResource(resourceMap));
    }

    /**
     * 保存一条地块附表记录
     * @param resourceInfoMap 地块附表列名-列名对应的值
     */
    public void saveResourceInfo(HashMap<String,Object> resourceInfoMap) throws Exception {
        this.saveCommon(IndustrySqlString.saveResourceInfo(resourceInfoMap));
    }


    /**
     * 中间库的trans_crgg转化成苏州市局工业库trans_crgg的数据
     * @param crggs 中间库的trans_crgg 的列名和列名值
     * @return
     */
    private List<HashMap<String,Object>> parseIndusrtyCrggs(List<HashMap<String,Object>> crggs){

        /**
         * 排除法，去掉 CONTROL_STATUS 字段-字段值
         */
        for (HashMap<String,Object> crgg:crggs){
            crgg.remove("CONTROL_STATUS");
        }
        return crggs;

    }


    /**
     * 中间库的trans_resource转化成苏州市局工业库trans_resource的数据
     * @param resources 中间库的trans_resource 的列名和列名值
     * @return
     */
    private List<HashMap<String,Object>> parseIndusrtyResource(List<HashMap<String,Object>> resources){

        /**
         * 排除法，去掉CONTROL_STATUS，SUCCESS_UNIT，SUCCESS_PRICE，CONTRACT_PERSON，CONTRACT_TELEPHONE，CONTRACT_ADDRESS
         * 以上字段的的字段-字段值
         */
        for (HashMap<String,Object> resource:resources){
            resource.remove("CONTROL_STATUS");
            resource.remove("SUCCESS_UNIT");
            resource.remove("SUCCESS_PRICE");
            resource.remove("CONTRACT_PERSON");
            resource.remove("CONTRACT_TELEPHONE");
            resource.remove("CONTRACT_ADDRESS");
        }
        return resources;

    }

    /**
     * 中间库的trans_resource_info转化成苏州市局工业库trans_resource的数据
     * @param resourceInfos 中间库的trans_resource_info 的列名和列名值
     * @return
     */
    private List<HashMap<String,Object>> parseIndusrtyResourceInfos(List<HashMap<String,Object>> resourceInfos){

        /**
         * 排除法，去掉CONTROL_STATUS 以上字段的的字段-字段值
         */
        for (HashMap<String,Object> resourceInfo:resourceInfos){
            resourceInfo.remove("CONTROL_STATUS");
        }
        return resourceInfos;

    }


}
