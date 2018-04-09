package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.landService;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.ResourceUtil;
import com.google.common.collect.Lists;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by trr on 2015/11/16.
 */
public class landServiceImpl implements landService{


    private HttpClient httpClient;
    private String baseUrl;
    private String resourceUrl;
    private String dBTag;
    private String xKey;
    private String zDH;
    private TransResourceService transResourceService;
    private TransResourceInfoService transResourceInfoService;
    private TransResourceApplyService transResourceApplyService;
    private TransResourceOfferService transResourceOfferService;
    private TransUserService transUserService;
    private TransUserApplyInfoService transUserApplyInfoService;
    private TransResourceSonService transResourceSonService;

    public TransResourceSonService getTransResourceSonService() {
        return transResourceSonService;
    }

    public void setTransResourceSonService(TransResourceSonService transResourceSonService) {
        this.transResourceSonService = transResourceSonService;
    }

    public TransUserService getTransUserService() {
        return transUserService;
    }

    public void setTransUserService(TransUserService transUserService) {
        this.transUserService = transUserService;
    }

    public TransUserApplyInfoService getTransUserApplyInfoService() {
        return transUserApplyInfoService;
    }

    public void setTransUserApplyInfoService(TransUserApplyInfoService transUserApplyInfoService) {
        this.transUserApplyInfoService = transUserApplyInfoService;
    }

