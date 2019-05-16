package nianzuochen.mybatis.test;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.User;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.UserMapper;

public class TestUserMapper {
	public static void main(String[] args) {
		SqlSession session = MySqlSessionFactory.getSqlSession();
		User user = new User("test", "021191");
		TestUserMapper tum = new TestUserMapper();
		//tum.testAddUser(session, user);
		
		//user = tum.testSelectSimpleUser(session, user);
		user = tum.testSelectSimpleUserById(session, 1);
		//user.setAge(20);
		//user.setSignatory("���Ҹ���������Ǻͼ�����һ��Է�");
		//tum.testUpdateUser(session, user);
		
		//tum.testRemoveUserById(session, user.getId());
		//�����ѯ��ʱ�򣬶�̬���۵��û���Ϣ����ѯ����
		//���εĴ��� ֮ǰд�Ĵ�����id�������ĳ���User�����Դ����Ǵ����ˡ�
		
		//user = tum.testSelectUser(session, user);
		System.out.print(user);
	}
	//�����ݿ�������û�
	public void testAddUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		um.addUser(user);
		System.out.print("finish");
		session.commit();
		//session.close();
	}
	//�������ݿ��е��û���Ϣ
	public void testUpdateUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		um.updateUser(user);
System.out.println("finish");
		session.commit();
		//session.close();
		
	}
	//��ѯ�û�����Ϣ������ѯ�û��Ķ�̬�Ͷ�̬������
	public User testSelectSimpleUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectSimpleUser(user.getName());
System.out.println("finish");
		session.commit();
		//session.close();
		return newUser;
	}
	
	//��ѯ�û�����Ϣ������ѯ�û��Ķ�̬�Ͷ�̬������
	public User testSelectSimpleUserById(SqlSession session, int id) {
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectSimpleUserById(id);
System.out.println("finish");
		session.commit();
		//session.close();
		return newUser;
	}
	//ɾ���û�
	public void testRemoveUserById(SqlSession session, int id) {
		UserMapper um = session.getMapper(UserMapper.class);
		um.removeUserById(id);
		session.commit();
		//session.close();
	}
	//��ѯ�û� ������ѯ�û��Ķ�̬�Ͷ�̬������
	public User testSelectUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectUser(user);
		session.commit();
		//session.close();
		return newUser;
	}
}
