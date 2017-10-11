package shit.db.execute;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import shit.db.exception.ShitDBExecuteException;

public class ShitDBExecuteSQLNotQuery extends ShitDBExecuteSQL<Void> {

	public ShitDBExecuteSQLNotQuery(Connection conn) {
		super(conn);
	}

	@Override
	public Void execute(String sql, List<Serializable> params) throws ShitDBExecuteException {
		PreparedStatement stmt = prepareStatement(sql, params);
		try {
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBExecuteException(e);
		}
		return null;
	}

}
