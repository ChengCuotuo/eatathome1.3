package nianzuochen.eatathome.service;

/*
 * ���������й�user���ݿ��е� ����
 * �����û�����������޸�ɾ����ѯ�û��Ķ�̬
 * ��̬��ѯ�����򵥲�ѯ��Ҳ���ǽ�������Ϣ��ѯ��������̬�Ͷ�̬������
 * ��̬����ϸ��ѯ�����ǰ�����̬�Ͷ�̬������
 * */

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.User;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.UserMapper;

public class UserService {
	private SqlSession session;
	
	public UserService() {
		
	}
	
	//�����ݿ�������û�
	public void addUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		um.addUser(user);
		System.out.print("finish");
		session.commit();
		//session.close();
	}
	//�������ݿ��е��û���Ϣ
	public void updateUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		um.updateUser(user);
//System.out.println("finish");
		session.commit();
		//session.close();
		
	}
	//��ѯ�����û�в�ѯ���Ļ����ص���null
	//��ѯ�û�����Ϣ������ѯ�û��Ķ�̬�Ͷ�̬������
	public User selectSimpleUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectSimpleUser(user.getName());
//System.out.println("finish");
		session.commit();
		//session.close();
		return newUser;
	}
	
	//��ѯ�û� ������ѯ�û��Ķ�̬�Ͷ�̬������
	public User selectUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectUser(user);
		session.commit();
		session.close();
		return newUser;
	}
	
	//ɾ���û�
	public void removeUserById(int id) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		um.removeUserById(id);
		session.commit();
		//session.close();
	}
	
	//�ر�����Ự
	public void close() {
		session.close();
	}
}
