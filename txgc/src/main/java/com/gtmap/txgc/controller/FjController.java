package com.gtmap.txgc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gtmap.txgc.service.BusinessService;

@Controller
public class FjController {

	private Logger logger = Logger.getLogger(DataController.class);
	
	@Resource
	private BusinessService businessService;
	
	@RequestMapping("xmfj")
	public String fjManage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String guid = request.getParameter("guid");
		String username = request.getParameter("username");
		List<HashMap<String,Object>> list = new ArrayList<>();
		try {
			list = businessService.retrieveFjByLinkGuid(guid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("fjList", list);
		model.addAttribute("guid", guid);
		model.addAttribute("username", username);
		return "fjManage";
	}
	
	@ResponseBody
	@RequestMapping("deleteFj")
	public void deleteFj(HttpServletRequest request,
			HttpServletResponse response) {
		String guid = request.getParameter("guid");//附件guid
		businessService.deleteFj(guid);
	}
	
	@RequestMapping("fjsc")
	public String fjsc(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "fujian", required = false) MultipartFile fujian) {
		String linkGuid = request.getParameter("guid");// 附件关联guid
		String username = request.getParameter("username");
		if (null != fujian && !fujian.getOriginalFilename().equals("")) {
			saveFj(fujian, linkGuid, request);
		}
		return "redirect:xmfj?guid="+linkGuid+"&username="+username;
	}
	
	@ResponseBody  
	@RequestMapping("download") 
	public void download(String guid, HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> fj = businessService.retrieveFj(guid);
		if(fj!=null&&fj.size()>0){
			String fileName = (String)fj.get("FJ_MC");
			try {
				response.setCharacterEncoding("utf-8");
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				logger.info("下载文件设置参数异常",e1);
			}
			try {
				String path = getFjPath(request);
				path = path + fj.get("FJ_LJ")+fj.get("REAL_NAME");
				File downloadFile = new File(path);// 项目绝对路径 + 文件类型的路径
				InputStream inputStream = new FileInputStream(downloadFile);
	
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[2048];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				os.close();// 这里主要关闭。
				inputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
	}
	
	private String saveFj(MultipartFile fujian, String guid,HttpServletRequest request) {
		String username = request.getParameter("username");
		HashMap<String,Object> fjMap = new HashMap<String,Object>();
		String filePath = getFjPath(request);
		String fileName = fujian.getOriginalFilename();
		String fjGuid = UUID.randomUUID().toString();
		// 设置附件对象参数
		fjMap.put("FJ_GUID", fjGuid);
		fjMap.put("FJ_MC", fileName);
		String fileLj = "/xmFile/" + guid + "/";
		fjMap.put("FJ_LJ", fileLj);
		fjMap.put("LINK_GUID", guid);
		username = username!=null?username:"";
		fjMap.put("CREATE_USER", username);
		int pointIndex = fileName.indexOf(".");
		String realName = fjGuid;
		if (pointIndex > 0) {
			String fileNameSuf = fileName.substring(pointIndex);
			realName = fjGuid + fileNameSuf;
		}
		fjMap.put("REAL_NAME", realName);
		String fujianPath = filePath+fileLj;
		File parentPath = new File(fujianPath);
		if (!parentPath.exists()) {
			parentPath.mkdirs();
		}
		try {
			SaveFileFromInputStream(fujian.getInputStream(),fujianPath, realName);// 写入文件
			businessService.saveFj(fjMap); //存入数据库
		} catch (IOException e) {
			logger.error("文件上传异常:"+guid+","+fjGuid);
			logger.error(e.getMessage(), e);
		}
		return fjGuid;
	}
	
	private void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	private String getFjPath(HttpServletRequest request){
		String absolutePath = request.getSession().getServletContext().getRealPath("");
		String filePath = new File(absolutePath).getParentFile().getParentFile().getAbsolutePath();
		return filePath;
	}
}
