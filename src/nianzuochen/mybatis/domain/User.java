package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User implements Serializable{
	private Integer id;				//�û�id
	@NotBlank(message = "�û�������Ϊ��")
	private String name;			//�û���
	@NotBlank(message = "���벻��Ϊ��")
	private String password;//����
	@Min(value = 6)					//�������䷶Χ��6��120֮��
	private Integer age;				//�û�����
	private String gender;			//�û��Ա�������man��woman֮��
	private String head;			//ͷ���ַ
	@Length(min = 0, max = 150)		//���Ƹ���ǩ���ĳ���
	private String signature;		//����ǩ��
	private Integer delete;			//��ɾ���Ķ�̬������
	private Timestamp createDate;	//�û�����ʱ��
	private List<Dynamic> dynamics;	//�û��Ķ�̬
	
	public List<Dynamic> getDynamics() {
		return dynamics;
	}

	public void setDynamics(List<Dynamic> dynamics) {
		this.dynamics = dynamics;
	}

	public User() {
		super();
	}
	
	//������췽��ʵ���û�ע���ʱ��ʹ�õģ��û�����ʱ���������ݿ����ɵ�
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.age = 18;
		this.gender = "man";
		this.head = this.name + ".jpg";
		this.signature = "��æҲҪ�ǵó�ջؼҳԷ�";
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
	
	//�û������û���Ϣ�Ĺ��췽��
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

	public String getSignatory() {
		return signature;
	}

	public void setSignatory(String signature) {
		this.signature = signature;
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
