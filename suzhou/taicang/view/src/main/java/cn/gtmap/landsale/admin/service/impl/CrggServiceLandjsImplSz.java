package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageImpl;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.CrggService;
import cn.gtmap.landsale.admin.service.CrggServiceSz;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceInfo;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.NumberUtils;
import cn.gtmap.landsale.util.ThreadPoolManager;
import com.esri.core.geometry.Operator;
import com.esri.core.geometry.OperatorExportToGeoJson;
import com.esri.core.geometry.OperatorFactoryLocal;
import com.esri.core.geometry.Polygon;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.*;

/**
 * 江苏土地市场网出让公告导入服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public class CrggServiceLandjsImplSz implements CrggServiceSz {
    private HttpClient httpClient;
    private String baseUrl;
    private String crggUrl;
    private String attachmentUrl;
    private String crggContentUrl;
    private String userName;
    private String passWord;
    private TransFileService transFileService;
    private TransCrggService transCrggService;
    private TransResourceService transResourceService;
    private OperatorFactoryLocal operatorFactoryLocal = OperatorFactoryLocal.getInstance();
    private TransResourceInfoService transResourceInfoService;
    private TransGpsOffsetService transGpsOffsetService;
    private AttachmentCategoryService attachmentCategoryService;
    private CrggService crggService;

    public void setCrggService(CrggService crggService) {
        this.crggService = crggService;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setCrggUrl(String crggUrl) {
        this.crggUrl = crggUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public void setCrggContentUrl(String crggContentUrl) {
        this.crggContentUrl = crggContentUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public void setTransFileService(TransFileService transFileService) {
        this.transFileService = transFileService;
    }


    public void setTransCrggService(TransCrggService transCrggService) {
        this.transCrggService = transCrggService;
    }

    public TransResourceService getTransResourceService() {
        return transResourceService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransResourceInfoService(TransResourceInfoService transResourceInfoService) {
        this.transResourceInfoService = transResourceInfoService;
    }

    public void setTransGpsOffsetService(TransGpsOffsetService transGpsOffsetService) {
        this.transGpsOffsetService = transGpsOffsetService;
    }

    public void setAttachmentCategoryService(AttachmentCategoryService attachmentCategoryService) {
        this.attachmentCategoryService = attachmentCategoryService;
    }

    /**
     * 分页形式获取出让公告
     *
     * @param where   条件
     * @param request 分页请求对象
     * @return 出让公告列表
     */
    @Override
    ////@Cacheable(value="CrggServiceLandjsCache",key="'Where'+#where+'Page'+#request.index")
    public Page<TransCrgg> findTransCrgg(String where, Pageable request) throws Exception {
        Document doc = crggService.postHttpClient(generateRequestXml(where, request.getSize(), request.getIndex()));
        Integer totalRecords = parseRecordCount(doc);
        List<TransCrgg> result = parseTransCrgg(doc,false,false,false);
        return new PageImpl(result,totalRecords,request);
    }

    /**
     * 解析返回记录数量
     * @param document
     * @return
     */
    private Integer parseRecordCount(Document document){
        Integer count = 0;
        if(StringUtils.isNotBlank(document.selectSingleNode("//TotalRecords").getText()))
            count=Integer.parseInt(document.selectSingleNode("//TotalRecords").getText());
        return count;
    }

    //@Cacheable(value="CrggServiceLandjsCache",key="'getTransCrgg_Where'+#where+'count'+#count")
    public List<TransCrgg> getTransCrgg(String where,int count) throws Exception {
        Document doc = crggService.postHttpClient(generateRequestXml(where, count, null));
        List<TransCrgg> result = parseTransCrgg(doc, true, true, true);
        return result;
    }

    /**
     * 生成http请求头
     * @param where 条件
     * @param pageSize 分页大小
     * @param currentPage 当前页数
     * @return
     */
    private String generateRequestXml(String where,Integer pageSize,Integer currentPage){
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("LandjsRequest");
        Element element = root.addElement("Action");
        element.addText("31");
        element = root.addElement("BusinessType");
        element.addText("12");
        if(pageSize!=null){
            element = root.addElement("PageSize");
            element.addText(pageSize.toString());
        }
        if(StringUtils.isNotBlank(where)){
            element = root.addElement("BusinessWhere");
            element.addText(where);
        }
        if(currentPage!=null){
            element = root.addElement("CurrentPage");
            element.addText(currentPage.toString());
        }
        root.addElement("Data");

        return document.asXML();
    }


    /**
     * 获取出让公告对象
     *
     * @param ggId 出让公告Id
     * @return 出让公告对象
     */
    @Override
    //@Cacheable(value="CrggServiceLandjsCache",key="'ggId'+#ggId")
    public TransCrgg findTransCrgg(String ggId) throws Exception {
        String where = "GYGG_GUID = '"+ggId+"'";
        Document doc = crggService.postHttpClient(generateRequestXml(where, null, null));
        List<TransCrgg> transCrggList = parseTransCrgg(doc, false, false, false);
        return (transCrggList!=null&&transCrggList.size()>0)?transCrggList.get(0):null;
    }

    /**
     * 根据出让公告Id下载导入
     *
     * @param gyggIds
     */
    @Override
    @Transactional
    public void getTransCrgg(Collection<String> gyggIds) throws Exception {
        if(gyggIds==null||gyggIds.size()==0) return;

        //去除已导入存在的
        Iterator<String> iterator = gyggIds.iterator();
        while (iterator.hasNext()){
            String gyggId = iterator.next();
            if(transCrggService.getTransCrggByGyggId(gyggId)!=null)
                iterator.remove();
        }
        if(gyggIds.size()>0){
            StringBuilder where = new StringBuilder("GYGG_GUID in (");
            iterator = gyggIds.iterator();
            while (iterator.hasNext()){
                where.append("'").append(iterator.next()).append("'");
                if(iterator.hasNext())
                    where.append(",");
            }
            where.append(")");
            List<TransCrgg> transCrggList = getTransCrgg(where.toString(), gyggIds.size());
            for(TransCrgg transCrgg:transCrggList){
                transCrgg = transCrggService.saveTransCrgg(transCrgg);
                /*List<TransFile> transFileList = transCrgg.getAttachmentList();
                for(TransFile transFile:transFileList){
                    transFile.setFileKey(transCrgg.getGgId());
                    if(StringUtils.isNotBlank(transFile.getUrl()))
                        transFileService.save(transFile,transFile.getUrl());
                    else
                        transFileService.saveTransFile(transFile);
                }*/
                List<TransResource> transResourceList = transCrgg.getResourceList();
                for(TransResource transResource:transResourceList){
                    transResource.setGgId(transCrgg.getGgId());
                    transResourceService.saveTransResource(transResource);
                    /*for(TransFile transFile:transResource.getAttachmentList()){
                        transFile.setFileKey(transResource.getResourceId());
                        if(StringUtils.isNotBlank(transFile.getUrl()))
                            transFileService.save(transFile,transFile.getUrl());
                        else
                            transFileService.saveTransFile(transFile);
                    }*/

                    for(TransResourceInfo transResourceInfo:transResource.getTransResourceInfoList()){
                        transResourceInfo.setResourceId(transResource.getResourceId());
                        transResourceInfoService.saveTransResourceInfo(transResourceInfo);
                    }
                }
            }
            // 创建一个有个5工作线程的线程池
            ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
            threadPoolManager.addTask(createTask(transCrggList));
        }

    }

    /**
     * @作者 王建明
     * @创建日期 2016/3/1
     * @创建时间 11:19
     * @描述 —— 线程执行附件下载任务
     */
    public void runTransFileDownLoadTask(List<TransCrgg> transCrggList) throws Exception{
        for(TransCrgg transCrgg:transCrggList){
            List<TransFile> transFileList = transCrgg.getAttachmentList();
            for(TransFile transFile:transFileList){
                transFile.setFileKey(transCrgg.getGgId());
                if(StringUtils.isNotBlank(transFile.getUrl()))
                    transFileService.save(transFile,transFile.getUrl());
                else
                    transFileService.saveTransFile(transFile);
            }
            List<TransResource> transResourceList = transCrgg.getResourceList();
            for(TransResource transResource:transResourceList){
                transResource.setGgId(transCrgg.getGgId());
                for(TransFile transFile:transResource.getAttachmentList()){
                    transFile.setFileKey(transResource.getResourceId());
                    if(StringUtils.isNotBlank(transFile.getUrl()))
                        transFileService.save(transFile,transFile.getUrl());
                    else
                        transFileService.saveTransFile(transFile);
                }
            }
        }
    }

    private Runnable createTask(final List<TransCrgg> transCrggList) {
        return new Runnable() {
            public void run() {
                try {
                    runTransFileDownLoadTask(transCrggList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Document postHttpClient(String requestXml) throws Exception {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            HttpPost httpost = new HttpPost(baseUrl+crggUrl);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", userName));
            params.add(new BasicNameValuePair("passWord", passWord));
            params.add(new BasicNameValuePair("requestXML", requestXml));
            httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = closeableHttpClient.execute(httpost);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode()==200) {
                Document doc=DocumentHelper.parseText(EntityUtils.toString(entity, "UTF-8"));
                return DocumentHelper.parseText(doc.getRootElement().getText());
            }else{
                throw new AppException(8101);
            }
        }finally {
            if(response!=null)
                response.close();
        }
    }

    /**
     * 获取土地市场网的出让公告的html内容，并处理和转义
     * @param gyggGuid
     * @return
     * @throws Exception
     */
    private String getCrggHtmlContent(String gyggGuid) throws Exception {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            StringBuilder requestUrl = new StringBuilder();
            requestUrl.append(baseUrl).append(crggContentUrl).append("?GYGG_GUID=").append(gyggGuid);
            HttpGet httpGet = new HttpGet(requestUrl.toString());
            response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode()==200) {
                return getCrggFormContent(EntityUtils.toString(entity, "UTF-8"));
            }else{
                throw new AppException(8101);
            }
        }finally {
            if(response!=null)
                response.close();
        }
    }

    private String getCrggFormContent(String htmlContent){
        org.jsoup.nodes.Document doc = Jsoup.parse(htmlContent);
        Elements bodyElements = doc.getElementsByTag("body");
        return bodyElements.html();
    }


    /**
     * 解析供应公告对象
     * @param document xml文档对象
     * @param parseResource 是否解析供应公告地块信息
     * @param parseAttachment 是否解析供应公告附件信息
     * @param parseCrggContent 是否获取出让公告内容
     * @return
     */
    private List<TransCrgg> parseTransCrgg(Document document,boolean parseResource,boolean parseAttachment,
                                           boolean parseCrggContent) throws Exception {
        List<TransCrgg> transCrggList = Lists.newArrayList();
        List<Element> dataElements=document.selectNodes("//Data");
        for(Element dataElement:dataElements){
            //出让公告对象
            TransCrgg crggObject=new TransCrgg();
            Element element=(Element) dataElement.selectSingleNode("PRI_TABLE_DATA[@tableName='T_REMISE_AFFICHE']/RECORD");
            String gyggGuid=element.valueOf("@GYGG_GUID");
            crggObject.setGyggId(gyggGuid);
            crggObject.setGgTitle(element.valueOf("@AFFICHE_NAME"));
            crggObject.setGgNum(element.valueOf("@AFFICHE_NO"));
            if(parseCrggContent)
                crggObject.setGgContent(getCrggHtmlContent(gyggGuid));
            crggObject.setRegionCode(element.valueOf("@XZQ_DM"));
            crggObject.setGgBeginTime(new Date());//DateUtils.parse(element.valueOf("@AFFICHE_DATE"))
            crggObject.setGgEndTime(DateUtils.parse(element.valueOf("@AFFICHE_END")));
            String type=element.valueOf("@REMISE_TYPE");
            if ("挂牌".equals(type)){
                crggObject.setGgType(Constants.GgTypeGp);
            }else if("拍卖".equals(type)){
                crggObject.setGgType(Constants.GgTypePm);
            }else if("招标".equals(type)){
                crggObject.setGgType(Constants.GgTypeZb);
            }
            //出让公告地块列表
            if(parseResource){
                List<TransResource> resourceList=  parseTransResource(dataElement);
                crggObject.setResourceList(resourceList);
            }

            //公告附件
            if(parseAttachment){
                List<TransFile> transFileList = parseTransResourceFile(gyggGuid,dataElement);
                crggObject.setAttachmentList(transFileList);
            }

            transCrggList.add(crggObject);
        }

        return  transCrggList;
    }

    /**
     * 解析供应公告地块信息
     * @param dataElement XML节点对象
     * @return
     */
    private List<TransResource> parseTransResource(Element dataElement) throws Exception {
        List<TransResource> transResourceList = Lists.newArrayList();
        List<Element> resourceElements = dataElement.selectNodes("SUB_TABLE_DATA[@tableName='T_AFFICHE_PARCEL']/RECORD");
        for(Element resourceElement:resourceElements){
            String ggdkGuid = resourceElement.valueOf("@GGDK_GUID");
            TransResource transResource=new TransResource();
            transResource.setRegionCode(resourceElement.valueOf("@XZQ_DM"));
            transResource.setResourceCode(resourceElement.valueOf("@PARCEL_NO"));
            transResource.setResourceLocation(resourceElement.valueOf("@LAND_POSITION"));
            transResource.setResourceType(Constants.ResourceTypeLand);
            transResource.setResourceStatus(Constants.ResourceStatusGongGao);

            transResource.setBeginOffer(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@START_PRICE"), "0.0"));
            transResource.setCrArea(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@REMISE_AREA"), "0.0"));
            transResource.setFixedOffer(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@BAIL"), "0.0"));
            transResource.setAddOffer(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@BID_SCOPE"), "0.0"));
            transResource.setMaxOffer(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@ZGXJ"), "0.0"));
            if (StringUtils.isNotBlank(resourceElement.valueOf("@POST_DATE")))
                transResource.setShowTime(DateUtils.parse(resourceElement.valueOf("@POST_DATE")));
            else
                transResource.setShowTime(DateUtils.parse(resourceElement.valueOf("@AFFICHE_DATE")));

            transResource.setGpBeginTime(DateUtils.parse(resourceElement.valueOf("@BID_STARTTIME")));
            transResource.setGpEndTime(DateUtils.parse(resourceElement.valueOf("@BID_ENDTIME")));
            transResource.setBmBeginTime(DateUtils.parse(resourceElement.valueOf("@SIGN_STARTTIME")));
            transResource.setBmEndTime(DateUtils.parse(resourceElement.valueOf("@SIGN_ENDTIME")));
            transResource.setBzjBeginTime(DateUtils.parse(resourceElement.valueOf("@PAYMENT_STARTTIME")));
            transResource.setBzjEndTime(DateUtils.parse(resourceElement.valueOf("@PAYMENT_ENDTIME")));

            transResource.setBeginHouse(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@BZXZFCSMJ"), "0.0"));
            transResource.setAddHouse(NumberUtils.createDoubleIfBlank(resourceElement.valueOf("@BZXZFJJMJFD"), "0.0"));

            int offerUnit = StringUtils.isNotBlank(resourceElement.valueOf("@CJDW"))?NumberUtils.createInteger(resourceElement.valueOf("@CJDW")):1;
            switch (offerUnit){
                case 1:
                    transResource.setOfferUnit(0);
                    break;
                case 2:
                    transResource.setOfferUnit(1);
                    break;
                case 4:
                    transResource.setOfferUnit(2);
                    break;
                default:
                    transResource.setOfferUnit(0);
                    break;
            }

            if(StringUtils.isNotBlank(resourceElement.valueOf("@LAND_USE")))
                transResource.setLandUse(toLandUse(resourceElement.valueOf("@LAND_USE")));
            String minOffer = resourceElement.valueOf("@SFYDJ");
            if(StringUtils.isNotBlank(minOffer)&&minOffer.equals("1"))
                transResource.setMinOffer(true);
            else
                 transResource.setMinOffer(false);

            transResource.setOwnershipUnit(resourceElement.valueOf("@REMISE_UNIT"));
            String bidRule = resourceElement.valueOf("@BID_RULES");
            if(StringUtils.isNotBlank(bidRule)&&bidRule.equals(2))
                transResource.setBidRule(Constants.BidRule.ZHTJYXZD);
            else
                transResource.setBidRule(Constants.BidRule.JGZD);

            String cjfs = resourceElement.valueOf("@CJFS");
            if(StringUtils.isNotBlank(cjfs)&&cjfs.equals("02"))
                transResource.setBidType(Constants.BidType.YUAN_M);
            else if(StringUtils.isNotBlank(cjfs)&&cjfs.equals("03"))
                transResource.setBidType(Constants.BidType.YUAN_JZ_M);
            else if(StringUtils.isNotBlank(cjfs)&&cjfs.equals("04"))
                transResource.setBidType(Constants.BidType.WANYUAN_MU);
            else
                transResource.setBidType(Constants.BidType.ZJ_WANYUAN);

            String offerType = resourceElement.valueOf("@REMISE_TYPE");
            if(StringUtils.isNotBlank(offerType)&&offerType.equals("招标"))
                transResource.setOfferType(Constants.OfferType.BIDDING);
            else if(StringUtils.isNotBlank(offerType)&&offerType.equals("拍卖"))
                transResource.setOfferType(Constants.OfferType.AUCTION);
            else if(StringUtils.isNotBlank(offerType)&&offerType.equals("协议"))
                transResource.setOfferType(Constants.OfferType.AGREEMENT);
            else
                transResource.setOfferType(Constants.OfferType.LISTING);

            transResource.setTransResourceInfoList(parseTransResourceInfo(resourceElement));

            String geometry = parseTransResourceCoords(ggdkGuid,dataElement);
            transResource.setGeometry(geometry);

            List<TransFile> transFileList = parseTransResourceFile(ggdkGuid, dataElement);
            transResource.setAttachmentList(transFileList);

            transResourceList.add(transResource);


        }
        return  transResourceList;
    }

    /**
     * 解析地块坐标
     * @param dataElement
     * @return
     */
    private String parseTransResourceCoords(String ggdkGuid,Element dataElement){
        List<Element> coordElements = dataElement.selectNodes("SUB_TABLE_DATA[@tableName='T_ZD_JZDZB']/RECORD[@GGDK_GUID='"+ggdkGuid+"']");
        if(coordElements==null||coordElements.size()<=0){
            return null;
        }
        String firstJzdh = coordElements.get(0).valueOf("@JZDH");
        Polygon polygon = new Polygon();
        Double firstZbX= NumberUtils.createDouble(coordElements.get(0).valueOf("@XZB"));
        Double firstZbY = NumberUtils.createDouble(coordElements.get(0).valueOf("@YZB"));
        polygon.startPath(firstZbX,firstZbY);
        boolean newRing = false;
        for(int i=1;i<coordElements.size();i++){
            Double zb_x = NumberUtils.createDouble(coordElements.get(i).valueOf("@XZB"));
            Double zb_y = NumberUtils.createDouble(coordElements.get(i).valueOf("@YZB"));
            String jzdh = coordElements.get(i).valueOf("@JZDH");
            if(newRing){
                polygon.startPath(zb_x,zb_y);
                newRing = false;
                firstZbX= zb_x;
                firstZbY =zb_y;
                firstJzdh = jzdh;
            }else{
                if((zb_x.compareTo(firstZbX)==0&&zb_y.compareTo(firstZbY)==0)&&firstJzdh.equals(jzdh))
                    newRing = true;
                else
                    polygon.lineTo(zb_x, zb_y);
            }

        }
        OperatorExportToGeoJson exporter = (OperatorExportToGeoJson) operatorFactoryLocal.getOperator(Operator.Type.ExportToGeoJson);
        return exporter.execute(polygon);

    }

    private List<TransResourceInfo> parseTransResourceInfo(Element resourceElement){
        TransResourceInfo transResourceInfo = new TransResourceInfo();
        transResourceInfo.setBuildingArea(resourceElement.valueOf("@CONSTRUCT_AREA"));
        transResourceInfo.setYearCount(resourceElement.valueOf("@USE_YEAR"));
        transResourceInfo.setOfficeRatio(resourceElement.valueOf("@SHAREPERCENT"));
        transResourceInfo.setInvestmentIntensity(resourceElement.valueOf("@LOWESTINVEST"));
        transResourceInfo.setConstructContent(resourceElement.valueOf("@BUILDMATTER"));

        String minPlotRatio=resourceElement.valueOf("@MIN_RJL");
        String maxPlotRatio=resourceElement.valueOf("@MAX_RJL");
        StringBuilder plotRatio = new StringBuilder();
        if(StringUtils.isNotBlank(minPlotRatio))
            plotRatio.append(minPlotRatio).append("≤");
        if(StringUtils.isNotBlank(minPlotRatio)||StringUtils.isNotBlank(maxPlotRatio))
            plotRatio.append("容积率");
        if(StringUtils.isNotBlank(maxPlotRatio))
            plotRatio.append("≤").append(maxPlotRatio);
        transResourceInfo.setPlotRatio(plotRatio.toString());

        String minGreeningRate=resourceElement.valueOf("@MIN_LHL");
        String maxGreeningRate=resourceElement.valueOf("@MAX_LHL");
        StringBuilder greeningRate = new StringBuilder();

        if(StringUtils.isNotBlank(minGreeningRate))
            greeningRate.append(minGreeningRate).append("≤");
        if(StringUtils.isNotBlank(minGreeningRate)||StringUtils.isNotBlank(maxGreeningRate))
            greeningRate.append("绿化率");
        if(StringUtils.isNotBlank(maxGreeningRate))
            greeningRate.append("≤").append(maxGreeningRate);

        transResourceInfo.setGreeningRate(greeningRate.toString());

        String minBuildingDensity=resourceElement.valueOf("@MIN_JZ_MD");
        String maxBuildingDensity=resourceElement.valueOf("@MAX_JZ_MD");
        StringBuilder buildingDensity = new StringBuilder();

        if(StringUtils.isNotBlank(minBuildingDensity))
            buildingDensity.append(minBuildingDensity).append("≤");
        if(StringUtils.isNotBlank(minBuildingDensity)||StringUtils.isNotBlank(maxBuildingDensity))
            buildingDensity.append("建筑密度");
        if(StringUtils.isNotBlank(maxBuildingDensity))
            buildingDensity.append("≤").append(maxBuildingDensity);

        transResourceInfo.setBuildingDensity(buildingDensity.toString());

        String minBuildingHeight=resourceElement.valueOf("@MIN_JZXG");
        String maxBuildingHeight=resourceElement.valueOf("@MAX_JZXG");
        StringBuilder buildingHeight = new StringBuilder();

        if(StringUtils.isNotBlank(minBuildingHeight))
            buildingHeight.append(minBuildingHeight).append("≤");
        if(StringUtils.isNotBlank(minBuildingHeight)||StringUtils.isNotBlank(maxBuildingHeight))
            buildingHeight.append("建筑限高");
        if(StringUtils.isNotBlank(maxBuildingHeight))
            buildingHeight.append("≤").append(maxBuildingHeight);

        transResourceInfo.setBuildingHeight(buildingHeight.toString());
        return Lists.newArrayList(transResourceInfo);
    }


    /**
     * 解析附件
     * @param dataElement
     * @return
     */
    private List<TransFile> parseTransResourceFile(String linkGuid,Element dataElement) throws Exception {
        List<TransFile> transFileList = Lists.newArrayList();
        List<Element> fileElements = dataElement.selectNodes("SUB_TABLE_DATA[@tableName='T_FJ']/RECORD[@LINK_GUID='"+linkGuid+"']");
        if(fileElements==null)
            return transFileList;

        for(Element fileElement:fileElements){
            TransFile transFile = new TransFile();
            transFile.setFileName(fileElement.valueOf("@FJ_FILENAME"));
            transFile.setDescription(fileElement.valueOf("@FJ_MS"));
            int type = NumberUtils.createInteger(fileElement.valueOf("@FJ_LX"));
            transFile.setFileType(getTransFileType(type));
            if(StringUtils.isNotBlank(dataElement.valueOf("@CREATE_DATE")))
                transFile.setCreateAt(DateUtils.parse(dataElement.valueOf("@CREATE_DATE")));
            else
                transFile.setCreateAt(Calendar.getInstance().getTime());
            transFile.setUrl(baseUrl+attachmentUrl+URLEncoder.encode(formatAttachmentPath(fileElement.valueOf("@FJ_LJ")),"UTF-8"));
            transFileList.add(transFile);
        }
        return transFileList;
    }

    private String getTransFileType(int type){
        String fileType = null;
        switch (type){
            case 141:
                fileType = "CRGG";
                break;
            case 142:
                fileType= "JMXZ";
                break;
            default:
                fileType = Constants.FileType.QT.getCode();
                break;
        }
        return fileType;
    }


    /**
     * 格式化获取到的附件的路径，替换附件路径中的反斜杠
     * @param path
     * @return
     */
    private String formatAttachmentPath(String path){
        if(StringUtils.isNotBlank(path))
            return path.replaceAll("\\\\","/");
        else
            return "";
    }

    private Constants.LandUse toLandUse(String landUser){
        if(landUser.equals("商服用地"))
            return Constants.LandUse.SFYD;
        else if(landUser.equals("工矿仓储用地"))
            return Constants.LandUse.GKCCYD;
        else if(landUser.equals("工业"))
        	return Constants.LandUse.GYYD;
        else if(landUser.equals("仓储"))
        	return Constants.LandUse.CCYD;
        else if(landUser.equals("住宅"))
            return Constants.LandUse.ZZYD;
        else if(landUser.equals("商住"))
            return Constants.LandUse.SZYD;
        else
            return Constants.LandUse.QT;
    }

}
