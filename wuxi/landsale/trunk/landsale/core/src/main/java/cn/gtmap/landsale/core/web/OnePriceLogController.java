package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.OnePriceLog;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OnePriceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/onePriceLog")
public class OnePriceLogController {

    @Autowired
    OnePriceLogService onePriceLogService;

    @RequestMapping(value = "/findOnePriceLogList")
    List<OnePriceLog> findOnePriceLogList(@RequestParam(value = "transResourceId") String transResourceId){
        return onePriceLogService.findOnePriceLogList(transResourceId);
    }

    @RequestMapping(value = "/findOnePriceLogListOrderByBj")
    List<OnePriceLog> findOnePriceLogListOrderByBj(@RequestParam(value = "transResourceId") String transResourceId){
        return onePriceLogService.findOnePriceLogListOrderByBj(transResourceId);
    }

    @RequestMapping(value = "/findOnePriceLogListByTransResourceIdTransUserId")
    OnePriceLog findOnePriceLogListByTransResourceIdTransUserId(@RequestParam(value = "transResourceId") String transResourceId, @RequestParam(value = "transUserId") String transUserId){
        return onePriceLogService.findOnePriceLogListByTransResourceIdTransUserId(transResourceId, transUserId);
    }

    @RequestMapping(value = "/findOnePriceLogListByTransResourceIdPrice")
    OnePriceLog findOnePriceLogListByTransResourceIdPrice(@RequestParam(value = "transResourceId") String transResourceId,@RequestParam(value = "price") Long price){
        return onePriceLogService.findOnePriceLogListByTransResourceIdPrice(transResourceId,price);
    }

    @RequestMapping(value = "/saveOnePriceLog")
    ResponseMessage<OnePriceLog> saveOnePriceLog(@RequestBody OnePriceLog onePriceLog){
        return onePriceLogService.saveOnePriceLog(onePriceLog);
    }

    @RequestMapping(value = "/getOnePriceLog")
    OnePriceLog getOnePriceLog(@RequestParam(value = "applyId") String applyId){
        return onePriceLogService.getOnePriceLog(applyId);
    }

}
