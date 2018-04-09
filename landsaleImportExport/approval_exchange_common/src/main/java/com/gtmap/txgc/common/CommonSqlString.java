package com.gtmap.txgc.common;

import oracle.sql.TIMESTAMP;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 中间库sql
 * Created by liushaoshuai on 2018/1/5.
 */
public class CommonSqlString {

    /**
     * 重中间库中取一条crgg
     * @param regionList
     * @param afficheList
     * @return
     */
    public static HashMap<String,Object> queryCrgg(List<String> regionList,List<String> afficheList){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=regionList&&regionList.size()>0){
            sb.append("region_code in (");
            for(String region:regionList){
                sb.append("'"+region.toString()+"',");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append(") ");
        }

        if (null!=afficheList&&afficheList.size()>0){
            sb.append(" and affiche_type in (");
            for(String affiche:afficheList){
                sb.append("'"+affiche.toString()+"',");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append(") ");
        }
        sb.append(" and control_status = '0' ");
        sb.append(" and rownum<2 ");

        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_crgg", condition);
        return parametersMap;
    }

    /**
     * 查询地块信息，地块控制状态是0的
     * @param ggId
     * @return
     */
    public static HashMap<String,Object> queryResource(String ggId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=ggId){
            sb.append("gg_id = '"+ggId+"'");
        }
        sb.append(" and control_status = '0' ");
        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_resource", condition);
        return parametersMap;
    }

    /**
     * 查询地块附属信息表
     * @param resourceId
     * @return
     */
    public static HashMap<String,Object> queryResourceInfo(String resourceId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=resourceId){
            sb.append("resource_id = '"+resourceId+"'");
        }
        sb.append(" and control_status = '0' ");
        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_resource_info", condition);
        return parametersMap;
    }

    /**
     * 查询地块的小地块表
     * @param resourceId
     * @return
     */
    public static HashMap<String,Object> queryResourceSon(String resourceId){
        StringBuffer sb = new StringBuffer();
        String condition = null;
        if (null!=resourceId){
            sb.append("resource_id = '"+resourceId+"'");
        }

        condition = sb.toString();
        HashMap<String,Object> parametersMap = Untils.genParameterMap("*", "trans_resource_son", condition);
        return parametersMap;
    }

    /**
     * 更新公告控制状态
     * @param ggId
     * @param controlStatus
     * @return
     */
    public static  HashMap<String,Object> updateCrgg(String ggId,String controlStatus) {
        StringBuffer condition = new StringBuffer();
        StringBuffer column = new StringBuffer();
        if (null!=ggId){
            condition.append("gg_id = '"+ggId+"'");
        }

        if (null!=controlStatus){
            column.append("control_status ='"+ controlStatus +"' ");
        }
        HashMap<String,Object> parametersMap = Untils.genParameterMap(column.toString(), "trans_crgg", condition.toString());
        return parametersMap;
    }

    @Deprecated
    public static  HashMap<String,Object> updateCrgg2(String ggId,String controlStatus) {
        StringBuffer condition = new StringBuffer();
        StringBuffer column = new StringBuffer();
        if (null!=ggId){
            condition.append("gg_id = '"+ggId+"'");
        }

        if (null!=controlStatus){
            column.append("crgg_stauts ='"+ controlStatus +"' ");
        }
        HashMap<String,Object> parametersMap = Untils.genParameterMap(column.toString(), "trans_crgg", condition.toString());
        return parametersMap;
    }

    /**
     * 更新地块控制状态
     * @param resourceId
     * @param controlStatus
     * @return
     */
    public static  HashMap<String,Object> updateResource(String resourceId,String controlStatus) {
        StringBuffer condition = new StringBuffer();
        StringBuffer column = new StringBuffer();
        if (null!=resourceId){
            condition.append("resource_id = '"+resourceId+"'");
        }

        if (null!=controlStatus){
            column.append("control_status ='"+ controlStatus +"' ");
        }
        HashMap<String,Object> parametersMap = Untils.genParameterMap(column.toString(), "trans_resource", condition.toString());
        return parametersMap;
    }

    /**
     * 更新地块附表更新状态
     * @param infoId
     * @param controlStatus
     * @return
     */
    public static  HashMap<String,Object> updateResourceInfo(String infoId,String controlStatus) {
        StringBuffer condition = new StringBuffer();
        StringBuffer column = new StringBuffer();
        if (null!=infoId){
            condition.append("info_id = '"+infoId+"'");
        }

        if (null!=controlStatus){
            column.append("control_status ='"+ controlStatus +"' ");
        }
        HashMap<String,Object> parametersMap = Untils.genParameterMap(column.toString(), "trans_resource_info", condition.toString());
        return parametersMap;
    }

    /**
     * 更新地块表成功信息
     * @param parameterMap
     * @return
     */
    public static  String updateResourceSucessInfo(HashMap<String,Object> parameterMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("update trans_resource set ");
        Set<String> keys = parameterMap.keySet();
        for (String key:keys){
            Object object = parameterMap.get(key);
            if (!"RESOURCE_ID".equalsIgnoreCase(key)){//对RESOURCE_ID不更新
                if (object instanceof TIMESTAMP){//时间单独处理
                    sb.append(key+"= to_timestamp('"+object.toString()+"','yyyy-mm-dd hh24:mi:ss.ff'),");
                }else {
                    sb.append(key + "= '" + object.toString() + "',");
                }
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" where 1=1 ");
        sb.append(" and resource_id = '"+parameterMap.get("RESOURCE_ID").toString()+"'");
        return sb.toString();
    }

}
