package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Dynamic;
public interface DynamicMapper {
	//添加用户的动态
	void addDynamic(Dynamic dynamic);
	//更新动态的好评
	void updateDynamicUpCount(Dynamic dynnamic);
	//更新动态的差评
	void updateDynamicDownCount(Dynamic dynamic);
	//使用用户的id查询用户的动态
	List<Dynamic> selectDynamicByUserId(int id);
	//查询最近发布的20条动态
	List<Dynamic> selectTwentyDynamics();
}
