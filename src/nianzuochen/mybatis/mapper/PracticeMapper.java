package nianzuochen.mybatis.mapper;

import java.util.List;

import nianzuochen.mybatis.domain.Practice;

public interface PracticeMapper {
	//��ѯ��ĳһ�ֲ�Ʒ������������������ List �洢
	List<Practice> selectParactices(String mid);
}
