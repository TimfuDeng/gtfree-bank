package cn.gtmap.landsale.support;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * 将postgre中的字段转换为LinkedHashMap
 * Created by JIBO on 2016/9/14.
 */
public class JSONHandler implements TypeHandler<Map> {


    public void setParameter(PreparedStatement preparedStatement, int i, Map map, JdbcType jdbcType) throws SQLException {
        if(map == null){
            preparedStatement.setString(i, null);
        }else {
            String json = JSON.toJSONString(map);
            preparedStatement.setString(i, json);
        }
    }

    public Map getResult(ResultSet resultSet, String s) throws SQLException {
        String  json = resultSet.getString(s);
        return  jsonToObject(json);
    }

    public Map getResult(ResultSet resultSet, int i) throws SQLException {
        String  json = resultSet.getString(i);
        return  jsonToObject(json);
    }

    public Map getResult(CallableStatement callableStatement, int i) throws SQLException {
        String  json = callableStatement.getString(i);
        return  jsonToObject(json);
    }

    private Map jsonToObject(String json){
        return JSON.parseObject(json, LinkedHashMap.class);
    }
}
