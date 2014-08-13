/**
 * 
 * @author lunatic 2014年7月26日
 */
public class S {
	public static void syntaxError(String msg, int row, int col) {
		throw new SyntaxException(msg, Pos.create(row, col));
	}

	public static void syntaxError(String msg, Pos pos) {
		throw new SyntaxException(msg, pos);
	}

	public static void syntaxError(String msg) {
		throw new SyntaxException(msg);
	}
}
