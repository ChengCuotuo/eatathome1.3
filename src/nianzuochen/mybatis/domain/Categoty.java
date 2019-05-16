package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.util.List;

public class Categoty implements Serializable{
	private String id;		//菜品类别编号
	private String name;	//分类的名称
	private List<Menu> menus;	//菜品
	
	public Categoty(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public List<Menu> getMenus() {
		return menus;
	}
	
}
