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
 * 用户发出登录/注册的请求，将跳转到登录/注册的界面
 * 无论用户是希望登录还是注册，此时都用该有一个用户类，记录用户的信息
 * 添加一个model对象，向前台界面提交用户对象，用于数据校验
 * 
 * 2018-12-18:
 * 		bug:编程中的数据使用的是全局变量，导致每次系统只能允许一个用户登录。
 * 		修改的方案，将进行改成session对象内的信息，每个用户登录之后都会分的一个session（会话），就不会互相干扰了
 * 		还需要修改相关的显示用户信息的页面，用户的信息要从session中获取，请求页面转发里面可能存在bug，等测试才能得到
 * 		测试还是错误，多页面登录的时候还并没有实现多用户登录，应该是在判断登录的页面出错了
 * 		register_loginForm请求出问题，新用户没有跳转到登录注册页面，也就是hasLogined不能使用
 * 		
 * 		出现异常### Error querying database. Cause: org.apache.ibatis.executor.ExecutorException: Executor was clos
 *		出现这种错误主要是我们调用一个对象的时候获取的sqlSession对象但是它只创建一次，一旦关闭，后面就会报错
 *		在service的每个方法中调用私有成员再new一次就好了
 *		哈哈哈哈哈哈哈，成功了。
 * */

@Controller
public class RegisterLoginController {
	//临时的用户，用来存'储用户登录的信息，方便对用户进行操作
	//2018-12-18改成存储再session对象中的user
	//public static User recordUser = new User();
	//提供user表中的相关功能
	private static UserService userService = new UserService();
	//提供dynamic表中的相关功能
	private static DynamicService dynamicService = new DynamicService();
	//提供发表评论的相关功能
	private static CommonService commonService = new CommonService();
	//判断是否已经登录
	//2018-12-18 多用户登录的时候，使用一个全局变量判断是否登录是错误的
	//private static boolean hasLogined = false;
	//哈希表用来记录每次更新后新出现的动态，key是动态的id值，value初始化为false
	//当用户差评或好评之后就变成true，表示已经对词动态进行了评价，再词评价将不会修改数据库的存储
	//初始化的位置是再登录成功时候第一次将前20动态添加到request中，之后就是再登录后每次进入
	//dynamicForma界面时候
	public static HashMap<Integer, Boolean> recordEvaluate = 
			new HashMap<>(20); 
	//用来存储用户上传的动态的照片，如果当点击发布动态的时候只会讲最后一张照片上传，剩下的将被清除
	//以防止出现垃圾文件
	private static ArrayList<String> dynamicPhotoPath = new ArrayList<>();
	
	//转发请求
	@RequestMapping(value="substance/{formName}", method=RequestMethod.POST)
	public String toRregister_loginForm(HttpServletRequest request,
			@PathVariable String formName, Model model) {
		//需要判断register_loginForm传过来的时候是否已经登录，如果已经登录了那就直接跳转到dynamicForm界面
		//并且model中添加recordUser对象
		HttpSession session = request.getSession();
		
		if (formName.equals("register_loginForm")) {
			User user = (User)session.getAttribute("user");
			//user不存在说明此时没有用户登录
			if (user != null) {
				//2018-12-18用户获取，改成session.getAttribute("user");来获取user对象，而不是每次请求都添加到request中
				//model.addAttribute("user", RegisterLoginController.recordUser);
				//保证每次进入的时候都更新内容
				DynamicService dynamicService = new DynamicService();
				List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
				refreshRecordEvaluate(dynamics);
				//在model中添加dynamics
				model.addAttribute("dynamics", dynamics);
				return "dynamicForm";
			} else {
				//2018-12-18为空说明没有用户登录，所以跳转到用户登录页面
				User user2 = new User();
				model.addAttribute("user", user2);
				return "register_loginForm";
			}
		}
//其他的直接跳转，觉得这里有隐藏的bug先做个标记
		return formName;
	}
	
