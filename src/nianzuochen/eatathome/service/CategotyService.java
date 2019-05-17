package nianzuochen.eatathome.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Categoty;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.CategotyMapper;

public class CategotyService {
	private SqlSession session;
	
	public CategotyService() {
		
	}
	//获取所有的菜品的分类信息
	public List<Categoty> selectCategoties(){
		session = MySqlSessionFactory.getSqlSession();
		CategotyMapper cp = session.getMapper(CategotyMapper.class);
		List<Categoty> categoties = cp.selectCategoties();
		session.commit();
		return categoties;
	}
	
	//获取某一类菜品的信息
	public Categoty selectCategotyById(String id) {
		session = MySqlSessionFactory.getSqlSession();
		CategotyMapper cp = session.getMapper(CategotyMapper.class);
		Categoty categoty = cp.selectCategotyById(id);
		session.commit();
		return categoty;
	}
}
