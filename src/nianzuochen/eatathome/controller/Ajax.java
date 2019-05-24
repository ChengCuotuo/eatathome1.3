package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.MenuService;
import nianzuochen.mybatis.domain.Menu;
import nianzuochen.mybatis.domain.Practice;
import nianzuochen.mybatis.domain.User;

@Controller
public class Ajax {
	private MenuService ms = new MenuService();
	//��Ӧǰ̨����͵����󷵻���͵Ļ�����Ϣ
	//��ѯ��Ʒ�������Ϣ ��Ʒ�������ɵĳ������� ��һ���ַ���
	@RequestMapping(value="menus/{formName}")
	public void getInfo(HttpServletResponse response, @PathVariable String formName) throws IOException {
		//�����ַ�����
		response.setContentType("text/html; charset=utf-8");
		ArrayList<Menu> menus = (ArrayList<Menu>)ms.selectSameType(formName);
		StringBuilder info = new StringBuilder();
		//<p><a href="practice/zxwc1">��������</a></p>
		for (Menu m : menus) {
			info.append("<p><a href=\"practice/" +m.getId() + "\">" + m.getName() + "</a></p>");
		}
		//System.out.println(info.toString());
		response.getWriter().print(info.toString());
	}
	
	//��ȡĳ����Ʒ������
	@RequestMapping(value="practice/{formName}")
	public void getPractices(HttpServletResponse response, @PathVariable String formName) {
		//�����ַ�����
		response.setCharacterEncoding("utf-8");
		//��ȡ�����ݸ�ʽΪ json �ȽϺô���
		Menu menu = ms.selectMenu(formName);
		try {
			//com.fasterxml.jackson.databind.JsonMappingException: No serializer found for class 
			//���ִ��쳣����Ϊ menu ���е� practices��һ�Զ�Ĺ�ϵ��ʹ���������� fetchType="lazy"
			//������Ϣ�޷����� �ĳ� fetchType="eager"
			ObjectMapper mapper = new ObjectMapper();
			String info = mapper.writeValueAsString(menu);
			//System.out.println(info);
			//���÷��ص���������
			response.setContentType("text/javascript");
	        response.getWriter().print(info);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping(value="tomenus{formName}")
	public void toMenuForm(HttpServletResponse response, @PathVariable String formName) throws IOException {
		//System.out.println("tomenus");
		//�����ַ�����
		response.setContentType("text/html; charset=utf-8");
		ArrayList<Menu> menus = (ArrayList<Menu>)ms.selectSameType(formName);
		StringBuilder info = new StringBuilder();
		//<p><a href="practice/zxwc1">��������</a></p>
		for (Menu m : menus) {
			info.append("<p><a href=\"toMenuPractice/" +m.getId() + "\">" + m.getName() + "</a></p>");
		}
		//System.out.println(info.toString());
		response.getWriter().print(info.toString());
	}
	
	//��ȡĳ����Ʒ������
	@RequestMapping(value="toMenuPractice/{formName}")
	public String toMenuPractice(HttpServletResponse response) {
		return "menu.html";
	}
	
	//�û���¼�ɹ����붯̬ҳ��
	@RequestMapping(value="toDynamicForm")
	public String toDynamic( HttpServletRequest request, HttpServletResponse response) throws IOException {
		//��¼�ɹ�֮�󷵻ص�����Ӧ����ǰ 20 ����̬ �û���ͷ�� ��������Ϣ
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user == null) {
			return "loginForm.html";
		} else {
			//���ص��� menu.jsp 
			return "dynamicForm.html";
		}
	}
	
	//menu ��Ϣ����
	@RequestMapping(value="getuserinfo")
	public void updateUserInfo(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		StringBuilder info = new StringBuilder();
		if (user == null) {
			info.append("{\"result\": \"null\"");
		} else {
			info.append("{\"result\":\"has\", \"userName\":\"" + user.getName() + "\", \"img\":\"" + user.getHead() + "\" }");
			//System.out.println(info.toString());
			response.getWriter().print(info.toString());
		}
	}
}
