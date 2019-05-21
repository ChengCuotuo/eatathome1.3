package nianzuochen.mybatis.test;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.mybatis.domain.Menu;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.MenuMapper;

public class TestMenu {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
		MenuMapper mp = session.getMapper(MenuMapper.class);
		Menu menu = mp.selectMenu("zc1");
		try {
			ObjectMapper mapper = new ObjectMapper();
			String info = mapper.writeValueAsString(menu);
			System.out.println(info);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
//		ArrayList<Menu> menus = (ArrayList<Menu>)mp.selectSameType("zc");
//		for (Menu m : menus) {
//			System.out.println(m);
//		}
	}
}	
