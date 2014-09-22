/**
 * 
 * @author lunatic 2014年7月26日
 */
public class Pos {
	int row;
	int col;

	public Pos(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public static Pos create(int row, int col) {
		return new Pos(row, col);
	}

	public String toString() {
		return "位置：(" + (row == col && row == -1 ? "EOF" : row + ", " + col)
				+ ")";
	}
}
