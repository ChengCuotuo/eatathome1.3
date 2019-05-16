package nianzuochen.mybatis.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Dynamic;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.DynamicMapper;
import nianzuochen.mybatis.mapper.UserMapper;

public class TestDynamicMapper {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
//		Dynamic dynamic = 
//				new Dynamic(1, "���Ϻ����Ѿ۲�", "test2018-11-27", 0, 0);
		TestDynamicMapper tm = new TestDynamicMapper();
		//tm.addDynamic(session, dynamic);
		
		List<Dynamic> dynamics = tm.testSelectTwentyDynamics(session);
//		for (Dynamic dyn : dynamics) {
//			System.out.println(dyn);
//		}
		
		
		//�޸��Ѿ���ѯ����һ����̬�ĺ��� ��һ
		Dynamic dynamic = dynamics.get(1);
		dynamic.setUpCount(dynamic.getUpCount() + 1);
		tm.testUpdateUserUpCount(session, dynamic);
		
		//�޸��Ѿ���ѯ����һ����̬�Ĳ��� ��һ
//		dynamic = dynamics.get(2);
//		dynamic.setDownCount(dynamic.getDownCount() + 1);
//		tm.testUpdateUserDownCount(session, dynamic);
	}
	
	//��Ӷ�̬
	public void addDynamic(SqlSession session, Dynamic dynamic) {
		DynamicMapper mp = session.getMapper(DynamicMapper.class);
		mp.addDynamic(dynamic);
		session.commit();
		//session.close();
System.out.println("finish");
	}
	//��ѯǰ20����̬
	public List<Dynamic> testSelectTwentyDynamics(SqlSession session){
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		List<Dynamic> dynamics = dm.selectTwentyDynamics();
		session.commit();
System.out.println("finish");
		return dynamics;
	}
	
	//���¶�̬�ĺ���
	public void testUpdateUserUpCount(SqlSession session, Dynamic dynamic) {
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicUpCount(dynamic);
		session.commit();
System.out.println("finish");
		//session.close();
	}
	
	//���¶�̬�Ĳ���
	public void testUpdateUserDownCount(SqlSession session, Dynamic dynamic) {
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicDownCount(dynamic);
		session.commit();
System.out.println("finish");
		//session.close();
	}
}
