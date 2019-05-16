package nianzuochen.mybatis.factory;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionFactory {
	private static SqlSessionFactory sqlSessionFactory= null;
	static {
		try {
			InputStream inputStream =
					Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	//��ȡsqlSession
	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	//��ȡSqlSessionFactory�ľ�̬����
		public static SqlSessionFactory getSqlSessionFactory() {
			return sqlSessionFactory;
		}
}
