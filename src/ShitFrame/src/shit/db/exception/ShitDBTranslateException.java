package shit.db.exception;

/**
 * ShitDB框架SQL语句翻译错误
 * 
 * @author GongTengPangYi
 *
 */
public class ShitDBTranslateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8323977277321064866L;

	public ShitDBTranslateException() {
		super();
	}

	public ShitDBTranslateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShitDBTranslateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShitDBTranslateException(String message) {
		super(message);
	}

	public ShitDBTranslateException(Throwable cause) {
		super(cause);
	}

}