	//当前登录的 用户对动态的评论
	@RequestMapping(value="substance/evaluate")
	public String evaluate(HttpServletRequest request, Model model){
		String goodOrBad = request.getParameter("button");
		int dynamicId = Integer.parseInt(request.getParameter("dynamicId"));
		//判断recordEvaluate中的dynamicId对应的值是否为false
		//是false的话判断如果是好评还是差评并修改数据库
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
		//2018-12-18这里不用添加，动态界面应该从session中获取用户信息
		//model.addAttribute("user", RegisterLoginController.recordUser);
//		//保证每次进入的时候都更新内容
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
		refreshRecordEvaluate(dynamics);
		//在model中添加dynamics
		model.addAttribute("dynamics", dynamics);
		return "dynamicForm";
	}
	//刷新 首先判断哈希表中的数据是否存在新的动态中 如果不存在就要删除
	private static void refreshRecordEvaluate(final List<Dynamic> dynamics) {
		List<Integer> dynamicsId = new ArrayList<>();
		for(Dynamic dyn : dynamics) {
			dynamicsId.add(dyn.getId());
		}
		//数据清洗，先将就的信息删除 
		Set<Integer> keySet = recordEvaluate.keySet();
		for (Integer id : keySet) {
			if(!dynamicsId.contains(id)) {
				recordEvaluate.remove(id);
			}
		}
		//再将新的信息写入
		for (Dynamic dyn : dynamics) {
			if(!recordEvaluate.containsKey(dyn.getId())) {
				recordEvaluate.put(dyn.getId(), false);
			}
		}
	}
	//处理用户的评论
	@RequestMapping(value="substance/mycom", method=RequestMethod.POST)
	public String common(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String coment = request.getParameter("comment").trim();
		int dynamicId = Integer.parseInt(request.getParameter("dynamicId"));
		//添加评论,2018-12-18用户的id是当前用户的id
		User user = (User)session.getAttribute("user");
		commonService.addCommon(new Common(dynamicId, user.getId(), coment));
		
		//2018-12-16不需要添加用户信息，用户信息存储再session中
		//model.addAttribute("user", RegisterLoginController.recordUser);
		//保证每次进入的时候都更新内容
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
//		for (int i = 0; i < dynamics.size(); i++) {
//			List<Common> commons = dynamics.get(i).getCommons();
//			for (Common com : commons) {
//				System.out.println(com.getComment());
//			}
//		}
		//在model中添加dynamics
		model.addAttribute("dynamics", dynamics);
		return "dynamicForm";
	}
	/*
	 * 判断用户是登录还是注册
	 * 是注册的话需要在数据库中新添加一个用户
	 * 是登录的话需要现在数据库中查找 得到用户的id等其他信息
	 * 
	 * 2018-12-18
	 * 为了实现多用户登录，当用户登录成功之后，用户的信息应该是存储在session中
	 * */
	@RequestMapping(value="substance/login", method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request,
			@Valid @ModelAttribute User user,
			Errors errors, 
			Model model) {
		HttpSession session = request.getSession();
		//获取服务器的上下文位置的路径 这个是此项目的运行路径
		String path = request.getServletContext().getRealPath("/images");
		//String path = "/eatathome1.1/images/";
		String info = request.getParameter("submit");
		try {
			if (info.equals("注册")) {
				return "registerForm";
			}
			if(errors.hasErrors()) {
				return "register_loginForm";
			}
			if (info.equals("登录")) {
//测试了很多次都是出错，原来是User类中的toString里面对于dynamic和common的内容没有判空就直接输出了
//也就是下面的输出recorderUser出错
//				if (user == null) {
//					System.out.println("null");
//				} else {
//					user = new User(user.getName(), user.getPassword());
//					System.out.println(user);
//				}
				//登录成功之后把用户的信息保存早model中和recordUser中
				//这里不能使用selectSimpleUser 因为这个方法在设计的时候是为了直接使用用户名进行查询的
				//使用在这里完全错误呀
				//2018-12-18从数据库中读取用户信息，确定用户是否存在
				User hadUser = userService.selectUser(user);
//System.out.println("recordUser: "  + recordUser);
				//查询到用户了 说明用户名和密码匹配允许登录
				//需要更新用户的信息成查询到的详细信息 并跳转到动态页面
				//没有查询到说明用户名或密码错误 跳转到登录界面
				if (hadUser != null) {
					//已经登录 判断是否登录应该是看session中是否有用户
					//hasLogined = true;
					//查询用户的头像是否在项目的运行路径下 
					//没有的话就使用路径下的初始头像
					//这个需要手动配置一下路径下的默认头像
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
					//2018-12-18将用户的信息添加到session中
					session.setAttribute("user", hadUser);
					//得到的动态里面的所有的照片的位置需要在显示的时候添加上文件的路径名
					session.setAttribute("path", "/eatathome1.1/images");
					//当用户成功登录之后需要把dynamicForm中的信息查询处理
					//当然页面信息也是按照查询出来的内容进行显示的
					DynamicService dynamicService = new DynamicService();
					List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
					initRecordEvaluate(dynamics);
					//在model中添加dynamics
					model.addAttribute("dynamics", dynamics);
					return "dynamicForm";
				} else {
//System.out.println("用户名和密码不匹配");
					model.addAttribute("message", "用户名或密码不匹配");
					return "register_loginForm";
				}
			} else {
System.out.println("既不是登录，也不是注册，你想干嘛");
				return "register_loginForm";
			}
		} catch (IllegalStateException ex) {
			ex.printStackTrace();
			return "register_loginForm";
		} 
	}
	//用户注册
	@RequestMapping(value="substance/register", method=RequestMethod.POST)
	public String UserRegister(HttpServletRequest request,
			@Valid @ModelAttribute User user,
			Errors errors, 
			Model model) {
		//注册阶段并不保留用户的信息到model中
		//2018-12-18用户名在数据库中是否重复
		User userHad = userService.selectSimpleUser(user);
//System.out.println("recordUser: " + recordUser);
		//当查询不到用户的时候 说明这个用户名不存在 就允许用这个对象进行注册
		if (userHad == null) {
			userService.addUser(new User(user.getName(), user.getPassword()));
			//注册成功之后跳转到登录界面
			model.addAttribute("message", "注册成功，请登录 ");
			return "register_loginForm";
		} else {
			//注册失败 跳转到注册界面 用户可以选择重新注册或继续查看菜谱
			model.addAttribute("message", "用户名已存在");
			return "registerForm";
		}
	}
	//初始化recordEvaluate
	private static void initRecordEvaluate(final List<Dynamic> dynamics) {
		for (int i = 0; i < dynamics.size(); i++) {
			recordEvaluate.put(dynamics.get(i).getId(), false);
		}
	}
	
