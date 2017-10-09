package shit.db.exception;

/**
 * ShitDB框架错误操作流程异常
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBWrongControlException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7575217600675655074L;

	public ShitDBWrongControlException() {
		super();
	}

	public ShitDBWrongControlException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShitDBWrongControlException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShitDBWrongControlException(String message) {
		super(message);
	}

	public ShitDBWrongControlException(Throwable cause) {
		super(cause);
	}

}
