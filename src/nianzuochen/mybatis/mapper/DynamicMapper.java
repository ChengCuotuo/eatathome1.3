package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Dynamic;
public interface DynamicMapper {
	//����û��Ķ�̬
	void addDynamic(Dynamic dynamic);
	//���¶�̬�ĺ���
	void updateDynamicUpCount(Dynamic dynnamic);
	//���¶�̬�Ĳ���
	void updateDynamicDownCount(Dynamic dynamic);
	//ʹ���û���id��ѯ�û��Ķ�̬
	List<Dynamic> selectDynamicByUserId(int id);
	//��ѯ���������20����̬
	List<Dynamic> selectTwentyDynamics();
}
