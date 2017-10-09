package shit.db.exception;

/**
 * ShitDB框架数据库语句执行异常
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBExecuteException extends ShitDBJDBCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3865004310573292382L;

	public ShitDBExecuteException() {
		super();
	}

	public ShitDBExecuteException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
	}

	public ShitDBExecuteException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public ShitDBExecuteException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
	}

	public ShitDBExecuteException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public ShitDBExecuteException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public ShitDBExecuteException(String reason) {
		super(reason);
	}

	public ShitDBExecuteException(Throwable cause) {
		super(cause);
	}

}
