package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable{
	private String id;			//菜单的编号
	private String name;		//菜品的名称
	private String img;			//菜品介绍的图片地址
	private String content;		//菜品的配料
	private String cid;		//菜品分类的编号
	private List<Practice> practices;	//菜品做法
	
	public Menu(String id, String name, String img, String content, String cid) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.content = content;
		this.cid = cid;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImg() {
		return img;
	}

	public String getContent() {
		return content;
	}

	public String getCid() {
		return cid;
	}

	public List<Practice> getPractices() {
		return practices;
	}
	
}
