package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
public class Dynamic implements Serializable{
	private Integer id;			//动态的逻辑id
	private Integer user_id;	//该动态发出的用户id
	private String describe;	//用户对该动态的描述
	private String photo;		//动态的照片信息，只允许一张照片
	private Timestamp date;		//动态发布的时间
	private Integer upCount;	//动态的好评
	private Integer downCount;	//动态差评
	private User dynamicUser;	//发布该动态的用户
	private List<Common> commons;	//用户的动态
	
	public Dynamic() {
		super();
	}
	//发布动态的用户id 动态的描述 动态照片 好评量 差评量
	public Dynamic(Integer user_id, String describe, String photo,
			Integer upCount, Integer downCount) {
		super();
		this.user_id = user_id;
		this.describe = describe;
		this.photo = photo;
		this.upCount = upCount;
		this.downCount = downCount;
	}
	//仅仅需要动态的id和动态的好评量和差评量的构造方法用于修改数据库中动态表
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

	public String getDate() {
		return date.toString();
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
				", " + this.upCount + ", " + this.downCount + ", " + this.date.toString() + ", ");
		for (Common com : commons) {
			result.append("\n\t Common->[" + com.getComment() + ", " + com.getDynamic_id() + "]");
		}
		result.append("\n\t]");
		return result.toString();
 	}
}
