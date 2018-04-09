package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.LandUseDictClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.service.TransFloorPriceService;
import cn.gtmap.landsale.admin.service.TransFloorPriceViewService;
import cn.gtmap.landsale.admin.service.TransRoleService;
import cn.gtmap.landsale.admin.service.TransUserViewService;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 底价配置
 *
 * @author cxm
 * @version v1.0, 2017/11/23
 */
@Controller
@RequestMapping("/floorPrice")
public class TransFloorPriceController {
    @Autowired
    TransRoleService transRoleService;

    @Autowired
    TransUserViewService transUserViewService;

    @Autowired
    TransFloorPriceViewService transFloorPriceViewService;

    @Autowired
    LandUseDictClient landUseDictClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransFloorPriceService transFloorPriceService;

    @RequestMapping("/index")
    public String index(String viewName, Integer userType, String regionCodes, @PageDefault(value = 10) Pageable page, Model model,HttpServletRequest request) {
        //获取人员列表
        Page<TransFloorPriceView> transFloorPricList = transFloorPriceViewService.findFloorUserPage(viewName, userType, regionCodes, page);
        model.addAttribute("transFloorPricList", transFloorPricList);
        model.addAttribute("viewName", viewName);
        model.addAttribute("userType", userType);
        model.addAttribute("regionCodes", regionCodes);
        SecUtil.getLoginUserToSession(request);
        return "floorPrice/floorPrice-list";
    }

    @RequestMapping("/grant")
    public String grant(Model model, String userId) {
//        TransFloorPrice transFloorPrice = transFloorPriceService.getTransFloorPrice(userId);
//        if (transFloorPrice!=null){
//            String codes=transFloorPrice.getTdytDictCode();
//            List nameList=new ArrayList<>();
//            for (String code:codes.split(",")){
//                LandUseDict landUseDict=landUseDictClient.getLandUseDict(code);
//                nameList.add(landUseDict.getName());
//            }
//            String tdytNames=listToString(nameList.toArray(),",");
//            model.addAttribute("tdytNames", tdytNames);
//        }

        TransFloorPriceView transFloorPriceView=transFloorPriceViewService.getFloorViewById(userId);
        model.addAttribute("transFloorPriceView", transFloorPriceView);
        model.addAttribute("tdytNames", transFloorPriceView.getTdytDictName());
        model.addAttribute("transRegionName", transFloorPriceView.getRegionName());
        model.addAttribute("transRegionCode", transFloorPriceView.getRegionCode());
             return "floorPrice/floorPrice-grant";
    }

//    private   String listToString(Object[] list,String separater){
//        return StringUtils.join(list, separater);
//    }


    @RequestMapping("/grant/save")
    @ResponseBody
    public ResponseMessage<TransFloorPrice> saveGrant(@RequestParam(value = "userId", required = true) String userId, String regionCodes, String tdytDictCodes) {
        TransFloorPrice transFloorPrice = transFloorPriceService.getTransFloorPrice(userId);
        if (transFloorPrice == null) {
            return transFloorPriceService.saveGrant(userId, regionCodes, tdytDictCodes);
        } else {
            return transFloorPriceService.editGrant(transFloorPrice, regionCodes, tdytDictCodes);
        }
    }


    @RequestMapping("/grant/deleteGrant")
    @ResponseBody
    public ResponseMessage<TransFloorPrice> deleteGrant(@RequestParam(value = "floorPriceIds", required = true) String floorPriceIds) {
        if (StringUtils.isNotBlank(floorPriceIds)){
            return transFloorPriceService.deleteGrant(floorPriceIds);
        }else {
            return new ResponseMessage(false,"该用户还未授权，无法删除授权！");
        }
    }


    @RequestMapping("/getLandUseDictTree")
    @ResponseBody
    public List<Tree> getLandUseDictTree(String tdytDictCode) {
        List<LandUseDict> landUseDictList = landUseDictClient.getLandUseDictList();
        return landUseDictList2Node(landUseDictList, tdytDictCode);
    }

    private List<Tree> landUseDictList2Node(List<LandUseDict> landUseDictList, String tdytDictCode) {
        List<Tree> treeList = new ArrayList();
        if (StringUtils.isEmpty(tdytDictCode)) {
            for (LandUseDict landUseDict : landUseDictList) {
                Tree tree = new Tree();
                tree.setId(landUseDict.getCode());
                tree.setpId(landUseDict.getParent());
                tree.setName(landUseDict.getName());
                treeList.add(tree);
            }
        } else {
            String[] tdytDictCodeArr = tdytDictCode.split(",");
            for (LandUseDict landUseDict : landUseDictList) {
                Tree tree = new Tree();
                tree.setId(landUseDict.getCode());
                tree.setpId(landUseDict.getParent());
                tree.setName(landUseDict.getName());
                for (String s : tdytDictCodeArr) {
                    if (s.equals(landUseDict.getCode())) {
                        tree.setChecked(true);
                    }
                }
                treeList.add(tree);
            }
        }
        return treeList;
    }


}
