package shit.db.query;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.exception.ShitDBWrongControlException;

/**
 * 基本数据库查询操作，纯粹使用数据库连接完成的查询操作
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBQueryBasic extends ShitDBQuery {

	public ShitDBQueryBasic(Connection conn, Class<?> modelClass) {
		super(conn, modelClass);
	}

	public ShitDBQueryBasic(Connection conn, Class<?> modelClass, String shitQL, Map<String, Serializable> params) {
		super(conn, modelClass, shitQL, params);
	}

	public ShitDBQueryBasic(Connection conn, Class<?> modelClass, String shitQL, ShitDBPager pager,
			Map<String, Serializable> params) {
		super(conn, modelClass, shitQL, pager, params);
	}

	public ShitDBQueryBasic(Connection conn, Class<?> modelClass, String shitQL, ShitDBPager pager) {
		super(conn, modelClass, shitQL, pager);
	}

	public ShitDBQueryBasic(Connection conn, Class<?> modelClass, String shitQL) {
		super(conn, modelClass, shitQL);
	}

	@Override
	public List<Serializable> query() throws ShitDBJDBCException, ShitDBWrongControlException, ShitDBConfigureException,
			ShitDBTranslateException {
		executeQuery.setShowSql(showSql);
		ShitDBResultModel result = initDBQuery();
		List<Serializable> list = result.analysis(executeQuery.excute());
		return list;
	}

}
