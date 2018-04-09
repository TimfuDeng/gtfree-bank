package com.gtmap.txgc.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtmap.txgc.bean.PewgBean;
import com.gtmap.txgc.service.BusinessService;

@Controller
public class DataController {
	
	private Logger logger = Logger.getLogger(DataController.class);
	
	@Resource
	private BusinessService businessService;
	
	@RequestMapping("retrieve")
	public String retrievePewg(HttpServletRequest request){
		String username = request.getParameter("username");
		String pageName = request.getParameter("pageName");
		HashMap<String, Object> map = businessService.retrieveUser(username);
		String xzqdmlike = map!=null?(String)map.get("REGIONCODE"):"";
		return "redirect:../report/ReportServer?reportlet=txgc%2F"+pageName+".cpt&op=write&__cutpage__=v&username="+username+"&xzqdmlike="+xzqdmlike;
	}
	
	@RequestMapping("getPewg")
	@ResponseBody
	public List<HashMap<String,Object>> getPewg(HttpServletRequest request){
		PewgBean pewg = genPewgBean(request);
		List<HashMap<String, Object>> list = businessService.retrievePewg(pewg);
		return list;
	}
	
	@RequestMapping("getPewgCount")
	@ResponseBody
	public int getPewgCount(HttpServletRequest request){
		PewgBean pewg = genPewgBean(request);
		List<HashMap<String, Object>> list = businessService.retrievePewg(pewg);
		return list.size();
	}
	
	@RequestMapping("validationPewg")
	@ResponseBody
	public String validationPewg(HttpServletRequest request){
		PewgBean pewg = genPewgBean(request);
		String result = "true";
		List<HashMap<String, Object>> list = businessService.retrievePewg(pewg);
		if(list!=null&&list.size()>0){
			result = "1";
		}else{
			if(pewg.getMj()!=null&&pewg.getMj().length()>6){
				HashMap<String, Object> maxMap = businessService.retrieveMaxPewg(pewg);
				if(maxMap!=null&&maxMap.size()>0){
					String mjArray[] = pewg.getMj().split(",");
					if(mjArray.length==7){
						for(int i=0;i<mjArray.length;i++){
							String mjStr = mjArray[i];
							if(mjStr!=null&&mjStr.length()>0&&maxMap.get("MJ"+i)!=null){
								try{
									double mj = Double.parseDouble(mjStr);
									double maxMj = Double.parseDouble(maxMap.get("MJ"+i).toString());
									if(mj<maxMj){
										return "2";
									}
								}catch(Exception e){
									logger.error("Convert Area error:"+mjStr+","+maxMap.get("MJ"+i).toString());
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	private PewgBean genPewgBean(HttpServletRequest request){
		String xzq = request.getParameter("xzq");
		String tbrq = request.getParameter("tbrq");
		String mj = request.getParameter("mj");
		PewgBean pewg = new PewgBean();
		pewg.setXzq(xzq.replaceAll("[^0-9a-zA-Z]", ""));
		pewg.setTbrq(tbrq);
		pewg.setMj(mj);
		return pewg;
	}
	
}
