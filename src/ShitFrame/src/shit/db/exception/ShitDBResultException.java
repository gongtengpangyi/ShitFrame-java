package shit.db.exception;

/**
 * ShitDB框架处理查询结果异常
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBResultException extends ShitDBJDBCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1624270701296264393L;

	public ShitDBResultException() {
		super();
	}

	public ShitDBResultException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
	}

	public ShitDBResultException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public ShitDBResultException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
	}

	public ShitDBResultException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public ShitDBResultException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public ShitDBResultException(String reason) {
		super(reason);
	}

	public ShitDBResultException(Throwable cause) {
		super(cause);
	}

}
