package cn.gtmap.landsale.admin.service;

import org.springframework.ui.Model;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Arrays;
import java.util.List;

/**
 * Created by trr on 2015/11/16.
 */
public interface landService {
    /**
     * 根据地块号从房地一张图系统得到地块信息
     * @param resourceCode
     * @return
     * @throws Exception
     */
    public String getLand( String resourceCode) throws Exception;

    /**
     * 根据地块号从出让系统将出任地块信息提供接口到房地一张图
     * @param resourceCode
     * @return
     */
    public List crggMapList(String resourceCode);

    /**
     * 根据地块编号获得成交公告信息提供接口到房地一张图
     * @return
     */
    public List crggOfferMapList(String resourceCode);
}
