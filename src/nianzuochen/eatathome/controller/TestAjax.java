package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import nianzuochen.mybatis.domain.Practice;

@Controller
public class TestAjax {
	private CategotyService cs = new CategotyService();
	private MenuService ms = new MenuService();
	//��Ӧǰ̨����͵����󷵻���͵Ļ�����Ϣ
	@ResponseBody
	@RequestMapping(value="menu/{formName}")
	public void getInfo(HttpServletResponse response, @PathVariable String formName) {
		response.setContentType("text/html; charset=utf-8");
		ObjectMapper mapper = new ObjectMapper();
		//��һ�����������Ĳ������� zc1
		Menu menu = ms.selectMenu(formName);
		Map<String,String> map = new HashMap<String,String>();
		String practiceInfoJson = "";
		String practicesJson = "";
		try {
			if (menu != null) {
				map.put("id", menu.getId());
				map.put("name", menu.getName());
				map.put("img", menu.getImg());
				map.put("content", menu.getContent());
				//��Ҫʹ�� jackjson ������(map��������)����� json ��ʽ��������
				practiceInfoJson = mapper.writeValueAsString(map);
				ArrayList<Practice> practices = (ArrayList<Practice>)menu.getPractices();
//				for (Practice p : practices) {
//					practicesJson.append(mapper.writeValueAsString(p) + ", ");
//				}
				//practicesJson.append("{}}}");
				practicesJson = mapper.writeValueAsString(practices);
			}
			//�����ݷ�װ�� json ��ʽ�����ݸ�ǰ̨
			practiceInfoJson = practiceInfoJson.replace("}", ", \"practice\": " + practicesJson + "}");
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		try {
			//�� json ���������ݸ�ǰ̨
			response.getWriter().print(practiceInfoJson);
			//response.getWriter().print(practicesJson);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
