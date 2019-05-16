package nianzuochen.eatathome.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import nianzuochen.eatathome.service.CommonService;
import nianzuochen.eatathome.service.DynamicService;
import nianzuochen.eatathome.service.UserService;
import nianzuochen.mybatis.domain.Common;
import nianzuochen.mybatis.domain.Dynamic;
import nianzuochen.mybatis.domain.User;

/*
 * �û�������¼/ע������󣬽���ת����¼/ע��Ľ���
 * �����û���ϣ����¼����ע�ᣬ��ʱ���ø���һ���û��࣬��¼�û�����Ϣ
 * ���һ��model������ǰ̨�����ύ�û�������������У��
 * 
 * 2018-12-18:
 * 		bug:����е�����ʹ�õ���ȫ�ֱ���������ÿ��ϵͳֻ������һ���û���¼��
 * 		�޸ĵķ����������иĳ�session�����ڵ���Ϣ��ÿ���û���¼֮�󶼻�ֵ�һ��session���Ự�����Ͳ��ụ�������
 * 		����Ҫ�޸���ص���ʾ�û���Ϣ��ҳ�棬�û�����ϢҪ��session�л�ȡ������ҳ��ת��������ܴ���bug���Ȳ��Բ��ܵõ�
 * 		���Ի��Ǵ��󣬶�ҳ���¼��ʱ�򻹲�û��ʵ�ֶ��û���¼��Ӧ�������жϵ�¼��ҳ�������
 * 		register_loginForm��������⣬���û�û����ת����¼ע��ҳ�棬Ҳ����hasLogined����ʹ��
 * 		
 * 		�����쳣### Error querying database. Cause: org.apache.ibatis.executor.ExecutorException: Executor was clos
 *		�������ִ�����Ҫ�����ǵ���һ�������ʱ���ȡ��sqlSession��������ֻ����һ�Σ�һ���رգ�����ͻᱨ��
 *		��service��ÿ�������е���˽�г�Ա��newһ�ξͺ���
 *		�����������������ɹ��ˡ�
 * */

@Controller
public class RegisterLoginController {
	//��ʱ���û���������'���û���¼����Ϣ��������û����в���
	//2018-12-18�ĳɴ洢��session�����е�user
	//public static User recordUser = new User();
	//�ṩuser���е���ع���
	private static UserService userService = new UserService();
	//�ṩdynamic���е���ع���
	private static DynamicService dynamicService = new DynamicService();
	//�ṩ�������۵���ع���
	private static CommonService commonService = new CommonService();
	//�ж��Ƿ��Ѿ���¼
	//2018-12-18 ���û���¼��ʱ��ʹ��һ��ȫ�ֱ����ж��Ƿ��¼�Ǵ����
	//private static boolean hasLogined = false;
	//��ϣ��������¼ÿ�θ��º��³��ֵĶ�̬��key�Ƕ�̬��idֵ��value��ʼ��Ϊfalse
	//���û����������֮��ͱ��true����ʾ�Ѿ��Դʶ�̬���������ۣ��ٴ����۽������޸����ݿ�Ĵ洢
	//��ʼ����λ�����ٵ�¼�ɹ�ʱ���һ�ν�ǰ20��̬��ӵ�request�У�֮������ٵ�¼��ÿ�ν���
	//dynamicForma����ʱ��
	public static HashMap<Integer, Boolean> recordEvaluate = 
			new HashMap<>(20); 
	//�����洢�û��ϴ��Ķ�̬����Ƭ����������������̬��ʱ��ֻ�ὲ���һ����Ƭ�ϴ���ʣ�µĽ������
	//�Է�ֹ���������ļ�
	private static ArrayList<String> dynamicPhotoPath = new ArrayList<>();
	
	//ת������
	@RequestMapping(value="substance/{formName}", method=RequestMethod.POST)
	public String toRregister_loginForm(HttpServletRequest request,
			@PathVariable String formName, Model model) {
		//��Ҫ�ж�register_loginForm��������ʱ���Ƿ��Ѿ���¼������Ѿ���¼���Ǿ�ֱ����ת��dynamicForm����
		//����model�����recordUser����
		HttpSession session = request.getSession();
		
		if (formName.equals("register_loginForm")) {
			User user = (User)session.getAttribute("user");
			//user������˵����ʱû���û���¼
			if (user != null) {
				//2018-12-18�û���ȡ���ĳ�session.getAttribute("user");����ȡuser���󣬶�����ÿ��������ӵ�request��
				//model.addAttribute("user", RegisterLoginController.recordUser);
				//��֤ÿ�ν����ʱ�򶼸�������
				DynamicService dynamicService = new DynamicService();
				List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
				refreshRecordEvaluate(dynamics);
				//��model�����dynamics
				model.addAttribute("dynamics", dynamics);
				return "dynamicForm";
			} else {
				//2018-12-18Ϊ��˵��û���û���¼��������ת���û���¼ҳ��
				User user2 = new User();
				model.addAttribute("user", user2);
				return "register_loginForm";
			}
		}
//������ֱ����ת���������������ص�bug���������
		return formName;
	}
	
