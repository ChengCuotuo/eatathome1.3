package nianzuochen.mybatis.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import nianzuochen.mybatis.domain.Dynamic;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.DynamicMapper;
import nianzuochen.mybatis.mapper.UserMapper;

public class TestDynamicMapper {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
//		Dynamic dynamic = 
//				new Dynamic(1, "晚上和朋友聚餐", "test2018-11-27", 0, 0);
		TestDynamicMapper tm = new TestDynamicMapper();
		//tm.addDynamic(session, dynamic);
		
		List<Dynamic> dynamics = tm.testSelectTwentyDynamics(session);
//		for (Dynamic dyn : dynamics) {
//			System.out.println(dyn);
//		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String str = mapper.writeValueAsString(dynamics);
			System.out.println(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		//修改已经查询到的一条动态的好评 加一
//		Dynamic dynamic = dynamics.get(1);
//		dynamic.setUpCount(dynamic.getUpCount() + 1);
//		tm.testUpdateUserUpCount(session, dynamic);
//		
		//修改已经查询到的一条动态的差评 加一
//		dynamic = dynamics.get(2);
//		dynamic.setDownCount(dynamic.getDownCount() + 1);
//		tm.testUpdateUserDownCount(session, dynamic);
	}
	
	//添加动态
	public void addDynamic(SqlSession session, Dynamic dynamic) {
		DynamicMapper mp = session.getMapper(DynamicMapper.class);
		mp.addDynamic(dynamic);
		session.commit();
		//session.close();
System.out.println("finish");
	}
	//查询前20条动态
	public List<Dynamic> testSelectTwentyDynamics(SqlSession session){
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		List<Dynamic> dynamics = dm.selectTwentyDynamics();
		session.commit();
System.out.println("finish");
		return dynamics;
	}
	
	//更新动态的好评
	public void testUpdateUserUpCount(SqlSession session, Dynamic dynamic) {
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicUpCount(dynamic);
		session.commit();
System.out.println("finish");
		//session.close();
	}
	
	//更新动态的差评
	public void testUpdateUserDownCount(SqlSession session, Dynamic dynamic) {
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicDownCount(dynamic);
		session.commit();
System.out.println("finish");
		//session.close();
	}
}
