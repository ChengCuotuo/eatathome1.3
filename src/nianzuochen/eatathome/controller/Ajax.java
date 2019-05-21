package nianzuochen.eatathome.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.MenuService;
import nianzuochen.mybatis.domain.Menu;
import nianzuochen.mybatis.domain.Practice;

@Controller
public class Ajax {
	private MenuService ms = new MenuService();
	//响应前台对早餐的请求返回早餐的基本信息
	//查询菜品分类的信息 菜品名称做成的超链接组 是一个字符串
	@RequestMapping(value="menus/{formName}")
	public void getInfo(HttpServletResponse response, @PathVariable String formName) throws IOException {
		//设置字符编码
		response.setCharacterEncoding("utf-8");
		ArrayList<Menu> menus = (ArrayList<Menu>)ms.selectSameType(formName);
		StringBuilder info = new StringBuilder();
		//<p><a href="practice/zxwc1">黄油曲奇</a></p>
		for (Menu m : menus) {
			info.append("<p><a href=\"practice/" +m.getId() + "\">" + m.getName() + "</a></p>");
		}
		//System.out.println(info.toString());
		response.getWriter().print(info.toString());
	}
	
	//获取某个菜品的做法
	@RequestMapping(value="practice/{formName}")
	public void getPractices(HttpServletResponse response, @PathVariable String formName) {
		//设置字符编码
		response.setCharacterEncoding("utf-8");
		//获取的数据格式为 json 比较好处理
		Menu menu = ms.selectMenu(formName);
		try {
			//com.fasterxml.jackson.databind.JsonMappingException: No serializer found for class 
			//出现此异常，因为 menu 类中的 practices是一对多的关系，使用了懒加载 fetchType="lazy"
			//导致信息无法加载 改成 fetchType="eager"
			ObjectMapper mapper = new ObjectMapper();
			String info = mapper.writeValueAsString(menu);
			//System.out.println(info);
			//设置返回的数据类型
			response.setContentType("text/javascript");
	        response.getWriter().print(info);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