	//��ǰ��¼�� �û��Զ�̬������
	@RequestMapping(value="substance/evaluate")
	public String evaluate(HttpServletRequest request, Model model){
		String goodOrBad = request.getParameter("button");
		int dynamicId = Integer.parseInt(request.getParameter("dynamicId"));
		//�ж�recordEvaluate�е�dynamicId��Ӧ��ֵ�Ƿ�Ϊfalse
		//��false�Ļ��ж�����Ǻ������ǲ������޸����ݿ�
		if(!recordEvaluate.get(dynamicId)) {
			int upCount = Integer.parseInt(request.getParameter("upCount"));
			int downCount = Integer.parseInt(request.getParameter("downCount"));
			recordEvaluate.replace(dynamicId, false, true);
			if(goodOrBad.equals("good")) {
				upCount++;
				dynamicService.updateDynamicUpCount(new Dynamic(dynamicId, upCount, downCount));
			} else if (goodOrBad.equals("bad")){
				downCount++;
				dynamicService.updateDynamicDownCount(new Dynamic(dynamicId, upCount, downCount));
			}
		}
		//2018-12-18���ﲻ����ӣ���̬����Ӧ�ô�session�л�ȡ�û���Ϣ
		//model.addAttribute("user", RegisterLoginController.recordUser);
//		//��֤ÿ�ν����ʱ�򶼸�������
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
		refreshRecordEvaluate(dynamics);
		//��model�����dynamics
		model.addAttribute("dynamics", dynamics);
		return "dynamicForm";
	}
	//ˢ�� �����жϹ�ϣ���е������Ƿ�����µĶ�̬�� ��������ھ�Ҫɾ��
	private static void refreshRecordEvaluate(final List<Dynamic> dynamics) {
		List<Integer> dynamicsId = new ArrayList<>();
		for(Dynamic dyn : dynamics) {
			dynamicsId.add(dyn.getId());
		}
		//������ϴ���Ƚ��͵���Ϣɾ�� 
		Set<Integer> keySet = recordEvaluate.keySet();
		for (Integer id : keySet) {
			if(!dynamicsId.contains(id)) {
				recordEvaluate.remove(id);
			}
		}
		//�ٽ��µ���Ϣд��
		for (Dynamic dyn : dynamics) {
			if(!recordEvaluate.containsKey(dyn.getId())) {
				recordEvaluate.put(dyn.getId(), false);
			}
		}
	}
	//�����û�������
	@RequestMapping(value="substance/mycom", method=RequestMethod.POST)
	public String common(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String coment = request.getParameter("comment").trim();
		int dynamicId = Integer.parseInt(request.getParameter("dynamicId"));
		//�������,2018-12-18�û���id�ǵ�ǰ�û���id
		User user = (User)session.getAttribute("user");
		commonService.addCommon(new Common(dynamicId, user.getId(), coment));
		
		//2018-12-16����Ҫ����û���Ϣ���û���Ϣ�洢��session��
		//model.addAttribute("user", RegisterLoginController.recordUser);
		//��֤ÿ�ν����ʱ�򶼸�������
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
//		for (int i = 0; i < dynamics.size(); i++) {
//			List<Common> commons = dynamics.get(i).getCommons();
//			for (Common com : commons) {
//				System.out.println(com.getComment());
//			}
//		}
		//��model�����dynamics
		model.addAttribute("dynamics", dynamics);
		return "dynamicForm";
	}
	/*
	 * �ж��û��ǵ�¼����ע��
	 * ��ע��Ļ���Ҫ�����ݿ��������һ���û�
	 * �ǵ�¼�Ļ���Ҫ�������ݿ��в��� �õ��û���id��������Ϣ
	 * 
	 * 2018-12-18
	 * Ϊ��ʵ�ֶ��û���¼�����û���¼�ɹ�֮���û�����ϢӦ���Ǵ洢��session��
	 * */
	@RequestMapping(value="substance/login", method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request,
			@Valid @ModelAttribute User user,
			Errors errors, 
			Model model) {
		HttpSession session = request.getSession();
		//��ȡ��������������λ�õ�·�� ����Ǵ���Ŀ������·��
		String path = request.getServletContext().getRealPath("/images");
		//String path = "/eatathome1.1/images/";
		String info = request.getParameter("submit");
		try {
			if (info.equals("ע��")) {
				return "registerForm";
			}
			if(errors.hasErrors()) {
				return "register_loginForm";
			}
			if (info.equals("��¼")) {
//�����˺ܶ�ζ��ǳ���ԭ����User���е�toString�������dynamic��common������û���пվ�ֱ�������
//Ҳ������������recorderUser����
//				if (user == null) {
//					System.out.println("null");
//				} else {
//					user = new User(user.getName(), user.getPassword());
//					System.out.println(user);
//				}
				//��¼�ɹ�֮����û�����Ϣ������model�к�recordUser��
				//���ﲻ��ʹ��selectSimpleUser ��Ϊ�����������Ƶ�ʱ����Ϊ��ֱ��ʹ���û������в�ѯ��
				//ʹ����������ȫ����ѽ
				//2018-12-18�����ݿ��ж�ȡ�û���Ϣ��ȷ���û��Ƿ����
				User hadUser = userService.selectUser(user);
//System.out.println("recordUser: "  + recordUser);
				//��ѯ���û��� ˵���û���������ƥ�������¼
				//��Ҫ�����û�����Ϣ�ɲ�ѯ������ϸ��Ϣ ����ת����̬ҳ��
				//û�в�ѯ��˵���û������������ ��ת����¼����
				if (hadUser != null) {
					//�Ѿ���¼ �ж��Ƿ��¼Ӧ���ǿ�session���Ƿ����û�
					//hasLogined = true;
					//��ѯ�û���ͷ���Ƿ�����Ŀ������·���� 
					//û�еĻ���ʹ��·���µĳ�ʼͷ��
					//�����Ҫ�ֶ�����һ��·���µ�Ĭ��ͷ��
//System.out.println(path);
//E:\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\eatathome1.1\images\
					File file = new File(path + "/heads/",hadUser.getHead());
					if (file.exists()) {
						//recordUser.setHead(path + "/heads/" + recordUser.getHead());
						hadUser.setHead("/eatathome1.1/images/heads/" + hadUser.getHead());
					} else{
						hadUser.setHead("../images/heads/defaulthead.png");
					}
//System.out.println(recordUser.getHead());
					//2018-12-18���û�����Ϣ��ӵ�session��
					session.setAttribute("user", hadUser);
					//�õ��Ķ�̬��������е���Ƭ��λ����Ҫ����ʾ��ʱ��������ļ���·����
					session.setAttribute("path", "/eatathome1.1/images");
					//���û��ɹ���¼֮����Ҫ��dynamicForm�е���Ϣ��ѯ����
					//��Ȼҳ����ϢҲ�ǰ��ղ�ѯ���������ݽ�����ʾ��
					DynamicService dynamicService = new DynamicService();
					List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
					initRecordEvaluate(dynamics);
					//��model�����dynamics
					model.addAttribute("dynamics", dynamics);
					return "dynamicForm";
				} else {
//System.out.println("�û��������벻ƥ��");
					model.addAttribute("message", "�û��������벻ƥ��");
					return "register_loginForm";
				}
			} else {
System.out.println("�Ȳ��ǵ�¼��Ҳ����ע�ᣬ�������");
				return "register_loginForm";
			}
		} catch (IllegalStateException ex) {
			ex.printStackTrace();
			return "register_loginForm";
		} 
	}
	//�û�ע��
	@RequestMapping(value="substance/register", method=RequestMethod.POST)
	public String UserRegister(HttpServletRequest request,
			@Valid @ModelAttribute User user,
			Errors errors, 
			Model model) {
		//ע��׶β��������û�����Ϣ��model��
		//2018-12-18�û��������ݿ����Ƿ��ظ�
		User userHad = userService.selectSimpleUser(user);
//System.out.println("recordUser: " + recordUser);
		//����ѯ�����û���ʱ�� ˵������û��������� ������������������ע��
		if (userHad == null) {
			userService.addUser(new User(user.getName(), user.getPassword()));
			//ע��ɹ�֮����ת����¼����
			model.addAttribute("message", "ע��ɹ������¼ ");
			return "register_loginForm";
		} else {
			//ע��ʧ�� ��ת��ע����� �û�����ѡ������ע�������鿴����
			model.addAttribute("message", "�û����Ѵ���");
			return "registerForm";
		}
	}
	//��ʼ��recordEvaluate
	private static void initRecordEvaluate(final List<Dynamic> dynamics) {
		for (int i = 0; i < dynamics.size(); i++) {
			recordEvaluate.put(dynamics.get(i).getId(), false);
		}
	}
	
