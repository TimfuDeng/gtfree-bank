package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.core.BankAllList;
import cn.gtmap.landsale.core.RegionAllList;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.RMBUtils;
import cn.gtmap.landsale.util.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 出让地块管理
 * User: JIFF
 * Date: 2014/12/31
 */
public class ResourceController{
    //将挂牌时间，报名时间，保证金交纳记录在static变量里，下次新增时提取
    public static Date bmBeginTime = null;
    public static Date bmEndTime = null;
    public static Date gpBeginTime = null;
    public static Date gpEndTime = null;
    public static Date bzjBeginTime = null;
    public static Date bzjEndTime = null;
}
