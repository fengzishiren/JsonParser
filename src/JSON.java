public class JSON {

	private static Parser parser = new Parser();

	public static Object fromString(String json) {
		return parser.parse(json);
	}

	public static void main(String[] args) {

		String text = "{\"name\": \"hzzhenglh\"}";
		JsonObject jo = (JsonObject) JSON.fromString(text);

		Log.d("------------------------------------------");
		Object object = jo.get("name");
		Log.d(object);

		text = "{" + "\"name\": \"hzzhenglh\"," + "\"hobby\": ["
				+ "\"lanqiu\"," + "            \"yumaoqiu\"" + ", null]}";

		Log.d(text);
		jo = (JsonObject) JSON.fromString(text);
		Log.d("------------------------------------------");
		JsonArray ja = (JsonArray) jo.get("hobby");
		Log.d(ja);
		object = jo.get("name");
		Log.d(object);

	}
}
