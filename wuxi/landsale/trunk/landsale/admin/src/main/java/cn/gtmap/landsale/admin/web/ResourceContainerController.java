package cn.gtmap.landsale.admin.web;

import cn.gtmap.landsale.admin.core.TransResourceContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zsj
 * @version v1.0, 2017/12/12
 */
@RestController
@RequestMapping("/resourceContainer")
public class ResourceContainerController {

    @Autowired
    TransResourceContainer transResourceContainer;

    @RequestMapping("/remove")
    public void remove(@RequestParam("resourceId") String resourceId) {
        transResourceContainer.removeResource(resourceId);
    }

}
