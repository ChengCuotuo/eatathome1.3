package nianzuochen.eatathome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitHomePage {
	//��ʼ����ҳ��
	@RequestMapping(value="init")
	public String initHomePage() {
		
		//���ص��� menu.jsp
		return "menu.html";
	}
}
