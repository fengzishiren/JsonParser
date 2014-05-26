package com.lunatic.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author hzzhenglh
 * 2014年5月20日
 */
public class JsonUtil {
    private JsonUtil() {
    }

    public static final String NEW_LINE = System.getProperty("line.separator");

    public static boolean isPrimitive(Object o) {
        try {
            return ((Class<?>) o.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
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
                sb.append('"').append(e.getKey()).append("\": ").append(toJSON(e.getValue())).append(", ");
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
                sb.append(line).append(JsonUtil.NEW_LINE);
            }
            sb.delete(sb.length() - NEW_LINE.length(), sb.length());
        } finally {
            if (reader != null)
                reader.close();

        }
        return sb.toString();
    }
}
