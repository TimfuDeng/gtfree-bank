package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.SysCakey;


public interface SysCakeyService {

    SysCakey getSysCakey(String cakey);

    SysCakey getSysCakeyByUserId(String userId);

}
