package cn.gtmap.landsale.admin.register;

import cn.gtmap.landsale.common.model.DealNotice;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "core-server")
public interface ClientClient {

    /**
     * 获得大于timevalue的最新报价，最多15条
     * @param resourceId
     * @param timeValue
     * @return
     */
    @RequestMapping(value = "/client/getOfferList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceOffer> getOfferList(@RequestParam("resourceId") String resourceId, @RequestParam("timeValue") long timeValue);

}
