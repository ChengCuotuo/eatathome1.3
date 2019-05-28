package nianzuochen.eatathome.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import nianzuochen.eatathome.service.UserService;
import nianzuochen.mybatis.domain.User;

//�����û���Ϣ���޸�
@Controller
public class Information {
	private UserService us = new UserService();
	
	//��ת���û���Ϣ�޸ĵ�ҳ�� userinfo
	@RequestMapping(value="userinfo")
	public String touUserinfoPage () {
		return "changeinfo.html";
	} 
	
	@RequestMapping(value="changehead", method=RequestMethod.POST)
	public String userinfo(HttpServletRequest request,
			@RequestParam("photo") MultipartFile file) {
		//�ṩ�û�����Ϣ �����û��޸�ͷ�� 
		// �û�ͷ�������ϴ�֮����Ҫ�޸����ݿ��е��û��� head ���Ե�ֵΪ�û���
		//�����û������޸�����
		//�����޸�������Ҫ��������룬����֮������޸�
		//����ȡ����������Ϣ��ͼƬ�洢����
		String filePath = request.getServletContext().getRealPath("/easyui_pages/pages/images/user/");
		//�洢���ļ������� ��ǰ�û��� + ��ǰʱ��ĺ��� + �ϴ����ļ��ĺ�׺��
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userName = user.getName();
		String fileName = file.getOriginalFilename();	//��ȡ�ļ���ԭ����
		//ʹ���û���������Ϊͷ������ƣ���׺��Ϊ�ļ���ԭ��׺��
		fileName = userName + fileName.substring(fileName.indexOf("."));
		File storePath = new File(filePath, fileName);
		if (storePath.getParentFile().exists()) {
			try {
				//D:\apache-tomcat-8.5.35\wtpwebapps\eatathome1.3\easyui_pages\pages\images//user			
				//���ϴ����ļ��洢����session�л�ȡ�����͵�ǰ�û�����Ϣ�ϴ������ݿ�
				file.transferTo(new File(filePath + fileName));
				//�޸����ݿ��е���Ϣ��Ȼ���޸� session �е���Ϣ
				if (user.getHead().equals("default.jpg")) {
					user.setHead(fileName);
					session.setAttribute("user", user);
					us.updateUser(user);
				}
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			//System.out.println(filePath + fileName);
			System.out.println("notFound");
		}	
		return "changeinfo.html";
	}
	
	//�޸����� ���ݵĲ����� oldpass �� pass
	@RequestMapping(value="changepass", method=RequestMethod.POST)
	public void changePass(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String oldpass = request.getParameter("oldpass");
		String pass = request.getParameter("pass");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user.getPassword().equals(oldpass)) {
			user.setPassword(pass);
			us.updateUser(user);
			response.getWriter().print("{\"result\":\"success\"}");
		} else {
			response.getWriter().print("{\"result\":\"�������������\"}");
		}
	}
}
