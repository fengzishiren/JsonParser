import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author lunatic 2014年7月26日
 */
public class JSON {

	private static Parser parser = new Parser();

	public static Object parse(String text) {
		return parser.parse(text);
	}

	public static Object fromString(String text) {
		return parser.parse(text);
	}

	public static Object fromFile(String filename)
			throws FileNotFoundException, IOException {
		return parser.parse(U.read(new FileReader(filename)));
	}

}
