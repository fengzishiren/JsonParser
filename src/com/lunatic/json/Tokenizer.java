package com.lunatic.json;

import java.util.ArrayList;

/**
 * 
 * 标记/令牌生成器
 * 
 * 
 * 
 * @author lunatic
 */
public class Tokenizer {

	private char[] sens;

	public TokenList tokens = new TokenList(1000);

	private int index = 0;

	public Tokenizer(String text) {
		this.sens = text.toCharArray();
	}

	/**
	 * 
	 * 跳到下一个token 空行、空白字符、注释
	 */
	public void nextToken() {

		for (; index < sens.length;) {
			char c = sens[index];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				index++;
			}
			// skip '// xxx
			else if (c == '/' && sens[index + 1] == '/') {
				index++;
				while (sens[++index] != '\r' && sens[index] != '\n')
					;
				index += JsonUtil.NEW_LINE.length();
			}
			// skip '/* xxx */'
			else if (c == '/' && sens[index + 1] == '*') {
				index++;
				boolean expectFinish = false;
				while (sens[++index] != '*' || sens[++index] != '/')
					if (!expectFinish)
						expectFinish = true;
				if (!expectFinish)
					throw new SyntaxException("'/*'开始的注释必须以'*/'结束");
				index++; // next
			} else
				break;
		}
	}

	/**
	 * 
	 * 解析当前token
	 */
	public void parseToken() {
		char elem = sens[index];
		int start = index;
		byte type = -1;
		int length = -1;

		switch (elem) {
		case '{':
			type = TokenType.CURLY_BRACKET_LEFT;
			length = 1;
			index += length;
			break;
		case '}':
			type = TokenType.CURLY_BRACKET_RIGHT;
			length = 1;
			index += length;
			break;
		case '[':
			type = TokenType.SQUARE_BRACKET_LEFT;
			length = 1;
			index += length;
			break;
		case ']':
			type = TokenType.SQUARE_BRACKET_RIGHT;
			length = 1;
			index += length;
			break;
		case '\"':
			// Note:Quote skip
			// K, V Or ["", "", ""]
			type = TokenType.STRING_TOKEN;
			start = ++index; // skip '"'
			while (sens[index++] != '\"')
				;
			length = index - start - 1;
			break;
		case ':':
			type = TokenType.COLON;
			length = 1;
			index += length;
			break;
		case ',':
			type = TokenType.COMMA;
			length = 1;
			index += length;
			break;

		default:// deal with: number, boolean, null
			//第一个字符不可能为空
			while (++index != sens.length && sens[index] != ' ' && sens[index] != '\t'
					&& sens[index] != '\r' && sens[index] != '\n'
					&& sens[index] != ',' && sens[index] != ']'
					&& sens[index] != '}')
				;
			// index++;
			// endsWith ' '
			// ;
			length = index - start;

			String special = new String(sens, start, length);
			if (special.equals("true") || special.equals("false"))
				type = TokenType.BOOLEAN_TOKEN;
			else if (special.equals("null"))
				type = TokenType.NULL_TOKEN;
			else
				// Note：其他情形需要在解析器中测试 将转换失败的结果定位于语法错误
				type = TokenType.NUMBER_TOKEN;

				// number:13 boolean true/false
			break;
		}

		Token token = new Token(new String(sens, start, length), type);
		Log.d("token: " + token.content + " len:" + token.content.length()
				+ " type:" + token.type);
		tokens.append(token);
	}

	public TokenList parseTokens() {
		try {
			for (; index < sens.length;) {
				nextToken();
				if (index < sens.length)
					parseToken();
				else
					break;
			}
			return tokens;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SyntaxException("格式错误", e);
		}
	}
}

class TokenList extends ArrayList<Token> {
	private static final long serialVersionUID = 1L;

	public TokenList() {
		super(1000);
	}

	public TokenList(int capacity) {
		super(capacity);
	}

	public void append(Token tk) {
		super.add(tk);
	}
}

class Token {
	String content;

	byte type;

	public Token(String content, byte type) {
		super();
		this.content = content;
		this.type = type;
	}

}

interface TokenType {

	public static final byte CURLY_BRACKET_LEFT = 1; // {

	public static final byte CURLY_BRACKET_RIGHT = 2; // }

	public static final byte SQUARE_BRACKET_LEFT = 3; // [

	public static final byte SQUARE_BRACKET_RIGHT = 4; // ]

	public static final byte STRING_TOKEN = 5; //

	public static final byte NUMBER_TOKEN = 7; //

	public static final byte BOOLEAN_TOKEN = 8; //

	public static final byte NULL_TOKEN = 9; // null

	public static final byte COLON = 10; // :

	public static final byte COMMA = 11; // ,

}