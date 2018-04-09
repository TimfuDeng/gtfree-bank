package cn.gtmap.landsale.admin.register;

import cn.gtmap.landsale.common.model.TransUserUnion;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by M150237 on 2017-11-09.
 */
@FeignClient(name = "core-server")
public interface TransUserUnionClient {

    /**
     * 获取 联合竞买列表
     * @param applyId 地块申请Id
     * @return 出让公告对象
     */
    @RequestMapping(value = "/union/findTransUserUnion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransUserUnion> findTransUserUnion(@RequestParam(value = "applyId") String applyId);




}
