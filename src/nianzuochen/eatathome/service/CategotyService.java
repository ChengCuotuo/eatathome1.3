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
}
