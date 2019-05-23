package nianzuochen.eatathome.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitHomePage {
	
	@RequestMapping(value="init")
	public String initHomePage(HttpServletResponse response) {
		//·µ»ØµÄÊÇ menu.jsp
		return "menu.html";
	}
}
