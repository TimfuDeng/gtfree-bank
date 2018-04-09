package cn.gtmap.landsale.core.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    /**
     * 获得大于timevalue的最新报价，最多15条
     * @param resourceId
     * @param timeValue
     * @return
     */
    @RequestMapping("/getOfferList")
    public List<TransResourceOffer> getOfferList(@RequestParam("resourceId") String resourceId, @RequestParam("timeValue") long timeValue) {
        return clientService.getOfferList(resourceId,timeValue);
    }

    @RequestMapping("/findTransResourceOffers")
    public Page<TransResourceOffer> findTransResourceOffers(@RequestBody Pageable request, @RequestParam("resourceId") String resourceId) {
        return clientService.findTransResourceOffers(request,resourceId);
    }

}
