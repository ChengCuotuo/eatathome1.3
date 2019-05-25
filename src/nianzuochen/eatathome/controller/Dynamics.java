package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.DynamicService;
import nianzuochen.mybatis.domain.Dynamic;

@Controller
public class Dynamics {
	private DynamicService ds = new DynamicService();
	
	//刷新动态，显示前20条动态
	@RequestMapping(value="viewDynamics")
	public void viewDynamic(HttpServletResponse response)  {
		response.setContentType("text/html; charset=utf-8");
		List<Dynamic> dynamics = ds.selectTwentyDynamics();
		try {
			ObjectMapper mapper = new ObjectMapper();
			String info = mapper.writeValueAsString(dynamics);
			response.getWriter().print(info);
			//System.out.println(info);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	//发布动态
	@RequestMapping(value="releaseDynamic")
	public String releaseDynamic() {
		return "";
	}
}
