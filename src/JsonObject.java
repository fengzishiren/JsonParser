import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author lunatic 2014年7月26日
 */
public class JsonObject {
	private Map<Object, Object> properties = new HashMap<Object, Object>();

	public Object get(String key) {
		return properties.get(key);
	}

	public void put(Object key, Object val) {
		properties.put(key, val);
	}

	@Override
	public String toString() {

		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		Iterator<Entry<Object, Object>> it = entrySet.iterator();
		if (!it.hasNext())
			return "{}";
		StringBuilder sb = new StringBuilder("{");
		for (;;) {
			Entry<Object, Object> e = it.next();
			Object key = e.getKey();
			Object value = e.getValue();
			sb.append('\"').append(U.toString(key)).append("\": ");
			if (value == null) // or skip ?
				sb.append("null");
			else if (U.isPrimitive(value) || value instanceof JsonArray
					|| value instanceof JsonObject)
				sb.append(value);
			else
				sb.append('\"').append(U.toString(value)).append("\"");
			if (!it.hasNext())
				return sb.append('}').toString();
			sb.append(", ");
		}

	}

	public static void main(String[] args) {

		JsonObject jo = new JsonObject();
		jo.put("name", "hzzhe\"nglh");
		jo.put("age", 17);

		System.out.println(jo);

		JsonObject jos = new JsonObject();
		jos.put("obj", jo);

		JsonArray ja = new JsonArray();
		ja.add("nihao");
		ja.add(Boolean.TRUE);
		ja.add(jo);

		jos.put("ls", ja);
		System.out.println(jos);
	}
}
