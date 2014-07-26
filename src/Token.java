/**
 * 
 * @author lunatic
 * 2014年7月26日
 */
public class Token {
	Object content;
	byte type;
	Pos pos;

	public Token(Object content, byte type, int row, int col) {
		super();
		this.content = content;
		this.type = type;
		pos = Pos.create(row, col);
	}

	public String toString() {
		return "[content: " + content + ", type: " + Tag.types[type - 1] + "]";
	}
}
