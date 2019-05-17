package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Common;

public interface CommonMapper {
	List<Common> selectCommonsById(int id);
	void addComent(Common coment);
}
