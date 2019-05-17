package nianzuochen.eatathome.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Practice;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.PracticeMapper;

public class PracticeService {
	private SqlSession session;
	
	public PracticeService() {
		
	}
	
	//��ѯ��ĳһ�ֲ�Ʒ������������������ List �洢
	List<Practice> selectParactices(String mid) {
		session = MySqlSessionFactory.getSqlSession();
		PracticeMapper pp = session.getMapper(PracticeMapper.class);
		List<Practice> practices = pp.selectParactices(mid);
		session.commit();
		return practices;
	}
}
