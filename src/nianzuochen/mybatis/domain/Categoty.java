package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.util.List;

public class Categoty implements Serializable{
	private String id;		//��Ʒ�����
	private String name;	//���������
	private List<Menu> menus;	//��Ʒ
	
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
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("id: " + this.id + ", name: " + this.name + ", menus -> {");
		if (menus != null) {
			for (Menu m : menus) {
				str.append(m.toString() + "\n");
			}
		}
		str.append("}");
		return str.toString();
	}
}
