package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable{
	private String id;			//�˵��ı��
	private String name;		//��Ʒ������
	private String img;			//��Ʒ���ܵ�ͼƬ��ַ
	private String content;		//��Ʒ������
	private String cid;		//��Ʒ����ı��
	private List<Practice> practices;	//��Ʒ����
	
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
