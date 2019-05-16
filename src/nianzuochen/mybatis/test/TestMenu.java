package nianzuochen.mybatis.test;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Menu;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.MenuMapper;

public class TestMenu {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
		MenuMapper mp = session.getMapper(MenuMapper.class);
		//Menu menu = mp.selectMenu("zc1");
		//System.out.println(menu);
		
		ArrayList<Menu> menus = (ArrayList<Menu>)mp.selectSameType("zc");
		for (Menu m : menus) {
			System.out.println(m);
		}
	}
}	
