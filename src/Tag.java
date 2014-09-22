/**
 * 
 * @author lunatic 2014年7月26日
 */
public class Tag {
	public static final byte EOF = 0;
	public static final byte OPEN_BRACE = 1;// # {
	public static final byte CLOSE_BRACE = 2;// # }
	public static final byte OPEN_BRACKET = 3;// # [
	public static final byte CLOSE_BRACKET = 4;// # ]
	public static final byte KEY_STRING = 5;// # key ->string
	public static final byte STRING = 6;// # string
	public static final byte COLON = 7;// # :
	public static final byte NUMBER = 8;// # 10 8 18 9.10101
	public static final byte BOOLEAN = 9;// # true false
	public static final byte COMMA = 10;// # ,
	public static final byte NULL = 11;// # null

	private static final String[] types = { "EOF", "{", "}", "[", "]", "KEY(String)", "String", ":",
			"Number", "Bool", ",", "null" };

	public static final String toString(byte tag) {
		return types[tag];
	}
}
