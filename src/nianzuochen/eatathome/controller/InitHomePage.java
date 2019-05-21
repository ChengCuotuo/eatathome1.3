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
	//初始化主页面
	@RequestMapping(value="initPage")
	public void initPage(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		//获取 jackjson 对象
		ObjectMapper mapper = new ObjectMapper();
		//查询表 categoty 中的数据
		ArrayList<Categoty> categoties = (ArrayList<Categoty>) cs.selectCategoties();
		StringBuilder json = new StringBuilder("[");
		try {
			//在初始化页面需要将数据库中菜品分类信息查询出来用来初始化 menu.html 页面
			//需要将得到的数据信息格式化
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
		//返回的是 menu.jsp
		return "menu.html";
	}
}
