import java.io.IOException;

import org.junit.Test;

/**
 * 
 * @author lunatic
 * 2014年7月26日
 */
public class JSONTest {
	/**
	 * 所有结果均用info级别打印
	 */
	@Test
	public void testFoundation() {
		String text = "{\"name\": \"hzzhenglh\"}";
		JsonObject jo = (JsonObject) JSON.fromString(text);

		Log.d("------------------------------------------");
		Object object = jo.get("name");
		Log.i(object);

		text = "{" + "\"name\": \"hzzhenglh\"," + "\"hobby\": ["
				+ "\"lanqiu\"," + "            \"yumaoqiu\"" + ", null]}";
		Log.d(text);

		jo = (JsonObject) JSON.fromString(text);
		Log.d("------------------------------------------");
		JsonArray ja = (JsonArray) jo.get("hobby");
		Log.i(ja);
		object = jo.get("name");
		Log.i(object);

		text = "[\"郑林海\", \"hzzhenglh@corpnetease.com\", 23]";
		Log.d(text);

		ja = (JsonArray) JSON.fromString(text);
		Log.i(ja);

		Log.w("END!");
	}

	@Test
	public void testFileJson() throws Exception {

		String json = UTest.readJsonFromFile("testmiddle.json");
		JsonObject jo = (JsonObject) JSON.fromString(json);
		Log.i("M: " + jo);

		json = UTest.readJsonFromFile("testbig.json");
		jo = (JsonObject) JSON.fromString(json);
		Log.i("B: " + jo);

		json = UTest.readJsonFromFile("test.json");
		jo = (JsonObject) JSON.fromString(json);
		Log.i("T: " + jo);
	}

	public static void main(String[] args) throws IOException {
		String j = UTest.readJsonFromFile("test.json");
		System.out.println(j);
	}

}
