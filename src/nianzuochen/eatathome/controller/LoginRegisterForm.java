package nianzuochen.eatathome.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nianzuochen.eatathome.service.UserService;
import nianzuochen.mybatis.domain.User;

@Controller
public class LoginRegisterForm {
	private UserService userService = new UserService();
	
	//处理登录和注册页面的转发
	@RequestMapping(value="toLoginForm")
	public String forwardLoginForm() {
		return "loginForm.html";
	}
	
	@RequestMapping(value="toRegisterForm")
	public String forwardRegisterForm() {
		return "registForm.html";
	}
	
	// 处理用户注册 register
	@RequestMapping(value="register", method=RequestMethod.POST)
	public void UserRegister(HttpServletResponse response,
			@Valid @ModelAttribute User user) throws IOException {
		response.setCharacterEncoding("utf-8");
		User userHad = userService.selectSimpleUser(user);
		//当查询不到用户的时候 说明这个用户名不存在 就允许用这个对象进行注册
		if (userHad == null) {
			User newUser = new User(user.getName(), user.getPassword());
			newUser.setHead("default.jpg");
			userService.addUser(newUser);
			response.getWriter().print("{\"result\": \"success\", \"info\" : \"注册成功\"}");
			//注册成功之后跳转到登录界面
			//return "loginForm.html";
		} else {
			//注册失败 跳转到注册界面
			//return "registForm.html";
			response.getWriter().print("{\"result\": \"false\",\"info\" : \"该用户名已存在\"}");
		}
	}
	
	//处理 login 
	@RequestMapping(value="login", method=RequestMethod.POST)
	public void UserLogin(HttpServletRequest request, HttpServletResponse response, 
			@Valid @ModelAttribute User user) throws IOException {
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		//设置最大发呆时长 10分钟
		session.setMaxInactiveInterval(600);
		
		//System.out.println(user);
		User userHad = userService.selectUser(user);
		if (userHad == null) {
			response.getWriter().print("{\"result\": \"false\",\"info\" : \"用户名和密码不匹配\"}");
		} else {
			session.setAttribute("user", userHad);
			response.getWriter().print("{\"result\": \"success\", \"info\" : \"登录成功\"}");
		}
	}
}
