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
	//响应前台对早餐的请求返回早餐的基本信息
	@ResponseBody
	@RequestMapping(value="easyui_pages/pages/{formName}")
	public void getInfo(HttpServletResponse response, @PathVariable String formName) {
		response.setContentType("text/html; charset=utf-8");
		//单一处理，传递来的参数就是 zc1
		Menu menu = ms.selectMenu(formName);
		Map<String,String> map = new HashMap<String,String>();
		if (menu != null) {
			map.put("id", menu.getId());
			map.put("name", menu.getName());
			map.put("img", menu.getImg());
			map.put("content", menu.getContent());
			map.put("practice", menu.getPractices().toString());
		}
		//需要使用 jackjson 将数据(map类型数据)打包成 json 格式的数据流
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
			//把 json 数据流传递给前台
			response.getWriter().print(json);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
