package nianzuochen.eatathome.service;

/*
 * 处理有关common数据库的操作
 * 添加评论和查找评论
 * */
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import nianzuochen.mybatis.domain.Common;
import nianzuochen.mybatis.factory.MySqlSessionFactory;
import nianzuochen.mybatis.mapper.CommonMapper;

public class CommonService {
	private SqlSession session;
	
	public CommonService() {
		
	}
	
	//添加一条动态
	public void addCommon(Common coment) {
		session = MySqlSessionFactory.getSqlSession();
		CommonMapper cp = session.getMapper(CommonMapper.class);
		cp.addComent(coment);
		session.commit();
//System.out.println("finish");
		//session.close();
	}
		//查询某条动态的评论
	public List<Common> selectCommonsById(int id){
		session = MySqlSessionFactory.getSqlSession();
		CommonMapper cp = session.getMapper(CommonMapper.class);
		List<Common> coments = cp.selectCommonsById(id);
		session.commit();
		//session.close();
//System.out.println("finish");
		return coments;
	}
	//关闭这个会话
	public void close() {
		session.close();
	}
}