	//上传动态的照片
	//使用用户名加上 当前时间的毫秒值 文件的后缀名
	@RequestMapping(value="substance/uploadDynamicPhoto", method=RequestMethod.POST)
	public String upload(HttpServletRequest request,
			@RequestParam("file") MultipartFile file,
			Model model) {
		//2018-12-18用户的信息已经存储在session中不需要request中添加了，直接读取就好了
		//model.addAttribute("user", recordUser);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		//如果文件不为空，写入上传路径
		if(!file.isEmpty()) {
			//首先确定文件的父类路径
			String path = request.getServletContext().getRealPath("/images/dynamics/");
			//获取上传文件的原名，主要是用来获得用户的后缀名
			String filename = file.getOriginalFilename();
			//获取用户名
			String username = user.getName();
			filename = username + System.currentTimeMillis() + 
					filename.substring(filename.indexOf("."));
			File filepath = new File(path, filename);
			//判断路径是否存在，如果不存在就创建一个
			if(!filepath.getParentFile().exists()) {
				filepath.mkdirs();
			}
			//将文件保存在一个目标文件中
			try {
				file.transferTo(new File(path + filename));
				model.addAttribute("dynamicImage", "/eatathome1.1/images/dynamics/" +
						filename);
				//每次提交的动态照片的路径都会被存储下来，当点击确认上传之后会把最后一张照片作为
				//动态照片添加，其他的都会被删除
				dynamicPhotoPath.add(filename);
			}catch (IOException ex) {
				ex.printStackTrace();
				return "errorForm";
			}
			//再重新跳转之后，显示的界面并没有更新用户的信息
			return "releaseDynamicForm";
		}
		else
			return "errorForm";
	}
	