	//�ϴ���̬����Ƭ
	//ʹ���û������� ��ǰʱ��ĺ���ֵ �ļ��ĺ�׺��
	@RequestMapping(value="substance/uploadDynamicPhoto", method=RequestMethod.POST)
	public String upload(HttpServletRequest request,
			@RequestParam("file") MultipartFile file,
			Model model) {
		//2018-12-18�û�����Ϣ�Ѿ��洢��session�в���Ҫrequest������ˣ�ֱ�Ӷ�ȡ�ͺ���
		//model.addAttribute("user", recordUser);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		//����ļ���Ϊ�գ�д���ϴ�·��
		if(!file.isEmpty()) {
			//����ȷ���ļ��ĸ���·��
			String path = request.getServletContext().getRealPath("/images/dynamics/");
			//��ȡ�ϴ��ļ���ԭ������Ҫ����������û��ĺ�׺��
			String filename = file.getOriginalFilename();
			//��ȡ�û���
			String username = user.getName();
			filename = username + System.currentTimeMillis() + 
					filename.substring(filename.indexOf("."));
			File filepath = new File(path, filename);
			//�ж�·���Ƿ���ڣ���������ھʹ���һ��
			if(!filepath.getParentFile().exists()) {
				filepath.mkdirs();
			}
			//���ļ�������һ��Ŀ���ļ���
			try {
				file.transferTo(new File(path + filename));
				model.addAttribute("dynamicImage", "/eatathome1.1/images/dynamics/" +
						filename);
				//ÿ���ύ�Ķ�̬��Ƭ��·�����ᱻ�洢�����������ȷ���ϴ�֮�������һ����Ƭ��Ϊ
				//��̬��Ƭ��ӣ������Ķ��ᱻɾ��
				dynamicPhotoPath.add(filename);
			}catch (IOException ex) {
				ex.printStackTrace();
				return "errorForm";
			}
			//��������ת֮����ʾ�Ľ��沢û�и����û�����Ϣ
			return "releaseDynamicForm";
		}
		else
			return "errorForm";
	}
	
