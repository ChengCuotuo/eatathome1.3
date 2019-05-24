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
	
	//�����¼��ע��ҳ���ת��
	@RequestMapping(value="toLoginForm")
	public String forwardLoginForm() {
		return "loginForm.html";
	}
	
	// �����û�ע�� register
	@RequestMapping(value="register", method=RequestMethod.POST)
	public void UserRegister(HttpServletResponse response,
			@Valid @ModelAttribute User user) throws IOException {
		response.setCharacterEncoding("utf-8");
		User userHad = userService.selectSimpleUser(user);
		//����ѯ�����û���ʱ�� ˵������û��������� ������������������ע��
		if (userHad == null) {
			userService.addUser(new User(user.getName(), user.getPassword()));
			response.getWriter().print("{\"result\": \"success\", \"info\" : \"ע��ɹ�\"}");
			//ע��ɹ�֮����ת����¼����
			//return "loginForm.html";
		} else {
			//ע��ʧ�� ��ת��ע�����
			//return "registForm.html";
			response.getWriter().print("{\"result\": \"false\",\"info\" : \"���û����Ѵ���\"}");
		}
	}
	
	//���� login 
	@RequestMapping(value="login", method=RequestMethod.POST)
	public void UserLogin(HttpServletRequest request, HttpServletResponse response, 
			@Valid @ModelAttribute User user) throws IOException {
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		//������󷢴�ʱ��
		session.setMaxInactiveInterval(60);
		
		//System.out.println(user);
		User userHad = userService.selectUser(user);
		if (userHad == null) {
			response.getWriter().print("{\"result\": \"false\",\"info\" : \"�û��������벻ƥ��\"}");
		} else {
			session.setAttribute("user", userHad);
			response.getWriter().print("{\"result\": \"success\", \"info\" : \"��¼�ɹ�\"}");
		}
	}
}
