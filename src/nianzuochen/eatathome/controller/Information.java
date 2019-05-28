package nianzuochen.eatathome.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import nianzuochen.eatathome.service.UserService;
import nianzuochen.mybatis.domain.User;

//处理用户信息的修改
@Controller
public class Information {
	private UserService us = new UserService();
	
	//跳转到用户信息修改的页面 userinfo
	@RequestMapping(value="userinfo")
	public String touUserinfoPage () {
		return "changeinfo.html";
	} 
	
	@RequestMapping(value="changehead", method=RequestMethod.POST)
	public String userinfo(HttpServletRequest request,
			@RequestParam("photo") MultipartFile file) {
		//提供用户的信息 允许用户修改头像 
		// 用户头像重新上传之后需要修改数据库中的用户的 head 属性的值为用户名
		//允许用户重新修改密码
		//重新修改密码需要输入旧密码，成立之后才能修改
		//将获取到的描述信息和图片存储起来
		String filePath = request.getServletContext().getRealPath("/easyui_pages/pages/images/user/");
		//存储的文件的名称 当前用户名 + 当前时间的毫秒 + 上传的文件的后缀名
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userName = user.getName();
		String fileName = file.getOriginalFilename();	//获取文件的原名称
		//使用用户的名称作为头像的名称，后缀名为文件的原后缀名
		fileName = userName + fileName.substring(fileName.indexOf("."));
		File storePath = new File(filePath, fileName);
		if (storePath.getParentFile().exists()) {
			try {
				//D:\apache-tomcat-8.5.35\wtpwebapps\eatathome1.3\easyui_pages\pages\images//user			
				//将上传的文件存储，从session中获取描述和当前用户的信息上传到数据库
				file.transferTo(new File(filePath + fileName));
				//修改数据库中的信息，然后修改 session 中的信息
				if (user.getHead().equals("default.jpg")) {
					user.setHead(fileName);
					session.setAttribute("user", user);
					us.updateUser(user);
				}
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			//System.out.println(filePath + fileName);
			System.out.println("notFound");
		}	
		return "changeinfo.html";
	}
	
	//修改密码 传递的参数有 oldpass 和 pass
	@RequestMapping(value="changepass", method=RequestMethod.POST)
	public void changePass(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String oldpass = request.getParameter("oldpass");
		String pass = request.getParameter("pass");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user.getPassword().equals(oldpass)) {
			user.setPassword(pass);
			us.updateUser(user);
			response.getWriter().print("{\"result\":\"success\"}");
		} else {
			response.getWriter().print("{\"result\":\"旧密码输入错误\"}");
		}
	}
}
