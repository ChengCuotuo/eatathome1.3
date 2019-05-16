package nianzuochen.mybatis.domain;

import java.io.Serializable;
import java.util.List;

public class Practice implements Serializable{
	private String id;		//做法表的编号
	private String step;	//做法的步骤
	private String img;		//做法图片的位置
	private String mid;		//菜单的编号
	private Integer order;		//顺序
	
	public Practice(String id, String step, String img, String mid, Integer order) {
		super();
		this.id = id;
		this.step = step;
		this.img = img;
		this.mid = mid;
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
	
	public int getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return "step: " + this.step + ", img: " 
				+ this.img + ", mid: " + this.mid + ", order: " + this.order;
	}
}
