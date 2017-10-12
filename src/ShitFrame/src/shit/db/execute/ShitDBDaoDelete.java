package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;

import shit.db.exception.ShitDBExecuteException;
import shit.db.exception.ShitDBTranslateException;
import shit.db.sql.ShitQLDelete;

/**
 * 删除
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBDaoDelete extends ShitDBDaoNotQuery {

	public ShitDBDaoDelete(Connection conn, Serializable model) {
		super(conn, model);
	}

	public ShitDBDaoDelete(Connection conn) {
		super(conn);
	}

	@Override
	protected void initShitQLBuilder() throws ShitDBTranslateException {
		shitQLBuilder = new ShitQLDelete(model).buildShitQL();
	}

	@Override
	protected Serializable handleAfterExcute() throws ShitDBTranslateException, ShitDBExecuteException {
		return null;
	}

}
