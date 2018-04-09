package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.register.TransCaRegisterClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.TransCaRegisterService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCaRegister;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by M150237 on 2017-10-11.
 */
@Service
public class TransCaRegisterServiceImpl extends HibernateRepo<TransCaRegister, String> implements TransCaRegisterService{

    @Autowired
    TransCaRegisterClient transCaRegisterClient;

    @Autowired
    TransUserClient transUserClient;

    /**
     * 新增注册用户
     * @param transCaRegister
     * @param transUser
     * @param regionCode
     * @return
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransCaRegister> saveTransCaRegister(TransCaRegister transCaRegister, TransUser transUser, String regionCode) {
        TransUser chackUser = transUserClient.getTransUserByCAThumbprint(transUser.getCaThumbprint());
        // TODO 测试情况下使用 使用时删掉第一个判断
        if (StringUtils.isEmpty(transUser.getCaThumbprint()) || chackUser != null) {
            //通过CA信息查找注册表中是否已经存在这个用户
            // TODO 测试情况下使用 使用时删掉第一个判断
            if (!StringUtils.isEmpty(transUser.getCaThumbprint())) {
                List<TransCaRegister> transCaRegisterList = transCaRegisterClient.getCaRegisterByUserIdAndCode(chackUser.getUserId(), regionCode);
                if ((transCaRegisterList != null && transCaRegisterList.size() >= 1)){
                    //如果确定唯一的Ca用户，则提醒该用户已经存在
                    return new ResponseMessage(false, "CA数字证书信息已存在！", transCaRegisterList.get(0));
                } else {
                    transCaRegister.setRegisterId(null);
                    transCaRegister.setUserId(chackUser.getUserId());
                    transCaRegister.setRegisterTime(Calendar.getInstance().getTime());
                    transCaRegister.setRegisterUser(SecUtil.getLoginUserViewName());
                    return transCaRegisterClient.saveTransCaRegister(transCaRegister);
                }
            } else {
                transCaRegister.setRegisterId(null);
                transCaRegister.setUserId(chackUser.getUserId());
                transCaRegister.setRegisterTime(Calendar.getInstance().getTime());
                transCaRegister.setRegisterUser(SecUtil.getLoginUserViewName());
                return transCaRegisterClient.saveTransCaRegister(transCaRegister);
            }
        } else {
            return new ResponseMessage(false, "该CA用户未在系统中注册！");
        }
    }

}
