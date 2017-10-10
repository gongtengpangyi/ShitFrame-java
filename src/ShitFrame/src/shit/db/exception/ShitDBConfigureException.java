package shit.db.exception;

/**
 * ShitDB配置错误
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBConfigureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -888679536506503683L;

	public ShitDBConfigureException() {
		super();
	}

	public ShitDBConfigureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShitDBConfigureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShitDBConfigureException(String message) {
		super(message);
	}

	public ShitDBConfigureException(Throwable cause) {
		super(cause);
	}

}
