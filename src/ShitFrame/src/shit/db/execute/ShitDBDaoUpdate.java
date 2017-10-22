package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBJDBCException;
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.exception.ShitDBWrongControlException;
import shit.db.query.ShitDBQuery;
import shit.db.query.ShitDBQueryBasic;
import shit.db.sql.ShitQLSameModel;
import shit.db.sql.ShitQLUpdate;

/**
 * 执行更新操作，值得注意的是，规范上我们建议update的model需要是查询结果做过内参变化后再执行update的，
 * 但是少数情况下我们可能只想修改极少数变量而用了新参数，对于这种情况我们不支持将传入参数转化为储存结果
 * 储存结果的model我们将会在execute的返回值中给出： 
 * Trade trade = 。。。。。 ;
 * Trade trade2 = update.execute(trade); 
 * sysout(trade) 这个值不是存完之后数据库里的 
 * sysout(trade2) 这个值才是
 * 
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBDaoUpdate extends ShitDBDaoNotQuery {

	public ShitDBDaoUpdate(Connection conn, Serializable model) {
		super(conn, model);
	}

	public ShitDBDaoUpdate(Connection conn) {
		super(conn);
	}

	@Override
	protected void initShitQLBuilder() throws ShitDBTranslateException {
		shitQLBuilder = new ShitQLUpdate(model).buildShitQL();
	}

	@Override
	protected Serializable handleAfterExcute() throws ShitDBTranslateException, ShitDBExecuteException {
		Class<?> clazz = model.getClass();
		ShitQLSameModel sameModelBuilder = (ShitQLSameModel) new ShitQLSameModel(model).buildShitQL();
		String shitQL = "select * from " + clazz.getName() + sameModelBuilder.getShitQL();
		Map<String, Serializable> params = sameModelBuilder.getParamMap();
		ShitDBQuery query = new ShitDBQueryBasic(conn, clazz, shitQL, params);
		try {
			List<Serializable> list = query.query();
			if (list != null && list.size() > 0) {
				model = (Serializable) list.get(0);
			}
		} catch (ShitDBResultException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		} catch (ShitDBJDBCException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		} catch (ShitDBWrongControlException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		} catch (ShitDBConfigureException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		}
		return model;
	}

}
