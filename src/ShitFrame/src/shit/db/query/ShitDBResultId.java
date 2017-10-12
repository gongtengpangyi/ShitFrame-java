package shit.db.query;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import shit.db.exception.ShitDBResultException;

public class ShitDBResultId implements ShitDBResult<Serializable> {

	@Override
	public Serializable analysis(ResultSet resultSet) throws ShitDBResultException{
		try {
//			if (resultSet.next()) {
//				System.out.println(resultSet.getObject("id"));
//			}
			return resultSet.next() ? (Serializable)resultSet.getObject("id") : null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBResultException(e);
		}
	}

}
