package shit.db.exception;

/**
 * ShitDB框架数据库连接异常
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBConnectException extends ShitDBJDBCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 959952782931143710L;

	public ShitDBConnectException() {
		super();
	}

	public ShitDBConnectException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
	}

	public ShitDBConnectException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public ShitDBConnectException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
	}

	public ShitDBConnectException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public ShitDBConnectException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public ShitDBConnectException(String reason) {
		super(reason);
	}

	public ShitDBConnectException(Throwable cause) {
		super(cause);
	}

}
