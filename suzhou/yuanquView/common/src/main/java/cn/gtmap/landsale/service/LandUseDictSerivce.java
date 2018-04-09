package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.LandUseDict;

import java.util.List;

/**
 * 土地用途
 * Created by liushaoshuai on 2017/8/24.
 */
public interface LandUseDictSerivce {

    /**
      *@annotation 获取土地用途字典表数据
      *@author liushaoshuai【liushaoshuai@gtmap.cn】
      *@date 2017/8/24 14:25
      *@param
      *@return
      */
    List<LandUseDict> getLandUseDictList();

    /**
      *@annotation 获取字典表数据对象
      *@author liushaoshuai【liushaoshuai@gtmap.cn】
      *@date 2017/8/24 14:26
      *@param
      *@return
      */
    LandUseDict getLandUseDict(String code);
}
