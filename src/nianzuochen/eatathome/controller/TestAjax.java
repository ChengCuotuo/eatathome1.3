package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	//响应前台对早餐的请求返回早餐的基本信息
	@ResponseBody
	@RequestMapping(value="easyui_pages/pages/zc1")
	public void getInfo(HttpServletResponse response, String loginName) {
		response.setContentType("text/html; charset=utf-8");
		//单一处理，传递来的参数就是 zc1
		Categoty categoty = cs.selectCategotyById("zc");
		Map<String,String> map = new HashMap<String,String>();
		if (categoty != null) {
			map.put("id", categoty.getId());
			map.put("name", categoty.getName());
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
			response.getWriter().print(json);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
