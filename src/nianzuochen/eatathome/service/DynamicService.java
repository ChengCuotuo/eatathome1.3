package nianzuochen.eatathome.service;

/*
 * 处理有关dynamic数据库有关的操作
 * 包括动态的添加删除修改查询
 * */

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Dynamic;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.DynamicMapper;

public class DynamicService {
	private SqlSession session;
	
	public DynamicService() {
		
	}
	
	//添加动态
	public void addDynamic(Dynamic dynamic) {
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper mp = session.getMapper(DynamicMapper.class);
		mp.addDynamic(dynamic);
		session.commit();
		//session.close();
//System.out.println("finish");
	}
	
	public List<Dynamic> selectDynamicByUserId(int id){
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		List<Dynamic> dynamics = dm.selectDynamicByUserId(id);
		session.commit();
//System.out.println("finish");
		//session.close();
		return dynamics;
	}
	
	//查询前20条动态
	public List<Dynamic> selectTwentyDynamics(){
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		List<Dynamic> dynamics = dm.selectTwentyDynamics();
		session.commit();
//System.out.println("finish");
		//session.close();
		return dynamics;
	}
	
	//更新动态的好评
	public void updateDynamicUpCount(Dynamic dynamic) {
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicUpCount(dynamic);
		session.commit();
//System.out.println("finish");
		//session.close();
	}
	
	//更新动态的差评
	public void updateDynamicDownCount(Dynamic dynamic) {
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicDownCount(dynamic);
		session.commit();
//System.out.println("finish");
		//session.close();
	}
	
	//关闭这个会话
	public void close() {
		session.close();
	}
}
