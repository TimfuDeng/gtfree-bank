package cn.gtmap.landsale.admin.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransNotification;

import java.util.Collection;


/**
 * Created by light on 2015/9/23.
 */
public interface NotificationService {

    /**
     * 保存通知
     * @param transNote 通知对象
     */
    public TransNotification saveNote(TransNotification transNote);

    /**
     * 删除通知
      * @param noteIds 通知ids
     */
    public void deleteNote(Collection<String> noteIds);

    /**
     * 分页形式查询通知
     * @param where 查询条件
     * @param request 分页请求对象
     * @return 通知列表
     * @throws Exception
     */
    public Page<TransNotification> findNote(String where , Pageable request);

    /**
     * 查询通知对象
     * @param noteId 通知id
     * @return 通知对象
     * @throws Exception
     */
    public TransNotification findNote(String noteId) throws Exception;

}
