package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransOfferLog;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/19.
 */
public interface TransOfferLogService {
    List<TransOfferLog> getOfferLogListByLicenseId(Collection<String> licenseIds);
}
