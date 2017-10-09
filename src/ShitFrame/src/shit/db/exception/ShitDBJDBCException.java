package shit.db.exception;

import java.sql.SQLException;

/**
 * ShitDB框架数据库操作异常
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBJDBCException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3865004310573292382L;

	public ShitDBJDBCException() {
		super();
	}

	public ShitDBJDBCException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
	}

	public ShitDBJDBCException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public ShitDBJDBCException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
	}

	public ShitDBJDBCException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public ShitDBJDBCException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public ShitDBJDBCException(String reason) {
		super(reason);
	}

	public ShitDBJDBCException(Throwable cause) {
		super(cause);
	}

}
