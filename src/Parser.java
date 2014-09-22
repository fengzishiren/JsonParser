import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author lunatic 2014年7月26日
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
		if (look != Token.EOF)
			error("Expect \"%s\", but given \"%s\"", Tag.toString(Tag.EOF),
					Tag.toString(look.type));
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
			error("Expect \"%s\", but given \"%s\"", Tag.toString(type),
					Tag.toString(look.type));
	}

	private Object parse() {
		restrict(Tag.OPEN_BRACE, Tag.OPEN_BRACKET);
		if (look.type == Tag.OPEN_BRACE)
			return obj();
		if (look.type == Tag.OPEN_BRACKET)
			return arr();
		return null;// never touch
	}

	private JsonObject obj() {
		JsonObject jo = new JsonObject();

		match(Tag.OPEN_BRACE);
		restrict(Tag.KEY_STRING, Tag.CLOSE_BRACE);
		if (look.type != Tag.CLOSE_BRACE) {
			Pair pair = pair();
			jo.put(pair.key, pair.val);

			while (restrict(Tag.COMMA, Tag.CLOSE_BRACE)
					&& look.type == Tag.COMMA) {
				match(Tag.COMMA);
				pair = pair();
				jo.put(pair.key, pair.val);
			}

		}
		match(Tag.CLOSE_BRACE);

		return jo;
	}

	private JsonArray arr() {
		JsonArray ja = new JsonArray();

		match(Tag.OPEN_BRACKET);
		restrict(Tag.STRING, Tag.NULL, Tag.NUMBER, Tag.BOOLEAN, Tag.OPEN_BRACE,
				Tag.OPEN_BRACKET, Tag.CLOSE_BRACKET);
		if (look.type != Tag.CLOSE_BRACKET) {
			Object value = value();
			ja.add(value);
			while (restrict(Tag.COMMA, Tag.CLOSE_BRACKET)
					&& look.type == Tag.COMMA) {
				match(Tag.COMMA);
				value = value();
				ja.add(value);
			}
		}
		match(Tag.CLOSE_BRACKET);

		return ja;
	}

	private Pair pair() {
		restrict(Tag.KEY_STRING);
		Object key = look.content;
		match(Tag.KEY_STRING);
		match(Tag.COLON);
		Object val = value();
		return new Pair(key, val);
	}

	private Object value() {
		restrict(Tag.STRING, Tag.NULL, Tag.NUMBER, Tag.BOOLEAN, Tag.OPEN_BRACE,
				Tag.OPEN_BRACKET);
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

	private boolean restrict(byte... types) {
		if (look != null) {
			for (byte b : types) {
				if (look.type == b)
					return true;
			}
		}
		List<String> ls = new ArrayList<String>(types.length);
		for (byte b : types) {
			ls.add(String.format("%s", Tag.toString(b)));
		}
		error("Expect \"%s\", but given \"%s\"", U.join(" | ", ls),
				Tag.toString(look.type));
		return false;
	}

	class Pair {
		Object key, val;

		public Pair(Object key, Object val) {
			this.key = key;
			this.val = val;
		}
	}
}
