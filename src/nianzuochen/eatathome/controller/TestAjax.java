package nianzuochen.eatathome.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.CategotyService;
import nianzuochen.mybatis.domain.Categoty;

@Controller
public class TestAjax {
	private CategotyService cs = new CategotyService();
	
	//��Ӧǰ̨����͵����󷵻���͵Ļ�����Ϣ
	@ResponseBody
	@RequestMapping(value="easyui_pages/pages/zc1")
	public String getInfo(HttpServletRequest request,String loginName) {
		//��һ�����������Ĳ������� zc1
		Categoty categoty = cs.selectCategotyById("zc");
		Map<String,String> map = new HashMap<String,String>();
		if (categoty != null) {
			map.put("id", categoty.getId());
			map.put("name", categoty.getName());
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
		return json;
	}
}
