package com.gtmap.txgc.common;

import java.util.HashMap;

/**
 * 苏州交易工业sql
 * Created by liushaoshuai on 2018/1/5.
 */
public class IndustrySqlString {


    /**
     * 保存一条公告需要传入的参数
     * @param parameters 列名-列名对应的值
     * @return
     */
    public static HashMap<String,Object> saveCrgg(HashMap<String,Object>  parameters) throws Exception{

        HashMap<String,Object> parametersMap = Untils.genSaveParameterType("trans_crgg", parameters);
        return parametersMap;
    }

    /**
     * 保存一条地块需要传入的参数
     * @param parameters 列名-列名对应的值
     * @return
     */
    public static HashMap<String,Object> saveResource(HashMap<String,Object>  parameters) throws Exception{

        HashMap<String,Object> parametersMap = Untils.genSaveParameterType("trans_resource", parameters);
        return parametersMap;
    }


    /**
     * 保存一条地块附件表需要传入的参数
     * @param parameters 列名-列名对应的值
     * @return
     */
    public static HashMap<String,Object> saveResourceInfo(HashMap<String,Object>  parameters) throws Exception{
        HashMap<String,Object> parametersMap = Untils.genSaveParameterType("trans_resource_info", parameters);
        return parametersMap;
    }


    /**
     * 根据地块id查询地块信息
     * @param resourceId
     * @return
     */
    public static HashMap<String,Object> queryResource(String resourceId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=resourceId) {
            sb.append("resource_id = '"+resourceId+"'");

        }
        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_resource", condition);
        return parametersMap;
    }

    /**
     * 根据报价id查询报价信息
     * @param offerId
     * @return
     */
    public static HashMap<String,Object> queryResourceOffer(String offerId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=offerId) {
            sb.append("offer_id = '"+offerId+"'");

        }
        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_resource_offer", condition);
        return parametersMap;
    }

    /**
     * 根据用户id查询用户信息map参数
     * @param userId
     * @return
     */
    public static HashMap<String,Object> queryUser(String userId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=userId) {
            sb.append("user_id = '"+userId+"'");

        }
        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_user", condition);
        return parametersMap;
    }

    /**
     * 根据用户id查询经办人信息map参数
     * @param userId
     * @return
     */
    public static HashMap<String,Object> queryApplyInfo(String userId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=userId) {
            sb.append("user_id = '"+userId+"'");

        }
        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_user_apply_info", condition);
        return parametersMap;
    }


    /**
     * 查询地块信息成交信息
     * @return
     */
    public static String querySuccessInfo(String days){
        StringBuffer sb = new StringBuffer();
        String sql = null;
        sb.append("select t1.resource_id,t1.over_time,t2.offer_price,")
                .append(" t3.user_name,t4.contact_person,t4.contact_address,")
                .append(" t4.contact_telephone from trans_resource t1 ")
                .append(" left join trans_resource_offer t2 on t1.offer_id=t2.offer_id ")
                .append(" left join trans_user t3 on t2.user_id=t3.user_id ")
                .append(" left join trans_user_apply_info t4 on t3.user_id=t4.user_id ")
                .append(" where 1=1 and ")
                .append(" t1.resource_edit_status='4' or t1.resource_edit_status='9' ")
                .append(" and  t1.over_time > (sysdate-"+days+") ");

        sql = sb.toString();
        return sql;
    }
}
