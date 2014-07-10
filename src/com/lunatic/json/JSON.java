package com.lunatic.json;
import java.io.IOException;
import java.io.Reader;

public class JSON {

    public static Object fromString(String json) {
        return new Parser().parse(json);
    }

    public static Object fromString(Reader reader) throws IOException {
        return new Parser().parse(JsonUtil.read(reader));
    }

    public static String toJson(Object javaObject) {
        return JsonUtil.toJson(javaObject);
    }

}
