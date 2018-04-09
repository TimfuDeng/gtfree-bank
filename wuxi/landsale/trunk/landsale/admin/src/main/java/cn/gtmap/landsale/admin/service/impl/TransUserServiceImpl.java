package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.register.TransJmrClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.TransUserService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.TransUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * 用户ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Service
public class TransUserServiceImpl extends HibernateRepo<TransUser, String> implements TransUserService {


    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransJmrClient transJmrClient;

    @Autowired
    TransRegionClient transRegionClient;

    /**
     * 保存用户
     * @param transUser 用户对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransUser> saveTransUser(TransUser transUser, String roleId) {
        // 通过用户名 查找用户
        TransUser transUserCheck = transUserClient.getTransUserByUserName(transUser.getUserName());
        if (transUserCheck != null) {
            return new ResponseMessage(false, "该用户名:" + transUser.getUserName() + "已存在!", transUser);
        }
        transUserCheck = transUserClient.getTransUserByCAThumbprint(transUser.getCaThumbprint());
        if (transUserCheck != null) {
            return new ResponseMessage(false, "CA数字证书信息已存在！", transUser);
        }
        // 通过角色查找 角色所属的行政区
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByRole(roleId);
        if (transRegionList != null && transRegionList.size() > 0) {
            transUser.setRegionCode(transRegionList.get(0).getRegionCode());
        }
        // 保存用户
        transUser.setUserId(null);
        transUser.setCreateAt(Calendar.getInstance().getTime());
        ResponseMessage<TransUser> responseMessage = transUserClient.saveTransUser(transUser, roleId);
//        // 保存用户角色
//        if (responseMessage.getFlag()) {
//            TransUserRole transUserRole = new TransUserRole();
//            transUserRole.setRoleId(roleId);
//            transUserRole.setUserId(responseMessage.getEmpty().getUserId());
//            transUserRoleService.saveTransUserRole(transUserRole);
//        }
        return new ResponseMessage(true, transUser);
    }

    /**
     * 修改用户
     * @param transUser 用户对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransUser> updateTransUser(TransUser transUser, String roleId) {
        // 根据 修改后用户名 查找是否存在相同UserName
        TransUser transUserCheck = transUserClient.getTransUserByUserName(transUser.getUserName());
        if (transUserCheck != null && !transUserCheck.getUserId().equals(transUser.getUserId())) {
            return new ResponseMessage(false, "用户名" + transUser.getUserName() + "已存在!", transUser);
        }
//        // 根据 用户查找原有的用户角色关系
//        List<TransUserRole> transUserRoleList = transUserRoleService.findTransUserRoleByUserId(transUser.getUserId());
//        if (transUserRoleList != null && transUserRoleList.size() > 0) {
//            transUserRoleService.deleteTransUserRole(transUserRoleList);
//        }
//        // 添加新关系
//        TransUserRole transUserRole = new TransUserRole();
//        transUserRole.setRoleId(roleId);
//        transUserRole.setUserId(transUser.getUserId());
//        transUserRoleService.saveTransUserRole(transUserRole);
//        // 通过角色查找 角色所属的行政区
//        List<TransRegion> transRegionList = transRegionClient.findTransRegionByRole(roleId);
//        if (transRegionList != null && transRegionList.size() > 0) {
//            transUser.setRegionCode(transRegionList.get(0).getRegionCode());
//        }
        // 修改用户
        transUser.setCreateAt(Calendar.getInstance().getTime());
        return transUserClient.saveTransUser(transUser, roleId);
    }


    /**
     * 删除用户
     * @param userIds 用户Ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransUser(String userIds) {
        return transUserClient.deleteTransUser(userIds);
    }


//    /**
//     * 保存用户
//     * @param transUser 用户对象
//     * @return
//     */
//    @Override
//    @Transactional
//    public ResponseMessage<TransUser> saveTransUser(TransUser transUser) {
//        // 通过用户名 查找用户
//        TransUser transUserCheck = transUserClient.getTransUserByUserName(transUser.getUserName());
//        if (transUserCheck != null) {
//            return new ResponseMessage(false, "该用户名:" + transUser.getUserName() + "已存在!", transUser);
//        }
//        transUserCheck = transUserClient.getTransUserByCAThumbprint(transUser.getCaThumbprint());
//        if (transUserCheck != null) {
//            return new ResponseMessage(false, "CA数字证书信息已存在！", transUser);
//        }
//        // 保存用户
//        transUser.setUserId(null);
//        transUser.setCreateAt(Calendar.getInstance().getTime());
//        ResponseMessage<TransUser> responseMessage = transJmrClient.addJmr(transUser);
//        return new ResponseMessage(true, transUser);
//    }

}
