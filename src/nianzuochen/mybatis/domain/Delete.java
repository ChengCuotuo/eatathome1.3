package nianzuochen.mybatis.domain;

import java.io.Serializable;

public class Delete implements Serializable{
	private Integer user_id;	//��ɾ���Ķ�̬���û���id
	private Integer dynamic_id;	//��ɾ���Ķ�̬��id
	
	public Delete() {
		super();
	}
	
	public Delete(Integer user_id, Integer dynamic_id) {
		super();
		this.user_id = user_id;
		this.dynamic_id = dynamic_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getDynamic_id() {
		return dynamic_id;
	}

	public void setDynamic_id(Integer dynamic_id) {
		this.dynamic_id = dynamic_id;
	}
	
}
