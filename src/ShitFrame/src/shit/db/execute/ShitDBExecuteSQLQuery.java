package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import shit.db.exception.ShitDBExecuteException;

public class ShitDBExecuteSQLQuery extends ShitDBExecuteSQL<ResultSet> {

	public ShitDBExecuteSQLQuery(Connection conn) {
		super(conn);
	}

	@Override
	public ResultSet execute(String sql, List<Serializable> params) throws ShitDBExecuteException {
		PreparedStatement stmt = prepareStatement(sql, params);
		try {
			return stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		}
	}

}
