package nianzuochen.mybatis.mapper;

import nianzuochen.mybatis.domain.User;

public interface UserMapper {
	//����û�
	void addUser(User user);
	//�����û�����Ϣ
	void updateUser(User user);
	//ɾ���û�
	void removeUserById(int id);
	//������ѯ�û�����Ϣ
	User selectSimpleUser(String name);
	//ʹ���û���id��ѯ�û�����Ϣ
	User selectSimpleUserById(int id);
	//��ѯ�û�����Ϣ�����������Ķ�̬�Լ���̬������
	User selectUser(User user);
	
}	
