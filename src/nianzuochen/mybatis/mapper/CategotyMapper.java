package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Categoty;

public interface CategotyMapper {
	public List<Categoty> selectCategoties();
	public Categoty selectCategotyById(String id);
}