	//�û�������̬
	@RequestMapping(value="substance/releaseDynamic", method=RequestMethod.POST)
	public String releaseDynamic(HttpServletRequest request, Model model) {
		
		String describe = request.getParameter("describe").trim(); 
		//2018-12-18�û��Ѿ��洢��session����ֱ�ӻ�ȡ�ͺ���
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Dynamic dynamic = new Dynamic(user.getId(), describe, 
				dynamicPhotoPath.get(dynamicPhotoPath.size() - 1), 0, 0);
		//��ɾ������ ����Ƭ���������̬��Ƭ�ļ�¼
		String path = request.getServletContext().getRealPath("/images/dynamics/");
		File f = null;
		//���ϣ����һ����Ϊ��̬��Ƭ��Ȼ�ǲ���ɾ����
		for(int i = 0; i < dynamicPhotoPath.size() - 1; i++) {
			f = new File(path + dynamicPhotoPath.get(i));
			f.delete();
		}
		dynamicPhotoPath.clear();
		dynamicService.addDynamic(dynamic);
		//2018-12-18�û��Ѿ��洢��session������û�ж��û�����Ϣ�����޸ģ�����Ҫ��������û���Ϣ
		//model.addAttribute("user", recordUser);
		
		//���·�����̬��ǰ20��
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
		refreshRecordEvaluate(dynamics);
		model.addAttribute("dynamics", dynamics);
		return "dynamicForm";
	}
	//�û��ύ�����޸��û���Ϣ�Ľ���
	@RequestMapping(value="substance/changeInfoForm", method=RequestMethod.POST)
	public String changeInfo(HttpServletRequest request, Model model) {
		//2018-12-18�û��޸���Ϣҳ�棬��session�л�ȡ�û���Ϣ�����޸�֮����Ҫ������ӻ�session
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		//2018-12-18û�ж��û���Ϣ�����޸�
		//model.addAttribute("user", recordUser);
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectDynamicByUserId(user.getId());
		model.addAttribute("dynamics", dynamics);
		return "changeInfoForm";
	}
	//�����û��ύ����ͷ��
	@RequestMapping(value="substance/uploadHead", method=RequestMethod.POST)
	public String uploadHead(HttpServletRequest request,
			@RequestParam("file") MultipartFile file,
			Model model) {
		//2018-12-18�û��޸���Ϣҳ�棬��session�л�ȡ�û���Ϣ�����޸�֮����Ҫ������ӻ�session
		//model.addAttribute("user", recordUser);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String headName = null;
		//����ļ���Ϊ�գ�д���ϴ�·��
		if(!file.isEmpty()) {
			//����ȷ���ļ��ĸ���·��
			String path = request.getServletContext().getRealPath("/images/heads/");
			//��ȡ�ϴ��ļ���ԭ������Ҫ����������û��ĺ�׺��
			String filename = file.getOriginalFilename();
			//��ȡ�û���
			String username = user.getName();

			headName = username + filename.substring(filename.lastIndexOf("."));
			user.setHead(headName);
			File filepath = new File(path, headName);
			//�ж�·���Ƿ���ڣ���������ھʹ���һ��
			if(!filepath.getParentFile().exists()) {
				filepath.mkdirs();
			}
			//���ļ�������һ��Ŀ���ļ���
			try {
				file.transferTo(new File(path + headName));
			}catch (IOException ex) {
				ex.printStackTrace();
				return "errorForm";
			}
		}
		//�ύ֮���û�ͷ�����ڵ�·��Ҫ�޸�
		user.setHead("/eatathome1.1/images/heads/" + headName);
		//session���Ѿ�����user�в������
		//model.addAttribute("user", recordUser);
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectDynamicByUserId(user.getId());
		model.addAttribute("dynamics", dynamics);
		
		return "changeInfoForm";
	}
	
