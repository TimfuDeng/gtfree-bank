package com.gtmap.txgc.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CommonDao {
	
	public List<HashMap<String,Object>> commonQuery(HashMap<String, Object> parametersMap);
	
	public List<HashMap<String,Object>> commonSqlQuery(@Param(value="sql")String sql);
	
	public void saveFj(HashMap<String, Object> parametersMap);
	
	public void deleteFj(HashMap<String, Object> parametersMap);
}
