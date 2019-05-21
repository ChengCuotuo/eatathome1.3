package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.CategotyService;
import nianzuochen.mybatis.domain.Categoty;

@Controller
public class InitHomePage {
	private CategotyService cs = new CategotyService();
	//��ʼ����ҳ��
	@RequestMapping(value="initPage")
	public void initPage(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		//��ȡ jackjson ����
		ObjectMapper mapper = new ObjectMapper();
		//��ѯ�� categoty �е�����
		ArrayList<Categoty> categoties = (ArrayList<Categoty>) cs.selectCategoties();
		StringBuilder json = new StringBuilder("[");
		try {
			//�ڳ�ʼ��ҳ����Ҫ�����ݿ��в�Ʒ������Ϣ��ѯ����������ʼ�� menu.html ҳ��
			//��Ҫ���õ���������Ϣ��ʽ��
			for (Categoty c : categoties) {
				String menuJson = mapper.writeValueAsString(c.getMenus());
				json.append("{\"id\": \"" + c.getId() + "\", \"name\": \"" + c.getName() + 
						"\", \"menus\":" + menuJson + "}, ");
			}
			json.append("{}]");
			//System.out.println(json.toString());
			response.getWriter().print(json.toString());
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//System.out.println(json);
	}
	
	@RequestMapping(value="init")
	public String initHomePage(HttpServletResponse response) {
		//���ص��� menu.jsp
		return "menu.html";
	}
}
