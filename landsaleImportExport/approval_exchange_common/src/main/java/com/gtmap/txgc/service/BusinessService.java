/*
package com.gtmap.txgc.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gtmap.txgc.bean.PewgBean;
import com.gtmap.txgc.common.SqlString;
import com.gtmap.txgc.dao.CommonDao;

@Service("businessService")
public class BusinessService {
	
	@Resource
	private CommonDao commonDao;
	
	public List<HashMap<String,Object>> retrievePewg(PewgBean pewg){
		List<HashMap<String,Object>> result = commonDao.commonQuery(SqlString.queryPewg(pewg.getXzq(), pewg.getTbrq()));
		return result;
	}
	
	public HashMap<String,Object> retrieveMaxPewg(PewgBean pewg){
		List<HashMap<String,Object>> result = commonDao.commonSqlQuery(SqlString.queryMaxPewg(pewg.getXzq(), pewg.getTbrq()));
		if(result!=null&&result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	public HashMap<String,Object> retrieveUser(String username){
		String sql = "select username,regioncode from t_user t where username='"+username+"'";
		List<HashMap<String,Object>> result = commonDao.commonSqlQuery(sql);
		if(result!=null&&result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	public List<HashMap<String,Object>> retrieveFjByLinkGuid(String linkGuid){
		linkGuid = linkGuid.replace("'", "");
		String sql = "SELECT FJ_GUID,FJ_MC,FJ_LJ,REAL_NAME,LINK_GUID FROM T_TXGC_FJ where LINK_GUID='"+linkGuid+"'";
		List<HashMap<String,Object>> list = commonDao.commonSqlQuery(sql);
		return list;
	}
	
	public HashMap<String,Object> retrieveFj(String guid){
		guid = guid.replace("'", "");
		String sql = "SELECT FJ_GUID,FJ_MC,FJ_LJ,REAL_NAME,LINK_GUID FROM T_TXGC_FJ where FJ_GUID='"+guid+"'";
		List<HashMap<String,Object>> list = commonDao.commonSqlQuery(sql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public void saveFj(HashMap<String,Object> fjMap){
		commonDao.saveFj(fjMap);
	}
	
	public void deleteFj(String guid){
		if(guid!=null&&guid.length()>0){
			HashMap<String,Object> parasMap = new HashMap<String,Object>();
			parasMap.put("fjGuid", guid);
			commonDao.deleteFj(parasMap);
		}
	}
}
*/
