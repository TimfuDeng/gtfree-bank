package com.gtmap.txgc.common;

import oracle.sql.TIMESTAMP;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 通用工具
 * Created by liushaoshuai on 2018/1/5.
 */
public class Untils {

    /**
     * 通过变量将所要查询的语句一一对应，在mapper里面一次将变量组织成sql
     * @param columns 列名
     * @param tableName 表名
     * @param condition 条件
     * @return
     */
    public static HashMap<String,Object> genParameterMap(String columns, String tableName, String condition){
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put(Constants.COLUMNS, columns);
        paraMap.put(Constants.TABLENAME, tableName);
        paraMap.put(Constants.CONDITION, condition);
        return paraMap;
    }


    /**
     * 传入表名和参数值
     * @param tableName 表名
     * @param parameters 列名-列名对应的值
     * @return tableName-${表名} parameters-{列名-列名对应的值}
     */
    public static HashMap<String,Object> genSaveParameterMap(String tableName,HashMap<String,Object>  parameters){
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put(Constants.TABLENAME, tableName);
        paraMap.put(Constants.PARAMETERS,parameters);
        return paraMap;
    }

    /**
     *
     * @param tableName 表名
     * @param parameters 列名-列名对应的值
     * @return key-value
     */
    public static HashMap<String,Object> genSaveMap(String tableName,HashMap<String,Object>  parameters){
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put(Constants.TABLENAME, tableName);
        paraMap.putAll(parameters);
        return paraMap;
    }

    /**
     * 传入表名和参数值 按类型(exp:timestamp、decimal、clob、varchar)来组织成对应的list
     * @param tableName 表名
     * @param parameters 列名-列名对应的值
     * @return tableName-${表名} columnstimestap-{列名} valuestimestap-{值}
     */
    public static HashMap<String,Object> genSaveParameterType(String tableName,HashMap<String,Object>  parameters) throws Exception{
        Set<String> sets = parameters.keySet();
        List<String> keyTimestamp = new ArrayList<String>();
        List<Object> objectTimestamp = new ArrayList<Object>();
        List<String> keyVarchar = new ArrayList<String>();
        List<Object> objectVarchar = new ArrayList<Object>();
        List<String> keyClob = new ArrayList<String>();
        List<String> objectClob = new ArrayList<String>();
        List<String> keyDecimal = new ArrayList<String>();
        List<Object> objectDecimal = new ArrayList<Object>();
        for (String key:sets){

            if (parameters.get(key) instanceof BigDecimal){
                keyDecimal.add(key);
                objectDecimal.add(parameters.get(key));
            }else if(parameters.get(key) instanceof Clob){
                keyClob.add(key);
                Clob clob = (Clob) parameters.get(key);
                objectClob.add(new String(Clob2String(clob)));
            }else if (parameters.get(key) instanceof TIMESTAMP){
                keyTimestamp.add(key);
                objectTimestamp.add(parameters.get(key).toString());
            }else {
                keyVarchar.add(key);
                objectVarchar.add(parameters.get(key));
            }


        }
        return genSaveListType(tableName, keyTimestamp, objectTimestamp, keyClob, objectClob, keyDecimal, objectDecimal, keyVarchar, objectVarchar);
    }


    /**
     * Clob转String
     * @param clob
     * @return
     * @throws Exception
     */
    public static String Clob2String(Clob clob) throws Exception{
        String resultString = "";
        Reader reader = clob.getCharacterStream();
        BufferedReader br = new BufferedReader(reader);
        String brLine = null;
        StringBuffer sb = new StringBuffer();
        while ((brLine = br.readLine()) != null){
            sb.append(brLine);
        }
        resultString = sb.toString();
        reader.close();
        br.close();
        return resultString;
    }


    /**
     *
     * @param tableName 表名
     * @param keyTimestamp timestamp类型数据列名
     * @param objectTimestamp timestamp 类型数据值
     * @param keyClob clob类型数据列名
     * @param objectClob clob 类型数据值
     * @param keyDecimal timestamp类型数据列名
     * @param objectDecimal decimal 类型数据值
     * @param keyVarchar timestamp类型数据列名
     * @param objectVarchar varchar 类型数据值
     * @return tableName-表名、key${类型}-类型数据列名、object${类型}-类型数据值
     */
    public static HashMap<String,Object> genSaveListType(String tableName,List<String> keyTimestamp,List<Object> objectTimestamp,
                                                         List<String> keyClob,List<String> objectClob,
                                                         List<String> keyDecimal,List<Object> objectDecimal,
                                                         List<String> keyVarchar,List<Object> objectVarchar) {
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put(Constants.TABLENAME, tableName);
        paraMap.put(Constants.COLUMNS_TIMESTAMP,keyTimestamp);
        paraMap.put(Constants.VALUES_TIMESTAMP,objectTimestamp);
        paraMap.put(Constants.COLUMNS_CLOB,keyClob);
        paraMap.put(Constants.VALUES_CLOB,objectClob);
        paraMap.put(Constants.COLUMNS_DECIMAL,keyDecimal);
        paraMap.put(Constants.VALUES_DECIMAL,objectDecimal);
        paraMap.put(Constants.COLUMNS_VARCHAR,keyVarchar);
        paraMap.put(Constants.VALUES_VARCHAR,objectVarchar);

        return paraMap;
    }


}
