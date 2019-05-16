package nianzuochen.eatathome.service;

/*
 * �����й�common���ݿ�Ĳ���
 * ������ۺͲ�������
 * */
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Common;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.CommonMapper;

public class CommonService {
	private SqlSession session;
	
	public CommonService() {
		
	}
	
	//���һ����̬
	public void addCommon(Common coment) {
		session = MySqlSessionFactory.getSqlSession();
		CommonMapper cp = session.getMapper(CommonMapper.class);
		cp.addComent(coment);
		session.commit();
//System.out.println("finish");
		//session.close();
	}
		//��ѯĳ����̬������
	public List<Common> selectCommonsById(int id){
		session = MySqlSessionFactory.getSqlSession();
		CommonMapper cp = session.getMapper(CommonMapper.class);
		List<Common> coments = cp.selectCommonsById(id);
		session.commit();
		//session.close();
//System.out.println("finish");
		return coments;
	}
	//�ر�����Ự
	public void close() {
		session.close();
	}
}
