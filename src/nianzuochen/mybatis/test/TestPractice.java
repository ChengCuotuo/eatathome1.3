package nianzuochen.mybatis.test;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Practice;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.PracticeMapper;

public class TestPractice {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
		PracticeMapper pm = session.getMapper(PracticeMapper.class);
		ArrayList<Practice> practices  = (ArrayList<Practice>) pm.selectParactices("zc1");
		for (Practice practice : practices) {
			System.out.println(practice);
		}
	}
}
