package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.CategotyService;
import nianzuochen.eatathome.service.MenuService;
import nianzuochen.mybatis.domain.Categoty;
import nianzuochen.mybatis.domain.Menu;

@Controller
public class TestAjax {
	private CategotyService cs = new CategotyService();
	private MenuService ms = new MenuService();
	//��Ӧǰ̨����͵����󷵻���͵Ļ�����Ϣ
	@ResponseBody
	@RequestMapping(value="menu/{formName}")
	public void getInfo(HttpServletResponse response, @PathVariable String formName) {
		response.setContentType("text/html; charset=utf-8");
		//��һ�����������Ĳ������� zc1
		Menu menu = ms.selectMenu(formName);
		Map<String,String> map = new HashMap<String,String>();
		if (menu != null) {
			map.put("id", menu.getId());
			map.put("name", menu.getName());
			map.put("img", menu.getImg());
			map.put("content", menu.getContent());
			map.put("practice", menu.getPractices().toString());
		}
		//��Ҫʹ�� jackjson ������(map��������)����� json ��ʽ��������
		String json = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(map);
			System.out.println(json);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
	
		System.out.println(map);
		try {
			//�� json ���������ݸ�ǰ̨
			response.getWriter().print(json);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
