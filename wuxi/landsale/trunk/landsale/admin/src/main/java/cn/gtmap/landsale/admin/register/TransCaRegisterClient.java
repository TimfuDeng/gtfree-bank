package cn.gtmap.landsale.admin.register;

import cn.gtmap.egovplat.core.data.PageImpl;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCaRegister;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ca管理服务
 * @author cxm
 * @version v1.0, 2017/9/29
 */
@FeignClient(name = "core-server")
public interface TransCaRegisterClient {

    /**
     * 获取CA用户分页服务
     * @param contactUser 联系人
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/register/findRegisterUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    PageImpl<TransCaRegister> findRegisterUser(@RequestParam(value = "contactUser", required = false) String contactUser,@RequestBody Pageable pageable);

    /**
     * 保存
     * @param transCaRegister
     * @return
     */
    @RequestMapping(value = "/register/saveTransCaRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransCaRegister> saveTransCaRegister(@RequestBody TransCaRegister transCaRegister);

    /**
     * 根据注册id获取CA用户
     * @param registerId 注册id
     * @return
     */
    @RequestMapping(value = "/register/getTransCaRegisterByRegisterId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransCaRegister> getTransCaRegisterByRegisterId(@RequestParam(value = "registerId", required = false) String registerId);

    /**
     * 根据用户ID获取CA用户
     * @param userId 用户ID
     * @return
     */
    @RequestMapping(value = "/register/getTransCaRegisterByUserId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransCaRegister> getTransCaRegisterByUserId(@RequestParam(value = "userId", required = false) String userId);

    /**
     * 根据用户ID和行政区代码获取CA用户
     * @param userId 用户ID
     * @param regionCode  行政区代码
     * @return
     */
    @RequestMapping(value = "/register/getCaRegisterByUserIdAndCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransCaRegister> getCaRegisterByUserIdAndCode(@RequestParam(value = "userId", required = false) String userId,@RequestParam(value = "regionCode", required = false) String regionCode);


}
