package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCaRegister;
import cn.gtmap.landsale.common.model.TransUser;

/**
 * ca用户管理
 * @author cxm
 * @version v1.0, 2017/9/29
 */
public interface TransCaRegisterService {
    /**
     * 新增注册用户
     * @param transCaRegister
     * @param transUser
     * @param regionCode
     * @return
     */
    ResponseMessage<TransCaRegister> saveTransCaRegister(TransCaRegister transCaRegister, TransUser transUser, String regionCode);

}
