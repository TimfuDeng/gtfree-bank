package cn.gtmap.landsale.view.web.console;

import cn.gtmap.landsale.core.ResourceOfferReal;
import cn.gtmap.landsale.core.ResourceOfferRealContainer;
import cn.gtmap.landsale.model.QueryCondition;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.ResourceOfferService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.support.UUIDGenerator;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by JIBO on 2016/9/14.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    static int pageSize=16;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    ResourceOfferService resourceOfferService;

    @Autowired
    ResourceOfferRealContainer resourceOfferRealContainer;

    @RequestMapping("time")
    public @ResponseBody String getServerTime() throws Exception{
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    @RequestMapping("list")
    public @ResponseBody
    Map<String,Object> getList(QueryCondition condition) throws Exception{
        List<TransResource> transResourceList=transResourceService.selectByPage(condition,pageSize);
        int count=transResourceService.selectCount(condition);
        int pageCount=(int)Math.ceil(count*1.0/pageSize*1.0);
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("condition",condition);
        result.put("data",transResourceList);
        result.put("pageCount",pageCount);
        return result;
    }

    @RequestMapping("resource")
    public @ResponseBody TransResource getResource(String resourceId) throws Exception{
        return transResourceService.selectByKey(resourceId);
    }

    @RequestMapping("offer")
    public @ResponseBody Map<String,Object> getOfferInfo(@RequestParam(required=true)String resourceId,
                                                               @RequestParam(required=false,defaultValue = "0")long time){
        Map<String,Object> result=new HashMap<String,Object>();
        ResourceOfferReal resourceOfferReal= resourceOfferRealContainer.getResourceOfferReal(resourceId);
        if (resourceOfferReal==null){
            List<TransResourceOffer> transResourceOfferList=
                    resourceOfferService.selectOfferListByPage(resourceId,20,0);
            int count=resourceOfferService.selectCount(resourceId);
            resourceOfferReal=new ResourceOfferReal(transResourceOfferList,count);
            resourceOfferRealContainer.putResourceOfferReal(resourceId,resourceOfferReal);
            result.put("count",count);
            result.put("list",transResourceOfferList);
            return result;
        }else{
            if (resourceOfferReal.getPutTime()>time){
                List<TransResourceOffer> transResourceOfferList=
                        resourceOfferReal.getTransResourceOfferList();
                List<TransResourceOffer> tempList = new ArrayList<TransResourceOffer>(transResourceOfferList);//通过CopyOnWriteArrayList创建一个ArrayList
                Collections.sort(tempList);

                result.put("count",resourceOfferReal.offerCount());
                if (tempList.size()>15){
                    result.put("list", tempList.subList(0,15));
                }else {
                    result.put("list", tempList);
                }
                return result;
            }else{
                return null;
            }
        }
    }

    @RequestMapping("add")
    public @ResponseBody String add(@RequestParam(required=true)String resourceId){
        TransResourceOffer resourceOffer=new TransResourceOffer();
        resourceOffer.setOfferId(UUIDGenerator.generate());
        resourceOffer.setResourceId(resourceId);
        resourceOffer.setOfferPrice(Math.random());
        resourceOffer.setOfferTime(Calendar.getInstance().getTimeInMillis());
        resourceOfferService.insert(resourceOffer);
        return resourceOffer.getOfferId();
    }

    @RequestMapping("finish")
    public @ResponseBody int finish(@RequestParam(required=true)String resourceId){
        TransResource resource= transResourceService.selectByKey(resourceId);
        return transResourceService.updateStatusToFinish(resource,"30","南京万科置业有限公司");
    }
}
