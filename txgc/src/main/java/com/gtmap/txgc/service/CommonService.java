package com.gtmap.txgc.service;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.gtmap.txgc.dao.CommonDao;

@Service("commonService")
public class CommonService {

	@Resource
	private CommonDao commonDao;
	
	/**
	 * 传入表名，字段名和查询条件进行执行
	 * @param parametersMap
	 * @return
	 */
	public List<HashMap<String,Object>> commonQuery(HashMap<String,Object> parametersMap){
		return commonDao.commonQuery(parametersMap);
	}
	
	/**
	 * 直接传入一个完整的SQL进行执行
	 * @param sql
	 * @return
	 */
	public List<HashMap<String,Object>> commonSqlQuery(String sql){
		return commonDao.commonSqlQuery(sql);
	}
	
}
