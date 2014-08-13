/**
 * 
 * @author lunatic 2014年7月26日
 */
public interface Tag {
	byte OPEN_BRACE = 1;// # {
	byte CLOSE_BRACE = 2;// # }
	byte OPEN_BRACKET = 3;// # [
	byte CLOSE_BRACKET = 4;// # ]
	byte KEY_STRING = 5;// # key ->string
	byte STRING = 6;// # string
	byte COLON = 7;// # :
	byte NUMBER = 8;// # 10 8 18 9.10101
	byte BOOLEAN = 9;// # true false
	byte COMMA = 10;// # ,
	byte NULL = 11;// # null

	String[] types = { "{", "}", "[", "]", "KEY(String)", "String", ":",
			"Number", "Bool", ",", "null" };

}
