package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;

import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.query.ShitDBResultId;
import shit.db.sql.ShitQLSameModel;
import shit.db.sql.ShitQLSave;
import shit.db.table.ShitDBTable;
import shit.helper.ShitReflectException;
import shit.helper.ShitReflectHelper;

/**
 * 执行储存操作
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBDaoSave extends ShitDBDaoNotQuery {

	public ShitDBDaoSave(Connection conn, Serializable model) {
		super(conn, model);
	}

	public ShitDBDaoSave(Connection conn) {
		super(conn);
	}

	@Override
	protected void initShitQLBuilder() throws ShitDBTranslateException {
		shitQLBuilder = new ShitQLSave(model).buildShitQL();
	}

	@Override
	protected Serializable handleAfterExcute() throws ShitDBTranslateException, ShitDBExecuteException {
		Class<?> clazz = model.getClass();
		ShitQLSameModel sameModelBuilder = (ShitQLSameModel) new ShitQLSameModel(model).buildShitQL();
		String shitQL = "select " + clazz.getAnnotation(ShitDBTable.class).primaryKey() + " as id from "
				+ clazz.getName() + sameModelBuilder.getShitQL();
		Map<String, Serializable> params = sameModelBuilder.getParamMap();
		ShitDBDaoQuery query = new ShitDBDaoQuery(conn, clazz, shitQL, params, null);
		ShitDBResultId result = new ShitDBResultId();
		try {
			Serializable id = result.analysis(query.excute());
			String key = clazz.getAnnotation(ShitDBTable.class).primaryKey();
			ShitReflectHelper.setValue(model, key, String.valueOf(id), true);
		} catch (ShitDBResultException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		} catch (ShitReflectException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		}
		return model;
	}

}
