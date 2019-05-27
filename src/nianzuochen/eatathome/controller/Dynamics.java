package nianzuochen.eatathome.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.eatathome.service.CommonService;
import nianzuochen.eatathome.service.DynamicService;
import nianzuochen.mybatis.domain.Common;
import nianzuochen.mybatis.domain.Dynamic;
import nianzuochen.mybatis.domain.User;

@Controller
public class Dynamics {
	private DynamicService ds = new DynamicService();
	private CommonService cs = new CommonService();
	
	//ˢ�¶�̬����ʾǰ20����̬
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
	
	//�ϴ�ͼƬ��ͼƬ���� json ��ʽ�ϴ���
	@RequestMapping(value="releaseDynamic", method=RequestMethod.POST)
	public String releaseDynamic(HttpServletRequest request,
			@RequestParam("photo") MultipartFile file) throws InterruptedException {
		//����ȡ����������Ϣ��ͼƬ�洢����
		String filePath = request.getServletContext().getRealPath("/easyui_pages/pages/images/dynamic/");
		//�洢���ļ������� ��ǰ�û��� + ��ǰʱ��ĺ��� + �ϴ����ļ��ĺ�׺��
		HttpSession session = request.getSession();
		String userName = ((User)session.getAttribute("user")).getName();
		String fileName = file.getOriginalFilename();	//��ȡ�ļ���ԭ����
		fileName = userName + System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
		//System.out.println("filePath: " + filePath + ", fileName: " + fileName);
		File storePath = new File(filePath, fileName);
		//System.out.println(filePath + fileName);
		if (storePath.getParentFile().exists()) {
			try {
				//���ϴ����ļ��洢����session�л�ȡ�����͵�ǰ�û�����Ϣ�ϴ������ݿ�
				file.transferTo(new File(filePath + fileName));
				//����ͼƬ�Ѿ��ϴ����˷������� ����Ҳ�Ѿ��������� session �У����������ݿ���д�������Ϣ��
				User user = (User)session.getAttribute("user");
				String describe = (String)session.getAttribute("describe");
				Dynamic dynamic = new Dynamic(user.getId(), describe, fileName, 0, 0);
				ds.addDynamic(dynamic);
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			//System.out.println(filePath + fileName);
			System.out.println("notFound");
		}
		
		return "dynamicForm.html";
	}
	
	//���û��Զ�̬������������ session ��
	@RequestMapping(value="storeDescribe")
	public void storeDescribe(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String describe = request.getParameter("describe");
		if (describe != null) {
			session.setAttribute("describe", describe);
			response.getWriter().print("{\"result\" : \"success\"}");
		} else {
			response.getWriter().print("{\"result\" : \"false\"}");
		}
		
	}
	
	// mycom ��ǰ�û���ĳһ����̬������ ���ص���һ�� json ���ݣ���������۳ɹ����� result="success"
	@RequestMapping(value="mycom")
	public void mycom(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int dynamic_id = Integer.parseInt(request.getParameter("dynamic_id"));
		String comment = request.getParameter("comment");
		//�Ѿ���ȡ�˵�ǰ���û�����ǰ�û����۵Ķ�̬�� id �Ѿ��û����۵����ݣ����ڿ��Խ���Ϣ�洢�����ݿ���
		cs.addCommon(new Common(dynamic_id, user.getId(), comment));
		response.getWriter().print("{\"result\":\"success\", \"userName\": \"" + user.getName() + "\"}");
	}
	
	//��ʾ�û���Ϣ userinfo
	@RequestMapping(value="userinfo")
	public String userinfo() {
		//�ṩ�û�����Ϣ �����û��޸�ͷ��
		return "";
	}
	
	//ת���� history.html ҳ��
	@RequestMapping(value="history")
	public String history () {
		return "history.html";
	}
	
	//��ʾ�û�����ʷ��̬
	@RequestMapping(value="getHistory")
	public void history(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List<Dynamic> dynamics = ds.selectDynamicByUserId(user.getId());
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
	
}
