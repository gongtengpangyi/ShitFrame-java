package shit.helper;

/**
 * 反射过程异常
 * @author GongTengPangYi
 *
 */
public class ShitReflectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6435031881728482256L;

	public ShitReflectException() {
		super();
	}

	public ShitReflectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShitReflectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShitReflectException(String message) {
		super(message);
	}

	public ShitReflectException(Throwable cause) {
		super(cause);
	}

}
