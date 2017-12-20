package shit.helper;

/**
 * 不是实现类异常
 * 
 * @author GongTengPangYi
 *
 */
public class ShitNotImplException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6435031881728482256L;

	public ShitNotImplException() {
		super();
	}

	public ShitNotImplException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShitNotImplException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShitNotImplException(String message) {
		super(message);
	}

	public ShitNotImplException(Throwable cause) {
		super(cause);
	}

}
