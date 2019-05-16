package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
public class Dynamic implements Serializable{
	private Integer id;			//��̬���߼�id
	private Integer user_id;	//�ö�̬�������û�id
	private String describe;	//�û��Ըö�̬������
	private String photo;		//��̬����Ƭ��Ϣ��ֻ����һ����Ƭ
	private Timestamp date;		//��̬������ʱ��
	private Integer upCount;	//��̬�ĺ���
	private Integer downCount;	//��̬����
	private User dynamicUser;	//�����ö�̬���û�
	private List<Common> commons;	//�û��Ķ�̬
	
	public Dynamic() {
		super();
	}
	//������̬���û�id ��̬������ ��̬��Ƭ ������ ������
	public Dynamic(Integer user_id, String describe, String photo,
			Integer upCount, Integer downCount) {
		super();
		this.user_id = user_id;
		this.describe = describe;
		this.photo = photo;
		this.upCount = upCount;
		this.downCount = downCount;
	}
	//������Ҫ��̬��id�Ͷ�̬�ĺ������Ͳ������Ĺ��췽�������޸����ݿ��ж�̬��
	public Dynamic(Integer id, Integer upCount, Integer downCount) {
		this.id = id;
		this.upCount = upCount;
		this.downCount = downCount;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Integer getUpCount() {
		return upCount;
	}

	public void setUpCount(Integer upCount) {
		this.upCount = upCount;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public User getDynamicUser() {
		return dynamicUser;
	}

	public void setDynamicUser(User dynamicUser) {
		this.dynamicUser = dynamicUser;
	}
	
	public List<Common> getCommons() {
		return commons;
	}

	public void setCommons(List<Common> commons) {
		this.commons = commons;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("Dynamic->[" + this.dynamicUser + this.describe + ", " +this.user_id +
				", " + this.upCount + ", " + this.downCount + ", ");
		for (Common com : commons) {
			result.append("\n\t Common->[" + com.getComment() + ", " + com.getDynamic_id() + "]");
		}
		result.append("\n\t]");
		return result.toString();
 	}
}
