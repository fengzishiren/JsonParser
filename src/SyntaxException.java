/**
 * 
 * @author lunatic 2014年7月26日
 */
public class SyntaxException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280162605911298279L;
	private Pos pos;

	public SyntaxException() {
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(String msg) {
		super(msg);
	}

	public SyntaxException(String msg, Pos pos) {
		super(msg);
		this.pos = pos;
	}

	public SyntaxException(Throwable cause) {
		super(cause);
	}

	public SyntaxException(String msg, Throwable cause) {
		super(msg, cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage() + pos.toString();
	}
}
