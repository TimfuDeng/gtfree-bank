package com.gtmap.txgc.common;

import java.util.HashMap;

public class SqlString {
	
	public static HashMap<String,Object> queryPewg(String xzq,String tbrq){
		String condition = "XZQ_DM='"+xzq+"' AND TBRQ='"+tbrq+"'";
		HashMap<String,Object> parametersMap = SqlString.genParameterMap("*", "T_TXGC_PEWG", condition);
		return parametersMap;
	}
	
	public static String queryMaxPewg(String xzq,String tbrq){
		StringBuffer sqlSb = new StringBuffer("select max(wgxy_mj) as MJ0 ,max(AGHB_MJ) as MJ1,max(LSGH_MJ) as MJ2,max(LSXM_MJ) as MJ3,max(SSGD_MJ) as MJ4,max(QTFS_MJ) as MJ5,max(ZZBZ_MJ) as MJ6 from t_txgc_pewg t");
		sqlSb.append(" where t.xzq_dm='").append(xzq).append("' and t.tbrq<'").append(tbrq).append("'");
		return sqlSb.toString();
	}
	
	public static String queryUser(String username){
		String condition = "select username,regioncode from t_user t where username='"+username+"'";
		return condition;
	}
	
	private static HashMap<String,Object> genParameterMap(String columns, String tableName, String condition){
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put(Constants.COLUMNS, columns);
		paraMap.put(Constants.TABLENAME, tableName);
		paraMap.put(Constants.CONDITION, condition);
		return paraMap;
	}
}
