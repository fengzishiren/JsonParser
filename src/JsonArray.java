import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author lunatic 2014年7月26日
 */
public class JsonArray {
	private List<Object> array = new ArrayList<Object>();

	public int length() {
		return array.size();
	}

	public Object get(int index) {
		try {
			return array.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void add(Object o) {
		array.add(o);
	}

	@Override
	public String toString() {
		Iterator<Object> it = array.iterator();
		if (!it.hasNext())
			return "[]";
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			Object e = it.next();
			if (e == null)
				sb.append("null");
			else if (U.isPrimitive(e) || e instanceof JsonArray
					|| e instanceof JsonObject)
				sb.append(e);
			else
				sb.append('\"').append(U.toString(e)).append('\"');
			if (!it.hasNext())
				return sb.append(']').toString();
			sb.append(", ");
		}

	}

	public static void main(String[] args) {
		JsonArray ja = new JsonArray();
		ja.add("nihao");
		ja.add(Boolean.TRUE);
		System.out.println(ja);
		// boolean b = typeofObject(Integer.valueOf(99));
		// System.out.println(b);
	}
}
