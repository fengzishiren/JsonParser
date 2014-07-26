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
	public static Object fromFile(String filename) throws FileNotFoundException, IOException {
		return parser.parse(U.read(new FileReader(filename)));
	}
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String text = "{\"firstName\":\"Brett\",\"lastName\":\"McLaughlin\",\"email\":\"aaaa\\\"bbbb\", \"age\":18, \"sex\":true, \"wife\":null}";

		JsonObject jo = (JsonObject) parse(text);
		System.out.println(text);
		System.out.println(jo.toString());
	}

}