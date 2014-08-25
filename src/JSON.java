import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author lunatic 2014年7月26日
 */
public class JSON {


	public static Object parse(String text) {
		return new Parser().parse(text);
	}

	public static Object fromString(String text) {
		return parse(text);
	}

	public static Object fromFile(String filename)
			throws FileNotFoundException, IOException {
		return parse(U.read(new FileReader(filename)));
	}
	
	public static String toJSON(Object jo) {
	    return U.toJson(jo);
	}

}
