package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User implements Serializable{
	private Integer id;				//用户id
	@NotBlank(message = "用户名不能为空")
	private String name;			//用户名
	@NotBlank(message = "密码不能为空")
	private String password;//密码
	@Min(value = 6)					//限制年龄范围在6到120之间
	private Integer age;				//用户年龄
	private String gender;			//用户性别，限制在man和woman之中
	private String head;			//头像地址
	@Length(min = 0, max = 150)		//限制个性签名的长度
	private String signature;		//个性签名
	private Integer delete;			//被删除的动态的条数
	private Timestamp createDate;	//用户创建时间
	private List<Dynamic> dynamics;	//用户的动态
	
	public List<Dynamic> getDynamics() {
		return dynamics;
	}

	public void setDynamics(List<Dynamic> dynamics) {
		this.dynamics = dynamics;
	}

	public User() {
		super();
	}
	
	//这个构造方法实在用户注册的时候使用的，用户创建时间是由数据库生成的
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.age = 18;
		this.gender = "man";
		this.head = this.name + ".jpg";
		this.signature = "再忙也要记得抽空回家吃饭";
		this.delete = 0;
	}

	public User(String name, String password, Integer age, String gender, 
			String head, String signature, Integer delete) {
		super();
		this.name = name;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.head = head;
		this.signature = signature;
		this.delete = delete;
	}
	
	//用户更新用户信息的构造方法
	public User(String password, Integer age, String gender, 
			String head, String signature, int id) {
		super();
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.head = head;
		this.signature = signature;
		this.id = id;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("User ->[" + this.name + ", " + this.age + ", " + 
				this.gender + ", " + this.signature + ", " + this.createDate + ", ");
		if (dynamics != null) {
			for (Dynamic dyn : dynamics) {
				result.append("\n\t Dynamic->[" + dyn.getDescribe() + ", " + dyn.getUser_id() +
						", " + dyn.getUpCount() + ", " + dyn.getDownCount() + ", ");
				if (dyn.getCommons() != null) {
					for (Common com : dyn.getCommons()) {
						result.append("\n\t\t Common->[" + com.getComment() + ", " + com.getDynamic_id() + "]");
					}
					result.append("\n\t\t]");
				}				
			}
		}
		result.append("\n\t]");
		return result.toString();
 	}
}
