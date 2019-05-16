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
		//user.setSignatory("最幸福的事情就是和家里人一起吃饭");
		//tum.testUpdateUser(session, user);
		
		//tum.testRemoveUserById(session, user.getId());
		//这个查询的时候，动态评论的用户信息，查询出错。
		//传参的错误 之前写的传参是id，后来改成了User，所以传参是错误了。
		
		//user = tum.testSelectUser(session, user);
		System.out.print(user);
	}
	//向数据库中添加用户
	public void testAddUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		um.addUser(user);
		System.out.print("finish");
		session.commit();
		//session.close();
	}
	//更新数据库中的用户信息
	public void testUpdateUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		um.updateUser(user);
System.out.println("finish");
		session.commit();
		//session.close();
		
	}
	//查询用户的信息，不查询用户的动态和动态的评论
	public User testSelectSimpleUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectSimpleUser(user.getName());
System.out.println("finish");
		session.commit();
		//session.close();
		return newUser;
	}
	
	//查询用户的信息，不查询用户的动态和动态的评论
	public User testSelectSimpleUserById(SqlSession session, int id) {
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectSimpleUserById(id);
System.out.println("finish");
		session.commit();
		//session.close();
		return newUser;
	}
	//删除用户
	public void testRemoveUserById(SqlSession session, int id) {
		UserMapper um = session.getMapper(UserMapper.class);
		um.removeUserById(id);
		session.commit();
		//session.close();
	}
	//查询用户 包括查询用户的动态和动态的评论
	public User testSelectUser(SqlSession session, User user) {
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectUser(user);
		session.commit();
		//session.close();
		return newUser;
	}
}