    public TransResourceOfferService getTransResourceOfferService() {
        return transResourceOfferService;
    }

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public TransResourceApplyService getTransResourceApplyService() {
        return transResourceApplyService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public TransResourceInfoService getTransResourceInfoService() {
        return transResourceInfoService;
    }

    public void setTransResourceInfoService(TransResourceInfoService transResourceInfoService) {
        this.transResourceInfoService = transResourceInfoService;
    }

    public TransResourceService getTransResourceService() {
        return transResourceService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getdBTag() {
        return dBTag;
    }

    public void setdBTag(String dBTag) {
        this.dBTag = dBTag;
    }

    public String getxKey() {
        return xKey;
    }

    public void setxKey(String xKey) {
        this.xKey = xKey;
    }



    @Override
    @Transactional
    public String getLand(String resourceCode) throws Exception{

        String result =  getHttpClient(resourceCode);

        return result;
    }

    /**
     * 根据地块号从出让系统将出任地块信息导入到房地一张图
     *
     * @param resourceCode
     * @return
     */
    @Override
    @Transactional
    public List crggMapList(String resourceCode) {
        List crggList=new ArrayList();
        List<String> codeArr = Lists.newArrayList(resourceCode.split(";"));
        List<TransResource> resourceList=new ArrayList<TransResource>();
        for(String code:codeArr){
            TransResource transResource = transResourceService.getResourceByCode(code);
            resourceList.add(transResource);
        }
        for(TransResource transResource:resourceList){
            TransResourceInfo transResourceInfo=transResourceInfoService.getTransResourceInfoByResourceId(transResource.getResourceId());

            Map<String,Object> crggMap=new HashMap<String, Object>();
            List crggSonList=new ArrayList();
            //crggMap.put("dkCode", ResourceUtil.obj2Str(transResource.getDkCode()) );
            crggMap.put("resourceCode",ResourceUtil.obj2Str(transResource.getResourceCode()));
            crggMap.put("resourceLocation",ResourceUtil.obj2Str(transResource.getResourceLocation()));
            crggMap.put("landUse", ResourceUtil.obj2Str(transResource.getLandUse()));
            crggMap.put("fixedOffer",ResourceUtil.obj2Str(transResource.getFixedOffer()));
            crggMap.put("fixedOfferUsd",ResourceUtil.obj2Str(transResource.getFixedOfferUsd()));
            crggMap.put("fixedOfferHkd",ResourceUtil.obj2Str(transResource.getFixedOfferHkd()));
            crggMap.put("beginOffer",ResourceUtil.obj2Str(transResource.getBeginOffer()));
            crggMap.put("addOffer",ResourceUtil.obj2Str(transResource.getAddOffer()));
            crggMap.put("crArea",ResourceUtil.obj2Str(transResource.getCrArea()));
            crggMap.put("minOffer",ResourceUtil.obj2Str(transResource.isMinOffer()));
            crggMap.put("gpBeginTime",ResourceUtil.obj2Str(transResource.getGpBeginTime()));
            crggMap.put("gpEndTime",ResourceUtil.obj2Str(transResource.getGpEndTime()));
            crggMap.put("bmBeginTime",ResourceUtil.obj2Str(transResource.getBmBeginTime()));
            crggMap.put("bmEndTime",ResourceUtil.obj2Str(transResource.getBmEndTime()));
            crggMap.put("bzjBeginTime",ResourceUtil.obj2Str(transResource.getBzjBeginTime()));
            crggMap.put("bzjEndTime",ResourceUtil.obj2Str(transResource.getBzjEndTime()));
            crggMap.put("shconArea",ResourceUtil.obj2Str(transResourceInfo.getShconArea()));
            crggMap.put("zzconArea",ResourceUtil.obj2Str(transResourceInfo.getZzconArea()));
            crggMap.put("bgconArea",ResourceUtil.obj2Str(transResourceInfo.getBgconArea()));
            crggMap.put("yearCount",ResourceUtil.obj2Str(transResourceInfo.getYearCount()));
            crggMap.put("tdTjXx",ResourceUtil.obj2Str(transResourceInfo.getTdTjXx()));
            crggMap.put("cdPz",ResourceUtil.obj2Str(transResourceInfo.getCdPz()));
            crggMap.put("jcSs",ResourceUtil.obj2Str(transResourceInfo.getJcSs()));
            crggMap.put("selrjl",ResourceUtil.obj2Str(transResourceInfo.getSelrjl()));
            crggMap.put("selrj2",ResourceUtil.obj2Str(transResourceInfo.getSelrj2()));
            crggMap.put("minRjl",ResourceUtil.obj2Str(transResourceInfo.getMinRjl()));
            crggMap.put("maxRjl",ResourceUtil.obj2Str(transResourceInfo.getMaxRjl()));
            crggMap.put("minJzMdTag",ResourceUtil.obj2Str(transResourceInfo.getMinJzMdTag()));
            crggMap.put("maxJzMdTag",ResourceUtil.obj2Str(transResourceInfo.getMaxJzMd()));
            crggMap.put("minJzMd",ResourceUtil.obj2Str(transResourceInfo.getMinJzMd()));
            crggMap.put("maxJzMd",ResourceUtil.obj2Str(transResourceInfo.getMaxJzMd()));
            crggMap.put("minLhlTag",ResourceUtil.obj2Str(transResourceInfo.getMinLhlTag()));
            crggMap.put("maxLhlTag",ResourceUtil.obj2Str(transResourceInfo.getMaxLhlTag()));
            crggMap.put("minLhl",ResourceUtil.obj2Str(transResourceInfo.getMinLhl()));
            crggMap.put("maxLhl",ResourceUtil.obj2Str(transResourceInfo.getMaxLhl()));
            crggMap.put("minJzXgTag",ResourceUtil.obj2Str(transResourceInfo.getMinJzXgTag()));
            crggMap.put("maxJzXgTag",ResourceUtil.obj2Str(transResourceInfo.getMaxJzXgTag()));
            crggMap.put("minJzxg",ResourceUtil.obj2Str(transResourceInfo.getMinJzxg()));
            crggMap.put("maxJzxg",ResourceUtil.obj2Str(transResourceInfo.getMaxJzxg()));
            crggMap.put("officeRatio",ResourceUtil.obj2Str(transResourceInfo.getOfficeRatio()));
            crggMap.put("investmentIntensity",ResourceUtil.obj2Str(transResourceInfo.getInvestmentIntensity()));
            crggMap.put("constructContent",ResourceUtil.obj2Str(transResourceInfo.getConstructContent()));
            crggMap.put("deliverTerm",ResourceUtil.obj2Str(transResourceInfo.getDeliverTerm()));
            crggMap.put("layoutOutline",ResourceUtil.obj2Str(transResourceInfo.getLayoutOutline()));
            crggMap.put("description",ResourceUtil.obj2Str(transResourceInfo.getDescription()));
            crggMap.put("surroundings",ResourceUtil.obj2Str(transResourceInfo.getSurroundings()));
            crggMap.put("memo",ResourceUtil.obj2Str(transResourceInfo.getMemo()));
            List<TransResourceSon> resourceSonList = transResourceSonService.getTransResourceSonList(transResource.getResourceId());
            for(TransResourceSon transResourceSon:resourceSonList){
                Map<String,Object> crggSonMap=new HashMap<String, Object>();
                crggSonMap.put("sonLandUse",transResourceSon.getSonLandUse());
                crggSonMap.put("sonYearCount",transResourceSon.getSonYearCount());
                crggSonMap.put("zdCode",transResourceSon.getZdCode());
                crggSonMap.put("zdArea",transResourceSon.getZdArea());
                crggSonList.add(crggSonMap);
            }
            crggMap.put("resourceSon",crggSonList);
            crggList.add(crggMap);
        }
        return crggList;
    }

    /**
     * 根据地块编号获得成交公告信息提供接口到房地一张图
     *
     * @return
     */
    @Override
    @Transactional
    public List crggOfferMapList(String resourceCode) {
        List crggOfferList=new ArrayList();
        List<String> codeArr = Lists.newArrayList(resourceCode.split(";"));
        List<TransResource> resourceList=new ArrayList<TransResource>();
        for(String code:codeArr){
            TransResource transResource = transResourceService.getResourceByCode(code);
            resourceList.add(transResource);
        }
        for(TransResource transResource:resourceList){

            TransResourceOffer transResourceOffer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
            if(null!=transResourceOffer){
                Map<String,Object> crggOfferMap=new HashMap<String, Object>();
                TransUser transUser= transUserService.getTransUser(transResourceOffer.getUserId());
                //crggOfferMap.put("dkCode", ResourceUtil.obj2Str(transResource.getDkCode()) );
                crggOfferMap.put("resourceCode",ResourceUtil.obj2Str(transResource.getResourceCode()));
                crggOfferMap.put("QLRMC",ResourceUtil.obj2Str(transUser.getViewName()));
                crggOfferMap.put("QLRZJLX",ResourceUtil.obj2Str(null));
                crggOfferMap.put("QLRZJH",ResourceUtil.obj2Str(null));
                crggOfferMap.put("QLRDWLX",ResourceUtil.obj2Str(null));
                crggOfferMap.put("QLRSSHY",ResourceUtil.obj2Str(null));
                crggOfferMap.put("QLRDZ",ResourceUtil.obj2Str(null));
                crggOfferMap.put("QLRYB",ResourceUtil.obj2Str(null));
                crggOfferMap.put("QLRDH",ResourceUtil.obj2Str(transUser.getMobile()));
                crggOfferMap.put("QLRCZ",ResourceUtil.obj2Str(null));
                crggOfferMap.put("FRMC",ResourceUtil.obj2Str(transUser.getViewName()));
                crggOfferMap.put("BGQLRMC","");
                crggOfferMap.put("BGQLRZJLX","");
                crggOfferMap.put("BGQLRZJH","");
                crggOfferMap.put("BGQLRDWLX","");
                crggOfferMap.put("BGQLRSSHY","");
                crggOfferMap.put("BGQLRDZ","");
                crggOfferMap.put("BGQLRYB","");
                crggOfferMap.put("BGQLRDH","");
                crggOfferMap.put("BGQLRCZ","");
                crggOfferMap.put("BGFRMC","");
                crggOfferMap.put("QdAddress","网上交易");
                crggOfferMap.put("TotalPrice",ResourceUtil.obj2Str(transResourceOffer.getOfferPrice()));
                crggOfferMap.put("PzDate",ResourceUtil.obj2Str(transResourceOffer.getOfferTime()));
                crggOfferMap.put("GzPeople","");
                crggOfferMap.put("Notes",ResourceUtil.obj2Str(transUser.getDescription()));
                crggOfferList.add(crggOfferMap);
            }

        }
        return crggOfferList;
    }




    String  getHttpClient(String resourceCode) throws  Exception{
        CloseableHttpClient closeableHttpClient=null;
        CloseableHttpResponse response=null;
        try {
            closeableHttpClient=(CloseableHttpClient)httpClient;
            HttpGet httpGet=new HttpGet(baseUrl+resourceUrl+"?DBTag="+dBTag+"&XKey="+xKey+"&ZDH="+resourceCode);
            response=closeableHttpClient.execute(httpGet);
            HttpEntity entity= response.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");
            if(response.getStatusLine().getStatusCode()==200){
                return result;
            }else{
                return "";
            }


        } finally {
            if(null!=response){
                response.close();
            }

        }



    }


}
