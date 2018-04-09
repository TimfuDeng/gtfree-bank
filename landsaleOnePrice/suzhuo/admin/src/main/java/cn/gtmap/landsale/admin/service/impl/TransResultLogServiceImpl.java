package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransResultLog;
import cn.gtmap.landsale.service.TransResultLogService;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by trr on 2016/8/14.
 */
public class TransResultLogServiceImpl extends HibernateRepo<TransResultLog,String> implements TransResultLogService {
    /**
     * 从原来系统里面获取报价记录
     *
     * @param transTargetId
     * @return
     */
    @Override
    @Transactional
    public List<TransResultLog> findTransOnePriceLogList(String transTargetId) {
        StringBuilder sql = new StringBuilder();

        sql.append("select PRICEUNIT,PRICE,PRICEDATE,TRANSUSERID from ( select t2.user_name priceUnit,t1.price price,t1.create_date priceDate,t2.id transUserId from trans_license t ")
                .append(" left join trans_offer_log t1 on t.id=t1.license_id ")
                .append(" left join sys_user t2 on t1.create_user_id=t2.id where 1=1 ");

        if (StringUtils.isNotBlank(transTargetId)) {
            sql.append(" and t.target_id='"+transTargetId+"'");
        }else {
            return null;
        }
        sql.append(" and t2.id is not null ")
                .append("order by t1.price desc )a  where  rownum<=20");
        return sql(sql.toString()).setResultTransformer(Transformers.aliasToBean(TransResultLog.class)).list();
       /* return (List<TransResultLog>) sql(sql.toString(), mapParam).setResultTransformer(Transformers.aliasToBean(TransResultLog.class));*/
    }

}
