/**
 * 
 * @author lunatic
 * 2014年7月26日
 */
public class Parser {
	private Lexer lexer;
	private Token look;

	public Parser() {
		this.lexer = new Lexer();
	}

	public Object parse(String text) {
		lexer.load(text);
		look = lexer.scan();
		Object o = parse();
		if (look != null)
			error("Expecting \"EOF\"");
		return o;
	}

	private void move() {
		look = lexer.scan();
	}

	private void error(String msg, Object... args) {
		S.syntaxError(String.format(msg, args), look.pos);
	}

	private void match(byte type) {
		if (look.type == type)
			move();
		else
			error("Expect \"%s\"", Tag.types[type - 1]);
	}

	private Object parse() {
		if (look.type == Tag.OPEN_BRACE)
			return obj();
		if (look.type == Tag.OPEN_BRACKET)
			return arr();
		error("Expect \"{\" or \"[\"");
		return null;//
	}

	private JsonObject obj() {
		JsonObject jo = new JsonObject();
		
		match(Tag.OPEN_BRACE);
		while (look.type != Tag.CLOSE_BRACE) {
			Pair pair = pair();
			jo.put(pair.key, pair.val);

			if (look.type == Tag.COMMA)
				match(Tag.COMMA);
			else
				break;
		}
		match(Tag.CLOSE_BRACE);
		
		return jo;
	}

	private JsonArray arr() {
		JsonArray ja = new JsonArray();

		match(Tag.OPEN_BRACKET);
		while (look.type != Tag.CLOSE_BRACKET) {
			Object value = value();
			ja.add(value);
			if (look.type == Tag.COMMA)
				match(Tag.COMMA);
			else
				break;
		}
		match(Tag.CLOSE_BRACKET);
		
		return ja;
	}

	private Pair pair() {
		Object key = look.content;
		match(Tag.KEY_STRING);
		match(Tag.COLON);
		Object val = value();
		return new Pair(key, val);
	}

	private Object value() {
		if (isVal()) {
			Object value = look.content;
			move();
			return value;
		} else
			return parse();
	}

	// string, number, false, true, null,
	private boolean isVal() {
		return look.type == Tag.BOOLEAN || look.type == Tag.NUMBER
				|| look.type == Tag.STRING || look.type == Tag.NULL;

	}
	
	class Pair {
		Object key, val;
		public Pair(Object key, Object val) {
			this.key = key;
			this.val = val;
		}
	}
}


