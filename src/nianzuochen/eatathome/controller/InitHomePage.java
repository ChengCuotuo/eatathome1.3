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
	@RequestMapping(value="init")
	public String initHomePage(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8"); 
		//获取 jackjson 对象
		ObjectMapper mapper = new ObjectMapper();
		//查询表 categoty 中的数据
		ArrayList<Categoty> categoties = (ArrayList<Categoty>) cs.selectCategoties();
		String json = "";
		try {
			//在初始化页面需要将数据库中菜品分类信息查询出来用来初始化 menu.html 页面
			json = mapper.writeValueAsString(categoties);
			response.getWriter().print(json);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//返回的是 menu.jsp
		return "menu.html";
	}
}
