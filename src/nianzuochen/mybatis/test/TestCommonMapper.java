package nianzuochen.mybatis.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Common;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.CommonMapper;
import nianzuochen.mybatis.mapper.DynamicMapper;

public class TestCommonMapper {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
		TestCommonMapper tp = new TestCommonMapper();
		
		Common coment = new Common(1, 1, "һֱ�Ҹ���");
		//tp.testAddCommon(session, coment);
		
		List<Common> coments = tp.testSelectCommonsById(session, 1);
		for (Common com : coments) {
			System.out.println(com);
		}
	}
	
	//���һ����̬
	public void testAddCommon(SqlSession session, Common coment) {
		CommonMapper cp = session.getMapper(CommonMapper.class);
		cp.addComent(coment);
		session.commit();
System.out.println("finish");
		//session.close();
	}
	//��ѯĳ����̬������
	public List<Common> testSelectCommonsById(SqlSession session, int id){
		CommonMapper cp = session.getMapper(CommonMapper.class);
		List<Common> coments = cp.selectCommonsById(id);
		session.commit();
		//session.close();
System.out.println("finish");
		return coments;
	}
}