	//�޸��û���������Ϣ
	@RequestMapping(value="substance/uploadInfo", method=RequestMethod.POST)
	public String uploadInfo(HttpServletRequest request, Model model) {
		//2018-12-18�û��޸���Ϣҳ�棬��session�л�ȡ�û���Ϣ�����޸�֮����Ҫ������ӻ�session
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		boolean change = true;
		String agestr = request.getParameter("age").trim();
		String gender = request.getParameter("gender").trim();
		String signature = request.getParameter("signature").trim();
		String password1 = request.getParameter("password1").trim();
		String password2 = request.getParameter("password2").trim();
		
		if(agestr.equals("") || agestr == null) {
			agestr = user.getAge() + "";
		} 
		int age = 0;
		try {
			age = Integer.parseInt(agestr);
			if(age < 0 || age > 120) {
				model.addAttribute("warning", "���䷶Χ����");
				change = false;
			} else {
				user.setAge(age);
			}
		} catch(Exception ex) {
			model.addAttribute("warning", "�������������");
			change = false;
		}
		
		
		if (gender.equals("") || gender == null) {
			gender = user.getGender();
		} else {
			if (!gender.equals("man") && !gender.equals("woman")) {
				model.addAttribute("warning", "�Ա�ֻ����man��woman");
				change = false;
			} else {
				user.setGender(gender);
			}
		}
		
		if (signature.equals("") || signature == null) {
			signature = user.getSignature();
		} else {
			user.setSignatory(signature);
		}
		
		if (!password1.equals(password2)) {
			model.addAttribute("warning", "�������벻��ͬ");
			change = false;
		} else {
			if (password1 == null || password1.equals("")) {
				password1 = user.getPassword();
			}
		}
		
		int index = user.getHead().lastIndexOf("/");
		if (change) {
			//��ʱ�����û� �����޸����ݿ��е��û���Ϣ
			User newUser = new User(password1, age, gender,
					user.getHead().substring(index + 1), signature, user.getId());
			userService = new UserService();
			userService.updateUser(newUser);
		} 
		//2018-12-18��session�������user��Ϣ��Ҳ�����޸�ԭ����user��Ϣ
		//model.addAttribute("user", recordUser);
		session.setAttribute("user", user);
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
		model.addAttribute("dynamics", dynamics);
		
		if (change) {
			return "dynamicForm";
		} else {
			return "changeInfoForm";
		}
	}
}
