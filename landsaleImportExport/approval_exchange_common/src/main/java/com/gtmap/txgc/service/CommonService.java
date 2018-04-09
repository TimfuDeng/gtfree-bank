package com.gtmap.txgc.service;

import com.gtmap.txgc.dao.CommonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("commonService")
public class CommonService {

	@Resource
	private CommonDao commonDao;
	
	/**
	 * 传入表名，字段名和查询条件进行执行
	 * @param parametersMap
	 * @return
	 */
	public List<HashMap<String,Object>> commonQuery(HashMap<String,Object> parametersMap) throws Exception{
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

	/**
	 * 传入表名，hashmap
	 * hashmap的key是字段名
	 * hashmap的value是值
	 * @param parameterMap
	 */
	public void saveCommon(HashMap<String,Object> parameterMap){
		commonDao.saveCommon(parameterMap);
	}

	/**
	 * 出入表名，列名，列名值 条件
	 * 这里的通用update具有一定的局限，必须在column里面把值设置好，须带改进
	 * 备注条件不能为空，否则会把表里面所有的记录都更新
	 * @param parameterMap
	 */
	public void updateCommon(HashMap<String,Object> parameterMap) {
		commonDao.updateCommon(parameterMap);
	}

/*	public void saveCrgg(HashMap<String,Object> parameterMap){
		commonDao.saveCrgg(parameterMap);
	}

	public void saveTest(){
		commonDao.saveTest();
	}*/




	
}
