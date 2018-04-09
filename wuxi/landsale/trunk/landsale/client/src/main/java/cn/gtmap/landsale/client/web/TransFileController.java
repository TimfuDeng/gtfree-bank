package cn.gtmap.landsale.client.web;

import cn.gtmap.landsale.client.register.TransFileClient;
import cn.gtmap.landsale.common.model.TransFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by M150237 on 2017-10-25.
 */
@Controller
@RequestMapping("/transfile")
public class TransFileController {

    @Autowired
    TransFileClient transFileClient;

    /**
     * 除了缩略图之外所有的附件
     * @param fileKey
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping("/attachments")
    @ResponseBody
    public List<TransFile> attachments(String fileKey) throws IOException {
        return transFileClient.getTransFileAttachments(fileKey);
    }

}
