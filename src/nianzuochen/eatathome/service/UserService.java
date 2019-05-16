package nianzuochen.eatathome.service;

/*
 * 用来处理有关user数据库中的 操纵
 * 包括用户的添加数据修改删除查询用户的动态
 * 动态查询包括简单查询，也就是仅仅是信息查询不包括动态和动态的评论
 * 动态的详细查询，则是包括动态和动态的评论
 * */

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.User;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.UserMapper;

public class UserService {
	private SqlSession session;
	
	public UserService() {
		
	}
	
	//向数据库中添加用户
	public void addUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		um.addUser(user);
		System.out.print("finish");
		session.commit();
		//session.close();
	}
	//更新数据库中的用户信息
	public void updateUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		um.updateUser(user);
//System.out.println("finish");
		session.commit();
		//session.close();
		
	}
	//查询，如果没有查询到的话返回的是null
	//查询用户的信息，不查询用户的动态和动态的评论
	public User selectSimpleUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectSimpleUser(user.getName());
//System.out.println("finish");
		session.commit();
		//session.close();
		return newUser;
	}
	
	//查询用户 包括查询用户的动态和动态的评论
	public User selectUser(User user) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		User newUser = um.selectUser(user);
		session.commit();
		session.close();
		return newUser;
	}
	
	//删除用户
	public void removeUserById(int id) {
		session = MySqlSessionFactory.getSqlSession();
		UserMapper um = session.getMapper(UserMapper.class);
		um.removeUserById(id);
		session.commit();
		//session.close();
	}
	
	//关闭这个会话
	public void close() {
		session.close();
	}
}
