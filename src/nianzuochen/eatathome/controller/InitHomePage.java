package nianzuochen.eatathome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitHomePage {
	//初始化主页面
	@RequestMapping(value="init")
	public String initHomePage() {
		
		//返回的是 menu.jsp
		return "WebContent/easyui_pages/pages/enum.html";
	}
}
