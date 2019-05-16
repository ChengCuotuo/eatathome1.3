package nianzuochen.mybatis.test;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Categoty;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.CategotyMapper;

public class TestCategoty {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
		CategotyMapper cp = session.getMapper(CategotyMapper.class);
//		ArrayList<Categoty> categoties = (ArrayList<Categoty>) cp.selectCategoties();
//		for (Categoty c : categoties) {
//			System.out.println(c);
//		}
		Categoty c = cp.selectCategotyById("zc");
		System.out.println(c);
	}
}
