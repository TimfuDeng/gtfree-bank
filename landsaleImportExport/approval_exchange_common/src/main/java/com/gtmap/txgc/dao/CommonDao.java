package com.gtmap.txgc.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface CommonDao {
	
	public  List<HashMap<String,Object>> commonQuery(HashMap<String, Object> parametersMap);
	
	public List<HashMap<String,Object>> commonSqlQuery(@Param(value="sql")String sql);

	public void saveCommon(HashMap<String,Object> parametersMap);

	public void updateCommon(HashMap<String,Object> parametersMap);

	/*public void saveCrgg(HashMap<String,Object> parametersMap);

	public void saveTest();*/

/*	public void saveFj(HashMap<String, Object> parametersMap);
	
	public void deleteFj(HashMap<String, Object> parametersMap);*/

	@Select("select * from dual")
	 List<HashMap<String,Object>> commonSqlQuery2();
}