	//用户发布动态
	@RequestMapping(value="substance/releaseDynamic", method=RequestMethod.POST)
	public String releaseDynamic(HttpServletRequest request, Model model) {
		
		String describe = request.getParameter("describe").trim(); 
		//2018-12-18用户已经存储在session中了直接获取就好了
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		Dynamic dynamic = new Dynamic(user.getId(), describe, 
				dynamicPhotoPath.get(dynamicPhotoPath.size() - 1), 0, 0);
		//先删除所有 的照片，再清除动态照片的记录
		String path = request.getServletContext().getRealPath("/images/dynamics/");
		File f = null;
		//智障，最后一个作为动态照片当然是不能删除的
		for(int i = 0; i < dynamicPhotoPath.size() - 1; i++) {
			f = new File(path + dynamicPhotoPath.get(i));
			f.delete();
		}
		dynamicPhotoPath.clear();
		dynamicService.addDynamic(dynamic);
		//2018-12-18用户已经存储在session中这里没有对用户的信息进行修改，不需要重新添加用户信息
		//model.addAttribute("user", recordUser);
		
		//重新发布动态的前20条
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectTwentyDynamics();
		refreshRecordEvaluate(dynamics);
		model.addAttribute("dynamics", dynamics);
		return "dynamicForm";
	}
	//用户提交请求修改用户信息的界面
	@RequestMapping(value="substance/changeInfoForm", method=RequestMethod.POST)
	public String changeInfo(HttpServletRequest request, Model model) {
		//2018-12-18用户修改信息页面，从session中获取用户信息进行修改之后需要重新添加回session
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		//2018-12-18没有对用户信息进行修改
		//model.addAttribute("user", recordUser);
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectDynamicByUserId(user.getId());
		model.addAttribute("dynamics", dynamics);
		return "changeInfoForm";
	}
	//更新用户提交的新头像
	@RequestMapping(value="substance/uploadHead", method=RequestMethod.POST)
	public String uploadHead(HttpServletRequest request,
			@RequestParam("file") MultipartFile file,
			Model model) {
		//2018-12-18用户修改信息页面，从session中获取用户信息进行修改之后需要重新添加回session
		//model.addAttribute("user", recordUser);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String headName = null;
		//如果文件不为空，写入上传路径
		if(!file.isEmpty()) {
			//首先确定文件的父类路径
			String path = request.getServletContext().getRealPath("/images/heads/");
			//获取上传文件的原名，主要是用来获得用户的后缀名
			String filename = file.getOriginalFilename();
			//获取用户名
			String username = user.getName();

			headName = username + filename.substring(filename.lastIndexOf("."));
			user.setHead(headName);
			File filepath = new File(path, headName);
			//判断路径是否存在，如果不存在就创建一个
			if(!filepath.getParentFile().exists()) {
				filepath.mkdirs();
			}
			//将文件保存在一个目标文件中
			try {
				file.transferTo(new File(path + headName));
			}catch (IOException ex) {
				ex.printStackTrace();
				return "errorForm";
			}
		}
		//提交之后用户头像所在的路径要修改
		user.setHead("/eatathome1.1/images/heads/" + headName);
		//session中已经存在user中不用添加
		//model.addAttribute("user", recordUser);
		DynamicService dynamicService = new DynamicService();
		List<Dynamic> dynamics = dynamicService.selectDynamicByUserId(user.getId());
		model.addAttribute("dynamics", dynamics);
		
		return "changeInfoForm";
	}
	
	//修改用户的其他信息
	@RequestMapping(value="substance/uploadInfo", method=RequestMethod.POST)
	public String uploadInfo(HttpServletRequest request, Model model) {
		//2018-12-18用户修改信息页面，从session中获取用户信息进行修改之后需要重新添加回session
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
				model.addAttribute("warning", "年龄范围出错");
				change = false;
			} else {
				user.setAge(age);
			}
		} catch(Exception ex) {
			model.addAttribute("warning", "年龄必须是整数");
			change = false;
		}
		
		
		if (gender.equals("") || gender == null) {
			gender = user.getGender();
		} else {
			if (!gender.equals("man") && !gender.equals("woman")) {
				model.addAttribute("warning", "性别只能是man或woman");
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
			model.addAttribute("warning", "两次密码不相同");
			change = false;
		} else {
			if (password1 == null || password1.equals("")) {
				password1 = user.getPassword();
			}
		}
		
		int index = user.getHead().lastIndexOf("/");
		if (change) {
			//临时的新用户 用来修改数据库中的用户信息
			User newUser = new User(password1, age, gender,
					user.getHead().substring(index + 1), signature, user.getId());
			userService = new UserService();
			userService.updateUser(newUser);
		} 
		//2018-12-18在session重新添加user信息，也就是修改原来的user信息
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
