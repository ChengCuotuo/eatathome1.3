package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Menu;

public interface MenuMapper {
	public Menu selectMenu(String id);
	List<Menu> selectSameType(String cid);
}
