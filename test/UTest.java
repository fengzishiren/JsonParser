import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
/**
 * 
 * @author lunatic
 * 2014年7月26日
 */
public class UTest {
	@Test
	public void testIsPrimitive() throws Exception {
		boolean result;
		int a = 10;
		result = U.isPrimitive(a);
		assertTrue(result);

		byte b = 3;
		result = U.isPrimitive(b);
		assertTrue(result);

		char c = 'a';
		result = U.isPrimitive(c);
		assertTrue(result);

		float f = 2.22f;
		result = U.isPrimitive(f);
		assertTrue(result);

		double d = 2323.3332323;
		result = U.isPrimitive(d);
		assertTrue(result);

		long l = System.currentTimeMillis();
		result = U.isPrimitive(l);
		assertTrue(result);

	}

	@Test 
	public void testJoin() throws Exception {
		String join = U.join(" ", Arrays.asList("a", "b", "c "));
		System.out.println(join);
	}
	
	@Test
	public void testToJson() throws Exception {

		@SuppressWarnings("unused")
		List<Data> list = Arrays.asList(new Data(), new Data());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "hzzhenglh");
		map.put("test", new Data());
		Log.d(U.toJson(map));
	}

	@Test
	public void testRead() throws Exception {

		String s = readJsonFromFile("testbig.json");
		Log.i(s);
	}

	public static final String readJsonFromFile(String name) throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(name);
		return U.read(new InputStreamReader(is));
	}
}

class Data {
	String name = "hzzhenglh";

	int age = 22;

	Object data = new OperationResult();
}

class OperationResult {
	int status = 0;

	String msg = "OK";

	Object attach = null;

	Boolean success = Boolean.TRUE;

	boolean permit = true;

	byte a = 23;

	double money = 2.333333;

	Long l = System.currentTimeMillis();
}