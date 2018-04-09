package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCaRegister;

import java.util.List;

/**
 * @author cxm
 * @version v1.0, 2017/9/29
 */
public interface TransCaRegisterService {

    /**
     * 获取CA用户分页服务
     * @param contactUser 联系人
     * @param request
     * @return
     */
    Page<TransCaRegister> findRegisterUser(String contactUser,Pageable request);

    /**
     * 保存
     * @param transCaRegister
     * @return
     */
    ResponseMessage<TransCaRegister> saveTransCaRegister(TransCaRegister transCaRegister);

    /**
     * 根据注册id获取CA用户
     * @param registerId 注册id
     * @return
     */
    List<TransCaRegister> getTransCaRegisterByRegisterId(String registerId);

    /**
     * 根据用户ID获取CA用户
     * @param userId 用户ID
     * @return
     */
    List<TransCaRegister> getTransCaRegisterByUserId(String userId);

//    List<TransCaRegister> getTransCaRegisterByUserId();

    /**
     * 根据用户ID和行政区代码获取CA用户
     * @param userId 用户ID
     * @param regionCode  行政区代码
     * @return
     */
    List<TransCaRegister> getCaRegisterByUserIdAndCode(String userId,String regionCode);

}
