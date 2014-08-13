import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author lunatic 2014年7月26日
 */
public class U {
	private U() {
	}

	private static Map<Character, String> escapeMap = new HashMap<Character, String>();

	static {
		escapeMap.put('\"', "\\\"");
		escapeMap.put('\\', "\\\\");
		escapeMap.put('/', "\\/");
		escapeMap.put('\b', "\\b");
		escapeMap.put('\f', "\\f");
		escapeMap.put('\n', "\\n");
		escapeMap.put('\r', "\\r");
		escapeMap.put('\t', "\\t");
		// escape.put("\\u", '\u5845')
	}
	/**
	 * 不同操作系统上的换行符号不统一 这里在读取文件的时候统一用’\n‘代替 在词法分析阶段被记录以便精确定位语法错误位置
	 */
	public static final char NEW_LINE = '\n';

	public static boolean isPrimitive(Object o) {
		try {
			return ((Class<?>) o.getClass().getField("TYPE").get(null))
					.isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	private static String escape(String s) {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			String str = escapeMap.get(c);
			// if (str == null)
			// S.syntaxError(String.format("Unrecognized \"%c\"", c));
			sb.append(str == null ? c : str);
		}
		return sb.toString();
	}

	public static String toString(Object o) {
		return escape(String.valueOf(o));
	}

	public static String toJson(Object o) {
		if (o instanceof String || isPrimitive(o)) {
			throw new SyntaxException("不是正确的JSON格式");
		}
		return toJSON(o);
	}

	private static String toJSON(Object o) {
		if (o instanceof Collection<?>) {

			Collection<?> c = (Collection<?>) o;
			StringBuilder sb = new StringBuilder("[");

			for (Object object : c) {
				sb.append(toJSON(object)).append(", ");
			}
			if (sb.length() > 1)
				sb.delete(sb.length() - 2, sb.length());
			sb.append("]");

			return sb.toString();

		} else if (o instanceof Map<?, ?>) {
			Map<?, ?> dict = (Map<?, ?>) o;
			Set<?> set = dict.entrySet();
			StringBuilder sb = new StringBuilder("{");

			for (Object object : set) {
				@SuppressWarnings("unchecked")
				Entry<Object, Object> e = (Entry<Object, Object>) object;
				sb.append('"').append(e.getKey()).append("\": ")
						.append(toJSON(e.getValue())).append(", ");
			}
			if (sb.length() > 1)
				sb.delete(sb.length() - 2, sb.length());
			sb.append("}");

			return sb.toString();
		}

		return toJSONObjectString(o);
	}

	private static String toJSONObjectString(Object o) {
		if (o == null)
			return "null";
		else if (o instanceof String) {
			return "\"" + o + "\"";
		} else if (isPrimitive(o))
			return o.toString();

		Class<? extends Object> clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();

		StringBuilder sb = new StringBuilder("{");
		for (Field field : fields) {
			field.setAccessible(true);
			Object o1 = null;
			try {
				o1 = field.get(o);
			} catch (Exception ignore) {

			}
			String val = toJSON(o1);
			String name = field.getName();
			sb.append('"').append(name).append("\": ").append(val).append(", ");
		}
		if (sb.length() > 1)
			sb.delete(sb.length() - 2, sb.length());
		// sb.deleteCharAt(sb.length() - 2);
		sb.append('}');
		return sb.toString();
	}

	public static final String read(Reader _reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(_reader);

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(U.NEW_LINE);
			}
			sb.deleteCharAt(sb.length() - 1);
		} finally {
			if (reader != null)
				reader.close();

		}
		return sb.toString();
	}

	public static final String join(String sep, List<String> ss) {
		if (ss.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = ss.iterator();
		while (true) {
			sb.append(it.next());
			if (it.hasNext())
				sb.append(sep);
			else
				break;
		}
		return sb.toString();
	}

	public static final String join(String sep, String... ss) {
		return join(sep, Arrays.asList(ss));
	}

	public static void main(String[] args) {
		String join = U.join(" ", Arrays.asList("a", "b"));

		System.out.println(join);
	}

}
