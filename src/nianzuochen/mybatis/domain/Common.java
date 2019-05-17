package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Common implements Serializable{
	private Integer id;				//评论的逻辑id
	private Integer dynamic_id;		//评论的动态的id
	private Integer common_user_id;	//评论用户的id
	private String comment;			//评论的内容
	private Timestamp date;			//评论的时间
	private User commonUser;		//评论的用户的信息
	
	public User getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(User commonUser) {
		this.commonUser = commonUser;
	}

	public Common() {
		super();
	}

	public Common(Integer dynamic_id, Integer common_user_id,
			String comment) {
		super();
		this.dynamic_id = dynamic_id;
		this.common_user_id = common_user_id;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDynamic_id() {
		return dynamic_id;
	}

	public void setDynamic_id(Integer dynamic_id) {
		this.dynamic_id = dynamic_id;
	}

	public Integer getCommon_user_id() {
		return common_user_id;
	}

	public void setCommon_user_id(Integer common_user_id) {
		this.common_user_id = common_user_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		String result = "Common->[" + this.comment +  ", " + this.id + "]";
		return result;
 	}
}
