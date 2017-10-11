package shit.db.query;

import java.io.Serializable;
import java.sql.ResultSet;

import shit.db.exception.ShitDBConfigureException;
import shit.db.exception.ShitDBResultException;
import shit.db.exception.ShitDBTranslateException;

public class ShitDBResultId implements ShitDBResult<Serializable> {

	@Override
	public Serializable analysis(ResultSet resultSet)
			throws ShitDBResultException, ShitDBConfigureException, ShitDBTranslateException {
		// TODO Auto-generated method stub
		return null;
	}

}
