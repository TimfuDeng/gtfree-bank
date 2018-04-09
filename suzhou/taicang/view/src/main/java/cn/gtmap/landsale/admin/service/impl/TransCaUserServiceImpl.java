package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.service.TransCaUserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 王建明
 * @创建日期 2015-10-27
 * @创建时间 8:30
 * @描述 —— ca验证用户操作类
 */
public class TransCaUserServiceImpl extends HibernateRepo<TransCaUser, String> implements TransCaUserService {
	/**
	 * 获取用户分页服务
	 *
	 * @param userName
	 * @param request
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<TransCaUser> findTransCaUser(String userName, Integer userType, Collection<String> regionCodes, Pageable request) {
		List<Criterion> criterionList = Lists.newArrayList();
		if (StringUtils.isNotBlank(userName)){
			criterionList.add(Restrictions.or(Restrictions.like("caName", userName, MatchMode.ANYWHERE),Restrictions.like("userName", userName, MatchMode.ANYWHERE)));
		}
		if (regionCodes != null && !regionCodes.isEmpty())
			criterionList.add(Restrictions.in("regionCode", regionCodes));
		return find(criteria(criterionList), request);
	}

	/**
	 * 根据用户Id获取用户对象
	 *
	 * @param userId 用户id
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public TransCaUser getTransCaUser(String userId) throws EntityNotFoundException {
		return get(userId);
	}

	/**
	 * 根据用户登录名获取用户对象
	 *
	 * @param userName
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public TransCaUser getTransCaUserByUserName(String userName) {
		return StringUtils.isNotBlank(userName) ? get(criteria(Restrictions.eq("userName", userName))) : null;
	}


	private TransCaUser getTransCaUserByCaName(String caName) {
		Criteria criteria = criteria(Restrictions.eq("caName", caName));
		criteria.addOrder(Order.desc("createAt"));
		List<TransCaUser> transCaUsers = list(criteria);
		if (transCaUsers != null && transCaUsers.size() > 0){
			for (int i = 1; i < transCaUsers.size(); i++) {
				delete(transCaUsers.get(i));
			}
			return transCaUsers.get(0);
		}
		return null;
	}

	/**
	 * @作者 王建明
	 * @创建日期 2017/7/3 0003
	 * @创建时间 上午 9:02
	 * @描述 —— 优先根据指纹取出用户信息，再根据用户名取出CA用户信息
	 */
	@Override
	@Transactional
	public TransCaUser getTransCaUserByKeyInfo(String userName, String thumbprint){
		TransCaUser transCaUser;
		if (StringUtils.isNotBlank(thumbprint)) {
			transCaUser = getTransCaUserByCAThumbprint(thumbprint);
			if (transCaUser != null)
				return transCaUser;
		}

		if (StringUtils.isNotBlank(userName)) {
			transCaUser = getTransCaUserByCaName(userName);
			if (transCaUser != null)
				return transCaUser;

			String suffixName = getSuffixCode(userName);
			if (StringUtils.isNotBlank(suffixName)) {
				Criteria criteria = criteria(Restrictions.like("caName", "%" + suffixName));
				criteria.addOrder(Order.desc("createAt"));
				List<TransCaUser> transCaUsers = list(criteria);
				if (transCaUsers != null && transCaUsers.size() > 0){
					for (int i = 1; i < transCaUsers.size(); i++) {
						delete(transCaUsers.get(i));
					}
					return transCaUsers.get(0);
				}
			}
		}
		return null;
	}

	/**
	 * @作者 王建明
	 * @创建日期 2017/6/5 0005
	 * @创建时间 下午 3:13
	 * @描述 —— 匹配用户显示姓名尾部代码
	 */
	private String getSuffixCode(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		String regEx = "[A-Za-z0-9]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(userName);
		while (mat.find()) {
			String data = mat.group(0);
			return data;
		}
		return null;
	}

	/**
	 * 根据用户的CA指纹获取用户
	 *
	 * @param thumbprint
	 * @return
	 */
	@Override
	@Transactional
	public TransCaUser getTransCaUserByCAThumbprint(String thumbprint) {
		Criteria criteria = criteria(Restrictions.eq("caThumbprint", thumbprint));
		criteria.addOrder(Order.desc("createAt"));

		List<TransCaUser> transCaUsers = list(criteria);
		if (transCaUsers != null && transCaUsers.size() > 0){
			for (int i = 1; i < transCaUsers.size(); i++) {
				delete(transCaUsers.get(i));
			}
			return transCaUsers.get(0);
		}

		return null;
	}

	/**
	 * 删除用户
	 *
	 * @param userIds 用户Ids
	 */
	@Override
	@Transactional
	public void deleteTransCaUser(Collection<String> userIds) {
		deleteByIds(userIds);
	}

	/**
	 * 保存用户
	 *
	 * @param transCaUser 用户对象
	 * @return
	 */
	@Override
	@Transactional
	public TransCaUser saveTransCaUser(TransCaUser transCaUser) {
		if (transCaUser.getCreateAt() == null)
			transCaUser.setCreateAt(Calendar.getInstance().getTime());
		if (StringUtils.isBlank(transCaUser.getUserId()))
			return save(transCaUser);
		else
			return merge(transCaUser);
	}
}