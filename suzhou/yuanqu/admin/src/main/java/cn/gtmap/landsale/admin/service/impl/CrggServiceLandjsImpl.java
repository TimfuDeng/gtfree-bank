package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageImpl;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.freemarker.PriceUtil;
import cn.gtmap.landsale.admin.service.CrggService;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.DateUtil;
import cn.gtmap.landsale.util.NumberUtils;
import cn.gtmap.landsale.util.ResourceUtil;
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
import org.dom4j.*;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.*;

/**
 * 江苏土地市场网出让公告导入服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public class CrggServiceLandjsImpl implements CrggService {
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
    private DealNoticeService dealNoticeService;
    private TransResourceOfferService transResourceOfferService;
    private TransUserService transUserService;

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

    public DealNoticeService getDealNoticeService() {
        return dealNoticeService;
    }

    public void setDealNoticeService(DealNoticeService dealNoticeService) {
        this.dealNoticeService = dealNoticeService;
    }

    public TransResourceOfferService getTransResourceOfferService() {
        return transResourceOfferService;
    }

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public TransUserService getTransUserService() {
        return transUserService;
    }

    public void setTransUserService(TransUserService transUserService) {
        this.transUserService = transUserService;
    }

    /**
     * 分页形式获取出让公告
     *
     * @param where   条件
     * @param request 分页请求对象
     * @return 出让公告列表
     */
    @Override
   /* @Cacheable(value="CrggServiceLandjsCache")*/
    public Page<TransCrgg> findTransCrgg(String where, Pageable request) throws Exception {
        Document doc = postHttpClient(generateRequestXml(where, request.getSize(), request.getIndex()));
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

    /*@Cacheable(value="CrggServiceLandjsCache",key="'getTransCrgg_Where'+#where+'count'+#count")*/
    public List<TransCrgg> getTransCrgg(String where,int count) throws Exception {
        Document doc = postHttpClient(generateRequestXml(where, count, null));
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
    /*@Cacheable(value="CrggServiceLandjsCache",key="'ggId'+#ggId")*/
    public TransCrgg findTransCrgg(String ggId) throws Exception {
        String where = "GYGG_GUID = '"+ggId+"'";
        Document doc = postHttpClient(generateRequestXml(where, null, null));
        List<TransCrgg> transCrggList = parseTransCrgg(doc, false, false, false);
        return (transCrggList!=null&&transCrggList.size()>0)?transCrggList.get(0):null;
    }

    /**
     * 根据出让公告Id上传公告
     *
     * @param gyggIds
     * @throws Exception
     */
    @Override
    @Transactional
    public void reportTransCrgg(Collection<String> gyggIds) throws Exception {

        //一次性可以上传多个公告，
        // 其中公告（有而且只有一个），地块（可多个），地块附件（可多个），
        // 宗地坐标属性（可选），宗地地块表（可选），宗地地界址点（可选）
        List<TransCrgg> transCrggList=new ArrayList<TransCrgg>();

        for(String gyggId:gyggIds){
           TransCrgg transCrgg =  transCrggService.getTransCrgg(gyggId);
            List<TransResource> resourceList=transResourceService.findTransResource(gyggId);
            for(TransResource transResource:resourceList){
               TransResourceInfo transResourceInfo= transResourceInfoService.getTransResourceInfoByResourceId(transResource.getResourceId());
                transResource.setTransResourceInfo(transResourceInfo);
                List<TransFile> transFileList= transFileService.getTransFileByKey(transResource.getResourceId());
                transCrgg.setAttachmentList(transFileList);

            }
            transCrgg.setResourceList(resourceList);
            transCrggList.add(transCrgg);


        }
        String reportTransCrggXml = generateReportTransCrggXml(transCrggList);


    }

    /**
     * 根据成交公示id上传成交公示
     *
     * @param noticeIds
     * @throws Exception
     */
    @Override
    @Transactional
    public void reportDealNotice(Collection<String> noticeIds) throws Exception {
        //一个成交公示，对应一个地块，对应一个地块扩展，一个最高成交信息，一个出让公告
        List<DealNotice> dealNoticeList=new ArrayList<DealNotice>();
        for(String noticeId:noticeIds){
            DealNotice dealNotice = dealNoticeService.getNotice(noticeId);
            dealNoticeList.add(dealNotice);
        }
        String  reportDealNoticeXml=generateReportDealNoticeXml(dealNoticeList);

    }

    private String generateReportDealNoticeXml(List<DealNotice> dealNoticeList){
        Document document =  DocumentHelper.createDocument();
        Element root = document.addElement("LandjsRequest");
        Element action= root.addElement("Action");
        action.addText("100");
        for(DealNotice dealNotice:dealNoticeList){
            TransResource transResource =  transResourceService.getResourceByCode(dealNotice.getResourceCode());
            TransResourceInfo transResourceInfo= transResourceInfoService.getTransResourceInfoByResourceId(transResource.getResourceId());
            TransCrgg transCrgg= transCrggService.getTransCrgg(transResource.getGgId());
            TransResourceOffer transResourceOffer= transResourceOfferService.getMaxOffer(transResource.getResourceId());
            TransUser transUser= transUserService.getTransUser(transResourceOffer.getUserId());
            Element data = root.addElement("Data");
            data.addAttribute("BusinessType","14");
            Element notice = data.addElement("PRI_TABLE_DATA");
            notice.addAttribute("tableName","T_BARGAIN_PARCEL");
            Element noticeRecord = notice.addElement("RECORD");
            noticeRecord.addAttribute("CJGS_GUID",dealNotice.getNoticeId());
            noticeRecord.addAttribute("XZQ_DM",transResource.getRegionCode());
            noticeRecord.addAttribute("AFFICHE_NO",transCrgg.getGgNum());
            noticeRecord.addAttribute("PARCEL_NO",transResource.getResourceCode());
            noticeRecord.addAttribute("AFFICHE_DATE",DateUtil.Date2Str(transCrgg.getGgBeginTime()));
            noticeRecord.addAttribute("DEAL_TYPE",transResource.getDealType());
            noticeRecord.addAttribute("REMISE_TYPE",transCrgg.getRemiseType());
            noticeRecord.addAttribute("BARGAIN_DATE", DateUtil.Longdate2Str(transResourceOffer.getOfferTime()));
            noticeRecord.addAttribute("ALIENEE",transUser.getViewName());
            noticeRecord.addAttribute("VALUE_PRICE","");
            noticeRecord.addAttribute("START_PRICE",ResourceUtil.double2Str(transResource.getBeginOffer()));
            noticeRecord.addAttribute("PRICE",ResourceUtil.double2Str(transResourceOffer.getOfferPrice()));
            noticeRecord.addAttribute("PAY_MODE","");
            noticeRecord.addAttribute("PAYMENT","");
            noticeRecord.addAttribute("PAIED","");
            noticeRecord.addAttribute("OTHER_CHARGE","");
            noticeRecord.addAttribute("LAND_POSITION",transResource.getResourceLocation());
            noticeRecord.addAttribute("REMISE_AREA",ResourceUtil.double2Str(transResource.getCrArea()));
            noticeRecord.addAttribute("LAND_USE",transResource.getLandUse().getTitle());
            noticeRecord.addAttribute("CUBAGE_PERCENT",ResourceUtil.double2Str(transResourceInfo.getMaxRjl()));
            noticeRecord.addAttribute("TOTAL_AREA","");
            noticeRecord.addAttribute("CONSTRUCT_AREA",transResourceInfo.getBuildingArea());
            noticeRecord.addAttribute("SHCON_AREA",ResourceUtil.double2Str(transResourceInfo.getShconArea()));
            noticeRecord.addAttribute("ZZCON_AREA",ResourceUtil.double2Str(transResourceInfo.getZzconArea()));
            noticeRecord.addAttribute("BGCON_AREA",ResourceUtil.double2Str(transResourceInfo.getBgconArea()));
            noticeRecord.addAttribute("DELIVER_TERM",transResourceInfo.getDeliverTerm());
            noticeRecord.addAttribute("USE_YEAR",transResourceInfo.getYearCount());
            noticeRecord.addAttribute("X","");
            noticeRecord.addAttribute("Y","");
            noticeRecord.addAttribute("STATUS",dealNotice.getDeployStatus()+"");
            noticeRecord.addAttribute("MEMO","");
            noticeRecord.addAttribute("DAIZH_AREA",ResourceUtil.double2Str(transResourceInfo.getDaizhArea()));
            noticeRecord.addAttribute("SHAPE","");
            noticeRecord.addAttribute("POST_USER",dealNotice.getNoticeAuthor());
            noticeRecord.addAttribute("REMISING_PARCEL_ID","");
            noticeRecord.addAttribute("LAYOUT_OUTLINE",transResourceInfo.getLayoutOutline());
            noticeRecord.addAttribute("DESCRIPTION",transResourceInfo.getDescription());
            noticeRecord.addAttribute("SURROUNDINGS",transResourceInfo.getSurroundings());
            noticeRecord.addAttribute("POST_TIME",DateUtil.Date2Str(dealNotice.getDeployTime()));
            noticeRecord.addAttribute("BARGAIN_TYPE","");
            noticeRecord.addAttribute("PARCEL_ID","");
            noticeRecord.addAttribute("AFFICHE_ID",transCrgg.getGgId());
            noticeRecord.addAttribute("CHECK_DATE","");
            noticeRecord.addAttribute("KSRQ","");
            noticeRecord.addAttribute("XM_MC","");
            noticeRecord.addAttribute("TD_YT","");
            noticeRecord.addAttribute("ISNEW","");
            noticeRecord.addAttribute("ZD_SL","");
            noticeRecord.addAttribute("ZPG_SJ_S","");
            noticeRecord.addAttribute("ZPG_SJ_E","");
            noticeRecord.addAttribute("ZPG_LX","");
            noticeRecord.addAttribute("EDIT_DATE","");
            noticeRecord.addAttribute("CREATE_DATE","");
            noticeRecord.addAttribute("HAVE_YCDK","");
            noticeRecord.addAttribute("GGDK_GUID","");
            noticeRecord.addAttribute("UKEYID","");
            noticeRecord.addAttribute("JMID","");
            noticeRecord.addAttribute("CRXZFS","");
            noticeRecord.addAttribute("BZXZFBL","");
            noticeRecord.addAttribute("BZXZFMJ","");
            noticeRecord.addAttribute("BZXZFTS","");
            noticeRecord.addAttribute("JYZT","");

        }

        return document.asXML();
    }

    private String generateReportTransCrggXml(List<TransCrgg> transCrggList){
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("LandjsRequest");
        Element  action = root.addElement("Action");
        action.addText("100");
       /* Element  businessType = root.addElement("BusinessType");
        businessType.addText("12");*/
        for(TransCrgg transCrgg:transCrggList){
            Element data = root.addElement("Data");
            data.addAttribute("BusinessType","12");
            Element crgg=data.addElement("PRI_TABLE_DATA");
            crgg.addAttribute("tableName","T_REMISE_AFFICHE");
            Element recordCrgg = crgg.addElement("RECORD");
            recordCrgg.addAttribute("GYGG_GUID",transCrgg.getGgId());//记录编号
            recordCrgg.addAttribute("XZQ_DM",transCrgg.getRegionCode());//行政区代码
            recordCrgg.addAttribute("AFFICHE_DATE",DateUtil.Date2Str(transCrgg.getGgBeginTime()));//公告开始时间
            recordCrgg.addAttribute("AFFICHE_END",DateUtil.Date2Str(transCrgg.getGgEndTime()));//公告截止时间
            recordCrgg.addAttribute("AFFICHE_NAME",transCrgg.getGgTitle());//公告名称
            recordCrgg.addAttribute("AFFICHE_NO",transCrgg.getGgNum());//公告编号
            recordCrgg.addAttribute("REMISE_TYPE",transCrgg.getRemiseType());//出让方式
            recordCrgg.addAttribute("REMISE_BULLETIN",transCrgg.getGgContent());//出让公告内容
            recordCrgg.addAttribute("POST_DATE", DateUtil.Date2Str(transCrgg.getPostDate()));//发布时间
            recordCrgg.addAttribute("PASS_MAN",transCrgg.getPassMan());//发布人
            recordCrgg.addAttribute("AUDITING_STATUS","1");//公告状态
            recordCrgg.addAttribute("POST_TIME","");//公告审核时间
            recordCrgg.addAttribute("AFFICHE_TYPE",transCrgg.getAfficheType()+"");//公告类别
            recordCrgg.addAttribute("RESOURCELB",transCrgg.getResourceLb());//资源类别
            recordCrgg.addAttribute("REMISE_UNIT",transCrgg.getRemiseUnit());//出让单位
            recordCrgg.addAttribute("CONFIRM_TIME","");//资格确认截止时间
            recordCrgg.addAttribute("GETFILE_STARTTIME","");//获取文件开始时间
            recordCrgg.addAttribute("GETFILE_ENDTIME","");//获取文件截止时间
            recordCrgg.addAttribute("GETFILE_ADDRESS","");//获取文件地点
            recordCrgg.addAttribute("SIGN_STARTTIME",DateUtil.Date2Str(transCrgg.getSignStartTime()));//报名开始时间
            recordCrgg.addAttribute("SIGN_ENDTIME", DateUtil.Date2Str(transCrgg.getSignEndTime()));//报名截止时间
            recordCrgg.addAttribute("SIGN_ADDRESS","");//报名地点
            recordCrgg.addAttribute("AUCTION_TIME","");//拍卖会时间
            recordCrgg.addAttribute("AUCTION_ADDRESS","");//拍卖地点
            recordCrgg.addAttribute("BID_STARTTIME",DateUtil.Date2Str(transCrgg.getBidStartTime()));//投标开始时间
            recordCrgg.addAttribute("BID_ENDTIME",DateUtil.Date2Str(transCrgg.getBidEndTime()));//投标截止时间
            recordCrgg.addAttribute("BID_ADDRESS","");//投标地点、拍卖会地点、挂牌地点
            recordCrgg.addAttribute("OPEN_TIME","");//开标时间
            recordCrgg.addAttribute("OPEN_ADDRESS","");//开标地点
            recordCrgg.addAttribute("WIN_STANDARD",transCrgg.getWinStandard());//中标原则
            recordCrgg.addAttribute("SET_TIME","");//标箱设立时间
            recordCrgg.addAttribute("SET_ADDRESS","");//标箱设立地点
            recordCrgg.addAttribute("PAYMENT_ENDTIME",DateUtil.Date2Str(transCrgg.getPaymentEndTime()));//缴纳保证金截止时间
            recordCrgg.addAttribute("LINKMAN",transCrgg.getLinkMan());//联系人
            recordCrgg.addAttribute("LINKADDRESS",transCrgg.getLinkAddress());//联系地址
            recordCrgg.addAttribute("LINKPHONE",transCrgg.getLinkPhone());//联系电话
            recordCrgg.addAttribute("OPENACCOUNT_UNIT","");//开户单位
            recordCrgg.addAttribute("OPENACCOUNT_BANK","");//开户银行
            recordCrgg.addAttribute("ACCOUNTS","");//账号
            recordCrgg.addAttribute("AGENT_STATUS","");//是否是代理
            recordCrgg.addAttribute("AGENT_NAME","");//代理人名称
            recordCrgg.addAttribute("AGENT_ADDRESS","");//代理人地址
            recordCrgg.addAttribute("AGENT_PHONE","");//代理人电话
            recordCrgg.addAttribute("PARCEL_MSG",transCrgg.getParcelMsg());//几（幅地块）
            recordCrgg.addAttribute("GG_LX",transCrgg.getGgLx());//公告类型
            recordCrgg.addAttribute("QT_GG_LX",transCrgg.getQtGglx());//其他公告类型
            recordCrgg.addAttribute("BID_TERM",transCrgg.getBidTerm());//竞买资格（申请人其他条件）
            recordCrgg.addAttribute("MEMO",transCrgg.getMemo());//备注
            recordCrgg.addAttribute("OTHER_TERM",transCrgg.getOtherTerm());//其他需要公告的事项
            recordCrgg.addAttribute("OTHER_MSG",transCrgg.getOtherMsg());//其他需要说明的宗地情况
            recordCrgg.addAttribute("PZ_JG",transCrgg.getPzJg());//批准机关
            recordCrgg.addAttribute("SQ_FS","");//申请方式
            recordCrgg.addAttribute("ZB_SJ_S","");//招标开始时间
            recordCrgg.addAttribute("ZB_SJ_E","");//招标截止时间
            recordCrgg.addAttribute("CR_SJ","");//出让时间仅公开/拍卖出让才有此字段
            recordCrgg.addAttribute("CR_DD","");//出让地点
            recordCrgg.addAttribute("SFFB","1");//是否发布0不发布 1已发布 默认导出到外网的都是1
            recordCrgg.addAttribute("OLD_GYGG_GUID","");//历史公告ID公告如有调整等关联的历史公告ID
            recordCrgg.addAttribute("GGDK_GUID","");//公告地块ID 其他公告关联的公告地块ID（如公告中止、终止）
            recordCrgg.addAttribute("EDIT_DATE","");//编辑时间
            recordCrgg.addAttribute("CREATE_DATE","");//创建时间
            recordCrgg.addAttribute("SFWSJY","");
            Element resource=data.addElement("SUB_TABLE_DATA");
            resource.addAttribute("tableName","T_AFFICHE_PARCEL");
            List<TransResource> transResourceList=transResourceService.findTransResource(transCrgg.getGgId());
            for(TransResource transResource:transResourceList){
                TransResourceInfo transResourceInfo=transResourceInfoService.getTransResourceInfoByResourceId(transResource.getResourceId());
                Element resourceRecord = resource.addElement("RECORD");
                resourceRecord.addAttribute("GGDK_GUID",transResource.getResourceId());//编号
                resourceRecord.addAttribute("XZQ_DM",transResource.getRegionCode());//市县代码
                resourceRecord.addAttribute("AFFICHE_NO",transCrgg.getGgNum());//公告编号
                resourceRecord.addAttribute("PARCEL_NO",transResource.getResourceCode());//地块编号
                resourceRecord.addAttribute("PARCEL_NAME","");//地块名称
                resourceRecord.addAttribute("AFFICHE_DATE", DateUtil.Date2Str(transResource.getShowTime()));//公告开始时间
                resourceRecord.addAttribute("AFFICHE_END", DateUtil.Date2Str(transResource.getOverTime()));//公告截止时间
                resourceRecord.addAttribute("SIGN_STARTTIME",DateUtil.Date2Str(transResource.getBmBeginTime()));//报名开始时间
                resourceRecord.addAttribute("SIGN_ENDTIME",DateUtil.Date2Str(transResource.getBmEndTime()));//报名截止时间
                resourceRecord.addAttribute("PAYMENT_STARTTIME",DateUtil.Date2Str(transResource.getBzjBeginTime()));//缴纳保证金开始时间   默认等于报名开始时间
                resourceRecord.addAttribute("PAYMENT_ENDTIME",DateUtil.Date2Str(transResource.getBzjEndTime()) );//缴纳保证金截止时间
                resourceRecord.addAttribute("AUCTION_TIME","");//拍卖会时间
                resourceRecord.addAttribute("POST_DATE","");//发布时间
                resourceRecord.addAttribute("REMISE_UNIT",transResource.getOwnershipUnit());//出让单位
                resourceRecord.addAttribute("SQ_FS","");//申请方式 et 1为单独申请，2为可联合申请
                resourceRecord.addAttribute("DEAL_TYPE",transResource.getDealType());//供地方式-出让
                resourceRecord.addAttribute("REMISE_TYPE",transResource.getOfferType()+"");//交易方式,招标、拍卖、挂牌、协议
                resourceRecord.addAttribute("BAIL",ResourceUtil.double2Str(transResource.getFixedOffer()));//保证金
                resourceRecord.addAttribute("BID_SCOPE", ResourceUtil.double2Str(transResource.getAddOffer()));//竞价幅度
                resourceRecord.addAttribute("START_PRICE",ResourceUtil.double2Str(transResource.getBeginOffer()));//起始价
                resourceRecord.addAttribute("LAND_POSITION",transResource.getResourceLocation());//位置
                resourceRecord.addAttribute("REMISE_AREA",ResourceUtil.double2Str(transResource.getCrArea()));//实际出让面积
                resourceRecord.addAttribute("LAND_USE",transResource.getLandUse()+"");//土地用途
                resourceRecord.addAttribute("TOTAL_AREA","");//用地总面积
                resourceRecord.addAttribute("DAIZH_AREA",ResourceUtil.double2Str(transResourceInfo.getDaizhArea()));//代征土地面积
                resourceRecord.addAttribute("CONSTRUCT_AREA",transResourceInfo.getConstructContent());//总建筑面积
                resourceRecord.addAttribute("SHCON_AREA",ResourceUtil.double2Str(transResourceInfo.getShconArea()));//商业建筑面积
                resourceRecord.addAttribute("ZZCON_AREA",ResourceUtil.double2Str(transResourceInfo.getZzconArea()));//住宅建筑面积
                resourceRecord.addAttribute("BGCON_AREA",ResourceUtil.double2Str(transResourceInfo.getBgconArea()));//办公建筑面积
                resourceRecord.addAttribute("DELIVER_TERM",transResourceInfo.getDeliverTerm());//现状土地条件（土地交付条件）
                resourceRecord.addAttribute("USE_YEAR","");//土地使用年期
                resourceRecord.addAttribute("MEMO",transResourceInfo.getMemo());//备注
                resourceRecord.addAttribute("AFFICHE_ID",transCrgg.getGgId());//公告ID
                resourceRecord.addAttribute("SURROUNDINGS",transResourceInfo.getSurroundings());//周边环境
                resourceRecord.addAttribute("LAYOUT_OUTLINE",transResourceInfo.getLayoutOutline());//规划要点
                resourceRecord.addAttribute("DESCRIPTION",transResourceInfo.getDescription());//地块介绍
                resourceRecord.addAttribute("POST_TIME","");
                resourceRecord.addAttribute("BID_RULES",transResource.getBidRule()+"");//竞价规则- 1 价高者得,2综合条件优者得
                resourceRecord.addAttribute("BUILDMATTER",transResourceInfo.getConstructContent());//建设内容
                resourceRecord.addAttribute("LOWESTINVEST",transResourceInfo.getInvestmentIntensity());//投资强度
                resourceRecord.addAttribute("SHAREPERCENT",transResourceInfo.getOfficeRatio());//办公和服务设施比例
                resourceRecord.addAttribute("BID_STARTTIME",DateUtil.Date2Str(transResource.getGpBeginTime()));//挂牌开始时间
                resourceRecord.addAttribute("BID_ENDTIME",DateUtil.Date2Str(transResource.getGpEndTime()));//挂牌截止时间
                resourceRecord.addAttribute("BID_ADDRESS","");//挂牌地点
                resourceRecord.addAttribute("AFFICHE_TYPE","");//公告类别 -0工业用地公告、1经营性用地公告、2其他公告、3协议出让类（划拨）公告
                resourceRecord.addAttribute("MIN_RJL",ResourceUtil.double2Str(transResourceInfo.getMinRjl()));//容积率下限
                resourceRecord.addAttribute("MAX_RJL",ResourceUtil.double2Str(transResourceInfo.getMaxRjl()));//容积率上限
                resourceRecord.addAttribute("MIN_JZ_MD",ResourceUtil.double2Str(transResourceInfo.getMinJzMd()));//建筑密度下限
                resourceRecord.addAttribute("MAX_JZ_MD",ResourceUtil.double2Str(transResourceInfo.getMaxJzMd()));//建筑密度上限
                resourceRecord.addAttribute("MIN_LHL",ResourceUtil.double2Str(transResourceInfo.getMinLhl()));//绿化率下限
                resourceRecord.addAttribute("MAX_LHL",ResourceUtil.double2Str(transResourceInfo.getMaxLhl()));//绿化率上限
                resourceRecord.addAttribute("MIN_JZXG",ResourceUtil.double2Str(transResourceInfo.getMinJzxg()));//建筑限高下限
                resourceRecord.addAttribute("MAX_JZXG",ResourceUtil.double2Str(transResourceInfo.getMaxJzxg()));//建筑限高上限
                resourceRecord.addAttribute("TD_YT",transResource.getLandUse().getTitle());//土地用途
                resourceRecord.addAttribute("LP_ZT","");//流拍状态
                resourceRecord.addAttribute("TD_TJ_XX",transResourceInfo.getTdTjXx());//约定土地提交条件
                resourceRecord.addAttribute("CD_PZ",transResourceInfo.getCdPz());//场地平整
                resourceRecord.addAttribute("JC_SS",transResourceInfo.getJcSs());//基础设施
                resourceRecord.addAttribute("SELRJL1",transResourceInfo.getSelrjl());//容积率下限关系符号
                resourceRecord.addAttribute("SELRJL2",transResourceInfo.getSelrj2());//容积率上限关系符号
                resourceRecord.addAttribute("MIN_JZ_MD_TAG",transResourceInfo.getMinJzMdTag());//建筑密度下限标识
                resourceRecord.addAttribute("MAX_JZ_MD_TAG",transResourceInfo.getMaxJzMdTag());//建筑密度上限标识
                resourceRecord.addAttribute("MIN_LHL_TAG",transResourceInfo.getMinLhlTag());//绿化率下限标识
                resourceRecord.addAttribute("MAX_LHL_TAG",transResourceInfo.getMaxLhlTag());//绿化率上限标识
                resourceRecord.addAttribute("MIN_JZ_XG_TAG",transResourceInfo.getMinJzXgTag());//建筑限高下限标识
                resourceRecord.addAttribute("MAX_JZ_XG_TAG",transResourceInfo.getMaxJzXgTag());//建筑限上限标识
                resourceRecord.addAttribute("X","");//X中心坐标
                resourceRecord.addAttribute("Y","");//Y中心坐标
                resourceRecord.addAttribute("CJFS",transResource.getBidType().getCode());//出价方式
                resourceRecord.addAttribute("CJDW","0"+transResource.getOfferUnit());//出价单位  offerUnit
                resourceRecord.addAttribute("CRXZFS","");//出让限制方式
                resourceRecord.addAttribute("ZGXJ",ResourceUtil.double2Str(transResource.getMaxOffer()));//最高限价
                resourceRecord.addAttribute("BZXZFCSBL","");//保障性住房初始比例
                resourceRecord.addAttribute("BZXZFJJZJFD","");//保障性住房竞价增价幅度
                resourceRecord.addAttribute("BZXZFCSMJ","");//保障性住房初始面积
                resourceRecord.addAttribute("BZXZFJJMJFD","");//保障性住房竞价面积幅度
                resourceRecord.addAttribute("BZXZFTX","");//保障性住房套型
                resourceRecord.addAttribute("BZXZFCSTS","");//保障性住房初始套数
                resourceRecord.addAttribute("BZXZFJJTSFD","");//保障性住房竞价套数幅度
                resourceRecord.addAttribute("EDIT_DATE","");//编辑时间
                resourceRecord.addAttribute("CREATE_DATE","");//创建时间
                resourceRecord.addAttribute("SFFB","1");//是否发布0不发布 1已发布  -默认导出到外网的都是1
                resourceRecord.addAttribute("JYZT","");//交易状态
                resourceRecord.addAttribute("RESOURCELB","");//资源类别
                if(transResource.isMinOffer()){
                    resourceRecord.addAttribute("SFYDJ","1");//是否有底价
                }else{
                    resourceRecord.addAttribute("SFYDJ","0");//是否有底价
                }
                resourceRecord.addAttribute("SFWSJY","");
                resourceRecord.addAttribute("SFWB","");
                resourceRecord.addAttribute("WBBZJ","");
                resourceRecord.addAttribute("VCUID","");
                resourceRecord.addAttribute("DEVICEID","");

            }
            Element file=data.addElement("SUB_TABLE_DATA");
            file.addAttribute("tableName","T_FJ");
            for(TransResource transResource:transResourceList){
                List<TransFile> transFileList= transFileService.getTransFileByKey(transResource.getResourceId());
                for(TransFile transFile:transFileList){
                    Element fileRecord = file.addElement("RECORD");
                    /*URL		VARCHAR2(500)	出让系统上传的XML文件带有这个字段，根据这个字段去下载附件文件
                    CREATE_DATE	创建时间	DATETIME
                    EDIT_DATE	更新时间	DATETIME*/
                    fileRecord.addAttribute("FJ_GUID",transFile.getFileId());//附件标识【主键】
                    fileRecord.addAttribute("LINK_GUID",transFile.getFileKey());//关联项目的标识
                    fileRecord.addAttribute("FJ_LX",transFile.getFileType());//附件类型
                    fileRecord.addAttribute("FJ_MC",transFile.getFileName());//附件名称?
                    fileRecord.addAttribute("FJ_XH",transFile.getFileNo());//附件序号
                    fileRecord.addAttribute("FJ_WJ_LX","");//附件的文件类型
                    fileRecord.addAttribute("FJ_MS",transFile.getDescription());//附件描述
                    fileRecord.addAttribute("FJ_LJ",transFile.getStorePath());//附件的物理路径
                    fileRecord.addAttribute("FJ_FILENAME",transFile.getFileName());//附件的文件名?
                    fileRecord.addAttribute("FJ_MIME_TYPE","");
                }
            }

        }
        return document.asXML();
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
                Date bidStartTime=ResourceUtil.getDate(ResourceUtil.addDate(transCrgg.getGgBeginTime(),1),9,0);
                transCrgg.setBidStartTime(bidStartTime);
                Date bidEndTime=ResourceUtil.getDate(ResourceUtil.addDate(bidStartTime,9),17,30);
                transCrgg.setBidEndTime(bidEndTime);
                transCrgg = transCrggService.saveTransCrgg(transCrgg);
               /* List<TransFile> transFileList = transCrgg.getAttachmentList();
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
                    transResource.setYxEndTime(transResource.getBmEndTime());
                    transResourceService.saveTransResource(transResource);
                  /*  for(TransFile transFile:transResource.getAttachmentList()){
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
            crggObject.setRegionCode(SecUtil.getPermittedRegions().toArray()[0].toString());
            crggObject.setGgBeginTime(DateUtils.parse(element.valueOf("@AFFICHE_DATE")));
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
            //transResource.setRegionCode(resourceElement.valueOf("@XZQ_DM"));
            transResource.setRegionCode(SecUtil.getPermittedRegions().toArray()[0].toString());
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
        else if(landUser.equals("住宅用地"))
            return Constants.LandUse.ZZYD;
        else if(landUser.equals("工业"))
            return Constants.LandUse.GY;
        else if(landUser.equals("仓储"))
            return Constants.LandUse.CC;
        else if(landUser.equals("科教"))
            return Constants.LandUse.KJ;
        else if(landUser.equals("工业(标准厂房)"))
            return Constants.LandUse.GYBZCF;
        else if(landUser.equals("工业(高标准厂房)"))
            return Constants.LandUse.GYGBZCF;
        else if(landUser.equals("工业(研发)"))
            return Constants.LandUse.GYYF;
        else if(landUser.equals("工业(文化创意)"))
            return Constants.LandUse.GYWHCY;
        else if(landUser.equals("工业(总部经济)"))
            return Constants.LandUse.GYZBJJ;
        else if(landUser.equals("科教(研发)"))
            return Constants.LandUse.KJYF;
        else if(landUser.equals("科教(文化创意)"))
            return Constants.LandUse.KJWHCY;
        else if(landUser.equals("科教(总部经济)"))
            return Constants.LandUse.KJYF;
        else if(landUser.equals("科教(研发)"))
            return Constants.LandUse.KJZBJJ;
        else
            return Constants.LandUse.QT;
    }

}
