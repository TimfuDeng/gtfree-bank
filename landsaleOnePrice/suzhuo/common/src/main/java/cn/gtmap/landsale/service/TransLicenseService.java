package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransLicense;
import cn.gtmap.landsale.model.TransNotice;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/12.
 */
public interface TransLicenseService {
    Page<TransLicense> findTransLicenseList(String title, Pageable request);

    List<TransLicense> findTransLicenseListByNo(String no, String targetId);

    List<TransLicense> findTransLicenseListById(String targetId);
    public List<TransLicense> findTransLicenseListInTargetId(Collection<String> targetIds);

    TransLicense getTransLicenseByTargetIdSysUserId(String transTargetId,String sysUserId);

    TransLicense saveTransLicense(TransLicense transLicense);

}
