/**
 * 
 * @author lunatic 2014年7月26日
 */
public class Token {
	Object content;
	byte type;
	Pos pos;

	public static final Token EOF = new Token(null, Tag.EOF, -1, -1);

	public Token(Object content, byte type, int row, int col) {
		super();
		this.content = content;
		this.type = type;
		pos = Pos.create(row, col);
	}

	public String toString() {
		return "[content: " + content + ", type: " + Tag.toString(type)+ "]";
	}
}
