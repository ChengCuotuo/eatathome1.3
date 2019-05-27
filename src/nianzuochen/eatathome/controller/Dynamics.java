package nianzuochen.eatathome.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.DynamicService;
import nianzuochen.mybatis.domain.Dynamic;
import nianzuochen.mybatis.domain.User;

@Controller
public class Dynamics {
	private DynamicService ds = new DynamicService();
	
	//刷新动态，显示前20条动态
	@RequestMapping(value="viewDynamics")
	public void viewDynamic(HttpServletResponse response)  {
		response.setContentType("text/html; charset=utf-8");
		List<Dynamic> dynamics = ds.selectTwentyDynamics();
		try {
			ObjectMapper mapper = new ObjectMapper();
			String info = mapper.writeValueAsString(dynamics);
			response.getWriter().print(info);
			//System.out.println(info);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	//上传图片，图片是以 json 格式上传的
	@RequestMapping(value="releaseDynamic", method=RequestMethod.POST)
	public String releaseDynamic(HttpServletRequest request,
			@RequestParam("photo") MultipartFile file) throws InterruptedException {
		//将获取到的描述信息和图片存储起来
		String filePath = request.getServletContext().getRealPath("/easyui_pages/pages/images/dynamic/");
		//存储的文件的名称 当前用户名 + 当前时间的毫秒 + 上传的文件的后缀名
		HttpSession session = request.getSession();
		String userName = ((User)session.getAttribute("user")).getName();
		String fileName = file.getOriginalFilename();	//获取文件的原名称
		fileName = userName + System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
		//System.out.println("filePath: " + filePath + ", fileName: " + fileName);
		File storePath = new File(filePath, fileName);
		//System.out.println(filePath + fileName);
		if (storePath.getParentFile().exists()) {
			try {
				//将上传的文件存储，从session中获取描述和当前用户的信息上传到数据库
				file.transferTo(new File(filePath + fileName));
				//System.out.println(storePath.getParentFile());
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println(filePath + fileName);
			System.out.println("notFound");
		}
		
		return "dynamicForm.html";
	}
	
	//将用户对动态的描述保存在 session 中
	@RequestMapping(value="storeDescribe")
	public void storeDescribe(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String describe = request.getParameter("describe");
		if (describe != null) {
			session.setAttribute("describe", describe);
			response.getWriter().print("{\"result\" : \"success\"}");
		} else {
			response.getWriter().print("{\"result\" : \"false\"}");
		}
		
	}
}
