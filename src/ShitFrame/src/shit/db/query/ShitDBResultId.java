package shit.db.query;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import shit.db.exception.ShitDBResultException;

/**
 * 处理只想要得到结果的队首id的结果，主要用于save操作的id返回
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBResultId implements ShitDBResult<Serializable> {

	@Override
	public Serializable analysis(ResultSet resultSet) throws ShitDBResultException {
		try {
			// if (resultSet.next()) {
			// System.out.println(resultSet.getObject("id"));
			// }
			return resultSet.next() ? (Serializable) resultSet.getObject("id") : null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ShitDBResultException(e);
		}
	}

}
