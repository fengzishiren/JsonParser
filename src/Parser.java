import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @(#)Main.java, 2014年5月19日.
 *
 * Copyright 2014 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 * @author hzzhenglh 2014年5月19日
 */
public class Parser {
	public static void main(String[] args) {

		Parser main = new Parser();
		JsonObject jo = (JsonObject) main.toObjectTree();
		Object name = jo.get("name");
		Log.d("name: " + name);

		JsonObject jp = (JsonObject) jo.get("project");
		Object id = jp.get("id");
		Log.d("id: " + id);

		JsonArray ja = (JsonArray) jo.get("members");
		Log.d("members:\t");
		jo = (JsonObject) ja.get(1);
		name = jo.get("name");
		Log.d("name: " + name);
		Log.d("end");
	}

	/**
	 * 
	 * { name : hzzhenglh , project : { id : X , expire : 1000 } } }
	 * 
	 * 
	 */

	// {"name": "hzzhenglh", "project": {"id": "X", "expire":1000}}
	// { name : hzzhenglh , project : { id : X, expire :1000 } }
	// String[] tokens = {"{", "name", ":", "hzzhenglh", ",", "project", ":",
	// "{", "id", ":", "X",
	// ",", "expire", ":", "1000", "}", "}"};

	Element[] elements = null;/*
							 * { new Element(ElementType.OBJ_START, "{"), new
							 * Element(ElementType.KEY, "name"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.VAL, "hzzhenglh"), new
							 * Element(ElementType.FIELD, ","), new
							 * Element(ElementType.KEY, "project"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.OBJ_START, "{"), new
							 * Element(ElementType.KEY, "id"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.VAL, "X"), new
							 * Element(ElementType.FIELD, ","), new
							 * Element(ElementType.KEY, "expire"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.VAL, 1000), new
							 * Element(ElementType.ObJ_END, "}"), new
							 * Element(ElementType.FIELD, ","), new
							 * Element(ElementType.KEY, "members"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.ARR_START, "["), new
							 * Element(ElementType.ARR_VAL, "郑林海"), new
							 * Element(ElementType.FIELD, ","), new
							 * Element(ElementType.OBJ_START, "{"), new
							 * Element(ElementType.KEY, "name"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.VAL, "hzzhenglh"), new
							 * Element(ElementType.FIELD, ","), new
							 * Element(ElementType.KEY, "project"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.OBJ_START, "{"), new
							 * Element(ElementType.KEY, "id"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.VAL, "X"), new
							 * Element(ElementType.FIELD, ","), new
							 * Element(ElementType.KEY, "expire"), new
							 * Element(ElementType.SEP, ":"), new
							 * Element(ElementType.VAL, 1000), new
							 * Element(ElementType.ObJ_END, "}"), new
							 * Element(ElementType.ObJ_END, "}"), new
							 * Element(ElementType.ARR_END, "]"), new
							 * Element(ElementType.ObJ_END, "}") };
							 */
	int pos = 0;

	private Stack<Element> stack = new Stack<Element>();

	private static final String OBJECT_START = "{";

	private static final String ARRAY_START = "[";

	private static final String OBJECT_END = "}";

	private static final String ARRAY_END = "]";

	String expect = null;

	public void push() {
		for (; pos < elements.length; pos++) {
			stack.push(elements[pos]);

			// json中有null类型时 content = null；
			Object content = elements[pos].content;
			// Log.e(content);

			if (OBJECT_END.equals(content)) {
				expect = OBJECT_START;
				pos++;
				break;
			} else if (ARRAY_END.equals(content)) {
				expect = ARRAY_START;
				pos++;
				break;
			}
		}
	}

	// { name : value, ... } 倒序
	// [ val, val, obj] 倒序
	public List<Element> pop() {
		ArrayList<Element> dataList = new ArrayList<Element>();
		Element pop = null;
		for (;;) {
			pop = stack.pop();

			dataList.add(pop);
			if (pop.content.equals(expect))
				break;

		}
		return dataList;
	}

	/**
	 * 
	 * [ val, arrval, .... ] OR {"":""}
	 * 
	 * @param eles
	 * @return
	 */
	public Element asObject(List<Element> eles) {

		for (Element e : eles) {
			Log.e("                    " + e.content);

		}
		JsonObject jo = null;
		JsonArray ja = null;
		for (int i = eles.size() - 1; i >= 0; i--) {
			Element curr = eles.get(i);
			int type = curr.type;

			switch (type) {
			case ElementType.OBJ_START:
				jo = new JsonObject();
				break;
			case ElementType.ARR_START:
				ja = new JsonArray();
				break;
			case ElementType.ARR_VAL:
				assertNotNull(ja, "必须以'['开始");
				ja.add(curr.getContent());
				break;
			case ElementType.KEY:
			case ElementType.SEP:
			case ElementType.VAL:
				Log.e(curr.content);
				assertNotNull(jo, "必须以'{'开始");
				Log.d("put:" + curr.content + "\t"
						+ eles.get(i).content);
				if (curr.type == ElementType.KEY
						&& eles.get(--i).type == ElementType.SEP) {
					i--;
					jo.put(curr.getContent(), eles.get(i).getContent());
				} else
					throw new SyntaxException(curr.content + "必须在‘：’之后");
				break;
			case ElementType.FIELD:
				break;
			case ElementType.ARR_END:
				break;
			case ElementType.ObJ_END:
				break;
			}
		}
		Log.d("~~~~~~~~~~~~~~~~" + jo);
		return new Element(ElementType.VAL, jo != null ? jo : ja);

	}

	/**
	 * 
	 * 生成对象树
	 * 
	 * @return 根元素
	 */
	public Object toObjectTree() {
		Element e = null;
		for (;;) {
			push();
			List<Element> tks = pop();
			Log.d("----------------------------------");
			e = asObject(tks);

			if (stack.isEmpty())
				break;
			stack.push(e);
		}
		if (pos != elements.length)
			Log.e("erro! POS");
		pos = 0; // 从0重新开始
		return e == null ? null : e.content;
	}

	private Element[] scanToken2Element(TokenList tokens) {
		Element[] eles = new Element[tokens.size()];
		int pos = 0;
		for (int i = 0; i < tokens.size(); i++) {

			Token token = tokens.get(i);
			switch (token.type) {
			case TokenType.CURLY_BRACKET_LEFT:
				eles[pos] = new Element(ElementType.OBJ_START, token.content);
				break;
			case TokenType.CURLY_BRACKET_RIGHT:
				eles[pos] = new Element(ElementType.ObJ_END, token.content);
				break;
			case TokenType.SQUARE_BRACKET_LEFT:
				eles[pos] = new Element(ElementType.ARR_START, token.content);
				break;
			case TokenType.SQUARE_BRACKET_RIGHT:
				eles[pos] = new Element(ElementType.ARR_END, token.content);
				break;
			case TokenType.COLON:
				eles[pos] = new Element(ElementType.SEP, token.content);
				break;
			case TokenType.COMMA:
				eles[pos] = new Element(ElementType.FIELD, token.content);
				break;
			case TokenType.STRING_TOKEN:
				Element e = new Element(token.content);
				if (tokens.get(i + 1).type == TokenType.COLON) { // is key
					e.type = ElementType.KEY;
					if (!(token.content instanceof String))
						throw new SyntaxException("name必须是string:"
								+ token.content);
				} else if (tokens.get(i - 1).type == TokenType.COLON) // is val
					e.type = ElementType.VAL;
				else
					e.type = ElementType.ARR_VAL;
				eles[pos] = e;
				break;
			case TokenType.NUMBER_TOKEN:
				Object res = null;
				try {
					res = Integer.parseInt(token.content);
				} catch (NumberFormatException e1) {
					try {
						res = Long.parseLong(token.content);
					} catch (NumberFormatException e2) {
						try {
							res = new BigDecimal(token.content);
						} catch (Exception e3) {
							throw new SyntaxException("无法识别的字符串："
									+ token.content);
						}
					}
				}
				Token tk = tokens.get(i - 1);
				boolean isVal = isValType(tk.type, token.content);
				eles[pos] = new Element(isVal ? ElementType.VAL
						: ElementType.ARR_VAL, token.content);
				break;

			case TokenType.BOOLEAN_TOKEN:
				Boolean b = token.content.equals("true") ? true : false;
				tk = tokens.get(i - 1);
				isVal = isValType(tk.type, token.content);
				eles[pos] = new Element(isVal ? ElementType.VAL
						: ElementType.ARR_VAL, b);
				break;
			case TokenType.NULL_TOKEN:
				tk = tokens.get(i - 1);
				isVal = isValType(tk.type, token.content);
				eles[pos] = new Element(isVal ? ElementType.VAL
						: ElementType.ARR_VAL, new Null());
				break;
			}
			pos++;
		}
		return eles;
	}

	private boolean isValType(int prevType, String orError) {
		if (prevType == TokenType.COLON)
			return true;
		else if (prevType == TokenType.COMMA
				|| prevType == TokenType.SQUARE_BRACKET_LEFT) // [ "",
																// ""]
			return false;
		else
			throw new SyntaxException("语法错误：" + orError);
	}

	public Object parse(String json) {
		Tokenizer tokenizer = new Tokenizer(json);
		TokenList tokens = tokenizer.parseTokens();
		elements = scanToken2Element(tokens);
		return toObjectTree();
	}

	private void assertNotNull(Object jo, String msg) {
		if (jo == null)
			throw new SyntaxException(msg);
	}
}

class Null {
	Object info;

	public Null() {
	}

	public Null(Object nil) {
		info = nil;
	}

	@Override
	public String toString() {
		return info == null ? "xixi: null" : info.toString();
	}
}

class Element {
	int type;

	Object content;

	public Element(Object content) {
		this.content = content;
	}

	public Object getContent() {
		return content instanceof Null ? null : content;
	}

	public Element(int type, Object content) {
		super();
		this.type = type;
		this.content = content;
	}
}

interface ElementType {
	int OBJ_START = -1;

	int KEY = 0;// key must be String

	int VAL = 1;

	int SEP = 2;

	int FIELD = 3;

	int ObJ_END = 5;

	int ARR_VAL = 6;

	int ARR_START = 8;

	int ARR_END = 9;

	// int OBJECT = 6;
	//
	// int ARRAY = 7;
}
