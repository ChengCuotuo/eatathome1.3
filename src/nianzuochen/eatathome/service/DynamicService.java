package nianzuochen.eatathome.service;

/*
 * �����й�dynamic���ݿ��йصĲ���
 * ������̬�����ɾ���޸Ĳ�ѯ
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
	
	//��Ӷ�̬
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
	
	//��ѯǰ20����̬
	public List<Dynamic> selectTwentyDynamics(){
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		List<Dynamic> dynamics = dm.selectTwentyDynamics();
		session.commit();
//System.out.println("finish");
		//session.close();
		return dynamics;
	}
	
	//���¶�̬�ĺ���
	public void updateDynamicUpCount(Dynamic dynamic) {
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicUpCount(dynamic);
		session.commit();
//System.out.println("finish");
		//session.close();
	}
	
	//���¶�̬�Ĳ���
	public void updateDynamicDownCount(Dynamic dynamic) {
		session = MySqlSessionFactory.getSqlSession();
		DynamicMapper dm = session.getMapper(DynamicMapper.class);
		dm.updateDynamicDownCount(dynamic);
		session.commit();
//System.out.println("finish");
		//session.close();
	}
	
	//�ر�����Ự
	public void close() {
		session.close();
	}
}
