package nianzuochen.mybatis.mapper;

import nianzuochen.mybatis.domain.User;

public interface UserMapper {
	//添加用户
	void addUser(User user);
	//更新用户的信息
	void updateUser(User user);
	//删除用户
	void removeUserById(int id);
	//仅仅查询用户的信息
	User selectSimpleUser(String name);
	//使用用户的id查询用户的信息
	User selectSimpleUserById(int id);
	//查询用户的信息，包括发布的动态以及动态的评论
	User selectUser(User user);
	
}	
