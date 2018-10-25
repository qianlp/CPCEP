package htos.coresys.exception;

/**
 * 异常处理，在action中抛出异常
 * 
 * @author zcl
 */
public class ActionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ActionException() {
		super();
	}

	public ActionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ActionException(String arg0) {
		super(arg0);
	}

	public ActionException(Throwable arg0) {
		super(arg0);
	}

}
