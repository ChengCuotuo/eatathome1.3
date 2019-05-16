package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Practice;

public interface PracticeMapper {
	//查询出某一种菜品的所有做法，所以是 List 存储
	List<Practice> selectParactices(String mid);
}
