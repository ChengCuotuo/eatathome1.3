package nianzuochen.eatathome.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Menu;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.MenuMapper;

public class MenuService {
	private SqlSession session;
	
	public MenuService() {
		
	}

	//获取一种菜品的详细信息，包括做法信息
	public Menu selectMenu(String id) {
		session = MySqlSessionFactory.getSqlSession();
		MenuMapper mp = session.getMapper(MenuMapper.class);
		Menu menu = mp.selectMenu(id);
		session.commit();
		return menu;
	}
	
	//获取同类菜品的信息
	public List<Menu> selectSameType(String cid) {
		session = MySqlSessionFactory.getSqlSession();
		MenuMapper mp = session.getMapper(MenuMapper.class);
		List<Menu> menus = mp.selectSameType(cid);
		session.commit();
		return menus;
	}
}
